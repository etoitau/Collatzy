package com.etoitau.collatzy.service;

import java.math.BigInteger;

public class Helper {

    public static Integer parseStringToIntegerWithDefault(String s, Integer def) {
        try {
            Integer sToInt = Integer.parseInt(s);
            return sToInt;
        } catch (NumberFormatException e) {
            return def;
        }
    }

    public static BigInteger parseStringToBigIntegerWithDefault(String s, String def) {
        try {
            return new BigInteger(s.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return (def == null)? null: new BigInteger(def);
        }
    }
}
