package com.hwa.excelllll.sonui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.a.o.Res;
import com.artifex.solib.m;
import com.artifex.sonui.editor.SOTextView;
import com.artifex.sonui.editor.Utilities;
import com.onesignal.core.internal.database.impl.OneSignalDbContract;
import defpackage.e6;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class j extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final k[] f4987a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final Context f4988b;
    private int c;

    public j(Context context, k[] kVarArr, int i) {
        this.f4987a = kVarArr;
        this.f4988b = context;
        this.c = i;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.f4987a.length;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.f4987a[i];
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0090 A[Catch: IOException -> 0x003c, TRY_ENTER, TRY_LEAVE, TryCatch #0 {IOException -> 0x003c, blocks: (B:8:0x002c, B:10:0x0034, B:16:0x005d, B:17:0x0061, B:20:0x0090, B:22:0x00a4, B:24:0x00b8, B:26:0x00c3, B:25:0x00bd, B:13:0x003f, B:15:0x0051), top: B:33:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:25:0x00bd A[Catch: IOException -> 0x003c, TryCatch #0 {IOException -> 0x003c, blocks: (B:8:0x002c, B:10:0x0034, B:16:0x005d, B:17:0x0061, B:20:0x0090, B:22:0x00a4, B:24:0x00b8, B:26:0x00c3, B:25:0x00bd, B:13:0x003f, B:15:0x0051), top: B:33:0x002c }] */
    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Bitmap bitmapDecodeStream;
        InputStream inputStreamOpen;
        double dZ;
        Bitmap bitmapCopy;
        k kVar = this.f4987a[i];
        View viewInflate = view == null ? LayoutInflater.from(this.f4988b).inflate(Res.i("sodk_editor_vis_explorer_entry"), (ViewGroup) null) : view;
        ImageView imageView = (ImageView) viewInflate.findViewById(Res.g("image"));
        if (kVar.e == null) {
            try {
                if (com.artifex.solib.a.b(kVar.f4990b)) {
                    inputStreamOpen = new m(kVar.f4990b);
                } else {
                    bitmapDecodeStream = BitmapFactory.decodeStream(this.f4988b.getAssets().open(kVar.f4990b));
                    if (bitmapDecodeStream == null) {
                        inputStreamOpen = this.f4988b.getAssets().open("no-thumb.png");
                    }
                    double dimensionPixelSize = this.f4988b.getResources().getDimensionPixelSize(Res.e("sodk_editor_explorer_grid_image_height"));
                    double height = bitmapDecodeStream.getHeight();
                    Double.isNaN(dimensionPixelSize);
                    Double.isNaN(height);
                    Double.isNaN(dimensionPixelSize);
                    Double.isNaN(height);
                    dZ = e6.z(dimensionPixelSize, height, dimensionPixelSize, height);
                    if (dZ < 1.0d) {
                        double height2 = bitmapDecodeStream.getHeight();
                        Double.isNaN(height2);
                        int iA = (int) e6.a(height2, height2, height2, dZ);
                        double width = bitmapDecodeStream.getWidth();
                        Double.isNaN(width);
                        bitmapCopy = Bitmap.createScaledBitmap(bitmapDecodeStream, (int) e6.a(width, width, dZ, width), iA, true);
                    } else {
                        bitmapCopy = bitmapDecodeStream.copy(Bitmap.Config.ARGB_8888, true);
                    }
                    kVar.e = bitmapCopy;
                    Drawable drawable = this.f4988b.getDrawable(Utilities.iconForDocType(kVar.f4989a));
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                    Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, (bitmapCreateBitmap.getWidth() * 3) / 4, (bitmapCreateBitmap.getHeight() * 3) / 4, true);
                    Canvas canvas2 = new Canvas(kVar.e);
                    Paint paint = new Paint();
                    paint.setColor(-7829368);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(1.0f);
                    canvas2.drawRect(1.0f, 1.0f, kVar.e.getWidth() - 1, kVar.e.getHeight() - 1, paint);
                    canvas2.drawBitmap(bitmapCreateScaledBitmap, kVar.e.getWidth() - bitmapCreateScaledBitmap.getWidth(), 0.0f, (Paint) null);
                }
                bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                double dimensionPixelSize2 = this.f4988b.getResources().getDimensionPixelSize(Res.e("sodk_editor_explorer_grid_image_height"));
                double height3 = bitmapDecodeStream.getHeight();
                Double.isNaN(dimensionPixelSize2);
                Double.isNaN(height3);
                Double.isNaN(dimensionPixelSize2);
                Double.isNaN(height3);
                dZ = e6.z(dimensionPixelSize2, height3, dimensionPixelSize2, height3);
                if (dZ < 1.0d) {
                    double height4 = bitmapDecodeStream.getHeight();
                    Double.isNaN(height4);
                    int iA2 = (int) e6.a(height4, height4, height4, dZ);
                    double width2 = bitmapDecodeStream.getWidth();
                    Double.isNaN(width2);
                    bitmapCopy = Bitmap.createScaledBitmap(bitmapDecodeStream, (int) e6.a(width2, width2, dZ, width2), iA2, true);
                } else {
                    bitmapCopy = bitmapDecodeStream.copy(Bitmap.Config.ARGB_8888, true);
                }
                kVar.e = bitmapCopy;
                Drawable drawable2 = this.f4988b.getDrawable(Utilities.iconForDocType(kVar.f4989a));
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas3 = new Canvas(bitmapCreateBitmap2);
                drawable2.setBounds(0, 0, canvas3.getWidth(), canvas3.getHeight());
                drawable2.draw(canvas3);
                Bitmap bitmapCreateScaledBitmap2 = Bitmap.createScaledBitmap(bitmapCreateBitmap2, (bitmapCreateBitmap2.getWidth() * 3) / 4, (bitmapCreateBitmap2.getHeight() * 3) / 4, true);
                Canvas canvas4 = new Canvas(kVar.e);
                Paint paint2 = new Paint();
                paint2.setColor(-7829368);
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setStrokeWidth(1.0f);
                canvas4.drawRect(1.0f, 1.0f, kVar.e.getWidth() - 1, kVar.e.getHeight() - 1, paint2);
                canvas4.drawBitmap(bitmapCreateScaledBitmap2, kVar.e.getWidth() - bitmapCreateScaledBitmap2.getWidth(), 0.0f, (Paint) null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(kVar.e);
        SOTextView sOTextView = (SOTextView) viewInflate.findViewById(Res.g(OneSignalDbContract.NotificationTable.COLUMN_NAME_TITLE));
        if (sOTextView != null) {
            sOTextView.setText(kVar.c);
            sOTextView.setTextColor(this.c);
        }
        return viewInflate;
    }
}
