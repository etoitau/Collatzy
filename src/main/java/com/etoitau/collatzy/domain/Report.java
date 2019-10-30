package com.etoitau.collatzy.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Report implements PathReport {
    private int divisor, multiplier, addend;
    private String startingPoint;
    private String result;
    private int length;
    private List<String> stringValueList;
    private boolean unknown = true;

    public Report(Path path) {
        List<NodeWithResult> nodeList = path.getPath();
        List<BigInteger> valueList = path.getValuePath();

        length = nodeList.size();

        if (nodeList.get(0) == null) return;

        CollatzConfig config = nodeList.get(0).getConfig();
        divisor = config.getD();
        multiplier = config.getM();
        addend = config.getP();

        stringValueList = new ArrayList<>();
        for (BigInteger value: valueList) {
            stringValueList.add(value.toString());
        }

        startingPoint = valueList.get(0).toString();

        if (nodeList.get(0).getResult() != null) {
            result = nodeList.get(0).getResult().toStringVerbose();
            unknown = (nodeList.get(0).getResult().getResult() == ResultState.Result.OPEN);
        }
    }

    @Override
    public boolean isUnknown() { return unknown; }

    @Override
    public String lastNum() {
        return stringValueList.get(stringValueList.size() - 1);
    }

    @Override
    public String getStartingPoint() {
        return startingPoint;
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public List<String> getStringValueList() {
        return stringValueList;
    }
}
