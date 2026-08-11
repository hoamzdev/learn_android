package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Intent;
import com.artifex.solib.SODoc;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public interface SODataLeakHandlers {
    void customSaveHandler(String str, SODoc sODoc, String str2, SOCustomSaveComplete sOCustomSaveComplete) throws UnsupportedOperationException, IOException;

    void doInsert();

    void finaliseDataLeakHandlers();

    void initDataLeakHandlers(Activity activity) throws IOException;

    void insertImageHandler(NUIDocView nUIDocView) throws UnsupportedOperationException;

    void insertPhotoHandler(NUIDocView nUIDocView) throws UnsupportedOperationException;

    void launchUrlHandler(String str) throws UnsupportedOperationException;

    void onActivityResult(int i, int i2, Intent intent);

    void openInHandler(String str, SODoc sODoc) throws UnsupportedOperationException;

    void openPdfInHandler(String str, SODoc sODoc) throws UnsupportedOperationException;

    void pauseHandler(SODoc sODoc, boolean z);

    void postSaveHandler(SOSaveAsComplete sOSaveAsComplete);

    void printHandler(SODoc sODoc) throws UnsupportedOperationException;

    void saveAsHandler(String str, SODoc sODoc, SOSaveAsComplete sOSaveAsComplete) throws UnsupportedOperationException;

    void saveAsPdfHandler(String str, SODoc sODoc) throws UnsupportedOperationException;

    void shareHandler(String str, SODoc sODoc) throws UnsupportedOperationException;
}
