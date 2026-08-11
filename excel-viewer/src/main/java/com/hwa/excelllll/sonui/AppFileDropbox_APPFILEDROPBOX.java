package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.Utilities;
import com.dropbox.core.android.AuthActivity;
import com.dropbox.core.e.g.ad;
import com.dropbox.core.e.g.ak;
import com.dropbox.core.e.g.am;
import com.dropbox.core.e.g.an;
import com.dropbox.core.e.g.ap;
import com.dropbox.core.e.g.aq;
import com.dropbox.core.e.g.at;
import com.dropbox.core.e.g.ay;
import com.dropbox.core.e.g.z;
import com.dropbox.core.q;
import com.dropbox.core.u;
import defpackage.c8;
import defpackage.e6;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
class AppFileDropbox extends AppFile {
    private static Activity k;
    private static AppFile.b p;
    private static com.dropbox.core.e.a q;
    private static boolean r;
    private static boolean s;
    private String l;
    private String m;
    private String n;
    private AsyncTask o;

    /* JADX INFO: renamed from: com.artifex.sonui.AppFileDropbox$3, reason: invalid class name */
    public class AnonymousClass3 implements AppFile.b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f4310a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ AppFile.AppFileListener f4311b;

        public AnonymousClass3(Context context, AppFile.AppFileListener appFileListener) {
            this.f4310a = context;
            this.f4311b = appFileListener;
        }

