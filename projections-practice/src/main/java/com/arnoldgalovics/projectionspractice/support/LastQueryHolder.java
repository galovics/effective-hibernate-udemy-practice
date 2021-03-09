package com.arnoldgalovics.projectionspractice.support;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LastQueryHolder {
    private static String LAST_EXECUTED_SELECT_QUERY;

    public static void setLastExecutedSelectQuery(String query) {
        LAST_EXECUTED_SELECT_QUERY = query;
    }

    public static List<String> lastSelectQuery() {
        if (LAST_EXECUTED_SELECT_QUERY != null) {
            String lowerCaseSelect = LAST_EXECUTED_SELECT_QUERY.toLowerCase();
            String selectFromSection = lowerCaseSelect.substring(lowerCaseSelect.indexOf("select") + 6, lowerCaseSelect.indexOf("from"));
            List<String> selectedColumns = Arrays.stream(selectFromSection.split(","))
                    .map(s -> s.split("as")[0])
                    .map(String::trim)
                    .map(s -> s.split("\\.")[1])
                    .collect(toList());
            return selectedColumns;
        }
        return Collections.emptyList();
    }
}
