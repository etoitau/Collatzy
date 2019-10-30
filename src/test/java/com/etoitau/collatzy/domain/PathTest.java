package com.etoitau.collatzy.domain;

import com.etoitau.collatzy.service.PathDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    static NodeWithResult five, twelve;

    @BeforeAll
    static void init() {

        PathDriver pd = new PathDriver(new CollatzConfig(), new NumberMap(new CollatzConfig()));
        five = pd.startNewDrive(new BigInteger("5"));
        pd.next(); // 16
        pd.next(); // 8
        pd.next(); // 4
        pd.next(); // 2
        pd.next(); // 1
        pd.next(); // 4

        twelve = pd.startNewDrive(new BigInteger("12"));
        pd.next(); // 6
        pd.next(); // 3
    }

    @Test
    void getPath() {
        NodePath path = new Path(five);
        List<NodeWithResult> list = path.getPath();
        assertEquals(five, list.get(0));
        assertEquals(6, list.size());
        assertEquals(new BigInteger("1"), list.get(list.size() - 1).getValue());

        path = new Path(twelve);
        list = path.getPath();
        assertEquals(3, list.size());
    }

    @Test
    void getValuePath() {
        NodePath path = new Path(five);
        List<BigInteger> list = path.getValuePath();
        assertEquals(new BigInteger("5"), list.get(0));
        assertEquals(6, list.size());
        assertEquals(new BigInteger("1"), list.get(list.size() - 1));

        path = new Path(twelve);
        list = path.getValuePath();
        assertEquals(3, list.size());
    }
}