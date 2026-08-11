package com.hwa.excelllll.customviews.seekbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class SavedState extends View.BaseSavedState {
    public static final Creator<SavedState> CREATOR = new C74231();

    /* JADX INFO: renamed from: a */
    public float f48334a;

    /* JADX INFO: renamed from: b */
    public float f48335b;

    /* JADX INFO: renamed from: c */
    public float f48336c;

    /* JADX INFO: renamed from: d */
    public int f48337d;

    /* JADX INFO: renamed from: e */
    public float f48338e;

    /* JADX INFO: renamed from: f */
    public float f48339f;

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.seekbar.SavedState$1 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class C74231 implements Creator<SavedState> {
        @Override // android.os.Parcelable.Creator
        public final SavedState createFromParcel(Parcel parcel) {
            SavedState savedState = new SavedState(parcel);
            savedState.f48334a = parcel.readFloat();
            savedState.f48335b = parcel.readFloat();
            savedState.f48336c = parcel.readFloat();
            savedState.f48337d = parcel.readInt();
            savedState.f48338e = parcel.readFloat();
            savedState.f48339f = parcel.readFloat();
            return savedState;
        }

        @Override // android.os.Parcelable.Creator
        public final SavedState[] newArray(int i) {
            return new SavedState[i];
        }
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.f48334a);
        parcel.writeFloat(this.f48335b);
        parcel.writeFloat(this.f48336c);
        parcel.writeInt(this.f48337d);
        parcel.writeFloat(this.f48338e);
        parcel.writeFloat(this.f48339f);
    }
}
