package com.etoitau.collatzy.service;

import java.math.BigInteger;

public class Helper {

    /**
     * For converting form input to an integer
     * @param s - input from form as String
     * @param def - if s is no good, use this value
     * @return - parsed s if possible, or def
     */
    public static Integer parseStringToIntegerWithDefault(String s, Integer def) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * For converting form input to a BigInteger
     * @param s - input from form as String
     * @param def - if s is no good, use this value
     * @return - parsed s if possible, or def
     */
    public static BigInteger parseStringToBigIntegerWithDefault(String s, String def) {
        try {
            return new BigInteger(s.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return (def == null)? null: new BigInteger(def);
        }
    }
}
