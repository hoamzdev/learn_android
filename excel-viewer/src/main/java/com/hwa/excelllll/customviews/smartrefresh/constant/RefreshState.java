package com.hwa.excelllll.customviews.smartrefresh.constant;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public enum RefreshState {
    None(0, false, false, false, false, false),
    PullDownToRefresh(1, true, false, false, false, false),
    PullUpToLoad(2, true, false, false, false, false),
    PullDownCanceled(1, false, false, false, false, false),
    PullUpCanceled(2, false, false, false, false, false),
    ReleaseToRefresh(1, true, false, false, false, true),
    ReleaseToLoad(2, true, false, false, false, true),
    ReleaseToTwoLevel(1, true, false, false, true, true),
    TwoLevelReleased(1, false, false, false, true, false),
    RefreshReleased(1, false, false, false, false, false),
    LoadReleased(2, false, false, false, false, false),
    Refreshing(1, false, true, false, false, false),
    Loading(2, false, true, false, false, false),
    TwoLevel(1, false, true, false, true, false),
    RefreshFinish(1, false, false, true, false, false),
    LoadFinish(2, false, false, true, false, false),
    TwoLevelFinish(1, false, false, true, true, false);


    /* JADX INFO: renamed from: a */
    public final boolean f48490a;

    /* JADX INFO: renamed from: b */
    public final boolean f48491b;

    /* JADX INFO: renamed from: c */
    public final boolean f48492c;

    /* JADX INFO: renamed from: d */
    public final boolean f48493d;

    /* JADX INFO: renamed from: e */
    public final boolean f48494e;

    /* JADX INFO: renamed from: f */
    public final boolean f48495f;

    /* JADX INFO: renamed from: g */
    public final boolean f48496g;

    RefreshState(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.f48490a = i == 1;
        this.f48491b = i == 2;
        this.f48493d = z;
        this.f48494e = z2;
        this.f48495f = z3;
        this.f48492c = z4;
        this.f48496g = z5;
    }
}
