package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.Utilities;
import com.xlsxreader.excelreader.R;
import java.util.ArrayList;
import java.util.Comparator;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class FileBrowser extends RelativeLayout {
    private static AppFile c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    ArrayList<AppFile> f4475a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    ArrayList<AppFile> f4476b;
    private AppCompatTextView btnSave;
    private Handler d;
    private Runnable e;
    private ListView f;
    private e g;
    private AppFile h;
    private SOEditText i;
    private BaseActivity k;

    public FileBrowser(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4476b = new ArrayList<>();
        this.k = null;
        b();
    }

    private void b() {
        LayoutInflater.from(getContext()).inflate(R.layout.sodk_file_browser, this);
        this.i = (SOEditText) findViewById(R.id.edit_text);
        this.btnSave = (AppCompatTextView) findViewById(R.id.save_button);
        ArrayList<AppFile> arrayList = new ArrayList<>();
        this.f4475a = arrayList;
        arrayList.add(new b(com.artifex.solib.a.b(getContext()).getAbsolutePath(), getResources().getString(R.string.sodk_editor_my_documents)));
        if (com.artifex.solib.a.d(getContext())) {
            this.f4475a.add(new b(Utilities.getDownloadDirectory(getContext()).getAbsolutePath(), getResources().getString(R.string.sodk_editor_download)));
            this.f4475a.add(new b(Utilities.getRootDirectory(getContext()).getAbsolutePath(), getResources().getString(R.string.sodk_editor_all)));
            String sDCardPath = Utilities.getSDCardPath(getContext());
            if (sDCardPath != null) {
                this.f4475a.add(new b(sDCardPath, "SD Card"));
            }
        }
        c = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.i.setEnabled(c != null);
        if (this.i.getText().toString().trim().length() <= 0 || c == null) {
            this.btnSave.setBackgroundResource(R.drawable.bg_button_nagative);
            this.btnSave.setEnabled(false);
        } else {
            this.btnSave.setBackgroundResource(R.drawable.bg_button_action_round_10);
            this.btnSave.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.names_bar);
        linearLayout.removeAllViews();
        a(null, getResources().getString(R.string.sodk_editor_storage), linearLayout);
        ArrayList<AppFile> arrayList = this.f4476b;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            AppFile appFile = arrayList.get(i);
            i++;
            linearLayout.addView(LayoutInflater.from(getContext()).inflate(R.layout.sodk_breadcrumb_slash, (ViewGroup) null));
            a(appFile, null, linearLayout);
        }
    }

    public void a(BaseActivity baseActivity, String str) {
        this.k = baseActivity;
        if (str != null && !str.isEmpty()) {
            this.i.setText(Utilities.removeExtension(str));
        }
        this.i.setSelectAllOnFocus(true);
        this.i.addTextChangedListener(new TextWatcher() { // from class: com.artifex.sonui.FileBrowser.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                FileBrowser.this.c();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        ListView listView = (ListView) findViewById(R.id.fileListView);
        this.f = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.artifex.sonui.FileBrowser.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                FileBrowser fileBrowser = FileBrowser.this;
                fileBrowser.a(fileBrowser.f, view, i, j);
            }
        });
        e eVar = new e(((Activity) getContext()).getLayoutInflater(), false);
        this.g = eVar;
        this.f.setAdapter((ListAdapter) eVar);
        this.d = new Handler();
        Runnable runnable = new Runnable() { // from class: com.artifex.sonui.FileBrowser.5
            @Override // java.lang.Runnable
            public void run() {
                FileBrowser.this.findViewById(R.id.no_documents_found).setVisibility(8);
                FileBrowser.this.findViewById(R.id.enumerate_progress).setVisibility(8);
                FileBrowser.this.i.clearFocus();
                Utilities.hideKeyboard(FileBrowser.this.getContext());
                FileBrowser.this.c();
                if (FileBrowser.c == null) {
                    FileBrowser.this.g.a();
                    for (int i = 0; i < FileBrowser.this.f4475a.size(); i++) {
                        FileBrowser.this.g.a(new f(FileBrowser.this.f4475a.get(i)));
                    }
                    AppFile.i = null;
                    FileBrowser.this.f4476b.clear();
                    FileBrowser.this.d();
                } else if (!FileBrowser.c.serviceAvailable()) {
                    FileBrowser.c = null;
                    FileBrowser.this.d.post(FileBrowser.this.e);
                    return;
                } else {
                    FileBrowser.this.findViewById(R.id.enumerate_progress).setVisibility(0);
                    FileBrowser.this.g.a();
                    FileBrowser.this.h = FileBrowser.c.enumerateDir(new AppFile.EnumerateListener() { // from class: com.artifex.sonui.FileBrowser.5.1
                        @Override // com.artifex.sonui.AppFile.EnumerateListener
                        public void a(ArrayList<AppFile> arrayList) {
                            if (arrayList == null) {
                                FileBrowser.c = null;
                                FileBrowser.this.d.post(FileBrowser.this.e);
                                return;
                            }
                            ArrayList arrayList2 = new ArrayList();
                            int size = arrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                AppFile appFile = arrayList.get(i2);
                                i2++;
                                AppFile appFile2 = appFile;
                                if (appFile2.d() || appFile2.b(FileBrowser.this.getContext())) {
                                    arrayList2.add(appFile2);
                                }
                            }
                            arrayList2.sort(new Comparator<AppFile>(this) { // from class: com.artifex.sonui.FileBrowser.5.1.1
                                @Override // java.util.Comparator
                                public int compare(AppFile appFile3, AppFile appFile4) {
                                    return appFile3.b().compareToIgnoreCase(appFile4.b());
                                }
                            });
                            int size2 = arrayList2.size();
                            int i3 = 0;
                            while (i3 < size2) {
                                Object obj = arrayList2.get(i3);
                                i3++;
                                FileBrowser.this.g.a(new f((AppFile) obj));
                            }
                            int size3 = arrayList2.size();
                            int i4 = R.id.no_documents_found;
                            AnonymousClass5 anonymousClass5 = AnonymousClass5.this;
                            if (size3 == 0) {
                                FileBrowser.this.findViewById(i4).setVisibility(0);
                            } else {
                                FileBrowser.this.findViewById(i4).setVisibility(8);
                            }
                            FileBrowser.this.findViewById(R.id.enumerate_progress).setVisibility(8);
                        }
                    });
                }
                FileBrowser.this.d();
            }
        };
        this.e = runnable;
        this.d.post(runnable);
    }

    public SOEditText getEditText() {
        return this.i;
    }

    public String getFileName() {
        return this.i.getText().toString().trim();
    }

    public AppFile getFolderAppFile() {
        return c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ListView listView, View view, int i, long j) {
        final f fVar = (f) this.g.getItem(i);
        if (fVar != null) {
            if (fVar.f4938a.isCloud() && !AppFile.i()) {
                Utilities.showMessage((Activity) getContext(), getContext().getString(R.string.sodk_editor_connection_error_title), getContext().getString(R.string.sodk_editor_connection_error_body));
            } else if (fVar.f4938a.d()) {
                AppFile.checkCloudPermission(this.k, fVar.f4938a, new AppFile.CloudPermissionChecked() { // from class: com.artifex.sonui.FileBrowser.1
                    @Override // com.artifex.sonui.AppFile.CloudPermissionChecked
                    public void a(boolean z) {
                        if (z) {
                            FileBrowser.this.f4476b.add(fVar.f4938a);
                            FileBrowser.c = fVar.f4938a;
                            FileBrowser.this.d.post(FileBrowser.this.e);
                        }
                    }
                });
            } else {
                this.i.setText(Utilities.removeExtension(fVar.f4938a.f4253b));
            }
        }
    }

    private void a(AppFile appFile, String str, LinearLayout linearLayout) {
        Button button = (Button) LayoutInflater.from(getContext()).inflate(R.layout.sodk_breadcrumb_button, (ViewGroup) null);
        if (str == null) {
            str = appFile.b();
        }
        button.setText(str);
        button.setTag(appFile);
        button.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.FileBrowser.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AppFile appFile2 = (AppFile) view.getTag();
                if (appFile2 == null) {
                    FileBrowser.this.f4476b = new ArrayList<>();
                    appFile2 = null;
                } else {
                    ArrayList<AppFile> arrayList = new ArrayList<>();
                    ArrayList<AppFile> arrayList2 = FileBrowser.this.f4476b;
                    int size = arrayList2.size();
                    int i = 0;
                    while (i < size) {
                        AppFile appFile3 = arrayList2.get(i);
                        i++;
                        AppFile appFile4 = appFile3;
                        arrayList.add(appFile4);
                        if (appFile4.isSameAs(appFile2)) {
                            break;
                        }
                    }
                    FileBrowser.this.f4476b = arrayList;
                }
                FileBrowser.c = appFile2;
                FileBrowser.this.d.post(FileBrowser.this.e);
            }
        });
        linearLayout.addView(button);
    }
}
