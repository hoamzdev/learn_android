package com.hwa.excelllll.excelsoui;

/** Minimal data-leak handler surface required by the Excel viewer. */
public interface SODataLeakHandlers {
    void launchUrlHandler(String url) throws UnsupportedOperationException;
}
