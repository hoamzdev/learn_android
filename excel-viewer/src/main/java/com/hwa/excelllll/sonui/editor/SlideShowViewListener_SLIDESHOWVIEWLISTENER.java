package com.hwa.excelllll.sonui.editor;

/* JADX INFO: loaded from: classes.dex */
public interface SlideShowViewListener {
    void slideAnimating(int i);

    void slideAnimationsCompleted(int i);

    void slideAnimationsStarted(int i);

    void slideAnimationsWaiting(int i);

    void slideEnded(int i);

    void slideShowCompleted();

    void slideStarted(int i);
}
