package com.hwa.excelllll.customviews.smartrefresh.header;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.onesignal.core.internal.permissions.PermissionsViewModel;
import com.xlsxreader.excelreader.R;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.ArrowDrawable;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.ProgressDrawable;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.SmartUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class ClassicsHeader extends InternalClassics<ClassicsHeader> implements RefreshHeader {

    /* JADX INFO: renamed from: A */
    public final String f48542A;

    /* JADX INFO: renamed from: B */
    public final String f48543B;

    /* JADX INFO: renamed from: C */
    public final String f48544C;

    /* JADX INFO: renamed from: D */
    public final String f48545D;

    /* JADX INFO: renamed from: q */
    public final String f48546q;

    /* JADX INFO: renamed from: r */
    public Date f48547r;

    /* JADX INFO: renamed from: s */
    public final TextView f48548s;

    /* JADX INFO: renamed from: t */
    public final SharedPreferences f48549t;

    /* JADX INFO: renamed from: u */
    public final SimpleDateFormat f48550u;

    /* JADX INFO: renamed from: v */
    public final boolean f48551v;

    /* JADX INFO: renamed from: w */
    public final String f48552w;

    /* JADX INFO: renamed from: x */
    public final String f48553x;

    /* JADX INFO: renamed from: y */
    public final String f48554y;

    /* JADX INFO: renamed from: z */
    public final String f48555z;

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.header.ClassicsHeader$1 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static /* synthetic */ class C74391 {

        /* JADX INFO: renamed from: a */
        public static final /* synthetic */ int[] f48556a;

        static {
            int[] iArr = new int[RefreshState.values().length];
            f48556a = iArr;
            try {
                RefreshState refreshState = RefreshState.None;
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                int[] iArr2 = f48556a;
                RefreshState refreshState2 = RefreshState.None;
                iArr2[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                int[] iArr3 = f48556a;
                RefreshState refreshState3 = RefreshState.None;
                iArr3[11] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                int[] iArr4 = f48556a;
                RefreshState refreshState4 = RefreshState.None;
                iArr4[9] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                int[] iArr5 = f48556a;
                RefreshState refreshState5 = RefreshState.None;
                iArr5[5] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                int[] iArr6 = f48556a;
                RefreshState refreshState6 = RefreshState.None;
                iArr6[7] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                int[] iArr7 = f48556a;
                RefreshState refreshState7 = RefreshState.None;
                iArr7[12] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public ClassicsHeader(Context context) {
        super(context, null, 0);
        this.f48590m = PermissionsViewModel.DELAY_TIME_CALLBACK_CALL;
        this.f48591n = 20;
        this.f48592o = 20;
        this.f48593p = 0;
        this.f48579b = SpinnerStyle.f48497d;
        this.f48546q = "LAST_UPDATE_TIME";
        this.f48551v = true;
        View.inflate(context, R.layout.srl_classics_header, this);
        ImageView imageView = (ImageView) findViewById(R.id.srl_classics_arrow);
        this.f48582e = imageView;
        TextView textView = (TextView) findViewById(R.id.srl_classics_update);
        this.f48548s = textView;
        ImageView imageView2 = (ImageView) findViewById(R.id.srl_classics_progress);
        this.f48583f = imageView2;
        this.f48581d = (TextView) findViewById(R.id.srl_classics_title);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, R.styleable.ClassicsHeader);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        new LinearLayout.LayoutParams(-2, -2).topMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextTimeMarginTop, SmartUtil.m21920c(0.0f));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsFooter_srlDrawableMarginRight, SmartUtil.m21920c(20.0f));
        layoutParams2.rightMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        layoutParams.width = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, layoutParams.width);
        layoutParams.height = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableArrowSize, layoutParams.height);
        layoutParams2.width = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, layoutParams2.width);
        layoutParams2.height = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableProgressSize, layoutParams2.height);
        layoutParams.width = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams.width);
        layoutParams.height = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams.height);
        layoutParams2.width = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams2.width);
        layoutParams2.height = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ClassicsHeader_srlDrawableSize, layoutParams2.height);
        this.f48590m = typedArrayObtainStyledAttributes.getInt(R.styleable.ClassicsHeader_srlFinishDuration, this.f48590m);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ClassicsHeader_srlEnableLastTime, true);
        this.f48551v = z;
        this.f48579b = SpinnerStyle.f48501h[typedArrayObtainStyledAttributes.getInt(R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, this.f48579b.f48502a)];
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlDrawableArrow);
        ImageView imageView3 = this.f48582e;
        if (zHasValue) {
            imageView3.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.ClassicsHeader_srlDrawableArrow));
        } else if (imageView3.getDrawable() == null) {
            ArrowDrawable arrowDrawable = new ArrowDrawable();
            this.f48585h = arrowDrawable;
            arrowDrawable.f48594a.setColor(getResources().getColor(R.color.colorSolidText));
            this.f48582e.setImageDrawable(this.f48585h);
        }
        boolean zHasValue2 = typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlDrawableProgress);
        ImageView imageView4 = this.f48583f;
        if (zHasValue2) {
            imageView4.setImageDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.ClassicsHeader_srlDrawableProgress));
        } else if (imageView4.getDrawable() == null) {
            ProgressDrawable progressDrawable = new ProgressDrawable();
            progressDrawable.f48595b = 0;
            progressDrawable.f48596c = 0;
            progressDrawable.f48597d = 0;
            progressDrawable.f48599f = new Path();
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(30, 3600);
            progressDrawable.f48598e = valueAnimatorOfInt;
            valueAnimatorOfInt.setDuration(10000L);
            valueAnimatorOfInt.setInterpolator(null);
            valueAnimatorOfInt.setRepeatCount(-1);
            valueAnimatorOfInt.setRepeatMode(1);
            this.f48586i = progressDrawable;
            progressDrawable.f48594a.setColor(getResources().getColor(R.color.colorSolidText));
            this.f48583f.setImageDrawable(this.f48586i);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextSizeTitle)) {
            this.f48581d.setTextSize(0, typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTitle, SmartUtil.m21920c(16.0f)));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextSizeTime)) {
            textView.setTextSize(0, typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ClassicsHeader_srlTextSizeTime, SmartUtil.m21920c(12.0f)));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlPrimaryColor)) {
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.ClassicsHeader_srlPrimaryColor, 0);
            this.f48588k = true;
            this.f48589l = color;
            RefreshKernel refreshKernel = this.f48584g;
            if (refreshKernel != null) {
                refreshKernel.mo21892c(this, color);
            }
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlAccentColor)) {
            int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.ClassicsHeader_srlAccentColor, 0);
            textView.setTextColor((16777215 & color2) | (-872415232));
            super.mo21909g(color2);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextPulling)) {
            this.f48552w = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextPulling);
        } else {
            this.f48552w = context.getString(R.string.srl_header_pulling);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextLoading)) {
            this.f48554y = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextLoading);
        } else {
            this.f48554y = context.getString(R.string.srl_header_loading);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextRelease)) {
            this.f48555z = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextRelease);
        } else {
            this.f48555z = context.getString(R.string.srl_header_release);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextFinish)) {
            this.f48542A = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextFinish);
        } else {
            this.f48542A = context.getString(R.string.srl_header_finish);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextFailed)) {
            this.f48543B = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextFailed);
        } else {
            this.f48543B = context.getString(R.string.srl_header_failed);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextSecondary)) {
            this.f48545D = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextSecondary);
        } else {
            this.f48545D = context.getString(R.string.srl_header_secondary);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextRefreshing)) {
            this.f48553x = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextRefreshing);
        } else {
            this.f48553x = context.getString(R.string.srl_header_refreshing);
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.ClassicsHeader_srlTextUpdate)) {
            this.f48544C = typedArrayObtainStyledAttributes.getString(R.styleable.ClassicsHeader_srlTextUpdate);
        } else {
            this.f48544C = context.getString(R.string.srl_header_update);
        }
        this.f48550u = new SimpleDateFormat(this.f48544C, Locale.getDefault());
        typedArrayObtainStyledAttributes.recycle();
        imageView2.animate().setInterpolator(null);
        textView.setVisibility(z ? 0 : 8);
        this.f48581d.setText(isInEditMode() ? this.f48553x : this.f48552w);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
        try {
            if ((context instanceof FragmentActivity) && ((FragmentActivity) context).getSupportFragmentManager().m3645G().size() > 0) {
                m21910k(new Date());
                return;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String str = this.f48546q + context.getClass().getName();
        this.f48546q = str;
        SharedPreferences sharedPreferences = context.getSharedPreferences("ClassicsHeader", 0);
        this.f48549t = sharedPreferences;
        m21910k(new Date(sharedPreferences.getLong(str, System.currentTimeMillis())));
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnStateChangedListener
    /* JADX INFO: renamed from: b */
    public final void mo21908b(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        ImageView imageView = this.f48582e;
        int iOrdinal = refreshState2.ordinal();
        boolean z = this.f48551v;
        TextView textView = this.f48548s;
        if (iOrdinal == 0) {
            textView.setVisibility(z ? 0 : 8);
        } else if (iOrdinal != 1) {
            if (iOrdinal == 5) {
                this.f48581d.setText(this.f48555z);
                imageView.animate().rotation(180.0f);
                return;
            }
            if (iOrdinal == 7) {
                this.f48581d.setText(this.f48545D);
                imageView.animate().rotation(0.0f);
                return;
            } else if (iOrdinal == 9 || iOrdinal == 11) {
                this.f48581d.setText(this.f48553x);
                imageView.setVisibility(8);
                return;
            } else {
                if (iOrdinal != 12) {
                    return;
                }
                imageView.setVisibility(8);
                textView.setVisibility(z ? 4 : 8);
                this.f48581d.setText(this.f48554y);
                return;
            }
        }
        this.f48581d.setText(this.f48552w);
        imageView.setVisibility(0);
        imageView.animate().rotation(0.0f);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics
    /* JADX INFO: renamed from: g */
    public final void mo21909g(int i) {
        this.f48548s.setTextColor((16777215 & i) | (-872415232));
        super.mo21909g(i);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics, com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: h */
    public final int mo21904h(RefreshLayout refreshLayout, boolean z) {
        TextView textView = this.f48581d;
        if (z) {
            textView.setText(this.f48542A);
            if (this.f48547r != null) {
                m21910k(new Date());
            }
        } else {
            textView.setText(this.f48543B);
        }
        return super.mo21904h(refreshLayout, z);
    }

    /* JADX INFO: renamed from: k */
    public final void m21910k(Date date) {
        this.f48547r = date;
        this.f48548s.setText(this.f48550u.format(date));
        SharedPreferences sharedPreferences = this.f48549t;
        if (sharedPreferences == null || isInEditMode()) {
            return;
        }
        sharedPreferences.edit().putLong(this.f48546q, date.getTime()).apply();
    }
}
