package com.etoitau.collatzy.domain;

import com.etoitau.collatzy.service.PathNodeBuilder;

import java.math.BigInteger;
import java.util.*;

/**
 * Object for collecting a set of nodes for a given configuration
 */
public class NumberMap {
    private final CollatzConfig config;
    private Map<BigInteger, NodeWithResult> nodes;

    public NumberMap(CollatzConfig config) {
        this.config = config;
        nodes = new HashMap<>();
    }

    public boolean contains(NodeWithResult pn) {
        return nodes.containsValue(pn);
    }

    public boolean contains(BigInteger val) {
        return nodes.containsKey(val);
    }

    /**
     * Add a node to the map. If node already exists, existing node will be updated with any new
     *  information contained in the new node
     * @param toAdd - the node being added
     * @return - returns the previous value or null if there wasn't one
     */
    public NodeWithResult add(NodeWithResult toAdd) {
        if (contains(toAdd)) {
            // if already have entry, update toAdd to include all available info
            NodeWithResult prev = nodes.get(toAdd.getValue());
            PathNodeBuilder.combineNodesWithResult(toAdd, prev);
        }
        return nodes.put(toAdd.getValue(), toAdd);
    }

    public void addAll(Collection<NodeWithResult> nodesToAdd) {
        for (NodeWithResult node: nodesToAdd) {
            add(node);
        }
    }

    public NodeWithResult get(BigInteger val) {
        return nodes.get(val);
    }

    public CollatzConfig getConfig() { return config; }

    public Collection<NodeWithResult> getNodes() {
        return nodes.values();
    }

    /**
     * serializing information about nodes in map.
     * Format is "...;12,6,LOOP 1 4 2;..."
     */
    public String nodesToString() {
        StringBuilder sb = new StringBuilder();
        for (NodeWithResult node: nodes.values()) {
            sb.append(node.getValue().toString()).append(",");
            sb.append( (node.getNext() != null)? node.getNext().getValue().toString(): "null").append(",");
            sb.append(node.getResult().toString()).append(";");
        }
        // remove last semicolon
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * deserializing NumberMap from string produced by nodesToString
     */
    public static NumberMap mapFromNodeString(CollatzConfig config, String serial) {
        NumberMap newMap = new NumberMap(config);
        // if no info to deserialize, return empty map
        if (serial == null || serial.isEmpty()) { return newMap; }
        // get all info from string
        // split on ; to get each node
        String[] stringNodes = serial.split(";");
        BigInteger[] vals = new BigInteger[stringNodes.length];
        BigInteger[] nexts = new BigInteger[stringNodes.length];
        String[] results = new String[stringNodes.length];

        // go through nodes and split info into node value, next node, and result
        for (int i = 0; i < stringNodes.length; i++) {
            String[] info = stringNodes[i].split(",", 3);
            vals[i] = new BigInteger(info[0]);
            nexts[i] = (info[1].equals("null"))? null: new BigInteger(info[1]);
            results[i] = info[2];
        }

        // Recreate nodes in NumberMap with three passes...
        for (BigInteger bi: vals) {
            // 1. add all nodes
            newMap.add(new PathNode(bi, config));
        }
        for (int i = 0; i < vals.length; i++) {
            // 2. set next for each
            newMap.get(vals[i]).setNext(newMap.get(nexts[i]));
        }
        for (int i = 0; i < results.length; i++) {
            // 3. set result for each
            // split result info. First part is LOOP or OPEN, rest are nodes of the loop if exists
            String[] resultParts = results[i].split(" ");

            // node default is OPEN, so if this is open, we're done
            if (resultParts[0].equals("LOOP")) {
                // if this is LOOP, update result type and add loop info
                ResultState result = new ResultState(ResultState.Result.LOOP);
                List<NodeWithResult> loopNodes = new ArrayList<>();
                for (int j = 1; j < resultParts.length; j++) {
                    loopNodes.add(newMap.get(new BigInteger(resultParts[j])));
                }
                result.addLoop(loopNodes);
                newMap.get(vals[i]).setResult(result);
            }
        }
        return newMap;
    }
}
