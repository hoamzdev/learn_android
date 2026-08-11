package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.supportv1.v4.app.ActivityCompat;
import android.supportv1.v4.content.ContextCompat;
import android.supportv1.v4.content.FileProvider;
import android.supportv1.v7.widget.DefaultItemAnimator;
import android.supportv1.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.a.o.Res;
import com.artifex.solib.ConfigOptions;
import com.artifex.sonui.editor.BaseActivity;
import com.artifex.sonui.editor.NUIActivity;
import com.artifex.sonui.editor.PrintHelperPdf;
import com.artifex.sonui.editor.SODocSession;
import com.artifex.sonui.editor.SOEditText;
import com.artifex.sonui.editor.SOEditTextOnEditorActionListener;
import com.artifex.sonui.editor.SOFileDatabase;
import com.artifex.sonui.editor.SOFileState;
import com.artifex.sonui.editor.SOTextView;
import com.artifex.sonui.editor.SlideShowActivity;
import com.artifex.sonui.editor.ToolbarButton;
import com.artifex.sonui.editor.Utilities;
import com.xlsxreader.excelreader.R;
import defpackage.d8;
import defpackage.e6;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ExplorerActivity extends BaseActivity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final k[] f4395a = new k[6];

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final k[] f4396b = new k[12];
    private static AppFile j;
    private ToolbarButton A;
    private ToolbarButton B;
    private ToolbarButton C;
    private d J;
    private com.artifex.sonui.a K;
    SOTextView c;
    List<AppFile> d;
    private ExplorerListView h;
    private e i;
    private Handler k;
    private Runnable l;
    private SOFileGrid m;
    private j n;
    private int o;
    private RecyclerView p;
    private SOEditText q;
    private SOTextView r;
    private SOTextView s;
    private SortOrderMenu t;
    private ConfigOptions v;
    private ArrayList<ToolbarButton> w;
    private ToolbarButton x;
    private ToolbarButton y;
    private ToolbarButton z;
    private int u = 1;
    List<AppFile> e = new ArrayList();
    private ArrayList<Button> D = new ArrayList<>();
    private boolean E = false;
    private boolean F = false;
    private boolean G = false;
    private int H = 0;
    ArrayList<AppFile> f = new ArrayList<>();
    private AppFile I = null;
    protected boolean g = true;
    private boolean L = false;
    private AppFile M = null;
    private boolean N = false;
    private TextWatcher O = null;

    /* JADX INFO: renamed from: com.artifex.sonui.ExplorerActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Activity f4397a;

        public AnonymousClass1(Activity activity) {
            this.f4397a = activity;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ExplorerActivity.this.F) {
                return;
            }
            AppFile.d(this.f4397a);
            ExplorerActivity explorerActivity = ExplorerActivity.this;
            int iG = Res.g("enumerate_progress");
            explorerActivity.findViewById(iG).setVisibility(8);
            ExplorerActivity.this.r.setVisibility(8);
            if (ExplorerActivity.j == null) {
                ExplorerActivity.this.f.clear();
                ExplorerActivity.this.e();
                ExplorerActivity.this.i.a();
                for (int i = 0; i < ExplorerActivity.this.d.size(); i++) {
                    ExplorerActivity.this.i.a(new f(ExplorerActivity.this.d.get(i)));
                }
                AppFile.i = null;
                ExplorerActivity.this.e.clear();
                ExplorerActivity.this.v();
            } else {
                if (!ExplorerActivity.j.serviceAvailable()) {
                    AppFile unused = ExplorerActivity.j = null;
                    ExplorerActivity.this.s();
                    return;
                }
                ExplorerActivity.this.F = true;
                final int firstVisiblePosition = ExplorerActivity.this.h.getFirstVisiblePosition();
                ExplorerActivity.this.findViewById(iG).setVisibility(0);
                ExplorerActivity.this.r.setVisibility(8);
                ExplorerActivity.this.i.a();
                ExplorerActivity.j.enumerateDir(new AppFile.EnumerateListener() { // from class: com.artifex.sonui.ExplorerActivity.1.1
                    @Override // com.artifex.sonui.AppFile.EnumerateListener
                    public void a(ArrayList<AppFile> arrayList) {
                        int iG2 = Res.g("enumerate_progress");
                        if (arrayList != null) {
                            ExplorerActivity.this.f = new ArrayList<>();
                            int size = arrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                AppFile appFile = arrayList.get(i2);
                                i2++;
                                AppFile appFile2 = appFile;
                                if (appFile2.d() || appFile2.b(AnonymousClass1.this.f4397a)) {
                                    ExplorerActivity.this.f.add(appFile2);
                                }
                            }
                            Collections.sort(ExplorerActivity.this.f, new Comparator<AppFile>() { // from class: com.artifex.sonui.ExplorerActivity.1.1.1
                                @Override // java.util.Comparator
                                /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                                public int compare(AppFile appFile3, AppFile appFile4) {
                                    return ExplorerActivity.this.a(appFile3, appFile4);
                                }
                            });
                            ExplorerActivity.this.i();
                            ExplorerActivity.this.findViewById(iG2).setVisibility(8);
                            if (ExplorerActivity.this.I == null || ExplorerActivity.j == null || !ExplorerActivity.this.I.isSameAs(ExplorerActivity.j)) {
                                ExplorerActivity.this.h.setSelection(0);
                            } else {
                                ExplorerActivity.this.h.setSelection(firstVisiblePosition);
                            }
                            ExplorerActivity.this.I = ExplorerActivity.j;
                        } else {
                            AppFile unused2 = ExplorerActivity.j = null;
                            ExplorerActivity.this.e = new ArrayList();
                            ExplorerActivity.this.findViewById(iG2).setVisibility(8);
                            ExplorerActivity.this.h.setSelection(0);
                        }
                        ExplorerActivity.this.F = false;
                    }
                });
            }
            ExplorerActivity.this.v();
        }
    }

    /* JADX INFO: renamed from: com.artifex.sonui.ExplorerActivity$10, reason: invalid class name */
    public class AnonymousClass10 implements BaseActivity.PermissionResultHandler {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Activity f4402a;

        public AnonymousClass10(Activity activity) {
            this.f4402a = activity;
        }

        @Override // com.artifex.sonui.editor.BaseActivity.PermissionResultHandler
        public boolean handle(int i, String[] strArr, int[] iArr) {
            BaseActivity.setPermissionResultHandler(null);
            if (i != 1) {
                return false;
            }
            if (iArr.length <= 0 || iArr[0] != 0) {
                boolean zShouldShowRequestPermissionRationale = this.f4402a.shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE");
                Activity activity = this.f4402a;
                if (zShouldShowRequestPermissionRationale) {
                    Utilities.yesNoMessage(activity, ExplorerActivity.this.getString(Res.j("sodk_editor_permission_denied")), ExplorerActivity.this.getString(Res.j("sodk_editor_permission_why")), ExplorerActivity.this.getString(Res.j("sodk_editor_yes")), ExplorerActivity.this.getString(Res.j("sodk_editor_no")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.10.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ExplorerActivity.this.b();
                        }
                    }, new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.10.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass10 anonymousClass10 = AnonymousClass10.this;
                            Utilities.showMessageAndWait(anonymousClass10.f4402a, ExplorerActivity.this.getString(Res.j("sodk_editor_permission_denied")), ExplorerActivity.this.getString(Res.j("sodk_editor_permission_final")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.10.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass10.this.f4402a.finish();
                                }
                            });
                        }
                    });
                } else {
                    activity.finish();
                }
            } else {
                ExplorerActivity.this.h();
            }
            return true;
        }
    }

    public interface a {
        void a(AppFile appFile);
    }

    private void a(int i, boolean z) {
        Utilities.hideKeyboard(this);
        this.t.setVisibility(4);
        int iG = Res.g("gettingStarted");
        int iG2 = Res.g("create_new");
        int iG3 = Res.g("recent");
        int iG4 = Res.g("storage");
        int iG5 = Res.g("buy_pro_panel");
        int iG6 = Res.g("create_new_panel");
        int iG7 = Res.g("recent_panel");
        int iG8 = Res.g("preview_panel");
        int iG9 = Res.g("storage_panel");
        if (i == 1) {
            this.t.a(0);
            this.t.setVisibility(0);
            findViewById(iG4).setSelected(true);
            findViewById(iG3).setSelected(false);
            findViewById(iG2).setSelected(false);
            findViewById(iG9).setVisibility(0);
            findViewById(iG8).setVisibility(8);
            findViewById(iG7).setVisibility(8);
            findViewById(iG6).setVisibility(8);
            findViewById(iG5).setVisibility(8);
            findViewById(iG).setVisibility(0);
            this.o = i;
            if (z) {
                s();
                return;
            }
            return;
        }
        if (i == 2) {
            findViewById(iG4).setSelected(false);
            findViewById(iG3).setSelected(true);
            findViewById(iG2).setSelected(false);
            findViewById(iG9).setVisibility(8);
            findViewById(iG8).setVisibility(8);
            findViewById(iG7).setVisibility(0);
            findViewById(iG6).setVisibility(8);
            findViewById(iG5).setVisibility(8);
            findViewById(iG).setVisibility(0);
            this.o = i;
            t();
            return;
        }
        if (i != 3) {
            if (i == 4) {
                findViewById(iG9).setVisibility(8);
                findViewById(iG8).setVisibility(0);
                findViewById(iG7).setVisibility(8);
                findViewById(iG6).setVisibility(8);
                findViewById(iG5).setVisibility(8);
                return;
            }
            return;
        }
        findViewById(iG4).setSelected(false);
        findViewById(iG3).setSelected(false);
        findViewById(iG2).setSelected(true);
        findViewById(iG9).setVisibility(8);
        findViewById(iG8).setVisibility(8);
        findViewById(iG7).setVisibility(8);
        findViewById(iG6).setVisibility(0);
        findViewById(iG5).setVisibility(8);
        findViewById(iG).setVisibility(0);
        this.o = i;
    }

    private void b(int i) {
        int iG = Res.g("templates_sub_panel");
        int iG2 = Res.g("templates_header");
        int iG3 = Res.g("create_new_sub_panel");
        int iG4 = Res.g("create_new_header");
        if (i == 1) {
            findViewById(iG4).setSelected(true);
            findViewById(iG3).setVisibility(0);
            findViewById(iG2).setSelected(false);
            findViewById(iG).setVisibility(8);
            return;
        }
        if (i == 2) {
            findViewById(iG4).setSelected(false);
            findViewById(iG3).setVisibility(8);
            findViewById(iG2).setSelected(true);
            findViewById(iG).setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(AppFile appFile) {
        Utilities.hideKeyboard(this);
        this.N = false;
        a(4);
        this.M = appFile;
        int iC = appFile.c(getBaseContext());
        ((ImageView) findViewById(Res.g("preview_icon"))).setImageResource(appFile.j());
        Drawable drawable = ((ImageView) findViewById(Res.g("preview_file_size_icon"))).getDrawable();
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        drawable.setColorFilter(iC, mode);
        ((ImageView) findViewById(Res.g("preview_last_viewed_icon"))).getDrawable().setColorFilter(iC, mode);
        SOTextView sOTextView = (SOTextView) findViewById(Res.g("preview_long_name"));
        String displayPath = appFile.getDisplayPath();
        String absolutePath = com.artifex.solib.a.b(this).getAbsolutePath();
        if (displayPath.startsWith(absolutePath)) {
            displayPath = getString(Res.j("sodk_editor_my_documents")) + displayPath.substring(absolutePath.length());
        }
        String absolutePath2 = getFilesDir().getAbsolutePath();
        if (displayPath.startsWith(absolutePath2)) {
            displayPath = getString(Res.j("sodk_editor_my_documents2")) + displayPath.substring(absolutePath2.length());
        }
        sOTextView.setText(displayPath);
        Context baseContext = getBaseContext();
        int iD = Res.d("sodk_explorer_preview_text_color");
        sOTextView.setTextColor(baseContext.getColor(iD));
        SOTextView sOTextView2 = (SOTextView) findViewById(Res.g("preview_short_name"));
        sOTextView2.setText(appFile.b());
        sOTextView2.setTextColor(getBaseContext().getColor(iD));
        ((SOTextView) findViewById(Res.g("preview_file_size_string"))).setText(String.format("%.1f KB", Float.valueOf(appFile.e() / 1000.0f)));
        long jF = appFile.f();
        SOFileState value = SOFileDatabase.getDatabase().getValue(appFile.c());
        if (value != null) {
            jF = value.getLastAccess();
        }
        ((SOTextView) findViewById(Res.g("preview_last_viewed_string"))).setText(new SimpleDateFormat("d/M/yyyy HH:mm").format(new Date(jF)));
        this.z.setEnabled(true);
        RecyclerView recyclerView = this.p;
        recyclerView.setAdapter(new i(recyclerView, new i.b() { // from class: com.artifex.sonui.ExplorerActivity.14
            @Override // com.artifex.sonui.i.b
            public void a(SODocSession sODocSession, int i) {
                ((i) ExplorerActivity.this.p.getAdapter()).g();
                ExplorerActivity.this.N = true;
                ExplorerActivity explorerActivity = ExplorerActivity.this;
                explorerActivity.a(sODocSession, i, explorerActivity.M);
            }
        }, new i.c() { // from class: com.artifex.sonui.ExplorerActivity.15
            @Override // com.artifex.sonui.i.c
            public void a(int i, int i2, int i3) {
                if (ExplorerActivity.this.N) {
                    return;
                }
                if (i2 == i3) {
                    i = i2;
                }
                ExplorerActivity.this.c.setText(String.format(ExplorerActivity.this.getString(Res.j("sodk_editor_page_d_of_d")), Integer.valueOf(i), Integer.valueOf(i3)));
            }
        }, new i.a() { // from class: com.artifex.sonui.ExplorerActivity.16
            @Override // com.artifex.sonui.i.a
            public void a(int i, int i2) {
                ExplorerActivity.this.z.setEnabled(false);
                BaseActivity currentActivity = BaseActivity.getCurrentActivity();
                Utilities.showMessageAndWait(BaseActivity.getCurrentActivity(), currentActivity.getString(Res.j("sodk_editor_unable_to_load_document_title")), Utilities.getOpenErrorDescription(currentActivity, i), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.16.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ExplorerActivity.this.onClickPreviewGoBack(null);
                    }
                });
            }

            @Override // com.artifex.sonui.i.a
            public void a() {
                ExplorerActivity.this.onClickPreviewGoBack(null);
            }
        }));
        RecyclerView recyclerView2 = this.p;
        new RecyclerView.OnScrollListener() { // from class: com.artifex.sonui.ExplorerActivity.17
            public void onScrolled(RecyclerView recyclerView3, int i, int i2) {
                ((i) ExplorerActivity.this.p.getAdapter()).d();
            }
        };
        recyclerView2.getClass();
        throw null;
    }

    private void d(AppFile appFile) {
        ChoosePathActivity.a(this, 3, false, new AnonymousClass27(this, appFile), appFile.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        this.d = arrayList;
        arrayList.add(new b(com.artifex.solib.a.b(this).getAbsolutePath(), getString(Res.j("sodk_editor_my_documents"))));
        if (this.g) {
            File filesDir = getFilesDir();
            if (filesDir.exists() && (fileArrListFiles = filesDir.listFiles(new FileFilter() { // from class: com.artifex.sonui.ExplorerActivity.20
                @Override // java.io.FileFilter
                public boolean accept(File file) {
                    String name = file.getName();
                    return !name.startsWith(".") && Utilities.isDocTypeSupported(this, name);
                }
            })) != null && fileArrListFiles.length > 0) {
                this.d.add(new b(filesDir.getAbsolutePath(), getString(Res.j("sodk_editor_my_documents2"))));
            }
        }
        if (com.artifex.solib.a.d(this)) {
            this.d.add(new b(Utilities.getDownloadDirectory(this).getAbsolutePath(), getString(Res.j("sodk_editor_download"))));
            this.d.add(new b(Utilities.getRootDirectory(this).getAbsolutePath(), getString(Res.j("sodk_editor_all"))));
            String sDCardPath = Utilities.getSDCardPath(this);
            if (sDCardPath != null) {
                this.d.add(new b(sDCardPath, "SD Card"));
            }
        }
        AppFile appFileA = c.a("root", "Google Drive", true, true);
        if (appFileA != null) {
            this.d.add(appFileA);
        }
        AppFile appFileB = c.b("0", "Box", true, true);
        if (appFileB != null) {
            this.d.add(appFileB);
        }
        AppFile appFileC = c.c("/", "Dropbox", true, true);
        if (appFileC != null) {
            this.d.add(appFileC);
        }
    }

    private void f() {
        ArrayList<ToolbarButton> arrayList = new ArrayList<>();
        this.w = arrayList;
        arrayList.add(this.x);
        this.w.add(this.y);
        this.w.add(this.A);
        this.w.add(this.C);
        if (this.B != null) {
            boolean zH = this.v.h();
            ToolbarButton toolbarButton = this.B;
            if (zH) {
                toolbarButton.setVisibility(0);
                this.w.add(this.B);
            } else {
                toolbarButton.setVisibility(8);
            }
        }
        if (this.z != null) {
            if (!this.v.m() && !this.v.n()) {
                this.z.setVisibility(8);
            } else {
                this.z.setVisibility(0);
                this.w.add(this.z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        int size = this.w.size();
        int maxWidth = ToolbarButton.getMaxWidth((ToolbarButton[]) this.w.toArray(new ToolbarButton[size]));
        this.H = maxWidth;
        ArrayList<ToolbarButton> arrayList = this.w;
        ToolbarButton.setAllSameSize(maxWidth, (ToolbarButton[]) arrayList.toArray(new ToolbarButton[arrayList.size()]));
        ((LinearLayout) findViewById(Res.g("box5"))).setWeightSum(size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.E = true;
        setContentView(Res.i("sodk_explorer"));
        File fileB = com.artifex.solib.a.b(this);
        if (!fileB.exists()) {
            fileB.mkdir();
        }
        this.c = (SOTextView) findViewById(Res.g("preview_page_counter"));
        this.x = (ToolbarButton) findViewById(Res.g("preview_copy_button"));
        this.y = (ToolbarButton) findViewById(Res.g("preview_delete_button"));
        this.z = (ToolbarButton) findViewById(Res.g("preview_print_button"));
        this.A = (ToolbarButton) findViewById(Res.g("preview_rename_button"));
        this.B = (ToolbarButton) findViewById(Res.g("preview_share_button"));
        this.C = (ToolbarButton) findViewById(Res.g("preview_present_button"));
        this.G = true;
        a(this.x);
        a(this.y);
        a(this.z);
        a(this.A);
        a(this.B);
        a(this.C);
        d dVar = this.J;
        if (dVar != null) {
            dVar.b();
        }
        f();
        com.artifex.solib.a.a((Context) this);
        SOFileDatabase.init(this);
        j = null;
        o();
        SortOrderMenu sortOrderMenu = (SortOrderMenu) findViewById(Res.g("sort_menu"));
        this.t = sortOrderMenu;
        sortOrderMenu.setSortOrderListener(new SortOrderMenu.SortOrderListener() { // from class: com.artifex.sonui.ExplorerActivity.31
            @Override // com.artifex.sonui.SortOrderMenu.SortOrderListener
            public void a(int i) {
                ExplorerActivity.this.u = i;
                ExplorerActivity.this.s();
            }
        });
        a(1);
        b(1);
        int color = getColor(Res.d("sodk_filegrid_template_text"));
        SOFileGrid sOFileGrid = (SOFileGrid) findViewById(Res.g("create_new_grid"));
        final j jVar = new j(getBaseContext(), f4395a, color);
        sOFileGrid.setAdapter((ListAdapter) jVar);
        sOFileGrid.setListener(new SOFileGrid.Listener() { // from class: com.artifex.sonui.ExplorerActivity.32
            @Override // com.artifex.sonui.SOFileGrid.Listener
            public void a(int i) {
                ExplorerActivity.this.a((k) jVar.getItem(i));
            }
        });
        SOFileGrid sOFileGrid2 = (SOFileGrid) findViewById(Res.g("templates_grid"));
        final j jVar2 = new j(getBaseContext(), f4396b, color);
        sOFileGrid2.setAdapter((ListAdapter) jVar2);
        sOFileGrid2.setListener(new SOFileGrid.Listener() { // from class: com.artifex.sonui.ExplorerActivity.33
            @Override // com.artifex.sonui.SOFileGrid.Listener
            public void a(int i) {
                ExplorerActivity.this.a((k) jVar2.getItem(i));
            }
        });
        k[] kVarArrN = n();
        this.m = (SOFileGrid) findViewById(Res.g("recent_grid"));
        j jVar3 = new j(getBaseContext(), kVarArrN, getColor(Res.d("sodk_filegrid_default_text")));
        this.n = jVar3;
        this.m.setAdapter((ListAdapter) jVar3);
        this.m.setListener(new SOFileGrid.Listener() { // from class: com.artifex.sonui.ExplorerActivity.34
            @Override // com.artifex.sonui.SOFileGrid.Listener
            public void a(int i) {
                final AppFile appFile = ((k) ExplorerActivity.this.n.getItem(i)).f;
                appFile.copyFromRemote(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.34.1
                    @Override // com.artifex.sonui.AppFile.AppFileListener
                    public void a(AppFile.a aVar) {
                        if (aVar == AppFile.a.Success) {
                            ExplorerActivity.this.c(appFile);
                        } else {
                            if (aVar == AppFile.a.Cancel) {
                                return;
                            }
                            AnonymousClass34 anonymousClass34 = AnonymousClass34.this;
                            Utilities.showMessage(this, ExplorerActivity.this.getString(Res.j("sodk_editor_error")), ExplorerActivity.this.getString(Res.j("sodk_editor_error_copying_from_remote")));
                        }
                    }
                });
            }
        });
        findViewById(Res.g("storage_icon")).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.ExplorerActivity.35
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ExplorerActivity.this.u();
            }
        });
        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(this);
        myLinearLayoutManager.setOrientation(0);
        RecyclerView recyclerView = (RecyclerView) findViewById(Res.g("preview_pager"));
        this.p = recyclerView;
        recyclerView.setLayoutManager(myLinearLayoutManager);
        this.p.setItemAnimator(new DefaultItemAnimator());
        ExplorerListView explorerListView = (ExplorerListView) findViewById(Res.g("fileListView"));
        this.h = explorerListView;
        explorerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.artifex.sonui.ExplorerActivity.36
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j2) {
                ExplorerActivity explorerActivity = ExplorerActivity.this;
                explorerActivity.a(explorerActivity.h, view, i, j2);
            }
        });
        e eVar = new e(getLayoutInflater(), true);
        this.i = eVar;
        this.h.setAdapter((ListAdapter) eVar);
        this.r = (SOTextView) findViewById(Res.g("explorer_no_documents_found"));
        this.s = (SOTextView) findViewById(Res.g("no_recent_documents_found"));
        this.k = new Handler();
        this.l = new AnonymousClass1(this);
        l();
        findViewById(Res.g("menu")).setOnTouchListener(new View.OnTouchListener() { // from class: com.artifex.sonui.ExplorerActivity.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ExplorerActivity.this.j();
                return true;
            }
        });
        findViewById(Res.g("menu2")).setOnTouchListener(new View.OnTouchListener() { // from class: com.artifex.sonui.ExplorerActivity.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ExplorerActivity.this.j();
                return true;
            }
        });
        findViewById(Res.g("menu3")).setOnTouchListener(new View.OnTouchListener() { // from class: com.artifex.sonui.ExplorerActivity.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ExplorerActivity.this.j();
                return true;
            }
        });
        this.q = (SOEditText) findViewById(Res.g("explorer_search_text_input"));
        q();
        this.q.setOnEditorActionListener(new SOEditTextOnEditorActionListener() { // from class: com.artifex.sonui.ExplorerActivity.5
            @Override // com.artifex.sonui.editor.SOEditTextOnEditorActionListener
            public boolean onEditorAction(SOEditText sOEditText, int i, KeyEvent keyEvent) {
                if (i != 6) {
                    return false;
                }
                Utilities.hideKeyboard(this);
                return true;
            }
        });
        ((ImageView) findViewById(Res.g("search_text_clear"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.ExplorerActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ExplorerActivity.this.q.setText("");
                AppFile appFile = ExplorerActivity.j;
                ExplorerActivity explorerActivity = ExplorerActivity.this;
                if (appFile != null) {
                    explorerActivity.i();
                    return;
                }
                explorerActivity.f.clear();
                ExplorerActivity.this.i.a();
                if (ExplorerActivity.this.d != null) {
                    for (int i = 0; i < ExplorerActivity.this.d.size(); i++) {
                        ExplorerActivity.this.i.a(new f(ExplorerActivity.this.d.get(i)));
                    }
                }
                ExplorerActivity.this.r.setVisibility(8);
            }
        });
        ((ImageView) findViewById(Res.g("search_icon"))).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.ExplorerActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ExplorerActivity.this.q.requestFocus();
                Utilities.showKeyboard(ExplorerActivity.this.getBaseContext());
            }
        });
        s();
        t();
        com.artifex.sonui.a aVar = this.K;
        if (aVar == null || aVar.a(this)) {
            View viewFindViewById = findViewById(Res.g("main_explorer_layout"));
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(0);
            }
            SOFileState autoOpen = SOFileState.getAutoOpen(this);
            if (autoOpen != null) {
                SOFileState.clearAutoOpen(this);
                a(autoOpen, 0);
            }
        }
        com.artifex.sonui.a.a.a(this, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        this.i.a();
        if (!this.f.isEmpty()) {
            ArrayList<AppFile> arrayList = this.f;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                AppFile appFile = arrayList.get(i);
                i++;
                AppFile appFile2 = appFile;
                if (b(appFile2)) {
                    this.i.a(new f(appFile2));
                }
            }
        }
        this.r.setVisibility(this.i.getCount() != 0 ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        View viewFindViewById = findViewById(Res.g("menu_panel"));
        View viewFindViewById2 = findViewById(Res.g("right_panel"));
        findViewById(Res.g("menu_cover")).setVisibility(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById2.getLayoutParams();
        layoutParams.rightMargin = -viewFindViewById.getLayoutParams().width;
        viewFindViewById2.setLayoutParams(layoutParams);
        viewFindViewById.setTranslationX(0.0f);
        viewFindViewById.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        View viewFindViewById = findViewById(Res.g("menu_panel"));
        View viewFindViewById2 = findViewById(Res.g("right_panel"));
        findViewById(Res.g("menu_cover")).setVisibility(8);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewFindViewById2.getLayoutParams();
        layoutParams.rightMargin = 0;
        viewFindViewById2.setLayoutParams(layoutParams);
        if (Utilities.isPhoneDevice(this)) {
            viewFindViewById.setVisibility(8);
        }
    }

    private void l() {
        final View viewFindViewById = findViewById(Res.g("main_explorer_layout"));
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.ExplorerActivity.8
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                viewFindViewById.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                boolean zIsPhoneDevice = Utilities.isPhoneDevice(this);
                int iG = Res.g("device_files_header");
                int iG2 = Res.g("menu3");
                int iG3 = Res.g("menu2");
                int iG4 = Res.g("menu");
                int iG5 = Res.g("menu_and_search");
                int iG6 = Res.g("buy_pro_container");
                int iE = Res.e("sodk_recent_files_top_margin");
                int iG7 = Res.g("recent_file_container");
                int iG8 = Res.g("menu_panel");
                int iG9 = Res.g("create_new_container");
                ExplorerActivity explorerActivity = ExplorerActivity.this;
                if (zIsPhoneDevice) {
                    ExplorerActivity.this.findViewById(iG8).getLayoutParams().width = (int) explorerActivity.getBaseContext().getResources().getDimension(Res.e("sodk_menu_panel_width"));
                    ExplorerActivity.this.findViewById(iG8).setVisibility(8);
                    ExplorerActivity.this.findViewById(iG4).setVisibility(0);
                    ExplorerActivity.this.findViewById(iG3).setVisibility(0);
                    ExplorerActivity.this.findViewById(iG2).setVisibility(0);
                    ExplorerActivity.this.findViewById(iG).setVisibility(8);
                    ExplorerActivity.this.findViewById(Res.g("device_files_container")).setBackgroundResource(Res.f("sodk_explorer_my_documents_phone"));
                    int dimension = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_my_doc_padding_phone"));
                    ExplorerActivity.this.findViewById(Res.g("storage_panel")).setPadding(dimension, 0, dimension, 0);
                    ExplorerActivity.this.findViewById(Res.g("sort_menu")).setTranslationY((int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_sort_menu_y_trans_phone")));
                    ExplorerActivity.this.findViewById(Res.g("recent_files_header")).setVisibility(8);
                    ExplorerActivity.this.findViewById(Res.g("recent_file_grid")).setBackgroundResource(Res.f("sodk_explorer_recent_file_grid_wrapper_phone"));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG7).getLayoutParams();
                    int dimension2 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(iE);
                    Resources resources = ExplorerActivity.this.getBaseContext().getResources();
                    int iE2 = Res.e("sodk_recent_files_margin_phone");
                    int dimension3 = (int) resources.getDimension(iE2);
                    layoutParams.setMargins(dimension3, dimension2, dimension3, dimension3);
                    ExplorerActivity.this.findViewById(iG7).setLayoutParams(layoutParams);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) ((LinearLayout) ExplorerActivity.this.findViewById(iG6)).getLayoutParams();
                    int dimension4 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(iE);
                    int dimension5 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(iE2);
                    layoutParams2.setMargins(dimension5, dimension4, dimension5, dimension5);
                    ExplorerActivity.this.findViewById(iG6).setLayoutParams(layoutParams2);
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG9).getLayoutParams();
                    int dimension6 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_create_new_top_margin"));
                    int dimension7 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_create_new_margin_phone"));
                    layoutParams3.setMargins(dimension7, dimension6, dimension7, dimension7);
                    ExplorerActivity.this.findViewById(iG9).setLayoutParams(layoutParams3);
                    ExplorerActivity.this.findViewById(Res.g("create_new_sub_panel")).setBackgroundResource(Res.f("sodk_explorer_file_grid_wrapper_phone"));
                    ExplorerActivity.this.findViewById(iG9).setBackgroundResource(Res.f("sodk_explorer_create_panel_bg_phone"));
                    ExplorerActivity.this.findViewById(Res.g("create_new_header")).setBackgroundResource(Res.f("sodk_create_new_header_bg_phone"));
                    ExplorerActivity.this.findViewById(Res.g("templates_header")).setBackgroundResource(Res.f("sodk_templates_header_bg_phone"));
                    LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG5).getLayoutParams();
                    layoutParams4.gravity = 0;
                    ExplorerActivity.this.findViewById(iG5).setLayoutParams(layoutParams4);
                    if (ExplorerActivity.this.G) {
                        ExplorerActivity.this.x.hideText();
                        ExplorerActivity.this.y.hideText();
                        ExplorerActivity.this.z.hideText();
                        ExplorerActivity.this.A.hideText();
                        ExplorerActivity.this.B.hideText();
                        ExplorerActivity.this.C.hideText();
                    }
                    ((SOTextView) ExplorerActivity.this.findViewById(Res.g("preview_short_name"))).setTextSize(2, 10.0f);
                    ((SOTextView) ExplorerActivity.this.findViewById(Res.g("preview_long_name"))).setTextSize(2, 10.0f);
                    ExplorerActivity.this.c.setTextSize(2, 10.0f);
                    int iConvertDpToPixel = Utilities.convertDpToPixel(1.0f);
                    ExplorerActivity.this.findViewById(Res.g("box2")).setPadding(iConvertDpToPixel, iConvertDpToPixel, iConvertDpToPixel, iConvertDpToPixel);
                } else {
                    ExplorerActivity.this.findViewById(iG8).getLayoutParams().width = (int) explorerActivity.getBaseContext().getResources().getDimension(Res.e("sodk_menu_panel_width_phone"));
                    ExplorerActivity.this.findViewById(iG8).setVisibility(0);
                    ExplorerActivity.this.findViewById(iG4).setVisibility(8);
                    ExplorerActivity.this.findViewById(iG3).setVisibility(8);
                    ExplorerActivity.this.findViewById(iG2).setVisibility(8);
                    ExplorerActivity.this.findViewById(iG).setVisibility(0);
                    ExplorerActivity.this.findViewById(Res.g("device_files_container")).setBackgroundResource(Res.f("sodk_explorer_my_documents"));
                    int dimension8 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_my_doc_padding"));
                    ExplorerActivity.this.findViewById(Res.g("storage_panel")).setPadding(dimension8, 0, dimension8, 0);
                    ExplorerActivity.this.findViewById(Res.g("sort_menu")).setTranslationY((int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_sort_menu_y_trans")));
                    ExplorerActivity.this.findViewById(Res.g("recent_files_header")).setVisibility(0);
                    ExplorerActivity.this.findViewById(Res.g("recent_file_grid")).setBackgroundResource(Res.f("sodk_explorer_recent_file_grid_wrapper"));
                    LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG7).getLayoutParams();
                    int dimension9 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(iE);
                    Resources resources2 = ExplorerActivity.this.getBaseContext().getResources();
                    int iE3 = Res.e("sodk_recent_files_margin");
                    int dimension10 = (int) resources2.getDimension(iE3);
                    layoutParams5.setMargins(dimension10, dimension9, dimension10, dimension10);
                    ExplorerActivity.this.findViewById(iG7).setLayoutParams(layoutParams5);
                    LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) ((LinearLayout) ExplorerActivity.this.findViewById(iG6)).getLayoutParams();
                    int dimension11 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(iE);
                    int dimension12 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(iE3);
                    layoutParams6.setMargins(dimension12, dimension11, dimension12, dimension12);
                    ExplorerActivity.this.findViewById(iG6).setLayoutParams(layoutParams6);
                    LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG9).getLayoutParams();
                    int dimension13 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_create_new_top_margin"));
                    int dimension14 = (int) ExplorerActivity.this.getBaseContext().getResources().getDimension(Res.e("sodk_create_new_margin"));
                    layoutParams7.setMargins(dimension14, dimension13, dimension14, dimension14);
                    ExplorerActivity.this.findViewById(iG9).setLayoutParams(layoutParams7);
                    ExplorerActivity.this.findViewById(Res.g("create_new_sub_panel")).setBackgroundResource(Res.f("sodk_explorer_file_grid_wrapper"));
                    ExplorerActivity.this.findViewById(iG9).setBackgroundResource(Res.f("sodk_explorer_create_panel_bg"));
                    ExplorerActivity.this.findViewById(Res.g("create_new_header")).setBackgroundResource(Res.f("sodk_create_new_header_bg"));
                    ExplorerActivity.this.findViewById(Res.g("templates_header")).setBackgroundResource(Res.f("sodk_templates_header_bg"));
                    LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG5).getLayoutParams();
                    layoutParams8.gravity = 5;
                    ExplorerActivity.this.findViewById(iG5).setLayoutParams(layoutParams8);
                    if (ExplorerActivity.this.G) {
                        ExplorerActivity.this.x.showText();
                        ExplorerActivity.this.y.showText();
                        ExplorerActivity.this.z.showText();
                        ExplorerActivity.this.A.showText();
                        ExplorerActivity.this.B.showText();
                        ExplorerActivity.this.C.showText();
                    }
                    ((SOTextView) ExplorerActivity.this.findViewById(Res.g("preview_short_name"))).setTextSize(2, 18.0f);
                    ((SOTextView) ExplorerActivity.this.findViewById(Res.g("preview_long_name"))).setTextSize(2, 18.0f);
                    ExplorerActivity.this.c.setTextSize(2, 18.0f);
                    int iConvertDpToPixel2 = Utilities.convertDpToPixel(10.0f);
                    ExplorerActivity.this.findViewById(Res.g("box2")).setPadding(iConvertDpToPixel2, iConvertDpToPixel2, iConvertDpToPixel2, iConvertDpToPixel2);
                    ExplorerActivity.this.k();
                }
                int width = ExplorerActivity.this.findViewById(Res.g("main_explorer_layout")).getWidth();
                int iConvertDpToPixel3 = Utilities.convertDpToPixel(this.getResources().getInteger(Res.h("sodk_editor_explorer_left_width")));
                ((LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(iG8).getLayoutParams()).width = iConvertDpToPixel3;
                ((LinearLayout.LayoutParams) ExplorerActivity.this.findViewById(Res.g("right_panel")).getLayoutParams()).width = width - iConvertDpToPixel3;
                viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.ExplorerActivity.8.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        viewFindViewById.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        ExplorerActivity.this.t.a(0);
                    }
                });
                ExplorerActivity.this.g();
            }
        });
    }

    private void m() {
        final i iVar;
        RecyclerView recyclerView = this.p;
        if (recyclerView == null || (iVar = (i) recyclerView.getAdapter()) == null) {
            return;
        }
        final int iC = iVar.c();
        final View viewFindViewById = findViewById(Res.g("preview_pager_container"));
        viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.ExplorerActivity.9
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                viewFindViewById.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                viewFindViewById.getWidth();
                int height = viewFindViewById.getHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ExplorerActivity.this.p.getLayoutParams();
                layoutParams.width = -2;
                layoutParams.height = (height * 8) / 10;
                ExplorerActivity.this.p.setLayoutParams(layoutParams);
                iVar.e();
                RecyclerView recyclerView2 = ExplorerActivity.this.p;
                if (recyclerView2.g) {
                    return;
                }
                recyclerView2.setScrollState(0);
                throw null;
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:19:0x006b  */
    private k[] n() {
        String foreignData;
        SOFileDatabase database = SOFileDatabase.getDatabase();
        ArrayList<SOFileDatabase.StateAndKey> statesAndKeys = database.getStatesAndKeys();
        Collections.sort(statesAndKeys, new Comparator<SOFileDatabase.StateAndKey>() { // from class: com.artifex.sonui.ExplorerActivity.11
            @Override // java.util.Comparator
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(SOFileDatabase.StateAndKey stateAndKey, SOFileDatabase.StateAndKey stateAndKey2) {
                long lastAccess = stateAndKey.state.getLastAccess();
                long lastAccess2 = stateAndKey2.state.getLastAccess();
                if (lastAccess < lastAccess2) {
                    return 1;
                }
                return lastAccess > lastAccess2 ? -1 : 0;
            }
        });
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < statesAndKeys.size(); i++) {
            SOFileDatabase.StateAndKey stateAndKey = statesAndKeys.get(i);
            SOFileState sOFileState = stateAndKey.state;
            if (sOFileState == null) {
                database.deleteEntry(stateAndKey.key);
                sOFileState.deleteThumbnailFile();
            } else if (!sOFileState.getUserPath().contains(com.artifex.solib.a.c(this)) && (foreignData = sOFileState.getForeignData()) != null) {
                AppFile appFileE = AppFile.e(foreignData);
                if (!appFileE.isCloud()) {
                    String strC = appFileE.c();
                    String thumbnail = sOFileState.getThumbnail();
                    if (com.artifex.solib.a.b(strC) && com.artifex.solib.a.b(thumbnail)) {
                        String strB = appFileE.b();
                        arrayList.add(new k(strB, thumbnail, strB, appFileE.getDisplayPath(), appFileE));
                    } else {
                        database.deleteEntry(stateAndKey.key);
                        sOFileState.deleteThumbnailFile();
                    }
                }
            }
        }
        k[] kVarArr = new k[arrayList.size()];
        arrayList.toArray(kVarArr);
        return kVarArr;
    }

    private void o() {
        k[] kVarArr = f4395a;
        kVarArr[0] = new k("document-blank.docx", "docx-2007.png", "Document.docx");
        kVarArr[1] = new k("presentation-blank.pptx", "pptx-2007.png", "Presentation.pptx");
        kVarArr[2] = new k("spreadsheet-blank.xlsx", "xlsx-2007.png", "Spreadsheet.xlsx");
        kVarArr[3] = new k("document-office2003.doc", "doc-1997.png", "2003 document.doc", "Document.docx");
        kVarArr[4] = new k("presentation-office2003.ppt", "ppt-1997.png", "2003 presentation.ppt", "Presentation.pptx");
        kVarArr[5] = new k("spreadsheet-office2003.xls", "xls-1997.png", "2003 spreadsheet.xls", "Spreadsheet.xlsx");
        k[] kVarArr2 = f4396b;
        kVarArr2[0] = new k("template-docx-a.docx", "template-docx-a.png", "Resume.docx");
        kVarArr2[1] = new k("template-docx-b.docx", "template-docx-b.png", "Report II.docx");
        kVarArr2[2] = new k("template-pptx-a.pptx", "template-pptx-a.png", "Dark II.pptx");
        kVarArr2[3] = new k("template-pptx-b.pptx", "template-pptx-b.png", "Light II.pptx");
        kVarArr2[4] = new k("template-xlsx-a.xlsx", "template-xlsx-a.png", "Expenses II.xlsx");
        kVarArr2[5] = new k("template-xlsx-b.xlsx", "template-xlsx-b.png", "Invoice.xlsx");
        kVarArr2[6] = new k("document-letter.docx", "document-letter.png", "Letter.docx");
        kVarArr2[7] = new k("document-report.docx", "document-report.png", "Report I.docx");
        kVarArr2[8] = new k("presentation-dark-amber.pptx", "presentation-dark-amber.png", "Dark I.pptx");
        kVarArr2[9] = new k("presentation-light-bubbles.pptx", "presentation-light-bubbles.png", "Light I.pptx");
        kVarArr2[10] = new k("spreadsheet-chart-data.xlsx", "spreadsheet-chart-data.png", "Chart.xlsx");
        kVarArr2[11] = new k("spreadsheet-expense-budget.xlsx", "spreadsheet-expense-budget.png", "Expenses I.xlsx");
    }

    private void p() {
        this.O = new TextWatcher() { // from class: com.artifex.sonui.ExplorerActivity.18
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    if (!ExplorerActivity.this.w().isEmpty() || ExplorerActivity.j != null) {
                        ExplorerActivity.this.i();
                        return;
                    }
                    ExplorerActivity.this.f.clear();
                    ExplorerActivity.this.i.a();
                    if (ExplorerActivity.this.d != null) {
                        for (int i4 = 0; i4 < ExplorerActivity.this.d.size(); i4++) {
                            ExplorerActivity.this.i.a(new f(ExplorerActivity.this.d.get(i4)));
                        }
                    }
                    ExplorerActivity.this.r.setVisibility(8);
                } catch (Exception e) {
                    Log.e("ExplorerActivity", "onTextChanged: " + e.getMessage());
                }
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        };
    }

    private void q() {
        if (this.O == null) {
            p();
        }
        SOEditText sOEditText = this.q;
        if (sOEditText != null) {
            sOEditText.addTextChangedListener(this.O);
        }
    }

    private void r() {
        TextWatcher textWatcher;
        SOEditText sOEditText = this.q;
        if (sOEditText == null || (textWatcher = this.O) == null) {
            return;
        }
        sOEditText.removeTextChangedListener(textWatcher);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void s() {
        Handler handler = this.k;
        if (handler != null) {
            handler.post(this.l);
        }
    }

    private void t() {
        k[] kVarArrN = n();
        j jVar = new j(getBaseContext(), kVarArrN, getBaseContext().getColor(Res.d("sodk_filegrid_default_text")));
        this.n = jVar;
        this.m.setAdapter((ListAdapter) jVar);
        this.s.setVisibility(kVarArrN.length == 0 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void u() {
        Button button;
        int size = this.D.size();
        if (size < 2 || (button = this.D.get(size - 2)) == null) {
            return;
        }
        button.performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void v() {
        LinearLayout linearLayout = (LinearLayout) findViewById(Res.g("names_bar"));
        linearLayout.removeAllViews();
        this.D = new ArrayList<>();
        a((AppFile) null, getString(Res.j("sodk_editor_storage")), linearLayout);
        for (AppFile appFile : this.e) {
            linearLayout.addView((SOTextView) LayoutInflater.from(this).inflate(Res.i("sodk_breadcrumb_slash"), (ViewGroup) null));
            a(appFile, (String) null, linearLayout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String w() {
        SOEditText sOEditText = this.q;
        return (sOEditText == null || sOEditText.getText() == null) ? "" : this.q.getText().toString();
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        int i3;
        com.artifex.sonui.a aVar = this.K;
        if (aVar == null || i != aVar.a()) {
            super.onActivityResult(i, i2, intent);
            return;
        }
        boolean zA = this.K.a(i, i2, intent, this);
        View viewFindViewById = findViewById(Res.g("main_explorer_layout"));
        if (zA) {
            SOFileState autoOpen = SOFileState.getAutoOpen(this);
            i3 = 0;
            if (autoOpen != null) {
                SOFileState.clearAutoOpen(this);
                a(autoOpen, 0);
            }
        } else {
            i3 = 4;
        }
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(i3);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (findViewById(Res.g("preview_panel")).getVisibility() == 0) {
            i iVar = (i) this.p.getAdapter();
            iVar.g();
            iVar.h();
            a(this.o);
            return;
        }
        View viewFindViewById = findViewById(Res.g("menu_panel"));
        if (Utilities.isPhoneDevice(this) && viewFindViewById.getVisibility() == 0) {
            k();
        } else if (findViewById(Res.g("storage_panel")).getVisibility() != 0 || this.D.size() <= 1) {
            moveTaskToBack(true);
        } else {
            ((Button) e6.g(2, this.D)).performClick();
        }
    }

    public void onClickControlCopy(View view) {
        d(((f) view.getTag()).f4938a);
    }

    public void onClickControlDelete(View view) {
        final AppFile appFile = ((f) view.getTag()).f4938a;
        String strB = appFile.b();
        boolean zD = appFile.d();
        int iJ = Res.j("sodk_editor_delete");
        if (zD) {
            Utilities.showMessage(this, getString(iJ), String.format(getString(Res.j("sodk_editor_is_a_directory")), strB));
        } else {
            Utilities.yesNoMessage(this, getString(iJ), String.format(getString(Res.j("sodk_editor_do_you_really_want_to_delete")), strB), getString(iJ), getString(Res.j("sodk_editor_cancel")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.21
                @Override // java.lang.Runnable
                public void run() {
                    appFile.deleteFile(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.21.1
                        @Override // com.artifex.sonui.AppFile.AppFileListener
                        public void a(AppFile.a aVar) {
                            ExplorerActivity.this.s();
                        }
                    });
                }
            }, null);
        }
    }

    public void onClickControlLogout(View view) {
        final AppFile appFile = ((f) view.getTag()).f4938a;
        Utilities.yesNoMessage(this, String.format(getString(Res.j("sodk_editor_confirm_logout_title")), appFile.b()), String.format(getString(Res.j("sodk_editor_confirm_logout_message")), new Object[0]), getString(Res.j("sodk_editor_log_out")), getString(Res.j("sodk_editor_cancel")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.22
            @Override // java.lang.Runnable
            public void run() {
                appFile.Logout(new AppFile.LogoutListener() { // from class: com.artifex.sonui.ExplorerActivity.22.1
                    @Override // com.artifex.sonui.AppFile.LogoutListener
                    public void a() {
                        ChooseDocListItemView.a(ExplorerActivity.this.h);
                    }
                });
            }
        }, new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.23
            @Override // java.lang.Runnable
            public void run() {
                ChooseDocListItemView.a(ExplorerActivity.this.h);
            }
        });
    }

    public void onClickControlRename(View view) {
        a(((f) view.getTag()).f4938a, false);
    }

    public void onClickControlShare(View view) {
        b(((f) view.getTag()).f4938a, true);
    }

    public void onClickCreateHeader(View view) {
        b(1);
    }

    public void onClickCreateNew(View view) {
        k();
        a(3);
    }

    public void onClickGettingStarted(View view) {
        c(getString(R.menu.main_menu));
    }

    public void onClickMenuCover(View view) {
        k();
    }

    public void onClickPreviewGoBack(View view) {
        i iVar = (i) this.p.getAdapter();
        iVar.g();
        iVar.h();
        a(this.o);
    }

    public void onClickRecent(View view) {
        k();
        t();
        a(2);
    }

    public void onClickStorage(View view) {
        k();
        a(1);
        s();
    }

    public void onClickSupport(View view) {
        String str;
        try {
            DoSupportActivity.class.getMethod("show", Activity.class).invoke(null, this);
        } catch (ClassNotFoundException unused) {
        } catch (IllegalAccessException unused2) {
            str = "onClickSupport: IllegalAccessException";
            Log.e("ExplorerActivity", str);
        } catch (LinkageError unused3) {
            str = "onClickSupport:  LinkageError";
            Log.e("ExplorerActivity", str);
        } catch (NoSuchMethodException unused4) {
            str = "onClickSupport: NoSuchMethodException";
            Log.e("ExplorerActivity", str);
        } catch (SecurityException unused5) {
            str = "onClickSupport: SecurityException";
            Log.e("ExplorerActivity", str);
        } catch (InvocationTargetException unused6) {
            str = "onClickSupport: InvocationTargetException";
            Log.e("ExplorerActivity", str);
        }
    }

    public void onClickTemplatesHeader(View view) {
        b(2);
    }

    public void onClickUserGuide(View view) {
        c(getString(Res.j("USER_GUIDE")));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m();
        l();
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        d dVar = this.J;
        if (dVar != null) {
            dVar.a();
        }
        super.onDestroy();
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        if (Utilities.isChromebook(this)) {
            PrintHelperPdf.setPrinting(false);
        }
        r();
        super.onPause();
    }

    public void onPreviewCopy(View view) {
        d(this.M);
    }

    public void onPreviewDelete(View view) {
        String str = String.format(getString(Res.j("sodk_editor_do_you_really_want_to_delete")), this.M.b());
        int iJ = Res.j("sodk_editor_delete");
        Utilities.yesNoMessage(this, getString(iJ), str, getString(iJ), getString(Res.j("sodk_editor_cancel")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.30
            @Override // java.lang.Runnable
            public void run() {
                ((i) ExplorerActivity.this.p.getAdapter()).g();
                SODocSession sODocSessionA = ((i) ExplorerActivity.this.p.getAdapter()).a();
                if (sODocSessionA != null) {
                    sODocSessionA.abort();
                }
                ExplorerActivity.this.M.deleteFile(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.30.1
                    @Override // com.artifex.sonui.AppFile.AppFileListener
                    public void a(AppFile.a aVar) {
                        ExplorerActivity.this.s();
                        ExplorerActivity explorerActivity = ExplorerActivity.this;
                        explorerActivity.a(explorerActivity.o);
                    }
                });
            }
        }, null);
    }

    public void onPreviewPrint(View view) {
        if (this.v.m() || this.v.n()) {
            g gVar = (g) Utilities.getDataLeakHandlers();
            if (gVar == null) {
                d8.a();
                return;
            }
            try {
                Utilities.setFileStateForPrint(SOFileState.toString(SOFileDatabase.getDatabase().getValue(this.M.c())));
                gVar.initDataLeakHandlers(this);
                gVar.printHandler(((i) this.p.getAdapter()).f());
            } catch (Exception unused) {
            }
        }
    }

    public void onPreviewRename(View view) {
        a(this.M, true);
    }

    public void onPreviewShare(View view) {
        if (this.v.h()) {
            b(this.M, false);
        }
    }

    public void onPreviewSlideshow(View view) {
        Utilities.hideKeyboard(this);
        SlideShowActivity.setSession(((i) this.p.getAdapter()).a());
        Intent intent = new Intent(this, (Class<?>) SlideShowActivity.class);
        intent.putExtra("SESSION", true);
        startActivityForResult(intent, 1);
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        c();
    }

    public void onClickBuyPro(View view) {
    }

    public void onClickRestorePurchase(View view) {
    }

    public void onClickSubscribeMonth(View view) {
    }

    public void onClickSubscribeYear(View view) {
    }

    /* JADX INFO: renamed from: com.artifex.sonui.ExplorerActivity$27, reason: invalid class name */
    public class AnonymousClass27 implements ChoosePathActivity.a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        final /* synthetic */ Activity f4437a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        final /* synthetic */ AppFile f4438b;

        public AnonymousClass27(Activity activity, AppFile appFile) {
            this.f4437a = activity;
            this.f4438b = appFile;
        }

        @Override // com.artifex.sonui.ChoosePathActivity.a
        public void a(FileBrowser fileBrowser) {
            String fileName = fileBrowser.getFileName();
            if (!Utilities.isValidFilename(fileName)) {
                Utilities.showMessage(this.f4437a, "", ExplorerActivity.this.getString(Res.j("sodk_editor_invalid_file_name")));
                return;
            }
            final AppFile appFileChild = fileBrowser.getFolderAppFile().child(defpackage.h.j(fileName, ".", com.artifex.solib.a.g(this.f4438b.b())));
            if (this.f4438b.isSameAs(appFileChild)) {
                Utilities.showMessage(this.f4437a, "", ExplorerActivity.this.getString(Res.j("sodk_editor_copy_same_file")));
            } else {
                appFileChild.exists(new AppFile.ExistsListener() { // from class: com.artifex.sonui.ExplorerActivity.27.1
                    @Override // com.artifex.sonui.AppFile.ExistsListener
                    public void a(boolean z) {
                        AnonymousClass27 anonymousClass27 = AnonymousClass27.this;
                        if (!z) {
                            ExplorerActivity.this.b(anonymousClass27.f4438b, appFileChild);
                        } else {
                            Activity activity = anonymousClass27.f4437a;
                            Utilities.yesNoMessage(activity, activity.getString(Res.j("sodk_editor_replace_file_title")), AnonymousClass27.this.f4437a.getString(Res.j("sodk_editor_replace_file_body")), AnonymousClass27.this.f4437a.getString(Res.j("sodk_editor_yes")), AnonymousClass27.this.f4437a.getString(Res.j("sodk_editor_no")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.27.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    AnonymousClass27 anonymousClass28 = AnonymousClass27.this;
                                    ExplorerActivity.this.b(anonymousClass28.f4438b, appFileChild);
                                }
                            }, new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.27.1.2
                                @Override // java.lang.Runnable
                                public void run() {
                                }
                            });
                        }
                    }
                });
            }
        }

        @Override // com.artifex.sonui.ChoosePathActivity.a
        public void a() {
        }
    }

    public String b(String str) {
        return str;
    }

    public void b() {
        if (ContextCompat.a(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            h();
        } else {
            BaseActivity.setPermissionResultHandler(new AnonymousClass10(this));
            ActivityCompat.b(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(AppFile appFile, AppFile appFile2) {
        if (!appFile.isCloud() && !appFile2.isCloud()) {
            com.artifex.solib.a.a(appFile.g(), appFile2.g(), true);
            s();
        } else {
            final AppFile appFileDuplicate = appFile.duplicate();
            final AppFile appFileDuplicate2 = appFile2.duplicate();
            appFileDuplicate.copyFromRemote(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.28
                @Override // com.artifex.sonui.AppFile.AppFileListener
                public void a(AppFile.a aVar) {
                    AppFile appFile3 = appFileDuplicate2;
                    appFile3.c = appFileDuplicate.c;
                    appFile3.copyToRemote(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.28.1
                        @Override // com.artifex.sonui.AppFile.AppFileListener
                        public void a(AppFile.a aVar2) {
                            ExplorerActivity.this.s();
                        }
                    });
                }
            });
        }
    }

    private void b(final AppFile appFile, boolean z) {
        if (z) {
            appFile.copyFromRemote(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.29
                @Override // com.artifex.sonui.AppFile.AppFileListener
                public void a(AppFile.a aVar) {
                    if (aVar == AppFile.a.Success) {
                        ExplorerActivity.this.e(appFile);
                    } else {
                        if (aVar == AppFile.a.Cancel) {
                            return;
                        }
                        Activity activity = this;
                        Utilities.showMessage(activity, activity.getString(Res.j("sodk_editor_share_error_title")), this.getString(Res.j("sodk_editor_share_error_body")));
                    }
                }
            });
        } else {
            e(appFile);
        }
    }

    private boolean b(AppFile appFile) {
        String strW = w();
        if (strW.isEmpty()) {
            return true;
        }
        return appFile.b().toLowerCase().matches(a(strW.toLowerCase()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(AppFile appFile) {
        boolean zB = com.artifex.solib.a.b(appFile.c());
        int iJ = Res.j("sodk_editor_no_documents_found");
        int iJ2 = Res.j("sodk_editor_share_error_title");
        if (!zB) {
            Utilities.showMessage(this, getString(iJ2), getString(iJ));
            return;
        }
        try {
            Uri uriB = FileProvider.b(this, getPackageName() + ".provider", new File(b(appFile.c())));
            String mimeType = Utilities.getMimeType(uriB.toString());
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", uriB);
            intent.setType(mimeType);
            intent.addFlags(1);
            startActivity(Intent.createChooser(intent, getString(Res.j("sodk_editor_share_with"))));
        } catch (Exception unused) {
            Utilities.showMessage(this, getString(iJ2), getString(iJ));
        }
    }

    public static String a(String str) {
        String str2;
        StringBuilder sb;
        StringBuilder sbT;
        boolean zContains = str.contains("*");
        String strN = "";
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '*') {
                sbT = defpackage.h.t(strN, ".*");
            } else {
                if (cCharAt == '\\') {
                    sbT = defpackage.h.t(strN, "\\\\");
                } else {
                    if ("?.+[{}()^$|/".indexOf(cCharAt) >= 0) {
                        sb = e6.t(strN);
                        str2 = "\\";
                    } else {
                        str2 = strN;
                        sb = new StringBuilder();
                    }
                    strN = e6.n(sb, str2, cCharAt);
                }
            }
            strN = sbT.toString();
        }
        return !zContains ? e6.l(".*", strN, ".*") : strN;
    }

    public void a() {
        AppNUIActivity.a();
        this.v = ConfigOptions.a();
        getIntent().getExtras();
        b();
        com.artifex.sonui.a.a.a(this, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        a(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(AppFile appFile, AppFile appFile2) {
        String strB;
        String strB2;
        switch (this.u) {
            case 2:
                strB = appFile2.b();
                strB2 = appFile.b();
                break;
            case 3:
                long jF = appFile.f();
                long jF2 = appFile2.f();
                if (jF > jF2) {
                    return 1;
                }
                return jF2 > jF ? -1 : 0;
            case 4:
                long jF3 = appFile.f();
                long jF4 = appFile2.f();
                if (jF3 > jF4) {
                    return -1;
                }
                return jF4 > jF3 ? 1 : 0;
            case 5:
                return (!appFile.d() ? com.artifex.solib.a.g(appFile.b()) : "000").compareToIgnoreCase(appFile2.d() ? "000" : com.artifex.solib.a.g(appFile2.b()));
            case 6:
                return (appFile2.d() ? "000" : com.artifex.solib.a.g(appFile2.b())).compareToIgnoreCase(!appFile.d() ? com.artifex.solib.a.g(appFile.b()) : "000");
            case 7:
                long jE = appFile.e();
                long jE2 = appFile2.e();
                if (jE > jE2) {
                    return 1;
                }
                return jE2 > jE ? -1 : 0;
            case 8:
                long jE3 = appFile.e();
                long jE4 = appFile2.e();
                if (jE3 > jE4) {
                    return -1;
                }
                return jE4 > jE3 ? 1 : 0;
            default:
                strB = appFile.b();
                strB2 = appFile2.b();
                break;
        }
        return strB.compareToIgnoreCase(strB2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ListView listView, View view, int i, long j2) {
        if (this.L) {
            return;
        }
        this.L = true;
        f fVar = (f) this.i.getItem(i);
        if (fVar != null) {
            if (fVar.f4938a.isCloud() && !AppFile.i()) {
                Utilities.showMessage(this, getString(Res.j("sodk_editor_connection_error_title")), getString(Res.j("sodk_editor_connection_error_body")));
                this.L = false;
                return;
            }
            boolean zD = fVar.f4938a.d();
            final AppFile appFile = fVar.f4938a;
            if (zD) {
                AppFile.checkCloudPermission(this, appFile, new AppFile.CloudPermissionChecked() { // from class: com.artifex.sonui.ExplorerActivity.12
                    @Override // com.artifex.sonui.AppFile.CloudPermissionChecked
                    public void a(boolean z) {
                        if (z) {
                            ExplorerActivity.this.e.add(appFile);
                            AppFile unused = ExplorerActivity.j = appFile;
                            ExplorerActivity.this.k.post(ExplorerActivity.this.l);
                        }
                        ExplorerActivity.this.L = false;
                    }
                });
            } else {
                appFile.copyFromRemote(this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.13
                    @Override // com.artifex.sonui.AppFile.AppFileListener
                    public void a(AppFile.a aVar) {
                        if (aVar == AppFile.a.Success) {
                            ExplorerActivity.this.c(appFile);
                        } else if (aVar != AppFile.a.Cancel) {
                            Utilities.showMessage(this, ExplorerActivity.this.getString(Res.j("sodk_editor_error")), ExplorerActivity.this.getString(Res.j("sodk_editor_error_copying_from_remote")));
                        }
                        ExplorerActivity.this.L = false;
                    }
                });
            }
        }
    }

    private void a(AppFile appFile, String str, LinearLayout linearLayout) {
        Button button = (Button) LayoutInflater.from(this).inflate(Res.i("sodk_breadcrumb_button"), (ViewGroup) null);
        if (str == null) {
            str = appFile.b();
        }
        button.setText(str);
        button.setTag(appFile);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.ExplorerActivity.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AppFile appFile2 = (AppFile) view.getTag();
                if (appFile2 == null) {
                    ExplorerActivity.this.e = new ArrayList();
                    appFile2 = null;
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (AppFile appFile3 : ExplorerActivity.this.e) {
                        arrayList.add(appFile3);
                        if (appFile3.isSameAs(appFile2)) {
                            break;
                        }
                    }
                    ExplorerActivity.this.e = arrayList;
                }
                AppFile unused = ExplorerActivity.j = appFile2;
                ExplorerActivity.this.k.post(ExplorerActivity.this.l);
            }
        });
        linearLayout.addView(button);
        this.D.add(button);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AppFile appFile, final String str, final a aVar) {
        final SOFileDatabase database = SOFileDatabase.getDatabase();
        final SOFileState value = database.getValue(appFile.c);
        appFile.rename(str, this, new AppFile.AppFileListener() { // from class: com.artifex.sonui.ExplorerActivity.26
            @Override // com.artifex.sonui.AppFile.AppFileListener
            public void a(AppFile.a aVar2) {
                SOFileState sOFileState = value;
                if (sOFileState != null) {
                    String thumbnail = sOFileState.getThumbnail();
                    database.deleteEntry(appFile.c);
                    AppFile appFile2 = appFile;
                    appFile2.f4253b = str;
                    value.setUserPath(appFile2.c);
                    value.setOpenedPath(appFile.c);
                    String strUniqueThumbFilePath = SOFileDatabase.uniqueThumbFilePath();
                    com.artifex.solib.a.a(thumbnail, strUniqueThumbFilePath);
                    value.setThumbnail(strUniqueThumbFilePath);
                    value.setForeignData(appFile.toString());
                }
                aVar.a(appFile);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AppFile appFile, final boolean z) {
        String strB = appFile.b();
        final String strG = com.artifex.solib.a.g(strB);
        if (strG != null && !strG.isEmpty()) {
            strB = strB.substring(0, (strB.length() - strG.length()) - 1);
        }
        final SOEditText sOEditText = new SOEditText(this);
        sOEditText.setText(strB);
        sOEditText.setSelection(strB.length());
        sOEditText.setSingleLine();
        String str = String.format(getString(Res.j("sodk_editor_enter_a_new_name")), strB);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, Res.k("sodk_dialog_style"));
        int iJ = Res.j("sodk_editor_rename");
        builder.setTitle(getString(iJ)).setMessage(str).setView(sOEditText).setPositiveButton(iJ, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.ExplorerActivity.25
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                String string = sOEditText.getText().toString();
                if (!Utilities.isValidFilename(string)) {
                    Utilities.showMessageAndWait(this, "", ExplorerActivity.this.getString(Res.j("sodk_editor_invalid_file_name")), new Runnable() { // from class: com.artifex.sonui.ExplorerActivity.25.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass25 anonymousClass25 = AnonymousClass25.this;
                            ExplorerActivity.this.a(appFile, z);
                        }
                    });
                    return;
                }
                String str2 = strG;
                if (str2 != null && !str2.isEmpty()) {
                    StringBuilder sbT = defpackage.h.t(string, ".");
                    sbT.append(strG);
                    string = sbT.toString();
                }
                Utilities.hideKeyboard(this, sOEditText);
                ExplorerActivity.this.a(appFile, string, new a() { // from class: com.artifex.sonui.ExplorerActivity.25.2
                    @Override // com.artifex.sonui.ExplorerActivity.a
                    public void a(AppFile appFile2) {
                        ExplorerActivity.this.s();
                        AnonymousClass25 anonymousClass25 = AnonymousClass25.this;
                        if (!z || appFile2 == null) {
                            return;
                        }
                        ExplorerActivity.this.c(appFile2);
                    }
                });
            }
        }).setNegativeButton(getString(Res.j("sodk_editor_cancel")), new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.ExplorerActivity.24
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Utilities.hideKeyboard(this, sOEditText);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SODocSession sODocSession, int i, AppFile appFile) {
        Utilities.hideKeyboard(this);
        Intent intent = new Intent(this, (Class<?>) AppNUIActivity.class);
        intent.putExtra("SESSION", true);
        NUIActivity.setSession(sODocSession);
        intent.putExtra("STARTED_FROM_EXPLORER", true);
        intent.putExtra("START_PAGE", i);
        intent.putExtra("FOREIGN_DATA", appFile.toString());
        d dVar = this.J;
        if (dVar != null) {
            dVar.b();
        }
        startActivityForResult(intent, 1);
    }

    private void a(SOFileState sOFileState, int i) {
        if (sOFileState.isTemplate() || com.artifex.solib.a.b(sOFileState.getUserPath())) {
            Utilities.hideKeyboard(this);
            Intent intent = new Intent(this, (Class<?>) AppNUIActivity.class);
            intent.setAction("android.intent.action.VIEW");
            intent.putExtra("STARTED_FROM_EXPLORER", true);
            intent.putExtra("START_PAGE", i);
            intent.putExtra("STATE", SOFileState.toString(sOFileState));
            d dVar = this.J;
            if (dVar != null) {
                dVar.b();
            }
            startActivityForResult(intent, 1);
        }
    }

    private void a(ToolbarButton toolbarButton) {
        String string = toolbarButton.getText().toString();
        String str = new String();
        boolean z = false;
        for (int i = 0; i < string.length(); i++) {
            char cCharAt = string.charAt(i);
            if (Character.isWhitespace(cCharAt)) {
                if (!z) {
                    str = defpackage.h.D(str, "\n");
                }
                z = true;
            } else {
                str = str + cCharAt;
                z = false;
            }
        }
        toolbarButton.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(k kVar) {
        String strB = com.artifex.solib.a.b(getBaseContext(), kVar.f4989a);
        if (strB != null) {
            a(strB, 1);
            return;
        }
        Utilities.showMessage(this, "", getString(Res.j("sodk_editor_error_opening")) + kVar.f4989a);
    }

    private void a(String str, int i) {
        Utilities.hideKeyboard(this);
        Uri uriFromFile = Uri.fromFile(new File(str));
        Intent intent = new Intent(this, (Class<?>) AppNUIActivity.class);
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uriFromFile);
        intent.putExtra("STARTED_FROM_EXPLORER", true);
        intent.putExtra("START_PAGE", i);
        d dVar = this.J;
        if (dVar != null) {
            dVar.b();
        }
        startActivityForResult(intent, 1);
    }

    public void c() {
        q();
        d dVar = this.J;
        if (dVar != null) {
            dVar.b();
        }
        f();
        if (this.o == 2) {
            t();
        }
        if (this.E) {
            boolean z = this.N;
            int i = this.o;
            if (z) {
                a(i, true);
                this.N = false;
            } else if (i == 1) {
                s();
            }
        }
    }

    private void c(String str) {
        String strB = com.artifex.solib.a.b(getBaseContext(), str);
        if (strB != null) {
            a(strB, 1);
            return;
        }
        Utilities.showMessage(this, "", getString(Res.j("sodk_editor_error_opening")) + str);
    }
}
