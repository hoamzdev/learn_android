package com.hwa.excelllll.solib;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public abstract class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected static j f4240a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static ClipboardManager f4241b;
    private static ClipboardManager.OnPrimaryClipChangedListener c;
    private static int d;

    public interface a {
        void a(int i, int i2, int i3, String str, String str2, float f, float f2);
    }

    public static void a(String str) {
        if (ConfigOptions.a().j() && str != null) {
            j jVar = f4240a;
            if (jVar != null) {
                jVar.a(str);
            } else {
                f4241b.setPrimaryClip(ClipData.newPlainText("text", str));
            }
        }
    }

    public static String b(Context context) {
        if (!ConfigOptions.a().i()) {
            return "";
        }
        j jVar = f4240a;
        if (jVar == null) {
            return f4241b.hasPrimaryClip() ? f4241b.getPrimaryClip().getItemAt(0).coerceToText(context).toString() : "";
        }
        return jVar.a();
    }

    public static boolean c() {
        if (!ConfigOptions.a().i()) {
            return false;
        }
        j jVar = f4240a;
        return jVar == null ? f4241b.hasPrimaryClip() : jVar.b();
    }

    public static boolean d(Activity activity, String str) {
        return b(activity).isDocTypePowerPoint(str);
    }

    public static boolean e(Activity activity) {
        return b(activity).isTrackChangesEnabled();
    }

    public static boolean f(Activity activity, String str) {
        return b(activity).isDocTypeDoc(str);
    }

    public abstract SOBitmap a(int i, int i2);

    public abstract SODoc a(String str, l lVar, Context context);

    public static /* synthetic */ int d() {
        int i = d;
        d = i + 1;
        return i;
    }

    public static boolean e(Activity activity, String str) {
        return com.artifex.solib.a.c(str, "pdf");
    }

    public static String[] d(Activity activity) {
        return b(activity).getVersionInfo();
    }

    public static d c(Activity activity) {
        return d.a(activity);
    }

    public static boolean c(Activity activity, String str) {
        return b(activity).isDocTypeExcel(str);
    }

    public static SOBitmap a(String str, int i, int i2) {
        return com.artifex.solib.a.h(str) ? new b(i, i2) : new SOBitmap(i, i2);
    }

    public static SOSecureFS a() {
        return SOLib.f();
    }

    public static k a(Activity activity, String str) {
        return com.artifex.solib.a.h(str) ? c(activity) : b(activity);
    }

    public static void a(Context context) {
        f4241b = (ClipboardManager) context.getSystemService("clipboard");
        ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() { // from class: com.artifex.solib.k.2
            @Override // android.content.ClipboardManager.OnPrimaryClipChangedListener
            public void onPrimaryClipChanged() {
                if (k.c()) {
                    k.d();
                }
            }
        };
        c = onPrimaryClipChangedListener;
        f4241b.addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    public static void a(SODoc sODoc, final a aVar) {
        ((c) sODoc).a(new c.b() { // from class: com.artifex.solib.k.1
            @Override // com.artifex.solib.c.b
            public void a(int i, int i2, int i3, String str, String str2, float f, float f2) {
                aVar.a(i, i2, i3, str, str2, f, f2);
            }
        });
    }

    public static SOLib b(Activity activity) {
        return SOLib.a(activity);
    }

    public static int b() {
        return d;
    }

    public static boolean b(Activity activity, String str) {
        if (com.artifex.solib.a.h(str)) {
            return false;
        }
        return b(activity).isDocTypeOther(str);
    }
}
