package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.*;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ContinueTest {

    /**
     * There was a bug where every other continue didn't seem to advance the search at all
     * This test is to help investigate, and then check it doesn't reoccur
     */
    @Test
    public void continueTest() {
        // this config won't converge, so we can run as far as we need
        int d = 2, m = 2, p = 4;
        BigInteger start = new BigInteger("12"), cont = null;

        CollatzConfig config = new CollatzConfig(d, m, p);
        NumberMap map = new NumberMap(config);

        PathDriver pd = new PathDriver(config, map);

        // user starts at 12 ends up at 14
        pd.startNewDrive(start);
        for (int i = 0; i < 5; i++) {
            if (pd.hasResult()) break;
            pd.next();
        }
        NodeWithResult startNode = map.get(start);
        Path path = new Path(startNode);
        PathReport rept = new Report(path);
        String contStr = rept.lastNum();
        cont = new BigInteger(contStr); // 14

        // now continue from prev. goes from prev 14 to 11
        pd.startNewDrive(cont);
        for (int i = 0; i < 5; i++) {
            if (pd.hasResult()) break;
            pd.next();
        }
        startNode = map.get(start);
        path = new Path(startNode);
        rept = new Report(path);
        contStr = rept.lastNum();
        cont = new BigInteger(contStr);

        // continue again. goes from 11 to 34
        pd.startNewDrive(cont);
        for (int i = 0; i < 5; i++) {
            if (pd.hasResult()) break;
            pd.next();
        }
        startNode = map.get(start);
        path = new Path(startNode);
        rept = new Report(path);
        contStr = rept.lastNum();
        cont = new BigInteger(contStr);
        assertEquals("34", contStr);
    }
}
