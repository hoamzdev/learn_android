package com.hwa.excelllll.customviews.smartrefresh.constant;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class DimensionStatus {

    /* JADX INFO: renamed from: c */
    public static final DimensionStatus f48464c;

    /* JADX INFO: renamed from: d */
    public static final DimensionStatus f48465d;

    /* JADX INFO: renamed from: e */
    public static final DimensionStatus f48466e;

    /* JADX INFO: renamed from: f */
    public static final DimensionStatus f48467f;

    /* JADX INFO: renamed from: g */
    public static final DimensionStatus f48468g;

    /* JADX INFO: renamed from: h */
    public static final DimensionStatus[] f48469h;

    /* JADX INFO: renamed from: a */
    public final int f48470a;

    /* JADX INFO: renamed from: b */
    public final boolean f48471b;

    static {
        DimensionStatus dimensionStatus = new DimensionStatus(0, false);
        f48464c = dimensionStatus;
        DimensionStatus dimensionStatus2 = new DimensionStatus(1, true);
        DimensionStatus dimensionStatus3 = new DimensionStatus(2, false);
        f48465d = dimensionStatus3;
        DimensionStatus dimensionStatus4 = new DimensionStatus(3, true);
        DimensionStatus dimensionStatus5 = new DimensionStatus(4, false);
        f48466e = dimensionStatus5;
        DimensionStatus dimensionStatus6 = new DimensionStatus(5, true);
        DimensionStatus dimensionStatus7 = new DimensionStatus(6, false);
        f48467f = dimensionStatus7;
        DimensionStatus dimensionStatus8 = new DimensionStatus(7, true);
        DimensionStatus dimensionStatus9 = new DimensionStatus(8, false);
        DimensionStatus dimensionStatus10 = new DimensionStatus(9, true);
        f48468g = dimensionStatus10;
        f48469h = new DimensionStatus[]{dimensionStatus, dimensionStatus2, dimensionStatus3, dimensionStatus4, dimensionStatus5, dimensionStatus6, dimensionStatus7, dimensionStatus8, dimensionStatus9, dimensionStatus10, new DimensionStatus(10, false), new DimensionStatus(10, true)};
    }

    public DimensionStatus(int i, boolean z) {
        this.f48470a = i;
        this.f48471b = z;
    }

    /* JADX INFO: renamed from: a */
    public final boolean m21907a(DimensionStatus dimensionStatus) {
        int i = dimensionStatus.f48470a;
        int i2 = this.f48470a;
        if (i2 >= i) {
            return (!this.f48471b || f48468g == this) && i2 == i;
        }
        return true;
    }
}
