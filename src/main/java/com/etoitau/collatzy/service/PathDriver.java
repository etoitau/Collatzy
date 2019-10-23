package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathDriver {
    private CollatzConfig config;
    private CollatzCalculator calc;
    private NumberMap map;
    private Set<DeterminedPathNode> traveled;
    private DeterminedPathNode start, current;
    private ResultState result;

    public PathDriver(CollatzConfig config, NumberMap map, BigInteger startNum) {
        this.config = config;
        this.calc = new CollatzCalculator(config);
        this.map = map;
        startNewDrive(startNum);
    }

    public PathDriver(CollatzConfig config, NumberMap map) {
        this.config = config;
        this.calc = new CollatzCalculator(config);
        this.map = map;
    }

    public DeterminedPathNode startNewDrive(BigInteger startNum) {

        // check if already known
        if (map.contains(startNum)) {
            DeterminedPathNode found = map.get(startNum);
            if (found.getResult().getResult() == ResultState.Result.LOOP) {
                result = found.getResult();
                return found;
            }
        }

        // initialize for drive
        result = null;
        start = new PathNodeBuilder(startNum).addConfig(config).getNode();
        current = start;
        traveled = new HashSet<>();
        map.add(current);
        traveled.add(current);
        return start;
    }

    public boolean hasResult() {
        return (result != null && result.getResult() != null &&result.getResult() == ResultState.Result.LOOP);
    }

    public DeterminedPathNode next() {
        // get next node
        current.setNext(new PathNodeBuilder(calc.next(current.getValue())).addConfig(config).getNode());
        current = current.getNext();

        if (map.contains(current)) {
            // if in previously known territory, see if we know result
            ResultState prevResult = map.get(current.getValue()).getResult();
            if (prevResult != null && prevResult.getResult() == ResultState.Result.LOOP) {
                result = prevResult;
                saveDrive();
                return current;
            }
        }
        if (traveled.contains(current)) {
            // if we've been here before on this drive, then we're in a loop
            result = new ResultState(ResultState.Result.LOOP);
            // this combines this version with updated result with past version that has next
            map.add(current);
            // give it to result and it will detect and save the loop
            result.addLoop(current);
            // save drive to update result in everything we've visited
            saveDrive();
        }
        map.add(current);
        traveled.add(current);
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
