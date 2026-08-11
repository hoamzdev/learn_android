package com.hwa.excelllll.sonui.a;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import com.a.o.Res;
import com.artifex.solib.ConfigOptions;
import com.artifex.sonui.ExplorerActivity;
import com.b.a.a.a.c;
import com.b.a.a.a.g;

/* JADX INFO: loaded from: classes.dex */
public class a implements c.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ExplorerActivity f4497a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private c f4498b;
    private boolean c;
    private boolean d;

    public static void a(ExplorerActivity explorerActivity, boolean z) {
        int iG = Res.g("buyProTitle");
        if (explorerActivity.findViewById(iG) != null) {
            explorerActivity.findViewById(iG).setVisibility(8);
            explorerActivity.findViewById(Res.g("buyProSubtitle")).setVisibility(8);
        }
        int iG2 = Res.g("buyProLayout");
        if (explorerActivity.findViewById(iG2) != null) {
            explorerActivity.findViewById(iG2).setClickable(!z);
        }
        ConfigOptions.a().t(false);
        ConfigOptions.a().u(false);
        ConfigOptions.a().v(false);
        ConfigOptions.a().w(false);
        ConfigOptions.a().x(true);
    }

    private void b(boolean z) {
        this.c = z;
        this.d = z;
        a(this.f4497a, z);
    }

    public void onClickRestorePurchase(View view) {
        this.f4498b.getClass();
    }

    public void onClickSubscribeMonth(View view) {
        this.f4498b.getClass();
        throw null;
    }

    public void onClickSubscribeYear(View view) {
        this.f4498b.getClass();
        throw null;
    }

    public void a(int i, Throwable th) {
        Log.d("Billing", "Error");
    }

    private void a(g gVar) {
        boolean z = a(gVar, "test_pro_monthly") || a(gVar, "test_pro_monthly");
        if (this.c == z && this.d == z) {
            return;
        }
        a(z);
    }

    public void a(String str, g gVar) {
        if (str.equals("test_pro_monthly") || str.equals("test_pro_monthly")) {
            a(gVar);
        }
    }

    private void a(boolean z) {
        SharedPreferences.Editor editorEdit = this.f4497a.getSharedPreferences("PRO_SETTINGS", 0).edit();
        editorEdit.putBoolean("PRO", z);
        editorEdit.apply();
        b(z);
    }

    private boolean a(g gVar, String str) {
        if (gVar != null) {
            return gVar.e.c.h;
        }
        this.f4498b.getClass();
        throw null;
    }
}
