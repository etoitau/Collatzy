package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.CollatzConfig;
import com.etoitau.collatzy.domain.NodeWithResult;
import com.etoitau.collatzy.domain.PathNode;
import com.etoitau.collatzy.domain.ResultState;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PathNodeBuilderTest {
    private static BigInteger oneVal = new BigInteger("1");
    private static NodeWithResult twoNode = new PathNode(new BigInteger("4"));
    private static CollatzConfig altConfig = new CollatzConfig(2, 2, 2);
    private static ResultState open = new ResultState(ResultState.Result.OPEN);
    private static ResultState loop = new ResultState((ResultState.Result.LOOP));

    @Test
    void addNext() {
        PathNodeBuilder pnb = new PathNodeBuilder(oneVal);
        assertNull(pnb.getNode().getNext());
        pnb.addNext(twoNode);
        assertEquals(twoNode, pnb.getNode().getNext());
    }

    @Test
    void addResult() {
        PathNodeBuilder pnb = new PathNodeBuilder(oneVal);
        assertEquals(open.getResult(), pnb.getNode().getResult().getResult());
        pnb.addResult(loop);
        assertEquals(loop.getResult(), pnb.getNode().getResult().getResult());
    }

    @Test
    void addConfig() {
        PathNodeBuilder pnb = new PathNodeBuilder(oneVal);
        assertEquals(new CollatzConfig(), pnb.getNode().getConfig());
        pnb.addConfig(altConfig);
        assertEquals(altConfig, pnb.getNode().getConfig());
    }

    @Test
    void getNode() {
        // covered by above
    }

    @Test
    void combineWith() {
        PathNodeBuilder pnb = new PathNodeBuilder(oneVal);
        boolean gotException = false;
        try {
            pnb.combineWith(twoNode);
        } catch (Exception e) {
            gotException = true;
        }
        assertTrue(gotException);
        NodeWithResult oneNodePlus = new PathNodeBuilder(oneVal).addNext(twoNode).addResult(loop).getNode();

        try {
            pnb.combineWith(oneNodePlus);
        } catch (Exception e) {
            fail();
        }
        assertEquals(twoNode, pnb.getNode().getNext());
        assertEquals(loop, pnb.getNode().getResult());
    }

    @Test
    void combineDeterminedPathNodes() {
        PathNodeBuilder pnb = new PathNodeBuilder(oneVal);
        NodeWithResult oneNode = pnb.getNode();
        assertFalse(PathNodeBuilder.combineDeterminedPathNodes(oneNode, twoNode));
        NodeWithResult oneNodePlus = new PathNodeBuilder(oneVal).addNext(twoNode).addResult(loop).getNode();

        PathNodeBuilder.combineDeterminedPathNodes(oneNode, oneNodePlus);

        assertEquals(twoNode, oneNode.getNext());
        assertEquals(loop, oneNode.getResult());
    }
}