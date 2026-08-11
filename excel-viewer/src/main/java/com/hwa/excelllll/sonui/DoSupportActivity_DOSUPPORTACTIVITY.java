package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.supportv1.v7.app.AlertController;
import android.supportv1.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import com.a.o.Res;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.SOTextView;
import com.artifex.sonui.editor.Utilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import zendesk.core.AnonymousIdentity;
import zendesk.core.Zendesk;
import zendesk.support.CustomField;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;
import zendesk.support.guide.HelpCenterUiConfig;
import zendesk.support.request.RequestActivity;
import zendesk.support.request.RequestUiConfig;
import zendesk.support.requestlist.RequestListActivity;
import zendesk.support.requestlist.RequestListUiConfig;

/* JADX INFO: loaded from: classes.dex */
public class DoSupportActivity extends BaseActivity {
    private static final String EMAIL_KEY = "zen_email_address";
    private String emailAddress = "";

    /* JADX INFO: Access modifiers changed from: private */
    public void getEmail(final Runnable runnable, final Runnable runnable2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, Res.k("sodk_editor_alert_dialog_style")));
        final SOEditText sOEditText = new SOEditText(this);
        sOEditText.setInputType(32);
        String string = getString(Res.j("sodk_support_email_body"));
        AlertController.AlertParams alertParams = builder.f540a;
        alertParams.f = string;
        alertParams.m = getString(Res.j("sodk_support_email_title"));
        alertParams.n = sOEditText;
        sOEditText.setText(this.emailAddress);
        String string2 = getString(Res.j("sodk_editor_OK"));
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                DoSupportActivity.this.newEmailAddress(sOEditText.getText().toString());
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        };
        alertParams.l = string2;
        alertParams.k = onClickListener;
        String string3 = getString(Res.j("sodk_editor_cancel"));
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Runnable runnable3 = runnable2;
                if (runnable3 != null) {
                    runnable3.run();
                }
            }
        };
        alertParams.h = string3;
        alertParams.g = onClickListener2;
        builder.a().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void newEmailAddress(String str) {
        this.emailAddress = str;
        SOTextView sOTextView = (SOTextView) findViewById(Res.g("email_text"));
        Locale locale = Locale.US;
        sOTextView.setText("your email: " + this.emailAddress);
        findViewById(Res.g("email_wrapper")).setVisibility(0);
        Utilities.setStringPreference(Utilities.getPreferencesObject(this, Utilities.generalStore), EMAIL_KEY, this.emailAddress);
        setIdentity(this.emailAddress);
    }

    private void setIdentity(String str) {
        AnonymousIdentity.Builder builder = new AnonymousIdentity.Builder();
        builder.f22625b = str;
        builder.f22624a = str;
        Zendesk.INSTANCE.c(builder.a());
    }

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, (Class<?>) DoSupportActivity.class));
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        getWindow().setSoftInputMode(3);
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(Res.i("sodk_support"));
        Zendesk zendesk2 = Zendesk.INSTANCE;
        zendesk2.b(this);
        String str = "";
        String stringPreference = Utilities.getStringPreference(Utilities.getPreferencesObject(this, Utilities.generalStore), EMAIL_KEY, "");
        this.emailAddress = stringPreference;
        setIdentity(stringPreference);
        Support.j.a(zendesk2);
        String[] strArrD = com.artifex.solib.k.d(this);
        String str2 = strArrD != null ? strArrD[1] : "";
        try {
            str = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String string = getApplicationContext().getString(Res.j("sodk_app_name"));
        if (!str.isEmpty()) {
            string = (string + " " + getApplicationContext().getString(Res.j("sodk_editor_version_title")) + " ").concat(str);
        }
        if (!str2.isEmpty()) {
            string = string + " (" + str2 + ")";
        }
        Locale locale = Locale.US;
        String str3 = "Android " + Build.VERSION.RELEASE + ", Version " + Build.VERSION.SDK_INT;
        final CustomField customField = new CustomField(80110747L, string);
        final CustomField customField2 = new CustomField(80110767L, str3);
        ((Button) findViewById(Res.g("done_button"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DoSupportActivity.this.finish();
            }
        });
        ((Button) findViewById(Res.g("faq_button"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int i = HelpCenterActivity.f;
                HelpCenterUiConfig.Builder builder = new HelpCenterUiConfig.Builder();
                List list = Collections.EMPTY_LIST;
                builder.f23004a = list;
                builder.e = list;
                builder.d = new String[0];
                builder.f23005b = true;
                builder.f = true;
                builder.g = new ArrayList();
                builder.c = true;
                builder.f23005b = false;
                builder.f = false;
                Activity activity = this;
                List listAsList = Arrays.asList(new zendesk.commonui.i[0]);
                builder.g = listAsList;
                HelpCenterUiConfig helpCenterUiConfig = (HelpCenterUiConfig) zendesk.commonui.j.b(HelpCenterUiConfig.class, listAsList);
                if (helpCenterUiConfig != null) {
                    builder.f23005b = helpCenterUiConfig.f23003b;
                    builder.f23004a = helpCenterUiConfig.f23002a;
                    builder.e = helpCenterUiConfig.e;
                    builder.d = helpCenterUiConfig.d;
                    builder.f = helpCenterUiConfig.f;
                }
                Intent intent = new Intent(activity, (Class<?>) HelpCenterActivity.class);
                intent.putExtra("ZENDESK_UI_CONFIG", new HelpCenterUiConfig(builder));
                activity.startActivity(intent);
            }
        });
        ((Button) findViewById(Res.g("view_button"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DoSupportActivity.this.emailAddress.isEmpty()) {
                    DoSupportActivity.this.getEmail(new Runnable() { // from class: com.artifex.sonui.DoSupportActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RequestListUiConfig.Builder builderB = RequestListActivity.b();
                            Activity activity = this;
                            builderB.f23219a = Arrays.asList(new zendesk.commonui.i[0]);
                            RequestListUiConfig requestListUiConfig = new RequestListUiConfig(builderB);
                            Intent intent = new Intent(activity, (Class<?>) RequestListActivity.class);
                            intent.putExtra("ZENDESK_UI_CONFIG", requestListUiConfig);
                            activity.startActivity(intent);
                        }
                    }, null);
                    return;
                }
                RequestListUiConfig.Builder builderB = RequestListActivity.b();
                Activity activity = this;
                builderB.f23219a = Arrays.asList(new zendesk.commonui.i[0]);
                RequestListUiConfig requestListUiConfig = new RequestListUiConfig(builderB);
                Intent intent = new Intent(activity, (Class<?>) RequestListActivity.class);
                intent.putExtra("ZENDESK_UI_CONFIG", requestListUiConfig);
                activity.startActivity(intent);
            }
        });
        ((Button) findViewById(Res.g("contact_button"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DoSupportActivity.this.emailAddress.isEmpty()) {
                    DoSupportActivity.this.getEmail(new Runnable() { // from class: com.artifex.sonui.DoSupportActivity.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RequestUiConfig.Builder builderB = RequestActivity.b();
                            AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                            builderB.b(Arrays.asList(customField, customField2));
                            Activity activity = this;
                            activity.startActivity(builderB.a(activity, Arrays.asList(new zendesk.commonui.i[0])));
                        }
                    }, null);
                    return;
                }
                RequestUiConfig.Builder builderB = RequestActivity.b();
                builderB.b(Arrays.asList(customField, customField2));
                Activity activity = this;
                activity.startActivity(builderB.a(activity, Arrays.asList(new zendesk.commonui.i[0])));
            }
        });
        ((SOTextView) findViewById(Res.g("version_text"))).setText(string);
        ((SOTextView) findViewById(Res.g("email_text"))).setText(this.emailAddress);
        if (this.emailAddress.isEmpty()) {
            findViewById(Res.g("email_wrapper")).setVisibility(8);
        }
        ((Button) findViewById(Res.g("email_button"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.DoSupportActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DoSupportActivity.this.getEmail(null, null);
            }
        });
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(3);
    }
}
