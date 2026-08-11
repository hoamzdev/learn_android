package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class e extends h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Random f4933a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    Path f4934b;

    public e(String str, View view, View view2, int i) {
        super(str, view, view2, i);
        this.f4933a = new Random();
        this.f4934b = new Path();
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        int duration = (int) (f * getDuration());
        for (int i = 0; i < 40; i++) {
            for (int i2 = 0; i2 < 40; i2++) {
                if (this.f4933a.nextInt((int) getDuration()) <= duration) {
                    int i3 = this.g;
                    float f2 = (i3 * i2) / 40;
                    float f3 = ((i2 + 1) * i3) / 40;
                    int i4 = this.f;
                    this.f4934b.addRect(f2, (i4 * i) / 40, f3, ((i + 1) * i4) / 40, Path.Direction.CW);
                }
            }
        }
        this.d.setClipPath(this.f4934b);
        this.d.invalidate();
    }
}
