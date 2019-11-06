package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NumberMap;

/**
 * Object for collecting NumberMaps
 * Not currently used
 */
public interface NumberMapCollection {

    boolean hasMap(CollatzConfig cc);
    NumberMap getMap(CollatzConfig cc);
    NumberMapCollection addMap(NumberMap nm);
}
