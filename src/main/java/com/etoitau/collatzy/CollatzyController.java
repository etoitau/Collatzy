package com.etoitau.collatzy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CollatzyController {

    @RequestMapping("/path")
    public String greeting(@RequestParam(value="num", defaultValue="1") String numString,
                             @RequestParam(value="d", defaultValue = "2") Integer d,
                             @RequestParam(value="m", defaultValue = "3") Integer m,
                             @RequestParam(value="p", defaultValue = "1") Integer p) {

        return "start at: " + numString + "/nvalues: d = " + d + "/nm = " + m + "/np = " + p;
    }
}