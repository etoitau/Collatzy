package com.etoitau.collatzy.domain;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Report implements PathReport {
    private int divisor, multiplier, addend;
    private String startingPoint;
    private String result;
    private String stringPath;
    private transient int length;
    private transient List<String> stringValueList;

    public Report(Path path) {
        List<DeterminedPathNode> nodeList = path.getPath();
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
        stringPath = stringValueList.toString();

        startingPoint = valueList.get(0).toString();

        if (nodeList.get(0).getResult() != null) {
            result = nodeList.get(0).getResult().toStringVerbose();
        }
    }

    public static Report jsonToReport(String json) {
        Gson gson = new Gson();
        Report report = gson.fromJson(json, Report.class);
        return report;
    }


    @Override
    public String jsonReport() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String verboseReport() {
        StringBuilder sb = new StringBuilder();
        String br = "<br>";
        sb.append("Collatzy Report").append(br);

        sb.append("Algorithm Used:").append(br);
        sb.append(String.format("Divide by %d if number evenly divides by %d.", divisor, divisor)).append(br);
        sb.append(String.format("Otherwise multiply by %d and add %d.", multiplier, addend)).append(br);

        sb.append("Starting Point:").append(br);
        sb.append(startingPoint).append(br);

        sb.append("Result:").append(br);
        sb.append(result).append(br);

        sb.append("Length of path explored:").append(br);
        sb.append(length).append(br);

        sb.append("Path").append(br);
        sb.append("Step, Number").append(br);
        for (int i = 0; i < stringValueList.size(); i++) {
            sb.append(i).append(", ").append(stringValueList.get(i)).append(br);
        }
        return sb.toString();
    }
}
