package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.supportv1.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.a.o.Res;
import com.artifex.mupdf.fitz.PDFWidget;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SODoc;
import com.artifex.solib.SODocSaveListener;
import com.artifex.solib.SOSecureFS;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.NUIActivity;
import com.artifex.sonui.editor.NUIDocView;
import com.artifex.sonui.editor.PrintHelperPdf;
import com.artifex.sonui.editor.SOCustomSaveComplete;
import com.artifex.sonui.editor.SODataLeakHandlers;
import com.artifex.sonui.editor.SODocSession;
import com.artifex.sonui.editor.SOFileDatabase;
import com.artifex.sonui.editor.SOFileState;
import com.artifex.sonui.editor.SOSaveAsComplete;
import com.artifex.sonui.editor.Utilities;
import defpackage.d8;
import defpackage.e6;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class g implements SODataLeakHandlers {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected Activity f4940a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    protected ConfigOptions f4941b;
    protected SOSecureFS c;
    protected String d;
    protected String e;
    protected String f;
    protected ProgressDialog g;
    private NUIDocView j;
    private String k;
    private String l;
    private final String h = "DataLeakHandlers";
    private ArrayList<String> i = null;
    private boolean m = false;
    private boolean n = false;

    /* JADX INFO: renamed from: com.artifex.sonui.g$3, reason: invalid class name */
    public class AnonymousClass3 implements SODocSaveListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ AppFile f4951a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ SOFileState f4952b;
        final /* synthetic */ SODocSession c;
        final /* synthetic */ SOSaveAsComplete d;

        public AnonymousClass3(AppFile appFile, SOFileState sOFileState, SODocSession sODocSession, SOSaveAsComplete sOSaveAsComplete) {
            this.f4951a = appFile;
            this.f4952b = sOFileState;
            this.c = sODocSession;
            this.d = sOSaveAsComplete;
        }

        @Override // com.artifex.solib.SODocSaveListener
        public void onComplete(int i, int i2) {
            g.this.g.dismiss();
            if (i == 0) {
                this.f4951a.a(this.f4952b.getInternalPath());
                this.f4951a.copyToRemote(BaseActivity.getCurrentActivity(), new AppFile.AppFileListener() { // from class: com.artifex.sonui.g.3.1
                    @Override // com.artifex.sonui.AppFile.AppFileListener
                    public void a(AppFile.a aVar) {
                        Activity activity;
                        String string;
                        String string2;
                        Runnable runnable;
                        AppFile.a aVar2 = AppFile.a.Success;
                        AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                        if (aVar == aVar2) {
                            anonymousClass3.f4952b.setUserPath(anonymousClass3.f4951a.g());
                            AnonymousClass3 anonymousClass4 = AnonymousClass3.this;
                            anonymousClass4.c.createThumbnail(anonymousClass4.f4952b);
                            AppFile appFile = AnonymousClass3.this.f4951a;
                            appFile.a(appFile.g());
                            AnonymousClass3 anonymousClass5 = AnonymousClass3.this;
                            anonymousClass5.f4952b.setForeignData(anonymousClass5.f4951a.toString());
                            activity = g.this.f4940a;
                            int iJ = Res.j("sodk_editor_saved_body");
                            string = activity.getString(iJ);
                            string2 = g.this.f4940a.getString(iJ);
                            runnable = new Runnable() { // from class: com.artifex.sonui.g.3.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    g.this.m = false;
                                    AnonymousClass3 anonymousClass6 = AnonymousClass3.this;
                                    anonymousClass6.d.onComplete(0, anonymousClass6.f4951a.g());
                                }
                            };
                        } else {
                            activity = g.this.f4940a;
                            int iJ2 = Res.j("sodk_editor_error_saving_document");
                            string = activity.getString(iJ2);
                            string2 = g.this.f4940a.getString(iJ2);
                            runnable = new Runnable() { // from class: com.artifex.sonui.g.3.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    g.this.m = false;
                                    AnonymousClass3 anonymousClass6 = AnonymousClass3.this;
                                    anonymousClass6.d.onComplete(1, anonymousClass6.f4951a.g());
                                }
                            };
                        }
                        Utilities.showMessageAndWait(activity, string, string2, runnable);
                    }
                });
                return;
            }
            this.f4952b.setUserPath(null);
            String str = String.format(g.this.f4940a.getString(Res.j("sodk_editor_error_saving_document_code")), Integer.valueOf(i2));
            Activity activity = g.this.f4940a;
            Utilities.showMessage(activity, activity.getString(Res.j("sodk_editor_error")), str);
            g.this.m = false;
            this.d.onComplete(1, this.f4951a.g());
        }
    }

    private String a(Uri uri) {
        String string;
        if (!DocumentsContract.isDocumentUri(this.f4940a, uri)) {
            String scheme = uri.getScheme();
            if (scheme == null || !scheme.equalsIgnoreCase("content")) {
                if (scheme == null || !scheme.equalsIgnoreCase("file")) {
                    return null;
                }
                return uri.getPath();
            }
            Cursor cursorQuery = this.f4940a.getContentResolver().query(uri, null, null, null, null);
            if (!cursorQuery.moveToFirst() || (string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"))) == null) {
                return null;
            }
            return Uri.parse(string).getPath();
        }
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(this.f4940a.getContentResolver().getType(uri));
        StringBuilder sbT = e6.t(this.c != null ? this.f.replace(this.e, this.d) : this.l);
        sbT.append(UUID.randomUUID());
        sbT.append(".");
        sbT.append(extensionFromMimeType);
        String string2 = sbT.toString();
        try {
            byte[] bArr = new byte[PDFWidget.PDF_TX_FIELD_IS_MULTILINE];
            InputStream inputStreamOpenInputStream = this.f4940a.getContentResolver().openInputStream(uri);
            FileOutputStream fileOutputStream = new FileOutputStream(string2);
            while (inputStreamOpenInputStream.available() > 0) {
                inputStreamOpenInputStream.read(bArr);
                fileOutputStream.write(bArr);
            }
            inputStreamOpenInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            if (this.c != null) {
                string2 = string2.replace(this.d, this.e);
            }
            a(string2);
            return string2;
        } catch (Exception unused) {
            Log.e("DataLeakHandlers", "getRealPathFromURI(): Failed to copy image for insertion.");
            return null;
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void doInsert() {
        final NUIDocView nUIDocView;
        final String str = this.k;
        if (str == null || (nUIDocView = this.j) == null) {
            return;
        }
        this.j = null;
        this.k = null;
        new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.g.4
            @Override // java.lang.Runnable
            public void run() {
                nUIDocView.doInsertImage(str);
                ProgressDialog progressDialog = g.this.g;
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    g.this.g = null;
                }
            }
        }, 50L);
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void finaliseDataLeakHandlers() {
        if (this.i == null) {
            return;
        }
        int i = 0;
        while (true) {
            int size = this.i.size();
            ArrayList<String> arrayList = this.i;
            if (i >= size) {
                arrayList.clear();
                return;
            } else {
                com.artifex.solib.a.e(arrayList.get(i));
                i++;
            }
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void initDataLeakHandlers(Activity activity) throws IOException {
        String str;
        this.f4941b = ConfigOptions.a();
        this.f4940a = activity;
        this.j = null;
        this.k = null;
        try {
            SOSecureFS sOSecureFSA = com.artifex.solib.k.a();
            this.c = sOSecureFSA;
            if (sOSecureFSA == null) {
                throw new ClassNotFoundException();
            }
            this.d = sOSecureFSA.getSecurePath();
            this.e = this.c.getSecurePrefix();
            StringBuilder sb = new StringBuilder();
            sb.append(com.artifex.solib.a.c(this.f4940a));
            String str2 = File.separator;
            String strQ = e6.q(sb, str2, "dataleak", str2);
            this.f = strQ;
            if (!com.artifex.solib.a.b(strQ) && !com.artifex.solib.a.c(this.f)) {
                throw new IOException();
            }
            this.l = this.f4940a.getExternalCacheDir() + str2 + "dataleak" + str2;
            File file = new File(this.l);
            if (!file.exists() && !file.mkdirs()) {
                throw new IOException();
            }
            this.i = new ArrayList<>();
        } catch (ClassNotFoundException unused) {
            Log.i("DataLeakHandlers", "SecureFS implementation unavailable");
        } catch (ExceptionInInitializerError unused2) {
            str = "DataLeakHandlers experienced unexpected exception [ExceptionInInitializerError]";
            Log.e("DataLeakHandlers", str);
        } catch (LinkageError unused3) {
            str = "DataLeakHandlers experienced unexpected exception [LinkageError]";
            Log.e("DataLeakHandlers", str);
        } catch (SecurityException unused4) {
            str = "DataLeakHandlers experienced unexpected exception [SecurityException]";
            Log.e("DataLeakHandlers", str);
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void insertImageHandler(NUIDocView nUIDocView) throws UnsupportedOperationException {
        if (!this.f4941b.k()) {
            d8.a();
            return;
        }
        this.j = nUIDocView;
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        this.f4940a.startActivityForResult(Intent.createChooser(intent, "Select Image"), 10);
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void insertPhotoHandler(NUIDocView nUIDocView) throws UnsupportedOperationException {
        if (!this.f4941b.l()) {
            d8.a();
            return;
        }
        this.j = nUIDocView;
        String str = this.l + UUID.randomUUID() + ".jpg";
        this.k = str;
        a(str);
        Uri uriB = FileProvider.b(this.f4940a, this.f4940a.getApplicationContext().getPackageName() + ".provider", new File(this.k));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addFlags(1);
        intent.addFlags(2);
        intent.setClipData(ClipData.newRawUri(null, uriB));
        intent.putExtra("output", uriB);
        this.f4940a.startActivityForResult(intent, 11);
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void launchUrlHandler(String str) throws UnsupportedOperationException {
        if (!this.f4941b.o()) {
            d8.a();
        } else {
            this.f4940a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void onActivityResult(int i, int i2, Intent intent) {
        int iJ = Res.j("sodk_editor_please_wait");
        if (i == 10) {
            if (i2 != -1) {
                this.j = null;
                this.k = null;
                return;
            } else {
                a(this.f4940a.getString(iJ), "", true);
                this.k = a(intent.getData());
                return;
            }
        }
        if (i == 11) {
            if (i2 == -1) {
                a(this.f4940a.getString(iJ), "", true);
            } else {
                this.j = null;
                this.k = null;
            }
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void openInHandler(String str, SODoc sODoc) throws UnsupportedOperationException {
        if (!this.f4941b.f()) {
            d8.a();
            return;
        }
        if (this.n) {
            return;
        }
        this.n = true;
        SODocSession session = NUIDocView.currentNUIDocView().getSession();
        final SOFileState fileState = session.getFileState();
        final boolean hasBeenModified = session.getDoc().getHasBeenModified();
        final String strA = a((fileState.getUserPath() != null ? new File(fileState.getUserPath()) : new File(fileState.getOpenedPath())).getName(), (String) null);
        new File(strA).mkdirs();
        session.getDoc().a(fileState.getInternalPath(), new SODocSaveListener() { // from class: com.artifex.sonui.g.7
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(int i, int i2) {
                if (i != 0) {
                    String str2 = String.format(g.this.f4940a.getString(Res.j("sodk_editor_error_saving_document_code")), Integer.valueOf(i2));
                    Activity activity = g.this.f4940a;
                    Utilities.showMessage(activity, activity.getString(Res.j("sodk_editor_error")), str2);
                    g.this.n = false;
                    return;
                }
                new File(strA).mkdirs();
                com.artifex.solib.a.a(fileState.getInternalPath(), strA, true);
                g.this.a(strA);
                fileState.setHasChanges(hasBeenModified);
                g gVar = g.this;
                gVar.a(strA, 2, gVar.f4940a.getString(Res.j("sodk_editor_open_in")));
                new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.g.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        g.this.n = false;
                    }
                }, 1000L);
            }
        });
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void openPdfInHandler(String str, SODoc sODoc) throws UnsupportedOperationException {
        if (!this.f4941b.g()) {
            d8.a();
            return;
        }
        if (this.n) {
            return;
        }
        this.n = true;
        SODocSession session = NUIDocView.currentNUIDocView().getSession();
        SOFileState fileState = session.getFileState();
        final String strA = a((fileState.getUserPath() != null ? new File(fileState.getUserPath()) : new File(fileState.getOpenedPath())).getName(), ".pdf");
        new File(strA).mkdirs();
        a(strA);
        session.getDoc().b(strA, false, new SODocSaveListener() { // from class: com.artifex.sonui.g.8
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(int i, int i2) {
                g gVar = g.this;
                if (i == 0) {
                    gVar.a(strA, 3, gVar.f4940a.getString(Res.j("sodk_editor_open_pdf_in")));
                    new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.g.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            g.this.n = false;
                        }
                    }, 1000L);
                } else {
                    String str2 = String.format(gVar.f4940a.getString(Res.j("sodk_editor_error_saving_document_code")), Integer.valueOf(i2));
                    Activity activity = g.this.f4940a;
                    Utilities.showMessage(activity, activity.getString(Res.j("sodk_editor_error")), str2);
                    g.this.n = false;
                }
            }
        });
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void pauseHandler(final SODoc sODoc, boolean z) {
        Activity activity = this.f4940a;
        if ((!(activity instanceof NUIActivity) || ((NUIActivity) activity).childIntent() == null) && sODoc.r() != 0 && !SOFileState.mDontAutoOpen && this.f4941b.s() && sODoc.i()) {
            final SOFileState fileState = NUIDocView.currentNUIDocView().getSession().getFileState();
            final boolean hasBeenModified = sODoc.getHasBeenModified();
            final boolean zHasChanges = fileState.hasChanges();
            sODoc.a(fileState.getInternalPath(), new SODocSaveListener() { // from class: com.artifex.sonui.g.5
                @Override // com.artifex.solib.SODocSaveListener
                public void onComplete(int i, int i2) {
                    if (i != 0) {
                        String str = String.format(g.this.f4940a.getString(Res.j("sodk_editor_error_saving_document_code")), Integer.valueOf(i2));
                        Activity activity2 = g.this.f4940a;
                        Utilities.showMessage(activity2, activity2.getString(Res.j("sodk_editor_error")), str);
                        return;
                    }
                    fileState.setHasChanges(hasBeenModified || zHasChanges);
                    SOFileState sOFileState = new SOFileState(fileState);
                    sOFileState.updateAccess();
                    sOFileState.setHasChanges(hasBeenModified || zHasChanges);
                    SOFileState.setAutoOpen(g.this.f4940a.getApplicationContext(), sOFileState);
                    SOFileState sOFileState2 = fileState;
                    sOFileState2.setOpenedPath(sOFileState2.getInternalPath());
                    sODoc.a(fileState.getInternalPath());
                    NUIDocView.currentNUIDocView().forceReload();
                }
            });
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void postSaveHandler(final SOSaveAsComplete sOSaveAsComplete) {
        SOFileState fileState;
        SODocSession session = NUIDocView.currentNUIDocView().getSession();
        if (session == null || (fileState = session.getFileState()) == null) {
            return;
        }
        session.createThumbnail(fileState);
        String foreignData = fileState.getForeignData();
        if (foreignData != null) {
            AppFile appFileE = AppFile.e(foreignData);
            appFileE.a(fileState.getInternalPath());
            appFileE.copyToRemote(BaseActivity.getCurrentActivity(), new AppFile.AppFileListener() { // from class: com.artifex.sonui.g.6
                @Override // com.artifex.sonui.AppFile.AppFileListener
                public void a(AppFile.a aVar) {
                    sOSaveAsComplete.onComplete(0, null);
                    if (aVar == AppFile.a.Fail) {
                        Utilities.showMessage(BaseActivity.getCurrentActivity(), "Upload failed", "Upload failed");
                    }
                }
            });
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void printHandler(SODoc sODoc) throws UnsupportedOperationException {
        if (this.f4941b.m()) {
            new PrintHelperPdf().print(this.f4940a, sODoc);
        } else {
            d8.a();
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void saveAsHandler(String str, SODoc sODoc, SOSaveAsComplete sOSaveAsComplete) throws UnsupportedOperationException {
        if (this.f4941b.d()) {
            a(1, str, sOSaveAsComplete);
        } else {
            d8.a();
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void saveAsPdfHandler(String str, SODoc sODoc) throws UnsupportedOperationException {
        if (this.f4941b.e()) {
            a(2, str, (SOSaveAsComplete) null);
        } else {
            d8.a();
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void shareHandler(String str, SODoc sODoc) throws UnsupportedOperationException {
        if (!this.f4941b.h()) {
            d8.a();
            return;
        }
        if (this.n) {
            return;
        }
        this.n = true;
        SODocSession session = NUIDocView.currentNUIDocView().getSession();
        final SOFileState fileState = session.getFileState();
        final boolean hasBeenModified = session.getDoc().getHasBeenModified();
        final String strA = a((fileState.getUserPath() != null ? new File(fileState.getUserPath()) : new File(fileState.getOpenedPath())).getName(), (String) null);
        new File(strA).mkdirs();
        session.getDoc().a(fileState.getInternalPath(), new SODocSaveListener() { // from class: com.artifex.sonui.g.9
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(int i, int i2) {
                if (i != 0) {
                    String str2 = String.format(g.this.f4940a.getString(Res.j("sodk_editor_error_saving_document_code")), Integer.valueOf(i2));
                    Activity activity = g.this.f4940a;
                    Utilities.showMessage(activity, activity.getString(Res.j("sodk_editor_error")), str2);
                    g.this.n = false;
                    return;
                }
                com.artifex.solib.a.a(fileState.getInternalPath(), strA, true);
                g.this.a(strA);
                fileState.setHasChanges(hasBeenModified);
                g gVar = g.this;
                gVar.a(strA, 1, gVar.f4940a.getString(Res.j("sodk_editor_share_with")));
                new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.g.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        g.this.n = false;
                    }
                }, 1000L);
            }
        });
    }

    /* JADX INFO: renamed from: com.artifex.sonui.g$1, reason: invalid class name */
    public class AnonymousClass1 implements ChoosePathActivity.a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f4942a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f4943b;
        final /* synthetic */ SOSaveAsComplete c;

        public AnonymousClass1(int i, String str, SOSaveAsComplete sOSaveAsComplete) {
            this.f4942a = i;
            this.f4943b = str;
            this.c = sOSaveAsComplete;
        }

        @Override // com.artifex.sonui.ChoosePathActivity.a
        public void a(FileBrowser fileBrowser) {
            StringBuilder sbT;
            String str;
            SOFileState.mDontAutoOpen = false;
            String fileName = fileBrowser.getFileName();
            if (!Utilities.isValidFilename(fileName)) {
                Activity activity = g.this.f4940a;
                Utilities.showMessage(activity, "", activity.getString(Res.j("sodk_editor_invalid_file_name")));
                g.this.m = false;
                return;
            }
            if (this.f4942a == 2) {
                sbT = e6.t(fileName);
                str = ".pdf";
            } else {
                sbT = defpackage.h.t(fileName, ".");
                str = this.f4943b;
            }
            sbT.append(str);
            final AppFile appFileChild = fileBrowser.getFolderAppFile().child(sbT.toString());
            appFileChild.exists(new AppFile.ExistsListener() { // from class: com.artifex.sonui.g.1.1
                @Override // com.artifex.sonui.AppFile.ExistsListener
                public void a(boolean z) {
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    if (z) {
                        Activity activity2 = g.this.f4940a;
                        Utilities.yesNoMessage(activity2, activity2.getString(Res.j("sodk_editor_replace_file_title")), g.this.f4940a.getString(Res.j("sodk_editor_replace_file_body")), g.this.f4940a.getString(Res.j("sodk_editor_yes")), g.this.f4940a.getString(Res.j("sodk_editor_no")), new Runnable() { // from class: com.artifex.sonui.g.1.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                C00591 c00591 = C00591.this;
                                AnonymousClass1 anonymousClass2 = AnonymousClass1.this;
                                if (anonymousClass2.f4942a == 2) {
                                    g.this.a(appFileChild);
                                } else {
                                    g.this.a(appFileChild, anonymousClass2.c);
                                }
                            }
                        }, new Runnable() { // from class: com.artifex.sonui.g.1.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                g.this.m = false;
                            }
                        });
                    } else if (anonymousClass1.f4942a == 2) {
                        g.this.a(appFileChild);
                    } else {
                        g.this.a(appFileChild, anonymousClass1.c);
                    }
                }
            });
        }

        @Override // com.artifex.sonui.ChoosePathActivity.a
        public void a() {
            SOFileState.mDontAutoOpen = false;
            g.this.m = false;
        }
    }

    @Override // com.artifex.sonui.editor.SODataLeakHandlers
    public void customSaveHandler(String str, SODoc sODoc, String str2, SOCustomSaveComplete sOCustomSaveComplete) throws UnsupportedOperationException, IOException {
    }

    private String a(Context context, File file) {
        boolean zExists = new File(com.artifex.solib.a.c(context), file.getName()).exists();
        String name = file.getName();
        if (zExists) {
            int i = 0;
            int i2 = 0;
            while (true) {
                k[] kVarArr = ExplorerActivity.f4395a;
                if (i2 >= kVarArr.length) {
                    while (true) {
                        k[] kVarArr2 = ExplorerActivity.f4396b;
                        if (i >= kVarArr2.length) {
                            break;
                        }
                        k kVar = kVarArr2[i];
                        if (name.equalsIgnoreCase(kVar.f4989a)) {
                            return kVar.g;
                        }
                        i++;
                    }
                } else {
                    k kVar2 = kVarArr[i2];
                    if (name.equalsIgnoreCase(kVar2.f4989a)) {
                        return kVar2.g;
                    }
                    i2++;
                }
            }
        }
        return file.getName();
    }

    private Intent a(Uri uri, int i) {
        Intent intent;
        Intent intent2;
        String mimeType = Utilities.getMimeType(uri.toString());
        if (i != 1) {
            if (i == 2 || i == 3) {
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setDataAndType(uri, mimeType);
            } else {
                intent2 = null;
            }
            intent2.addFlags(1);
            return intent2;
        }
        intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setType(mimeType);
        intent2 = intent;
        intent2.addFlags(1);
        return intent2;
    }

    public String a(String str, String str2) {
        StringBuilder sb;
        if (str2 == null) {
            sb = new StringBuilder();
        } else {
            String strI = defpackage.h.i("", str);
            int iLastIndexOf = strI.lastIndexOf(".");
            if (iLastIndexOf >= 0) {
                strI = strI.substring(0, iLastIndexOf);
            }
            str = strI.concat(str2);
            sb = new StringBuilder();
        }
        sb.append(this.l);
        sb.append(UUID.randomUUID());
        return defpackage.h.p(sb, File.separator, str);
    }

    private void a(int i, String str, SOSaveAsComplete sOSaveAsComplete) {
        if (this.m) {
            return;
        }
        this.m = true;
        boolean z = NUIDocView.currentNUIDocView().getSession().getFileState().getUserPath() == null;
        String strG = com.artifex.solib.a.g(str);
        String strA = a(this.f4940a.getApplicationContext(), new File(str));
        SOFileState.mDontAutoOpen = true;
        ChoosePathActivity.a(this.f4940a, i, z, new AnonymousClass1(i, strG, sOSaveAsComplete), strA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AppFile appFile) {
        final SODocSession session = NUIDocView.currentNUIDocView().getSession();
        session.getFileState();
        final String str = this.f + UUID.randomUUID() + File.separator + appFile.b();
        com.artifex.solib.a.c(str);
        a(str);
        a(this.f4940a.getString(Res.j("sodk_editor_please_wait")), "", false);
        session.getDoc().b(str, false, new SODocSaveListener() { // from class: com.artifex.sonui.g.2
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(int i, int i2) {
                g gVar = g.this;
                if (i == 0) {
                    gVar.g.dismiss();
                    appFile.a(str);
                    appFile.copyToRemote(BaseActivity.getCurrentActivity(), new AppFile.AppFileListener() { // from class: com.artifex.sonui.g.2.1
                        @Override // com.artifex.sonui.AppFile.AppFileListener
                        public void a(AppFile.a aVar) {
                            g.this.m = false;
                            Activity activity = g.this.f4940a;
                            int iJ = Res.j("sodk_editor_saved_body");
                            Utilities.showMessage(activity, activity.getString(iJ), g.this.f4940a.getString(iJ));
                            SOFileState sOFileStateStateForPath = SOFileDatabase.getDatabase().stateForPath(appFile.g(), false, true);
                            session.createThumbnail(sOFileStateStateForPath);
                            AppFile appFile2 = appFile;
                            appFile2.a(appFile2.g());
                            sOFileStateStateForPath.setForeignData(appFile.toString());
                        }
                    });
                } else {
                    gVar.g.dismiss();
                    g.this.m = false;
                    String str2 = String.format(g.this.f4940a.getString(Res.j("sodk_editor_error_saving_document_code")), Integer.valueOf(i2));
                    Activity activity = g.this.f4940a;
                    Utilities.showMessage(activity, activity.getString(Res.j("sodk_editor_error")), str2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AppFile appFile, SOSaveAsComplete sOSaveAsComplete) {
        SODocSession session = NUIDocView.currentNUIDocView().getSession();
        SOFileState fileState = session.getFileState();
        a(this.f4940a.getString(Res.j("sodk_editor_please_wait")), "", false);
        session.getDoc().a(fileState.getInternalPath(), new AnonymousClass3(appFile, fileState, session, sOSaveAsComplete));
    }

    public void a(String str) {
        this.i.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, int i, String str2) {
        File file = new File(str);
        try {
            Uri uriB = FileProvider.b(this.f4940a, this.f4940a.getPackageName() + ".provider", file);
            ArrayList arrayList = new ArrayList();
            List<ResolveInfo> listQueryIntentActivities = this.f4940a.getPackageManager().queryIntentActivities(a(uriB, i), 0);
            if (!listQueryIntentActivities.isEmpty()) {
                for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                    String packageName = this.f4940a.getApplicationContext().getPackageName();
                    String str3 = resolveInfo.activityInfo.packageName;
                    if (str3.compareToIgnoreCase(packageName) != 0) {
                        Intent intentA = a(uriB, i);
                        intentA.setComponent(new ComponentName(str3, resolveInfo.activityInfo.name));
                        intentA.setPackage(str3);
                        arrayList.add(intentA);
                    }
                }
            }
            if (arrayList.isEmpty()) {
                Activity activity = this.f4940a;
                Utilities.showMessage(activity, str2, activity.getString(Res.j("sodk_editor_no_apps_can_perform")));
            } else {
                Intent intentCreateChooser = Intent.createChooser((Intent) arrayList.remove(0), str2);
                intentCreateChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
                this.f4940a.startActivity(intentCreateChooser);
            }
        } catch (Exception unused) {
            Activity activity2 = this.f4940a;
            Utilities.showMessage(activity2, activity2.getString(Res.j("sodk_editor_share_error_title")), this.f4940a.getString(Res.j("sodk_editor_no_documents_found")));
        }
    }

    public void a(String str, String str2, boolean z) {
        ProgressDialog progressDialog = new ProgressDialog(this.f4940a, Res.k("sodk_editor_alert_dialog_style"));
        this.g = progressDialog;
        progressDialog.setIndeterminate(true);
        this.g.setCancelable(z);
        this.g.setTitle(str);
        this.g.setMessage(str2);
        this.g.show();
    }
}
