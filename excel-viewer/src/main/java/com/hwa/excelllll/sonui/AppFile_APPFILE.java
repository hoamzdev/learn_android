package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.supportv1.v4.app.ActivityCompat;
import android.supportv1.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.Utilities;
import defpackage.e6;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public abstract class AppFile {
    static EnumerateListener i;
    private static CloudPermissionChecked k;
    private static boolean l;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f4252a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    String f4253b;
    String c;
    boolean d;
    long e;
    long f;
    String g;
    boolean h = false;
    protected ProgressDialog j;

    public interface AppFileListener {
        void a(a aVar);
    }

    public interface CloudPermissionChecked {
        void a(boolean z);
    }

    public interface EnumerateListener {
        void a(ArrayList<AppFile> arrayList);
    }

    public interface ExistsListener {
        void a(boolean z);
    }

    public interface LogoutListener {
        void a();
    }

    public enum a {
        Success,
        Fail,
        Cancel
    }

    public interface b {
        void a(int i);
    }

    public interface c {
        void a();
    }

    public static String a(AppFile appFile) {
        StringBuilder sbT = e6.t(defpackage.h.p(e6.t(defpackage.h.p(e6.t(defpackage.h.p(e6.t(defpackage.h.p(e6.t("" + String.valueOf(appFile.f4252a) + "|"), b(appFile.c), "|")), b(appFile.g), "|")), b(appFile.f4253b), "|")), appFile.d ? "TRUE" : "FALSE", "|"));
        sbT.append(String.valueOf(appFile.e));
        sbT.append("|");
        StringBuilder sbT2 = e6.t(sbT.toString());
        sbT2.append(String.valueOf(appFile.f));
        sbT2.append("|");
        return sbT2.toString();
    }

    public static String b(String str) {
        if (str == null) {
            return "--null--";
        }
        if (str.isEmpty()) {
            return "--empty--";
        }
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), 0);
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static String c(String str) {
        if (str.equals("--null--")) {
            return null;
        }
        if (str.equals("--empty--")) {
            return "";
        }
        try {
            return new String(Base64.decode(str, 0), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static void checkCloudPermission(final BaseActivity baseActivity, final AppFile appFile, final CloudPermissionChecked cloudPermissionChecked) {
        if (!com.artifex.sonui.c.a("AppFileGoogleDrive", appFile) || ContextCompat.a(baseActivity, "android.permission.GET_ACCOUNTS") == 0) {
            cloudPermissionChecked.a(true);
            return;
        }
        BaseActivity.setResumeHandler(new BaseActivity.ResumeHandler() { // from class: com.artifex.sonui.AppFile.3
            @Override // com.artifex.sonui.editor.BaseActivity.ResumeHandler
            public void handle() {
                Handler handler;
                Runnable runnable;
                BaseActivity.setResumeHandler(null);
                if (AppFile.l) {
                    if (AppFile.k == null) {
                        return;
                    }
                    handler = new Handler();
                    runnable = new Runnable() { // from class: com.artifex.sonui.AppFile.3.4
                        @Override // java.lang.Runnable
                        public void run() {
                            AppFile.k.a(AppFile.l);
                            CloudPermissionChecked unused = AppFile.k = null;
                        }
                    };
                } else if (baseActivity.shouldShowRequestPermissionRationale("android.permission.GET_ACCOUNTS")) {
                    BaseActivity baseActivity2 = baseActivity;
                    Utilities.yesNoMessage(baseActivity2, baseActivity2.getString(Res.j("sodk_editor_permission_denied")), baseActivity.getString(Res.j("sodk_editor_permission_google_why")), baseActivity.getString(Res.j("sodk_editor_yes")), baseActivity.getString(Res.j("sodk_editor_no")), new Runnable() { // from class: com.artifex.sonui.AppFile.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                            AppFile.checkCloudPermission(baseActivity, appFile, cloudPermissionChecked);
                        }
                    }, new Runnable() { // from class: com.artifex.sonui.AppFile.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            BaseActivity baseActivity3 = baseActivity;
                            Utilities.showMessageAndWait(baseActivity3, baseActivity3.getString(Res.j("sodk_editor_permission_denied")), baseActivity.getString(Res.j("sodk_editor_permission_google_final")), new Runnable() { // from class: com.artifex.sonui.AppFile.3.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (AppFile.k != null) {
                                        new Handler().post(new Runnable() { // from class: com.artifex.sonui.AppFile.3.2.1.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                AppFile.k.a(false);
                                                CloudPermissionChecked unused = AppFile.k = null;
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                    return;
                } else {
                    if (AppFile.k == null) {
                        return;
                    }
                    handler = new Handler();
                    runnable = new Runnable() { // from class: com.artifex.sonui.AppFile.3.3
                        @Override // java.lang.Runnable
                        public void run() {
                            AppFile.k.a(false);
                            CloudPermissionChecked unused = AppFile.k = null;
                        }
                    };
                }
                handler.post(runnable);
            }
        });
        BaseActivity.setPermissionResultHandler(new BaseActivity.PermissionResultHandler() { // from class: com.artifex.sonui.AppFile.4
            @Override // com.artifex.sonui.editor.BaseActivity.PermissionResultHandler
            public boolean handle(int i2, String[] strArr, int[] iArr) {
                BaseActivity.setPermissionResultHandler(null);
                if (i2 != 2) {
                    return false;
                }
                boolean unused = AppFile.l = false;
                if (iArr.length > 0 && iArr[0] == 0) {
                    boolean unused2 = AppFile.l = true;
                }
                return true;
            }
        });
        k = cloudPermissionChecked;
        ActivityCompat.b(baseActivity, new String[]{"android.permission.GET_ACCOUNTS"}, 2);
    }

    public static AppFile d(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] strArrSplit = str.split("\\|");
        AppFile appFileA = a(Integer.parseInt(strArrSplit[0]));
        if (appFileA == null) {
            return null;
        }
        appFileA.c = c(strArrSplit[1]);
        appFileA.g = c(strArrSplit[2]);
        appFileA.f4253b = c(strArrSplit[3]);
        appFileA.d = strArrSplit[4].equals("TRUE");
        appFileA.e = Long.parseLong(strArrSplit[5], 10);
        appFileA.f = Long.parseLong(strArrSplit[6], 10);
        return appFileA;
    }

    public static AppFile e(String str) {
        return d(str).fromString(str);
    }

    public static void f(String str) {
        Log.d("sonui", str);
    }

    public static boolean i() {
        InetAddress inetAddress;
        try {
            Future futureSubmit = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() { // from class: com.artifex.sonui.AppFile.1
                @Override // java.util.concurrent.Callable
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException unused) {
                        return null;
                    }
                }
            });
            inetAddress = (InetAddress) futureSubmit.get(3000L, TimeUnit.MILLISECONDS);
            try {
                futureSubmit.cancel(true);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e = e2;
            inetAddress = null;
        }
        return (inetAddress == null || inetAddress.equals("")) ? false : true;
    }

    public abstract void Logout(LogoutListener logoutListener);

    public abstract AppFile child(String str);

    public abstract void copyFromRemote(Context context, AppFileListener appFileListener);

    public abstract void copyToRemote(Context context, AppFileListener appFileListener);

    public abstract void deleteFile(Context context, AppFileListener appFileListener);

    public abstract AppFile duplicate();

    public abstract AppFile enumerateDir(EnumerateListener enumerateListener);

    public abstract void exists(ExistsListener existsListener);

    public abstract AppFile fromString(String str);

    public String g() {
        return this.g;
    }

    public abstract String getDisplayPath();

    public abstract int getFolderResourceId();

    public boolean h() {
        return this.h;
    }

    public abstract boolean isCloud();

    public abstract boolean isSameAs(AppFile appFile);

    public int j() {
        return Utilities.iconForDocType(b());
    }

    public void k() {
        this.j.dismiss();
    }

    public abstract void rename(String str, Context context, AppFileListener appFileListener);

    public abstract boolean serviceAvailable();

    public abstract String toString();

    public long f() {
        return this.f;
    }

    public static class d extends OutputStream {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private OutputStream f4268a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private a f4269b;
        private long c = 0;
        private long d;

        public interface a {
            void a(long j, long j2);
        }

        public d(long j, OutputStream outputStream, a aVar) {
            this.f4268a = outputStream;
            this.f4269b = aVar;
            this.d = j;
        }

        private void a(int i) {
            long j = this.c + ((long) i);
            this.c = j;
            this.f4269b.a(j, this.d);
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            try {
                this.f4268a.write(i);
            } catch (IOException unused) {
            }
            a(1);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.f4268a.write(bArr);
            a(bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.f4268a.write(bArr, i, i2);
            a(i2);
        }
    }

    public long e() {
        return this.e;
    }

    public String b() {
        return this.f4253b;
    }

    public static AppFile b(AppFile appFile) {
        return d(a(appFile));
    }

    public boolean b(Context context) {
        return Utilities.isDocTypeSupported(context, b());
    }

    public String c() {
        return this.c;
    }

    public int c(Context context) {
        return Utilities.colorForDocType(context, b());
    }

    public static void d(Context context) {
        File[] fileArrListFiles = new File(a(context)).listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                a(file);
            }
        }
    }

    public boolean d() {
        return this.d;
    }

    private static AppFile a(int i2) {
        AppFile bVar = i2 == 1 ? new com.artifex.sonui.b() : null;
        if (i2 == 2) {
            bVar = com.artifex.sonui.c.a();
        }
        if (i2 == 3) {
            bVar = com.artifex.sonui.c.b();
        }
        return i2 == 4 ? com.artifex.sonui.c.c() : bVar;
    }

    public static String a(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getExternalCacheDir());
        return defpackage.h.p(sb, File.separator, "cloud");
    }

    public String a(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(a(context));
        String str2 = File.separator;
        sb.append(str2);
        sb.append(UUID.randomUUID());
        sb.append(str2);
        sb.append(str);
        return sb.toString();
    }

    public int a() {
        return this.f4252a;
    }

    public static void a(Activity activity, final b bVar, final int i2) {
        activity.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.AppFile.2
            @Override // java.lang.Runnable
            public void run() {
                bVar.a(i2);
            }
        });
    }

    public void a(Context context, String str, c cVar) {
        a(context, str, cVar, 0, true);
    }

    public void a(Context context, String str, final c cVar, int i2, boolean z) {
        ProgressDialog progressDialog = new ProgressDialog(context, Res.k("sodk_dialog_style"));
        this.j = progressDialog;
        progressDialog.setMessage(str);
        this.j.setProgressStyle(i2);
        this.j.setCancelable(false);
        this.j.setProgressNumberFormat("");
        if (z) {
            ProgressDialog progressDialog2 = this.j;
            progressDialog2.setButton(-2, progressDialog2.getContext().getString(Res.j("sodk_editor_cancel")), new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.AppFile.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i3) {
                    dialogInterface.dismiss();
                    cVar.a();
                }
            });
        }
        this.j.show();
        Button button = this.j.getButton(-2);
        button.setBackground(null);
        button.setTextSize(16.0f);
    }

    private static void a(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                a(file2);
            }
        }
        file.delete();
    }

    public void a(String str) {
        this.c = str;
    }

    public static void a(Throwable th) {
        Log.e("sonui", Log.getStackTraceString(th));
    }
}
