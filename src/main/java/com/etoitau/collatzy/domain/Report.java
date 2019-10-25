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
    private boolean unknown = true;

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
            unknown = (nodeList.get(0).getResult().getResult() == ResultState.Result.OPEN);
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
    public String htmlReport() {
        StringBuilder sb = new StringBuilder();
        String br = "<br>";
        sb.append("<h2>Collatzy Report</h2>");

        sb.append("<h4>Starting Point:</h4>");
        sb.append(startingPoint).append(br).append(br);

        sb.append("<h4>Result:</h4>");
        sb.append(result).append(br).append(br);

        sb.append("<h4>Length of path explored:</h4>");
        sb.append(length).append(br).append(br);

        sb.append("<h4>Path:</h4><ol>");
        sb.append(pathReport());
        sb.append("</ol>");

        return sb.toString();
    }

    public String pathReport() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringValueList.size(); i++) {
            sb.append("<li>").append(stringValueList.get(i)).append("</li>");
        }
        return sb.toString();
    }

    @Override
    public boolean isUnknown() { return unknown; }

    @Override
    public String lastNum() {
        return stringValueList.get(stringValueList.size() - 1);
    }
}
