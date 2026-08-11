package com.hwa.excelllll.sonui;

import android.content.Context;
import com.artifex.solib.ConfigOptions;
import com.onesignal.IOneSignal;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.xlsxreader.excelreader.R;
import com.xlsxreader.excelreader.ads.AdsApplication;
import com.xlsxreader.excelreader.ads.AdsConfigKt;
import com.xlsxreader.excelreader.ads.CoroutineHelper;
import com.xlsxreader.excelreader.premium.DataWrappers;
import com.xlsxreader.excelreader.premium.IapConnector;
import com.xlsxreader.excelreader.premium.SubscriptionServiceListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import kotlin.Lazy;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class MainApp extends AdsApplication {
    private static Context context;

    private void a() {
        ConfigOptions configOptionsA = ConfigOptions.a();
        configOptionsA.a(true);
        configOptionsA.n(true);
        configOptionsA.b(true);
        configOptionsA.c(true);
        configOptionsA.o(false);
        configOptionsA.d(true);
        configOptionsA.e(true);
        configOptionsA.f(true);
        configOptionsA.g(true);
        configOptionsA.h(true);
        configOptionsA.i(true);
        configOptionsA.j(true);
        configOptionsA.k(true);
        configOptionsA.l(false);
        configOptionsA.m(true);
        configOptionsA.r(true);
        configOptionsA.p(true);
        configOptionsA.q(true);
        configOptionsA.t(false);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override // com.xlsxreader.excelreader.ads.AdsApplication, android.app.Application
    public void onCreate() {
        a();
        super.onCreate();
        context = this;
        Lazy lazy = OneSignal.f18975a;
        ((IOneSignal) lazy.getF20957a()).getDebug().setLogLevel(LogLevel.VERBOSE);
        ((IOneSignal) lazy.getF20957a()).initWithContext(this, "8e1cec24-5aad-46f9-8d83-a51dc828dec9");
        CoroutineHelper.a(this);
        IapConnector iapConnector = new IapConnector(this, new ArrayList(), Arrays.asList("com.xlsxreader.excelreader.lifetime"), Arrays.asList("com.xlsxreader.excelreader.monthly", "com.xlsxreader.excelreader.sixmonth", "com.xlsxreader.excelreader.yearly"), getString(R.string.licenseKey));
        iapConnector.a().f19999b.add(new SubscriptionServiceListener() { // from class: com.artifex.sonui.MainApp.1
            @Override // com.xlsxreader.excelreader.premium.SubscriptionServiceListener
            public void onSubscriptionRestored(DataWrappers.PurchaseInfo purchaseInfo) {
                String str = purchaseInfo.k;
                AdsConfigKt.a(MainApp.this).b(str.equals("com.xlsxreader.excelreader.lifetime") || str.equals("com.xlsxreader.excelreader.yearly") || str.equals("com.xlsxreader.excelreader.sixmonth") || str.equals("com.xlsxreader.excelreader.monthly"));
            }

            @Override // com.xlsxreader.excelreader.premium.BillingServiceListener
            public void onPricesUpdated(Map<String, DataWrappers.ProductDetails> map) {
            }

            @Override // com.xlsxreader.excelreader.premium.SubscriptionServiceListener
            public void onSubscriptionPurchased(DataWrappers.PurchaseInfo purchaseInfo) {
            }

            @Override // com.xlsxreader.excelreader.premium.BillingServiceListener
            public void onPurchaseFailed(DataWrappers.PurchaseInfo purchaseInfo, Integer num) {
            }
        });
    }
}