        @Override // com.artifex.sonui.AppFile.b
        public void a(int i) {
            if (i != 0) {
                AppFile.f(defpackage.h.g(i, "copyFromRemote error, code = "));
                AppFileDropbox.this.k();
                this.f4311b.a(AppFile.a.Fail);
            } else {
                AppFileDropbox appFileDropbox = AppFileDropbox.this;
                Context context = this.f4310a;
                appFileDropbox.a(context, context.getString(Res.j("sodk_editor_downloading")), new AppFile.c() { // from class: com.artifex.sonui.AppFileDropbox.3.1
                    @Override // com.artifex.sonui.AppFile.c
                    public void a() {
                        AppFileDropbox.this.o.cancel(true);
                    }
                }, 1, true);
                AppFileDropbox.this.o = new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileDropbox.3.2

                    /* JADX INFO: renamed from: a, reason: collision with root package name */
                    boolean f4313a = false;

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Void doInBackground(Void... voidArr) {
                        try {
                            String strA = AppFileDropbox.this.a(AppFileDropbox.k, AppFileDropbox.this.f4253b);
                            File file = new File(strA);
                            file.getParentFile().mkdirs();
                            if (!file.exists() && !file.createNewFile()) {
                                throw new IOException("copyFromRemote: can't create file");
                            }
                            com.dropbox.core.g gVarF = AppFileDropbox.q.f5689a.f(AppFileDropbox.this.l);
                            gVarF.a(new AppFile.d(((com.dropbox.core.e.g.k) gVarF.f5876a).i, new FileOutputStream(new File(strA)), new AppFile.d.a() { // from class: com.artifex.sonui.AppFileDropbox.3.2.1
                                @Override // com.artifex.sonui.AppFile.d.a
                                public void a(long j, long j2) {
                                    ProgressDialog progressDialog = AppFileDropbox.this.j;
                                    if (progressDialog != null) {
                                        progressDialog.setMax((int) j2);
                                        AppFileDropbox.this.j.setProgress((int) j);
                                    }
                                }
                            }));
                            AppFileDropbox.this.c = strA;
                            this.f4313a = true;
                            return null;
                        } catch (com.dropbox.core.e.g.j | com.dropbox.core.h | IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override // android.os.AsyncTask
                    public void onCancelled() {
                        AnonymousClass3.this.f4311b.a(AppFile.a.Cancel);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onPostExecute(Void r1) {
                        super.onPostExecute(r1);
                        AppFileDropbox.this.k();
                        AnonymousClass3.this.f4311b.a(this.f4313a ? AppFile.a.Success : AppFile.a.Fail);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onProgressUpdate(String... strArr) {
                        super.onProgressUpdate(strArr);
                    }
                }.execute(new Void[0]);
            }
        }
    }

    /* JADX INFO: renamed from: com.artifex.sonui.AppFileDropbox$4, reason: invalid class name */
    public class AnonymousClass4 implements AppFile.b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f4316a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ AppFile.AppFileListener f4317b;

        public AnonymousClass4(Context context, AppFile.AppFileListener appFileListener) {
            this.f4316a = context;
            this.f4317b = appFileListener;
        }

        @Override // com.artifex.sonui.AppFile.b
        public void a(int i) {
            if (i != 0) {
                AppFile.f(String.format("AppFileDropbox copyToRemote connection failed %d", Integer.valueOf(i)));
                this.f4317b.a(AppFile.a.Fail);
                return;
            }
            AppFileDropbox appFileDropbox = AppFileDropbox.this;
            Context context = this.f4316a;
            appFileDropbox.a(context, context.getString(Res.j("sodk_editor_uploading")), new AppFile.c() { // from class: com.artifex.sonui.AppFileDropbox.4.1
                @Override // com.artifex.sonui.AppFile.c
                public void a() {
                    AppFileDropbox.this.o.cancel(true);
                }
            }, 1, false);
            AppFileDropbox.this.o = new AsyncTask<Void, Integer, Void>() { // from class: com.artifex.sonui.AppFileDropbox.4.2

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                boolean f4319a = false;

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public Void doInBackground(Void... voidArr) {
                    try {
                        if (!new File(AppFileDropbox.this.c).exists()) {
                            throw new IOException();
                        }
                        ArrayList arrayList = new ArrayList();
                        AppFileDropbox appFileDropbox2 = AppFileDropbox.this;
                        appFileDropbox2.a(appFileDropbox2.m, AppFileDropbox.this.f4253b, (ArrayList<AppFile>) arrayList);
                        AppFileDropbox.b(AppFileDropbox.q, new File(AppFileDropbox.this.c), AppFileDropbox.this.m + AppFileDropbox.this.f4253b, new a() { // from class: com.artifex.sonui.AppFileDropbox.4.2.1
                            @Override // com.artifex.sonui.AppFileDropbox.a
                            public void a(long j, long j2) {
                                ProgressDialog progressDialog = AppFileDropbox.this.j;
                                if (progressDialog != null) {
                                    progressDialog.setMax((int) j2);
                                    AppFileDropbox.this.j.setProgress((int) j);
                                }
                            }
                        });
                        this.f4319a = true;
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
                public void onCancelled() {
                    boolean unused = AppFileDropbox.s = true;
                    AnonymousClass4.this.f4317b.a(AppFile.a.Cancel);
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onPostExecute(Void r1) {
                    super.onPostExecute(r1);
                    AppFileDropbox.this.k();
                    AnonymousClass4.this.f4317b.a(this.f4319a ? AppFile.a.Success : AppFile.a.Fail);
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onProgressUpdate(Integer... numArr) {
                    AppFileDropbox.this.j.setProgress(numArr[0].intValue());
                    super.onProgressUpdate(numArr);
                }
            }.execute(new Void[0]);
        }
    }

    public interface a {
        void a(long j, long j2);
    }

    public AppFileDropbox(String str, String str2, boolean z, boolean z2) {
        this.f4252a = 4;
        g(str);
        this.f4253b = str2;
        this.d = z;
        this.h = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, ArrayList<AppFile> arrayList) {
        String strD = str2 != null ? defpackage.h.D(str, str2) : str;
        if (strD.equals("/")) {
            strD = "";
        }
        try {
            for (z zVar : q.f5689a.h(strD).f5855a) {
                AppFileDropbox appFileDropbox = new AppFileDropbox();
                appFileDropbox.f4253b = zVar.a();
                appFileDropbox.l = str + zVar.a();
                appFileDropbox.m = this.l;
                appFileDropbox.g = appFileDropbox.g();
                appFileDropbox.n = "";
                appFileDropbox.e = 0L;
                appFileDropbox.f = 0L;
                if (zVar instanceof com.dropbox.core.e.g.k) {
                    com.dropbox.core.e.g.k kVar = (com.dropbox.core.e.g.k) zVar;
                    appFileDropbox.e = kVar.i;
                    appFileDropbox.f = kVar.g.getTime();
                    appFileDropbox.d = false;
                } else {
                    appFileDropbox.d = true;
                    appFileDropbox.l += "/";
                }
                arrayList.add(appFileDropbox);
            }
        } catch (com.dropbox.core.h e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:100:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:136:0x01b3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:137:0x01a7 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v26, types: [com.dropbox.core.u] */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v32, types: [com.dropbox.core.u] */
    /* JADX WARN: Type inference failed for: r0v33, types: [com.dropbox.core.q] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.dropbox.core.u] */
    /* JADX WARN: Type inference failed for: r0v50, types: [com.dropbox.core.u] */
    /* JADX WARN: Type inference failed for: r0v51, types: [com.dropbox.core.q] */
    /* JADX WARN: Type inference failed for: r0v59 */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.dropbox.core.q] */
    /* JADX WARN: Type inference failed for: r0v60 */
    /* JADX WARN: Type inference failed for: r0v61 */
    public static void b(com.dropbox.core.e.a aVar, File file, String str, a aVar2) {
        long j;
        ap apVarA;
        am amVar;
        Throwable th;
        PrintStream printStream;
        ap.b bVar = ap.b.f5781b;
        s = false;
        long length = file.length();
        long j2 = length < 5242880 ? length / 5 : 1048576L;
        long j3 = 0;
        String str2 = null;
        int i = 0;
        ?? r0 = 0;
        while (i < 5) {
            if (i > 0) {
                System.out.printf("Retrying chunked upload (%d / %d attempts)\n", Integer.valueOf(i + 1), 5);
            }
            try {
                try {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            fileInputStream.skip(j3);
                            if (str2 == null) {
                                try {
                                    str2 = ((at) aVar.f5689a.c().c(fileInputStream, j2)).f5787a;
                                    j3 += j2;
                                    aVar2.a(j3, length);
                                } catch (Throwable th2) {
                                    th = th2;
                                    j = length;
                                    th = th;
                                    try {
                                        throw th;
                                    } catch (Throwable th3) {
                                        try {
                                            fileInputStream.close();
                                        } catch (Throwable th4) {
                                            try {
                                                th.addSuppressed(th4);
                                            } catch (q e) {
                                                e = e;
                                                i++;
                                                length = j;
                                                r0 = e;
                                            } catch (u e2) {
                                                e = e2;
                                                a(e.f5889a);
                                                i++;
                                                length = j;
                                                r0 = e;
                                            }
                                        }
                                        throw th3;
                                    }
                                }
                            }
                            if (s) {
                                printStream = System.err;
                            } else {
                                ak akVar = new ak(str2, j3);
                                do {
                                    long j4 = j3;
                                    long j5 = length - j4;
                                    if (j5 > j2) {
                                        try {
                                            aVar.f5689a.a(akVar).c(fileInputStream, j2);
                                            j3 = j4 + j2;
                                            aVar2.a(j3, length);
                                            akVar = new ak(str2, j3);
                                        } catch (Throwable th5) {
                                            th = th5;
                                            j3 = j4;
                                            j = length;
                                            th = th;
                                            throw th;
                                        }
                                    } else {
                                        try {
                                            com.dropbox.core.e.g.a.C0078a c0078a = new com.dropbox.core.e.g.a.C0078a(str);
                                            ay ayVar = ay.d;
                                            if (ayVar == null) {
                                                ayVar = ay.c;
                                            }
                                            c0078a.f5740b = ayVar;
                                            j = length;
                                            try {
                                                System.out.println(((com.dropbox.core.e.g.k) aVar.f5689a.b(akVar, new com.dropbox.core.e.g.a(c0078a.f5739a, c0078a.f5740b, false, com.dropbox.core.d.b.b(new Date(file.lastModified())), false, null, false)).c(fileInputStream, j5)).b());
                                                try {
                                                    fileInputStream.close();
                                                    return;
                                                } catch (q e3) {
                                                    e = e3;
                                                    j3 = j4;
                                                    i++;
                                                    length = j;
                                                    r0 = e;
                                                } catch (u e4) {
                                                    e = e4;
                                                    j3 = j4;
                                                    a(e.f5889a);
                                                    i++;
                                                    length = j;
                                                    r0 = e;
                                                }
                                            } catch (Throwable th6) {
                                                th = th6;
                                                j3 = j4;
                                                th = th;
                                                throw th;
                                            }
                                        } catch (Throwable th7) {
                                            th = th7;
                                            j = length;
                                        }
                                    }
                                } while (!s);
                                printStream = System.err;
                            }
                            printStream.println("uploading to Dropbox was cancelled.");
                            fileInputStream.close();
                            return;
                        } catch (Throwable th8) {
                            th = th8;
                        }
                    } catch (an e5) {
                        e = e5;
                        j = length;
                        amVar = e.f5775a;
                        if (amVar.f5769a == am.b.f5773a) {
                        }
                        System.err.println("Error uploading to Dropbox: " + e.getMessage());
                        return;
                    } catch (aq e6) {
                        e = e6;
                        j = length;
                        apVarA = e.f5782a;
                        e = e;
                        if (apVarA.f5776a != bVar) {
                            System.err.println("Error uploading to Dropbox: " + e.getMessage());
                            return;
                        }
                        if (apVarA.f5776a != bVar) {
                            c8.b(apVarA.f5776a.name(), "Invalid tag: required Tag.INCORRECT_OFFSET, but was Tag.");
                            return;
                        }
                        j3 = apVarA.f5777b.f5783a;
                    } catch (q e7) {
                        e = e7;
                        j = length;
                    } catch (u e8) {
                        e = e8;
                        j = length;
                    }
                } catch (com.dropbox.core.h e9) {
                    System.err.println("Error uploading to Dropbox: " + e9.getMessage());
                    return;
                } catch (IOException e10) {
                    System.err.println("Error reading from file \"" + file + "\": " + e10.getMessage());
                    return;
                }
            } catch (an e11) {
                e = e11;
                amVar = e.f5775a;
                if (amVar.f5769a == am.b.f5773a || amVar.a().f5776a != bVar) {
                    System.err.println("Error uploading to Dropbox: " + e.getMessage());
                    return;
                }
                apVarA = amVar.a();
                e = e;
                if (apVarA.f5776a != bVar) {
                    c8.b(apVarA.f5776a.name(), "Invalid tag: required Tag.INCORRECT_OFFSET, but was Tag.");
                    return;
                }
                j3 = apVarA.f5777b.f5783a;
                i++;
                length = j;
                r0 = e;
            } catch (aq e12) {
                e = e12;
                apVarA = e.f5782a;
                e = e;
                if (apVarA.f5776a != bVar) {
                    System.err.println("Error uploading to Dropbox: " + e.getMessage());
                    return;
                }
                if (apVarA.f5776a != bVar) {
                    c8.b(apVarA.f5776a.name(), "Invalid tag: required Tag.INCORRECT_OFFSET, but was Tag.");
                    return;
                }
                j3 = apVarA.f5777b.f5783a;
                i++;
                length = j;
                r0 = e;
            }
        }
        System.err.println("Maxed out upload attempts to Dropbox. Most recent error: " + r0.getMessage());
    }

    private static void h(String str) {
        if (q == null) {
            q = new com.dropbox.core.e.a(new com.dropbox.core.j(com.dropbox.core.a.c.f5644b), str);
        }
    }

    private static void i(String str) {
        Utilities.setStringPreference(Utilities.getPreferencesObject(k, "com.artifex.sonui.dropboxintegration"), "access-token", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:6:0x000b  */
    public static void q() {
        String stringExtra;
        if (!r) {
            BaseActivity.setResumeHandler(null);
            p.a(2);
            p = null;
            r = false;
            return;
        }
        Intent intent = AuthActivity.f;
        if (intent == null) {
            stringExtra = null;
        } else {
            String stringExtra2 = intent.getStringExtra("ACCESS_TOKEN");
            stringExtra = intent.getStringExtra("ACCESS_SECRET");
            String stringExtra3 = intent.getStringExtra("UID");
            if (stringExtra2 == null || stringExtra2.equals("") || stringExtra == null || stringExtra.equals("") || stringExtra3 == null || stringExtra3.equals("")) {
                stringExtra = null;
            }
        }
        if (stringExtra == null || stringExtra.isEmpty()) {
            p.a(2);
        } else {
            i(stringExtra);
            h(stringExtra);
            p.a(0);
        }
        r = false;
        BaseActivity.setResumeHandler(null);
        p = null;
    }

    private static String r() {
        return Utilities.getStringPreference(Utilities.getPreferencesObject(k, "com.artifex.sonui.dropboxintegration"), "access-token", null);
    }

    @Override // com.artifex.sonui.AppFile
    public void Logout(AppFile.LogoutListener logoutListener) {
        k = BaseActivity.getCurrentActivity();
        i(null);
        logoutListener.a();
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile child(String str) {
        if (!this.d) {
            return null;
        }
        AppFileDropbox appFileDropbox = new AppFileDropbox();
        appFileDropbox.f4253b = str;
        appFileDropbox.m = this.l;
        appFileDropbox.g = null;
        appFileDropbox.c = null;
        return appFileDropbox;
    }

    @Override // com.artifex.sonui.AppFile
    public void copyFromRemote(Context context, AppFile.AppFileListener appFileListener) {
        a(new AnonymousClass3(context, appFileListener));
    }

    @Override // com.artifex.sonui.AppFile
    public void copyToRemote(Context context, AppFile.AppFileListener appFileListener) {
        a(new AnonymousClass4(context, appFileListener));
    }

    @Override // com.artifex.sonui.AppFile
    public void deleteFile(final Context context, final AppFile.AppFileListener appFileListener) {
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileDropbox.2
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i != 0) {
                    AppFile.f(defpackage.h.g(i, "deleteFile error, code = "));
                    AppFileDropbox.this.k();
                    appFileListener.a(AppFile.a.Fail);
                } else {
                    AppFileDropbox appFileDropbox = AppFileDropbox.this;
                    Context context2 = context;
                    appFileDropbox.a(context2, context2.getString(Res.j("sodk_editor_deleting")), new AppFile.c() { // from class: com.artifex.sonui.AppFileDropbox.2.1
                        @Override // com.artifex.sonui.AppFile.c
                        public void a() {
                            AppFileDropbox.this.o.cancel(true);
                        }
                    });
                    AppFileDropbox.this.o = new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileDropbox.2.2

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4308a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            try {
                                AppFileDropbox.q.f5689a.d(AppFileDropbox.this.l);
                                this.f4308a = true;
                                return null;
                            } catch (com.dropbox.core.h e) {
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
                            AppFileDropbox.this.k();
                            appFileListener.a(this.f4308a ? AppFile.a.Success : AppFile.a.Fail);
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onProgressUpdate(String... strArr) {
                            super.onProgressUpdate(strArr);
                        }
                    }.execute(new Void[0]);
                }
            }
        });
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile duplicate() {
        AppFileDropbox appFileDropbox = (AppFileDropbox) AppFile.b(this);
        appFileDropbox.l = this.l;
        appFileDropbox.m = this.m;
        appFileDropbox.n = this.n;
        return appFileDropbox;
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile enumerateDir(AppFile.EnumerateListener enumerateListener) {
        AppFile.i = enumerateListener;
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileDropbox.1
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                final ArrayList arrayList = new ArrayList();
                if (i == 0) {
                    new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileDropbox.1.1

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4303a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r2) {
                            ArrayList<AppFile> arrayList2;
                            AppFile.EnumerateListener enumerateListener2;
                            super.onPostExecute(r2);
                            if (this.f4303a) {
                                AppFile.EnumerateListener enumerateListener3 = AppFile.i;
                                if (enumerateListener3 == null) {
                                    return;
                                }
                                arrayList2 = arrayList;
                                enumerateListener2 = enumerateListener3;
                            } else {
                                enumerateListener2 = AppFile.i;
                                if (enumerateListener2 == null) {
                                    return;
                                } else {
                                    arrayList2 = null;
                                }
                            }
                            enumerateListener2.a(arrayList2);
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            AppFileDropbox appFileDropbox = AppFileDropbox.this;
                            appFileDropbox.a(appFileDropbox.l, (String) null, (ArrayList<AppFile>) arrayList);
                            this.f4303a = true;
                            return null;
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onProgressUpdate(String... strArr) {
                            super.onProgressUpdate(strArr);
                        }
                    }.execute(new Void[0]);
                    return;
                }
                AppFile.f(defpackage.h.g(i, "enumerateDir error, code = "));
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
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileDropbox.6
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i == 0) {
                    new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileDropbox.6.1

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4329a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            try {
                                if (AppFileDropbox.q.f5689a.g(AppFileDropbox.this.m + AppFileDropbox.this.f4253b) == null) {
                                    return null;
                                }
                                this.f4329a = true;
                                return null;
                            } catch (com.dropbox.core.h e) {
                                e.printStackTrace();
                                return null;
                            }
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r1) {
                            super.onPostExecute(r1);
                            existsListener.a(this.f4329a);
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onProgressUpdate(String... strArr) {
                            super.onProgressUpdate(strArr);
                        }
                    }.execute(new Void[0]);
                } else {
                    AppFile.f(defpackage.h.g(i, "exists error, code = "));
                    existsListener.a(false);
                }
            }
        });
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile fromString(String str) {
        AppFileDropbox appFileDropbox = (AppFileDropbox) AppFile.d(str);
        String[] strArrSplit = str.split("\\|");
        appFileDropbox.l = AppFile.c(strArrSplit[7]);
        appFileDropbox.m = AppFile.c(strArrSplit[8]);
        appFileDropbox.n = AppFile.c(strArrSplit[9]);
        return appFileDropbox;
    }

