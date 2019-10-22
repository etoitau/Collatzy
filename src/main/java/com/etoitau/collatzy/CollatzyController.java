package com.etoitau.collatzy;

import com.etoitau.collatzy.domain.*;
import com.etoitau.collatzy.service.PathDriver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;


@RestController
public class CollatzyController {

    @RequestMapping("/path")
    public String greeting(@RequestParam(value="num", defaultValue="1") String numString,
                             @RequestParam(value="d", defaultValue = "2") Integer d,
                             @RequestParam(value="m", defaultValue = "3") Integer m,
                             @RequestParam(value="p", defaultValue = "1") Integer p) {
        CollatzConfig config = new CollatzConfig(d, m, p);
        NumberMap map = new NumberMap(config);
        BigInteger startNum = new BigInteger(numString);
        PathDriver pd = new PathDriver(config, map, startNum);
        for (int i = 0; i < 100; i++) {
            if (pd.hasResult()) break;
            pd.next();
        }
        DeterminedPathNode start = map.get(startNum);
        Path path = new Path(start);
        Report rept = new Report(path);
        return rept.verboseReport();
    }
}