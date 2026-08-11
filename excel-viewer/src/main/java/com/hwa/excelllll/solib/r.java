package com.hwa.excelllll.solib;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.LinkedBlockingDeque;

/* JADX INFO: loaded from: classes.dex */
public class r {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f4250b;
    private Looper c;
    private Thread d = null;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected LinkedBlockingDeque<a> f4249a = new LinkedBlockingDeque<>();

    public r(Looper looper) {
        this.c = null;
        this.c = looper;
    }

    public void a(a aVar) {
        if (this.f4250b) {
            try {
                this.f4249a.put(aVar);
            } catch (Throwable th) {
                Log.e("Worker", "exception in Worker.add: " + th.toString());
            }
        }
    }

    public void b(a aVar) {
        if (this.f4250b) {
            try {
                this.f4249a.addFirst(aVar);
            } catch (Throwable th) {
                Log.e("Worker", "exception in Worker.addFirst: " + th.toString());
            }
        }
    }

    public void c() {
        this.f4250b = false;
        this.d.interrupt();
        this.f4249a.clear();
    }

    public static class a implements Runnable {
        public void a() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    public void a() {
        Thread thread = new Thread(new Runnable() { // from class: com.artifex.solib.r.1
            @Override // java.lang.Runnable
            public void run() {
                while (r.this.f4250b) {
                    try {
                        a aVarTake = r.this.f4249a.take();
                        aVarTake.a();
                        new Handler(r.this.c).post(aVarTake);
                    } catch (InterruptedException unused) {
                    } catch (Throwable th) {
                        Log.e("Worker", "exception in Worker thread: " + th.toString());
                    }
                }
            }
        });
        this.d = thread;
        thread.start();
        this.f4250b = true;
    }

    public boolean b() {
        return this.f4250b;
    }
}
