package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Separations {
    public static final int SEPARATION_COMPOSITE = 0;
    public static final int SEPARATION_DISABLED = 2;
    public static final int SEPARATION_SPOT = 1;
    private long pointer;

    static {
        Context.init();
    }

    public Separations(long j) {
        this.pointer = j;
    }

    public native boolean areSeparationsControllable();

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native int getNumberOfSeparations();

    public native Separation getSeparation(int i);

    public native int getSeparationBehavior(int i);

    public native void setSeparationBehavior(int i, int i2);
}
