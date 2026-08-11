package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class g extends h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ArrayList<Integer> f4935a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private ArrayList<Integer> f4936b;

    public g(String str, View view, View view2, int i) {
        super(str, view, view2, i);
        Random random = new Random();
        this.f4935a = new ArrayList<>();
        this.f4936b = new ArrayList<>();
        int i2 = 0;
        while (true) {
            int iNextInt = random.nextInt((this.e.equals("horz") ? this.f : this.g) / 25);
            this.f4935a.add(new Integer(i2));
            this.f4936b.add(new Integer(iNextInt));
            i2 += iNextInt;
            if (this.e.equals("horz")) {
                if (i2 >= this.f) {
                    return;
                }
            } else if (i2 >= this.g) {
                return;
            }
        }
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        Path.Direction direction = Path.Direction.CW;
        Path path = new Path();
        for (int i = 0; i < this.f4935a.size(); i++) {
            int iIntValue = this.f4935a.get(i).intValue();
            float fIntValue = this.f4936b.get(i).intValue() * f;
            if (this.e.equals("horz")) {
                float f6 = iIntValue;
                f4 = this.g;
                Path.Direction direction2 = Path.Direction.CW;
                f3 = fIntValue + f6;
                f5 = f6;
                f2 = 0.0f;
            } else {
                if (this.e.equals("vert")) {
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
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}
