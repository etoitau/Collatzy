package com.etoitau.collatzy.service;

public class Helper {

    public static Integer parseStringWithDefault(String s, Integer def) {
        try {
            Integer sToInt = Integer.parseInt(s);
            return sToInt;
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
