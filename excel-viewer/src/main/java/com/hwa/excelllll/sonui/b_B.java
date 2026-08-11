package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.content.Context;
import com.a.o.Res;
import com.artifex.sonui.editor.Utilities;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class b extends AppFile {
    public b(String str, String str2) {
        a(new File(str));
        this.f4253b = str2;
    }

    private void a(File file) {
        this.f4252a = 1;
        this.f4253b = file.getName();
        this.d = file.isDirectory();
        this.c = file.getAbsolutePath();
        this.g = file.getAbsolutePath();
        this.e = file.length();
        this.f = file.lastModified();
        if (this.d) {
            String str = this.c;
            String str2 = File.separator;
            if (!str.endsWith(str2)) {
                this.c = defpackage.h.p(new StringBuilder(), this.c, str2);
            }
            if (this.g.endsWith(str2)) {
                return;
            }
            this.g = defpackage.h.p(new StringBuilder(), this.g, str2);
        }
    }

    @Override // com.artifex.sonui.AppFile
    public void Logout(AppFile.LogoutListener logoutListener) {
        logoutListener.a();
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile child(String str) {
        if (!this.d) {
            return null;
        }
        AppFile appFileDuplicate = duplicate();
        appFileDuplicate.g = defpackage.h.p(new StringBuilder(), appFileDuplicate.g, str);
        appFileDuplicate.c = defpackage.h.p(new StringBuilder(), appFileDuplicate.c, str);
        appFileDuplicate.f4253b = str;
        return appFileDuplicate;
    }

    @Override // com.artifex.sonui.AppFile
    public void copyFromRemote(Context context, AppFile.AppFileListener appFileListener) {
        if (appFileListener != null) {
            appFileListener.a(AppFile.a.Success);
        }
    }

    @Override // com.artifex.sonui.AppFile
    public void copyToRemote(Context context, AppFile.AppFileListener appFileListener) {
        boolean zA = this.c.equalsIgnoreCase(this.g) ? true : com.artifex.solib.a.a(this.c, this.g, true);
        if (appFileListener != null) {
            appFileListener.a(zA ? AppFile.a.Success : AppFile.a.Fail);
        }
    }

    @Override // com.artifex.sonui.AppFile
    public void deleteFile(Context context, AppFile.AppFileListener appFileListener) {
        com.artifex.solib.a.e(this.c);
        if (appFileListener != null) {
            appFileListener.a(AppFile.a.Success);
        }
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile duplicate() {
        return AppFile.b(this);
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile enumerateDir(AppFile.EnumerateListener enumerateListener) {
        AppFile.i = enumerateListener;
        File[] fileArrListFiles = new File(this.c).listFiles(new FileFilter() { // from class: com.artifex.sonui.b.1
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                return !file.getName().startsWith(".");
            }
        });
        ArrayList<AppFile> arrayList = new ArrayList<>();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                arrayList.add(new b(file));
            }
        }
        AppFile.EnumerateListener enumerateListener2 = AppFile.i;
        if (enumerateListener2 != null) {
            enumerateListener2.a(arrayList);
        }
        return this;
    }

    @Override // com.artifex.sonui.AppFile
    public void exists(AppFile.ExistsListener existsListener) {
        existsListener.a(new File(g()).exists());
    }

    @Override // com.artifex.sonui.AppFile
    public AppFile fromString(String str) {
        return AppFile.d(str);
    }

    @Override // com.artifex.sonui.AppFile
    public String getDisplayPath() {
        return this.g;
    }

    @Override // com.artifex.sonui.AppFile
    public int getFolderResourceId() {
        return Res.f("sodk_editor_icon_folder");
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isCloud() {
        return false;
    }

    @Override // com.artifex.sonui.AppFile
    public boolean isSameAs(AppFile appFile) {
        String strC = c();
        String strC2 = appFile.c();
        return (strC == null || strC2 == null || strC.compareToIgnoreCase(strC2) != 0) ? false : true;
    }

    @Override // com.artifex.sonui.AppFile
    public void rename(String str, Context context, AppFile.AppFileListener appFileListener) {
        AppFile.a aVar;
        String strG = com.artifex.solib.a.g(b());
        String strG2 = com.artifex.solib.a.g(str);
        if (strG2 == null || strG2.isEmpty()) {
            str = defpackage.h.j(str, ".", strG);
            strG2 = strG;
        }
        boolean zEqualsIgnoreCase = strG2.equalsIgnoreCase(strG);
        int iJ = Res.j("sodk_editor_error");
        if (zEqualsIgnoreCase) {
            File file = new File(g());
            File file2 = new File(file.getParentFile(), str);
            if (!file2.exists()) {
                if (file.renameTo(file2)) {
                    this.g = file2.getAbsolutePath();
                    this.f4253b = file2.getName();
                    this.c = this.g;
                    if (appFileListener == null) {
                        return;
                    } else {
                        aVar = AppFile.a.Success;
                    }
                } else {
                    Utilities.showMessage((Activity) context, context.getString(iJ), context.getString(Res.j("sodk_editor_error_renaming_file")));
                    if (appFileListener == null) {
                        return;
                    } else {
                        aVar = AppFile.a.Fail;
                    }
                }
                appFileListener.a(aVar);
                return;
            }
            Utilities.showMessage((Activity) context, context.getString(iJ), String.format(context.getString(Res.j("sodk_editor_file_already_exists2")), str));
            if (appFileListener == null) {
                return;
            }
        } else {
            Utilities.showMessage((Activity) context, context.getString(iJ), String.format(context.getString(Res.j("sodk_editor_cant_change_extension")), strG, strG2));
            if (appFileListener == null) {
                return;
            }
        }
        appFileListener.a(AppFile.a.Fail);
    }

    @Override // com.artifex.sonui.AppFile
    public boolean serviceAvailable() {
        return true;
    }

    @Override // com.artifex.sonui.AppFile
    public String toString() {
        return AppFile.a(this);
    }

    private b(File file) {
        a(file);
    }

    public b() {
        this.f4252a = 1;
    }
}
