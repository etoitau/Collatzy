package com.etoitau.collatzy;

import com.etoitau.collatzy.domain.*;
import com.etoitau.collatzy.persistence.ConfigCollection;
import com.etoitau.collatzy.service.Helper;
import com.etoitau.collatzy.service.PathDriver;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

// to run: ./mvnw package && java -jar target/collatzy-0.0.1-SNAPSHOT.jar

@Controller
public class CollatzyController {
    private ConfigCollection cc = new ConfigCollection();
    private Logger logger = LoggerFactory.getLogger(CollatzyController.class);

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @GetMapping("/")
    public String index() {
        logger.info("index called");
        return "run_number_form";
    }

    @ResponseBody
    @RequestMapping("/path")
    public String getReportFor(@RequestParam(value="num", defaultValue="1") String numString,
                               @RequestParam(value="d", defaultValue = "2") String dStr,
                               @RequestParam(value="m", defaultValue = "3") String mStr,
                               @RequestParam(value="p", defaultValue = "1") String pStr,
                               @RequestParam(value="n", defaultValue = "100") String nStr,
                               @RequestParam(value="report", defaultValue = "verbose") String reportType) {
        logger.info(String.format("getReportFor called with num = %s, d = %s, m = %s, p = %s, and n = %s",
                numString, dStr, mStr, pStr, nStr));
        Integer d = Helper.parseStringWithDefault(dStr, 2);
        Integer m = Helper.parseStringWithDefault(mStr, 3);
        Integer p = Helper.parseStringWithDefault(pStr, 1);
        Integer n = Helper.parseStringWithDefault(nStr, 100);

        CollatzConfig config = new CollatzConfig(d, m, p);
        NumberMap map;
        if (cc.hasMap(config)) {
            map = cc.getMap(config);
        } else {
            map = new NumberMap(config);
            cc.addMap(map);
        }
        BigInteger startNum = new BigInteger(numString);
        PathDriver pd = new PathDriver(config, map, startNum);
        for (int i = 0; i < n; i++) {
            if (pd.hasResult()) break;
            pd.next();
        }
        DeterminedPathNode start = map.get(startNum);
        Path path = new Path(start);
        Report rept = new Report(path);
        return (reportType.equals("json"))? rept.jsonReport(): rept.verboseReport();
    }
}