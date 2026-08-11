package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SODocSaveListener;
import com.artifex.solib.SOLib;
import com.artifex.solib.SOSelectionLimits;
import com.artifex.solib.j;
import com.artifex.solib.k;
import com.artifex.solib.p;
import com.onesignal.core.internal.backend.impl.FeatureFlagsBackendService;
import com.xlsxreader.excelreader.Utils;
import com.xlsxreader.excelreader.adapter.ColorPickAdapter;
import com.xlsxreader.excelreader.customviews.BottomBtn;
import com.xlsxreader.excelreader.customviews.EditBtn;
import com.xlsxreader.excelreader.listener.GoPageDialogListener;
import com.xlsxreader.excelreader.model.ColorModel;
import com.xlsxreader.excelreader.ui.dialog.GoPageDialog;
import com.xlsxreader.excelreader.ui.dialog.LoadingDialog;
import defpackage.d8;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class NUIDocView extends FrameLayout implements View.OnClickListener, DocViewHost {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static int OVERSIZE_MARGIN;
    private static NUIDocView mNUIDocView;
    private int RS;
    private final SOBitmap[] S;
    private boolean T;
    private String U;
    private int V;
    private boolean W;
    private boolean aa;
    private k ah;
    private final ArrayList<String> ai;
    private int an;
    private boolean ao;
    private boolean ap;
    private boolean au;
    private boolean aw;
    protected ConstraintLayout bottomContainer;
    private LinearLayout bottomToolBar;
    private ConstraintLayout bottomToolBarEdit;
    protected EditBtn btnAlignCenter;
    protected EditBtn btnAlignJustify;
    protected EditBtn btnAlignLeft;
    protected EditBtn btnAlignRight;
    protected ImageView btnBack;
    private AppCompatImageView btnClearSearchText;
    protected AppCompatImageView btnCloseEdit;
    protected EditBtn btnCopy;
    protected EditBtn btnCut;
    protected BottomBtn btnEditTab;
    protected EditBtn btnFontDown;
    protected EditBtn btnFontUp;
    protected AppCompatImageView btnFullScreen;
    protected EditBtn btnIndentDecrease;
    protected EditBtn btnIndentIncrease;
    protected EditBtn btnInsertImage;
    protected EditBtn btnInsertPhoto;
    protected EditBtn btnListBullets;
    protected EditBtn btnListNumbers;
    protected EditBtn btnPaste;
    protected ImageView btnPrint;
    protected ImageView btnRedo;
    protected BottomBtn btnRotateTab;
    protected ImageView btnSave;
    protected ImageView btnSaveAsPdf;
    private AppCompatImageView btnSearchNext;
    private AppCompatImageView btnSearchPrevious;
    protected BottomBtn btnSearchTab;
    protected EditBtn btnTextBg;
    protected EditBtn btnTextBold;
    protected EditBtn btnTextColor;
    protected EditBtn btnTextItalic;
    protected EditBtn btnTextStrikeTrough;
    protected EditBtn btnTextUnder;
    protected BottomBtn btnThumbnailTab;
    protected ImageView btnUndo;
    private boolean c;
    private Boolean e;
    private SOEditText edtSearchTextInput;
    protected ConstraintLayout headerContainer;
    private InputView inputView;
    private String k;
    protected boolean keyboardShown;
    private String l;
    private LinearLayout llBgColorContainer;
    private LinearLayout llTextColorContainer;
    private p m;
    protected PageAdapter mAdapter;
    protected ConfigOptions mConfigOptions;
    private Context mContext;
    private DocListPagesView mDocListPageViews;
    private DocView mDocView;
    NUIView1.OnDoneListener mDoneListener;
    protected boolean mFinished;
    protected boolean mIsSession;
    private int mKeyboardHeight;
    protected int mPageCount;
    private LoadingDialog mProgressDialog;
    private Runnable mRunnable;
    private SOFileState mSOFileState;
    protected SODocSession mSession;
    private SOFileDatabase mSoFileDatabase;
    protected Uri mStartUri;
    protected SOFileState mState;
    private View mView;
    private SODataLeakHandlers n;
    private int pageNumber;
    private ProgressDialog progressDialog;
    private LinearLayout searchBackForwardContainer;
    protected AppCompatImageView shadowBottom;
    protected ConstraintLayout toolbarContainer;
    protected ConstraintLayout toolbarEditContainer;
    protected ConstraintLayout toolbarSearchContainer;
    private AppCompatTextView tvCancelSearch;
    protected TextView tvPageNumber;
    protected TextView tvTittle;
    private LinearLayout undoForwardContainer;

    /* JADX INFO: renamed from: com.artifex.sonui.editor.NUIDocView$14, reason: invalid class name */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class AnonymousClass14 implements Runnable {
        public AnonymousClass14() {
        }

        @Override // java.lang.Runnable
        public void run() {
            final ProgressDialog progressDialogCreateAndShowWaitSpinner = Utilities.createAndShowWaitSpinner(NUIDocView.this.getContext());
            NUIDocView nUIDocView = NUIDocView.this;
            if (nUIDocView.isActivityAlive((Activity) nUIDocView.mContext)) {
                NUIDocView.this.mSession.getDoc().a(NUIDocView.this.mSOFileState.getInternalPath(), new SODocSaveListener() { // from class: com.artifex.sonui.editor.NUIDocView.14.1
                    @Override // com.artifex.solib.SODocSaveListener
                    public void onComplete(int i, int i2) {
                        progressDialogCreateAndShowWaitSpinner.dismiss();
                        AnonymousClass14 anonymousClass14 = AnonymousClass14.this;
                        if (i != 0) {
                            Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error), String.format(NUIDocView.this.activity().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error_saving_document_code), Integer.valueOf(i2)));
                            return;
                        }
                        NUIDocView.this.mSOFileState.saveFile();
                        NUIDocView.this.updateUIAppearance();
                        if (NUIDocView.this.n != null) {
                            NUIDocView.this.n.postSaveHandler(new SOSaveAsComplete() { // from class: com.artifex.sonui.editor.NUIDocView.14.1.1
                                @Override // com.artifex.sonui.editor.SOSaveAsComplete
                                public void onComplete(int i3, String str) {
                                    NUIDocView.this.reloadFile();
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.artifex.sonui.editor.NUIDocView$16, reason: invalid class name */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class AnonymousClass16 implements Runnable {

        /* JADX INFO: renamed from: com.artifex.sonui.editor.NUIDocView$16$3, reason: invalid class name */
        /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
        public class AnonymousClass3 implements DialogInterface.OnClickListener {

            /* JADX INFO: renamed from: com.artifex.sonui.editor.NUIDocView$16$3$1, reason: invalid class name */
            /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
            public class AnonymousClass1 implements Runnable {
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    String str = NUIDocView.this.k;
                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                    if (str != null) {
                        NUIDocView.this.onCustomSaveButton();
                        return;
                    }
                    boolean z = NUIDocView.this.T;
                    AnonymousClass3 anonymousClass4 = AnonymousClass3.this;
                    if (z) {
                        NUIDocView.this.a(true);
                        return;
                    }
                    NUIDocView nUIDocView = NUIDocView.this;
                    if (nUIDocView.isActivityAlive((Activity) nUIDocView.mContext)) {
                        NUIDocView.this.mSession.getDoc().a(NUIDocView.this.mSOFileState.getInternalPath(), new SODocSaveListener() { // from class: com.artifex.sonui.editor.NUIDocView.16.3.1.1
                            @Override // com.artifex.solib.SODocSaveListener
                            public void onComplete(int i, int i2) {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                if (i != 0) {
                                    NUIDocView.this.mSOFileState.closeFile();
                                    Utilities.showMessage(NUIDocView.this.activity(), NUIDocView.this.activity().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error), String.format(NUIDocView.this.activity().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error_saving_document_code), Integer.valueOf(i2)));
                                    return;
                                }
                                NUIDocView.this.mSOFileState.saveFile();
                                SODataLeakHandlers sODataLeakHandlers = NUIDocView.this.n;
                                AnonymousClass1 anonymousClass2 = AnonymousClass1.this;
                                if (sODataLeakHandlers != null) {
                                    NUIDocView.this.n.postSaveHandler(new SOSaveAsComplete() { // from class: com.artifex.sonui.editor.NUIDocView.16.3.1.1.1
                                        @Override // com.artifex.sonui.editor.SOSaveAsComplete
                                        public void onComplete(int i3, String str2) {
                                            NUIDocView.this.mSOFileState.closeFile();
                                            NUIDocView.this.prefinish();
                                        }
                                    });
                                } else {
                                    NUIDocView.this.mSOFileState.closeFile();
                                    NUIDocView.this.prefinish();
                                }
                            }
                        });
                    }
                }
            }

            public AnonymousClass3() {
            }

            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                NUIDocView.this.preSaveQuestion(new AnonymousClass1(), new Runnable(this) { // from class: com.artifex.sonui.editor.NUIDocView.16.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                });
            }
        }

        public AnonymousClass16() {
        }

        @Override // java.lang.Runnable
        public void run() {
            int identifier;
            int i = com.xlsxreader.excelreader.R.string.sodk_editor_save;
            if (NUIDocView.this.k != null && (identifier = NUIDocView.this.getContext().getResources().getIdentifier("secure_save_upper", "string", NUIDocView.this.getContext().getPackageName())) != 0) {
                i = identifier;
            }
            new AlertDialog.Builder(NUIDocView.this.activity(), com.xlsxreader.excelreader.R.style.sodk_editor_alert_dialog_style).setTitle(com.xlsxreader.excelreader.R.string.sodk_editor_document_has_been_modified).setMessage(com.xlsxreader.excelreader.R.string.sodk_editor_would_you_like_to_save_your_changes).setCancelable(false).setPositiveButton(i, new AnonymousClass3()).setNegativeButton(com.xlsxreader.excelreader.R.string.sodk_editor_discard, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.NUIDocView.16.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    dialogInterface.dismiss();
                    NUIDocView.this.mSOFileState.closeFile();
                    NUIDocView.C(NUIDocView.this);
                    NUIDocView.this.prefinish();
                }
            }).setNeutralButton(com.xlsxreader.excelreader.R.string.sodk_editor_continue_editing, new DialogInterface.OnClickListener(this) { // from class: com.artifex.sonui.editor.NUIDocView.16.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class TabData {
        public int contentId;
        public int layoutId;
        public String name;
        public int visibility;

        public TabData(NUIDocView nUIDocView, String str, int i, int i2, int i3) {
            this.name = str;
            this.contentId = i;
            this.layoutId = i2;
            this.visibility = i3;
        }
    }

    public NUIDocView(Context context) {
        super(context);
        this.S = new SOBitmap[]{null, null};
        this.ai = new ArrayList<>();
        this.mFinished = false;
        this.keyboardShown = false;
        this.mConfigOptions = null;
        this.mIsSession = false;
        this.mStartUri = null;
        this.mState = null;
        this.mDoneListener = null;
        this.inputView = null;
        this.RS = 0;
        this.T = false;
        this.U = null;
        this.V = -1;
        this.W = false;
        this.aa = false;
        this.pageNumber = 0;
        this.mView = null;
        this.mKeyboardHeight = 0;
        this.an = 0;
        this.ao = false;
        this.ap = false;
        this.progressDialog = null;
        this.mRunnable = null;
        this.au = false;
        this.aw = false;
        this.c = false;
        this.m = null;
        init(context);
    }

    public static /* bridge */ /* synthetic */ void C(NUIDocView nUIDocView) {
        nUIDocView.e = Boolean.FALSE;
    }

    private void a() {
        int height = this.mView.getHeight();
        int i = (height * 15) / 100;
        Rect rect = new Rect();
        this.mView.getWindowVisibleDisplayFrame(rect);
        this.mKeyboardHeight = height - rect.bottom;
        Resources resources = getContext().getResources();
        int identifier = resources.getIdentifier("config_showNavigationBar", "bool", FeatureFlagsBackendService.TURBINE_FEATURES_PLATFORM_ANDROID);
        if ((identifier > 0 && resources.getBoolean(identifier)) || Utilities.isEmulator()) {
            int identifier2 = resources.getIdentifier("navigation_bar_height", "dimen", FeatureFlagsBackendService.TURBINE_FEATURES_PLATFORM_ANDROID);
            this.mKeyboardHeight -= identifier2 > 0 ? resources.getDimensionPixelSize(identifier2) : 0;
        }
        if (this.mKeyboardHeight >= i) {
            if (this.keyboardShown) {
                return;
            }
            onShowKeyboard(true);
        } else {
            this.mKeyboardHeight = 0;
            if (this.keyboardShown) {
                onShowKeyboard(false);
            }
        }
    }

    private void c() {
        this.c = false;
        ViewGroup viewGroup = (ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(getLayoutId(), (ViewGroup) null);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocView.3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                NUIDocView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (NUIDocView.this.c) {
                    return;
                }
                NUIDocView.this.mConfigOptions.b();
                NUIDocView.this.afterFirstLayoutComplete();
                NUIDocView.this.c = true;
            }
        });
        addView(viewGroup);
    }

    public static NUIDocView currentNUIDocView() {
        return mNUIDocView;
    }

    private void d() {
        try {
            k.b(activity());
            j jVarE = SOLib.e();
            if (jVarE == null) {
                try {
                    throw new ClassNotFoundException();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    jVarE.a(activity());
                } catch (SecurityException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (ClassNotFoundException | ExceptionInInitializerError | LinkageError | SecurityException | Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        LoadingDialog loadingDialog = this.mProgressDialog;
        if (loadingDialog != null) {
            try {
                Context context = loadingDialog.getContext();
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    if (activity.isFinishing() || activity.isDestroyed()) {
                        return;
                    }
                }
                if (this.mProgressDialog.isShowing() && this.mProgressDialog.getWindow() != null && this.mProgressDialog.getWindow().getDecorView().getWindowToken() != null) {
                    this.mProgressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.mProgressDialog = null;
            }
        }
    }

    private void e() {
        try {
            SODataLeakHandlers dataLeakHandlers = Utilities.getDataLeakHandlers();
            this.n = dataLeakHandlers;
            if (dataLeakHandlers == null) {
                try {
                    throw new ClassNotFoundException();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    dataLeakHandlers.initDataLeakHandlers(activity());
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (ClassNotFoundException | ExceptionInInitializerError | LinkageError | SecurityException | Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        int i;
        Point realScreenSize = Utilities.getRealScreenSize(activity());
        int i2 = realScreenSize.x > realScreenSize.y ? 2 : 1;
        if (this.ao || (i2 != (i = this.an) && i != 0)) {
            onOrientationChange();
        }
        this.ao = false;
        this.an = i2;
    }

    private void g() {
        DocListPagesView docListPagesView;
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.requestLayout();
        }
        if (usePagesView() && isPageListVisible() && (docListPagesView = this.mDocListPageViews) != null) {
            docListPagesView.requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        Utilities.hideKeyboard(getContext());
    }

    private void init(Context context) {
        this.mContext = context;
        this.mView = ((Activity) getContext()).getWindow().getDecorView();
        com.artifex.solib.a.a(context);
        this.V = context.getResources().getConfiguration().keyboard;
        this.mConfigOptions = ConfigOptions.a();
    }

    private void k() {
        if (this.m == null) {
            this.m = new AnonymousClass4();
            this.mSession.getDoc().a(this.m);
        }
        this.mSession.getDoc().c(false);
    }

    private void n() {
        if (this.btnListNumbers.isSelected()) {
            this.mSession.getDoc().B();
            return;
        }
        boolean zIsSelected = this.btnListBullets.isSelected();
        SODocSession sODocSession = this.mSession;
        if (zIsSelected) {
            sODocSession.getDoc().C();
        } else {
            sODocSession.getDoc().A();
        }
    }

    private boolean o() {
        int[] indentationLevel = this.mSession.getDoc().getIndentationLevel();
        return indentationLevel != null && indentationLevel[0] < indentationLevel[1];
    }

    private boolean p() {
        int[] indentationLevel = this.mSession.getDoc().getIndentationLevel();
        return indentationLevel != null && indentationLevel[0] > 0;
    }

    private void q() {
        DocListPagesView docListPagesView;
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.releaseBitmaps();
        }
        if (usePagesView() && (docListPagesView = this.mDocListPageViews) != null) {
            docListPagesView.releaseBitmaps();
        }
        int i = 0;
        while (true) {
            SOBitmap[] sOBitmapArr = this.S;
            if (i >= sOBitmapArr.length) {
                return;
            }
            SOBitmap sOBitmap = sOBitmapArr[i];
            if (sOBitmap != null) {
                sOBitmap.a().recycle();
                this.S[i] = null;
            }
            i++;
        }
    }

    private void s() {
        DocListPagesView docListPagesView;
        SOBitmap[] sOBitmapArr;
        SOBitmap[] sOBitmapArr2;
        DocView docView = this.mDocView;
        if (docView != null && (sOBitmapArr2 = this.S) != null) {
            docView.setBitmaps(sOBitmapArr2);
        }
        if (!usePagesView() || (docListPagesView = this.mDocListPageViews) == null || (sOBitmapArr = this.S) == null) {
            return;
        }
        docListPagesView.setBitmaps(sOBitmapArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchText() {
        Utilities.hideKeyboard(getContext());
        u();
        String string = this.edtSearchTextInput.getText().toString();
        SODoc doc = getDoc();
        doc.b(string);
        doc.q();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisableClick() {
        setEnableButton(this, false);
    }

    private void setEnableButton(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt != this.btnBack) {
                    setEnableButton(childAt, z);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFooterText(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        String name = new File(str).getName();
        boolean zIsEmpty = name.isEmpty();
        TextView textView = this.tvTittle;
        if (zIsEmpty) {
            textView.setText(str);
        } else {
            textView.setText(name);
        }
    }

    private void setValid(boolean z) {
        DocListPagesView docListPagesView;
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.setValid(z);
        }
        if (!usePagesView() || (docListPagesView = this.mDocListPageViews) == null) {
            return;
        }
        docListPagesView.setValid(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupForDocType(String str) {
        a(str);
        s();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgressDialog() {
        LoadingDialog loadingDialog = new LoadingDialog(getContext());
        loadingDialog.setContentView(com.xlsxreader.excelreader.R.layout.dialog_loading);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        this.mProgressDialog = loadingDialog;
        Window window = loadingDialog.getWindow();
        this.mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        window.setLayout(-1, -2);
        this.mProgressDialog.show();
    }

    private void u() {
        if (this.progressDialog == null) {
            new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.5
                @Override // java.lang.Runnable
                public void run() {
                    SODoc doc = NUIDocView.this.getDoc();
                    if (doc != null && NUIDocView.this.progressDialog == null && doc.p()) {
                        NUIDocView nUIDocView = NUIDocView.this;
                        nUIDocView.progressDialog = new ProgressDialog(nUIDocView.getContext(), com.xlsxreader.excelreader.R.style.sodk_editor_alert_dialog_style);
                        NUIDocView.this.progressDialog.setMessage(NUIDocView.this.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_searching) + "...");
                        NUIDocView.this.progressDialog.setCancelable(false);
                        NUIDocView.this.progressDialog.setButton(-2, NUIDocView.this.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_cancel), new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.NUIDocView.5.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NUIDocView.this.getDoc().cancelSearch();
                            }
                        });
                        NUIDocView.this.progressDialog.show();
                    }
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void v() {
        ProgressDialog progressDialog = this.progressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.progressDialog = null;
        }
    }

    private void w() {
        DocView docView = getDocView();
        docView.smoothScrollBy(0, (docView.getHeight() * 9) / 10, 400);
    }

    private void x() {
        DocView docView = getDocView();
        docView.smoothScrollBy(0, ((-docView.getHeight()) * 9) / 10, 400);
    }

    private void y() {
        DocView docView = getDocView();
        docView.smoothScrollBy(0, docView.getHeight() / 20, 100);
    }

    private void z() {
        DocView docView = getDocView();
        docView.smoothScrollBy(0, (-docView.getHeight()) / 20, 100);
    }

    public Activity activity() {
        return (Activity) getContext();
    }

    public void addDeleteOnClose(String str) {
        this.ai.add(str);
    }

    public void afterFirstLayoutComplete() {
        this.mFinished = false;
        if (this.mConfigOptions.r()) {
            SOFileDatabase.init(activity());
        }
        createEditButtons();
        createEditButtons2();
        createInsertButtons();
        createRecyclerColor();
        this.btnBack = (ImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.img_back);
        this.btnUndo = (ImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.img_undo);
        this.btnRedo = (ImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.img_redo);
        this.btnFullScreen = (AppCompatImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_full_screen);
        this.headerContainer = (ConstraintLayout) findViewById(com.xlsxreader.excelreader.R.id.header);
        this.bottomContainer = (ConstraintLayout) findViewById(com.xlsxreader.excelreader.R.id.bottom_container);
        this.shadowBottom = (AppCompatImageView) findViewById(com.xlsxreader.excelreader.R.id.shadow_bottom);
        this.btnCloseEdit = (AppCompatImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.iv_exit_edit);
        this.bottomToolBar = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.bottomToolBar);
        this.bottomToolBarEdit = (ConstraintLayout) findViewById(com.xlsxreader.excelreader.R.id.bottomEditToolBar);
        this.btnRotateTab = (BottomBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btnRotateTab);
        this.btnEditTab = (BottomBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btnEditTab);
        this.btnSearchTab = (BottomBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btnSearchTab);
        this.btnThumbnailTab = (BottomBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btnThumbnailTab);
        this.toolbarSearchContainer = (ConstraintLayout) findViewById(com.xlsxreader.excelreader.R.id.search_container);
        this.toolbarContainer = (ConstraintLayout) findViewById(com.xlsxreader.excelreader.R.id.toolbar_container);
        this.toolbarEditContainer = (ConstraintLayout) findViewById(com.xlsxreader.excelreader.R.id.cl_toolbar_edit);
        this.searchBackForwardContainer = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.ll_search_back_forward);
        this.undoForwardContainer = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.doUndoLayout);
        this.btnClearSearchText = (AppCompatImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.iv_clear);
        this.tvCancelSearch = (AppCompatTextView) createToolbarButton(com.xlsxreader.excelreader.R.id.tv_back);
        this.btnSearchNext = (AppCompatImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_search_forward);
        this.btnSearchPrevious = (AppCompatImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_search_back);
        if (!this.mConfigOptions.c()) {
            ImageView imageView = this.btnUndo;
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            ImageView imageView2 = this.btnRedo;
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
        }
        this.edtSearchTextInput = (SOEditText) findViewById(com.xlsxreader.excelreader.R.id.et_search_text);
        TextView textView = (TextView) findViewById(com.xlsxreader.excelreader.R.id.footer_page_text);
        this.tvPageNumber = textView;
        textView.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.editor.NUIDocView.6
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GoPageDialog goPageDialog = new GoPageDialog(NUIDocView.this.getContext(), new GoPageDialogListener() { // from class: com.artifex.sonui.editor.NUIDocView.6.1
                    @Override // com.xlsxreader.excelreader.listener.GoPageDialogListener
                    public void onPageNumber(int i) {
                        NUIDocView.this.goToPage(i);
                    }
                }, NUIDocView.this.getPageCount());
                Window window = goPageDialog.getWindow();
                goPageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                window.setLayout(-1, -2);
                goPageDialog.show();
            }
        });
        this.edtSearchTextInput.setOnEditorActionListener(new SOEditTextOnEditorActionListener() { // from class: com.artifex.sonui.editor.NUIDocView.7
            @Override // com.artifex.sonui.editor.SOEditTextOnEditorActionListener
            public boolean onEditorAction(SOEditText sOEditText, int i, KeyEvent keyEvent) {
                if (i != 5) {
                    return false;
                }
                NUIDocView.this.onSearchNext();
                return true;
            }
        });
        this.edtSearchTextInput.setOnKeyListener(new OnKeyListener() { // from class: com.artifex.sonui.editor.NUIDocView.8
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return i == 67 && NUIDocView.this.edtSearchTextInput.getSelectionStart() == 0 && NUIDocView.this.edtSearchTextInput.getSelectionEnd() == 0;
            }
        });
        this.btnSearchNext.setEnabled(false);
        this.btnSearchPrevious.setEnabled(false);
        this.edtSearchTextInput.addTextChangedListener(new TextWatcher() { // from class: com.artifex.sonui.editor.NUIDocView.9
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    NUIDocView.this.btnClearSearchText.setVisibility(0);
                } else if (charSequence.length() == 0) {
                    NUIDocView.this.btnClearSearchText.setVisibility(8);
                }
                NUIDocView.this.setSearchStart();
                NUIDocView.this.btnSearchNext.setEnabled(charSequence.toString().length() > 0);
                NUIDocView.this.btnSearchPrevious.setEnabled(charSequence.toString().length() > 0);
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.edtSearchTextInput.setCustomSelectionActionModeCallback(Utilities.editFieldDlpHandler);
        this.btnPrint = (ImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.btnPrint);
        this.btnSave = (ImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.btnSave);
        this.btnSaveAsPdf = (ImageView) createToolbarButton(com.xlsxreader.excelreader.R.id.btnSaveAsPdf);
        onDeviceSizeChange();
        setConfigurableButtons();
        this.mAdapter = createAdapter();
        DocView docViewCreateMainView = createMainView(activity());
        this.mDocView = docViewCreateMainView;
        docViewCreateMainView.setHost(this);
        this.mDocView.setAdapter(this.mAdapter);
        this.mDocView.setConfigOptions(this.mConfigOptions);
        if (usePagesView()) {
            DocListPagesView docListPagesView = new DocListPagesView(activity());
            this.mDocListPageViews = docListPagesView;
            docListPagesView.setHost(this);
            this.mDocListPageViews.setAdapter(this.mAdapter);
            this.mDocListPageViews.setMainView(this.mDocView);
            this.mDocListPageViews.setBorderColor(this.mDocView.getBorderColor());
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(com.xlsxreader.excelreader.R.id.doc_inner_container);
        relativeLayout.addView(this.mDocView, 0);
        this.mDocView.setup(relativeLayout);
        if (usePagesView()) {
            relativeLayout.addView(this.mDocListPageViews);
            this.mDocListPageViews.setup(relativeLayout);
            this.mDocListPageViews.setCanManipulatePages(canCanManipulatePages());
        }
        this.tvTittle = (TextView) findViewById(com.xlsxreader.excelreader.R.id.tvTittle);
        if (this.mConfigOptions.r()) {
            this.mSoFileDatabase = SOFileDatabase.getDatabase();
        }
        final Activity activity = activity();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocView.10
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                NUIDocView nUIDocView = NUIDocView.this;
                if (nUIDocView.mFinished) {
                    nUIDocView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    nUIDocView.f();
                }
            }
        });
        DocView docView = this.mDocView;
        if (docView != null && docView.getViewTreeObserver() != null) {
            final DocView docView2 = this.mDocView;
            docView2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocView.11
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    NUIDocView nUIDocView;
                    PageAdapter pageAdapter;
                    docView2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    NUIDocView nUIDocView2 = NUIDocView.this;
                    if (nUIDocView2.mIsSession) {
                        nUIDocView2.l = nUIDocView2.mSession.getUserPath();
                        NUIDocView nUIDocView3 = NUIDocView.this;
                        nUIDocView3.setupForDocType(nUIDocView3.l);
                        if (!NUIDocView.this.mConfigOptions.r()) {
                            d8.a();
                            return;
                        }
                        NUIDocView.this.showProgressDialog();
                        NUIDocView nUIDocView4 = NUIDocView.this;
                        nUIDocView4.mSOFileState = nUIDocView4.mSoFileDatabase.stateForPath(NUIDocView.this.l, NUIDocView.this.T);
                        NUIDocView.this.mSOFileState.setForeignData(NUIDocView.this.U);
                        NUIDocView nUIDocView5 = NUIDocView.this;
                        nUIDocView5.mSession.setFileState(nUIDocView5.mSOFileState);
                        NUIDocView.this.mSOFileState.openFile(NUIDocView.this.T);
                        NUIDocView.this.mSOFileState.setHasChanges(false);
                        NUIDocView nUIDocView6 = NUIDocView.this;
                        nUIDocView6.setFooterText(nUIDocView6.mSOFileState.getUserPath());
                        docView2.setDoc(NUIDocView.this.mSession.getDoc());
                        if (NUIDocView.this.usePagesView()) {
                            NUIDocView.this.mDocListPageViews.setDoc(NUIDocView.this.mSession.getDoc());
                        }
                        if (NUIDocView.this.mSession.getDoc() != null) {
                            NUIDocView nUIDocView7 = NUIDocView.this;
                            nUIDocView7.mAdapter.setDoc(nUIDocView7.mSession.getDoc());
                        }
                        NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSession.SODocSessionLoadListener() { // from class: com.artifex.sonui.editor.NUIDocView.11.1
                            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                            public void onCancel() {
                                NUIDocView.this.dismissProgressDialog();
                            }

                            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                            public void onDocComplete() {
                                NUIDocView.this.dismissProgressDialog();
                                NUIDocView.this.onDocCompleted();
                                NUIDocView.this.setPageNumberText();
                            }

                            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                            public void onError(int i, int i2) {
                                if (NUIDocView.this.mSession.isOpen()) {
                                    NUIDocView.this.setDisableClick();
                                    NUIDocView.this.dismissProgressDialog();
                                    if (NUIDocView.this.mSession.isCancelled() && i == 6) {
                                        return;
                                    }
                                    String openErrorDescription = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), i);
                                    AnonymousClass11 anonymousClass11 = AnonymousClass11.this;
                                    Utilities.showMessage(activity, NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error), openErrorDescription);
                                }
                            }

                            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                            public void onLayoutCompleted() {
                                NUIDocView.this.onLayoutChanged();
                            }

                            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                            public void onPageLoad(int i) {
                                NUIDocView.this.dismissProgressDialog();
                                NUIDocView.this.onPageLoaded(i);
                            }

                            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                            public void onSelectionChanged(int i, int i2) {
                                NUIDocView.this.onSelectionMonitor();
                            }
                        });
                        if (NUIDocView.this.usePagesView()) {
                            NUIDocView.this.mDocListPageViews.setScale(NUIDocView.this.getResources().getInteger(com.xlsxreader.excelreader.R.integer.sodk_editor_pagelist_width_percentage) / 100.0f);
                        }
                    } else {
                        if (nUIDocView2.mState == null) {
                            Uri uri = nUIDocView2.mStartUri;
                            String scheme = uri.getScheme();
                            if (scheme == null || !scheme.equalsIgnoreCase("content")) {
                                NUIDocView.this.l = uri.getPath();
                                if (NUIDocView.this.l == null) {
                                    Utilities.showMessage(activity, NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_invalid_file_name), NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error_opening_from_other_app));
                                    uri.toString();
                                    return;
                                }
                            } else {
                                String strB = com.artifex.solib.a.b(NUIDocView.this.getContext(), uri);
                                if (strB.equals("---fileOpen")) {
                                    Utilities.showMessage(activity, NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_content_error), NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error_opening_from_other_app));
                                    return;
                                }
                                boolean zStartsWith = strB.startsWith("---");
                                NUIDocView nUIDocView8 = NUIDocView.this;
                                if (zStartsWith) {
                                    String string = nUIDocView8.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_cant_create_temp_file);
                                    Utilities.showMessage(activity, NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_content_error), NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error_opening_from_other_app) + ": \n\n" + string);
                                    return;
                                }
                                nUIDocView8.l = strB;
                                if (NUIDocView.this.T) {
                                    NUIDocView.this.addDeleteOnClose(strB);
                                }
                            }
                            NUIDocView nUIDocView9 = NUIDocView.this;
                            nUIDocView9.setupForDocType(nUIDocView9.l);
                            nUIDocView9.setFooterText(nUIDocView9.l);
                            NUIDocView.this.showProgressDialog();
                            boolean zR = NUIDocView.this.mConfigOptions.r();
                            NUIDocView nUIDocView10 = NUIDocView.this;
                            nUIDocView10.mSOFileState = zR ? nUIDocView10.mSoFileDatabase.stateForPath(NUIDocView.this.l, NUIDocView.this.T) : new SOFileStateDummy(nUIDocView10.l);
                            NUIDocView.this.mSOFileState.openFile(NUIDocView.this.T);
                            NUIDocView.this.mSOFileState.setHasChanges(false);
                            nUIDocView9.mSession = new SODocSession(activity, nUIDocView9.ah);
                            NUIDocView nUIDocView11 = NUIDocView.this;
                            nUIDocView11.mSession.setFileState(nUIDocView11.mSOFileState);
                            NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSession.SODocSessionLoadListener() { // from class: com.artifex.sonui.editor.NUIDocView.11.3
                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onCancel() {
                                    NUIDocView.this.setDisableClick();
                                    NUIDocView.this.dismissProgressDialog();
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onDocComplete() {
                                    NUIDocView nUIDocView12 = NUIDocView.this;
                                    if (nUIDocView12.mFinished) {
                                        return;
                                    }
                                    nUIDocView12.dismissProgressDialog();
                                    NUIDocView.this.onDocCompleted();
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onError(int i, int i2) {
                                    NUIDocView nUIDocView12 = NUIDocView.this;
                                    if (nUIDocView12.mFinished) {
                                        return;
                                    }
                                    nUIDocView12.setDisableClick();
                                    NUIDocView.this.dismissProgressDialog();
                                    if (NUIDocView.this.mSession.isCancelled() && i == 6) {
                                        return;
                                    }
                                    String openErrorDescription = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), i);
                                    AnonymousClass11 anonymousClass11 = AnonymousClass11.this;
                                    Utilities.showMessage(activity, NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error), openErrorDescription);
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onLayoutCompleted() {
                                    NUIDocView.this.onLayoutChanged();
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onPageLoad(int i) {
                                    NUIDocView nUIDocView12 = NUIDocView.this;
                                    if (nUIDocView12.mFinished) {
                                        return;
                                    }
                                    nUIDocView12.dismissProgressDialog();
                                    NUIDocView.this.onPageLoaded(i);
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onSelectionChanged(int i, int i2) {
                                    NUIDocView nUIDocView12 = NUIDocView.this;
                                    if (nUIDocView12.mFinished) {
                                        return;
                                    }
                                    nUIDocView12.onSelectionMonitor();
                                }
                            });
                        } else {
                            if (!nUIDocView2.mConfigOptions.r()) {
                                d8.a();
                                return;
                            }
                            NUIDocView nUIDocView12 = NUIDocView.this;
                            nUIDocView12.l = nUIDocView12.mState.getOpenedPath();
                            nUIDocView12.setupForDocType(nUIDocView12.l);
                            nUIDocView12.setFooterText(nUIDocView12.mState.getUserPath());
                            NUIDocView.this.showProgressDialog();
                            nUIDocView12.mSOFileState = nUIDocView12.mState;
                            NUIDocView.this.mSOFileState.openFile(NUIDocView.this.T);
                            nUIDocView12.mSession = new SODocSession(activity, nUIDocView12.ah);
                            NUIDocView nUIDocView13 = NUIDocView.this;
                            nUIDocView13.mSession.setFileState(nUIDocView13.mSOFileState);
                            NUIDocView.this.mSession.setSODocSessionLoadListener(new SODocSession.SODocSessionLoadListener() { // from class: com.artifex.sonui.editor.NUIDocView.11.2
                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onCancel() {
                                    NUIDocView.this.dismissProgressDialog();
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onDocComplete() {
                                    NUIDocView nUIDocView14 = NUIDocView.this;
                                    if (nUIDocView14.mFinished) {
                                        return;
                                    }
                                    nUIDocView14.dismissProgressDialog();
                                    NUIDocView.this.onDocCompleted();
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onError(int i, int i2) {
                                    NUIDocView nUIDocView14 = NUIDocView.this;
                                    if (nUIDocView14.mFinished) {
                                        return;
                                    }
                                    nUIDocView14.setDisableClick();
                                    NUIDocView.this.dismissProgressDialog();
                                    if (NUIDocView.this.mSession.isCancelled() && i == 6) {
                                        return;
                                    }
                                    String openErrorDescription = Utilities.getOpenErrorDescription(NUIDocView.this.getContext(), i);
                                    AnonymousClass11 anonymousClass11 = AnonymousClass11.this;
                                    Utilities.showMessage(activity, NUIDocView.this.getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_error), openErrorDescription);
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onLayoutCompleted() {
                                    NUIDocView.this.onLayoutChanged();
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onPageLoad(int i) {
                                    NUIDocView nUIDocView14 = NUIDocView.this;
                                    if (nUIDocView14.mFinished) {
                                        return;
                                    }
                                    nUIDocView14.dismissProgressDialog();
                                    NUIDocView.this.onPageLoaded(i);
                                }

                                @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
                                public void onSelectionChanged(int i, int i2) {
                                    NUIDocView.this.onSelectionMonitor();
                                }
                            });
                        }
                        NUIDocView nUIDocView14 = NUIDocView.this;
                        nUIDocView14.mSession.open(nUIDocView14.mSOFileState.getInternalPath());
                        docView2.setDoc(NUIDocView.this.mSession.getDoc());
                        if (NUIDocView.this.usePagesView()) {
                            NUIDocView.this.mDocListPageViews.setDoc(NUIDocView.this.mSession.getDoc());
                        }
                        if (NUIDocView.this.mSession.getDoc() != null && (pageAdapter = (nUIDocView = NUIDocView.this).mAdapter) != null) {
                            pageAdapter.setDoc(nUIDocView.mSession.getDoc());
                        }
                        if (NUIDocView.this.usePagesView()) {
                            NUIDocView.this.mDocListPageViews.setScale(0.2f);
                        }
                    }
                    NUIDocView.this.createInputView();
                }
            });
        }
        if (Utilities.isPhoneDevice(activity())) {
            scaleHeader();
        }
    }

    public boolean canCanManipulatePages() {
        return false;
    }

    public void changeOrientationOnClick() {
        int i = getResources().getConfiguration().orientation;
        if (Build.VERSION.SDK_INT >= 28) {
            if (i == 1) {
                activity().setRequestedOrientation(0);
            } else {
                activity().setRequestedOrientation(-1);
            }
        }
    }

    public PageAdapter createAdapter() {
        return new PageAdapter(getContext(), this, 1);
    }

    public void createEditButtons() {
        this.btnFontUp = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_size_up);
        this.btnFontDown = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_size_down);
        this.btnTextColor = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_color);
        this.btnTextBg = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_bg);
        this.btnCut = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_cut);
        this.btnCopy = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_copy);
        this.btnPaste = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_paste);
        this.btnTextBold = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_bold);
        this.btnTextItalic = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_italic);
        this.btnTextUnder = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_text_under);
        this.btnTextStrikeTrough = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_striketrough);
    }

    public void createEditButtons2() {
        this.btnListBullets = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_list_bullets);
        this.btnListNumbers = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_list_numbers);
        this.btnAlignLeft = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_align_left);
        this.btnAlignCenter = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_align_center);
        this.btnAlignRight = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_align_right);
        this.btnAlignJustify = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_align_justify);
        this.btnIndentIncrease = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_indent_increase);
        this.btnIndentDecrease = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_indent_decrease);
    }

    public void createInputView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(com.xlsxreader.excelreader.R.id.doc_inner_container);
        InputView inputView = new InputView(getContext(), this.mSession.getDoc(), this);
        this.inputView = inputView;
        relativeLayout.addView(inputView);
    }

    public void createInsertButtons() {
        this.btnInsertImage = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_insert_image);
        this.btnInsertPhoto = (EditBtn) createToolbarButton(com.xlsxreader.excelreader.R.id.btn_insert_photo);
    }

    public DocView createMainView(Activity activity) {
        return new DocView(activity);
    }

    public void createRecyclerColor() {
        this.llTextColorContainer = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.text_color_recyclerView);
        this.llBgColorContainer = (LinearLayout) findViewById(com.xlsxreader.excelreader.R.id.bg_color_recyclerView);
        RecyclerView recyclerView = (RecyclerView) findViewById(com.xlsxreader.excelreader.R.id.recyclerBgColor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        recyclerView.setHasFixedSize(true);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_tran, "transparent", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_1, "#000000", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_2, "#ffffff", true));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_3, "#888888", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_4, "#fe0000", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_5, "#00f402", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_6, "#1f20fb", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_7, "#ff00aa", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_8, "#00effe", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_9, "#ff5c02", false));
        arrayList.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_10, "#ffc44e", false));
        recyclerView.setAdapter(new ColorPickAdapter(arrayList, new ColorPickAdapter.ColorChangedListener() { // from class: com.artifex.sonui.editor.NUIDocView.12
            @Override // com.xlsxreader.excelreader.adapter.ColorPickAdapter.ColorChangedListener
            public void onColorChanged(String str) {
                boolean zEquals = str.equals("transparent");
                NUIDocView nUIDocView = NUIDocView.this;
                if (zEquals) {
                    nUIDocView.mSession.getDoc().setSelectionBackgroundTransparent();
                } else {
                    nUIDocView.mSession.getDoc().setSelectionBackgroundColor(str);
                }
                Utils.k(com.xlsxreader.excelreader.R.dimen.cm_dp_44, NUIDocView.this.getContext(), NUIDocView.this.llBgColorContainer, false);
                NUIDocView.this.btnTextBg.setChoose(false);
            }
        }));
        RecyclerView recyclerView2 = (RecyclerView) findViewById(com.xlsxreader.excelreader.R.id.recyclerColor);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        recyclerView2.setHasFixedSize(true);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_1, "#000000", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_2, "#ffffff", true));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_3, "#888888", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_4, "#fe0000", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_5, "#00f402", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_6, "#1f20fb", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_7, "#ff00aa", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_8, "#00effe", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_9, "#ff5c02", false));
        arrayList2.add(new ColorModel(com.xlsxreader.excelreader.R.drawable.bg_color_10, "#ffc44e", false));
        recyclerView2.setAdapter(new ColorPickAdapter(arrayList2, new ColorPickAdapter.ColorChangedListener() { // from class: com.artifex.sonui.editor.NUIDocView.13
            @Override // com.xlsxreader.excelreader.adapter.ColorPickAdapter.ColorChangedListener
            public void onColorChanged(String str) {
                NUIDocView.this.mSession.getDoc().setSelectionFontColor(str);
                Utils.k(com.xlsxreader.excelreader.R.dimen.cm_dp_44, NUIDocView.this.getContext(), NUIDocView.this.llTextColorContainer, false);
                NUIDocView.this.btnTextColor.setChoose(false);
            }
        }));
    }

    public View createToolbarButton(int i) {
        View viewFindViewById = findViewById(i);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this);
        }
        return viewFindViewById;
    }

    public void defocusInputView() {
        InputView inputView = this.inputView;
        if (inputView != null) {
            inputView.clearFocus();
        }
    }

    public void doArrowKey(KeyEvent keyEvent) {
        boolean zIsShiftPressed = keyEvent.isShiftPressed();
        boolean zIsAltPressed = keyEvent.isAltPressed();
        switch (keyEvent.getKeyCode()) {
            case 19:
                if (!zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(2);
                }
                if (zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(12);
                }
                if (!zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(6);
                }
                if (zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(14);
                }
                onTyping();
                break;
            case 20:
                if (!zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(3);
                }
                if (zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(13);
                }
                if (!zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(7);
                }
                if (zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(15);
                }
                onTyping();
                break;
            case 21:
                if (!zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(0);
                }
                if (zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(8);
                }
                if (!zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(4);
                }
                if (zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(10);
                }
                onTyping();
                break;
            case 22:
                if (!zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(1);
                }
                if (zIsShiftPressed && !zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(9);
                }
                if (!zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(5);
                }
                if (zIsShiftPressed && zIsAltPressed) {
                    this.mSession.getDoc().processKeyCommand(11);
                }
                onTyping();
                break;
        }
    }

    public void doBold() {
        SODoc doc = this.mSession.getDoc();
        boolean z = !doc.getSelectionIsBold();
        this.btnTextBold.setSelected(z);
        doc.setSelectionIsBold(z);
    }

    public void doCopy() {
        this.mSession.getDoc().I();
    }

    public void doCut() {
        this.mSession.getDoc().H();
    }

    public void doInsertImage(String str) {
        if (!com.artifex.solib.a.b(str)) {
            Utilities.showMessage((Activity) getContext(), getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_insert_image_gone_title), getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_insert_image_gone_body));
            return;
        }
        String strPreInsertImage = Utilities.preInsertImage(getContext(), str);
        getDoc().d(strPreInsertImage);
        if (str.equalsIgnoreCase(strPreInsertImage)) {
            return;
        }
        addDeleteOnClose(strPreInsertImage);
    }

    public void doItalic() {
        SODoc doc = this.mSession.getDoc();
        boolean z = !doc.getSelectionIsItalic();
        this.btnTextItalic.setSelected(z);
        doc.setSelectionIsItalic(z);
    }

    public boolean doKeyDown(int i, KeyEvent keyEvent) {
        char unicodeChar;
        boolean zIsAltPressed = keyEvent.isAltPressed();
        boolean zIsCtrlPressed = keyEvent.isCtrlPressed();
        boolean zIsShiftPressed = keyEvent.isShiftPressed();
        BaseActivity baseActivity = (BaseActivity) getContext();
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 4) {
            if (baseActivity.onKeyDown(i, keyEvent)) {
                baseActivity.finish();
            } else {
                onBackPressed();
            }
            return true;
        }
        if (keyCode != 37) {
            if (keyCode != 47) {
                if (keyCode != 52) {
                    if (keyCode != 54) {
                        if (keyCode != 62) {
                            if (keyCode != 30) {
                                if (keyCode == 31) {
                                    if (inputViewHasFocus() && (zIsCtrlPressed || zIsAltPressed)) {
                                        doCopy();
                                        return true;
                                    }
                                } else if (keyCode != 49) {
                                    if (keyCode != 50) {
                                        if (keyCode != 66) {
                                            if (keyCode == 67) {
                                                if (!inputViewHasFocus()) {
                                                    return false;
                                                }
                                                onTyping();
                                                getDoc().N();
                                                return true;
                                            }
                                            switch (keyCode) {
                                                case 19:
                                                    if (!inputViewHasFocus()) {
                                                        if (zIsAltPressed || zIsCtrlPressed) {
                                                            w();
                                                        } else {
                                                            y();
                                                        }
                                                        return true;
                                                    }
                                                case 20:
                                                    if (!inputViewHasFocus()) {
                                                        if (zIsAltPressed || zIsCtrlPressed) {
                                                            x();
                                                        } else {
                                                            z();
                                                        }
                                                        return true;
                                                    }
                                                case 21:
                                                case 22:
                                                    if (inputViewHasFocus()) {
                                                        onTyping();
                                                        doArrowKey(keyEvent);
                                                        return true;
                                                    }
                                                    if (inputViewHasFocus()) {
                                                        doCopy();
                                                        return true;
                                                    }
                                                    break;
                                            }
                                        } else if (inputViewHasFocus()) {
                                            this.W = false;
                                            if ((keyEvent.getFlags() & 2) != 0) {
                                                onTyping();
                                                return true;
                                            }
                                        }
                                    } else if (inputViewHasFocus() && (zIsCtrlPressed || zIsAltPressed)) {
                                        onTyping();
                                        doPaste();
                                        return true;
                                    }
                                } else if (inputViewHasFocus() && (zIsCtrlPressed || zIsAltPressed)) {
                                    onTyping();
                                    doUnderline();
                                    return true;
                                }
                            } else if (inputViewHasFocus() && (zIsCtrlPressed || zIsAltPressed)) {
                                onTyping();
                                doBold();
                                return true;
                            }
                        } else if (!inputViewHasFocus()) {
                            if (zIsShiftPressed) {
                                w();
                            } else {
                                x();
                            }
                            return true;
                        }
                    } else if (zIsCtrlPressed || zIsAltPressed) {
                        onTyping();
                        if (zIsShiftPressed) {
                            doRedo();
                        } else {
                            doUndo();
                        }
                        return true;
                    }
                } else if (inputViewHasFocus() && (zIsCtrlPressed || zIsAltPressed)) {
                    onTyping();
                    doCut();
                    return true;
                }
            } else if (zIsCtrlPressed || zIsAltPressed) {
                doSave();
                return true;
            }
        } else if (inputViewHasFocus() && (zIsCtrlPressed || zIsAltPressed)) {
            onTyping();
            doItalic();
            return true;
        }
        if (inputViewHasFocus() && (unicodeChar = (char) keyEvent.getUnicodeChar()) != 0) {
            onTyping();
            getDoc().setSelectionText(String.valueOf(unicodeChar), 0, true);
        }
        return true;
    }

    public void doPaste() {
        this.mSession.getDoc().a(getContext(), getTargetPageNumber());
    }

    public void doRedo() {
        int currentEdit;
        SODocSession sODocSession = this.mSession;
        if (sODocSession == null || sODocSession.getDoc() == null || (currentEdit = this.mSession.getDoc().getCurrentEdit()) >= this.mSession.getDoc().getNumEdits()) {
            return;
        }
        resetInputView();
        getDoc().clearSelection();
        this.mSession.getDoc().setCurrentEdit(currentEdit + 1);
    }

    public void doSave() {
        if (this.T) {
            a(false);
        } else {
            preSaveQuestion(new AnonymousClass14(), new Runnable(this) { // from class: com.artifex.sonui.editor.NUIDocView.15
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }
    }

    public void doStrikethrough() {
        SODoc doc = this.mSession.getDoc();
        boolean z = !doc.getSelectionIsLinethrough();
        this.btnTextStrikeTrough.setSelected(z);
        doc.setSelectionIsLinethrough(z);
    }

    public void doUnderline() {
        SODoc doc = this.mSession.getDoc();
        boolean z = !doc.getSelectionIsUnderlined();
        this.btnTextUnder.setSelected(z);
        doc.setSelectionIsUnderlined(z);
    }

    public void doUndo() {
        int currentEdit;
        SODocSession sODocSession = this.mSession;
        if (sODocSession == null || sODocSession.getDoc() == null || (currentEdit = this.mSession.getDoc().getCurrentEdit()) <= 0) {
            return;
        }
        resetInputView();
        getDoc().clearSelection();
        this.mSession.getDoc().setCurrentEdit(currentEdit - 1);
    }

    public boolean documentHasBeenModified() {
        SODocSession sODocSession = this.mSession;
        if (sODocSession == null || sODocSession.getDoc() == null || this.mSOFileState == null) {
            return false;
        }
        return this.mSession.getDoc().getHasBeenModified() || this.mSOFileState.hasChanges();
    }

    public void endDocSession(boolean z) {
        DocListPagesView docListPagesView;
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.finish();
        }
        if (usePagesView() && (docListPagesView = this.mDocListPageViews) != null) {
            docListPagesView.finish();
        }
        SODocSession sODocSession = this.mSession;
        if (sODocSession != null) {
            sODocSession.endSession(z);
        }
        dismissProgressDialog();
    }

    public void focusInputView() {
        if (!ConfigOptions.a().c()) {
            defocusInputView();
            return;
        }
        InputView inputView = this.inputView;
        if (inputView != null) {
            inputView.setFocus();
        }
    }

    public void forceReload() {
        this.au = true;
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public int getBorderColor() {
        return getContext().getColor(com.xlsxreader.excelreader.R.color.sodk_editor_selected_page_border_color);
    }

    public SODoc getDoc() {
        SODocSession sODocSession = this.mSession;
        if (sODocSession == null) {
            return null;
        }
        return sODocSession.getDoc();
    }

    public DocListPagesView getDocListPagesView() {
        return this.mDocListPageViews;
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public DocView getDocView() {
        return this.mDocView;
    }

    public InputView getInputView() {
        return this.inputView;
    }

    public boolean getIsComposing() {
        return this.W;
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public int getKeyboardHeight() {
        return this.mKeyboardHeight;
    }

    public int getLayoutId() {
        return 0;
    }

    public int getPageCount() {
        return this.mPageCount;
    }

    public String getPageNumberText() {
        return (this.pageNumber + 1) + " / " + getPageCount();
    }

    public SODocSession getSession() {
        return this.mSession;
    }

    public int getStartPage() {
        return this.RS;
    }

    public int getTargetPageNumber() {
        DocPageView docPageViewFindPageContainingSelection = getDocView().findPageContainingSelection();
        if (docPageViewFindPageContainingSelection != null) {
            return docPageViewFindPageContainingSelection.getPageNumber();
        }
        Rect rect = new Rect();
        getDocView().getGlobalVisibleRect(rect);
        DocPageView docPageViewFindPageViewContainingPoint = getDocView().findPageViewContainingPoint((rect.left + rect.right) / 2, (rect.top + rect.bottom) / 2, true);
        if (docPageViewFindPageViewContainingPoint != null) {
            return docPageViewFindPageViewContainingPoint.getPageNumber();
        }
        return 0;
    }

    public void goBack() {
        ConstraintLayout constraintLayout = this.headerContainer;
        boolean z = false;
        boolean z2 = constraintLayout != null && constraintLayout.getVisibility() == 8;
        ConstraintLayout constraintLayout2 = this.bottomContainer;
        if (constraintLayout2 != null && constraintLayout2.getVisibility() == 8) {
            z = true;
        }
        if (z2 || z) {
            onFullScreenShow();
            return;
        }
        prepareToGoBack();
        if (documentHasBeenModified()) {
            activity().runOnUiThread(new AnonymousClass16());
        } else {
            this.e = Boolean.FALSE;
            prefinish();
        }
    }

    public void goToPage(int i, boolean z) {
        this.mDocView.scrollToPage(i, z);
        if (usePagesView()) {
            this.mDocListPageViews.scrollToPage(i, z);
        }
    }

    public void handleStartPage() {
        int startPage = getStartPage();
        if (getStartPage() <= 0 || getPageCount() < getStartPage()) {
            return;
        }
        setStartPage(0);
        this.mDocView.setStartPage(startPage);
        this.pageNumber = startPage - 1;
        setPageNumberText();
        this.mDocView.requestLayout();
    }

    public boolean hasRedo() {
        return true;
    }

    public boolean hasSearch() {
        return true;
    }

    public boolean hasUndo() {
        return true;
    }

    public boolean inputViewHasFocus() {
        InputView inputView = this.inputView;
        return inputView != null && inputView.hasFocus();
    }

    public boolean isActivityActive() {
        return this.aa;
    }

    public boolean isActivityAlive(Activity activity) {
        return (activity == null || activity.isFinishing() || activity.isDestroyed()) ? false : true;
    }

    public boolean isFullScreen() {
        return this.mConfigOptions.A() && this.aw;
    }

    public boolean isKeyboardVisible() {
        return this.keyboardShown;
    }

    public boolean isLandscapePhone() {
        return this.an == 2 && Utilities.isPhoneDevice(getContext());
    }

    public boolean isPageListVisible() {
        return false;
    }

    public boolean isRedactionMode() {
        return false;
    }

    public boolean isSearchVisible() {
        View viewFindViewById = findViewById(com.xlsxreader.excelreader.R.id.et_search_text);
        return viewFindViewById != null && viewFindViewById.getVisibility() == 0 && viewFindViewById.isShown();
    }

    public void layoutAfterPageLoad() {
        layoutNow();
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public void layoutNow() {
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.layoutNow();
        }
        if (this.mDocListPageViews != null && usePagesView() && isPageListVisible()) {
            this.mDocListPageViews.layoutNow();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        SODataLeakHandlers sODataLeakHandlers = this.n;
        if (sODataLeakHandlers != null) {
            sODataLeakHandlers.onActivityResult(i, i2, intent);
        } else {
            d8.a();
        }
    }

    public void onAddCameraClicked() {
        ArrayList arrayList = new ArrayList();
        String str = new String[]{"android.permission.CAMERA"}[0];
        if (ContextCompat.a(activity(), str) != 0) {
            arrayList.add(str);
        }
        if (arrayList.isEmpty()) {
            onInsertPhotoButton();
        } else {
            ActivityCompat.d(activity(), (String[]) arrayList.toArray(new String[0]), 69905);
        }
    }

    public void onAlignCenterButton() {
        this.mSession.getDoc().E();
    }

    public void onAlignJustifyButton() {
        this.mSession.getDoc().G();
    }

    public void onAlignLeftButton() {
        this.mSession.getDoc().D();
    }

    public void onAlignRightButton() {
        this.mSession.getDoc().F();
    }

    public void onBackPressed() {
        goBack();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view != null) {
            if (view == this.btnClearSearchText) {
                this.edtSearchTextInput.setText("");
                onSelectionChanged();
                hidePages();
                layoutNow();
            }
            if (view == this.btnFullScreen) {
                if (this.headerContainer.getVisibility() == 0) {
                    onFullScreenHide();
                } else {
                    onFullScreenShow();
                }
            }
            if (view == this.btnThumbnailTab) {
                onSelectionChanged();
                layoutNow();
            }
            if (view == this.btnEditTab) {
                activity().runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.17
                    @Override // java.lang.Runnable
                    public void run() {
                        NUIDocView.this.h();
                        NUIDocView.this.onSelectionChanged();
                        NUIDocView.this.bottomToolBar.setClickable(false);
                        NUIDocView.this.bottomToolBar.setFocusable(false);
                        NUIDocView.this.setEnableBottomBtn(false);
                        NUIDocView.this.bottomToolBarEdit.setVisibility(0);
                        NUIDocView.this.toolbarSearchContainer.setVisibility(8);
                        NUIDocView.this.toolbarContainer.setVisibility(8);
                        NUIDocView.this.toolbarEditContainer.setVisibility(0);
                        NUIDocView.this.layoutNow();
                    }
                });
            }
            if (view == this.btnSearchTab) {
                activity().runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.18
                    @Override // java.lang.Runnable
                    public void run() {
                        NUIDocView.this.onSelectionChanged();
                        NUIDocView.this.hidePages();
                        Utilities.showKeyboard(NUIDocView.this.getContext());
                        NUIDocView.this.searchBackForwardContainer.setVisibility(0);
                        NUIDocView.this.undoForwardContainer.setVisibility(8);
                        NUIDocView.this.toolbarContainer.setVisibility(8);
                        NUIDocView.this.toolbarSearchContainer.setVisibility(0);
                        NUIDocView.this.edtSearchTextInput.getText().clear();
                        NUIDocView.this.edtSearchTextInput.requestFocus();
                        NUIDocView.this.layoutNow();
                    }
                });
            }
            if (view == this.btnRotateTab) {
                h();
                changeOrientationOnClick();
                onSelectionChanged();
                hidePages();
                layoutNow();
            }
            if (view == this.tvCancelSearch) {
                activity().runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.19
                    @Override // java.lang.Runnable
                    public void run() {
                        NUIDocView.this.h();
                        NUIDocView.this.toolbarContainer.setVisibility(0);
                        NUIDocView.this.toolbarSearchContainer.setVisibility(8);
                        NUIDocView.this.searchBackForwardContainer.setVisibility(8);
                        NUIDocView.this.undoForwardContainer.setVisibility(0);
                    }
                });
            }
            if (view == this.btnCloseEdit) {
                activity().runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.20
                    @Override // java.lang.Runnable
                    public void run() {
                        NUIDocView.this.toolbarContainer.setVisibility(0);
                        NUIDocView.this.bottomToolBar.setVisibility(0);
                        NUIDocView.this.bottomToolBar.setFocusable(true);
                        NUIDocView.this.bottomToolBar.setClickable(true);
                        NUIDocView.this.bottomToolBarEdit.setVisibility(8);
                        NUIDocView.this.setEnableBottomBtn(true);
                        NUIDocView.this.toolbarSearchContainer.setVisibility(8);
                        NUIDocView.this.toolbarEditContainer.setVisibility(8);
                    }
                });
            }
            if (view == this.btnSave) {
                onSaveButton();
            }
            if (view == this.btnSaveAsPdf) {
                onSavePDFButton();
            }
            if (view == this.btnPrint) {
                onPrintButton();
            }
            if (view == this.btnFontUp) {
                onFontUpButton();
            }
            if (view == this.btnFontDown) {
                onFontDownButton();
            }
            if (view == this.btnTextColor) {
                if (this.llTextColorContainer.getVisibility() == 0) {
                    Utils.k(com.xlsxreader.excelreader.R.dimen.cm_dp_44, getContext(), this.llTextColorContainer, false);
                    this.btnTextColor.setChoose(false);
                    this.btnTextBg.setEnable(true);
                } else {
                    Utils.k(com.xlsxreader.excelreader.R.dimen.cm_dp_44, getContext(), this.llTextColorContainer, true);
                    this.btnTextColor.setChoose(true);
                    this.btnTextBg.setEnable(false);
                }
            }
            if (view == this.btnTextBg) {
                if (this.llBgColorContainer.getVisibility() == 0) {
                    Utils.k(com.xlsxreader.excelreader.R.dimen.cm_dp_44, getContext(), this.llBgColorContainer, false);
                    this.btnTextBg.setChoose(false);
                    this.btnTextColor.setEnable(true);
                } else {
                    Utils.k(com.xlsxreader.excelreader.R.dimen.cm_dp_44, getContext(), this.llBgColorContainer, true);
                    this.btnTextBg.setChoose(true);
                    this.btnTextColor.setEnable(false);
                }
            }
            if (view == this.btnCut) {
                onCutButton();
            }
            if (view == this.btnCopy) {
                onCopyButton();
            }
            if (view == this.btnPaste) {
                onPasteButton();
            }
            if (view == this.btnTextBold) {
                doBold();
            }
            if (view == this.btnTextItalic) {
                doItalic();
            }
            if (view == this.btnTextUnder) {
                doUnderline();
            }
            if (view == this.btnTextStrikeTrough) {
                doStrikethrough();
            }
            if (view == this.btnListBullets) {
                onListBulletsButton();
            }
            if (view == this.btnListNumbers) {
                onListNumbersButton();
            }
            if (view == this.btnAlignLeft) {
                onAlignLeftButton();
            }
            if (view == this.btnAlignCenter) {
                onAlignCenterButton();
            }
            if (view == this.btnAlignRight) {
                onAlignRightButton();
            }
            if (view == this.btnAlignJustify) {
                onAlignJustifyButton();
            }
            if (view == this.btnIndentIncrease) {
                onIndentIncreaseButton();
            }
            if (view == this.btnIndentDecrease) {
                onIndentDecreaseButton();
            }
            if (view == this.btnUndo) {
                onUndoButton(view);
            }
            if (view == this.btnRedo) {
                onRedoButton(view);
            }
            if (view == this.btnSearchNext) {
                onSearchNext();
            }
            if (view == this.btnSearchPrevious) {
                onSearchPrevious();
            }
            if (view == this.btnBack) {
                goBack();
            }
            if (view == this.btnInsertImage) {
                onInsertImageButton();
            }
            if (view == this.btnInsertPhoto) {
                onAddCameraClicked();
            }
        }
    }

    public void onConfigurationChange(Configuration configuration) {
        DocListPagesView docListPagesView;
        if (configuration != null && configuration.keyboard != this.V) {
            resetInputView();
            this.V = configuration.keyboard;
        }
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.onConfigurationChange();
        }
        if (!usePagesView() || (docListPagesView = this.mDocListPageViews) == null) {
            return;
        }
        docListPagesView.onConfigurationChange();
    }

    public void onCopyButton() {
        doCopy();
    }

    public void onCustomSaveButton() {
        if (this.n != null) {
            preSaveQuestion(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.21
                @Override // java.lang.Runnable
                public void run() {
                    String userPath = NUIDocView.this.mSOFileState.getUserPath();
                    if (userPath == null) {
                        userPath = NUIDocView.this.mSOFileState.getOpenedPath();
                    }
                    File file = new File(userPath);
                    NUIDocView.this.preSave();
                    try {
                        NUIDocView.this.n.customSaveHandler(file.getName(), NUIDocView.this.mSession.getDoc(), NUIDocView.this.k, new SOCustomSaveComplete() { // from class: com.artifex.sonui.editor.NUIDocView.21.1
                            @Override // com.artifex.sonui.editor.SOCustomSaveComplete
                            public void onComplete(int i, String str, boolean z) {
                                NUIDocView.this.mSOFileState.setHasChanges(false);
                                if (i == 0) {
                                    NUIDocView.this.mSOFileState.setHasChanges(false);
                                }
                                if (z) {
                                    NUIDocView.this.prefinish();
                                }
                            }
                        });
                    } catch (IOException | UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
            }, new Runnable(this) { // from class: com.artifex.sonui.editor.NUIDocView.22
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }
    }

    public void onCutButton() {
        doCut();
    }

    public void onDestroy() {
        ArrayList<String> arrayList;
        q();
        int i = 0;
        while (true) {
            int size = this.ai.size();
            arrayList = this.ai;
            if (i >= size) {
                break;
            }
            com.artifex.solib.a.e(arrayList.get(i));
            i++;
        }
        arrayList.clear();
        SODataLeakHandlers sODataLeakHandlers = this.n;
        if (sODataLeakHandlers != null) {
            sODataLeakHandlers.finaliseDataLeakHandlers();
        }
    }

    public void onDeviceSizeChange() {
        Resources resources;
        int i;
        if (Utilities.isPhoneDevice(activity())) {
            scaleHeader();
            resources = getContext().getResources();
            i = com.xlsxreader.excelreader.R.dimen.sodk_editor_after_back_button_phone;
        } else {
            resources = getContext().getResources();
            i = com.xlsxreader.excelreader.R.dimen.sodk_editor_after_back_button;
        }
        resources.getDimension(i);
    }

    public void onDocCompleted() {
        if (this.mFinished) {
            return;
        }
        int iR = this.mSession.getDoc().r();
        this.mPageCount = iR;
        this.mAdapter.setCount(iR);
        layoutNow();
        if (k.e(activity())) {
            this.mSession.getDoc().setAuthor(Utilities.getStringPreference(Utilities.getPreferencesObject(activity(), Utilities.generalStore), "DocAuthKey", Utilities.getApplicationName(activity())));
        }
    }

    public void onFontDownButton() {
        double selectionFontSize = this.mSession.getDoc().getSelectionFontSize();
        if (((int) selectionFontSize) > 6) {
            this.mSession.getDoc().setSelectionFontSize(selectionFontSize - 1.0d);
        }
    }

    public void onFontUpButton() {
        double selectionFontSize = this.mSession.getDoc().getSelectionFontSize();
        if (((int) selectionFontSize) < 72) {
            this.mSession.getDoc().setSelectionFontSize(selectionFontSize + 1.0d);
        }
    }

    public void onFullScreenHide() {
        findViewById(com.xlsxreader.excelreader.R.id.header).setVisibility(8);
        findViewById(com.xlsxreader.excelreader.R.id.bottom_container).setVisibility(8);
        findViewById(com.xlsxreader.excelreader.R.id.shadow_bottom).setVisibility(8);
        hidePages();
        layoutNow();
    }

    public void onFullScreenShow() {
        findViewById(com.xlsxreader.excelreader.R.id.header).setVisibility(0);
        findViewById(com.xlsxreader.excelreader.R.id.bottom_container).setVisibility(0);
        findViewById(com.xlsxreader.excelreader.R.id.shadow_bottom).setVisibility(0);
        hidePages();
        layoutNow();
    }

    public void onHideKeyBoard(Runnable runnable) {
        boolean z = getKeyboardHeight() > 0;
        Utilities.hideKeyboard(getContext());
        if (z) {
            this.mRunnable = runnable;
        } else {
            runnable.run();
        }
    }

    public void onIndentDecreaseButton() {
        int[] indentationLevel = this.mSession.getDoc().getIndentationLevel();
        if (indentationLevel == null || indentationLevel[0] <= 0) {
            return;
        }
        this.mSession.getDoc().setIndentationLevel(indentationLevel[0] - 1);
    }

    public void onIndentIncreaseButton() {
        int[] indentationLevel = this.mSession.getDoc().getIndentationLevel();
        if (indentationLevel == null || indentationLevel[0] >= indentationLevel[1]) {
            return;
        }
        this.mSession.getDoc().setIndentationLevel(indentationLevel[0] + 1);
    }

    public void onInsertImageButton() {
        onHideKeyBoard(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.23
            @Override // java.lang.Runnable
            public void run() {
                if (NUIDocView.this.n == null) {
                    d8.a();
                    return;
                }
                try {
                    NUIDocView.this.n.insertImageHandler(NUIDocView.this);
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onInsertPhotoButton() {
        onHideKeyBoard(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.24
            @Override // java.lang.Runnable
            public void run() {
                if (NUIDocView.this.n == null) {
                    d8.a();
                    return;
                }
                try {
                    NUIDocView.this.n.insertPhotoHandler(NUIDocView.this);
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        View currentFocus;
        BaseActivity baseActivity = (BaseActivity) getContext();
        if (baseActivity.isSlideShow() || (currentFocus = baseActivity.getCurrentFocus()) == null || keyEvent.getKeyCode() != 4) {
            return super.onKeyPreIme(i, keyEvent);
        }
        currentFocus.clearFocus();
        Utilities.hideKeyboard(getContext());
        return true;
    }

    public void onLayoutChanged() {
        SODocSession sODocSession = this.mSession;
        if (sODocSession == null || sODocSession.getDoc() == null || this.mFinished) {
            return;
        }
        this.mDocView.onLayoutChanged();
    }

    public void onListBulletsButton() {
        boolean zIsSelected = this.btnListBullets.isSelected();
        EditBtn editBtn = this.btnListBullets;
        if (!zIsSelected) {
            editBtn.setSelected(true);
            editBtn = this.btnListNumbers;
        }
        editBtn.setSelected(false);
        n();
    }

    public void onListNumbersButton() {
        boolean zIsSelected = this.btnListNumbers.isSelected();
        EditBtn editBtn = this.btnListNumbers;
        if (!zIsSelected) {
            editBtn.setSelected(true);
            editBtn = this.btnListBullets;
        }
        editBtn.setSelected(false);
        n();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        a();
        super.onMeasure(i, i2);
    }

    public void onOrientationChange() {
        this.mDocView.onOrientationChange();
        if (usePagesView()) {
            this.mDocListPageViews.onOrientationChange();
        }
        if (!isFullScreen()) {
            showUI(!this.keyboardShown);
        }
        onDeviceSizeChange();
    }

    public void onPageLoaded(int i) {
        boolean z = this.mPageCount == 0;
        this.mPageCount = i;
        if (z) {
            k();
            updateUIAppearance();
        }
        int count = this.mAdapter.getCount();
        int i2 = this.mPageCount;
        boolean z2 = i2 != count;
        if (i2 < count) {
            this.mDocView.removeAllViewsInLayout();
            if (usePagesView()) {
                this.mDocListPageViews.removeAllViewsInLayout();
            }
        }
        this.mAdapter.setCount(this.mPageCount);
        if (z2) {
            final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocView.25
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                    NUIDocView nUIDocView = NUIDocView.this;
                    if (nUIDocView.mFinished) {
                        return;
                    }
                    boolean reflowMode = nUIDocView.mDocView.getReflowMode();
                    NUIDocView nUIDocView2 = NUIDocView.this;
                    if (reflowMode) {
                        nUIDocView2.onReflowScale();
                    } else {
                        nUIDocView2.mDocView.scrollSelectionIntoView();
                    }
                }
            });
            layoutAfterPageLoad();
        } else {
            g();
        }
        handleStartPage();
        if (this.ap) {
            return;
        }
        this.ap = true;
        new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.26
            @Override // java.lang.Runnable
            public void run() {
                NUIDocView.this.setPageNumberText();
                NUIDocView.this.ap = false;
            }
        }, 1000L);
    }

    public void onPasteButton() {
        doPaste();
    }

    public void onPause() {
        DocView docView;
        onPauseCommon();
        DocView docView2 = this.mDocView;
        if ((docView2 != null && docView2.finished()) || this.mSOFileState == null || (docView = this.mDocView) == null || docView.getDoc() == null || this.n == null) {
            return;
        }
        SODoc doc = this.mDocView.getDoc();
        this.n.pauseHandler(doc, doc.getHasBeenModified());
    }

    public void onPauseCommon() {
        this.aa = false;
        resetInputView();
    }

    public void onPrintButton() {
        SODataLeakHandlers sODataLeakHandlers = this.n;
        if (sODataLeakHandlers == null) {
            d8.a();
            return;
        }
        try {
            SODocSession sODocSession = this.mSession;
            if (sODocSession == null || sODocSession.getDoc() == null) {
                return;
            }
            sODataLeakHandlers.printHandler(this.mSession.getDoc());
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public void onRedoButton(View view) {
        doRedo();
    }

    public void onReflowScale() {
        this.mDocView.onReflowScale();
        if (usePagesView()) {
            this.mDocListPageViews.onReflowScale();
        }
    }

    public void onResume() {
        onResumeCommon();
        this.mKeyboardHeight = 0;
        onShowKeyboard(false);
        SOFileState autoOpen = SOFileState.getAutoOpen(getContext());
        if (autoOpen != null && this.mSOFileState != null && autoOpen.getLastAccess() > this.mSOFileState.getLastAccess()) {
            this.mSOFileState.setHasChanges(autoOpen.hasChanges());
        }
        SOFileState.clearAutoOpen(getContext());
        SODataLeakHandlers sODataLeakHandlers = this.n;
        if (sODataLeakHandlers != null) {
            sODataLeakHandlers.doInsert();
        }
    }

    public void onResumeCommon() {
        DocListPagesView docListPagesView;
        mNUIDocView = this;
        String str = this.l;
        if (str != null) {
            a(str);
            s();
        }
        if (this.au) {
            this.au = false;
            getDoc().a(true);
            reloadFile();
        }
        this.aa = true;
        focusInputView();
        DocView docView = getDocView();
        if (docView != null) {
            docView.forceLayout();
        }
        if (!usePagesView() || (docListPagesView = getDocListPagesView()) == null) {
            return;
        }
        docListPagesView.forceLayout();
    }

    public void onSaveButton() {
        preSave();
        doSave();
    }

    public void onSavePDFButton() {
        SOFileState sOFileState;
        SODocSession sODocSession;
        if (this.n == null || (sOFileState = this.mSOFileState) == null || sOFileState.getOpenedPath() == null || (sODocSession = this.mSession) == null || sODocSession.getDoc() == null) {
            d8.a();
            return;
        }
        try {
            this.n.saveAsPdfHandler(new File(this.mSOFileState.getOpenedPath()).getName(), this.mSession.getDoc());
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public void onSearch() {
        this.searchBackForwardContainer.setVisibility(0);
        this.undoForwardContainer.setVisibility(8);
        this.toolbarContainer.setVisibility(8);
        this.toolbarSearchContainer.setVisibility(0);
        this.edtSearchTextInput.getText().clear();
        this.edtSearchTextInput.requestFocus();
        Utilities.showKeyboard(getContext());
    }

    public void onSearchNext() {
        SODoc doc = getDoc();
        if (doc == null || doc.p()) {
            return;
        }
        doc.d(false);
        searchText();
    }

    public void onSearchPrevious() {
        SODoc doc = getDoc();
        if (doc == null || doc.p()) {
            return;
        }
        doc.d(true);
        searchText();
    }

    public void onSelectionChanged() {
        try {
            SODocSession sODocSession = this.mSession;
            if (sODocSession == null || sODocSession.getDoc() == null || this.mFinished) {
                return;
            }
            this.mDocView.onSelectionChanged();
            if (usePagesView() && isPageListVisible()) {
                this.mDocListPageViews.onSelectionChanged();
            }
            updateUIAppearance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelectionMonitor() {
        onSelectionChanged();
        DocView docView = getDocView();
        if (docView == null || docView.getSelectionLimits() == null) {
            defocusInputView();
        } else {
            focusInputView();
        }
    }

    public void onShowKeyBoard(Runnable runnable) {
        boolean z = getKeyboardHeight() > 0;
        Utilities.showKeyboard(getContext());
        if (z) {
            runnable.run();
        } else {
            this.mRunnable = runnable;
        }
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public void onShowKeyboard(final boolean z) {
        DocListPagesView docListPagesView;
        if (!isActivityActive() || getPageCount() <= 0) {
            return;
        }
        this.keyboardShown = z;
        onShowKeyboardPreventPush();
        if (!isFullScreen()) {
            showUI(!z);
        }
        if (usePagesView() && (docListPagesView = getDocListPagesView()) != null) {
            docListPagesView.onShowKeyboard(z);
        }
        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.NUIDocView.27
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                DocView docView = NUIDocView.this.getDocView();
                if (docView != null) {
                    docView.onShowKeyboard(z);
                }
                if (NUIDocView.this.mRunnable != null) {
                    NUIDocView.this.mRunnable.run();
                    NUIDocView.this.mRunnable = null;
                }
            }
        });
    }

    public void onShowKeyboardPreventPush() {
        int i = activity().getWindow().getAttributes().flags;
    }

    public void onTyping() {
        System.currentTimeMillis();
    }

    public void onUndoButton(View view) {
        doUndo();
    }

    public void preSaveQuestion(Runnable runnable, Runnable runnable2) {
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public void prefinish() {
        DocListPagesView docListPagesView;
        if (this.mFinished) {
            return;
        }
        this.mFinished = true;
        SODocSession sODocSession = this.mSession;
        if (sODocSession != null && sODocSession.getDoc() != null) {
            this.mSession.getDoc().a((p) null);
        }
        this.m = null;
        dismissProgressDialog();
        SOFileState sOFileState = this.mSOFileState;
        if (sOFileState != null) {
            sOFileState.closeFile();
        }
        Utilities.hideKeyboard(getContext());
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.finish();
            this.mDocView = null;
        }
        if (usePagesView() && (docListPagesView = this.mDocListPageViews) != null) {
            docListPagesView.finish();
            this.mDocListPageViews = null;
        }
        SODocSession sODocSession2 = this.mSession;
        if (sODocSession2 != null) {
            sODocSession2.abort();
        }
        SODoc doc = getDoc();
        if (doc != null) {
            doc.M();
        }
        PageAdapter pageAdapter = this.mAdapter;
        if (pageAdapter != null) {
            pageAdapter.setDoc(null);
        }
        this.mAdapter = null;
        Boolean bool = this.e;
        if (bool != null) {
            endDocSession(bool.booleanValue());
            this.e = null;
        }
        if (this.mSession == null) {
            NUIView1.OnDoneListener onDoneListener = this.mDoneListener;
            if (onDoneListener != null) {
                onDoneListener.done();
                return;
            }
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(getContext(), com.xlsxreader.excelreader.R.style.sodk_editor_alert_dialog_style);
        progressDialog.setMessage(getContext().getString(com.xlsxreader.excelreader.R.string.sodk_editor_wait));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().clearFlags(2);
        progressDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.artifex.sonui.editor.NUIDocView.28
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.28.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SODocSession sODocSession3 = NUIDocView.this.mSession;
                        if (sODocSession3 != null) {
                            sODocSession3.destroy();
                        }
                        progressDialog.dismiss();
                        NUIView1.OnDoneListener onDoneListener2 = NUIDocView.this.mDoneListener;
                        if (onDoneListener2 != null) {
                            onDoneListener2.done();
                        }
                    }
                });
            }
        });
        progressDialog.show();
    }

    public void releaseBitmaps() {
        setValid(false);
        q();
        s();
    }

    public void resetInputView() {
        InputView inputView = this.inputView;
        if (inputView != null) {
            inputView.resetEditable();
        }
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public void selectionupdated() {
        onSelectionChanged();
    }

    public void setConfigurableButtons() {
        if (this.btnSave != null) {
            boolean zP = this.mConfigOptions.p();
            ImageView imageView = this.btnSave;
            if (zP) {
                imageView.setVisibility(0);
            } else {
                imageView.setVisibility(8);
            }
        }
        if (shouldConfigureSaveAsPDFButton() && this.btnSaveAsPdf != null) {
            boolean zE = this.mConfigOptions.e();
            ImageView imageView2 = this.btnSaveAsPdf;
            if (zE) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
        }
        if (this.btnPrint != null) {
            if (this.mConfigOptions.m() || this.mConfigOptions.n()) {
                this.btnPrint.setVisibility(0);
            } else {
                this.btnPrint.setVisibility(8);
            }
        }
        if (!this.mConfigOptions.c()) {
            ImageView imageView3 = this.btnUndo;
            if (imageView3 != null) {
                imageView3.setVisibility(8);
            }
            ImageView imageView4 = this.btnRedo;
            if (imageView4 != null) {
                imageView4.setVisibility(8);
            }
        }
        if (this.btnInsertImage != null) {
            boolean zK = this.mConfigOptions.k();
            EditBtn editBtn = this.btnInsertImage;
            if (zK) {
                editBtn.setVisibility(0);
            } else {
                editBtn.setVisibility(8);
            }
        }
        if (this.btnInsertPhoto != null) {
            boolean zL = this.mConfigOptions.l();
            EditBtn editBtn2 = this.btnInsertPhoto;
            if (zL) {
                editBtn2.setVisibility(0);
            } else {
                editBtn2.setVisibility(8);
            }
        }
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public void setCurrentPage(int i) {
        if (usePagesView()) {
            this.mDocListPageViews.setCurrentPage(i);
            this.mDocListPageViews.scrollToPage(i, false);
        }
        this.pageNumber = i;
        setPageNumberText();
        this.mSession.getFileState().setPageNumber(this.pageNumber);
    }

    public void setEnableBottomBtn(boolean z) {
        this.btnRotateTab.setEnable(z);
        this.btnEditTab.setEnable(z);
        this.btnSearchTab.setEnable(z);
        this.btnThumbnailTab.setEnable(z);
    }

    public void setIsComposing(boolean z) {
        this.W = z;
    }

    public void setPageCount(int i) {
        this.mPageCount = i;
        PageAdapter pageAdapter = this.mAdapter;
        if (pageAdapter != null) {
            pageAdapter.setCount(i);
        }
        g();
    }

    public void setPageNumberText() {
        new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.29
            @Override // java.lang.Runnable
            public void run() {
                NUIDocView nUIDocView = NUIDocView.this;
                nUIDocView.tvPageNumber.setText(nUIDocView.getPageNumberText());
                NUIDocView.this.tvPageNumber.measure(0, 0);
            }
        });
    }

    public void setStartPage(int i) {
        this.RS = i;
    }

    public boolean shouldConfigureSaveAsPDFButton() {
        return true;
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public boolean showKeyboard() {
        Utilities.showKeyboard(getContext());
        return true;
    }

    public void showUI(boolean z) {
        View viewFindViewById = findViewById(com.xlsxreader.excelreader.R.id.header);
        if (isLandscapePhone() || viewFindViewById.getVisibility() != 0) {
            viewFindViewById.setVisibility(0);
            layoutNow();
        }
        if (this.mConfigOptions.A() && z) {
            this.aw = false;
            if (getDocView() != null) {
                getDocView().onFullscreen(false);
            }
            layoutNow();
        }
    }

    public void start(SOFileState sOFileState, int i, NUIView1.OnDoneListener onDoneListener) {
        this.mIsSession = false;
        this.T = sOFileState.isTemplate();
        this.mState = sOFileState;
        setStartPage(i);
        this.mDoneListener = onDoneListener;
        c();
        e();
        d();
    }

    public void triggerOrientationChange() {
        this.ao = true;
    }

    public void triggerRender() {
        DocView docView = this.mDocView;
        if (docView != null) {
            docView.triggerRender();
        }
        if (this.mDocListPageViews != null && usePagesView() && isPageListVisible()) {
            this.mDocListPageViews.triggerRender();
        }
    }

    public void updateEditUIAppearance() {
        boolean isActive;
        boolean z;
        if (getDocView() == null) {
            return;
        }
        SOSelectionLimits selectionLimits = getDocView().getSelectionLimits();
        if (selectionLimits != null) {
            isActive = selectionLimits.getIsActive();
            z = isActive && !selectionLimits.getIsCaret();
            if (isActive) {
                selectionLimits.getIsCaret();
                isActive = true;
            }
        } else {
            isActive = false;
            z = false;
        }
        SODoc doc = this.mSession.getDoc();
        boolean z2 = z && !doc.selectionIsAutoshapeOrImage();
        this.btnTextBold.setEnable(z2);
        this.btnTextBold.setSelected(z2 && doc.getSelectionIsBold());
        this.btnTextItalic.setEnable(z2);
        this.btnTextItalic.setSelected(z2 && doc.getSelectionIsItalic());
        this.btnTextUnder.setEnable(z2);
        this.btnTextUnder.setSelected(z2 && doc.getSelectionIsUnderlined());
        this.btnTextStrikeTrough.setEnable(z2);
        this.btnTextStrikeTrough.setSelected(z2 && doc.getSelectionIsLinethrough());
        this.btnAlignLeft.setEnable(isActive);
        this.btnAlignLeft.setSelected(isActive && doc.getSelectionIsAlignLeft());
        this.btnAlignCenter.setEnable(isActive);
        this.btnAlignCenter.setSelected(isActive && doc.getSelectionIsAlignCenter());
        this.btnAlignRight.setEnable(isActive);
        this.btnAlignRight.setSelected(isActive && doc.getSelectionIsAlignRight());
        this.btnAlignJustify.setEnable(isActive);
        this.btnAlignJustify.setSelected(isActive && doc.getSelectionIsAlignJustify());
        this.btnListBullets.setEnable(isActive);
        this.btnListBullets.setSelected(isActive && doc.getSelectionListStyleIsDisc());
        this.btnListNumbers.setEnable(isActive);
        this.btnListNumbers.setSelected(isActive && doc.getSelectionListStyleIsDecimal());
        this.btnIndentIncrease.setEnable(isActive && o());
        this.btnIndentDecrease.setEnable(isActive && p());
    }

    public void updateInsertUIAppearance() {
        if (getDocView() == null) {
            return;
        }
        SOSelectionLimits selectionLimits = getDocView().getSelectionLimits();
        boolean z = selectionLimits != null && selectionLimits.getIsActive() && selectionLimits.getIsCaret();
        if (this.btnInsertImage != null && this.mConfigOptions.k()) {
            this.btnInsertImage.setEnable(z);
        }
        if (this.btnInsertPhoto == null || !this.mConfigOptions.l()) {
            return;
        }
        this.btnInsertPhoto.setEnable(z);
    }

    public void updateSaveUIAppearance() {
        if (this.btnSave != null) {
            documentHasBeenModified();
            this.mConfigOptions.c();
        }
    }

    public void updateUIAppearance() {
        boolean z;
        boolean z2;
        updateSaveUIAppearance();
        if (getDocView() == null) {
            return;
        }
        SOSelectionLimits selectionLimits = getDocView().getSelectionLimits();
        if (selectionLimits != null) {
            boolean isActive = selectionLimits.getIsActive();
            z2 = isActive && !selectionLimits.getIsCaret();
            z = isActive && selectionLimits.getIsCaret();
        } else {
            z = false;
            z2 = false;
        }
        if (this.mConfigOptions.c()) {
            updateEditUIAppearance();
            updateUndoUIAppearance();
            boolean zSelectionIsAutoshapeOrImage = getDoc().selectionIsAutoshapeOrImage();
            boolean z3 = z2 && !zSelectionIsAutoshapeOrImage;
            long jRound = Math.round(this.mSession.getDoc().getSelectionFontSize());
            EditBtn editBtn = this.btnFontUp;
            if (z3) {
                editBtn.setEnable(jRound < 72);
                this.btnFontDown.setEnable(jRound > 6);
            } else {
                editBtn.setEnable(false);
                this.btnFontDown.setEnable(false);
            }
            this.btnCut.setEnable(z2 && this.mSession.getDoc().getSelectionCanBeDeleted());
            this.btnCopy.setEnable(z2 && this.mSession.getDoc().getSelectionCanBeCopied());
            this.btnPaste.setEnable(!zSelectionIsAutoshapeOrImage && (z || z2) && this.mSession.getDoc().J());
            updateInsertUIAppearance();
        }
    }

    public void updateUndoUIAppearance() {
        SODocSession sODocSession = this.mSession;
        if (sODocSession == null || sODocSession.getDoc() == null) {
            return;
        }
        int currentEdit = this.mSession.getDoc().getCurrentEdit();
        int numEdits = this.mSession.getDoc().getNumEdits();
        setEnableButton(this.btnUndo, currentEdit > 0);
        setEnableButton(this.btnRedo, currentEdit < numEdits);
    }

    public boolean usePagesView() {
        return true;
    }

    public boolean wasTyping() {
        return System.currentTimeMillis() < 500;
    }

    public void goToPage(int i) {
        goToPage(i, false);
    }

    public void hidePages() {
    }

    public void preSave() {
    }

    public void prepareToGoBack() {
    }

    public void reloadFile() {
    }

    public void scaleHeader() {
    }

    public void scaleToolbar() {
    }

    public void setSearchStart() {
    }

    public void updateReviewUIAppearance() {
    }

    public void start(SODocSession sODocSession, int i, String str, NUIView1.OnDoneListener onDoneListener) {
        this.mIsSession = true;
        this.mSession = sODocSession;
        this.T = false;
        setStartPage(i);
        this.mDoneListener = onDoneListener;
        this.U = str;
        c();
        e();
        d();
    }

    public void start(Uri uri, boolean z, int i, String str, NUIView1.OnDoneListener onDoneListener) {
        this.T = z;
        this.mStartUri = uri;
        this.k = str;
        this.mDoneListener = onDoneListener;
        c();
        e();
        d();
    }

    @Override // com.artifex.sonui.editor.DocViewHost
    public void clickSheetButton(int i, boolean z) {
    }

    /* JADX INFO: renamed from: com.artifex.sonui.editor.NUIDocView$4, reason: invalid class name */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class AnonymousClass4 implements p {
        public AnonymousClass4() {
        }

        @Override // com.artifex.solib.p
        public void a() {
            NUIDocView.this.v();
            Utilities.yesNoMessage((Activity) NUIDocView.this.getContext(), NUIDocView.this.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_no_more_found), NUIDocView.this.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_keep_searching), NUIDocView.this.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_str_continue), NUIDocView.this.getResources().getString(com.xlsxreader.excelreader.R.string.sodk_editor_stop), new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.4.1
                @Override // java.lang.Runnable
                public void run() {
                    new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.4.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            NUIDocView.this.searchText();
                        }
                    });
                }
            }, new Runnable(this) { // from class: com.artifex.sonui.editor.NUIDocView.4.2
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }

        @Override // com.artifex.solib.p
        public boolean b() {
            NUIDocView.this.searchText();
            return true;
        }

        @Override // com.artifex.solib.p
        public boolean c() {
            NUIDocView.this.searchText();
            return true;
        }

        @Override // com.artifex.solib.p
        public void d() {
            NUIDocView.this.v();
        }

        @Override // com.artifex.solib.p
        public void e() {
            NUIDocView.this.v();
        }

        @Override // com.artifex.solib.p
        public void a(int i) {
        }

        @Override // com.artifex.solib.p
        public void a(int i, RectF rectF) {
            NUIDocView.this.v();
            NUIDocView.this.getDocView().onFoundText(i, rectF);
        }
    }

    public NUIDocView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.S = new SOBitmap[]{null, null};
        this.ai = new ArrayList<>();
        this.mFinished = false;
        this.keyboardShown = false;
        this.mConfigOptions = null;
        this.mIsSession = false;
        this.mStartUri = null;
        this.mState = null;
        this.mDoneListener = null;
        this.inputView = null;
        this.RS = 0;
        this.T = false;
        this.U = null;
        this.V = -1;
        this.W = false;
        this.aa = false;
        this.pageNumber = 0;
        this.mView = null;
        this.mKeyboardHeight = 0;
        this.an = 0;
        this.ao = false;
        this.ap = false;
        this.progressDialog = null;
        this.mRunnable = null;
        this.au = false;
        this.aw = false;
        this.c = false;
        this.m = null;
        init(context);
    }

    private void a(String str) {
        this.ah = k.a(activity(), str);
        Point realScreenSize = Utilities.getRealScreenSize(activity());
        int iMax = Math.max(realScreenSize.x, realScreenSize.y);
        int i = (iMax * 120) / 100;
        OVERSIZE_MARGIN = (i - iMax) / 2;
        int i2 = 0;
        while (true) {
            SOBitmap[] sOBitmapArr = this.S;
            if (i2 >= sOBitmapArr.length) {
                return;
            }
            sOBitmapArr[i2] = this.ah.a(i, i);
            i2++;
        }
    }

    public NUIDocView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.S = new SOBitmap[]{null, null};
        this.ai = new ArrayList<>();
        this.mFinished = false;
        this.keyboardShown = false;
        this.mConfigOptions = null;
        this.mIsSession = false;
        this.mStartUri = null;
        this.mState = null;
        this.mDoneListener = null;
        this.inputView = null;
        this.RS = 0;
        this.T = false;
        this.U = null;
        this.V = -1;
        this.W = false;
        this.aa = false;
        this.pageNumber = 0;
        this.mView = null;
        this.mKeyboardHeight = 0;
        this.an = 0;
        this.ao = false;
        this.ap = false;
        this.progressDialog = null;
        this.mRunnable = null;
        this.au = false;
        this.aw = false;
        this.c = false;
        this.m = null;
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final boolean z) {
        if (this.n != null) {
            preSaveQuestion(new Runnable() { // from class: com.artifex.sonui.editor.NUIDocView.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String userPath = NUIDocView.this.mSOFileState.getUserPath();
                        if (userPath == null) {
                            userPath = NUIDocView.this.mSOFileState.getOpenedPath();
                        }
                        try {
                            NUIDocView.this.n.saveAsHandler(new File(userPath).getName(), NUIDocView.this.mSession.getDoc(), new SOSaveAsComplete() { // from class: com.artifex.sonui.editor.NUIDocView.1.1
                                @Override // com.artifex.sonui.editor.SOSaveAsComplete
                                public void onComplete(int i, String str) {
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    if (i == 0) {
                                        NUIDocView.this.setFooterText(str);
                                        NUIDocView.this.mSOFileState.setUserPath(str);
                                        AnonymousClass1 anonymousClass2 = AnonymousClass1.this;
                                        if (z) {
                                            NUIDocView.this.prefinish();
                                        }
                                        NUIDocView nUIDocView = NUIDocView.this;
                                        if (nUIDocView.mFinished) {
                                            return;
                                        }
                                        nUIDocView.mSOFileState.setHasChanges(false);
                                        NUIDocView.this.onSelectionChanged();
                                        NUIDocView.this.reloadFile();
                                    } else {
                                        NUIDocView.this.mSOFileState.setUserPath(null);
                                    }
                                    NUIDocView nUIDocView2 = NUIDocView.this;
                                    nUIDocView2.T = nUIDocView2.mSOFileState.isTemplate();
                                }
                            });
                        } catch (UnsupportedOperationException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedOperationException unused) {
                    }
                }
            }, new Runnable(this) { // from class: com.artifex.sonui.editor.NUIDocView.2
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        } else {
            d8.a();
        }
    }
}
