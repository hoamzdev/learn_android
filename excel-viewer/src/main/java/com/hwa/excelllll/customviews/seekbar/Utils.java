package com.hwa.excelllll.customviews.seekbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class Utils {
    /* JADX INFO: renamed from: a */
    public static Bitmap m21866a(Context context, int i, int i2, int i3) {
        Exception exc;
        Bitmap bitmapCreateBitmap = null;
        if (context != null && i > 0 && i2 > 0 && i3 != 0) {
            Drawable drawable = context.getResources().getDrawable(i3, null);
            try {
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    if (bitmap != null) {
                        try {
                            if (bitmap.getHeight() > 0) {
                                Matrix matrix = new Matrix();
                                matrix.postScale((i * 1.0f) / bitmap.getWidth(), (i2 * 1.0f) / bitmap.getHeight());
                                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                            }
                        } catch (Exception e) {
                            exc = e;
                            bitmapCreateBitmap = bitmap;
                            exc.printStackTrace();
                            return bitmapCreateBitmap;
                        }
                    }
                }
                bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmapCreateBitmap;
            } catch (Exception e2) {
                exc = e2;
            }
        }
        return bitmapCreateBitmap;
    }
}
