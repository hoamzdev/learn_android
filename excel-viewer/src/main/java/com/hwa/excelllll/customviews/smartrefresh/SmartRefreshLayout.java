package com.hwa.excelllll.customviews.smartrefresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.supportv1.p030v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.ads.AdError;
import com.xlsxreader.excelreader.R;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.DefaultRefreshFooterCreator;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.DefaultRefreshHeaderCreator;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.DefaultRefreshInitializer;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshFooter;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.DimensionStatus;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.xlsxreader.excelreader.customviews.smartrefresh.footer.BallPulseFooter;
import com.xlsxreader.excelreader.customviews.smartrefresh.header.BezierRadarHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.impl.RefreshContentWrapper;
import com.xlsxreader.excelreader.customviews.smartrefresh.impl.RefreshFooterWrapper;
import com.xlsxreader.excelreader.customviews.smartrefresh.impl.RefreshHeaderWrapper;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract;
import com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnRefreshListener;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.DesignUtil;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.SmartUtil;
import java.util.LinkedList;
import java.util.WeakHashMap;
import p000.C0013a7;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class SmartRefreshLayout extends ViewGroup implements RefreshLayout, NestedScrollingParent {

    /* JADX INFO: renamed from: n1 */
    public static DefaultRefreshFooterCreator f48340n1;

    /* JADX INFO: renamed from: o1 */
    public static DefaultRefreshHeaderCreator f48341o1;

    /* JADX INFO: renamed from: p1 */
    public static DefaultRefreshInitializer f48342p1;

    /* JADX INFO: renamed from: q1 */
    public static final MarginLayoutParams f48343q1 = new MarginLayoutParams(-1, -1);

    /* JADX INFO: renamed from: A */
    public final VelocityTracker f48344A;

    /* JADX INFO: renamed from: B */
    public final SmartUtil f48345B;

    /* JADX INFO: renamed from: C */
    public final int[] f48346C;

    /* JADX INFO: renamed from: D */
    public boolean f48347D;

    /* JADX INFO: renamed from: D0 */
    public boolean f48348D0;

    /* JADX INFO: renamed from: E */
    public boolean f48349E;

    /* JADX INFO: renamed from: E0 */
    public OnRefreshListener f48350E0;

    /* JADX INFO: renamed from: F */
    public final boolean f48351F;

    /* JADX INFO: renamed from: F0 */
    public int f48352F0;

    /* JADX INFO: renamed from: G */
    public final boolean f48353G;

    /* JADX INFO: renamed from: G0 */
    public boolean f48354G0;

    /* JADX INFO: renamed from: H */
    public final boolean f48355H;

    /* JADX INFO: renamed from: H0 */
    public final int[] f48356H0;

    /* JADX INFO: renamed from: I */
    public final boolean f48357I;

    /* JADX INFO: renamed from: I0 */
    public final NestedScrollingChildHelper f48358I0;

    /* JADX INFO: renamed from: J */
    public final boolean f48359J;

    /* JADX INFO: renamed from: J0 */
    public final NestedScrollingParentHelper f48360J0;

    /* JADX INFO: renamed from: K */
    public final boolean f48361K;

    /* JADX INFO: renamed from: K0 */
    public int f48362K0;

    /* JADX INFO: renamed from: L */
    public final boolean f48363L;

    /* JADX INFO: renamed from: L0 */
    public DimensionStatus f48364L0;

    /* JADX INFO: renamed from: M */
    public final boolean f48365M;

    /* JADX INFO: renamed from: M0 */
    public int f48366M0;

    /* JADX INFO: renamed from: N */
    public boolean f48367N;

    /* JADX INFO: renamed from: N0 */
    public DimensionStatus f48368N0;

    /* JADX INFO: renamed from: O */
    public final boolean f48369O;

    /* JADX INFO: renamed from: O0 */
    public final int f48370O0;

    /* JADX INFO: renamed from: P */
    public final boolean f48371P;

    /* JADX INFO: renamed from: P0 */
    public final int f48372P0;

    /* JADX INFO: renamed from: Q */
    public final boolean f48373Q;

    /* JADX INFO: renamed from: Q0 */
    public float f48374Q0;

    /* JADX INFO: renamed from: R */
    public final boolean f48375R;

    /* JADX INFO: renamed from: R0 */
    public final float f48376R0;

    /* JADX INFO: renamed from: S */
    public boolean f48377S;

    /* JADX INFO: renamed from: S0 */
    public final float f48378S0;

    /* JADX INFO: renamed from: T */
    public final boolean f48379T;

    /* JADX INFO: renamed from: T0 */
    public final float f48380T0;

    /* JADX INFO: renamed from: U */
    public final boolean f48381U;

    /* JADX INFO: renamed from: U0 */
    public RefreshHeader f48382U0;

    /* JADX INFO: renamed from: V */
    public boolean f48383V;

    /* JADX INFO: renamed from: V0 */
    public RefreshFooter f48384V0;

    /* JADX INFO: renamed from: W */
    public boolean f48385W;

    /* JADX INFO: renamed from: W0 */
    public RefreshContentWrapper f48386W0;

    /* JADX INFO: renamed from: X0 */
    public Paint f48387X0;

    /* JADX INFO: renamed from: Y0 */
    public final Handler f48388Y0;

    /* JADX INFO: renamed from: Z0 */
    public final RefreshKernelImpl f48389Z0;

    /* JADX INFO: renamed from: a */
    public final int f48390a;

    /* JADX INFO: renamed from: a1 */
    public RefreshState f48391a1;

    /* JADX INFO: renamed from: b */
    public int f48392b;

    /* JADX INFO: renamed from: b1 */
    public RefreshState f48393b1;

    /* JADX INFO: renamed from: c */
    public int f48394c;

    /* JADX INFO: renamed from: c1 */
    public long f48395c1;

    /* JADX INFO: renamed from: d */
    public int f48396d;

    /* JADX INFO: renamed from: d1 */
    public int f48397d1;

    /* JADX INFO: renamed from: e */
    public int f48398e;

    /* JADX INFO: renamed from: e1 */
    public int f48399e1;

    /* JADX INFO: renamed from: f */
    public final int f48400f;

    /* JADX INFO: renamed from: f1 */
    public boolean f48401f1;

    /* JADX INFO: renamed from: g */
    public final int f48402g;

    /* JADX INFO: renamed from: g1 */
    public boolean f48403g1;

    /* JADX INFO: renamed from: h */
    public float f48404h;

    /* JADX INFO: renamed from: h1 */
    public boolean f48405h1;

    /* JADX INFO: renamed from: i */
    public float f48406i;

    /* JADX INFO: renamed from: i1 */
    public boolean f48407i1;

    /* JADX INFO: renamed from: j */
    public float f48408j;

    /* JADX INFO: renamed from: j1 */
    public boolean f48409j1;

    /* JADX INFO: renamed from: k */
    public float f48410k;

    /* JADX INFO: renamed from: k1 */
    public MotionEvent f48411k1;

    /* JADX INFO: renamed from: l */
    public float f48412l;

    /* JADX INFO: renamed from: l1 */
    public Runnable f48413l1;

    /* JADX INFO: renamed from: m */
    public float f48414m;

    /* JADX INFO: renamed from: m1 */
    public ValueAnimator f48415m1;

    /* JADX INFO: renamed from: n */
    public final float f48416n;

    /* JADX INFO: renamed from: o */
    public char f48417o;

    /* JADX INFO: renamed from: p */
    public boolean f48418p;

    /* JADX INFO: renamed from: q */
    public boolean f48419q;

    /* JADX INFO: renamed from: r */
    public boolean f48420r;

    /* JADX INFO: renamed from: s */
    public final int f48421s;

    /* JADX INFO: renamed from: t */
    public final int f48422t;

    /* JADX INFO: renamed from: u */
    public final int f48423u;

    /* JADX INFO: renamed from: v */
    public final int f48424v;

    /* JADX INFO: renamed from: w */
    public final int f48425w;

    /* JADX INFO: renamed from: x */
    public final int f48426x;

    /* JADX INFO: renamed from: y */
    public int f48427y;

    /* JADX INFO: renamed from: z */
    public final Scroller f48428z;

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$10 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static /* synthetic */ class C742710 {

        /* JADX INFO: renamed from: a */
        public static final /* synthetic */ int[] f48431a;

        static {
            int[] iArr = new int[RefreshState.values().length];
            f48431a = iArr;
            try {
                RefreshState refreshState = RefreshState.None;
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                int[] iArr2 = f48431a;
                RefreshState refreshState2 = RefreshState.None;
                iArr2[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                int[] iArr3 = f48431a;
                RefreshState refreshState3 = RefreshState.None;
                iArr3[2] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                int[] iArr4 = f48431a;
                RefreshState refreshState4 = RefreshState.None;
                iArr4[3] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                int[] iArr5 = f48431a;
                RefreshState refreshState5 = RefreshState.None;
                iArr5[4] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                int[] iArr6 = f48431a;
                RefreshState refreshState6 = RefreshState.None;
                iArr6[5] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                int[] iArr7 = f48431a;
                RefreshState refreshState7 = RefreshState.None;
                iArr7[6] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                int[] iArr8 = f48431a;
                RefreshState refreshState8 = RefreshState.None;
                iArr8[7] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                int[] iArr9 = f48431a;
                RefreshState refreshState9 = RefreshState.None;
                iArr9[9] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                int[] iArr10 = f48431a;
                RefreshState refreshState10 = RefreshState.None;
                iArr10[10] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                int[] iArr11 = f48431a;
                RefreshState refreshState11 = RefreshState.None;
                iArr11[11] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                int[] iArr12 = f48431a;
                RefreshState refreshState12 = RefreshState.None;
                iArr12[12] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$7 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    class RunnableC74337 implements Runnable {

        /* JADX INFO: renamed from: a */
        public int f48442a = 0;

        /* JADX INFO: renamed from: b */
        public final /* synthetic */ int f48443b;

        /* JADX INFO: renamed from: c */
        public final /* synthetic */ boolean f48444c;

        public RunnableC74337(int i, boolean z) {
            this.f48443b = i;
            this.f48444c = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i = this.f48442a;
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            Handler handler = smartRefreshLayout.f48388Y0;
            boolean z = this.f48444c;
            if (i != 0) {
                int iMo21904h = smartRefreshLayout.f48384V0.mo21904h(smartRefreshLayout, true);
                if (iMo21904h < Integer.MAX_VALUE) {
                    boolean z2 = z && smartRefreshLayout.f48359J && smartRefreshLayout.f48392b < 0 && smartRefreshLayout.f48386W0.m21912a();
                    int i2 = smartRefreshLayout.f48392b;
                    final int iMax = i2 - (z2 ? Math.max(i2, -smartRefreshLayout.f48366M0) : 0);
                    if (smartRefreshLayout.f48418p || smartRefreshLayout.f48354G0) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (smartRefreshLayout.f48418p) {
                            float f = smartRefreshLayout.f48410k;
                            smartRefreshLayout.f48406i = f;
                            smartRefreshLayout.f48396d = smartRefreshLayout.f48392b - iMax;
                            smartRefreshLayout.f48418p = false;
                            float f2 = smartRefreshLayout.f48357I ? iMax : 0;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 0, smartRefreshLayout.f48408j, f + f2 + (smartRefreshLayout.f48390a * 2), 0));
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 2, smartRefreshLayout.f48408j, smartRefreshLayout.f48410k + f2, 0));
                        }
                        if (smartRefreshLayout.f48354G0) {
                            smartRefreshLayout.f48352F0 = 0;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 1, smartRefreshLayout.f48408j, smartRefreshLayout.f48410k, 0));
                            smartRefreshLayout.f48354G0 = false;
                            smartRefreshLayout.f48396d = 0;
                        }
                    }
                    handler.postDelayed(new Runnable() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.7.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RefreshContentWrapper refreshContentWrapperM21915e;
                            ValueAnimator valueAnimatorM21895f;
                            RunnableC74337 runnableC74337 = RunnableC74337.this;
                            SmartRefreshLayout smartRefreshLayout2 = SmartRefreshLayout.this;
                            if (!smartRefreshLayout2.f48371P || iMax >= 0) {
                                refreshContentWrapperM21915e = null;
                            } else {
                                refreshContentWrapperM21915e = smartRefreshLayout2.f48386W0.m21915e(smartRefreshLayout2.f48392b);
                                if (refreshContentWrapperM21915e != null) {
                                    refreshContentWrapperM21915e.onAnimationUpdate(ValueAnimator.ofInt(0, 0));
                                }
                            }
                            AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.7.1.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    RunnableC74337 runnableC74338 = RunnableC74337.this;
                                    if (animator == null || animator.getDuration() != 0) {
                                        SmartRefreshLayout smartRefreshLayout3 = SmartRefreshLayout.this;
                                        smartRefreshLayout3.f48407i1 = false;
                                        if (runnableC74338.f48444c) {
                                            smartRefreshLayout3.m21873A(true);
                                        }
                                        SmartRefreshLayout smartRefreshLayout4 = SmartRefreshLayout.this;
                                        if (smartRefreshLayout4.f48391a1 == RefreshState.LoadFinish) {
                                            smartRefreshLayout4.m21888y(RefreshState.None);
                                        }
                                    }
                                }
                            };
                            RefreshKernelImpl refreshKernelImpl = smartRefreshLayout2.f48389Z0;
                            int i3 = smartRefreshLayout2.f48392b;
                            if (i3 > 0) {
                                valueAnimatorM21895f = refreshKernelImpl.m21895f(0);
                            } else {
                                if (refreshContentWrapperM21915e != null || i3 == 0) {
                                    ValueAnimator valueAnimator = smartRefreshLayout2.f48415m1;
                                    if (valueAnimator != null) {
                                        valueAnimator.setDuration(0L);
                                        smartRefreshLayout2.f48415m1.cancel();
                                        smartRefreshLayout2.f48415m1 = null;
                                    }
                                    smartRefreshLayout2.f48389Z0.m21896g(0, false);
                                    smartRefreshLayout2.f48389Z0.mo21890a(RefreshState.None);
                                } else if (runnableC74337.f48444c && smartRefreshLayout2.f48359J) {
                                    int i4 = -smartRefreshLayout2.f48366M0;
                                    if (i3 >= i4) {
                                        smartRefreshLayout2.m21888y(RefreshState.None);
                                    } else {
                                        valueAnimatorM21895f = refreshKernelImpl.m21895f(i4);
                                    }
                                } else {
                                    valueAnimatorM21895f = refreshKernelImpl.m21895f(0);
                                }
                                valueAnimatorM21895f = null;
                            }
                            if (valueAnimatorM21895f != null) {
                                valueAnimatorM21895f.addListener(animatorListenerAdapter);
                            } else {
                                animatorListenerAdapter.onAnimationEnd(null);
                            }
                        }
                    }, smartRefreshLayout.f48392b < 0 ? iMo21904h : 0L);
                    return;
                }
                return;
            }
            RefreshState refreshState = smartRefreshLayout.f48391a1;
            RefreshState refreshState2 = RefreshState.None;
            if (refreshState == refreshState2 && smartRefreshLayout.f48393b1 == RefreshState.Loading) {
                smartRefreshLayout.f48393b1 = refreshState2;
            } else {
                ValueAnimator valueAnimator = smartRefreshLayout.f48415m1;
                if (valueAnimator != null && ((refreshState.f48493d || refreshState == RefreshState.LoadReleased) && refreshState.f48491b)) {
                    valueAnimator.setDuration(0L);
                    smartRefreshLayout.f48415m1.cancel();
                    smartRefreshLayout.f48415m1 = null;
                    if (smartRefreshLayout.f48389Z0.m21895f(0) == null) {
                        smartRefreshLayout.m21888y(refreshState2);
                    } else {
                        smartRefreshLayout.m21888y(RefreshState.PullUpCanceled);
                    }
                } else if (refreshState == RefreshState.Loading && smartRefreshLayout.f48384V0 != null && smartRefreshLayout.f48386W0 != null) {
                    this.f48442a = i + 1;
                    handler.postDelayed(this, this.f48443b);
                    smartRefreshLayout.m21888y(RefreshState.LoadFinish);
                    return;
                }
            }
            if (z) {
                smartRefreshLayout.m21873A(true);
            }
        }
    }

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$8 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    class RunnableC74348 implements Runnable {

        /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$8$1, reason: invalid class name */
        /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
        class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                throw null;
            }
        }

        /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$8$2, reason: invalid class name */
        /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
        class AnonymousClass2 extends AnimatorListenerAdapter {
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    throw null;
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            throw null;
        }
    }

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$9 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    class RunnableC74359 implements Runnable {

        /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$9$1, reason: invalid class name */
        /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
        class AnonymousClass1 implements ValueAnimator.AnimatorUpdateListener {
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                throw null;
            }
        }

        /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout$9$2, reason: invalid class name */
        /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
        class AnonymousClass2 extends AnimatorListenerAdapter {
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    throw null;
                }
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            throw null;
        }
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class BounceRunnable implements Runnable {

        /* JADX INFO: renamed from: b */
        public final int f48450b;

        /* JADX INFO: renamed from: e */
        public float f48453e;

        /* JADX INFO: renamed from: a */
        public int f48449a = 0;

        /* JADX INFO: renamed from: d */
        public float f48452d = 0.0f;

        /* JADX INFO: renamed from: c */
        public long f48451c = AnimationUtils.currentAnimationTimeMillis();

        public BounceRunnable(float f, int i) {
            this.f48453e = f;
            this.f48450b = i;
            SmartRefreshLayout.this.f48388Y0.postDelayed(this, 10L);
            RefreshKernelImpl refreshKernelImpl = SmartRefreshLayout.this.f48389Z0;
            if (f > 0.0f) {
                refreshKernelImpl.mo21890a(RefreshState.PullDownToRefresh);
            } else {
                refreshKernelImpl.mo21890a(RefreshState.PullUpToLoad);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            RefreshKernelImpl refreshKernelImpl = smartRefreshLayout.f48389Z0;
            if (smartRefreshLayout.f48413l1 != this || smartRefreshLayout.f48391a1.f48495f) {
                return;
            }
            int iAbs = Math.abs(smartRefreshLayout.f48392b);
            int i = this.f48450b;
            int iAbs2 = Math.abs(i);
            float f = this.f48453e;
            if (iAbs >= iAbs2) {
                int i2 = this.f48449a;
                if (i != 0) {
                    int i3 = i2 + 1;
                    this.f48449a = i3;
                    this.f48453e = (float) (Math.pow(0.44999998807907104d, i3 * 2) * ((double) f));
                } else {
                    int i4 = i2 + 1;
                    this.f48449a = i4;
                    this.f48453e = (float) (Math.pow(0.8500000238418579d, i4 * 2) * ((double) f));
                }
            } else {
                int i5 = this.f48449a + 1;
                this.f48449a = i5;
                this.f48453e = (float) (Math.pow(0.949999988079071d, i5 * 2) * ((double) f));
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            float f2 = this.f48453e * (((jCurrentAnimationTimeMillis - this.f48451c) * 1.0f) / 1000.0f);
            if (Math.abs(f2) >= 1.0f) {
                this.f48451c = jCurrentAnimationTimeMillis;
                float f3 = this.f48452d + f2;
                this.f48452d = f3;
                smartRefreshLayout.m21887x(f3);
                smartRefreshLayout.f48388Y0.postDelayed(this, 10L);
                return;
            }
            RefreshState refreshState = smartRefreshLayout.f48393b1;
            boolean z = refreshState.f48493d;
            if (z && refreshState.f48490a) {
                refreshKernelImpl.mo21890a(RefreshState.PullDownCanceled);
            } else if (z && refreshState.f48491b) {
                refreshKernelImpl.mo21890a(RefreshState.PullUpCanceled);
            }
            smartRefreshLayout.f48413l1 = null;
            if (Math.abs(smartRefreshLayout.f48392b) >= Math.abs(i)) {
                smartRefreshLayout.m21880q(i, 0, smartRefreshLayout.f48345B, Math.min(Math.max((int) (Math.abs(smartRefreshLayout.f48392b - i) / SmartUtil.f48601b), 30), 100) * 10);
            }
        }
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class FlingRunnable implements Runnable {

        /* JADX INFO: renamed from: a */
        public int f48455a;

        /* JADX INFO: renamed from: b */
        public float f48456b;

        /* JADX INFO: renamed from: c */
        public long f48457c = 0;

        /* JADX INFO: renamed from: d */
        public long f48458d = AnimationUtils.currentAnimationTimeMillis();

        public FlingRunnable(float f) {
            this.f48456b = f;
            this.f48455a = SmartRefreshLayout.this.f48392b;
        }

        @Override // java.lang.Runnable
        public final void run() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            RefreshKernelImpl refreshKernelImpl = smartRefreshLayout.f48389Z0;
            if (smartRefreshLayout.f48413l1 != this || smartRefreshLayout.f48391a1.f48495f) {
                return;
            }
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            long j = jCurrentAnimationTimeMillis - this.f48458d;
            float fPow = (float) (Math.pow(0.9800000190734863d, (jCurrentAnimationTimeMillis - this.f48457c) / 100.0f) * ((double) this.f48456b));
            this.f48456b = fPow;
            float f = ((j * 1.0f) / 1000.0f) * fPow;
            if (Math.abs(f) <= 1.0f) {
                smartRefreshLayout.f48413l1 = null;
                return;
            }
            this.f48458d = jCurrentAnimationTimeMillis;
            int i = (int) (this.f48455a + f);
            this.f48455a = i;
            if (smartRefreshLayout.f48392b * i > 0) {
                refreshKernelImpl.m21896g(i, true);
                smartRefreshLayout.f48388Y0.postDelayed(this, 10L);
                return;
            }
            smartRefreshLayout.f48413l1 = null;
            refreshKernelImpl.m21896g(0, true);
            View view = smartRefreshLayout.f48386W0.f48566c;
            int i2 = (int) (-this.f48456b);
            float f2 = SmartUtil.f48601b;
            if (view instanceof ScrollView) {
                ((ScrollView) view).fling(i2);
            } else if (view instanceof AbsListView) {
                ((AbsListView) view).fling(i2);
            } else if (view instanceof WebView) {
                ((WebView) view).flingScroll(0, i2);
            } else if (view instanceof NestedScrollView) {
                ((NestedScrollView) view).m2708d(i2);
            } else if (view instanceof RecyclerView) {
                ((RecyclerView) view).m4231F(0, i2);
            }
            if (!smartRefreshLayout.f48407i1 || f <= 0.0f) {
                return;
            }
            smartRefreshLayout.f48407i1 = false;
        }
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static class LayoutParams extends MarginLayoutParams {

        /* JADX INFO: renamed from: a */
        public int f48460a;

        /* JADX INFO: renamed from: b */
        public SpinnerStyle f48461b;

        public LayoutParams(int i) {
            super(-1, i);
            this.f48460a = 0;
            this.f48461b = null;
        }
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class RefreshKernelImpl implements RefreshKernel {
        public RefreshKernelImpl() {
        }

        @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel
        /* JADX INFO: renamed from: a */
        public final void mo21890a(RefreshState refreshState) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            boolean z = smartRefreshLayout.f48359J;
            switch (refreshState) {
                case None:
                    RefreshState refreshState2 = smartRefreshLayout.f48391a1;
                    RefreshState refreshState3 = RefreshState.None;
                    if (refreshState2 != refreshState3 && smartRefreshLayout.f48392b == 0) {
                        smartRefreshLayout.m21888y(refreshState3);
                    } else if (smartRefreshLayout.f48392b != 0) {
                        m21895f(0);
                    }
                    break;
                case PullDownToRefresh:
                    if (!smartRefreshLayout.f48391a1.f48494e && smartRefreshLayout.m21885v(smartRefreshLayout.f48347D)) {
                        smartRefreshLayout.m21888y(RefreshState.PullDownToRefresh);
                    } else {
                        smartRefreshLayout.setViceState(RefreshState.PullDownToRefresh);
                    }
                    break;
                case PullUpToLoad:
                    if (smartRefreshLayout.m21885v(smartRefreshLayout.f48349E)) {
                        RefreshState refreshState4 = smartRefreshLayout.f48391a1;
                        if (!refreshState4.f48494e && !refreshState4.f48495f && (!smartRefreshLayout.f48383V || !z || !smartRefreshLayout.f48385W)) {
                            smartRefreshLayout.m21888y(RefreshState.PullUpToLoad);
                        }
                    }
                    smartRefreshLayout.setViceState(RefreshState.PullUpToLoad);
                    break;
                case PullDownCanceled:
                    if (!smartRefreshLayout.f48391a1.f48494e && smartRefreshLayout.m21885v(smartRefreshLayout.f48347D)) {
                        smartRefreshLayout.m21888y(RefreshState.PullDownCanceled);
                        mo21890a(RefreshState.None);
                    } else {
                        smartRefreshLayout.setViceState(RefreshState.PullDownCanceled);
                    }
                    break;
                case PullUpCanceled:
                    if (!smartRefreshLayout.m21885v(smartRefreshLayout.f48349E) || smartRefreshLayout.f48391a1.f48494e || (smartRefreshLayout.f48383V && z && smartRefreshLayout.f48385W)) {
                        smartRefreshLayout.setViceState(RefreshState.PullUpCanceled);
                    } else {
                        smartRefreshLayout.m21888y(RefreshState.PullUpCanceled);
                        mo21890a(RefreshState.None);
                    }
                    break;
                case ReleaseToRefresh:
                    if (!smartRefreshLayout.f48391a1.f48494e && smartRefreshLayout.m21885v(smartRefreshLayout.f48347D)) {
                        smartRefreshLayout.m21888y(RefreshState.ReleaseToRefresh);
                    } else {
                        smartRefreshLayout.setViceState(RefreshState.ReleaseToRefresh);
                    }
                    break;
                case ReleaseToLoad:
                    if (smartRefreshLayout.m21885v(smartRefreshLayout.f48349E)) {
                        RefreshState refreshState5 = smartRefreshLayout.f48391a1;
                        if (!refreshState5.f48494e && !refreshState5.f48495f && (!smartRefreshLayout.f48383V || !z || !smartRefreshLayout.f48385W)) {
                            smartRefreshLayout.m21888y(RefreshState.ReleaseToLoad);
                        }
                    }
                    smartRefreshLayout.setViceState(RefreshState.ReleaseToLoad);
                    break;
                case ReleaseToTwoLevel:
                    if (!smartRefreshLayout.f48391a1.f48494e && smartRefreshLayout.m21885v(smartRefreshLayout.f48347D)) {
                        smartRefreshLayout.m21888y(RefreshState.ReleaseToTwoLevel);
                    } else {
                        smartRefreshLayout.setViceState(RefreshState.ReleaseToTwoLevel);
                    }
                    break;
                case TwoLevelReleased:
                default:
                    smartRefreshLayout.m21888y(refreshState);
                    break;
                case RefreshReleased:
                    if (!smartRefreshLayout.f48391a1.f48494e && smartRefreshLayout.m21885v(smartRefreshLayout.f48347D)) {
                        smartRefreshLayout.m21888y(RefreshState.RefreshReleased);
                    } else {
                        smartRefreshLayout.setViceState(RefreshState.RefreshReleased);
                    }
                    break;
                case LoadReleased:
                    if (!smartRefreshLayout.f48391a1.f48494e && smartRefreshLayout.m21885v(smartRefreshLayout.f48349E)) {
                        smartRefreshLayout.m21888y(RefreshState.LoadReleased);
                    } else {
                        smartRefreshLayout.setViceState(RefreshState.LoadReleased);
                    }
                    break;
                case Refreshing:
                    smartRefreshLayout.setStateRefreshing(true);
                    break;
                case Loading:
                    smartRefreshLayout.setStateLoading(true);
                    break;
            }
        }

        @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel
        /* JADX INFO: renamed from: b */
        public final RefreshKernelImpl mo21891b() {
            AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.RefreshKernelImpl.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (animator == null || animator.getDuration() != 0) {
                        SmartRefreshLayout.this.f48389Z0.mo21890a(RefreshState.TwoLevel);
                    }
                }
            };
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            int measuredHeight = smartRefreshLayout.getMeasuredHeight();
            float f = smartRefreshLayout.f48412l;
            ValueAnimator valueAnimatorM21895f = m21895f(f > 1.0f ? (int) f : (int) (measuredHeight * f));
            if (valueAnimatorM21895f == null || valueAnimatorM21895f != smartRefreshLayout.f48415m1) {
                animatorListenerAdapter.onAnimationEnd(null);
                return this;
            }
            valueAnimatorM21895f.setDuration(smartRefreshLayout.f48398e);
            valueAnimatorM21895f.addListener(animatorListenerAdapter);
            return this;
        }

        @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel
        /* JADX INFO: renamed from: c */
        public final RefreshKernelImpl mo21892c(InternalAbstract internalAbstract, int i) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            if (smartRefreshLayout.f48387X0 == null && i != 0) {
                smartRefreshLayout.f48387X0 = new Paint();
            }
            if (internalAbstract.equals(smartRefreshLayout.f48382U0)) {
                smartRefreshLayout.f48397d1 = i;
                return this;
            }
            if (internalAbstract.equals(smartRefreshLayout.f48384V0)) {
                smartRefreshLayout.f48399e1 = i;
            }
            return this;
        }

        @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel
        /* JADX INFO: renamed from: d */
        public final RefreshKernelImpl mo21893d() {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            smartRefreshLayout.f48398e = 0;
            smartRefreshLayout.f48412l = 0.0f;
            smartRefreshLayout.f48414m = 0.0f;
            return this;
        }

        @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel
        /* JADX INFO: renamed from: e */
        public final SmartRefreshLayout mo21894e() {
            return SmartRefreshLayout.this;
        }

        /* JADX INFO: renamed from: f */
        public final ValueAnimator m21895f(int i) {
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            return smartRefreshLayout.m21880q(i, 0, smartRefreshLayout.f48345B, smartRefreshLayout.f48400f);
        }

        /* JADX WARN: Code duplicated, block: B:46:0x0090  */
        /* JADX INFO: renamed from: g */
        public final RefreshKernelImpl m21896g(int i, boolean z) {
            float f;
            int i2;
            boolean z2;
            RefreshFooter refreshFooter;
            RefreshHeader refreshHeader;
            RefreshFooter refreshFooter2;
            RefreshFooter refreshFooter3;
            RefreshHeader refreshHeader2;
            RefreshHeader refreshHeader3;
            RefreshFooter refreshFooter4;
            SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
            boolean z3 = smartRefreshLayout.f48357I;
            boolean z4 = smartRefreshLayout.f48355H;
            RefreshKernelImpl refreshKernelImpl = smartRefreshLayout.f48389Z0;
            if (smartRefreshLayout.f48392b != i || (((refreshHeader3 = smartRefreshLayout.f48382U0) != null && refreshHeader3.mo21905i()) || ((refreshFooter4 = smartRefreshLayout.f48384V0) != null && refreshFooter4.mo21905i()))) {
                int i3 = smartRefreshLayout.f48392b;
                smartRefreshLayout.f48392b = i;
                if (z) {
                    RefreshState refreshState = smartRefreshLayout.f48393b1;
                    if (refreshState.f48493d || refreshState.f48494e) {
                        if (i > smartRefreshLayout.f48362K0 * smartRefreshLayout.f48378S0) {
                            if (smartRefreshLayout.f48391a1 != RefreshState.ReleaseToTwoLevel) {
                                refreshKernelImpl.mo21890a(RefreshState.ReleaseToRefresh);
                            }
                        } else if ((-i) > smartRefreshLayout.f48366M0 * smartRefreshLayout.f48380T0 && !smartRefreshLayout.f48383V) {
                            refreshKernelImpl.mo21890a(RefreshState.ReleaseToLoad);
                        } else if (i < 0 && !smartRefreshLayout.f48383V) {
                            refreshKernelImpl.mo21890a(RefreshState.PullUpToLoad);
                        } else if (i > 0) {
                            refreshKernelImpl.mo21890a(RefreshState.PullDownToRefresh);
                        }
                    }
                }
                RefreshContentWrapper refreshContentWrapper = smartRefreshLayout.f48386W0;
                SpinnerStyle spinnerStyle = SpinnerStyle.f48497d;
                if (refreshContentWrapper != null) {
                    if (i < 0 || (refreshHeader2 = smartRefreshLayout.f48382U0) == null) {
                        i2 = 0;
                        z2 = false;
                    } else {
                        if (smartRefreshLayout.m21886w(z4, refreshHeader2)) {
                            i2 = i;
                        } else if (i3 < 0) {
                            i2 = 0;
                        } else {
                            i2 = 0;
                            z2 = false;
                        }
                        z2 = true;
                    }
                    if (i <= 0 && (refreshFooter3 = smartRefreshLayout.f48384V0) != null) {
                        if (smartRefreshLayout.m21886w(z3, refreshFooter3)) {
                            i2 = i;
                        } else if (i3 > 0) {
                            i2 = 0;
                        }
                        z2 = true;
                    }
                    if (z2) {
                        smartRefreshLayout.f48386W0.m21914d(i2, smartRefreshLayout.f48423u, smartRefreshLayout.f48424v);
                        if (smartRefreshLayout.f48383V && smartRefreshLayout.f48385W && smartRefreshLayout.f48359J && (refreshFooter2 = smartRefreshLayout.f48384V0) != null && refreshFooter2.getSpinnerStyle() == spinnerStyle && smartRefreshLayout.m21885v(smartRefreshLayout.f48349E)) {
                            smartRefreshLayout.f48384V0.getView().setTranslationY(Math.max(0, i2));
                        }
                        boolean z5 = smartRefreshLayout.f48351F;
                        SpinnerStyle spinnerStyle2 = SpinnerStyle.f48498e;
                        boolean z6 = (z5 && (refreshHeader = smartRefreshLayout.f48382U0) != null && refreshHeader.getSpinnerStyle() == spinnerStyle2) || smartRefreshLayout.f48397d1 != 0;
                        boolean z7 = (smartRefreshLayout.f48353G && (refreshFooter = smartRefreshLayout.f48384V0) != null && refreshFooter.getSpinnerStyle() == spinnerStyle2) || smartRefreshLayout.f48399e1 != 0;
                        if ((z6 && (i2 >= 0 || i3 > 0)) || (z7 && (i2 <= 0 || i3 < 0))) {
                            smartRefreshLayout.invalidate();
                        }
                    }
                }
                if ((i >= 0 || i3 > 0) && smartRefreshLayout.f48382U0 != null) {
                    int iMax = Math.max(i, 0);
                    int i4 = smartRefreshLayout.f48362K0;
                    int i5 = (int) (i4 * smartRefreshLayout.f48374Q0);
                    float f2 = (iMax * 1.0f) / (i4 == 0 ? 1 : i4);
                    if (smartRefreshLayout.m21885v(smartRefreshLayout.f48347D)) {
                        f = 1.0f;
                    } else {
                        f = 1.0f;
                        if (smartRefreshLayout.f48391a1 == RefreshState.RefreshFinish && !z) {
                        }
                    }
                    if (i3 != smartRefreshLayout.f48392b) {
                        SpinnerStyle spinnerStyle3 = smartRefreshLayout.f48382U0.getSpinnerStyle();
                        RefreshHeader refreshHeader4 = smartRefreshLayout.f48382U0;
                        if (spinnerStyle3 == spinnerStyle) {
                            refreshHeader4.getView().setTranslationY(smartRefreshLayout.f48392b);
                            if (smartRefreshLayout.f48397d1 != 0 && smartRefreshLayout.f48387X0 != null && !smartRefreshLayout.m21886w(z4, smartRefreshLayout.f48382U0)) {
                                smartRefreshLayout.invalidate();
                            }
                        } else if (refreshHeader4.getSpinnerStyle().f48504c) {
                            View view = smartRefreshLayout.f48382U0.getView();
                            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                            MarginLayoutParams marginLayoutParams = layoutParams instanceof MarginLayoutParams ? (MarginLayoutParams) layoutParams : SmartRefreshLayout.f48343q1;
                            view.measure(MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(Math.max((smartRefreshLayout.f48392b - marginLayoutParams.bottomMargin) - marginLayoutParams.topMargin, 0), 1073741824));
                            int i6 = marginLayoutParams.leftMargin;
                            int i7 = marginLayoutParams.topMargin + smartRefreshLayout.f48370O0;
                            view.layout(i6, i7, view.getMeasuredWidth() + i6, view.getMeasuredHeight() + i7);
                        }
                        smartRefreshLayout.f48382U0.mo21906j(z, f2, iMax, i4, i5);
                    }
                    if (z && smartRefreshLayout.f48382U0.mo21905i()) {
                        int i8 = (int) smartRefreshLayout.f48408j;
                        int width = smartRefreshLayout.getWidth();
                        smartRefreshLayout.f48382U0.mo21900a(i8, width, smartRefreshLayout.f48408j / (width == 0 ? 1 : width));
                    }
                } else {
                    f = 1.0f;
                }
                if ((i <= 0 || i3 < 0) && smartRefreshLayout.f48384V0 != null) {
                    int i9 = -Math.min(i, 0);
                    int i10 = smartRefreshLayout.f48366M0;
                    int i11 = (int) (i10 * smartRefreshLayout.f48376R0);
                    float f3 = (i9 * f) / (i10 == 0 ? 1 : i10);
                    if (smartRefreshLayout.m21885v(smartRefreshLayout.f48349E) || (smartRefreshLayout.f48391a1 == RefreshState.LoadFinish && !z)) {
                        if (i3 != smartRefreshLayout.f48392b) {
                            SpinnerStyle spinnerStyle4 = smartRefreshLayout.f48384V0.getSpinnerStyle();
                            RefreshFooter refreshFooter5 = smartRefreshLayout.f48384V0;
                            if (spinnerStyle4 == spinnerStyle) {
                                refreshFooter5.getView().setTranslationY(smartRefreshLayout.f48392b);
                                if (smartRefreshLayout.f48399e1 != 0 && smartRefreshLayout.f48387X0 != null && !smartRefreshLayout.m21886w(z3, smartRefreshLayout.f48384V0)) {
                                    smartRefreshLayout.invalidate();
                                }
                            } else if (refreshFooter5.getSpinnerStyle().f48504c) {
                                View view2 = smartRefreshLayout.f48384V0.getView();
                                ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
                                MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof MarginLayoutParams ? (MarginLayoutParams) layoutParams2 : SmartRefreshLayout.f48343q1;
                                view2.measure(MeasureSpec.makeMeasureSpec(view2.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(Math.max(((-smartRefreshLayout.f48392b) - marginLayoutParams2.bottomMargin) - marginLayoutParams2.topMargin, 0), 1073741824));
                                int i12 = marginLayoutParams2.leftMargin;
                                int measuredHeight = (smartRefreshLayout.getMeasuredHeight() + marginLayoutParams2.topMargin) - smartRefreshLayout.f48372P0;
                                view2.layout(i12, measuredHeight - view2.getMeasuredHeight(), view2.getMeasuredWidth() + i12, measuredHeight);
                            }
                            smartRefreshLayout.f48384V0.mo21906j(z, f3, i9, i10, i11);
                        }
                        if (z && smartRefreshLayout.f48384V0.mo21905i()) {
                            int i13 = (int) smartRefreshLayout.f48408j;
                            int width2 = smartRefreshLayout.getWidth();
                            smartRefreshLayout.f48384V0.mo21900a(i13, width2, smartRefreshLayout.f48408j / (width2 == 0 ? 1 : width2));
                        }
                    }
                }
            }
            return this;
        }
    }

    public SmartRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f48398e = 300;
        this.f48400f = 300;
        this.f48412l = 1.0f;
        this.f48414m = 0.16666667f;
        this.f48416n = 0.5f;
        this.f48417o = 'n';
        this.f48421s = -1;
        this.f48422t = -1;
        this.f48423u = -1;
        this.f48424v = -1;
        this.f48347D = true;
        this.f48349E = false;
        this.f48351F = true;
        this.f48353G = true;
        this.f48355H = true;
        this.f48357I = true;
        this.f48359J = false;
        this.f48361K = true;
        this.f48363L = true;
        this.f48365M = false;
        this.f48367N = true;
        this.f48369O = false;
        this.f48371P = true;
        this.f48373Q = true;
        this.f48375R = true;
        this.f48377S = true;
        this.f48379T = false;
        this.f48381U = false;
        this.f48383V = false;
        this.f48385W = false;
        this.f48348D0 = false;
        this.f48356H0 = new int[2];
        NestedScrollingChildHelper nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        this.f48358I0 = nestedScrollingChildHelper;
        this.f48360J0 = new NestedScrollingParentHelper();
        DimensionStatus dimensionStatus = DimensionStatus.f48464c;
        this.f48364L0 = dimensionStatus;
        this.f48368N0 = dimensionStatus;
        this.f48374Q0 = 2.5f;
        this.f48376R0 = 2.5f;
        this.f48378S0 = 1.0f;
        this.f48380T0 = 1.0f;
        this.f48389Z0 = new RefreshKernelImpl();
        RefreshState refreshState = RefreshState.None;
        this.f48391a1 = refreshState;
        this.f48393b1 = refreshState;
        this.f48395c1 = 0L;
        this.f48397d1 = 0;
        this.f48399e1 = 0;
        this.f48407i1 = false;
        this.f48409j1 = false;
        this.f48411k1 = null;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.f48388Y0 = new Handler();
        this.f48428z = new Scroller(context);
        this.f48344A = VelocityTracker.obtain();
        this.f48402g = context.getResources().getDisplayMetrics().heightPixels;
        this.f48345B = new SmartUtil(0);
        this.f48390a = viewConfiguration.getScaledTouchSlop();
        this.f48425w = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f48426x = viewConfiguration.getScaledMaximumFlingVelocity();
        this.f48366M0 = SmartUtil.m21920c(60.0f);
        this.f48362K0 = SmartUtil.m21920c(100.0f);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmartRefreshLayout);
        if (!typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_android_clipToPadding)) {
            super.setClipToPadding(false);
        }
        if (!typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_android_clipChildren)) {
            super.setClipChildren(false);
        }
        DefaultRefreshInitializer defaultRefreshInitializer = f48342p1;
        if (defaultRefreshInitializer != null) {
            defaultRefreshInitializer.initialize();
        }
        this.f48416n = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlDragRate, 0.5f);
        this.f48374Q0 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlHeaderMaxDragRate, this.f48374Q0);
        this.f48376R0 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlFooterMaxDragRate, 2.5f);
        this.f48378S0 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlHeaderTriggerRate, 1.0f);
        this.f48380T0 = typedArrayObtainStyledAttributes.getFloat(R.styleable.SmartRefreshLayout_srlFooterTriggerRate, 1.0f);
        this.f48347D = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableRefresh, this.f48347D);
        this.f48400f = typedArrayObtainStyledAttributes.getInt(R.styleable.SmartRefreshLayout_srlReboundDuration, 300);
        this.f48349E = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableLoadMore, this.f48349E);
        this.f48362K0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlHeaderHeight, this.f48362K0);
        this.f48366M0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlFooterHeight, this.f48366M0);
        this.f48370O0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlHeaderInsetStart, this.f48370O0);
        this.f48372P0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.SmartRefreshLayout_srlFooterInsetStart, this.f48372P0);
        this.f48379T = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlDisableContentWhenRefresh, false);
        this.f48381U = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlDisableContentWhenLoading, false);
        this.f48355H = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent, true);
        this.f48357I = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterTranslationContent, true);
        this.f48361K = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnablePreviewInEditMode, true);
        this.f48367N = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableAutoLoadMore, this.f48367N);
        this.f48363L = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableOverScrollBounce, true);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnablePureScrollMode, false);
        this.f48369O = z;
        this.f48371P = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableScrollContentWhenLoaded, true);
        this.f48373Q = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableScrollContentWhenRefreshed, true);
        this.f48375R = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterFollowWhenLoadFinished, false);
        this.f48359J = z2;
        this.f48359J = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableFooterFollowWhenNoMoreData, z2);
        this.f48351F = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableClipHeaderWhenFixedBehind, true);
        this.f48353G = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableClipFooterWhenFixedBehind, true);
        this.f48365M = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableOverScrollDrag, false);
        this.f48421s = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlFixedHeaderViewId, -1);
        this.f48422t = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlFixedFooterViewId, -1);
        this.f48423u = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlHeaderTranslationViewId, -1);
        this.f48424v = typedArrayObtainStyledAttributes.getResourceId(R.styleable.SmartRefreshLayout_srlFooterTranslationViewId, -1);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SmartRefreshLayout_srlEnableNestedScrolling, this.f48377S);
        this.f48377S = z3;
        nestedScrollingChildHelper.m2458g(z3);
        this.f48348D0 = this.f48348D0 || typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_srlEnableLoadMore);
        typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_srlEnableHeaderTranslationContent);
        typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_srlEnableFooterTranslationContent);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_srlHeaderHeight);
        DimensionStatus dimensionStatus2 = DimensionStatus.f48467f;
        this.f48364L0 = zHasValue ? dimensionStatus2 : this.f48364L0;
        this.f48368N0 = typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_srlFooterHeight) ? dimensionStatus2 : this.f48368N0;
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.SmartRefreshLayout_srlAccentColor, 0);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.SmartRefreshLayout_srlPrimaryColor, 0);
        if (color2 != 0) {
            if (color != 0) {
                this.f48346C = new int[]{color2, color};
            } else {
                this.f48346C = new int[]{color2};
            }
        } else if (color != 0) {
            this.f48346C = new int[]{0, color};
        }
        if (z && !this.f48348D0 && !this.f48349E) {
            this.f48349E = true;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public static void setDefaultRefreshFooterCreator(DefaultRefreshFooterCreator defaultRefreshFooterCreator) {
        f48340n1 = defaultRefreshFooterCreator;
    }

    public static void setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator defaultRefreshHeaderCreator) {
        f48341o1 = defaultRefreshHeaderCreator;
    }

    public static void setDefaultRefreshInitializer(DefaultRefreshInitializer defaultRefreshInitializer) {
        f48342p1 = defaultRefreshInitializer;
    }

    /* JADX INFO: renamed from: A */
    public final void m21873A(boolean z) {
        RefreshState refreshState = this.f48391a1;
        if (refreshState == RefreshState.Refreshing && z) {
            m21883t(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.f48395c1))), 300) << 16, true, Boolean.TRUE);
            return;
        }
        if (refreshState == RefreshState.Loading && z) {
            m21882s(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.f48395c1))), 300) << 16, true);
            return;
        }
        if (this.f48383V != z) {
            this.f48383V = z;
            RefreshFooter refreshFooter = this.f48384V0;
            if (refreshFooter != null) {
                if (!refreshFooter.mo21899c(z)) {
                    this.f48385W = false;
                    new RuntimeException("Footer:" + this.f48384V0 + " NoMoreData is not supported.(不支持NoMoreData，请使用[ClassicsFooter]或者[自定义Footer并实现setNoMoreData方法且返回true])").printStackTrace();
                    return;
                }
                this.f48385W = true;
                if (this.f48383V && this.f48359J && this.f48392b > 0 && this.f48384V0.getSpinnerStyle() == SpinnerStyle.f48497d && m21885v(this.f48349E) && m21886w(this.f48347D, this.f48382U0)) {
                    this.f48384V0.getView().setTranslationY(this.f48392b);
                }
            }
        }
    }

    /* JADX INFO: renamed from: B */
    public final void m21874B(RefreshFooter refreshFooter) {
        RefreshFooter refreshFooter2;
        RefreshFooter refreshFooter3 = this.f48384V0;
        if (refreshFooter3 != null) {
            super.removeView(refreshFooter3.getView());
        }
        this.f48384V0 = refreshFooter;
        this.f48407i1 = false;
        this.f48399e1 = 0;
        this.f48385W = false;
        this.f48403g1 = false;
        this.f48368N0 = DimensionStatus.f48464c;
        this.f48349E = !this.f48348D0 || this.f48349E;
        LayoutParams layoutParams = new LayoutParams(-2);
        ViewGroup.LayoutParams layoutParams2 = this.f48384V0.getView().getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        boolean z = this.f48384V0.getSpinnerStyle().f48503b;
        RefreshFooter refreshFooter4 = this.f48384V0;
        if (z) {
            super.addView(refreshFooter4.getView(), getChildCount(), layoutParams);
        } else {
            super.addView(refreshFooter4.getView(), 0, layoutParams);
        }
        int[] iArr = this.f48346C;
        if (iArr == null || (refreshFooter2 = this.f48384V0) == null) {
            return;
        }
        refreshFooter2.setPrimaryColors(iArr);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0063  */
    /* JADX INFO: renamed from: C */
    public final boolean m21875C(float f) {
        RefreshState refreshState;
        if (f == 0.0f) {
            f = this.f48427y;
        }
        if (Build.VERSION.SDK_INT > 27 && this.f48386W0 != null) {
            getScaleY();
            View view = this.f48386W0.f48564a;
            if (getScaleY() == -1.0f && view.getScaleY() == -1.0f) {
                f = -f;
            }
        }
        int i = 0;
        if (Math.abs(f) > this.f48425w) {
            int i2 = this.f48392b;
            if (i2 * f < 0.0f) {
                RefreshState refreshState2 = this.f48391a1;
                RefreshState refreshState3 = RefreshState.Refreshing;
                if (refreshState2 == refreshState3 || refreshState2 == RefreshState.Loading || (i2 < 0 && this.f48383V)) {
                    FlingRunnable flingRunnable = new FlingRunnable(f);
                    RefreshState refreshState4 = this.f48391a1;
                    if (refreshState4.f48495f) {
                        flingRunnable = null;
                    } else {
                        if (this.f48392b != 0) {
                            boolean z = refreshState4.f48494e;
                            boolean z2 = this.f48359J;
                            if ((!z && (!this.f48383V || !z2 || !this.f48385W || !m21885v(this.f48349E))) || (((this.f48391a1 == RefreshState.Loading || (this.f48383V && z2 && this.f48385W && m21885v(this.f48349E))) && this.f48392b < (-this.f48366M0)) || (this.f48391a1 == refreshState3 && this.f48392b > this.f48362K0))) {
                                int i3 = this.f48392b;
                                float fPow = flingRunnable.f48456b;
                                int i4 = i3;
                                while (true) {
                                    if (i3 * i4 > 0) {
                                        i++;
                                        fPow = (float) (Math.pow(0.9800000190734863d, (i * 10) / 10.0f) * ((double) fPow));
                                        float f2 = 0.01f * fPow;
                                        if (Math.abs(f2) < 1.0f) {
                                            RefreshState refreshState5 = this.f48391a1;
                                            if (!refreshState5.f48494e || ((refreshState5 == (refreshState = RefreshState.Refreshing) && i4 > this.f48362K0) || (refreshState5 != refreshState && i4 < (-this.f48366M0)))) {
                                                flingRunnable = null;
                                            }
                                        } else {
                                            i4 = (int) (i4 + f2);
                                        }
                                    }
                                }
                            }
                        }
                        flingRunnable.f48457c = AnimationUtils.currentAnimationTimeMillis();
                        this.f48388Y0.postDelayed(flingRunnable, 10L);
                    }
                    this.f48413l1 = flingRunnable;
                    return true;
                }
                if (refreshState2.f48496g) {
                    return true;
                }
            }
            boolean z3 = this.f48365M;
            boolean z4 = this.f48363L;
            if ((f < 0.0f && ((z4 && (this.f48349E || z3)) || ((this.f48391a1 == RefreshState.Loading && i2 >= 0) || (this.f48367N && m21885v(this.f48349E))))) || (f > 0.0f && ((z4 && this.f48347D) || z3 || (this.f48391a1 == RefreshState.Refreshing && this.f48392b <= 0)))) {
                this.f48409j1 = false;
                Scroller scroller = this.f48428z;
                scroller.fling(0, 0, 0, (int) (-f), 0, 0, -2147483647, Integer.MAX_VALUE);
                scroller.computeScrollOffset();
                invalidate();
            }
        }
        return false;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout
    /* JADX INFO: renamed from: a */
    public final SmartRefreshLayout mo21876a(RefreshHeader refreshHeader) {
        RefreshHeader refreshHeader2;
        RefreshHeader refreshHeader3 = this.f48382U0;
        if (refreshHeader3 != null) {
            super.removeView(refreshHeader3.getView());
        }
        this.f48382U0 = refreshHeader;
        this.f48397d1 = 0;
        this.f48401f1 = false;
        this.f48364L0 = DimensionStatus.f48464c;
        LayoutParams layoutParams = new LayoutParams(-2);
        ViewGroup.LayoutParams layoutParams2 = this.f48382U0.getView().getLayoutParams();
        if (layoutParams2 instanceof LayoutParams) {
            layoutParams = (LayoutParams) layoutParams2;
        }
        boolean z = this.f48382U0.getSpinnerStyle().f48503b;
        RefreshHeader refreshHeader4 = this.f48382U0;
        if (z) {
            super.addView(refreshHeader4.getView(), getChildCount(), layoutParams);
        } else {
            super.addView(refreshHeader4.getView(), 0, layoutParams);
        }
        int[] iArr = this.f48346C;
        if (iArr != null && (refreshHeader2 = this.f48382U0) != null) {
            refreshHeader2.setPrimaryColors(iArr);
        }
        return this;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout
    /* JADX INFO: renamed from: b */
    public final SmartRefreshLayout mo21877b(int i) {
        m21883t(i, true, Boolean.FALSE);
        return this;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout
    /* JADX INFO: renamed from: c */
    public final SmartRefreshLayout mo21878c(boolean z) {
        this.f48347D = z;
        return this;
    }

    @Override // android.view.View
    public final void computeScroll() {
        RefreshState refreshState;
        Scroller scroller = this.f48428z;
        scroller.getCurrY();
        if (scroller.computeScrollOffset()) {
            int finalY = scroller.getFinalY();
            boolean z = this.f48365M;
            if ((finalY >= 0 || !((this.f48347D || z) && this.f48386W0.m21913b())) && (finalY <= 0 || !((this.f48349E || z) && this.f48386W0.m21912a()))) {
                this.f48409j1 = true;
                invalidate();
                return;
            }
            if (this.f48409j1) {
                float currVelocity = finalY > 0 ? -scroller.getCurrVelocity() : scroller.getCurrVelocity();
                if (this.f48415m1 == null) {
                    if (currVelocity > 0.0f && ((refreshState = this.f48391a1) == RefreshState.Refreshing || refreshState == RefreshState.TwoLevel)) {
                        this.f48413l1 = new BounceRunnable(currVelocity, this.f48362K0);
                    } else if (currVelocity < 0.0f && (this.f48391a1 == RefreshState.Loading || ((this.f48359J && this.f48383V && this.f48385W && m21885v(this.f48349E)) || (this.f48367N && !this.f48383V && m21885v(this.f48349E) && this.f48391a1 != RefreshState.Refreshing)))) {
                        this.f48413l1 = new BounceRunnable(currVelocity, -this.f48366M0);
                    } else if (this.f48392b == 0 && this.f48363L) {
                        this.f48413l1 = new BounceRunnable(currVelocity, 0);
                    }
                }
            }
            scroller.forceFinished(true);
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout
    /* JADX INFO: renamed from: d */
    public final SmartRefreshLayout mo21879d(OnRefreshListener onRefreshListener) {
        this.f48350E0 = onRefreshListener;
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:161:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:164:0x01df  */
    /* JADX WARN: Code duplicated, block: B:176:0x0203  */
    /* JADX WARN: Code duplicated, block: B:179:0x0234  */
    /* JADX WARN: Code duplicated, block: B:185:0x0241 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:191:0x025b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:192:0x025d A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:198:0x0277  */
    /* JADX WARN: Code duplicated, block: B:201:0x027e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:202:0x0280  */
    /* JADX WARN: Code duplicated, block: B:207:0x028a  */
    /* JADX WARN: Code duplicated, block: B:211:0x0294  */
    /* JADX WARN: Code duplicated, block: B:215:0x02a6  */
    /* JADX WARN: Code duplicated, block: B:222:0x02b6  */
    /* JADX WARN: Code duplicated, block: B:225:0x02d7  */
    /* JADX WARN: Code duplicated, block: B:228:0x02f6  */
    /* JADX WARN: Code duplicated, block: B:232:0x02fe  */
    /* JADX WARN: Code duplicated, block: B:234:0x0322  */
    /* JADX WARN: Code duplicated, block: B:236:0x032e  */
    /* JADX WARN: Code duplicated, block: B:238:0x0338  */
    /* JADX WARN: Code duplicated, block: B:248:0x0352  */
    /* JADX WARN: Code duplicated, block: B:250:0x0378  */
    /* JADX WARN: Code duplicated, block: B:253:0x0386  */
    /* JADX WARN: Code duplicated, block: B:255:0x038a  */
    /* JADX WARN: Code duplicated, block: B:69:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:71:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:73:0x00d5  */
    /* JADX WARN: Code duplicated, block: B:75:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:80:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:93:0x0103 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:94:0x0105  */
    /* JADX WARN: Code duplicated, block: B:96:0x0108 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:98:0x010c  */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00db, code lost:
    
        if (r2.f48491b != false) goto L258;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchTouchEvent(MotionEvent r26) {
        /*
            Method dump skipped, instruction units count: 915
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        Paint paint;
        Paint paint2;
        RefreshContentWrapper refreshContentWrapper = this.f48386W0;
        View view2 = refreshContentWrapper != null ? refreshContentWrapper.f48564a : null;
        RefreshHeader refreshHeader = this.f48382U0;
        SpinnerStyle spinnerStyle = SpinnerStyle.f48498e;
        SpinnerStyle spinnerStyle2 = SpinnerStyle.f48497d;
        boolean z = this.f48361K;
        if (refreshHeader != null && refreshHeader.getView() == view) {
            if (!m21885v(this.f48347D)) {
                return true;
            }
            if (!z && isInEditMode()) {
                return true;
            }
            if (view2 != null) {
                int iMax = Math.max(view2.getPaddingTop() + view2.getTop() + this.f48392b, view.getTop());
                int i = this.f48397d1;
                if (i != 0 && (paint2 = this.f48387X0) != null) {
                    paint2.setColor(i);
                    if (this.f48382U0.getSpinnerStyle().f48504c) {
                        iMax = view.getBottom();
                    } else if (this.f48382U0.getSpinnerStyle() == spinnerStyle2) {
                        iMax = view.getBottom() + this.f48392b;
                    }
                    int i2 = iMax;
                    canvas.drawRect(0.0f, view.getTop(), getWidth(), i2, this.f48387X0);
                    iMax = i2;
                }
                if ((this.f48351F && this.f48382U0.getSpinnerStyle() == spinnerStyle) || this.f48382U0.getSpinnerStyle().f48504c) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), view.getTop(), view.getRight(), iMax);
                    boolean zDrawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return zDrawChild;
                }
            }
        }
        RefreshFooter refreshFooter = this.f48384V0;
        if (refreshFooter != null && refreshFooter.getView() == view) {
            if (!m21885v(this.f48349E)) {
                return true;
            }
            if (!z && isInEditMode()) {
                return true;
            }
            if (view2 != null) {
                int iMin = Math.min((view2.getBottom() - view2.getPaddingBottom()) + this.f48392b, view.getBottom());
                int i3 = this.f48399e1;
                if (i3 != 0 && (paint = this.f48387X0) != null) {
                    paint.setColor(i3);
                    if (this.f48384V0.getSpinnerStyle().f48504c) {
                        iMin = view.getTop();
                    } else if (this.f48384V0.getSpinnerStyle() == spinnerStyle2) {
                        iMin = view.getTop() + this.f48392b;
                    }
                    int i4 = iMin;
                    canvas.drawRect(0.0f, i4, getWidth(), view.getBottom(), this.f48387X0);
                    iMin = i4;
                }
                if ((this.f48353G && this.f48384V0.getSpinnerStyle() == spinnerStyle) || this.f48384V0.getSpinnerStyle().f48504c) {
                    canvas.save();
                    canvas.clipRect(view.getLeft(), iMin, view.getRight(), view.getBottom());
                    boolean zDrawChild2 = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return zDrawChild2;
                }
            }
        }
        return super.drawChild(canvas, view, j);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        LayoutParams layoutParams = new LayoutParams(context, attributeSet);
        layoutParams.f48460a = 0;
        layoutParams.f48461b = null;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SmartRefreshLayout_Layout);
        layoutParams.f48460a = typedArrayObtainStyledAttributes.getColor(R.styleable.SmartRefreshLayout_Layout_layout_srlBackgroundColor, 0);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle)) {
            layoutParams.f48461b = SpinnerStyle.f48501h[typedArrayObtainStyledAttributes.getInt(R.styleable.SmartRefreshLayout_Layout_layout_srlSpinnerStyle, 0)];
        }
        typedArrayObtainStyledAttributes.recycle();
        return layoutParams;
    }

    @Override // android.view.ViewGroup
    public int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.f48360J0;
        return nestedScrollingParentHelper.f5360b | nestedScrollingParentHelper.f5359a;
    }

    public RefreshFooter getRefreshFooter() {
        RefreshFooter refreshFooter = this.f48384V0;
        if (refreshFooter != null) {
            return refreshFooter;
        }
        return null;
    }

    public RefreshHeader getRefreshHeader() {
        RefreshHeader refreshHeader = this.f48382U0;
        if (refreshHeader != null) {
            return refreshHeader;
        }
        return null;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout
    public RefreshState getState() {
        return this.f48391a1;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        if (this.f48377S) {
            return this.f48365M || this.f48347D || this.f48349E;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        RefreshKernelImpl refreshKernelImpl;
        RefreshFooter refreshFooter;
        super.onAttachedToWindow();
        boolean z = true;
        this.f48405h1 = true;
        if (!isInEditMode()) {
            View view = null;
            if (this.f48382U0 == null) {
                DefaultRefreshHeaderCreator defaultRefreshHeaderCreator = f48341o1;
                if (defaultRefreshHeaderCreator != null) {
                    getContext();
                    RefreshHeader refreshHeaderM21898a = defaultRefreshHeaderCreator.m21898a();
                    if (refreshHeaderM21898a == null) {
                        C0013a7.m25t("DefaultRefreshHeaderCreator can not return null");
                        return;
                    }
                    mo21876a(refreshHeaderM21898a);
                } else {
                    Context context = getContext();
                    BezierRadarHeader bezierRadarHeader = new BezierRadarHeader(context, null, 0);
                    bezierRadarHeader.f48522i = false;
                    bezierRadarHeader.f48527n = -1;
                    bezierRadarHeader.f48528o = 0;
                    bezierRadarHeader.f48533t = 0;
                    bezierRadarHeader.f48534u = 0.0f;
                    bezierRadarHeader.f48535v = 0.0f;
                    bezierRadarHeader.f48536w = 0.0f;
                    bezierRadarHeader.f48538y = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
                    bezierRadarHeader.f48579b = SpinnerStyle.f48498e;
                    bezierRadarHeader.f48523j = new Path();
                    Paint paint = new Paint();
                    bezierRadarHeader.f48524k = paint;
                    paint.setAntiAlias(true);
                    bezierRadarHeader.f48531r = SmartUtil.m21920c(7.0f);
                    bezierRadarHeader.f48534u = SmartUtil.m21920c(20.0f);
                    bezierRadarHeader.f48535v = SmartUtil.m21920c(7.0f);
                    paint.setStrokeWidth(SmartUtil.m21920c(3.0f));
                    bezierRadarHeader.setMinimumHeight(SmartUtil.m21920c(100.0f));
                    if (bezierRadarHeader.isInEditMode()) {
                        bezierRadarHeader.f48525l = AdError.NETWORK_ERROR_CODE;
                        bezierRadarHeader.f48536w = 1.0f;
                        bezierRadarHeader.f48533t = 270;
                    } else {
                        bezierRadarHeader.f48536w = 0.0f;
                    }
                    TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, R.styleable.BezierRadarHeader);
                    bezierRadarHeader.f48522i = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BezierRadarHeader_srlEnableHorizontalDrag, false);
                    bezierRadarHeader.f48517d = typedArrayObtainStyledAttributes.getColor(R.styleable.BezierRadarHeader_srlAccentColor, -1);
                    bezierRadarHeader.f48520g = true;
                    bezierRadarHeader.f48518e = typedArrayObtainStyledAttributes.getColor(R.styleable.BezierRadarHeader_srlPrimaryColor, -14540254);
                    bezierRadarHeader.f48519f = true;
                    bezierRadarHeader.f48520g = typedArrayObtainStyledAttributes.hasValue(R.styleable.BezierRadarHeader_srlAccentColor);
                    bezierRadarHeader.f48519f = typedArrayObtainStyledAttributes.hasValue(R.styleable.BezierRadarHeader_srlPrimaryColor);
                    typedArrayObtainStyledAttributes.recycle();
                    mo21876a(bezierRadarHeader);
                }
            }
            if (this.f48384V0 == null) {
                DefaultRefreshFooterCreator defaultRefreshFooterCreator = f48340n1;
                if (defaultRefreshFooterCreator != null) {
                    getContext();
                    RefreshFooter refreshFooterM21897a = defaultRefreshFooterCreator.m21897a();
                    if (refreshFooterM21897a == null) {
                        C0013a7.m25t("DefaultRefreshFooterCreator can not return null");
                        return;
                    }
                    m21874B(refreshFooterM21897a);
                } else {
                    boolean z2 = this.f48349E;
                    Context context2 = getContext();
                    BallPulseFooter ballPulseFooter = new BallPulseFooter(context2, null, 0);
                    ballPulseFooter.f48508g = -1118482;
                    ballPulseFooter.f48509h = -1615546;
                    ballPulseFooter.f48511j = 0L;
                    ballPulseFooter.f48512k = false;
                    ballPulseFooter.f48513l = new AccelerateDecelerateInterpolator();
                    ballPulseFooter.setMinimumHeight(SmartUtil.m21920c(60.0f));
                    TypedArray typedArrayObtainStyledAttributes2 = context2.obtainStyledAttributes((AttributeSet) null, R.styleable.BallPulseFooter);
                    Paint paint2 = new Paint();
                    ballPulseFooter.f48507f = paint2;
                    paint2.setColor(-1);
                    paint2.setStyle(Paint.Style.FILL);
                    paint2.setAntiAlias(true);
                    ballPulseFooter.f48579b = SpinnerStyle.f48497d;
                    ballPulseFooter.f48579b = SpinnerStyle.f48501h[typedArrayObtainStyledAttributes2.getInt(R.styleable.BallPulseFooter_srlClassicsSpinnerStyle, 0)];
                    if (typedArrayObtainStyledAttributes2.hasValue(R.styleable.BallPulseFooter_srlNormalColor)) {
                        int color = typedArrayObtainStyledAttributes2.getColor(R.styleable.BallPulseFooter_srlNormalColor, 0);
                        ballPulseFooter.f48508g = color;
                        ballPulseFooter.f48505d = true;
                        if (!ballPulseFooter.f48512k) {
                            paint2.setColor(color);
                        }
                    }
                    if (typedArrayObtainStyledAttributes2.hasValue(R.styleable.BallPulseFooter_srlAnimatingColor)) {
                        int color2 = typedArrayObtainStyledAttributes2.getColor(R.styleable.BallPulseFooter_srlAnimatingColor, 0);
                        ballPulseFooter.f48509h = color2;
                        ballPulseFooter.f48506e = true;
                        if (ballPulseFooter.f48512k) {
                            paint2.setColor(color2);
                        }
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                    ballPulseFooter.f48510i = SmartUtil.m21920c(4.0f);
                    m21874B(ballPulseFooter);
                    this.f48349E = z2;
                }
            } else {
                this.f48349E = this.f48349E || !this.f48348D0;
            }
            if (this.f48386W0 == null) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    RefreshHeader refreshHeader = this.f48382U0;
                    if ((refreshHeader == null || childAt != refreshHeader.getView()) && ((refreshFooter = this.f48384V0) == null || childAt != refreshFooter.getView())) {
                        this.f48386W0 = new RefreshContentWrapper(childAt);
                    }
                }
            }
            if (this.f48386W0 == null) {
                int iM21920c = SmartUtil.m21920c(20.0f);
                TextView textView = new TextView(getContext());
                textView.setTextColor(-39424);
                textView.setGravity(17);
                textView.setTextSize(20.0f);
                textView.setText(R.string.srl_content_empty);
                super.addView(textView, 0, new LayoutParams(-1));
                this.f48386W0 = new RefreshContentWrapper(textView);
                textView.setPadding(iM21920c, iM21920c, iM21920c, iM21920c);
            }
            View viewFindViewById = findViewById(this.f48421s);
            View viewFindViewById2 = findViewById(this.f48422t);
            this.f48386W0.f48572i.getClass();
            RefreshContentWrapper refreshContentWrapper = this.f48386W0;
            refreshContentWrapper.f48572i.f48574b = this.f48375R;
            View view2 = refreshContentWrapper.f48564a;
            boolean zIsInEditMode = view2.isInEditMode();
            View view3 = null;
            while (true) {
                refreshKernelImpl = this.f48389Z0;
                if (view3 != null && (!(view3 instanceof NestedScrollingParent) || (view3 instanceof NestedScrollingChild))) {
                    break;
                }
                boolean z3 = view3 == null ? z : false;
                LinkedList linkedList = new LinkedList();
                linkedList.add(view2);
                View view4 = view;
                while (linkedList.size() > 0 && view4 == null) {
                    View view5 = (View) linkedList.poll();
                    if (view5 != null) {
                        if ((z3 || view5 != view2) && SmartUtil.m21921d(view5)) {
                            view4 = view5;
                        } else if (view5 instanceof ViewGroup) {
                            ViewGroup viewGroup = (ViewGroup) view5;
                            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                                linkedList.add(viewGroup.getChildAt(i2));
                            }
                        }
                    }
                }
                if (view4 != null) {
                    view2 = view4;
                }
                if (view2 == view3) {
                    break;
                }
                if (!zIsInEditMode) {
                    DesignUtil.m21917a(view2, refreshKernelImpl, refreshContentWrapper);
                }
                z = true;
                view3 = view2;
                view = null;
            }
            if (view3 != null) {
                refreshContentWrapper.f48566c = view3;
            }
            if (viewFindViewById != null || viewFindViewById2 != null) {
                refreshContentWrapper.f48567d = viewFindViewById;
                refreshContentWrapper.f48568e = viewFindViewById2;
                FrameLayout frameLayout = new FrameLayout(refreshContentWrapper.f48564a.getContext());
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                int iIndexOfChild = smartRefreshLayout.getLayout().indexOfChild(refreshContentWrapper.f48564a);
                smartRefreshLayout.getLayout().removeView(refreshContentWrapper.f48564a);
                frameLayout.addView(refreshContentWrapper.f48564a, 0, new ViewGroup.LayoutParams(-1, -1));
                smartRefreshLayout.getLayout().addView(frameLayout, iIndexOfChild, refreshContentWrapper.f48564a.getLayoutParams());
                refreshContentWrapper.f48564a = frameLayout;
                if (viewFindViewById != null) {
                    viewFindViewById.setTag("fixed-top");
                    ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
                    ViewGroup viewGroup2 = (ViewGroup) viewFindViewById.getParent();
                    int iIndexOfChild2 = viewGroup2.indexOfChild(viewFindViewById);
                    viewGroup2.removeView(viewFindViewById);
                    layoutParams.height = SmartUtil.m21923f(viewFindViewById);
                    viewGroup2.addView(new Space(refreshContentWrapper.f48564a.getContext()), iIndexOfChild2, layoutParams);
                    frameLayout.addView(viewFindViewById, 1, layoutParams);
                }
                if (viewFindViewById2 != null) {
                    viewFindViewById2.setTag("fixed-bottom");
                    ViewGroup.LayoutParams layoutParams2 = viewFindViewById2.getLayoutParams();
                    ViewGroup viewGroup3 = (ViewGroup) viewFindViewById2.getParent();
                    int iIndexOfChild3 = viewGroup3.indexOfChild(viewFindViewById2);
                    viewGroup3.removeView(viewFindViewById2);
                    FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(layoutParams2);
                    layoutParams2.height = SmartUtil.m21923f(viewFindViewById2);
                    viewGroup3.addView(new Space(refreshContentWrapper.f48564a.getContext()), iIndexOfChild3, layoutParams2);
                    layoutParams3.gravity = 80;
                    frameLayout.addView(viewFindViewById2, 1, layoutParams3);
                }
            }
            if (this.f48392b != 0) {
                m21888y(RefreshState.None);
                RefreshContentWrapper refreshContentWrapper2 = this.f48386W0;
                this.f48392b = 0;
                refreshContentWrapper2.m21914d(0, this.f48423u, this.f48424v);
            }
        }
        int[] iArr = this.f48346C;
        if (iArr != null) {
            RefreshHeader refreshHeader2 = this.f48382U0;
            if (refreshHeader2 != null) {
                refreshHeader2.setPrimaryColors(iArr);
            }
            RefreshFooter refreshFooter2 = this.f48384V0;
            if (refreshFooter2 != null) {
                refreshFooter2.setPrimaryColors(iArr);
            }
        }
        RefreshContentWrapper refreshContentWrapper3 = this.f48386W0;
        if (refreshContentWrapper3 != null) {
            super.bringChildToFront(refreshContentWrapper3.f48564a);
        }
        RefreshHeader refreshHeader3 = this.f48382U0;
        if (refreshHeader3 != null && refreshHeader3.getSpinnerStyle().f48503b) {
            super.bringChildToFront(this.f48382U0.getView());
        }
        RefreshFooter refreshFooter3 = this.f48384V0;
        if (refreshFooter3 == null || !refreshFooter3.getSpinnerStyle().f48503b) {
            return;
        }
        super.bringChildToFront(this.f48384V0.getView());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f48405h1 = false;
        this.f48348D0 = true;
        this.f48413l1 = null;
        ValueAnimator valueAnimator = this.f48415m1;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.f48415m1.removeAllUpdateListeners();
            this.f48415m1.setDuration(0L);
            this.f48415m1.cancel();
            this.f48415m1 = null;
        }
        RefreshHeader refreshHeader = this.f48382U0;
        if (refreshHeader != null && this.f48391a1 == RefreshState.Refreshing) {
            refreshHeader.mo21904h(this, false);
        }
        RefreshFooter refreshFooter = this.f48384V0;
        if (refreshFooter != null && this.f48391a1 == RefreshState.Loading) {
            refreshFooter.mo21904h(this, false);
        }
        if (this.f48392b != 0) {
            this.f48389Z0.m21896g(0, true);
        }
        RefreshState refreshState = this.f48391a1;
        RefreshState refreshState2 = RefreshState.None;
        if (refreshState != refreshState2) {
            m21888y(refreshState2);
        }
        Handler handler = this.f48388Y0;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.f48407i1 = false;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x004d  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public final void onFinishInflate() {
        int i;
        int i2;
        super.onFinishInflate();
        int childCount = super.getChildCount();
        if (childCount > 3) {
            C0013a7.m25t("最多只支持3个子View，Most only support three sub view");
            return;
        }
        int i3 = -1;
        int i4 = 0;
        char c = 0;
        while (true) {
            if (i4 >= childCount) {
                break;
            }
            View childAt = super.getChildAt(i4);
            if (SmartUtil.m21921d(childAt) && (c < 2 || i4 == 1)) {
                i3 = i4;
                c = 2;
            } else if (!(childAt instanceof RefreshInternal) && c < 1) {
                c = i4 > 0 ? (char) 1 : (char) 0;
                i3 = i4;
            }
            i4++;
        }
        if (i3 >= 0) {
            this.f48386W0 = new RefreshContentWrapper(super.getChildAt(i3));
            if (i3 == 1) {
                i2 = childCount != 3 ? -1 : 2;
                i = 0;
            } else if (childCount == 2) {
                i = -1;
                i2 = 1;
            } else {
                i = -1;
                i2 = -1;
            }
        } else {
            i = -1;
            i2 = -1;
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt2 = super.getChildAt(i5);
            if (i5 == i || (i5 != i2 && i == -1 && this.f48382U0 == null && (childAt2 instanceof RefreshHeader))) {
                this.f48382U0 = childAt2 instanceof RefreshHeader ? (RefreshHeader) childAt2 : new RefreshHeaderWrapper(childAt2);
            } else if (i5 == i2 || (i2 == -1 && (childAt2 instanceof RefreshFooter))) {
                this.f48349E = this.f48349E || !this.f48348D0;
                this.f48384V0 = childAt2 instanceof RefreshFooter ? (RefreshFooter) childAt2 : new RefreshFooterWrapper(childAt2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iMax;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        int childCount = super.getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = super.getChildAt(i5);
            if (childAt.getVisibility() != 8 && childAt.getTag(R.string.srl_component_falsify) != childAt) {
                RefreshContentWrapper refreshContentWrapper = this.f48386W0;
                MarginLayoutParams marginLayoutParams = f48343q1;
                boolean z2 = this.f48361K;
                if (refreshContentWrapper != null && refreshContentWrapper.f48564a == childAt) {
                    boolean z3 = isInEditMode() && z2 && m21885v(this.f48347D) && this.f48382U0 != null;
                    View view = this.f48386W0.f48564a;
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    MarginLayoutParams marginLayoutParams2 = layoutParams instanceof MarginLayoutParams ? (MarginLayoutParams) layoutParams : marginLayoutParams;
                    int i6 = marginLayoutParams2.leftMargin + paddingLeft;
                    int i7 = marginLayoutParams2.topMargin + paddingTop;
                    int measuredWidth = view.getMeasuredWidth() + i6;
                    int measuredHeight = view.getMeasuredHeight() + i7;
                    if (z3 && m21886w(this.f48355H, this.f48382U0)) {
                        int i8 = this.f48362K0;
                        i7 += i8;
                        measuredHeight += i8;
                    }
                    view.layout(i6, i7, measuredWidth, measuredHeight);
                }
                RefreshHeader refreshHeader = this.f48382U0;
                SpinnerStyle spinnerStyle = SpinnerStyle.f48497d;
                if (refreshHeader != null && refreshHeader.getView() == childAt) {
                    boolean z4 = isInEditMode() && z2 && m21885v(this.f48347D);
                    View view2 = this.f48382U0.getView();
                    ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
                    MarginLayoutParams marginLayoutParams3 = layoutParams2 instanceof MarginLayoutParams ? (MarginLayoutParams) layoutParams2 : marginLayoutParams;
                    int i9 = marginLayoutParams3.leftMargin;
                    int i10 = marginLayoutParams3.topMargin + this.f48370O0;
                    int measuredWidth2 = view2.getMeasuredWidth() + i9;
                    int measuredHeight2 = view2.getMeasuredHeight() + i10;
                    if (!z4 && this.f48382U0.getSpinnerStyle() == spinnerStyle) {
                        int i11 = this.f48362K0;
                        i10 -= i11;
                        measuredHeight2 -= i11;
                    }
                    view2.layout(i9, i10, measuredWidth2, measuredHeight2);
                }
                RefreshFooter refreshFooter = this.f48384V0;
                if (refreshFooter != null && refreshFooter.getView() == childAt) {
                    boolean z5 = isInEditMode() && z2 && m21885v(this.f48349E);
                    View view3 = this.f48384V0.getView();
                    ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
                    if (layoutParams3 instanceof MarginLayoutParams) {
                        marginLayoutParams = (MarginLayoutParams) layoutParams3;
                    }
                    SpinnerStyle spinnerStyle2 = this.f48384V0.getSpinnerStyle();
                    int i12 = marginLayoutParams.leftMargin;
                    int measuredHeight3 = getMeasuredHeight() + marginLayoutParams.topMargin;
                    int i13 = this.f48372P0;
                    int measuredHeight4 = measuredHeight3 - i13;
                    if (this.f48383V && this.f48385W && this.f48359J && this.f48386W0 != null && this.f48384V0.getSpinnerStyle() == spinnerStyle && m21885v(this.f48349E)) {
                        View view4 = this.f48386W0.f48564a;
                        ViewGroup.LayoutParams layoutParams4 = view4.getLayoutParams();
                        measuredHeight4 = view4.getMeasuredHeight() + paddingTop + paddingTop + (layoutParams4 instanceof MarginLayoutParams ? ((MarginLayoutParams) layoutParams4).topMargin : 0);
                    }
                    if (spinnerStyle2 == SpinnerStyle.f48500g) {
                        measuredHeight4 = marginLayoutParams.topMargin - i13;
                    } else {
                        if (z5 || spinnerStyle2 == SpinnerStyle.f48499f || spinnerStyle2 == SpinnerStyle.f48498e) {
                            iMax = this.f48366M0;
                        } else if (spinnerStyle2.f48504c && this.f48392b < 0) {
                            iMax = Math.max(m21885v(this.f48349E) ? -this.f48392b : 0, 0);
                        }
                        measuredHeight4 -= iMax;
                    }
                    view3.layout(i12, measuredHeight4, view3.getMeasuredWidth() + i12, view3.getMeasuredHeight() + measuredHeight4);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:116:0x022f  */
    /* JADX WARN: Code duplicated, block: B:119:0x0248 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:120:0x024a  */
    /* JADX WARN: Code duplicated, block: B:130:0x0279  */
    /* JADX WARN: Code duplicated, block: B:168:0x0303 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x00ea  */
    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        DimensionStatus[] dimensionStatusArr;
        int i5;
        int i6;
        RefreshContentWrapper refreshContentWrapper;
        View view;
        int iMax;
        DimensionStatus dimensionStatus;
        boolean z2;
        int iMax2;
        int i7;
        int i8;
        boolean z3 = isInEditMode() && this.f48361K;
        int childCount = super.getChildCount();
        int i9 = 0;
        int measuredHeight = 0;
        while (i9 < childCount) {
            View childAt = super.getChildAt(i9);
            if (childAt.getVisibility() == 8 || childAt.getTag(R.string.srl_component_falsify) == childAt) {
                z = z3;
                i3 = childCount;
                i4 = i9;
            } else {
                RefreshHeader refreshHeader = this.f48382U0;
                DimensionStatus[] dimensionStatusArr2 = DimensionStatus.f48469h;
                RefreshKernelImpl refreshKernelImpl = this.f48389Z0;
                DimensionStatus dimensionStatus2 = DimensionStatus.f48465d;
                DimensionStatus dimensionStatus3 = DimensionStatus.f48466e;
                MarginLayoutParams marginLayoutParams = f48343q1;
                SpinnerStyle spinnerStyle = SpinnerStyle.f48500g;
                if (refreshHeader == null || refreshHeader.getView() != childAt) {
                    z = z3;
                    i3 = childCount;
                    i4 = i9;
                    dimensionStatusArr = dimensionStatusArr2;
                } else {
                    View view2 = this.f48382U0.getView();
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    MarginLayoutParams marginLayoutParams2 = layoutParams instanceof MarginLayoutParams ? (MarginLayoutParams) layoutParams : marginLayoutParams;
                    z = z3;
                    i3 = childCount;
                    int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin, layoutParams.width);
                    int i10 = this.f48362K0;
                    DimensionStatus dimensionStatus4 = this.f48364L0;
                    i4 = i9;
                    dimensionStatusArr = dimensionStatusArr2;
                    if (dimensionStatus4.f48470a >= 6) {
                        iMax2 = i10;
                    } else {
                        int i11 = layoutParams.height;
                        if (i11 > 0) {
                            iMax2 = i11 + marginLayoutParams2.bottomMargin + marginLayoutParams2.topMargin;
                            if (dimensionStatus4.m21907a(dimensionStatus3)) {
                                this.f48362K0 = layoutParams.height + marginLayoutParams2.bottomMargin + marginLayoutParams2.topMargin;
                                this.f48364L0 = dimensionStatus3;
                            }
                        } else if (i11 != -2 || (this.f48382U0.getSpinnerStyle() == spinnerStyle && this.f48364L0.f48471b)) {
                            iMax2 = i10;
                        } else {
                            int iMax3 = Math.max((MeasureSpec.getSize(i2) - marginLayoutParams2.bottomMargin) - marginLayoutParams2.topMargin, 0);
                            view2.measure(childMeasureSpec, MeasureSpec.makeMeasureSpec(iMax3, LinearLayoutManager.INVALID_OFFSET));
                            int measuredHeight2 = view2.getMeasuredHeight();
                            if (measuredHeight2 > 0) {
                                if (measuredHeight2 != iMax3 && this.f48364L0.m21907a(dimensionStatus2)) {
                                    this.f48362K0 = measuredHeight2 + marginLayoutParams2.bottomMargin + marginLayoutParams2.topMargin;
                                    this.f48364L0 = dimensionStatus2;
                                }
                                iMax2 = -1;
                            } else {
                                iMax2 = i10;
                            }
                        }
                    }
                    if (this.f48382U0.getSpinnerStyle() == spinnerStyle) {
                        iMax2 = MeasureSpec.getSize(i2);
                        i8 = -1;
                        i7 = 0;
                    } else {
                        if (!this.f48382U0.getSpinnerStyle().f48504c || z) {
                            i7 = 0;
                        } else {
                            i7 = 0;
                            iMax2 = Math.max(0, m21885v(this.f48347D) ? this.f48392b : 0);
                        }
                        i8 = -1;
                    }
                    if (iMax2 != i8) {
                        view2.measure(childMeasureSpec, MeasureSpec.makeMeasureSpec(Math.max((iMax2 - marginLayoutParams2.bottomMargin) - marginLayoutParams2.topMargin, i7), 1073741824));
                    }
                    DimensionStatus dimensionStatus5 = this.f48364L0;
                    boolean z4 = dimensionStatus5.f48471b;
                    if (!z4) {
                        if (!z4) {
                            dimensionStatus5 = dimensionStatusArr[dimensionStatus5.f48470a + 1];
                        }
                        this.f48364L0 = dimensionStatus5;
                        RefreshHeader refreshHeader2 = this.f48382U0;
                        int i12 = this.f48362K0;
                        refreshHeader2.mo21902e(refreshKernelImpl, i12, (int) (this.f48374Q0 * i12));
                    }
                    if (z && m21885v(this.f48347D)) {
                        measuredHeight += view2.getMeasuredHeight();
                    }
                }
                RefreshFooter refreshFooter = this.f48384V0;
                if (refreshFooter == null || refreshFooter.getView() != childAt) {
                    i5 = measuredHeight;
                    i6 = 0;
                } else {
                    View view3 = this.f48384V0.getView();
                    ViewGroup.LayoutParams layoutParams2 = view3.getLayoutParams();
                    MarginLayoutParams marginLayoutParams3 = layoutParams2 instanceof MarginLayoutParams ? (MarginLayoutParams) layoutParams2 : marginLayoutParams;
                    int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i, marginLayoutParams3.leftMargin + marginLayoutParams3.rightMargin, layoutParams2.width);
                    int i13 = this.f48366M0;
                    DimensionStatus dimensionStatus6 = this.f48368N0;
                    i5 = measuredHeight;
                    if (dimensionStatus6.f48470a >= 6) {
                        iMax = i13;
                    } else {
                        int i14 = layoutParams2.height;
                        if (i14 > 0) {
                            int i15 = i14 + marginLayoutParams3.topMargin + marginLayoutParams3.bottomMargin;
                            if (dimensionStatus6.m21907a(dimensionStatus3)) {
                                this.f48366M0 = layoutParams2.height + marginLayoutParams3.topMargin + marginLayoutParams3.bottomMargin;
                                this.f48368N0 = dimensionStatus3;
                            }
                            iMax = i15;
                        } else if (i14 != -2 || (this.f48384V0.getSpinnerStyle() == spinnerStyle && this.f48368N0.f48471b)) {
                            iMax = i13;
                        } else {
                            int iMax4 = Math.max((MeasureSpec.getSize(i2) - marginLayoutParams3.bottomMargin) - marginLayoutParams3.topMargin, 0);
                            view3.measure(childMeasureSpec2, MeasureSpec.makeMeasureSpec(iMax4, LinearLayoutManager.INVALID_OFFSET));
                            int measuredHeight3 = view3.getMeasuredHeight();
                            if (measuredHeight3 > 0) {
                                if (measuredHeight3 != iMax4 && this.f48368N0.m21907a(dimensionStatus2)) {
                                    this.f48366M0 = measuredHeight3 + marginLayoutParams3.topMargin + marginLayoutParams3.bottomMargin;
                                    this.f48368N0 = dimensionStatus2;
                                }
                                iMax = -1;
                            } else {
                                iMax = i13;
                            }
                        }
                    }
                    if (this.f48384V0.getSpinnerStyle() == spinnerStyle) {
                        iMax = MeasureSpec.getSize(i2);
                    } else {
                        if (this.f48384V0.getSpinnerStyle().f48504c && !z) {
                            i6 = 0;
                            iMax = Math.max(0, m21885v(this.f48349E) ? -this.f48392b : 0);
                        }
                        if (iMax != -1) {
                            view3.measure(childMeasureSpec2, MeasureSpec.makeMeasureSpec(Math.max((iMax - marginLayoutParams3.bottomMargin) - marginLayoutParams3.topMargin, i6), 1073741824));
                        }
                        dimensionStatus = this.f48368N0;
                        z2 = dimensionStatus.f48471b;
                        if (!z2) {
                            if (!z2) {
                                dimensionStatus = dimensionStatusArr[dimensionStatus.f48470a + 1];
                            }
                            this.f48368N0 = dimensionStatus;
                            RefreshFooter refreshFooter2 = this.f48384V0;
                            int i16 = this.f48366M0;
                            refreshFooter2.mo21902e(refreshKernelImpl, i16, (int) (this.f48376R0 * i16));
                        }
                        if (!z && m21885v(this.f48349E)) {
                            measuredHeight = view3.getMeasuredHeight() + i5;
                        }
                        refreshContentWrapper = this.f48386W0;
                        if (refreshContentWrapper == null && (view = refreshContentWrapper.f48564a) == childAt) {
                            ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
                            if (layoutParams3 instanceof MarginLayoutParams) {
                                marginLayoutParams = (MarginLayoutParams) layoutParams3;
                            }
                            MarginLayoutParams marginLayoutParams4 = marginLayoutParams;
                            view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams4.leftMargin + marginLayoutParams4.rightMargin, layoutParams3.width), ViewGroup.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop() + marginLayoutParams4.topMargin + marginLayoutParams4.bottomMargin + ((!z || ((this.f48382U0 == null || !m21885v(this.f48347D) || !m21886w(this.f48355H, this.f48382U0)) ? i6 : 1) == 0) ? i6 : this.f48362K0) + ((!z || ((this.f48384V0 == null || !m21885v(this.f48349E) || !m21886w(this.f48357I, this.f48384V0)) ? i6 : 1) == 0) ? i6 : this.f48366M0), layoutParams3.height));
                            measuredHeight += view.getMeasuredHeight();
                        }
                    }
                    i6 = 0;
                    if (iMax != -1) {
                        view3.measure(childMeasureSpec2, MeasureSpec.makeMeasureSpec(Math.max((iMax - marginLayoutParams3.bottomMargin) - marginLayoutParams3.topMargin, i6), 1073741824));
                    }
                    dimensionStatus = this.f48368N0;
                    z2 = dimensionStatus.f48471b;
                    if (!z2) {
                        if (!z2) {
                            dimensionStatus = dimensionStatusArr[dimensionStatus.f48470a + 1];
                        }
                        this.f48368N0 = dimensionStatus;
                        RefreshFooter refreshFooter3 = this.f48384V0;
                        int i17 = this.f48366M0;
                        refreshFooter3.mo21902e(refreshKernelImpl, i17, (int) (this.f48376R0 * i17));
                    }
                    if (!z) {
                    }
                    refreshContentWrapper = this.f48386W0;
                    if (refreshContentWrapper == null) {
                    }
                }
                measuredHeight = i5;
                refreshContentWrapper = this.f48386W0;
                if (refreshContentWrapper == null) {
                }
            }
            i9 = i4 + 1;
            z3 = z;
            childCount = i3;
        }
        super.setMeasuredDimension(View.resolveSize(super.getSuggestedMinimumWidth(), i), View.resolveSize(measuredHeight, i2));
        this.f48408j = getMeasuredWidth() / 2.0f;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        return this.f48358I0.m2452a(f, f2, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        return (this.f48407i1 && f2 > 0.0f) || m21875C(-f2) || this.f48358I0.m2453b(f, f2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        int i3 = this.f48352F0;
        int i4 = 0;
        if (i2 * i3 > 0) {
            int iAbs = Math.abs(i2);
            int iAbs2 = Math.abs(this.f48352F0);
            int i5 = this.f48352F0;
            if (iAbs > iAbs2) {
                this.f48352F0 = 0;
                i4 = i5;
            } else {
                this.f48352F0 = i5 - i2;
                i4 = i2;
            }
            m21887x(this.f48352F0);
        } else if (i2 > 0 && this.f48407i1) {
            int i6 = i3 - i2;
            this.f48352F0 = i6;
            m21887x(i6);
            i4 = i2;
        }
        this.f48358I0.m2454c(i, i2 - i4, 0, iArr, null);
        iArr[1] = iArr[1] + i4;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        boolean zM2455d = this.f48358I0.m2455d(i, i2, i3, i4, this.f48356H0, 0, null);
        int i5 = i4 + this.f48356H0[1];
        boolean z = this.f48365M;
        if ((i5 < 0 && (this.f48347D || z)) || (i5 > 0 && (this.f48349E || z))) {
            RefreshState refreshState = this.f48393b1;
            if (refreshState == RefreshState.None || refreshState.f48494e) {
                this.f48389Z0.mo21890a(i5 > 0 ? RefreshState.PullUpToLoad : RefreshState.PullDownToRefresh);
                if (!zM2455d) {
                    ViewParent parent = getParent();
                    if (parent instanceof ViewGroup) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
            }
            int i6 = this.f48352F0 - i5;
            this.f48352F0 = i6;
            m21887x(i6);
        }
        if (!this.f48407i1 || i2 >= 0) {
            return;
        }
        this.f48407i1 = false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onNestedScrollAccepted(View view, View view2, int i) {
        this.f48360J0.f5359a = i;
        this.f48358I0.m2459h(i & 2, 0);
        this.f48352F0 = this.f48392b;
        this.f48354G0 = true;
        m21884u(0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onStartNestedScroll(View view, View view2, int i) {
        if (isEnabled() && isNestedScrollingEnabled() && (i & 2) != 0) {
            return this.f48365M || this.f48347D || this.f48349E;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onStopNestedScroll(View view) {
        this.f48360J0.f5359a = 0;
        this.f48354G0 = false;
        this.f48352F0 = 0;
        m21889z();
        this.f48358I0.m2460i(0);
    }

    /* JADX INFO: renamed from: q */
    public final ValueAnimator m21880q(int i, int i2, SmartUtil smartUtil, int i3) {
        if (this.f48392b == i) {
            return null;
        }
        ValueAnimator valueAnimator = this.f48415m1;
        if (valueAnimator != null) {
            valueAnimator.setDuration(0L);
            this.f48415m1.cancel();
            this.f48415m1 = null;
        }
        this.f48413l1 = null;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.f48392b, i);
        this.f48415m1 = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(i3);
        this.f48415m1.setInterpolator(smartUtil);
        this.f48415m1.addListener(new AnimatorListenerAdapter() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                RefreshState refreshState;
                RefreshState refreshState2;
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    smartRefreshLayout.f48415m1 = null;
                    if (smartRefreshLayout.f48392b == 0 && (refreshState = smartRefreshLayout.f48391a1) != (refreshState2 = RefreshState.None) && !refreshState.f48494e && !refreshState.f48493d) {
                        smartRefreshLayout.m21888y(refreshState2);
                        return;
                    }
                    RefreshState refreshState3 = smartRefreshLayout.f48391a1;
                    if (refreshState3 != smartRefreshLayout.f48393b1) {
                        smartRefreshLayout.setViceState(refreshState3);
                    }
                }
            }
        });
        this.f48415m1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (smartRefreshLayout.f48415m1 != null) {
                    smartRefreshLayout.f48389Z0.m21896g(((Integer) valueAnimator2.getAnimatedValue()).intValue(), false);
                }
            }
        });
        this.f48415m1.setStartDelay(i2);
        this.f48415m1.start();
        return this.f48415m1;
    }

    /* JADX INFO: renamed from: r */
    public final SmartRefreshLayout m21881r() {
        RefreshState refreshState;
        RefreshState refreshState2 = this.f48391a1;
        RefreshState refreshState3 = RefreshState.None;
        if (refreshState2 == refreshState3 && ((refreshState = this.f48393b1) == RefreshState.Refreshing || refreshState == RefreshState.Loading)) {
            this.f48393b1 = refreshState3;
        }
        if (refreshState2 == RefreshState.Refreshing) {
            m21883t(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.f48395c1))), 300) << 16, true, Boolean.FALSE);
            return this;
        }
        if (refreshState2 == RefreshState.Loading) {
            m21882s(Math.min(Math.max(0, 300 - ((int) (System.currentTimeMillis() - this.f48395c1))), 300) << 16, false);
            return this;
        }
        if (this.f48389Z0.m21895f(0) == null) {
            m21888y(refreshState3);
            return this;
        }
        if (this.f48391a1.f48490a) {
            m21888y(RefreshState.PullDownCanceled);
            return this;
        }
        m21888y(RefreshState.PullUpCanceled);
        return this;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        View view = this.f48386W0.f48566c;
        WeakHashMap weakHashMap = ViewCompat.f5378a;
        if (view.isNestedScrollingEnabled()) {
            this.f48420r = z;
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    /* JADX INFO: renamed from: s */
    public final void m21882s(int i, boolean z) {
        int i2 = i >> 16;
        int i3 = (i << 16) >> 16;
        RunnableC74337 runnableC74337 = new RunnableC74337(i2, z);
        if (i3 > 0) {
            this.f48388Y0.postDelayed(runnableC74337, i3);
        } else {
            runnableC74337.run();
        }
    }

    @Override // android.view.View
    public void setNestedScrollingEnabled(boolean z) {
        this.f48377S = z;
        this.f48358I0.m2458g(z);
    }

    public void setStateDirectLoading(boolean z) {
        RefreshState refreshState = this.f48391a1;
        RefreshState refreshState2 = RefreshState.Loading;
        if (refreshState != refreshState2) {
            this.f48395c1 = System.currentTimeMillis();
            this.f48407i1 = true;
            m21888y(refreshState2);
            m21882s(AdError.SERVER_ERROR_CODE, false);
            RefreshFooter refreshFooter = this.f48384V0;
            if (refreshFooter != null) {
                int i = this.f48366M0;
                refreshFooter.mo21901d(this, i, (int) (this.f48376R0 * i));
            }
        }
    }

    public void setStateLoading(final boolean z) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    SmartRefreshLayout.this.setStateDirectLoading(z);
                }
            }
        };
        m21888y(RefreshState.LoadReleased);
        ValueAnimator valueAnimatorM21895f = this.f48389Z0.m21895f(-this.f48366M0);
        if (valueAnimatorM21895f != null) {
            valueAnimatorM21895f.addListener(animatorListenerAdapter);
        }
        RefreshFooter refreshFooter = this.f48384V0;
        if (refreshFooter != null) {
            int i = this.f48366M0;
            refreshFooter.mo21903f(this, i, (int) (this.f48376R0 * i));
        }
        if (valueAnimatorM21895f == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    public void setStateRefreshing(final boolean z) {
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (animator == null || animator.getDuration() != 0) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                    smartRefreshLayout.f48395c1 = jCurrentTimeMillis;
                    smartRefreshLayout.m21888y(RefreshState.Refreshing);
                    OnRefreshListener onRefreshListener = smartRefreshLayout.f48350E0;
                    if (onRefreshListener == null) {
                        smartRefreshLayout.mo21877b(3000);
                    } else if (z) {
                        onRefreshListener.mo21916g(smartRefreshLayout);
                    }
                    RefreshHeader refreshHeader = smartRefreshLayout.f48382U0;
                    if (refreshHeader != null) {
                        int i = smartRefreshLayout.f48362K0;
                        refreshHeader.mo21901d(smartRefreshLayout, i, (int) (smartRefreshLayout.f48374Q0 * i));
                    }
                }
            }
        };
        m21888y(RefreshState.RefreshReleased);
        ValueAnimator valueAnimatorM21895f = this.f48389Z0.m21895f(this.f48362K0);
        if (valueAnimatorM21895f != null) {
            valueAnimatorM21895f.addListener(animatorListenerAdapter);
        }
        RefreshHeader refreshHeader = this.f48382U0;
        if (refreshHeader != null) {
            int i = this.f48362K0;
            refreshHeader.mo21903f(this, i, (int) (this.f48374Q0 * i));
        }
        if (valueAnimatorM21895f == null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
    }

    public void setViceState(RefreshState refreshState) {
        RefreshState refreshState2 = this.f48391a1;
        if (refreshState2.f48493d && refreshState2.f48490a != refreshState.f48490a) {
            m21888y(RefreshState.None);
        }
        if (this.f48393b1 != refreshState) {
            this.f48393b1 = refreshState;
        }
    }

    /* JADX INFO: renamed from: t */
    public final void m21883t(int i, final boolean z, final Boolean bool) {
        final int i2 = i >> 16;
        int i3 = (i << 16) >> 16;
        Runnable runnable = new Runnable() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.6

            /* JADX INFO: renamed from: a */
            public int f48437a = 0;

            @Override // java.lang.Runnable
            public final void run() {
                int i4 = this.f48437a;
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                int i5 = smartRefreshLayout.f48400f;
                SmartUtil smartUtil = smartRefreshLayout.f48345B;
                RefreshKernelImpl refreshKernelImpl = smartRefreshLayout.f48389Z0;
                if (i4 == 0) {
                    RefreshState refreshState = smartRefreshLayout.f48391a1;
                    RefreshState refreshState2 = RefreshState.None;
                    Boolean bool2 = bool;
                    if (refreshState == refreshState2 && smartRefreshLayout.f48393b1 == RefreshState.Refreshing) {
                        smartRefreshLayout.f48393b1 = refreshState2;
                    } else {
                        ValueAnimator valueAnimator = smartRefreshLayout.f48415m1;
                        if (valueAnimator != null && refreshState.f48490a && (refreshState.f48493d || refreshState == RefreshState.RefreshReleased)) {
                            valueAnimator.setDuration(0L);
                            smartRefreshLayout.f48415m1.cancel();
                            smartRefreshLayout.f48415m1 = null;
                            if (refreshKernelImpl.m21895f(0) == null) {
                                smartRefreshLayout.m21888y(refreshState2);
                            } else {
                                smartRefreshLayout.m21888y(RefreshState.PullDownCanceled);
                            }
                        } else if (refreshState == RefreshState.Refreshing && smartRefreshLayout.f48382U0 != null && smartRefreshLayout.f48386W0 != null) {
                            this.f48437a = i4 + 1;
                            smartRefreshLayout.f48388Y0.postDelayed(this, i2);
                            smartRefreshLayout.m21888y(RefreshState.RefreshFinish);
                            if (bool2 == Boolean.FALSE) {
                                smartRefreshLayout.m21873A(false);
                            }
                        }
                    }
                    if (bool2 == Boolean.TRUE) {
                        smartRefreshLayout.m21873A(true);
                        return;
                    }
                    return;
                }
                int iMo21904h = smartRefreshLayout.f48382U0.mo21904h(smartRefreshLayout, z);
                if (iMo21904h < Integer.MAX_VALUE) {
                    if (smartRefreshLayout.f48418p || smartRefreshLayout.f48354G0) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (smartRefreshLayout.f48418p) {
                            float f = smartRefreshLayout.f48410k;
                            smartRefreshLayout.f48406i = f;
                            smartRefreshLayout.f48396d = 0;
                            smartRefreshLayout.f48418p = false;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 0, smartRefreshLayout.f48408j, (f + smartRefreshLayout.f48392b) - (smartRefreshLayout.f48390a * 2), 0));
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 2, smartRefreshLayout.f48408j, smartRefreshLayout.f48410k + smartRefreshLayout.f48392b, 0));
                        }
                        if (smartRefreshLayout.f48354G0) {
                            smartRefreshLayout.f48352F0 = 0;
                            SmartRefreshLayout.super.dispatchTouchEvent(MotionEvent.obtain(jCurrentTimeMillis, jCurrentTimeMillis, 1, smartRefreshLayout.f48408j, smartRefreshLayout.f48410k, 0));
                            smartRefreshLayout.f48354G0 = false;
                            smartRefreshLayout.f48396d = 0;
                        }
                    }
                    int i6 = smartRefreshLayout.f48392b;
                    if (i6 <= 0) {
                        if (i6 < 0) {
                            smartRefreshLayout.m21880q(0, iMo21904h, smartUtil, i5);
                            return;
                        } else {
                            refreshKernelImpl.m21896g(0, false);
                            refreshKernelImpl.mo21890a(RefreshState.None);
                            return;
                        }
                    }
                    ValueAnimator valueAnimatorM21880q = smartRefreshLayout.m21880q(0, iMo21904h, smartUtil, i5);
                    RefreshContentWrapper refreshContentWrapperM21915e = smartRefreshLayout.f48373Q ? smartRefreshLayout.f48386W0.m21915e(smartRefreshLayout.f48392b) : null;
                    if (valueAnimatorM21880q == null || refreshContentWrapperM21915e == null) {
                        return;
                    }
                    valueAnimatorM21880q.addUpdateListener(refreshContentWrapperM21915e);
                }
            }
        };
        if (i3 > 0) {
            this.f48388Y0.postDelayed(runnable, i3);
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: renamed from: u */
    public final boolean m21884u(int i) {
        if (i == 0) {
            if (this.f48415m1 != null) {
                RefreshState refreshState = this.f48391a1;
                if (refreshState.f48495f || refreshState == RefreshState.TwoLevelReleased || refreshState == RefreshState.RefreshReleased || refreshState == RefreshState.LoadReleased) {
                    return true;
                }
                RefreshState refreshState2 = RefreshState.PullDownCanceled;
                RefreshKernelImpl refreshKernelImpl = this.f48389Z0;
                if (refreshState == refreshState2) {
                    refreshKernelImpl.mo21890a(RefreshState.PullDownToRefresh);
                } else if (refreshState == RefreshState.PullUpCanceled) {
                    refreshKernelImpl.mo21890a(RefreshState.PullUpToLoad);
                }
                this.f48415m1.setDuration(0L);
                this.f48415m1.cancel();
                this.f48415m1 = null;
            }
            this.f48413l1 = null;
        }
        return this.f48415m1 != null;
    }

    /* JADX INFO: renamed from: v */
    public final boolean m21885v(boolean z) {
        return z && !this.f48369O;
    }

    /* JADX INFO: renamed from: w */
    public final boolean m21886w(boolean z, RefreshInternal refreshInternal) {
        return z || this.f48369O || refreshInternal == null || refreshInternal.getSpinnerStyle() == SpinnerStyle.f48498e;
    }

    /* JADX INFO: renamed from: x */
    public final void m21887x(float f) {
        RefreshState refreshState;
        if (this.f48354G0 && !this.f48375R && f < 0.0f && !this.f48386W0.m21912a()) {
            f = 0.0f;
        }
        int i = this.f48402g;
        if (f > i * 5 && getTag() == null) {
            float f2 = i;
            if (this.f48410k < f2 / 6.0f && this.f48408j < f2 / 16.0f) {
                Toast.makeText(getContext(), "你这么死拉，臣妾做不到啊！", 0).show();
                setTag("你这么死拉，臣妾做不到啊！");
            }
        }
        RefreshState refreshState2 = this.f48391a1;
        RefreshState refreshState3 = RefreshState.TwoLevel;
        RefreshKernelImpl refreshKernelImpl = this.f48389Z0;
        if (refreshState2 != refreshState3 || f <= 0.0f || this.f48386W0 == null) {
            RefreshState refreshState4 = RefreshState.Refreshing;
            float f3 = this.f48416n;
            if (refreshState2 != refreshState4 || f < 0.0f) {
                float f4 = this.f48376R0;
                if (f < 0.0f && (refreshState2 == RefreshState.Loading || ((this.f48359J && this.f48383V && this.f48385W && m21885v(this.f48349E)) || (this.f48367N && !this.f48383V && m21885v(this.f48349E))))) {
                    int i2 = this.f48366M0;
                    if (f > (-i2)) {
                        refreshKernelImpl.m21896g((int) f, true);
                    } else {
                        float f5 = (f4 - 1.0f) * i2;
                        int iMax = Math.max((i * 4) / 3, getHeight());
                        int i3 = this.f48366M0;
                        float f6 = iMax - i3;
                        float f7 = -Math.min(0.0f, (i3 + f) * f3);
                        float f8 = -f7;
                        if (f6 == 0.0f) {
                            f6 = 1.0f;
                        }
                        refreshKernelImpl.m21896g(((int) (-Math.min((1.0f - ((float) Math.pow(100.0d, f8 / f6))) * f5, f7))) - this.f48366M0, true);
                    }
                } else if (f >= 0.0f) {
                    float f9 = this.f48374Q0 * this.f48362K0;
                    float fMax = Math.max(i / 2, getHeight());
                    float fMax2 = Math.max(0.0f, f3 * f);
                    float f10 = -fMax2;
                    if (fMax == 0.0f) {
                        fMax = 1.0f;
                    }
                    refreshKernelImpl.m21896g((int) Math.min((1.0f - ((float) Math.pow(100.0d, f10 / fMax))) * f9, fMax2), true);
                } else {
                    float f11 = f4 * this.f48366M0;
                    float fMax3 = Math.max(i / 2, getHeight());
                    float f12 = -Math.min(0.0f, f3 * f);
                    float f13 = -f12;
                    if (fMax3 == 0.0f) {
                        fMax3 = 1.0f;
                    }
                    refreshKernelImpl.m21896g((int) (-Math.min((1.0f - ((float) Math.pow(100.0d, f13 / fMax3))) * f11, f12)), true);
                }
            } else {
                float f14 = this.f48362K0;
                if (f < f14) {
                    refreshKernelImpl.m21896g((int) f, true);
                } else {
                    float f15 = (this.f48374Q0 - 1.0f) * f14;
                    int iMax2 = Math.max((i * 4) / 3, getHeight());
                    int i4 = this.f48362K0;
                    float f16 = iMax2 - i4;
                    float fMax4 = Math.max(0.0f, (f - i4) * f3);
                    float f17 = -fMax4;
                    if (f16 == 0.0f) {
                        f16 = 1.0f;
                    }
                    refreshKernelImpl.m21896g(((int) Math.min((1.0f - ((float) Math.pow(100.0d, f17 / f16))) * f15, fMax4)) + this.f48362K0, true);
                }
            }
        } else {
            int measuredHeight = getMeasuredHeight();
            float f18 = this.f48412l;
            refreshKernelImpl.m21896g(Math.min((int) f, f18 > 1.0f ? (int) f18 : (int) (measuredHeight * f18)), true);
        }
        if (!this.f48367N || this.f48383V || !m21885v(this.f48349E) || f >= 0.0f || (refreshState = this.f48391a1) == RefreshState.Refreshing || refreshState == RefreshState.Loading || refreshState == RefreshState.LoadFinish) {
            return;
        }
        if (this.f48381U) {
            this.f48413l1 = null;
            refreshKernelImpl.m21895f(-this.f48366M0);
        }
        setStateDirectLoading(false);
        this.f48388Y0.postDelayed(new Runnable() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout.5
            @Override // java.lang.Runnable
            public final void run() {
                SmartRefreshLayout.this.m21882s(AdError.SERVER_ERROR_CODE, false);
            }
        }, this.f48400f);
    }

    /* JADX INFO: renamed from: y */
    public final void m21888y(RefreshState refreshState) {
        RefreshState refreshState2 = this.f48391a1;
        if (refreshState2 == refreshState) {
            if (this.f48393b1 != refreshState2) {
                this.f48393b1 = refreshState2;
                return;
            }
            return;
        }
        this.f48391a1 = refreshState;
        this.f48393b1 = refreshState;
        RefreshHeader refreshHeader = this.f48382U0;
        RefreshFooter refreshFooter = this.f48384V0;
        if (refreshHeader != null) {
            refreshHeader.mo21908b(this, refreshState2, refreshState);
        }
        if (refreshFooter != null) {
            refreshFooter.mo21908b(this, refreshState2, refreshState);
        }
        if (refreshState == RefreshState.LoadFinish) {
            this.f48407i1 = false;
        }
    }

    /* JADX INFO: renamed from: z */
    public final void m21889z() {
        RefreshState refreshState = this.f48391a1;
        RefreshState refreshState2 = RefreshState.TwoLevel;
        RefreshKernelImpl refreshKernelImpl = this.f48389Z0;
        if (refreshState == refreshState2) {
            int measuredHeight = getMeasuredHeight();
            float f = this.f48412l;
            int i = f > 1.0f ? (int) f : (int) (measuredHeight * f);
            if (this.f48427y > -1000 && this.f48392b > i / 2) {
                ValueAnimator valueAnimatorM21895f = refreshKernelImpl.m21895f(i);
                if (valueAnimatorM21895f != null) {
                    valueAnimatorM21895f.setDuration(this.f48398e);
                    return;
                }
                return;
            }
            if (this.f48418p) {
                SmartRefreshLayout smartRefreshLayout = SmartRefreshLayout.this;
                if (smartRefreshLayout.f48391a1 == refreshState2) {
                    smartRefreshLayout.f48389Z0.mo21890a(RefreshState.TwoLevelFinish);
                    if (smartRefreshLayout.f48392b != 0) {
                        refreshKernelImpl.m21895f(0).setDuration(smartRefreshLayout.f48398e);
                        return;
                    } else {
                        refreshKernelImpl.m21896g(0, false);
                        smartRefreshLayout.m21888y(RefreshState.None);
                        return;
                    }
                }
                return;
            }
            return;
        }
        RefreshState refreshState3 = RefreshState.Loading;
        if (refreshState == refreshState3 || (this.f48359J && this.f48383V && this.f48385W && this.f48392b < 0 && m21885v(this.f48349E))) {
            int i2 = this.f48392b;
            int i3 = -this.f48366M0;
            if (i2 < i3) {
                refreshKernelImpl.m21895f(i3);
                return;
            } else {
                if (i2 > 0) {
                    refreshKernelImpl.m21895f(0);
                    return;
                }
                return;
            }
        }
        RefreshState refreshState4 = this.f48391a1;
        RefreshState refreshState5 = RefreshState.Refreshing;
        if (refreshState4 == refreshState5) {
            int i4 = this.f48392b;
            int i5 = this.f48362K0;
            if (i4 > i5) {
                refreshKernelImpl.m21895f(i5);
                return;
            } else {
                if (i4 < 0) {
                    refreshKernelImpl.m21895f(0);
                    return;
                }
                return;
            }
        }
        if (refreshState4 == RefreshState.PullDownToRefresh) {
            refreshKernelImpl.mo21890a(RefreshState.PullDownCanceled);
            return;
        }
        if (refreshState4 == RefreshState.PullUpToLoad) {
            refreshKernelImpl.mo21890a(RefreshState.PullUpCanceled);
            return;
        }
        if (refreshState4 == RefreshState.ReleaseToRefresh) {
            refreshKernelImpl.mo21890a(refreshState5);
            return;
        }
        if (refreshState4 == RefreshState.ReleaseToLoad) {
            refreshKernelImpl.mo21890a(refreshState3);
            return;
        }
        if (refreshState4 == RefreshState.ReleaseToTwoLevel) {
            refreshKernelImpl.mo21890a(RefreshState.TwoLevelReleased);
            return;
        }
        if (refreshState4 == RefreshState.RefreshReleased) {
            if (this.f48415m1 == null) {
                refreshKernelImpl.m21895f(this.f48362K0);
            }
        } else if (refreshState4 == RefreshState.LoadReleased) {
            if (this.f48415m1 == null) {
                refreshKernelImpl.m21895f(-this.f48366M0);
            }
        } else {
            if (refreshState4 == RefreshState.LoadFinish || this.f48392b == 0) {
                return;
            }
            refreshKernelImpl.m21895f(0);
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout
    public ViewGroup getLayout() {
        return this;
    }

    public SmartRefreshLayout(Context context) {
        this(context, null);
    }
}
