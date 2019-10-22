package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.DeterminedPathNode;
import com.etoitau.collatzy.domain.NumberMap;
import com.etoitau.collatzy.domain.ResultState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PathDriverTest {

    @Test
    void DemoTest() {
        CollatzConfig config = new CollatzConfig();
        NumberMap map = new NumberMap(config);
        PathDriver pd = new PathDriver(config, map);
        pd.startNewDrive(new BigInteger("5"));
        BigInteger bi = new BigInteger("16");

        DeterminedPathNode node = pd.next(); // 16
        assertEquals(bi, node.getValue());
        pd.next(); // 8
        pd.next(); // 4
        pd.next(); // 2
        node = pd.next(); // 1
        // should not have detected loop yet
        assertEquals(ResultState.Result.OPEN, node.getResult().getResult());
        assertFalse(pd.hasResult());
        // first repeated element, should detect loop
        node = pd.next(); // 4
        // should have updated result for all in path
        assertEquals(ResultState.Result.LOOP, node.getResult().getResult());
        assertEquals(ResultState.Result.LOOP, map.get(bi).getResult().getResult());
        assertEquals(ResultState.Result.LOOP, map.get(new BigInteger("5")).getResult().getResult());
        // result should have loop nodes saved
        assertEquals(3, node.getResult().getLoopNodes().size());
        assertTrue(pd.hasResult());

        // again with new config
        config = new CollatzConfig(2, 3, -1);
        map = new NumberMap(config);
        pd = new PathDriver(config, map);
        pd.startNewDrive(new BigInteger("4"));
        bi = new BigInteger("2");

        node = pd.next(); // 2
        assertEquals(bi, node.getValue());
        node = pd.next(); // 1
        // should not have detected loop yet
        assertEquals(ResultState.Result.OPEN, node.getResult().getResult());
        // first repeated element, should detect loop
        node = pd.next(); // 2
        // should have updated result for all in path
        assertEquals(ResultState.Result.LOOP, node.getResult().getResult());
        assertEquals(ResultState.Result.LOOP, map.get(bi).getResult().getResult());
        assertEquals(ResultState.Result.LOOP, map.get(new BigInteger("4")).getResult().getResult());
        // result should have loop nodes saved
        assertEquals(2, node.getResult().getLoopNodes().size()); // 2, 1
    }



    @Test
    void hasResult() {
        // covered by demo
    }

    @Test
    void next() {
        // covered by demo
    }
}