package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathDriver {
    private CollatzCalculator calc;
    private NumberMap map;
    private Set<DeterminedPathNode> traveled;
    private DeterminedPathNode start, current;
    private ResultState result;

    public PathDriver(CollatzConfig config, NumberMap map, BigInteger startNum) {
        this.calc = new CollatzCalculator(config);
        this.map = map;
        startNewDrive(startNum);
    }

    public PathDriver(CollatzConfig config, NumberMap map) {
        this(config, map, null);
    }

    public ResultState startNewDrive(BigInteger startNum) {
        // check if already known
        if (map.contains(startNum)) {
            result = map.get(startNum).getResult();
            return result;
        }
        // initialize for drive
        result = null;
        start = new PathNode(startNum);
        current = start;
        traveled = new HashSet<>();
        traveled.add(current);
        return null;
    }

    public boolean hasResult() {
        return (result != null);
    }

    public DeterminedPathNode next() {
        // get next node
        current.setNext(new PathNode(calc.next(current.getValue())));
        current = current.getNext();

        if (map.contains(current)) {
            // if in previously known territory, see if we know result
            ResultState prevResult = map.get(current.getValue()).getResult();
            if (prevResult.getResult() == ResultState.Result.LOOP) {
                result = prevResult;
            }
        } else if (traveled.contains(current)) {
            // if we've been here before on this drive, then we're in a loop
            result = new ResultState(ResultState.Result.LOOP);
            List<DeterminedPathNode> loop = new ArrayList<>();
            loop.add(current);
            current = current.getNext();
            while (!loop.contains(current)) {
                loop.add(current);
                current = current.getNext();
            }
            result.addLoop(loop);
            saveDrive();
        }
        map.add(current);
        return current;
    }

    private void saveDrive() {
        List<DeterminedPathNode> loop = result.getLoopNodes();
        DeterminedPathNode cursor = start;
        cursor.setResult(result);
        while (!loop.contains(cursor)) {
            map.add(cursor);
            cursor = cursor.getNext();
            cursor.setResult(result);
        }
        for (DeterminedPathNode node : loop) {
            node.setResult(result);
            map.add(node);
        }
    }
}
