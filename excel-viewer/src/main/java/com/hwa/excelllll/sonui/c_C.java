package com.hwa.excelllll.sonui;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static AppFile a(String str, String str2, String str3, boolean z, boolean z2) {
        StringBuilder sbT;
        String str4;
        try {
            Class<?> cls = Class.forName("com.artifex.sonui." + str);
            Class cls2 = Boolean.TYPE;
            return (AppFile) cls.getConstructor(String.class, String.class, cls2, cls2).newInstance(str2, str3, Boolean.valueOf(z), Boolean.valueOf(z2));
        } catch (ClassNotFoundException unused) {
            Log.i("AppFileUtilites", "getAppFile: " + str + " ClassNotFoundException");
            return null;
        } catch (IllegalAccessException unused2) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str4 = " IllegalAccessException";
            sbT.append(str4);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (InstantiationException unused3) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str4 = " InstantiationException";
            sbT.append(str4);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (LinkageError unused4) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str4 = " LinkageError";
            sbT.append(str4);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (NoSuchMethodException unused5) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str4 = " NoSuchMethodException";
            sbT.append(str4);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (SecurityException unused6) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str4 = " SecurityException";
            sbT.append(str4);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (InvocationTargetException unused7) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str4 = " InvocationTargetException";
            sbT.append(str4);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        }
    }

    public static AppFile b() {
        return a("AppFileBox");
    }

    public static AppFile c() {
        return a("AppFileDropbox");
    }

    public static AppFile b(String str, String str2, boolean z, boolean z2) {
        return a("AppFileBox", str, str2, z, z2);
    }

    public static AppFile c(String str, String str2, boolean z, boolean z2) {
        return a("AppFileDropbox", str, str2, z, z2);
    }

    private static AppFile a(String str) {
        StringBuilder sbT;
        String str2;
        try {
            return (AppFile) Class.forName("com.artifex.sonui." + str).getConstructor(null).newInstance(null);
        } catch (ClassNotFoundException unused) {
            Log.i("AppFileUtilites", "getAppFile: " + str + " ClassNotFoundException");
            return null;
        } catch (IllegalAccessException unused2) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str2 = " IllegalAccessException";
            sbT.append(str2);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (InstantiationException unused3) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str2 = " InstantiationException";
            sbT.append(str2);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (LinkageError unused4) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str2 = " LinkageError";
            sbT.append(str2);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (NoSuchMethodException unused5) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str2 = " NoSuchMethodException";
            sbT.append(str2);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (SecurityException unused6) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str2 = " SecurityException";
            sbT.append(str2);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        } catch (InvocationTargetException unused7) {
            sbT = defpackage.h.t("getAppFile: ", str);
            str2 = " InvocationTargetException";
            sbT.append(str2);
            Log.e("AppFileUtilites", sbT.toString());
            return null;
        }
    }

    public static AppFile a() {
        return a("AppFileGoogleDrive");
    }

    public static AppFile a(String str, String str2, boolean z, boolean z2) {
        return a("AppFileGoogleDrive", str, str2, z, z2);
    }

    public static boolean a(String str, Object obj) {
        try {
            if (!Class.forName("com.artifex.sonui." + str).isInstance(obj)) {
                return false;
            }
            Log.d("isInstance", "Object is iostance of " + str);
            return true;
        } catch (ClassNotFoundException unused) {
            Log.i("AppFileUtilites", "isInstance: ClassNotFoundException");
            return false;
        } catch (LinkageError unused2) {
            Log.e("AppFileUtilites", "isInstance: LinkageError");
            return false;
        }
    }
}
