package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.SparseIntArray;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOPageListener;
import com.artifex.solib.SORender;
import com.artifex.solib.o;
import defpackage.d8;
import defpackage.e6;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class PrintHelper {
    public void print(final Context context, final SODoc sODoc) {
        ((PrintManager) context.getSystemService("print")).print("Print Job Name", new PrintDocumentAdapter() { // from class: com.artifex.sonui.editor.PrintHelper.1
            private int d;
            private int e;
            private PrintAttributes f;
            private PrintDocumentInfo g;
            private PrintedPdfDocument h = null;
            private boolean i;

            /* JADX INFO: Access modifiers changed from: private */
            public PageRange[] a(SparseIntArray sparseIntArray) {
                ArrayList arrayList = new ArrayList();
                int size = sparseIntArray.size();
                int i = 0;
                while (i < size) {
                    int iValueAt = sparseIntArray.valueAt(i);
                    int i2 = iValueAt;
                    int i3 = i2;
                    while (i < size && i2 - i3 <= 1) {
                        int iValueAt2 = sparseIntArray.valueAt(i);
                        i++;
                        i3 = i2;
                        i2 = iValueAt2;
                    }
                    arrayList.add(new PageRange(iValueAt, i2));
                    i++;
                }
                PageRange[] pageRangeArr = new PageRange[arrayList.size()];
                arrayList.toArray(pageRangeArr);
                return pageRangeArr;
            }

            @Override // android.print.PrintDocumentAdapter
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                    return;
                }
                this.i = bundle.getBoolean("EXTRA_PRINT_PREVIEW");
                float fMax = Math.max(printAttributes2.getResolution().getHorizontalDpi(), printAttributes2.getResolution().getVerticalDpi());
                int widthMils = (((int) ((printAttributes2.getMediaSize().getWidthMils() * fMax) / 1000.0f)) - ((int) ((printAttributes2.getMinMargins().getLeftMils() * fMax) / 1000.0f))) - ((int) ((printAttributes2.getMinMargins().getRightMils() * fMax) / 1000.0f));
                if (this.d != widthMils) {
                    this.d = widthMils;
                }
                int heightMils = (((int) ((fMax * printAttributes2.getMediaSize().getHeightMils()) / 1000.0f)) - ((int) ((printAttributes2.getMinMargins().getTopMils() * fMax) / 1000.0f))) - ((int) ((printAttributes2.getMinMargins().getBottomMils() * fMax) / 1000.0f));
                if (this.e != heightMils) {
                    this.e = heightMils;
                }
                this.f = printAttributes2;
                try {
                    PrintDocumentInfo printDocumentInfoBuild = new PrintDocumentInfo.Builder("PrintJob.pdf").setContentType(0).setPageCount(sODoc.r()).build();
                    this.g = printDocumentInfoBuild;
                    layoutResultCallback.onLayoutFinished(printDocumentInfoBuild, true);
                } catch (Exception e) {
                    layoutResultCallback.onLayoutFailed(null);
                    d8.i(e);
                }
            }

            @Override // android.print.PrintDocumentAdapter
            public void onWrite(final PageRange[] pageRangeArr, final ParcelFileDescriptor parcelFileDescriptor, final CancellationSignal cancellationSignal, final WriteResultCallback writeResultCallback) {
                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                } else {
                    new AsyncTask<Void, Void, Void>() { // from class: com.artifex.sonui.editor.PrintHelper.1.1
                        private final SparseIntArray f = new SparseIntArray();

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            anonymousClass1.h = new PrintedPdfDocument(context, AnonymousClass1.this.f);
                            float fMin = Math.min(AnonymousClass1.this.h.getPageContentRect().width() / AnonymousClass1.this.d, AnonymousClass1.this.h.getPageContentRect().height() / AnonymousClass1.this.e);
                            int iR = sODoc.r();
                            for (int i = 0; i < iR; i++) {
                                if (isCancelled()) {
                                    return null;
                                }
                                if (a(pageRangeArr, i)) {
                                    SparseIntArray sparseIntArray = this.f;
                                    sparseIntArray.append(sparseIntArray.size(), i);
                                    PdfDocument.Page pageStartPage = AnonymousClass1.this.h.startPage(i);
                                    a(i, fMin, pageStartPage);
                                    AnonymousClass1.this.h.finishPage(pageStartPage);
                                }
                            }
                            try {
                                try {
                                    AnonymousClass1.this.h.writeTo(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                                    writeResultCallback.onWriteFinished(a(this.f));
                                } catch (IOException unused) {
                                    writeResultCallback.onWriteFailed(null);
                                }
                                return null;
                            } finally {
                                AnonymousClass1.this.h.close();
                            }
                        }

                        @Override // android.os.AsyncTask
                        public void onPreExecute() {
                            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.artifex.sonui.editor.PrintHelper.1.1.1
                                @Override // android.os.CancellationSignal.OnCancelListener
                                public void onCancel() {
                                    cancel(true);
                                }
                            });
                        }

                        private void a(int i, float f, PdfDocument.Page page) {
                            Canvas canvas = page.getCanvas();
                            SOPage page2 = sODoc.getPage(i, new SOPageListener() { // from class: com.artifex.sonui.editor.PrintHelper.1.1.2
                                @Override // com.artifex.solib.SOPageListener
                                public void update(RectF rectF) {
                                }
                            });
                            PointF pointFZoomToFitRect = page2.zoomToFitRect(AnonymousClass1.this.d, AnonymousClass1.this.e);
                            double dMax = Math.max(pointFZoomToFitRect.x, pointFZoomToFitRect.y);
                            float f2 = AnonymousClass1.this.i ? 5.0f : 1.0f;
                            double d = f2;
                            Double.isNaN(dMax);
                            Double.isNaN(d);
                            Double.isNaN(dMax);
                            Double.isNaN(d);
                            double dZ = e6.z(dMax, d, dMax, d);
                            Point pointSizeAtZoom = page2.sizeAtZoom(dZ);
                            Rect rect = new Rect(0, 0, pointSizeAtZoom.x, pointSizeAtZoom.y);
                            float f3 = f2 * f;
                            rect.right = (int) (rect.right * f3);
                            rect.bottom = (int) (rect.bottom * f3);
                            SOBitmap sOBitmap = new SOBitmap(pointSizeAtZoom.x, pointSizeAtZoom.y);
                            PointF pointF = new PointF(0.0f, 0.0f);
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            SORender sORenderA = page2.a(dZ, pointF, sOBitmap, new o() { // from class: com.artifex.sonui.editor.PrintHelper.1.1.3
                                @Override // com.artifex.solib.o
                                public void a(int i2) {
                                    countDownLatch.countDown();
                                }
                            }, false);
                            try {
                                countDownLatch.await();
                            } catch (InterruptedException unused) {
                            }
                            canvas.drawBitmap(sOBitmap.a(), (Rect) null, rect, (Paint) null);
                            sORenderA.destroy();
                            page2.m();
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onCancelled(Void r1) {
                            writeResultCallback.onWriteCancelled();
                            AnonymousClass1.this.h.close();
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                }
            }

            @Override // android.print.PrintDocumentAdapter
            public void onFinish() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean a(PageRange[] pageRangeArr, int i) {
                int length = pageRangeArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    if (pageRangeArr[i2].getStart() <= i && pageRangeArr[i2].getEnd() >= i) {
                        return true;
                    }
                }
                return false;
            }
        }, null);
    }
}
