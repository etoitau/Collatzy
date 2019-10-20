package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathDriver {
    CollatzCalculator calc;
    NumberMap map;
    Set<PathNode> traveled;
    PathNode start, current;
    ResultState result;

    public PathDriver(CollatzConfig config, NumberMap map) {
        this.calc = new CollatzCalculator(config);
        this.map = map;
    }

    public ResultState runDrive(BigInteger startNum) {
        if (map.contains(start)) return map.get(start.getVal()).getResult();

        start = new PathNode(startNum);

        PathNode current = start;

        traveled = new HashSet<>();
        traveled.add(current);

        while(true) {
            // get next node
            current.setNext(new PathNode(calc.next(current.getVal())));
            current = current.getNext();
            // if in previously known territory
            if (map.contains(current)) {
                result = map.get(current.getVal()).getResult();
                return result;
            }

            // if we've seen this before
            if (traveled.contains(current)) {
                // if we've been here before
                if (current.getVal().equals(calc.next(current.getVal()))) {
                    // if terminal at a value
                    result = new ResultState(ResultState.Result.VALUE);
                    result.addVal(current.getVal());
                    return result;
                } else {
                    // if terminal in a loop
                    result = new ResultState(ResultState.Result.LOOP);
                    List<PathNode> loop = new ArrayList<>();
                    loop.add(current);
                    current = current.getNext();
                    while (!loop.contains(current)) {
                        loop.add(current);
                        current = current.getNext();
                    }
                    result.addLoop(loop);
                    return result;
                }
            }

            // do we think it's blown up?
            // TODO


        }

    }

    public DeterminedPathNode saveDrive() {
        // TODO
        return null;
    }

}
