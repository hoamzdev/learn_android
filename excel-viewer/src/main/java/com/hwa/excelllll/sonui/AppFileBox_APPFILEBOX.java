package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.Utilities;
import com.box.androidsdk.content.BoxException;
import com.box.androidsdk.content.auth.BoxAuthentication;
import com.box.androidsdk.content.models.BoxFile;
import com.box.androidsdk.content.models.BoxFolder;
import com.box.androidsdk.content.models.BoxItem;
import com.box.androidsdk.content.models.BoxIteratorItems;
import com.box.androidsdk.content.models.BoxSession;
import com.box.androidsdk.content.models.BoxVoid;
import com.box.androidsdk.content.requests.BoxRequest;
import com.box.androidsdk.content.requests.BoxRequestUpload;
import com.box.androidsdk.content.requests.BoxRequestsFile;
import com.box.androidsdk.content.requests.BoxRequestsFolder;
import defpackage.a7;
import defpackage.e6;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
class AppFileBox extends AppFile {
    private static Activity k;
    private String l;
    private String m;
    private String n;
    private AsyncTask o;
    private BoxSession p = null;
    private com.box.androidsdk.content.c q;
    private com.box.androidsdk.content.b r;
    private com.box.androidsdk.content.d s;

    /* JADX INFO: renamed from: com.artifex.sonui.AppFileBox$3, reason: invalid class name */
    public class AnonymousClass3 implements AppFile.b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f4278a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ AppFile.AppFileListener f4279b;

        public AnonymousClass3(Context context, AppFile.AppFileListener appFileListener) {
            this.f4278a = context;
            this.f4279b = appFileListener;
        }

