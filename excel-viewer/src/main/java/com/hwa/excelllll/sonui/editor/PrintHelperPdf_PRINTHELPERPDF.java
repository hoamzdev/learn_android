package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.view.WindowManager;
import com.artifex.solib.SODoc;
import com.artifex.solib.SODocSaveListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class PrintHelperPdf {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static boolean f4739a;

    private static ProgressDialog a(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException unused) {
        }
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        progressDialog.setContentView(R.layout.sodk_editor_wait_spinner);
        return progressDialog;
    }

    public static void setPrinting(boolean z) {
        f4739a = z;
    }

    public void print(final Context context, SODoc sODoc) {
        if (f4739a) {
            return;
        }
        f4739a = true;
        final String str = com.artifex.solib.a.c(context) + "/print/" + UUID.randomUUID() + ".pdf";
        com.artifex.solib.a.c(str);
        com.artifex.solib.a.e(str);
        final ProgressDialog progressDialogA = a(context);
        sODoc.b(str, false, new SODocSaveListener() { // from class: com.artifex.sonui.editor.PrintHelperPdf.1
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(int i, int i2) {
                progressDialogA.dismiss();
                if (i != 0) {
                    String str2 = String.format(context.getString(R.string.sodk_editor_error_saving_document_code), Integer.valueOf(i2));
                    Context context2 = context;
                    Utilities.showMessage((Activity) context2, context2.getString(R.string.sodk_editor_error), str2);
                } else {
                    PrintDocumentAdapter printDocumentAdapter = new PrintDocumentAdapter() { // from class: com.artifex.sonui.editor.PrintHelperPdf.1.1
                        @Override // android.print.PrintDocumentAdapter
                        public void onFinish() {
                            com.artifex.solib.a.e(str);
                            boolean unused = PrintHelperPdf.f4739a = false;
                        }

                        @Override // android.print.PrintDocumentAdapter
                        public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                            if (cancellationSignal.isCanceled()) {
                                layoutResultCallback.onLayoutCancelled();
                            } else {
                                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder("Name of file").setContentType(0).build(), true);
                            }
                        }

                        @Override // android.print.PrintDocumentAdapter
                        public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) throws Throwable {
                            Throwable th;
                            FileOutputStream fileOutputStream;
                            FileOutputStream fileOutputStream2;
                            FileOutputStream fileOutputStream3;
                            Object obj = null;
                            FileOutputStream fileOutputStream4 = null;
                            FileOutputStream fileOutputStream5 = null;
                            FileOutputStream fileOutputStream6 = null;
                            Object obj2 = null;
                            Object obj3 = null;
                            try {
                                try {
                                    Object objF = com.artifex.solib.a.f(str);
                                    try {
                                        fileOutputStream3 = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
                                        try {
                                            byte[] bArr = new byte[1024];
                                            while (true) {
                                                int iA = com.artifex.solib.a.a(objF, bArr);
                                                if (iA <= 0) {
                                                    break;
                                                } else {
                                                    fileOutputStream3.write(bArr, 0, iA);
                                                }
                                            }
                                            writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                                            if (objF != null) {
                                                com.artifex.solib.a.a(objF);
                                            }
                                        } catch (FileNotFoundException unused) {
                                            fileOutputStream4 = fileOutputStream3;
                                            FileOutputStream fileOutputStream7 = fileOutputStream4;
                                            obj2 = objF;
                                            fileOutputStream2 = fileOutputStream7;
                                            if (obj2 != null) {
                                                com.artifex.solib.a.a(obj2);
                                            }
                                            if (fileOutputStream2 == null) {
                                                return;
                                            } else {
                                                fileOutputStream3 = fileOutputStream2;
                                            }
                                        } catch (Exception unused2) {
                                            fileOutputStream5 = fileOutputStream3;
                                            FileOutputStream fileOutputStream8 = fileOutputStream5;
                                            obj3 = objF;
                                            fileOutputStream2 = fileOutputStream8;
                                            if (obj3 != null) {
                                                com.artifex.solib.a.a(obj3);
                                            }
                                            if (fileOutputStream2 == null) {
                                                return;
                                            }
                                            fileOutputStream3 = fileOutputStream2;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            fileOutputStream6 = fileOutputStream3;
                                            FileOutputStream fileOutputStream9 = fileOutputStream6;
                                            obj = objF;
                                            fileOutputStream = fileOutputStream9;
                                            if (obj != null) {
                                                try {
                                                    com.artifex.solib.a.a(obj);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    throw th;
                                                }
                                            }
                                            if (fileOutputStream == null) {
                                                throw th;
                                            }
                                            fileOutputStream.close();
                                            throw th;
                                        }
                                    } catch (FileNotFoundException unused3) {
                                    } catch (Exception unused4) {
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                } catch (FileNotFoundException unused5) {
                                    fileOutputStream2 = null;
                                } catch (Exception unused6) {
                                    fileOutputStream2 = null;
                                } catch (Throwable th4) {
                                    th = th4;
                                    fileOutputStream = null;
                                }
                                fileOutputStream3.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                    };
                    ((PrintManager) context.getSystemService("print")).print(Utilities.getApplicationName(context) + " Document", printDocumentAdapter, null);
                }
            }
        });
    }
}
