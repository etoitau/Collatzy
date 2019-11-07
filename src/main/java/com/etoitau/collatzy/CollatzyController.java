package com.etoitau.collatzy;

import com.etoitau.collatzy.domain.*;
import com.etoitau.collatzy.persistence.ConfigCollection;
import com.etoitau.collatzy.persistence.ConfigEntry;
import com.etoitau.collatzy.persistence.ConfigNodesRepository;
import com.etoitau.collatzy.persistence.DatabaseManager;
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

// to run: ./mvnw package && java -jar target/collatzy-0.0.1-SNAPSHOT.jar

@Controller
public class CollatzyController {
    private Logger logger = LoggerFactory.getLogger(CollatzyController.class);

    private static final Integer DEFAULT_RUN_SIZE = 100;

    @Autowired
    private ConfigNodesRepository repository;

    @Autowired
    private DatabaseManager dm;

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @GetMapping(value={"/", "/about"})
    public String index() {
        return "about";
    }

    @GetMapping(value={"/api"})
    public String api() {
        return "api";
    }

    @GetMapping(value={"/run"})
    public String run(Model model,
                      @RequestParam(value="num", defaultValue="12") String numString,
                      @RequestParam(value="d", defaultValue = "2") String dStr,
                      @RequestParam(value="m", defaultValue = "3") String mStr,
                      @RequestParam(value="p", defaultValue = "1") String pStr) {
        logger.info("run called by Get");
        model.addAttribute("d", dStr);
        model.addAttribute("m", mStr);
        model.addAttribute("p", pStr);
        model.addAttribute("num", numString);
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
                               @RequestParam(value="report", defaultValue = "verbose") String reportType) {
        logger.info(String.format("getReportFor called with num = %s, d = %s, m = %s, p = %s, and n = %s",
                numString, dStr, mStr, pStr, "100"));
        PathReport rept = getReportScript(numString, dStr, mStr, pStr, "100", null);
        ReportPrinter printer = (reportType.equals("json"))? new ReportPrinterJson(rept): new ReportPrinterHTML(rept);
        return printer.print();
    }

    @ResponseBody
    @RequestMapping("/find")
    public String getEntry(@RequestParam(value="d", defaultValue = "2") String dStr,
                           @RequestParam(value="m", defaultValue = "3") String mStr,
                           @RequestParam(value="p", defaultValue = "1") String pStr) {
        logger.info("getEntry called with " + dStr + ", " + mStr + ", " + pStr);
        Integer d = Helper.parseStringToIntegerWithDefault(dStr, 2);
        Integer m = Helper.parseStringToIntegerWithDefault(mStr, 3);
        Integer p = Helper.parseStringToIntegerWithDefault(pStr, 1);
        ConfigEntry result = dm.getEntry(new CollatzConfig(d, m, p));
        return result.getSerialNodes();
    }

//    @ResponseBody
//    @RequestMapping("/msg")
//    public String saveMessage(@RequestParam(value="msg", defaultValue="") String msg,
//                              @RequestParam(value="d", defaultValue = "2") String dStr,
//                              @RequestParam(value="m", defaultValue = "3") String mStr,
//                              @RequestParam(value="p", defaultValue = "1") String pStr) {
//        logger.info("msg called");
//        Integer d = Helper.parseStringToIntegerWithDefault(dStr, 2);
//        Integer m = Helper.parseStringToIntegerWithDefault(mStr, 3);
//        Integer p = Helper.parseStringToIntegerWithDefault(pStr, 1);
//        CollatzConfig config = new CollatzConfig(d, m, p);
//        ConfigEntry entry = dm.getEntry(config);
//        entry.setSerialNodes(msg);
//        repository.save(entry);
//        return "saved";
//    }

//    @ResponseBody
//    @RequestMapping("/delete")
//    public String deleteEntry(@RequestParam(value="d", defaultValue = "2") String dStr,
//                           @RequestParam(value="m", defaultValue = "3") String mStr,
//                           @RequestParam(value="p", defaultValue = "1") String pStr) {
//        logger.info("deleteEntry called");
//        Integer d = Helper.parseStringToIntegerWithDefault(dStr, 2);
//        Integer m = Helper.parseStringToIntegerWithDefault(mStr, 3);
//        Integer p = Helper.parseStringToIntegerWithDefault(pStr, 1);
//        dm.delete(new CollatzConfig(d, m, p));
//        return "deleted";
//    }


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

        ConfigEntry entry = dm.getEntry(config);
        NumberMap map = dm.getMapFromEntry(entry);

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
        dm.saveMap(map);

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

}