        @Override // com.artifex.sonui.AppFile.b
        public void a(int i) {
            if (i != 0) {
                AppFile.f(defpackage.h.g(i, "copyFromRemote error, code = "));
                AppFileBox.this.k();
                this.f4279b.a(AppFile.a.Fail);
            } else {
                AppFileBox appFileBox = AppFileBox.this;
                Context context = this.f4278a;
                appFileBox.a(context, context.getString(Res.j("sodk_editor_downloading")), new AppFile.c() { // from class: com.artifex.sonui.AppFileBox.3.1
                    @Override // com.artifex.sonui.AppFile.c
                    public void a() {
                        AppFileBox.this.o.cancel(true);
                    }
                }, 1, true);
                AppFileBox.this.o = new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileBox.3.2

                    /* JADX INFO: renamed from: a, reason: collision with root package name */
                    boolean f4281a = false;

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public Void doInBackground(Void... voidArr) {
                        try {
                            String strA = AppFileBox.this.a(AppFileBox.k, AppFileBox.this.f4253b);
                            File file = new File(strA);
                            file.getParentFile().mkdirs();
                            if (!file.exists() && !file.createNewFile()) {
                                throw new IOException("copyFromRemote: can't create file");
                            }
                            BoxRequestsFile.DownloadFile downloadFileF = AppFileBox.this.r.f(file, AppFileBox.this.l);
                            downloadFileF.f5056a = new com.box.androidsdk.content.a.b() { // from class: com.artifex.sonui.AppFileBox.3.2.1
                                @Override // com.box.androidsdk.content.a.b
                                public void a(long j, long j2) {
                                    ProgressDialog progressDialog = AppFileBox.this.j;
                                    if (progressDialog != null) {
                                        progressDialog.setMax((int) j2);
                                        AppFileBox.this.j.setProgress((int) j);
                                    }
                                }
                            };
                            AppFileBox.this.c = strA;
                            this.f4281a = true;
                            return null;
                        } catch (BoxException | IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override // android.os.AsyncTask
                    public void onCancelled() {
                        AnonymousClass3.this.f4279b.a(AppFile.a.Cancel);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onPostExecute(Void r1) {
                        super.onPostExecute(r1);
                        AppFileBox.this.k();
                        AnonymousClass3.this.f4279b.a(this.f4281a ? AppFile.a.Success : AppFile.a.Fail);
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

    /* JADX INFO: renamed from: com.artifex.sonui.AppFileBox$4, reason: invalid class name */
    public class AnonymousClass4 implements AppFile.b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f4284a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ AppFile.AppFileListener f4285b;

        public AnonymousClass4(Context context, AppFile.AppFileListener appFileListener) {
            this.f4284a = context;
            this.f4285b = appFileListener;
        }

        @Override // com.artifex.sonui.AppFile.b
        public void a(int i) {
            if (i != 0) {
                AppFile.f(String.format("AppFileBox copyToRemote connection failed %d", Integer.valueOf(i)));
                this.f4285b.a(AppFile.a.Fail);
                return;
            }
            AppFileBox appFileBox = AppFileBox.this;
            Context context = this.f4284a;
            appFileBox.a(context, context.getString(Res.j("sodk_editor_uploading")), new AppFile.c() { // from class: com.artifex.sonui.AppFileBox.4.1
                @Override // com.artifex.sonui.AppFile.c
                public void a() {
                    AppFileBox.this.o.cancel(true);
                }
            }, 1, false);
            AppFileBox.this.o = new AsyncTask<Void, Integer, Void>() { // from class: com.artifex.sonui.AppFileBox.4.2

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                boolean f4287a = false;

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public Void doInBackground(Void... voidArr) {
                    BoxRequestUpload boxRequestUploadC;
                    com.box.androidsdk.content.a.b bVar;
                    try {
                        File file = new File(AppFileBox.this.c);
                        if (!file.exists()) {
                            throw new IOException();
                        }
                        ArrayList arrayList = new ArrayList();
                        AppFileBox appFileBox2 = AppFileBox.this;
                        appFileBox2.a(appFileBox2.m, AppFileBox.this.f4253b, (ArrayList<AppFile>) arrayList);
                        if (arrayList.size() > 0) {
                            boxRequestUploadC = AppFileBox.this.r.e(file, ((AppFileBox) arrayList.get(0)).l);
                            bVar = new com.box.androidsdk.content.a.b() { // from class: com.artifex.sonui.AppFileBox.4.2.1
                                @Override // com.box.androidsdk.content.a.b
                                public void a(long j, long j2) {
                                    ProgressDialog progressDialog = AppFileBox.this.j;
                                    if (progressDialog != null) {
                                        progressDialog.setMax((int) j2);
                                        AppFileBox.this.j.setProgress((int) j);
                                    }
                                }
                            };
                        } else {
                            boxRequestUploadC = AppFileBox.this.r.c(file, AppFileBox.this.m);
                            boxRequestUploadC.o = AppFileBox.this.f4253b;
                            bVar = new com.box.androidsdk.content.a.b() { // from class: com.artifex.sonui.AppFileBox.4.2.2
                                @Override // com.box.androidsdk.content.a.b
                                public void a(long j, long j2) {
                                    ProgressDialog progressDialog = AppFileBox.this.j;
                                    if (progressDialog != null) {
                                        progressDialog.setMax((int) j2);
                                        AppFileBox.this.j.setProgress((int) j);
                                    }
                                }
                            };
                        }
                        boxRequestUploadC.f5056a = bVar;
                        boxRequestUploadC.k();
                        this.f4287a = true;
                        return null;
                    } catch (BoxException | IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
                public void onCancelled() {
                    AnonymousClass4.this.f4285b.a(AppFile.a.Cancel);
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onPostExecute(Void r1) {
                    super.onPostExecute(r1);
                    AppFileBox.this.k();
                    AnonymousClass4.this.f4285b.a(this.f4287a ? AppFile.a.Success : AppFile.a.Fail);
                }

                @Override // android.os.AsyncTask
                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onProgressUpdate(Integer... numArr) {
                    AppFileBox.this.j.setProgress(numArr[0].intValue());
                    super.onProgressUpdate(numArr);
                }
            }.execute(new Void[0]);
        }
    }

    public AppFileBox(String str, String str2, boolean z, boolean z2) {
        this.f4252a = 3;
        g(str);
        this.f4253b = str2;
        this.d = z;
        this.h = z2;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0056  */
    private void a(final AppFile.b bVar) {
        com.box.androidsdk.content.g.f5041a = "tnbrpsb37qu0olkh8ig77t6c8vrgfszs";
        com.box.androidsdk.content.g.f5042b = "GEhDHAGPlnPIm6s5Q62kBcLZi4yK4Gcq";
        k = BaseActivity.getCurrentActivity();
        Activity activity = k;
        BoxAuthentication boxAuthentication = BoxAuthentication.f;
        boxAuthentication.d.getClass();
        String string = activity.getSharedPreferences(BoxAuthentication.b.f5015a, 0).getString(BoxAuthentication.b.c, null);
        ConcurrentHashMap concurrentHashMapI = boxAuthentication.i(activity);
        if (concurrentHashMapI == null) {
            string = null;
        } else if (com.box.androidsdk.content.utils.g.g(string) || concurrentHashMapI.get(string) == null) {
            if (concurrentHashMapI.size() == 1) {
                Iterator it = concurrentHashMapI.keySet().iterator();
                if (it.hasNext()) {
                    string = (String) it.next();
                } else {
                    string = null;
                }
            } else {
                string = null;
            }
        }
        String str = com.box.androidsdk.content.g.f5041a;
        String str2 = com.box.androidsdk.content.g.f5042b;
        BoxSession boxSession = new BoxSession();
        boxSession.i = "com.box.sdk.android";
        boxSession.f5050a = com.box.androidsdk.content.g.c;
        boxSession.e = str;
        boxSession.g = str2;
        boxSession.f = "https://app.box.com/static/sync_redirect.html";
        boxSession.o();
        if (com.box.androidsdk.content.utils.g.g(str) || com.box.androidsdk.content.utils.g.g(str2)) {
            a7.t("Session must have a valid client id and client secret specified.");
            return;
        }
        boxSession.f5050a = activity.getApplicationContext();
        if (!com.box.androidsdk.content.utils.g.g(string)) {
            boxSession.d = string != null ? (BoxAuthentication.BoxAuthenticationInfo) boxAuthentication.i(activity).get(string) : null;
            boxSession.j = string;
        }
        if (boxSession.d == null) {
            boxSession.j = string;
            boxSession.d = new BoxAuthentication.BoxAuthenticationInfo();
        }
        boxSession.d.f("client_id", str);
        boxSession.l();
        this.p = boxSession;
        boxSession.f5051b = new BoxAuthentication.a() { // from class: com.artifex.sonui.AppFileBox.7
            @Override // com.box.androidsdk.content.auth.BoxAuthentication.a
            public void a(BoxAuthentication.BoxAuthenticationInfo boxAuthenticationInfo) {
                AppFile.f("auth listener: onRefreshed");
                AppFile.a(AppFileBox.k, bVar, 1);
            }

            @Override // com.box.androidsdk.content.auth.BoxAuthentication.a
            public void b(BoxAuthentication.BoxAuthenticationInfo boxAuthenticationInfo) {
                AppFile.f("auth listener: onAuthCreated");
                AppFileBox appFileBox = AppFileBox.this;
                appFileBox.q = new com.box.androidsdk.content.c(appFileBox.p);
                AppFileBox appFileBox2 = AppFileBox.this;
                appFileBox2.r = new com.box.androidsdk.content.b(appFileBox2.p);
                AppFileBox appFileBox3 = AppFileBox.this;
                appFileBox3.s = new com.box.androidsdk.content.d(appFileBox3.p);
                AppFile.a(AppFileBox.k, bVar, 0);
            }

            @Override // com.box.androidsdk.content.auth.BoxAuthentication.a
            public void a(BoxAuthentication.BoxAuthenticationInfo boxAuthenticationInfo, Exception exc) {
                AppFile.f("auth listener: onAuthFailure");
                AppFile.a(AppFileBox.k, bVar, 2);
            }

            @Override // com.box.androidsdk.content.auth.BoxAuthentication.a
            public void b(BoxAuthentication.BoxAuthenticationInfo boxAuthenticationInfo, Exception exc) {
                AppFile.f("auth listener: onLoggedOut");
                AppFile.a(AppFileBox.k, bVar, 3);
            }
        };
        this.p.k(k);
    }

    @Override // com.artifex.sonui.AppFile
    public void Logout(AppFile.LogoutListener logoutListener) {
        BoxSession boxSession = this.p;
        if (boxSession != null) {
            boxSession.p();
        }
        logoutListener.a();
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile child(String str) {
        if (!this.d) {
            return null;
        }
        AppFileBox appFileBox = new AppFileBox();
        appFileBox.f4253b = str;
        appFileBox.m = this.l;
        appFileBox.g = null;
        appFileBox.c = null;
        return appFileBox;
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
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileBox.2
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i != 0) {
                    AppFile.f(defpackage.h.g(i, "deleteFile error, code = "));
                    AppFileBox.this.k();
                    appFileListener.a(AppFile.a.Fail);
                } else {
                    AppFileBox appFileBox = AppFileBox.this;
                    Context context2 = context;
                    appFileBox.a(context2, context2.getString(Res.j("sodk_editor_deleting")), new AppFile.c() { // from class: com.artifex.sonui.AppFileBox.2.1
                        @Override // com.artifex.sonui.AppFile.c
                        public void a() {
                            AppFileBox.this.o.cancel(true);
                        }
                    });
                    AppFileBox.this.o = new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileBox.2.2

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4276a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) throws Throwable {
                            try {
                                com.box.androidsdk.content.b bVar = AppFileBox.this.r;
                                BoxRequestsFile.DeleteFile deleteFile = new BoxRequestsFile.DeleteFile(BoxVoid.class, bVar.d(AppFileBox.this.l), bVar.f5002a);
                                deleteFile.i = BoxRequest.Methods.d;
                                deleteFile.k();
                                this.f4276a = true;
                                return null;
                            } catch (BoxException e) {
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
                            AppFileBox.this.k();
                            appFileListener.a(this.f4276a ? AppFile.a.Success : AppFile.a.Fail);
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
        AppFileBox appFileBox = (AppFileBox) AppFile.b(this);
        appFileBox.l = this.l;
        appFileBox.m = this.m;
        appFileBox.n = this.n;
        return appFileBox;
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile enumerateDir(AppFile.EnumerateListener enumerateListener) {
        AppFile.i = enumerateListener;
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileBox.1
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                final ArrayList arrayList = new ArrayList();
                if (i == 0) {
                    new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileBox.1.1

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4271a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r2) {
                            ArrayList<AppFile> arrayList2;
                            AppFile.EnumerateListener enumerateListener2;
                            super.onPostExecute(r2);
                            if (this.f4271a) {
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
                            AppFileBox appFileBox = AppFileBox.this;
                            appFileBox.a(appFileBox.l, (String) null, (ArrayList<AppFile>) arrayList);
                            this.f4271a = true;
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
        a(new AppFile.b() { // from class: com.artifex.sonui.AppFileBox.6
            @Override // com.artifex.sonui.AppFile.b
            public void a(int i) {
                if (i == 0) {
                    new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileBox.6.1

                        /* JADX INFO: renamed from: a, reason: collision with root package name */
                        boolean f4298a = false;

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public Void doInBackground(Void... voidArr) {
                            ArrayList arrayList = new ArrayList();
                            AppFileBox appFileBox = AppFileBox.this;
                            appFileBox.a(appFileBox.m, AppFileBox.this.f4253b, (ArrayList<AppFile>) arrayList);
                            if (arrayList.size() <= 0) {
                                return null;
                            }
                            this.f4298a = true;
                            return null;
                        }

                        @Override // android.os.AsyncTask
                        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                        public void onPostExecute(Void r1) {
                            super.onPostExecute(r1);
                            existsListener.a(this.f4298a);
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
        AppFileBox appFileBox = (AppFileBox) AppFile.d(str);
        String[] strArrSplit = str.split("\\|");
        appFileBox.l = AppFile.c(strArrSplit[7]);
        appFileBox.m = AppFile.c(strArrSplit[8]);
        appFileBox.n = AppFile.c(strArrSplit[9]);
        return appFileBox;
    }

    @Override // com.artifex.sonui.AppFile
    public String g() {
        StringBuilder sbT;
        StringBuilder sbT2;
        StringBuilder sbT3;
        String str = "Box/" + this.f4253b;
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
        return Res.f(this.l.equalsIgnoreCase("0") ? "sodk_icon_cloud_box" : "sodk_editor_icon_folder");
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isCloud() {
        return true;
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isSameAs(AppFile appFile) {
        return appFile != null && a() == appFile.a() && this.l.compareTo(((AppFileBox) appFile).l) == 0;
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
            a(new AppFile.b() { // from class: com.artifex.sonui.AppFileBox.5
                @Override // com.artifex.sonui.AppFile.b
                public void a(int i) {
                    if (i != 0) {
                        AppFile.f(defpackage.h.g(i, "rename error, code = "));
                        AppFileBox.this.k();
                        appFileListener.a(AppFile.a.Fail);
                    } else {
                        AppFileBox appFileBox = AppFileBox.this;
                        Context context2 = context;
                        appFileBox.a(context2, context2.getString(Res.j("sodk_editor_renaming")), new AppFile.c() { // from class: com.artifex.sonui.AppFileBox.5.1
                            @Override // com.artifex.sonui.AppFile.c
                            public void a() {
                                AppFileBox.this.o.cancel(true);
                            }
                        });
                        AppFileBox.this.o = new AsyncTask<Void, String, Void>() { // from class: com.artifex.sonui.AppFileBox.5.2

                            /* JADX INFO: renamed from: a, reason: collision with root package name */
                            boolean f4294a = false;

                            @Override // android.os.AsyncTask
                            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                            public Void doInBackground(Void... voidArr) throws Throwable {
                                try {
                                    com.box.androidsdk.content.b bVar = AppFileBox.this.r;
                                    String str2 = AppFileBox.this.l;
                                    String str3 = str;
                                    BoxRequestsFile.UpdateFile updateFile = new BoxRequestsFile.UpdateFile(BoxFile.class, bVar.d(str2), bVar.f5002a);
                                    updateFile.i = BoxRequest.Methods.c;
                                    updateFile.d.put("name", str3);
                                    updateFile.k();
                                    this.f4294a = true;
                                    return null;
                                } catch (BoxException e) {
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
                                AppFileBox.this.k();
                                appFileListener.a(this.f4294a ? AppFile.a.Success : AppFile.a.Fail);
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

    public AppFileBox() {
        this.f4252a = 3;
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
        sb.append("Box-");
        sb.append(this.m);
        sb.append("-");
        return e6.q(sb, this.l, str2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, ArrayList<AppFile> arrayList) {
        try {
            BoxRequestsFolder.GetFolderItems getFolderItemsC = this.q.c(str);
            getFolderItemsC.o("name", "modified_at", "id", "size");
            for (BoxItem boxItem : (BoxIteratorItems) getFolderItemsC.k()) {
                if (str2 == null || str2.equalsIgnoreCase(boxItem.p("name"))) {
                    AppFileBox appFileBox = new AppFileBox();
                    appFileBox.f4253b = boxItem.p("name");
                    appFileBox.d = boxItem instanceof BoxFolder;
                    appFileBox.l = boxItem.A();
                    appFileBox.n = "";
                    appFileBox.m = this.l;
                    appFileBox.g = appFileBox.g();
                    appFileBox.e = boxItem.C().longValue();
                    appFileBox.f = boxItem.D().getTime();
                    arrayList.add(appFileBox);
                }
            }
        } catch (BoxException e) {
            e.printStackTrace();
        }
    }
}
