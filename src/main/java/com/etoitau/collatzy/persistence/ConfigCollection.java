package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NumberMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Object for collecting NumberMaps
 * Not currently used
 */
public class ConfigCollection implements NumberMapCollection {
    private Map<CollatzConfig, NumberMap> map = new HashMap<>();


    @Override
    public boolean hasMap(CollatzConfig cc) {
        return map.containsKey(cc);
    }

    @Override
    public NumberMap getMap(CollatzConfig cc) {
        return map.get(cc);
    }

    @Override
    public NumberMapCollection addMap(NumberMap nm) {
        CollatzConfig cc = nm.getConfig();
        if (!hasMap(cc)) {
            map.put(cc, nm);
        } else {
            map.get(cc).addAll(nm.getNodes());
        }
        return this;
    }

    public void clear() {
        map = new HashMap<>();
    }

    public NumberMap remove(CollatzConfig cc) {
        return map.remove(cc);
    }
}
