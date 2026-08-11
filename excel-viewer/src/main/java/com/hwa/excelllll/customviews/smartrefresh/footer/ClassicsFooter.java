package com.hwa.excelllll.customviews.smartrefresh.footer;

import android.widget.ImageView;
import android.widget.TextView;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshFooter;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class ClassicsFooter extends InternalClassics<ClassicsFooter> implements RefreshFooter {

    /* JADX INFO: renamed from: q */
    public boolean f48514q;

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.footer.ClassicsFooter$1 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static /* synthetic */ class C74371 {

        /* JADX INFO: renamed from: a */
        public static final /* synthetic */ int[] f48515a;

        static {
            int[] iArr = new int[RefreshState.values().length];
            f48515a = iArr;
            try {
                RefreshState refreshState = RefreshState.None;
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                int[] iArr2 = f48515a;
                RefreshState refreshState2 = RefreshState.None;
                iArr2[2] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                int[] iArr3 = f48515a;
                RefreshState refreshState3 = RefreshState.None;
                iArr3[12] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                int[] iArr4 = f48515a;
                RefreshState refreshState4 = RefreshState.None;
                iArr4[10] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                int[] iArr5 = f48515a;
                RefreshState refreshState5 = RefreshState.None;
                iArr5[6] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                int[] iArr6 = f48515a;
                RefreshState refreshState6 = RefreshState.None;
                iArr6[11] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnStateChangedListener
    /* JADX INFO: renamed from: b */
    public final void mo21908b(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        ImageView imageView = this.f48582e;
        if (this.f48514q) {
            return;
        }
        int iOrdinal = refreshState2.ordinal();
        if (iOrdinal == 0) {
            imageView.setVisibility(0);
        } else if (iOrdinal != 2) {
            if (iOrdinal == 6) {
                this.f48581d.setText((CharSequence) null);
                imageView.animate().rotation(0.0f);
                return;
            }
            switch (iOrdinal) {
                case 10:
                case 12:
                    imageView.setVisibility(8);
                    this.f48581d.setText((CharSequence) null);
                    break;
                case 11:
                    this.f48581d.setText((CharSequence) null);
                    imageView.setVisibility(8);
                    break;
            }
            return;
        }
        this.f48581d.setText((CharSequence) null);
        imageView.animate().rotation(180.0f);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshFooter
    /* JADX INFO: renamed from: c */
    public final boolean mo21899c(boolean z) {
        if (this.f48514q == z) {
            return true;
        }
        this.f48514q = z;
        ImageView imageView = this.f48582e;
        TextView textView = this.f48581d;
        if (z) {
            textView.setText((CharSequence) null);
            imageView.setVisibility(8);
            return true;
        }
        textView.setText((CharSequence) null);
        imageView.setVisibility(0);
        return true;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics, com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: h */
    public final int mo21904h(RefreshLayout refreshLayout, boolean z) {
        super.mo21904h(refreshLayout, z);
        if (this.f48514q) {
            return 0;
        }
        this.f48581d.setText((CharSequence) null);
        return this.f48590m;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalClassics, com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... iArr) {
        if (this.f48579b == SpinnerStyle.f48498e) {
            super.setPrimaryColors(iArr);
        }
    }
}
