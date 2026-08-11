package com.hwa.excelllll.sonui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.xlsxreader.excelreader.BaseActivity;
import com.xlsxreader.excelreader.R;
import defpackage.d8;
import defpackage.j1;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class FeedbackActivity extends BaseActivity {
    private EditText txt_body;
    private TextView txt_submit;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insetsD = windowInsetsCompat.d(647);
        view.setPadding(insetsD.f1667a, insetsD.f1668b, insetsD.c, insetsD.d);
        return WindowInsetsCompat.f1821b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        try {
            Uri uri = Uri.parse("mailto:gbwasappstudio@gmail.com?subject=" + Uri.encode("Feedback from " + getString(R.string.app_name)) + "&body=" + Uri.encode(this.txt_body.getText().toString()));
            Intent intent = new Intent("android.intent.action.SENDTO");
            intent.setData(uri);
            startActivity(Intent.createChooser(intent, getString(R.string.send_email_using)));
            this.txt_body.setText("");
        } catch (Exception e) {
            d8.i(e);
        }
    }

    public void onBack(View view) {
        getOnBackPressedDispatcher().b().a();
    }

    @Override // com.xlsxreader.excelreader.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WindowCompat.a(getWindow(), false);
        setContentView(R.layout.activity_feedback);
        this.txt_submit = (TextView) findViewById(R.id.tv_submit);
        this.txt_body = (EditText) findViewById(R.id.txt_body);
        this.txt_submit.setEnabled(false);
        ViewCompat.v(findViewById(R.id.llMain), new defpackage.f(24));
        this.txt_body.addTextChangedListener(new TextWatcher() { // from class: com.artifex.sonui.FeedbackActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int length = FeedbackActivity.this.txt_body.getText().length();
                FeedbackActivity feedbackActivity = FeedbackActivity.this;
                if (length <= 5) {
                    feedbackActivity.txt_submit.setAlpha(0.5f);
                } else {
                    feedbackActivity.txt_submit.setEnabled(true);
                    FeedbackActivity.this.txt_submit.setAlpha(1.0f);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = FeedbackActivity.this.txt_body.getText().length();
                FeedbackActivity feedbackActivity = FeedbackActivity.this;
                if (length <= 5) {
                    feedbackActivity.txt_submit.setAlpha(0.5f);
                } else {
                    feedbackActivity.txt_submit.setEnabled(true);
                    FeedbackActivity.this.txt_submit.setAlpha(1.0f);
                }
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                int length = FeedbackActivity.this.txt_body.getText().length();
                FeedbackActivity feedbackActivity = FeedbackActivity.this;
                if (length <= 5) {
                    feedbackActivity.txt_submit.setAlpha(0.5f);
                } else {
                    feedbackActivity.txt_submit.setEnabled(true);
                    FeedbackActivity.this.txt_submit.setAlpha(1.0f);
                }
            }
        });
        this.txt_submit.setOnClickListener(new j1(1, this));
    }
}
