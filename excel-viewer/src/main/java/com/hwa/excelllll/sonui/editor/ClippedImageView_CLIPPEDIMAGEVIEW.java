package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class ClippedImageView extends ImageView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Path f4523a;

    public ClippedImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4523a = null;
        setWillNotDraw(false);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.f4523a != null) {
            canvas.save();
        }
        Path path = this.f4523a;
        if (path != null) {
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
        if (this.f4523a != null) {
            canvas.restore();
        }
    }

    public void setClipPath(Path path) {
        this.f4523a = path;
    }

    public ClippedImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClippedImageView(Context context) {
        this(context, null, 0);
    }
}
