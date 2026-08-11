package com.hwa.excelllll.excelsoui;

import android.graphics.Path;
import android.view.animation.Animation;
public interface AnimatableView {
    Path getClipPath();

    int getMeasuredHeight();

    int getMeasuredWidth();

    void invalidate();

    void postInvalidate();

    void setClipPath(Path path);

    void startAnimation(Animation animation);
}