    @Override // com.artifex.sonui.AppFile
    public String g() {
        StringBuilder sbT;
        StringBuilder sbT2;
        StringBuilder sbT3;
        String str = "Dropbox/" + this.f4253b;
        if (this.l == null) {
            sbT = defpackage.h.t(str, "|null");
        } else {
            sbT = defpackage.h.t(str, "|");
            sbT.append(this.l);
        }
        String string = sbT.toString();
        if (this.m == null) {
            sbT2 = defpackage.h.t(string, "|null");
        } else {
            sbT2 = defpackage.h.t(string, "|");
            sbT2.append(this.m);
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
        return Res.f(this.l.equalsIgnoreCase("/") ? "sodk_icon_cloud_dropbox" : "sodk_editor_icon_folder");
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isCloud() {
        return true;
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isSameAs(AppFile appFile) {
        return appFile != null && a() == appFile.a() && this.l.compareTo(((AppFileDropbox) appFile).l) == 0;
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
            a(new AppFile.b() { // from class: com.artifex.sonui.AppFileDropbox.5
                @Override // com.artifex.sonui.AppFile.b
                public void a(int i) {
                    if (i != 0) {
                        AppFile.f(defpackage.h.g(i, "rename error, code = "));
                        AppFileDropbox.this.k();
                        appFileListener.a(AppFile.a.Fail);
                    } else {
                        AppFileDropbox appFileDropbox = AppFileDropbox.this;
                        Context context2 = context;
                        appFileDropbox.a(context2, context2.getString(Res.j("sodk_editor_renaming")), new AppFile.c() { // from class: com.artifex.sonui.AppFileDropbox.5.1
                            @Override // com.artifex.sonui.AppFile.c
                            public void a() {
                                AppFileDropbox.this.o.cancel(true);
                            }
                        });
                        AppFileDropbox.this.o = new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileDropbox.5.2

                            /* JADX INFO: renamed from: a, reason: collision with root package name */
                            boolean f4325a = false;

                            @Override // android.os.AsyncTask
                            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                            public Void doInBackground(Void... voidArr) {
                                try {
                                    AppFileDropbox.q.f5689a.e(AppFileDropbox.this.l, AppFileDropbox.this.m + str);
                                    this.f4325a = true;
                                    return null;
                                } catch (ad | com.dropbox.core.h e) {
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
                                AppFileDropbox.this.k();
                                appFileListener.a(this.f4325a ? AppFile.a.Success : AppFile.a.Fail);
                            }

                            @Override // android.os.AsyncTask
                            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                            public void onProgressUpdate(String... strArr) {
                                super.onProgressUpdate(strArr);
                            }
                        }.execute(new Void[0]);
                    }
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
        return defpackage.h.p(e6.t(defpackage.h.p(e6.t(defpackage.h.p(e6.t(AppFile.a(this)), AppFile.b(this.l), "|")), AppFile.b(this.m), "|")), AppFile.b(this.n), "|");
    }

    public AppFileDropbox() {
        this.f4252a = 4;
    }

    private void g(String str) {
        this.f4253b = null;
        this.d = false;
        this.c = null;
        this.g = null;
        this.e = 0L;
        this.f = 0L;
        this.l = str;
        this.m = null;
        this.n = null;
    }

    @Override // com.artifex.sonui.AppFile
    public String a(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppFile.a(context));
        String str2 = File.separator;
        sb.append(str2);
        sb.append("Dropbox-");
        sb.append(this.m);
        sb.append("-");
        return e6.q(sb, this.l, str2, str);
    }

    private static void a(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
            System.err.println("Error uploading to Dropbox: interrupted during backoff.");
            System.exit(1);
        }
    }

    private void a(AppFile.b bVar) {
        k = BaseActivity.getCurrentActivity();
        BaseActivity.setResumeHandler(new BaseActivity.ResumeHandler() { // from class: com.artifex.sonui.AppFileDropbox.7
            @Override // com.artifex.sonui.editor.BaseActivity.ResumeHandler
            public void handle() {
                AppFileDropbox.q();
            }
        });
        String strR = r();
        if (strR == null) {
            com.dropbox.core.android.a.a(k);
            p = bVar;
            r = true;
        } else {
            h(strR);
            BaseActivity.setResumeHandler(null);
            bVar.a(0);
        }
    }
}
