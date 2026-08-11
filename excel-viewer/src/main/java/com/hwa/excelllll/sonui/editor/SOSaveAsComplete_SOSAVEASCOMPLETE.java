package com.hwa.excelllll.sonui.editor;

/* JADX INFO: loaded from: classes.dex */
public interface SOSaveAsComplete {
    public static final int SOSaveAsComplete_Cancelled = 2;
    public static final int SOSaveAsComplete_Error = 1;
    public static final int SOSaveAsComplete_Succeeded = 0;

    void onComplete(int i, String str);
}
