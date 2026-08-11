package com.hwa.excelllll.solib;

import android.util.Log;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ConfigOptions {
    private static ConfigOptions C;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public a f4162a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private String f4163b = "ConfigOptions";
    private boolean c = true;
    private boolean f = true;
    private boolean g = true;
    private boolean h = true;
    private boolean i = true;
    private boolean j = true;
    private boolean k = true;
    private boolean l = true;
    private boolean m = true;
    private boolean n = true;
    private boolean o = true;
    private boolean p = true;
    private boolean q = false;
    private boolean r = true;
    private boolean d = true;
    private boolean e = true;
    private boolean s = true;
    private boolean t = false;
    private int u = 30;
    private boolean v = true;
    private boolean w = false;
    private boolean x = false;
    private boolean y = false;
    private boolean z = false;
    private boolean A = false;
    private boolean B = false;

    public interface a {
        void a(View view);
    }

    private ConfigOptions() {
    }

    public boolean A() {
        return this.B;
    }

    public void a(int i) {
        this.u = i;
        Log.i(this.f4163b, "mAppAuthTimeout set to " + String.valueOf(this.u));
    }

    public void b(boolean z) {
        this.g = z;
        String str = this.f4163b;
        defpackage.h.C(this.g, new StringBuilder("mSaveAsEnabled set to "), str);
    }

    public void c(boolean z) {
        this.h = z;
        String str = this.f4163b;
        defpackage.h.C(this.h, new StringBuilder("mSaveAsPdfEnabled set to "), str);
    }

    public void d(boolean z) {
        this.i = z;
        String str = this.f4163b;
        defpackage.h.C(this.i, new StringBuilder("mOpenInEnabled set to "), str);
    }

    public void e(boolean z) {
        this.j = z;
        String str = this.f4163b;
        defpackage.h.C(this.j, new StringBuilder("OpenPdfInEnabled set to "), str);
    }

    public void f(boolean z) {
        this.k = z;
        String str = this.f4163b;
        defpackage.h.C(this.k, new StringBuilder("mShareEnabled set to "), str);
    }

    public void g(boolean z) {
        this.l = z;
        String str = this.f4163b;
        defpackage.h.C(this.l, new StringBuilder("mExtClipboardInEnabled set to "), str);
    }

    public void h(boolean z) {
        this.m = z;
        String str = this.f4163b;
        defpackage.h.C(this.m, new StringBuilder("mExtClipboardOutEnabled set to "), str);
    }

    public void i(boolean z) {
        this.n = z;
        String str = this.f4163b;
        defpackage.h.C(this.n, new StringBuilder("mImageInsertEnabled set to "), str);
    }

    public void j(boolean z) {
        this.o = z;
        String str = this.f4163b;
        defpackage.h.C(this.o, new StringBuilder("mPhotoInsertEnabled set to "), str);
    }

    public void k(boolean z) {
        this.p = z;
        String str = this.f4163b;
        defpackage.h.C(this.p, new StringBuilder("mPrintingEnabled set to "), str);
    }

    public void l(boolean z) {
        this.q = z;
        String str = this.f4163b;
        defpackage.h.C(this.q, new StringBuilder("mSecurePrintingEnabled set to "), str);
    }

    public void m(boolean z) {
        this.r = z;
        String str = this.f4163b;
        defpackage.h.C(this.r, new StringBuilder("mLaunchUrlEnabled set to "), str);
    }

    public void n(boolean z) {
        this.v = z;
        String str = this.f4163b;
        defpackage.h.C(this.v, new StringBuilder("mSaveEnabled set to "), str);
    }

    public void o(boolean z) {
        this.w = z;
        String str = this.f4163b;
        defpackage.h.C(this.w, new StringBuilder("mSaveEnabled set to "), str);
    }

    public void p(boolean z) {
        this.d = z;
        String str = this.f4163b;
        defpackage.h.C(this.d, new StringBuilder("mUsePersistentFileState set to "), str);
    }

    public void q(boolean z) {
        this.e = z;
        String str = this.f4163b;
        defpackage.h.C(this.e, new StringBuilder("mAllowAutoOpen set to "), str);
    }

    public void r(boolean z) {
        this.s = z;
        String str = this.f4163b;
        defpackage.h.C(this.s, new StringBuilder("mDocAuthEntryEnabled set to "), str);
    }

    public void s(boolean z) {
        this.t = z;
        String str = this.f4163b;
        defpackage.h.C(this.t, new StringBuilder("mAppAuthEnabled set to "), str);
    }

    public void t(boolean z) {
        this.x = z;
        String str = this.f4163b;
        defpackage.h.C(this.x, new StringBuilder("mTrackChangesFeatureEnabled set to "), str);
    }

    public void u(boolean z) {
        this.y = z;
        String str = this.f4163b;
        defpackage.h.C(this.y, new StringBuilder("mFormFillingEnabled set to "), str);
    }

    public void v(boolean z) {
        this.z = z;
        String str = this.f4163b;
        defpackage.h.C(this.z, new StringBuilder("mFormSigningFeatureEnabled set to "), str);
    }

    public void w(boolean z) {
        this.A = z;
        String str = this.f4163b;
        defpackage.h.C(this.A, new StringBuilder("mRedactionsEnabled set to "), str);
    }

    public void x(boolean z) {
        this.B = z;
        String str = this.f4163b;
        defpackage.h.C(this.B, new StringBuilder("mFullscreenEnabled set to "), str);
    }

    public boolean y() {
        return this.z;
    }

    public boolean z() {
        return this.A;
    }

    public boolean b() {
        return this.c;
    }

    public boolean c() {
        return this.f;
    }

    public boolean d() {
        return this.g;
    }

    public boolean e() {
        return this.h;
    }

    public boolean f() {
        return this.i;
    }

    public boolean g() {
        return this.j;
    }

    public boolean h() {
        return this.k;
    }

    public boolean i() {
        return this.l;
    }

    public boolean j() {
        return this.m;
    }

    public boolean k() {
        return this.n;
    }

    public boolean l() {
        return this.o;
    }

    public boolean m() {
        return this.p;
    }

    public boolean n() {
        return this.q;
    }

    public boolean o() {
        return this.r;
    }

    public boolean p() {
        return this.v;
    }

    public boolean q() {
        return this.w;
    }

    public boolean r() {
        return this.d;
    }

    public boolean s() {
        return this.e;
    }

    public boolean t() {
        return this.s;
    }

    public boolean u() {
        return this.t;
    }

    public int v() {
        return this.u;
    }

    public boolean w() {
        return this.x;
    }

    public boolean x() {
        return this.y;
    }

    public static ConfigOptions a() {
        if (C == null) {
            synchronized (ConfigOptions.class) {
                try {
                    C = new ConfigOptions();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return C;
    }

    public void a(boolean z) {
        this.f = z;
        String str = this.f4163b;
        defpackage.h.C(this.f, new StringBuilder("mEditingEnabled set to "), str);
    }
}
