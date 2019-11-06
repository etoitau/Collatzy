package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.PathReport;

public class ReportPrinterHTML implements ReportPrinter {
    private PathReport rept;

    public ReportPrinterHTML(PathReport rept) {
        this.rept = rept;
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder();
        String br = "<br>";
        sb.append("<h2>Collatzy Report</h2>");

        sb.append("<h4>Starting Point:</h4>");
        sb.append(rept.getStartingPoint()).append(br).append(br);

        sb.append("<h4>Result:</h4>");
        sb.append(rept.getResult()).append(br).append(br);

        sb.append("<h4>Length of path explored:</h4>");
        sb.append(rept.getLength()).append(br).append(br);

        sb.append("<h4>Path:</h4><ol>");
        sb.append(pathReport());
        sb.append("</ol>");

        return sb.toString();
    }

    private String pathReport() {
        StringBuilder sb = new StringBuilder();
        for (String val: rept.getStringValueList()) {
            sb.append("<li>").append(val).append("</li>");
        }
        return sb.toString();
    }
}
