package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPoint;
import defpackage.e6;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class DocPdfPageView extends DocPageView {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private DisplayMetrics f4560b;
    private ArrayList<InkAnnotation> c;

    public class InkAnnotation {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private float f4562b;
        private int c;
        private ArrayList<PointF> d = new ArrayList<>();

        public InkAnnotation(int i, float f) {
            this.c = i;
            this.f4562b = f;
        }

        public void add(PointF pointF) {
            this.d.add(new SOPoint(pointF, this.d.size() == 0 ? 0 : 1));
        }

        public void draw(Canvas canvas) {
            Path path = new Path();
            PointF pointF = new PointF();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(this.f4562b * ((float) DocPdfPageView.this.getFactor()));
            paint.setColor(this.c);
            int size = this.d.size();
            ArrayList<PointF> arrayList = this.d;
            if (size < 2) {
                DocPdfPageView.this.pageToView(arrayList.get(0), pointF);
                canvas.drawCircle(pointF.x, pointF.y, (this.f4562b * DocPdfPageView.this.mScale) / 2.0f, paint);
                return;
            }
            Iterator<PointF> it = arrayList.iterator();
            DocPdfPageView.this.pageToView(it.next(), pointF);
            float f = pointF.x;
            float f2 = pointF.y;
            path.moveTo(f, f2);
            while (it.hasNext()) {
                DocPdfPageView.this.pageToView(it.next(), pointF);
                float f3 = pointF.x;
                float f4 = pointF.y;
                path.quadTo(f, f2, (f3 + f) / 2.0f, (f4 + f2) / 2.0f);
                f = f3;
                f2 = f4;
            }
            path.lineTo(f, f2);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        }

        public int getLineColor() {
            return this.c;
        }

        public float getLineThickness() {
            return this.f4562b;
        }

        public Rect getRect() {
            ArrayList<PointF> arrayList = this.d;
            int size = arrayList.size();
            int i = 0;
            Rect rect = null;
            int i2 = 0;
            while (i2 < size) {
                PointF pointF = arrayList.get(i2);
                i2++;
                PointF pointF2 = pointF;
                i++;
                if (i == 1) {
                    float f = pointF2.x;
                    float f2 = pointF2.y;
                    rect = new Rect((int) f, (int) f2, (int) f, (int) f2);
                } else {
                    rect.union((int) pointF2.x, (int) pointF2.y);
                }
            }
            return rect;
        }

        public SOPoint[] points() {
            return (SOPoint[]) this.d.toArray(new SOPoint[0]);
        }

        public void setLineColor(int i) {
            this.c = i;
        }

        public void setLineThickness(float f) {
            this.f4562b = f;
        }
    }

    public DocPdfPageView(Context context, SODoc sODoc) {
        super(context, sODoc);
        this.f4560b = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRealMetrics(this.f4560b);
    }

    private void a(Canvas canvas) {
        if (this.c != null) {
            int minInkSize = getMinInkSize();
            ArrayList<InkAnnotation> arrayList = this.c;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                InkAnnotation inkAnnotation = arrayList.get(i);
                i++;
                InkAnnotation inkAnnotation2 = inkAnnotation;
                Rect rect = inkAnnotation2.getRect();
                if (rect.width() >= minInkSize || rect.height() >= minInkSize) {
                    inkAnnotation2.draw(canvas);
                }
            }
        }
    }

    private int getMinInkSize() {
        return viewToPage((int) (this.f4560b.densityDpi * 0.1f));
    }

    public void clearInk() {
        if (this.c != null) {
            invalidate();
            this.c.clear();
        }
    }

    public void continueDrawInk(float f, float f2) {
        ArrayList<InkAnnotation> arrayList = this.c;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        ((InkAnnotation) e6.g(1, this.c)).add(screenToPage(new PointF(f, f2)));
        invalidate();
    }

    public void createNote(float f, float f2) {
        getDoc().createTextAnnotationAt(screenToPage(new PointF(f, f2)), getPageNumber());
        invalidate();
    }

    public void endDrawInk() {
        ArrayList<InkAnnotation> arrayList = this.c;
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        InkAnnotation inkAnnotation = (InkAnnotation) e6.g(1, this.c);
        int minInkSize = getMinInkSize();
        Rect rect = inkAnnotation.getRect();
        if (rect.width() >= minInkSize || rect.height() >= minInkSize) {
            return;
        }
        ArrayList<InkAnnotation> arrayList2 = this.c;
        arrayList2.remove(arrayList2.size() - 1);
        invalidate();
    }

    @Override // com.artifex.sonui.editor.DocPageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isValid() && this.mPage != null) {
            a(canvas);
            drawSelection(canvas);
            drawSearchHighlight(canvas);
        }
    }

    public void saveInk() {
        ArrayList<InkAnnotation> arrayList = this.c;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                InkAnnotation inkAnnotation = arrayList.get(i);
                i++;
                InkAnnotation inkAnnotation2 = inkAnnotation;
                getDoc().createInkAnnotation(getPageNumber(), inkAnnotation2.points(), inkAnnotation2.getLineThickness(), inkAnnotation2.getLineColor());
            }
        }
        ArrayList<InkAnnotation> arrayList2 = this.c;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
        invalidate();
    }

    public void setInkLineColor(int i) {
        ArrayList<InkAnnotation> arrayList = this.c;
        if (arrayList != null) {
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                InkAnnotation inkAnnotation = arrayList.get(i2);
                i2++;
                inkAnnotation.setLineColor(i);
            }
            invalidate();
        }
    }

    public void setInkLineThickness(float f) {
        ArrayList<InkAnnotation> arrayList = this.c;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                InkAnnotation inkAnnotation = arrayList.get(i);
                i++;
                inkAnnotation.setLineThickness(f);
            }
            invalidate();
        }
    }

    public void startDrawInk(float f, float f2, int i, float f3) {
        if (this.c == null) {
            this.c = new ArrayList<>();
        }
        InkAnnotation inkAnnotation = new InkAnnotation(i, f3);
        this.c.add(inkAnnotation);
        inkAnnotation.add(screenToPage(new PointF(f, f2)));
        invalidate();
    }

    public void drawSearchHighlight(Canvas canvas) {
    }

    public void drawSelection(Canvas canvas) {
    }
}
