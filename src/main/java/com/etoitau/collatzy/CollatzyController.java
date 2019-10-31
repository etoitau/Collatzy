package com.etoitau.collatzy;

import com.etoitau.collatzy.domain.*;
import com.etoitau.collatzy.persistence.ConfigCollection;
import com.etoitau.collatzy.persistence.ConfigEntry;
import com.etoitau.collatzy.persistence.ConfigNodesRepository;
import com.etoitau.collatzy.service.*;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

// to run: ./mvnw package && java -jar target/collatzy-0.0.1-SNAPSHOT.jar

@Controller
public class CollatzyController {
    private ConfigCollection cc = new ConfigCollection();
    private Logger logger = LoggerFactory.getLogger(CollatzyController.class);

    private static final Integer DEFAULT_RUN_SIZE = 100;

    @Autowired
    ConfigNodesRepository repository;

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @GetMapping(value={"/", "/run"})
    public String index(Model model) {
        logger.info("index or run called by Get");
        model.addAttribute("d", "2");
        model.addAttribute("m", "3");
        model.addAttribute("p", "1");
        model.addAttribute("num", "12");
        model.addAttribute("hasReport", false);

        model.addAttribute("runForm", new RunForm());
        return "run_number_form";
    }

    @PostMapping("/run")
    public String runSubmit(@ModelAttribute RunForm runForm, Model model) {
        logger.info("Run called by Post");
        PathReport rept = getReportScript(runForm);
        model.addAttribute("d", runForm.getD());
        model.addAttribute("m", runForm.getM());
        model.addAttribute("p", runForm.getP());
        model.addAttribute("num", runForm.getNum());
        model.addAttribute("cont", rept.lastNum());
        model.addAttribute("hasReport", true);
        model.addAttribute("report", new ReportPrinterHTML(rept).print());
        model.addAttribute("isUnknown", rept.isUnknown());
        return "run_number_form";
    }


    /**
     * RESTful API for running an analysis of a Collatz-like problem
     * i.e.:
     * f(num + 1) = (num % d == 0)? n / d: num * m + p
     * following parameters are string representations of the above variables
     * @param numString - the starting number, n
     * @param dStr - the divisor, 2 in the classic case
     * @param mStr - the multiplier, 3 in the classic case
     * @param pStr - the addend, 1 in the classic case
     * @param nStr - max number of many iterations to run, many problems do not converge
     * @param reportType - "json" for a json-formatted info dump, otherwise an html report
     * @return - the report as specified above
     */
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
        PathReport rept = getReportScript(numString, dStr, mStr, pStr, nStr, null);
        ReportPrinter printer = (reportType.equals("json"))? new ReportPrinterJson(rept): new ReportPrinterHTML(rept);
        return printer.print();
    }

    @ResponseBody
    @RequestMapping("/msg")
    public String saveMessage(@RequestParam(value="msg", defaultValue="") String msg) {
        logger.info(String.format("saveMessage called with message %s", msg));
        ConfigEntry entry = new ConfigEntry(new CollatzConfig());
        entry.setJsonNodes(msg);
        repository.save(entry);
        return "saved";
    }

    @ResponseBody
    @RequestMapping("/find")
    public String getEntry() {
        logger.info("getEntry called");
        List<ConfigEntry> results = repository.findAll();
        return results.get(0).getJsonNodes();
    }


    private PathReport getReportScript(
            String numString, String dStr, String mStr, String pStr, String nStr, String continueFrom) {
        logger.info(String.format("getReportScript called with num = %s, d = %s, m = %s, p = %s, and continueFrom = %s",
                numString, dStr, mStr, pStr, continueFrom));
        Integer d = Helper.parseStringToIntegerWithDefault(dStr, 2);
        Integer m = Helper.parseStringToIntegerWithDefault(mStr, 3);
        Integer p = Helper.parseStringToIntegerWithDefault(pStr, 1);
        Integer n = Helper.parseStringToIntegerWithDefault(nStr, DEFAULT_RUN_SIZE);
        BigInteger startNum = Helper.parseStringToBigIntegerWithDefault(numString, "12");
        BigInteger contNum = Helper.parseStringToBigIntegerWithDefault(continueFrom, null);

        CollatzConfig config = new CollatzConfig(d, m, p);
        NumberMap map;
        if (cc.hasMap(config)) {
            map = cc.getMap(config);
        } else {
            map = new NumberMap(config);
            cc.addMap(map);
        }

        PathDriver pd;
        if (contNum != null) {
            pd = new PathDriver(config, map, contNum);
            for (int i = 0; i < n; i++) {
                if (pd.hasResult()) break;
                pd.next();
            }
        }
        pd = new PathDriver(config, map, startNum);
        for (int i = 0; i < n; i++) {
            if (pd.hasResult()) break;
            pd.next();
        }
        NodeWithResult start = map.get(startNum);
        Path path = new Path(start);
        return new Report(path);
    }

    private PathReport getReportScript(RunForm runForm) {
        logger.info("getReportScript called with form");

        return getReportScript(runForm.getNum(),
                runForm.getD(),
                runForm.getM(),
                runForm.getP(),
                DEFAULT_RUN_SIZE.toString(),
                runForm.getCont()
        );
    }

    private void syncWithDB(CollatzConfig config) {
        // get serialized from db for config
        // turn into list of nodes
        // add all to numbermap
        // get numbermap list of nodes
        // serialize
        // update to db
    }
}