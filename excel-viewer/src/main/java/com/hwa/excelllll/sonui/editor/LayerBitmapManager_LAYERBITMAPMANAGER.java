package com.hwa.excelllll.sonui.editor;

import android.graphics.Bitmap;
import com.artifex.solib.SOBitmap;
import java.util.TreeSet;

/* JADX INFO: loaded from: classes.dex */
public class LayerBitmapManager {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final long f4659a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    long f4660b;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private int q = 0;
    private int r = 0;
    private MemoryInfoProvider s = null;
    private TreeSet<SOBitmap> t = new TreeSet<>();
    private TreeSet<SOBitmap> u = new TreeSet<>();
    private TreeSet<SOBitmap> v = new TreeSet<>();

    /* JADX INFO: renamed from: com.artifex.sonui.editor.LayerBitmapManager$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4661a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f4662b;

        static {
            int[] iArr = new int[SOBitmap.Type.values().length];
            f4662b = iArr;
            try {
                iArr[SOBitmap.Type.A8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4662b[SOBitmap.Type.RGB565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4662b[SOBitmap.Type.RGBA8888.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[Bitmap.Config.values().length];
            f4661a = iArr2;
            try {
                iArr2[Bitmap.Config.ALPHA_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4661a[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4661a[Bitmap.Config.ARGB_8888.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public LayerBitmapManager() {
        long jMaxMemory = Runtime.getRuntime().maxMemory() / 6;
        this.f4659a = jMaxMemory;
        this.f4660b = jMaxMemory;
    }

    private boolean a(int i, TreeSet<SOBitmap> treeSet) {
        boolean z = false;
        while (!treeSet.isEmpty() && i > this.f4660b) {
            SOBitmap sOBitmapFirst = treeSet.first();
            treeSet.remove(sOBitmapFirst);
            this.f4660b += (long) sOBitmapFirst.a().getByteCount();
            sOBitmapFirst.e();
            this.g++;
            if (treeSet == this.t) {
                this.h++;
            } else if (treeSet == this.u) {
                this.i++;
            } else {
                this.j++;
            }
            z = true;
        }
        if (z) {
            Runtime.getRuntime().gc();
        }
        return ((long) i) <= this.f4660b;
    }

    public void addToCache(SOBitmap sOBitmap) {
        TreeSet<SOBitmap> treeSet;
        synchronized (this) {
            if (sOBitmap == null) {
                return;
            }
            try {
                Bitmap bitmapA = sOBitmap.a();
                if (bitmapA != null && !bitmapA.isRecycled()) {
                    int i = AnonymousClass1.f4661a[sOBitmap.a().getConfig().ordinal()];
                    if (i != 1) {
                        treeSet = i != 2 ? this.t : this.u;
                    } else {
                        treeSet = this.v;
                    }
                    if (treeSet == null) {
                        return;
                    }
                    if (a(bitmapA.getByteCount(), treeSet)) {
                        treeSet.add(sOBitmap);
                        this.f4660b -= (long) bitmapA.getByteCount();
                        return;
                    }
                    sOBitmap.e();
                    Runtime.getRuntime().gc();
                    this.o++;
                    if (treeSet == this.t) {
                        this.p++;
                    } else if (treeSet == this.u) {
                        this.q++;
                    } else {
                        this.r++;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void clearAllBitmaps() {
        synchronized (this) {
            try {
                TreeSet<SOBitmap> treeSet = this.t;
                if (treeSet != null) {
                    a(treeSet);
                }
                TreeSet<SOBitmap> treeSet2 = this.u;
                if (treeSet2 != null) {
                    a(treeSet2);
                }
                if (this.u != null) {
                    a(this.v);
                }
                Runtime.getRuntime().gc();
                this.c = 0;
                this.g = 0;
                this.k = 0;
                this.o = 0;
                this.m = 0;
                this.e = 0;
                this.q = 0;
                this.i = 0;
                this.n = 0;
                this.f = 0;
                this.r = 0;
                this.j = 0;
                this.l = 0;
                this.d = 0;
                this.p = 0;
                this.h = 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void dispose() {
        synchronized (this) {
            clearAllBitmaps();
            this.t = null;
            this.u = null;
            this.v = null;
            this.s = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0030 A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x003f A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0055 A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x005e A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0062 A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x0068 A[Catch: all -> 0x005b, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x0078 A[Catch: all -> 0x005b, TRY_ENTER, TryCatch #0 {all -> 0x005b, blocks: (B:4:0x0003, B:8:0x0011, B:15:0x001e, B:17:0x0024, B:35:0x007c, B:20:0x0030, B:22:0x003f, B:24:0x0055, B:31:0x006d, B:27:0x005e, B:29:0x0062, B:30:0x0068, B:34:0x0078, B:36:0x007f, B:38:0x0086, B:41:0x0091, B:43:0x009a, B:44:0x00a0, B:46:0x00a4, B:47:0x00aa, B:50:0x00b1, B:9:0x0014, B:10:0x0017), top: B:55:0x0003 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0078 -> B:35:0x007c). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    public com.artifex.solib.SOBitmap get(int r12, com.artifex.solib.SOBitmap.Type r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            int r0 = r12 * r12
            int[] r1 = com.artifex.sonui.editor.LayerBitmapManager.AnonymousClass1.f4662b     // Catch: java.lang.Throwable -> L5b
            int r2 = r13.ordinal()     // Catch: java.lang.Throwable -> L5b
            r1 = r1[r2]     // Catch: java.lang.Throwable -> L5b
            r2 = 1
            if (r1 == r2) goto L17
            r3 = 2
            if (r1 == r3) goto L14
            java.util.TreeSet<com.artifex.solib.SOBitmap> r1 = r11.t     // Catch: java.lang.Throwable -> L5b
            goto L19
        L14:
            java.util.TreeSet<com.artifex.solib.SOBitmap> r1 = r11.u     // Catch: java.lang.Throwable -> L5b
            goto L19
        L17:
            java.util.TreeSet<com.artifex.solib.SOBitmap> r1 = r11.v     // Catch: java.lang.Throwable -> L5b
        L19:
            r3 = 0
            if (r1 != 0) goto L1e
            monitor-exit(r11)
            return r3
        L1e:
            boolean r4 = r1.isEmpty()     // Catch: java.lang.Throwable -> L5b
            if (r4 != 0) goto L2c
            java.lang.Object r4 = r1.first()     // Catch: java.lang.Throwable -> L5b
            r10 = r4
            r4 = r1
            r1 = r10
            goto L7c
        L2c:
            r4 = r1
            r1 = r3
        L2e:
            if (r1 == 0) goto L7f
            android.graphics.Bitmap r5 = r1.a()     // Catch: java.lang.Throwable -> L5b
            int r6 = r5.getWidth()     // Catch: java.lang.Throwable -> L5b
            int r7 = r5.getHeight()     // Catch: java.lang.Throwable -> L5b
            int r6 = r6 * r7
            if (r0 > r6) goto L78
            r4.remove(r1)     // Catch: java.lang.Throwable -> L5b
            long r6 = r11.f4660b     // Catch: java.lang.Throwable -> L5b
            int r13 = r5.getByteCount()     // Catch: java.lang.Throwable -> L5b
            long r8 = (long) r13     // Catch: java.lang.Throwable -> L5b
            long r6 = r6 + r8
            r11.f4660b = r6     // Catch: java.lang.Throwable -> L5b
            int r13 = r11.c     // Catch: java.lang.Throwable -> L5b
            int r13 = r13 + r2
            r11.c = r13     // Catch: java.lang.Throwable -> L5b
            java.util.TreeSet<com.artifex.solib.SOBitmap> r13 = r11.t     // Catch: java.lang.Throwable -> L5b
            if (r4 != r13) goto L5e
            int r13 = r11.d     // Catch: java.lang.Throwable -> L5b
            int r13 = r13 + r2
            r11.d = r13     // Catch: java.lang.Throwable -> L5b
            goto L6d
        L5b:
            r0 = move-exception
            r12 = r0
            goto Lb8
        L5e:
            java.util.TreeSet<com.artifex.solib.SOBitmap> r13 = r11.u     // Catch: java.lang.Throwable -> L5b
            if (r4 != r13) goto L68
            int r13 = r11.e     // Catch: java.lang.Throwable -> L5b
            int r13 = r13 + r2
            r11.e = r13     // Catch: java.lang.Throwable -> L5b
            goto L6d
        L68:
            int r13 = r11.f     // Catch: java.lang.Throwable -> L5b
            int r13 = r13 + r2
            r11.f = r13     // Catch: java.lang.Throwable -> L5b
        L6d:
            com.artifex.solib.SOBitmap r0 = new com.artifex.solib.SOBitmap     // Catch: java.lang.Throwable -> L5b
            r2 = 0
            r3 = 0
            r5 = r12
            r4 = r12
            r0.<init>(r1, r2, r3, r4, r5)     // Catch: java.lang.Throwable -> L5b
            monitor-exit(r11)
            return r0
        L78:
            java.lang.Object r1 = r4.higher(r1)     // Catch: java.lang.Throwable -> L5b
        L7c:
            com.artifex.solib.SOBitmap r1 = (com.artifex.solib.SOBitmap) r1     // Catch: java.lang.Throwable -> L5b
            goto L2e
        L7f:
            r11.a(r0, r4)     // Catch: java.lang.Throwable -> L5b
            com.artifex.sonui.editor.MemoryInfoProvider r0 = r11.s     // Catch: java.lang.Throwable -> L5b
            if (r0 == 0) goto Lb1
            long r5 = r11.a(r12, r13)     // Catch: java.lang.Throwable -> L5b
            boolean r0 = r0.checkMemoryAvailable(r5)     // Catch: java.lang.Throwable -> L5b
            if (r0 == 0) goto L91
            goto Lb1
        L91:
            int r12 = r11.k     // Catch: java.lang.Throwable -> L5b
            int r12 = r12 + r2
            r11.k = r12     // Catch: java.lang.Throwable -> L5b
            java.util.TreeSet<com.artifex.solib.SOBitmap> r12 = r11.t     // Catch: java.lang.Throwable -> L5b
            if (r4 != r12) goto La0
            int r12 = r11.l     // Catch: java.lang.Throwable -> L5b
            int r12 = r12 + r2
            r11.l = r12     // Catch: java.lang.Throwable -> L5b
            goto Laf
        La0:
            java.util.TreeSet<com.artifex.solib.SOBitmap> r12 = r11.u     // Catch: java.lang.Throwable -> L5b
            if (r4 != r12) goto Laa
            int r12 = r11.m     // Catch: java.lang.Throwable -> L5b
            int r12 = r12 + r2
            r11.m = r12     // Catch: java.lang.Throwable -> L5b
            goto Laf
        Laa:
            int r12 = r11.n     // Catch: java.lang.Throwable -> L5b
            int r12 = r12 + r2
            r11.n = r12     // Catch: java.lang.Throwable -> L5b
        Laf:
            monitor-exit(r11)
            return r3
        Lb1:
            com.artifex.solib.SOBitmap r0 = new com.artifex.solib.SOBitmap     // Catch: java.lang.Throwable -> L5b
            r0.<init>(r12, r12, r13)     // Catch: java.lang.Throwable -> L5b
            monitor-exit(r11)
            return r0
        Lb8:
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.artifex.sonui.editor.LayerBitmapManager.get(int, com.artifex.solib.SOBitmap$Type):com.artifex.solib.SOBitmap");
    }

    public void setMemoryInfoProvider(MemoryInfoProvider memoryInfoProvider) {
        this.s = memoryInfoProvider;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0018  */
    /* JADX WARN: Code duplicated, block: B:13:0x002f A[LOOP:0: B:6:0x0008->B:13:0x002f, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:17:0x0007 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x000a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x002d -> B:5:0x0007). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:7:0x000a
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private void a(TreeSet<com.artifex.solib.SOBitmap> r6) {
        /*
            r5 = this;
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto L7
            goto L2f
        L7:
            r0 = 0
        L8:
            if (r0 == 0) goto L36
            r6.remove(r0)
            android.graphics.Bitmap r1 = r0.a()
            boolean r1 = r1.isRecycled()
            if (r1 == 0) goto L18
            goto L29
        L18:
            long r1 = r5.f4660b
            android.graphics.Bitmap r3 = r0.a()
            int r3 = r3.getByteCount()
            long r3 = (long) r3
            long r1 = r1 + r3
            r5.f4660b = r1
            r0.e()
        L29:
            boolean r0 = r6.isEmpty()
            if (r0 != 0) goto L7
        L2f:
            java.lang.Object r0 = r6.first()
            com.artifex.solib.SOBitmap r0 = (com.artifex.solib.SOBitmap) r0
            goto L8
        L36:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.artifex.sonui.editor.LayerBitmapManager.a(java.util.TreeSet):void");
    }

    private long a(int i, SOBitmap.Type type) {
        int i2 = AnonymousClass1.f4662b[type.ordinal()];
        int i3 = 2;
        if (i2 != 2) {
            i3 = i2 != 3 ? 1 : 4;
        }
        return i * i * i3;
    }
}
