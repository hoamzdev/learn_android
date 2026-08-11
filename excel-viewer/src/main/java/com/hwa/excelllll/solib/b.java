package com.hwa.excelllll.solib;

import android.graphics.Bitmap;
import android.graphics.Rect;

/* JADX INFO: loaded from: classes.dex */
public class b extends SOBitmap {
    public b(int i, int i2) {
        int i3 = SOBitmap.f4164a + 1;
        SOBitmap.f4164a = i3;
        this.f4165b = i3;
        this.bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.rect = new Rect(0, 0, i, i2);
    }
}
