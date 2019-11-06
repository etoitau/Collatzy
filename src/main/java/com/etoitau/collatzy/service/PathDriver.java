package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.*;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Object for forging a NodePath and saving results
 * Typical use would be to set up with a configuration to follow, and a NumberMap to which results are saved
 * Then start a drive from a given point and call next to go to next point in path.
 */
public class PathDriver {
    private CollatzConfig config;
    private CollatzCalculator calc;
    private NumberMap map;
    private Set<NodeWithResult> traveled;
    private NodeWithResult start, current;
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

    /**
     * Set or reset driver to start exploring a path from the given start
     * @param startNum - starting point
     * @return - current node (starting point)
     */
    public NodeWithResult startNewDrive(BigInteger startNum) {
        // check if already known
        if (map.contains(startNum)) {
            NodeWithResult found = map.get(startNum);
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

    /**
     * @return - true if remainder of path is already explored
     */
    public boolean hasResult() {
        return (result != null && result.getResult() != null &&result.getResult() == ResultState.Result.LOOP);
    }

    /**
     * Get next node in path and save new info to NumberMap
     * @return - the next node
     */
    public NodeWithResult next() {
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

    /**
     * update result value for all nodes in path
     */
    private void saveDrive() {
        List<NodeWithResult> loop = result.getLoopNodes();
        NodeWithResult cursor = start;
        cursor.setResult(result);
        while (!loop.contains(cursor)) {
            map.add(cursor);
            cursor = cursor.getNext();
            cursor.setResult(result);
        }
        for (NodeWithResult node : loop) {
            node.setResult(result);
            map.add(node);
        }
    }
}
