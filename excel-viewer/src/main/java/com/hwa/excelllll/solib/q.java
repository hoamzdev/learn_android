package com.hwa.excelllll.solib;

import com.facebook.ads.AdError;
import com.onesignal.core.internal.permissions.PermissionsViewModel;
import com.onesignal.inAppMessages.internal.display.impl.WebViewManager;

/* JADX INFO: loaded from: classes.dex */
public class q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public String f4247a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f4248b;
    public String c;
    public String d;
    public int e;

    public q(String str) {
        this.f4248b = "";
        this.c = "";
        this.d = "";
        this.e = 0;
        this.f4247a = str;
        if (str != null) {
            for (String str2 : str.split(";")) {
                String[] strArrSplit = str2.trim().split(":");
                if (strArrSplit[0].trim().equals(WebViewManager.EVENT_TYPE_KEY)) {
                    this.f4248b = strArrSplit[1].trim();
                }
                if (strArrSplit[0].trim().equals("direction")) {
                    this.c = strArrSplit[1].trim();
                }
                if (strArrSplit[0].trim().equals("speed")) {
                    String strTrim = strArrSplit[1].trim();
                    this.d = strTrim;
                    this.e = PermissionsViewModel.DELAY_TIME_CALLBACK_CALL;
                    if (strTrim.equals("veryslow")) {
                        this.e = 5000;
                    }
                    if (this.d.equals("slow")) {
                        this.e = AdError.NETWORK_ERROR_CODE;
                    }
                    if (this.d.equals("medium")) {
                        this.e = 750;
                    }
                    if (this.d.equals("veryfast")) {
                        this.e = 300;
                    }
                    if (this.d.equals("fastest")) {
                        this.e = 75;
                    }
                }
            }
        }
    }
}
