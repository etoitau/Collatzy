package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NumberMap;

public interface NumberMapCollection {

    public boolean hasMap(CollatzConfig cc);
    public NumberMap getMap(CollatzConfig cc);
    public NumberMapCollection addMap(NumberMap nm);
}
