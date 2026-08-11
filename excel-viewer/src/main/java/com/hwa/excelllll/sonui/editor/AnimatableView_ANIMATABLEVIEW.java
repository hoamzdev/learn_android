package com.hwa.excelllll.sonui.editor;

import android.graphics.Path;
import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
public interface AnimatableView {
    Path getClipPath();

    int getMeasuredHeight();

    int getMeasuredWidth();

    void invalidate();

    void postInvalidate();

    void setClipPath(Path path);

    void startAnimation(Animation animation);
}
