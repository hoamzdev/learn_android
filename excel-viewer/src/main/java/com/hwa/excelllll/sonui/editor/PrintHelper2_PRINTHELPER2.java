package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOPageListener;
import com.artifex.solib.SORender;
import com.artifex.solib.o;
import defpackage.e6;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class PrintHelper2 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    PrintAttributes f4732a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f4733b;
    private int c;

    private void a(SODoc sODoc, int i, float f, PdfDocument.Page page) {
        Canvas canvas = page.getCanvas();
        float f2 = f * 1.0f;
        canvas.scale(f2, f2);
        SOPage page2 = sODoc.getPage(i, new SOPageListener() { // from class: com.artifex.sonui.editor.PrintHelper2.2
            @Override // com.artifex.solib.SOPageListener
            public void update(RectF rectF) {
            }
        });
        PointF pointFZoomToFitRect = page2.zoomToFitRect(this.f4733b, this.c);
        double dMax = Math.max(pointFZoomToFitRect.x, pointFZoomToFitRect.y);
        Point pointSizeAtZoom = page2.sizeAtZoom(dMax);
        Rect rect = new Rect(0, 0, pointSizeAtZoom.x, pointSizeAtZoom.y);
        SOBitmap sOBitmap = new SOBitmap(pointSizeAtZoom.x, pointSizeAtZoom.y);
        PointF pointF = new PointF(0.0f, 0.0f);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Double.isNaN(dMax);
        Double.isNaN(1.0d);
        Double.isNaN(dMax);
        Double.isNaN(1.0d);
        SORender sORenderA = page2.a(e6.z(dMax, 1.0d, dMax, 1.0d), pointF, sOBitmap, new o() { // from class: com.artifex.sonui.editor.PrintHelper2.3
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

    public void print(final Context context, final SODoc sODoc) {
        final String str = context.getExternalCacheDir() + "/print/" + UUID.randomUUID() + ".pdf";
        PrintDocumentAdapter printDocumentAdapter = new PrintDocumentAdapter() { // from class: com.artifex.sonui.editor.PrintHelper2.1
            @Override // android.print.PrintDocumentAdapter
            public void onFinish() {
                try {
                    new File(str).delete();
                } catch (NullPointerException unused) {
                }
            }

            @Override // android.print.PrintDocumentAdapter
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                    return;
                }
                PrintHelper2.this.f4732a = printAttributes2;
                float fMax = Math.max(printAttributes2.getResolution().getHorizontalDpi(), printAttributes2.getResolution().getVerticalDpi());
                int widthMils = (((int) ((printAttributes2.getMediaSize().getWidthMils() * fMax) / 1000.0f)) - ((int) ((printAttributes2.getMinMargins().getLeftMils() * fMax) / 1000.0f))) - ((int) ((printAttributes2.getMinMargins().getRightMils() * fMax) / 1000.0f));
                if (PrintHelper2.this.f4733b != widthMils) {
                    PrintHelper2.this.f4733b = widthMils;
                }
                int heightMils = (((int) ((fMax * printAttributes2.getMediaSize().getHeightMils()) / 1000.0f)) - ((int) ((printAttributes2.getMinMargins().getTopMils() * fMax) / 1000.0f))) - ((int) ((printAttributes2.getMinMargins().getBottomMils() * fMax) / 1000.0f));
                if (PrintHelper2.this.c != heightMils) {
                    PrintHelper2.this.c = heightMils;
                }
                new File(str).mkdirs();
                com.artifex.solib.a.e(str);
                PrintHelper2.this.a(context, sODoc, str);
                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder("Name of file").setContentType(0).build(), true);
            }

            @Override // android.print.PrintDocumentAdapter
            public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) throws Throwable {
                FileOutputStream fileOutputStream;
                FileOutputStream fileOutputStream2;
                FileInputStream fileInputStream = null;
                FileOutputStream fileOutputStream3 = null;
                FileOutputStream fileOutputStream4 = null;
                FileInputStream fileInputStream2 = null;
                try {
                    try {
                        FileInputStream fileInputStream3 = new FileInputStream(str);
                        try {
                            fileOutputStream2 = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int i = fileInputStream3.read(bArr);
                                    if (i <= 0) {
                                        break;
                                    } else {
                                        fileOutputStream2.write(bArr, 0, i);
                                    }
                                }
                                writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                                fileInputStream3.close();
                            } catch (FileNotFoundException | Exception unused) {
                                fileOutputStream3 = fileOutputStream2;
                                fileOutputStream2 = fileOutputStream3;
                                fileInputStream2 = fileInputStream3;
                                fileInputStream2.close();
                            } catch (Throwable th) {
                                fileOutputStream4 = fileOutputStream2;
                                th = th;
                                fileOutputStream = fileOutputStream4;
                                fileInputStream = fileInputStream3;
                                try {
                                    fileInputStream.close();
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                throw th;
                            }
                        } catch (FileNotFoundException | Exception unused2) {
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (FileNotFoundException | Exception unused3) {
                        fileOutputStream2 = null;
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = null;
                    }
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        };
        ((PrintManager) context.getSystemService("print")).print(Utilities.getApplicationName(context) + " Document", printDocumentAdapter, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, SODoc sODoc, String str) {
        PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(context, this.f4732a);
        float fMin = Math.min(printedPdfDocument.getPageContentRect().width() / this.f4733b, printedPdfDocument.getPageContentRect().height() / this.c);
        int iR = sODoc.r();
        for (int i = 0; i < iR; i++) {
            PdfDocument.Page pageStartPage = printedPdfDocument.startPage(i);
            a(sODoc, i, fMin, pageStartPage);
            printedPdfDocument.finishPage(pageStartPage);
        }
        try {
            printedPdfDocument.writeTo(new FileOutputStream(new File(str)));
        } catch (IOException unused) {
        } finally {
            printedPdfDocument.close();
        }
    }
}
