package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.security.keystore.KeyGenParameterSpec;
import com.artifex.solib.ConfigOptions;
import defpackage.a7;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import javax.crypto.KeyGenerator;

/* JADX INFO: loaded from: classes2.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final byte[] f4491a = {1, 2, 3, 4, 5, 6};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static a f4492b = null;
    private KeyguardManager c;
    private boolean d;
    private boolean e;
    private ConfigOptions f;
    private int g;

    /* JADX INFO: renamed from: com.artifex.sonui.a$1, reason: invalid class name */
    /* JADX INFO: loaded from: classes.dex */
    public class AnonymousClass1 implements DialogInterface.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Activity f4493a;

        public AnonymousClass1(Activity activity) {
            this.f4493a = activity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            this.f4493a.startActivity(new Intent("android.settings.SECURITY_SETTINGS"));
            a.this.d = false;
        }
    }

    /* JADX INFO: renamed from: com.artifex.sonui.a$2, reason: invalid class name */
    /* JADX INFO: loaded from: classes.dex */
    public class AnonymousClass2 implements DialogInterface.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Activity f4495a;

        public AnonymousClass2(Activity activity) {
            this.f4495a = activity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            a.this.d = false;
            a.this.a(this.f4495a);
        }
    }

    private void b() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (keyStore.isKeyEntry("authorisation_key")) {
                keyStore.deleteEntry("authorisation_key");
            }
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder("authorisation_key", 3).setBlockModes("CBC").setUserAuthenticationRequired(true).setUserAuthenticationValidityDurationSeconds(this.f.v()).setEncryptionPaddings("PKCS7Padding").build());
            this.g = this.f.v();
            keyGenerator.generateKey();
        } catch (IOException | InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | CertificateException unused) {
        }
    }

    public boolean a(int i, int i2, Intent intent, Activity activity) {
        if (i != 101) {
            return false;
        }
        this.e = false;
        this.f.u();
        return true;
    }

    public int a() {
        return 101;
    }

    public boolean a(Activity activity) {
        this.f.u();
        return true;
    }

    private void b(Activity activity) {
        if (this.e) {
            return;
        }
        Intent intentCreateConfirmDeviceCredentialIntent = this.c.createConfirmDeviceCredentialIntent(null, null);
        if (intentCreateConfirmDeviceCredentialIntent == null) {
            a7.t("No Screen Lock");
        } else {
            this.e = true;
            activity.startActivityForResult(intentCreateConfirmDeviceCredentialIntent, 101);
        }
    }
}
