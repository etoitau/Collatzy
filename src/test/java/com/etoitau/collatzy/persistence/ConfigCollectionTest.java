package com.etoitau.collatzy.persistence;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NodeWithResult;
import com.etoitau.collatzy.domain.NumberMap;
import com.etoitau.collatzy.domain.PathNode;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConfigCollectionTest {

    @Test
    void demoTest() {
        // setup
        ConfigCollection cc = new ConfigCollection();
        CollatzConfig config1 = new CollatzConfig(2, 3,1 );
        CollatzConfig config2 = new CollatzConfig(2, 2, 2);
        NumberMap nm = new NumberMap(config1);
        NumberMap nm2 = new NumberMap(config1);
        NumberMap nm3 = new NumberMap(config2);
        NodeWithResult one1 = new PathNode(new BigInteger("1"), config1);
        NodeWithResult one2 = new PathNode(new BigInteger("1"), config2);
        NodeWithResult two1 = new PathNode(new BigInteger("2"), config1);
        NodeWithResult two2 = new PathNode(new BigInteger("2"), config2);
        NodeWithResult one1Plus = new PathNode(new BigInteger("1"), config1);
        one1Plus.setNext(two1);
        nm.add(one1Plus);
        nm2.add(one1);
        nm2.add(two1);
        nm3.add(one2);
        nm3.add(two2);

        // should start empty
        assertFalse(cc.hasMap(config1));
        // should be able to add, detect, and retrieve
        cc.addMap(nm);
        assertTrue(cc.hasMap(config1));
        assertEquals(nm, cc.getMap(config1));
        // add NumberMap with same config, but different elements
        cc.addMap(nm2);
        // entry should have node that only exists in map just added
        assertEquals(two1, cc.getMap(config1).get(new BigInteger("2")));
        // entry should have composite of node it had before and version in added map
        assertEquals(two1, cc.getMap(config1).get(new BigInteger("1")).getNext());

        // test can remove
        cc.addMap(nm3);
        cc.remove(config1);
        assertFalse(cc.hasMap(config1));
        assertTrue(cc.hasMap(config2));

        // test can clear
        cc.clear();
        assertFalse(cc.hasMap(config2));
    }


}