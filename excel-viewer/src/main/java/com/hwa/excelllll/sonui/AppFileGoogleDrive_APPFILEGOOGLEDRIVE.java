package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.webkit.MimeTypeMap;
import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.Utilities;
import com.facebook.ads.AdError;
import com.ping.abis.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.ping.abis.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.ping.abis.client.googleapis.media.MediaHttpDownloader;
import com.ping.abis.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.ping.abis.client.googleapis.media.MediaHttpUploader;
import com.ping.abis.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.ping.abis.client.http.FileContent;
import com.ping.abis.client.repackaged.com.ping.comed.base.Preconditions;
import com.ping.abis.services.drive.Drive;
import com.ping.abis.services.drive.Drive.Files;
import com.ping.abis.services.drive.model.FileList;
import defpackage.e6;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class AppFileGoogleDrive extends AppFile {
    private static Activity o;
    private static final Map<String, String> q;
    private static Object r;
    private static String s;
    private static Drive t;
    private static boolean u;
    private static a v;
    private String k;
    private String l;
    private String m;
    private String n;
    private AsyncTask p;

    /* JADX INFO: renamed from: com.artifex.sonui.AppFileGoogleDrive$3, reason: invalid class name */
    /* JADX INFO: loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass3 {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4338a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f4339b;

        static {
            int[] iArr = new int[MediaHttpUploader.UploadState.values().length];
            f4339b = iArr;
            try {
                iArr[3] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4339b[4] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[MediaHttpDownloader.DownloadState.values().length];
            f4338a = iArr2;
            try {
                iArr2[2] = 1;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: renamed from: com.artifex.sonui.AppFileGoogleDrive$7, reason: invalid class name */
    public class AnonymousClass7 implements AppFile.b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f4350a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ AppFile.AppFileListener f4351b;

        public AnonymousClass7(Context context, AppFile.AppFileListener appFileListener) {
            this.f4350a = context;
            this.f4351b = appFileListener;
        }

        @Override // com.artifex.sonui.AppFile.b
        public void a(int i) {
            if (i != 0) {
                AppFile.f(String.format("AppFileGoogleDrive copyFromRemote connection failed %d", Integer.valueOf(i)));
                this.f4351b.a(AppFile.a.Fail);
                return;
            }
            AppFileGoogleDrive appFileGoogleDrive = AppFileGoogleDrive.this;
            Context context = this.f4350a;
            appFileGoogleDrive.a(context, context.getString(Res.j("sodk_editor_downloading")), new AppFile.c() { // from class: com.artifex.sonui.AppFileGoogleDrive.7.1
                @Override // com.artifex.sonui.AppFile.c
                public void a() {
                    AppFileGoogleDrive.this.p.cancel(true);
                }
            }, 1, true);
            AppFileGoogleDrive.this.p = new AsyncTask<Void, Integer, Void>() { // from class: com.artifex.sonui.AppFileGoogleDrive.7.2

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                boolean f4353a = false;

                /* JADX INFO: renamed from: com.artifex.sonui.AppFileGoogleDrive$7$2$2, reason: invalid class name and collision with other inner class name */
                public class C00372 implements MediaHttpDownloaderProgressListener {

                    /* JADX INFO: renamed from: a, reason: collision with root package name */
                    final /* synthetic */ AppFile.d f4356a;

                    /* JADX INFO: renamed from: b, reason: collision with root package name */
                    final /* synthetic */ String f4357b;

                    public C00372(AppFile.d dVar, String str) {
                        this.f4356a = dVar;
                        this.f4357b = str;
                    }

                    public void progressChanged(MediaHttpDownloader mediaHttpDownloader) throws IOException {
                        int[] iArr = AnonymousClass3.f4338a;
                        mediaHttpDownloader.getClass();
                        throw null;
                    }
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public Void doInBackground(Void... voidArr) {
                    try {
                        File file = new File(AppFileGoogleDrive.this.a(AppFileGoogleDrive.o, AppFileGoogleDrive.this.f4253b));
                        file.getParentFile().mkdirs();
                        new AppFile.d(AppFileGoogleDrive.this.e, new FileOutputStream(file), new AppFile.d.a() { // from class: com.artifex.sonui.AppFileGoogleDrive.7.2.1
                            @Override // com.artifex.sonui.AppFile.d.a
                            public void a(long j, long j2) {
                                ProgressDialog progressDialog = AppFileGoogleDrive.this.j;
                                if (progressDialog != null) {
                                    progressDialog.setMax((int) j2);
                                    AppFileGoogleDrive.this.j.setProgress((int) j);
                                }
                            }
                        });
                        if (!AppFileGoogleDrive.this.m.contains("vnd.google-apps")) {
                            Drive drive = AppFileGoogleDrive.t;
                            drive.getClass();
                            new Drive.Files.Get(drive.new Files(), AppFileGoogleDrive.this.k);
                            throw null;
                        }
                        Drive drive2 = AppFileGoogleDrive.t;
                        drive2.getClass();
                        String str = AppFileGoogleDrive.this.k;
                        AppFileGoogleDrive appFileGoogleDrive2 = AppFileGoogleDrive.this;
                        String strK = appFileGoogleDrive2.k(appFileGoogleDrive2.m);
                        Drive.Files.Export export = new Drive.Files.Export(drive2, "GET", "files/{fileId}/export", Void.class);
                        Preconditions.b(str, "Required parameter fileId must be specified.");
                        Preconditions.b(strK, "Required parameter mimeType must be specified.");
                        export.c.getClass();
                        throw null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
                public void onCancelled() {
                    AnonymousClass7.this.f4351b.a(AppFile.a.Cancel);
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onPostExecute(Void r1) {
                    super.onPostExecute(r1);
                    AppFileGoogleDrive.this.k();
                    AnonymousClass7.this.f4351b.a(this.f4353a ? AppFile.a.Success : AppFile.a.Fail);
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onProgressUpdate(Integer... numArr) {
                    super.onProgressUpdate(numArr);
                }
            }.execute(new Void[0]);
        }
    }

    public interface a {
        void a();

        void a(Exception exc);
    }

    static {
        HashMap map = new HashMap();
        q = map;
        map.put("application/vnd.google-apps.document", "docx");
        map.put("application/vnd.google-apps.spreadsheet", "xlsx");
        map.put("application/vnd.google-apps.presentation", "pptx");
        map.put("application/vnd.ms-word.document.macroenabled.12", "docm");
        map.put("application/vnd.openxmlformats-officedocument.wordprocessingml.template", "dotx");
        map.put("application/vnd.ms-word.template.macroenabled.12", "dotm");
        map.put("application/vnd.ms-excel.sheet.macroenabled.12", "xlsm");
        map.put("application/vnd.openxmlformats-officedocument.spreadsheetml.template", "xltx");
        map.put("application/vnd.ms-excel.template.macroenabled.12", "xltm");
        map.put("application/vnd.ms-powerpoint", "pps");
        map.put("application/vnd.ms-powerpoint.presentation.macroenabled.12", "pptm");
        map.put("application/vnd.openxmlformats-officedocument.presentationml.template", "potx");
        map.put("application/vnd.ms-powerpoint.presentation.macroenabled.12", "potm");
        map.put("application/vnd.openxmlformats-officedocument.presentationml.slideshow", "ppsx");
        map.put("application/vnd.ms-powerpoint.slideshow.macroenabled.12", "ppsm");
        r = null;
        s = null;
        v = null;
    }

    public AppFileGoogleDrive(String str, String str2, boolean z, boolean z2) {
        i(str);
        this.f4253b = str2;
        this.d = z;
        this.h = z2;
    }

    public static void a(int i, int i2, Intent intent) {
        String str;
        if (i != 1001) {
            if (i != 1002) {
                return;
            }
            if (i2 == -1) {
                a(new a() { // from class: com.artifex.sonui.AppFileGoogleDrive.11
                    @Override // com.artifex.sonui.AppFileGoogleDrive.a
                    public void a(Exception exc) {
                    }

                    @Override // com.artifex.sonui.AppFileGoogleDrive.a
                    public void a() {
                    }
                });
                return;
            } else {
                AppFile.f("act result - NO AUTH");
                return;
            }
        }
        if (intent != null) {
            if (intent.getStringExtra("authAccount") != null) {
                l(intent.getStringExtra("authAccount"));
            }
            if (u()) {
                u = true;
                v.a();
                return;
            }
            str = "act result - NO ACCOUNT";
        } else {
            str = "connFail - UNSPECD 1";
        }
        AppFile.f(str);
        v.a(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList<ContentValues> b(String str, String str2) {
        StringBuilder sb;
        String str3;
        ArrayList<ContentValues> arrayList = new ArrayList<>();
        if (t != null && u) {
            try {
                if (str.equalsIgnoreCase("root")) {
                    sb = new StringBuilder();
                    str3 = "(sharedWithMe or '";
                } else {
                    sb = new StringBuilder();
                    str3 = "'";
                }
                sb.append(str3);
                sb.append(str);
                Drive drive = t;
                drive.getClass();
                new Drive.Files.List(drive, "GET", "files", FileList.class).d();
                throw null;
            } catch (Exception e) {
                AppFile.a(e);
            }
        }
        return arrayList;
    }

    public static String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    private void i(String str) {
        this.f4252a = 2;
        this.f4253b = null;
        this.d = false;
        this.c = null;
        this.g = null;
        this.e = 0L;
        this.f = 0L;
        this.k = str;
        this.l = null;
        this.m = null;
        this.n = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String k(String str) {
        if (str.equalsIgnoreCase("application/vnd.google-apps.document")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        if (str.equalsIgnoreCase("application/vnd.google-apps.spreadsheet")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        return str.equalsIgnoreCase("application/vnd.google-apps.presentation") ? "application/vnd.openxmlformats-officedocument.presentationml.presentation" : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void l(String str) {
        Object obj = r;
        s = str;
        Utilities.setStringPreference(obj, "account_name", str);
    }

    private static String t() {
        String str = s;
        if (str != null) {
            return str;
        }
        String stringPreference = Utilities.getStringPreference(r, "account_name", null);
        s = stringPreference;
        return stringPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean u() {
        return false;
    }

    private static void v() {
        u();
        if (t() == null || t == null) {
            return;
        }
        u = false;
        new AsyncTask<Void, Void, Exception>() { // from class: com.artifex.sonui.AppFileGoogleDrive.2

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            boolean f4337a = false;

            @Override // android.os.AsyncTask
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public Exception doInBackground(Void... voidArr) {
                try {
                    Drive drive = AppFileGoogleDrive.t;
                    drive.getClass();
                    new Drive.Files.Get(drive.new Files(), "root");
                    throw null;
                } catch (UserRecoverableAuthIOException e) {
                    return e;
                } catch (GoogleAuthIOException e2) {
                    return e2;
                } catch (IOException unused) {
                    return null;
                } catch (Exception e3) {
                    AppFile.a(e3);
                    this.f4337a = true;
                    return null;
                }
            }

            @Override // android.os.AsyncTask
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public void onPostExecute(Exception exc) {
                super.onPostExecute(exc);
                if (AppFileGoogleDrive.u) {
                    AppFileGoogleDrive.w();
                } else if (this.f4337a) {
                    AppFileGoogleDrive.l(null);
                    AppFileGoogleDrive.u();
                } else {
                    AppFileGoogleDrive.b(exc);
                }
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void w() {
        AppFile.f("CONNECTED TO: " + t());
        v.a();
    }

    @Override // com.artifex.sonui.AppFile
    public void Logout(AppFile.LogoutListener logoutListener) {
        BaseActivity currentActivity = BaseActivity.getCurrentActivity();
        o = currentActivity;
        Context applicationContext = currentActivity.getApplicationContext();
        r = Utilities.getPreferencesObject(applicationContext, PreferenceManager.getDefaultSharedPreferencesName(applicationContext));
        l(null);
        logoutListener.a();
    }

    @Override // com.artifex.sonui.AppFile
    public int c(Context context) {
        String str = this.n;
        return str == null ? Utilities.colorForDocType(context, b()) : Utilities.colorForDocExt(context, str);
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile child(String str) {
        if (!this.d) {
            return null;
        }
        AppFileGoogleDrive appFileGoogleDrive = new AppFileGoogleDrive();
        appFileGoogleDrive.f4253b = str;
        appFileGoogleDrive.l = this.k;
        appFileGoogleDrive.g = null;
        appFileGoogleDrive.c = null;
        return appFileGoogleDrive;
    }

    @Override // com.artifex.sonui.AppFile
    public void copyFromRemote(Context context, AppFile.AppFileListener appFileListener) {
        a(new AnonymousClass7(context, appFileListener));
    }

    @Override // com.artifex.sonui.AppFile
    public void copyToRemote(final Context context, final AppFile.AppFileListener appFileListener) {
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileGoogleDrive.8
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i != 0) {
                    AppFile.f(String.format("AppFileGoogleDrive copyToRemote connection failed %d", Integer.valueOf(i)));
                    appFileListener.a(AppFile.a.Fail);
                    return;
                }
                AppFileGoogleDrive appFileGoogleDrive = AppFileGoogleDrive.this;
                Context context2 = context;
                appFileGoogleDrive.a(context2, context2.getString(Res.j("sodk_editor_uploading")), new AppFile.c() { // from class: com.artifex.sonui.AppFileGoogleDrive.8.1
                    @Override // com.artifex.sonui.AppFile.c
                    public void a() {
                        AppFileGoogleDrive.this.p.cancel(true);
                    }
                }, 1, false);
                AppFileGoogleDrive.this.p = new AsyncTask<Void, Integer, Void>() { // from class: com.artifex.sonui.AppFileGoogleDrive.8.2

                    /* JADX INFO: renamed from: a, reason: collision with root package name */
                    boolean f4361a = false;

                    /* JADX INFO: renamed from: com.artifex.sonui.AppFileGoogleDrive$8$2$1, reason: invalid class name */
                    public class AnonymousClass1 implements MediaHttpUploaderProgressListener {

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        final /* synthetic */ ArrayList f4363a;

                        public AnonymousClass1(ArrayList arrayList) {
                            this.f4363a = arrayList;
                        }

                        public void progressChanged(MediaHttpUploader mediaHttpUploader) throws IOException {
                            int[] iArr = AnonymousClass3.f4339b;
                            mediaHttpUploader.getClass();
                            throw null;
                        }
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Void doInBackground(Void... voidArr) {
                        try {
                            File file = new File(AppFileGoogleDrive.this.c);
                            if (!file.exists()) {
                                throw new IOException();
                            }
                            ArrayList arrayListB = AppFileGoogleDrive.b(AppFileGoogleDrive.this.l, AppFileGoogleDrive.this.f4253b);
                            String mimeType = AppFileGoogleDrive.getMimeType(AppFileGoogleDrive.this.c);
                            if (arrayListB != null && arrayListB.size() > 0) {
                                ((ContentValues) arrayListB.get(0)).getAsString("mime");
                            }
                            String str = AppFileGoogleDrive.this.l;
                            com.ping.abis.services.drive.model.File file2 = new com.ping.abis.services.drive.model.File();
                            String str2 = AppFileGoogleDrive.this.f4253b;
                            file2.c = Collections.singletonList(str);
                            new FileContent(file, mimeType);
                            Drive drive = AppFileGoogleDrive.t;
                            drive.getClass();
                            new Drive.Files.Create(drive, "POST", "/upload/nullfiles", file2, com.ping.abis.services.drive.model.File.class).c.getClass();
                            throw null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override // android.os.AsyncTask
                    public void onCancelled() {
                        appFileListener.a(AppFile.a.Cancel);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onPostExecute(Void r1) {
                        super.onPostExecute(r1);
                        AppFileGoogleDrive.this.k();
                        appFileListener.a(this.f4361a ? AppFile.a.Success : AppFile.a.Fail);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onProgressUpdate(Integer... numArr) {
                        super.onProgressUpdate(numArr);
                    }
                }.execute(new Void[0]);
            }
        });
    }

    @Override // com.artifex.sonui.AppFile
    public void deleteFile(final Context context, final AppFile.AppFileListener appFileListener) {
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileGoogleDrive.6
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i != 0) {
                    AppFile.f(String.format("AppFileGoogleDrive deleteFile connection failed %d", Integer.valueOf(i)));
                    appFileListener.a(AppFile.a.Fail);
                    return;
                }
                AppFileGoogleDrive appFileGoogleDrive = AppFileGoogleDrive.this;
                Context context2 = context;
                appFileGoogleDrive.a(context2, context2.getString(Res.j("sodk_editor_deleting")), new AppFile.c() { // from class: com.artifex.sonui.AppFileGoogleDrive.6.1
                    @Override // com.artifex.sonui.AppFile.c
                    public void a() {
                        AppFileGoogleDrive.this.p.cancel(true);
                    }
                });
                AppFileGoogleDrive.this.p = new AsyncTask<Void, Integer, Void>() { // from class: com.artifex.sonui.AppFileGoogleDrive.6.2

                    /* JADX INFO: renamed from: a, reason: collision with root package name */
                    boolean f4348a = false;

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Void doInBackground(Void... voidArr) {
                        try {
                            Drive drive = AppFileGoogleDrive.t;
                            drive.getClass();
                            String str = AppFileGoogleDrive.this.k;
                            Drive.Files.Delete delete = new Drive.Files.Delete(drive, "DELETE", "files/{fileId}", Void.class);
                            Preconditions.b(str, "Required parameter fileId must be specified.");
                            delete.d();
                            throw null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override // android.os.AsyncTask
                    public void onCancelled() {
                        appFileListener.a(AppFile.a.Cancel);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onPostExecute(Void r1) {
                        super.onPostExecute(r1);
                        AppFileGoogleDrive.this.k();
                        appFileListener.a(this.f4348a ? AppFile.a.Success : AppFile.a.Fail);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onProgressUpdate(Integer... numArr) {
                        AppFileGoogleDrive.this.j.setProgress(numArr[0].intValue());
                        super.onProgressUpdate(numArr);
                    }
                }.execute(new Void[0]);
            }
        });
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile duplicate() {
        AppFileGoogleDrive appFileGoogleDrive = (AppFileGoogleDrive) AppFile.b(this);
        appFileGoogleDrive.k = this.k;
        appFileGoogleDrive.l = this.l;
        appFileGoogleDrive.m = this.m;
        appFileGoogleDrive.n = this.n;
        return appFileGoogleDrive;
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile enumerateDir(AppFile.EnumerateListener enumerateListener) {
        AppFile.i = enumerateListener;
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileGoogleDrive.5
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i == 0) {
                    final ArrayList arrayList = new ArrayList();
                    new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileGoogleDrive.5.1
                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            ArrayList arrayListJ = AppFileGoogleDrive.j(AppFileGoogleDrive.this.k);
                            int size = arrayListJ.size();
                            int i2 = 0;
                            while (i2 < size) {
                                Object obj = arrayListJ.get(i2);
                                i2++;
                                ContentValues contentValues = (ContentValues) obj;
                                String asString = contentValues.getAsString("gdid");
                                String asString2 = contentValues.getAsString("name");
                                String asString3 = contentValues.getAsString("mime");
                                String asString4 = contentValues.getAsString("prnt");
                                long jLongValue = 0;
                                long jLongValue2 = contentValues.getAsLong("size") != null ? contentValues.getAsLong("size").longValue() : 0L;
                                boolean zB = AppFileGoogleDrive.b(contentValues);
                                if (contentValues.getAsLong("date") != null) {
                                    jLongValue = contentValues.getAsLong("date").longValue();
                                }
                                AppFileGoogleDrive appFileGoogleDrive = new AppFileGoogleDrive();
                                appFileGoogleDrive.f4253b = asString2;
                                appFileGoogleDrive.d = zB;
                                appFileGoogleDrive.k = asString;
                                appFileGoogleDrive.e = jLongValue2;
                                appFileGoogleDrive.f = jLongValue;
                                appFileGoogleDrive.m = asString3;
                                appFileGoogleDrive.l = asString4;
                                appFileGoogleDrive.n = (String) AppFileGoogleDrive.q.get(asString3);
                                appFileGoogleDrive.g = appFileGoogleDrive.g();
                                arrayList.add(appFileGoogleDrive);
                            }
                            return null;
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r1) {
                            super.onPostExecute(r1);
                            AppFile.EnumerateListener enumerateListener2 = AppFile.i;
                            if (enumerateListener2 != null) {
                                enumerateListener2.a(arrayList);
                            }
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onProgressUpdate(String... strArr) {
                            super.onProgressUpdate(strArr);
                        }
                    }.execute(new Void[0]);
                    return;
                }
                AppFile.f(String.format("AppFileGoogleDrive enumerateDir connection failed %d", Integer.valueOf(i)));
                AppFile.EnumerateListener enumerateListener2 = AppFile.i;
                if (enumerateListener2 != null) {
                    enumerateListener2.a(null);
                }
            }
        });
        return this;
    }

    @Override // com.artifex.sonui.AppFile
    public void exists(final AppFile.ExistsListener existsListener) {
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileGoogleDrive.10
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i == 0) {
                    new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileGoogleDrive.10.1

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4335a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            if (AppFileGoogleDrive.b(AppFileGoogleDrive.this.l, AppFileGoogleDrive.this.f4253b).size() <= 0) {
                                return null;
                            }
                            this.f4335a = true;
                            return null;
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r1) {
                            super.onPostExecute(r1);
                            existsListener.a(this.f4335a);
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onProgressUpdate(String... strArr) {
                            super.onProgressUpdate(strArr);
                        }
                    }.execute(new Void[0]);
                } else {
                    AppFile.f(String.format("AppFileGoogleDrive exists connection failed %d", Integer.valueOf(i)));
                    existsListener.a(false);
                }
            }
        });
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile fromString(String str) {
        AppFileGoogleDrive appFileGoogleDrive = (AppFileGoogleDrive) AppFile.d(str);
        String[] strArrSplit = str.split("\\|");
        appFileGoogleDrive.k = AppFile.c(strArrSplit[7]);
        appFileGoogleDrive.l = AppFile.c(strArrSplit[8]);
        appFileGoogleDrive.m = AppFile.c(strArrSplit[9]);
        appFileGoogleDrive.n = AppFile.c(strArrSplit[10]);
        return appFileGoogleDrive;
    }

    @Override // com.artifex.sonui.AppFile
    public String g() {
        StringBuilder sbT;
        StringBuilder sbT2;
        StringBuilder sbT3;
        String str = "Google Drive/" + this.f4253b;
        if (this.k == null) {
            sbT = defpackage.h.t(str, "|null");
        } else {
            sbT = defpackage.h.t(str, "|");
            sbT.append(this.k);
        }
        String string = sbT.toString();
        if (this.l == null) {
            sbT2 = defpackage.h.t(string, "|null");
        } else {
            sbT2 = defpackage.h.t(string, "|");
            sbT2.append(this.l);
        }
        String string2 = sbT2.toString();
        if (this.f4253b == null) {
            sbT3 = defpackage.h.t(string2, "|null");
        } else {
            StringBuilder sbT4 = defpackage.h.t(string2, "|");
            sbT4.append(this.f4253b);
            sbT3 = sbT4;
        }
        return sbT3.toString();
    }

    @Override // com.artifex.sonui.AppFile
    public String getDisplayPath() {
        String[] strArrSplit = this.g.split("\\|");
        return strArrSplit.length >= 1 ? strArrSplit[0] : this.f4253b;
    }

    @Override // com.artifex.sonui.AppFile
    public int getFolderResourceId() {
        return Res.f(this.k.equalsIgnoreCase("root") ? "sodk_icon_cloud_googledrive" : "sodk_editor_icon_folder");
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isCloud() {
        return true;
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isSameAs(AppFile appFile) {
        return appFile != null && a() == appFile.a() && this.k.compareTo(((AppFileGoogleDrive) appFile).k) == 0;
    }

    @Override // com.artifex.sonui.AppFile
    public int j() {
        String str = this.n;
        return str == null ? Utilities.iconForDocType(b()) : Utilities.iconForDocExt(str);
    }

    @Override // com.artifex.sonui.AppFile
    public void rename(final String str, final Context context, final AppFile.AppFileListener appFileListener) {
        String strG = com.artifex.solib.a.g(b());
        String strG2 = com.artifex.solib.a.g(str);
        if (strG2 == null || strG2.isEmpty()) {
            str = defpackage.h.j(str, ".", strG);
            strG2 = strG;
        }
        if (strG2.equalsIgnoreCase(strG)) {
            a(new AppFile.b() { // from class: com.artifex.sonui.AppFileGoogleDrive.9
                @Override // com.artifex.sonui.AppFile.b
                public void a(int i) {
                    if (i != 0) {
                        AppFile.f(String.format("AppFileGoogleDrive rename connection failed %d", Integer.valueOf(i)));
                        appFileListener.a(AppFile.a.Fail);
                        return;
                    }
                    AppFileGoogleDrive appFileGoogleDrive = AppFileGoogleDrive.this;
                    Context context2 = context;
                    appFileGoogleDrive.a(context2, context2.getString(Res.j("sodk_editor_renaming")), new AppFile.c() { // from class: com.artifex.sonui.AppFileGoogleDrive.9.1
                        @Override // com.artifex.sonui.AppFile.c
                        public void a() {
                            AppFileGoogleDrive.this.p.cancel(true);
                        }
                    });
                    AppFileGoogleDrive.this.p = new AsyncTask<Void, Integer, Void>() { // from class: com.artifex.sonui.AppFileGoogleDrive.9.2

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4368a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            try {
                                com.ping.abis.services.drive.model.File file = new com.ping.abis.services.drive.model.File();
                                String str2 = str;
                                Drive drive = AppFileGoogleDrive.t;
                                drive.getClass();
                                String str3 = AppFileGoogleDrive.this.k;
                                Drive.Files.Update update = new Drive.Files.Update(drive, "PATCH", "files/{fileId}", file, com.ping.abis.services.drive.model.File.class);
                                Preconditions.b(str3, "Required parameter fileId must be specified.");
                                update.d();
                                throw null;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }

                        @Override // android.os.AsyncTask
                        public void onCancelled() {
                            appFileListener.a(AppFile.a.Cancel);
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r1) {
                            super.onPostExecute(r1);
                            AppFileGoogleDrive.this.k();
                            appFileListener.a(this.f4368a ? AppFile.a.Success : AppFile.a.Fail);
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onProgressUpdate(Integer... numArr) {
                            AppFileGoogleDrive.this.j.setProgress(numArr[0].intValue());
                            super.onProgressUpdate(numArr);
                        }
                    }.execute(new Void[0]);
                }
            });
            return;
        }
        Utilities.showMessage((Activity) context, context.getString(Res.j("sodk_editor_error")), String.format(context.getString(Res.j("sodk_editor_cant_change_extension")), strG, strG2));
        if (appFileListener != null) {
            appFileListener.a(AppFile.a.Fail);
        }
    }

    @Override // com.artifex.sonui.AppFile
    public boolean serviceAvailable() {
        return true;
    }

    @Override // com.artifex.sonui.AppFile
    public String toString() {
        return defpackage.h.p(e6.t(defpackage.h.p(e6.t(defpackage.h.p(e6.t(defpackage.h.p(e6.t(AppFile.a(this)), AppFile.b(this.k), "|")), AppFile.b(this.l), "|")), AppFile.b(this.m), "|")), AppFile.b(this.n), "|");
    }

    public AppFileGoogleDrive() {
        this.f4252a = 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList<ContentValues> j(String str) {
        return b(str, (String) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Exception exc) {
        if (exc == null) {
            AppFile.f("connFail - UNSPECD 1");
            v.a(exc);
        } else if (exc instanceof UserRecoverableAuthIOException) {
            AppFile.f("connFail - has res");
            o.startActivityForResult(null, AdError.LOAD_TOO_FREQUENTLY_ERROR_CODE);
        } else {
            if (exc instanceof GoogleAuthIOException) {
                AppFile.f("connFail - SHA1?");
                exc.getMessage();
            } else {
                AppFile.f("connFail - UNSPECD 2");
            }
            v.a(exc);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(ContentValues contentValues) {
        String asString = contentValues.getAsString("mime");
        return asString != null && asString.equalsIgnoreCase("application/vnd.google-apps.folder");
    }

    @Override // com.artifex.sonui.AppFile
    public String a(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppFile.a(context));
        String str2 = File.separator;
        sb.append(str2);
        sb.append("GoogleDrive-");
        sb.append(this.l);
        sb.append("-");
        String strQ = e6.q(sb, this.k, str2, str);
        if (this.n == null) {
            return strQ;
        }
        StringBuilder sbT = defpackage.h.t(strQ, ".");
        sbT.append(this.n);
        return sbT.toString();
    }

    @Override // com.artifex.sonui.AppFile
    public boolean b(Context context) {
        String strB;
        if (this.n == null) {
            strB = b();
        } else {
            strB = b() + "." + this.n;
        }
        return Utilities.isDocTypeSupported(context, strB);
    }

    private void a(final AppFile.b bVar) {
        o = BaseActivity.getCurrentActivity();
        BaseActivity.setResultHandler(new BaseActivity.ResultHandler() { // from class: com.artifex.sonui.AppFileGoogleDrive.1
            @Override // com.artifex.sonui.editor.BaseActivity.ResultHandler
            public boolean handle(int i, int i2, Intent intent) {
                if (i != 1001 && i != 1002) {
                    return false;
                }
                AppFileGoogleDrive.a(i, i2, intent);
                return true;
            }
        });
        a(new a() { // from class: com.artifex.sonui.AppFileGoogleDrive.4
            @Override // com.artifex.sonui.AppFileGoogleDrive.a
            public void a() {
                BaseActivity.setResultHandler(null);
                bVar.a(0);
            }

            @Override // com.artifex.sonui.AppFileGoogleDrive.a
            public void a(Exception exc) {
                BaseActivity.setResultHandler(null);
                bVar.a(1);
            }
        });
    }

    private static void a(a aVar) {
        v = aVar;
        v();
    }
}
