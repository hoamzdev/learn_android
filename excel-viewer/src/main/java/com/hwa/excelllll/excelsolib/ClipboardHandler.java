package com.hwa.excelllll.excelsolib;

import android.app.Activity;
public interface ClipboardHandler {
    String getText();

    void initialize(Activity activity);

    void setText(String text);

    boolean hasText();
}
