package com.example.controller;



import com.example.model.PerformanceModel;
import com.example.view.PerformanceView;
import java.util.List;

public class PerformanceController {
    private PerformanceView view;

    public PerformanceController(PerformanceView view) {
        this.view = view;
    }

    public void handleGenerateIndexes() {
        String sqlQuery = view.getSQLInput();
        List<String> indexes = PerformanceModel.generateIndexes(sqlQuery);

        String result = String.join("\n", indexes);
        view.setIndexesOutput(result);
    }
}
