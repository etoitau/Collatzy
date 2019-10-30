package com.etoitau.collatzy.service;

import com.etoitau.collatzy.domain.PathReport;
import com.etoitau.collatzy.domain.Report;
import com.etoitau.collatzy.service.ReportPrinter;
import com.google.gson.Gson;

public class ReportPrinterJson implements ReportPrinter {
    private PathReport rept;

    public ReportPrinterJson(PathReport rept) {
        this.rept = rept;
    }

    @Override
    public String print() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static Report jsonToReport(String jReport) {
        Gson gson = new Gson();
        Report report = gson.fromJson(jReport, Report.class);
        return report;
    }
}
