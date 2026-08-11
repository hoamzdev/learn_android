package com.hwa.excelllll.sonui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.RestrictionsManager;
import android.os.Bundle;
import android.util.Log;
import com.artifex.solib.ConfigOptions;

/* JADX INFO: loaded from: classes.dex */
public class d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Context f4500a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private BroadcastReceiver f4501b;
    private RestrictionsManager c;
    private a d;

    public interface a {
    }

    public void a() {
        Context context = this.f4500a;
        if (context != null) {
            context.unregisterReceiver(this.f4501b);
        }
        this.d = null;
    }

    public void b() {
        String str;
        RestrictionsManager restrictionsManager = this.c;
        if (restrictionsManager == null) {
            str = "applyRestrictions: mRestrictionsMgr == null";
        } else {
            Bundle applicationRestrictions = restrictionsManager.getApplicationRestrictions();
            if (applicationRestrictions != null) {
                ConfigOptions configOptionsA = ConfigOptions.a();
                if (applicationRestrictions.containsKey("preventCopyPasteToOtherApps")) {
                    boolean z = !applicationRestrictions.getBoolean("preventCopyPasteToOtherApps");
                    configOptionsA.h(z);
                    configOptionsA.d(z);
                    configOptionsA.e(z);
                }
                if (applicationRestrictions.containsKey("preventPasteFromOtherApps")) {
                    boolean z2 = !applicationRestrictions.getBoolean("preventPasteFromOtherApps");
                    configOptionsA.g(z2);
                    configOptionsA.i(z2);
                    configOptionsA.j(z2);
                }
                if (applicationRestrictions.containsKey("preventPrinting")) {
                    configOptionsA.k(!applicationRestrictions.getBoolean("preventPrinting"));
                }
                if (applicationRestrictions.containsKey("preventSecurePrinting")) {
                    configOptionsA.l(!applicationRestrictions.getBoolean("preventSecurePrinting"));
                }
                if (applicationRestrictions.containsKey("requireUnlock")) {
                    configOptionsA.s(applicationRestrictions.getBoolean("requireUnlock"));
                }
                if (applicationRestrictions.containsKey("unlockTimeout")) {
                    configOptionsA.a(applicationRestrictions.getInt("unlockTimeout"));
                    return;
                }
                return;
            }
            str = "applyRestrictions: appRestrictions == null";
        }
        Log.e("AppRestrictions", str);
    }
}
