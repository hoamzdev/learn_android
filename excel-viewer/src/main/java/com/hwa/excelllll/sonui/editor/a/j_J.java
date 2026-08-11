package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;
import java.util.ArrayList;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class j extends k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ArrayList<Integer> f4931a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private ArrayList<Integer> f4932b;

    public j(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.f4931a = null;
        this.f4932b = null;
        this.d = 10;
    }

    private void b() {
        Random random = new Random();
        this.f4931a = new ArrayList<>();
        this.f4932b = new ArrayList<>();
        int i = 0;
        while (true) {
            int iNextInt = random.nextInt((this.d == 10 ? this.f : this.g) / 25);
            this.f4931a.add(new Integer(i));
            this.f4932b.add(new Integer(iNextInt));
            i += iNextInt;
            if (this.d == 10) {
                if (i >= this.f) {
                    return;
                }
            } else if (i >= this.g) {
                return;
            }
        }
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        Path.Direction direction = Path.Direction.CW;
        if (this.f4931a == null) {
            b();
        }
        Path path = new Path();
        if (this.c == 1) {
            f = 1.0f - f;
        }
        for (int i = 0; i < this.f4931a.size(); i++) {
            int iIntValue = this.f4931a.get(i).intValue();
            float fIntValue = this.f4932b.get(i).intValue() * f;
            int i2 = this.d;
            if (i2 == 10) {
                float f6 = iIntValue;
                f4 = this.g;
                Path.Direction direction2 = Path.Direction.CW;
                f3 = fIntValue + f6;
                f5 = f6;
                f2 = 0.0f;
            } else {
                if (i2 == 5) {
                    f2 = iIntValue;
                    float f7 = this.f;
                    Path.Direction direction3 = Path.Direction.CW;
                    f3 = f7;
                    f4 = fIntValue + f2;
                    f5 = 0.0f;
                }
            }
            path.addRect(f2, f5, f4, f3, direction);
        }
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}
