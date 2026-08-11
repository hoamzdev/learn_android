package com.hwa.excelllll.sonui.editor;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;
import com.artifex.solib.animation.SOAnimationColourEffectCommand;
import com.artifex.solib.animation.SOAnimationCommand;
import com.artifex.solib.animation.SOAnimationDisposeCommand;
import com.artifex.solib.animation.SOAnimationEasings;
import com.artifex.solib.animation.SOAnimationFadeCommand;
import com.artifex.solib.animation.SOAnimationMoveCommand;
import com.artifex.solib.animation.SOAnimationPlotCommand;
import com.artifex.solib.animation.SOAnimationRenderCommand;
import com.artifex.solib.animation.SOAnimationRotateCommand;
import com.artifex.solib.animation.SOAnimationScaleCommand;
import com.artifex.solib.animation.SOAnimationSetOpacityCommand;
import com.artifex.solib.animation.SOAnimationSetPositionCommand;
import com.artifex.solib.animation.SOAnimationSetTransformCommand;
import com.artifex.solib.animation.SOAnimationSetVisibilityCommand;
import com.artifex.solib.animation.SOAnimationWaitForEventCommand;
import com.artifex.solib.animation.SOAnimationWaitForLayerCommand;
import com.artifex.solib.animation.SOAnimationWaitForTimeCommand;
import com.artifex.sonui.editor.a.p;
import defpackage.e6;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public final class SlideShowConductor {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final SODoc f4790a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final SOPage f4791b;
    private int e;
    private SOAnimationCommand[] f;
    private ArrayList<c> g;
    private Timer h;
    private final SlideShowConductorViewManager i;
    private String j = "SlideShowConductor";
    private boolean k = false;
    private boolean l = false;
    private boolean c = false;
    private boolean d = true;

    public final class a extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private com.artifex.sonui.editor.a.e f4795b;

        public a(SOAnimationColourEffectCommand sOAnimationColourEffectCommand, SlideShowConductorView slideShowConductorView) {
            super();
            this.f4795b = null;
            int i = sOAnimationColourEffectCommand.f4189a;
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
                this.f4795b = new com.artifex.sonui.editor.a.e(sOAnimationColourEffectCommand.g, sOAnimationColourEffectCommand.i, (int) (sOAnimationColourEffectCommand.k * 1000.0f), i, slideShowConductorView);
            }
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorColourEffectTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            com.artifex.sonui.editor.a.e eVar = this.f4795b;
            if (eVar != null && !eVar.b() && !this.f4795b.c()) {
                this.f4795b.d();
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorFadeTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void b(c cVar) {
            com.artifex.sonui.editor.a.e eVar = this.f4795b;
            if (eVar == null || !eVar.b() || !this.f4795b.c()) {
                super.b(cVar);
            } else {
                this.f4795b.a();
                this.f4795b = null;
            }
        }
    }

    public final class m extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private c f4820b;

        public m(c cVar) {
            super();
            this.f4820b = cVar;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "UnblockLayerOnCompleteTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                this.f4820b.f(cVar);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("UnblockLayerOnCompleteTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class n extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private c f4822b;

        public n(c cVar) {
            super();
            this.f4822b = cVar;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "UnblockLayerOnStartTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                this.f4822b.g(cVar);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("UnblockLayerOnStartTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public SlideShowConductor(SODoc sODoc, SOPage sOPage, SlideShowConductorViewManager slideShowConductorViewManager) {
        this.f4790a = sODoc;
        this.f4791b = sOPage;
        this.i = slideShowConductorViewManager;
    }

    private void a(Date date) {
        ArrayList<c> arrayList;
        if (this.d || (arrayList = this.g) == null || arrayList.isEmpty()) {
            return;
        }
        ArrayList<c> arrayList2 = this.g;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            c cVar = arrayList2.get(i2);
            i2++;
            c cVar2 = cVar;
            if (cVar2.l != null && !a(cVar2)) {
                ArrayList arrayList3 = cVar2.m;
                int size2 = arrayList3.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj = arrayList3.get(i3);
                    i3++;
                    l lVar = (l) obj;
                    if (!lVar.f4817a) {
                        float fD = d(lVar.f4818b, date);
                        float f2 = lVar.c;
                        if (fD >= f2) {
                            float f3 = (fD - f2) / lVar.d;
                            boolean z = lVar.e;
                            if (f3 >= 1.0f) {
                                int i4 = lVar.f;
                                if (i4 <= 0 || f3 < i4) {
                                    if (lVar.g && f3 % 2.0f >= 1.0f) {
                                        z = !z;
                                    }
                                    f3 %= 1.0f;
                                } else {
                                    if (lVar.g && i4 % 2 == 0) {
                                        z = !z;
                                    }
                                    lVar.f4817a = true;
                                    f3 = 1.0f;
                                }
                            }
                            if (z) {
                                f3 = 1.0f - f3;
                            }
                            lVar.h = f3;
                            lVar.a(cVar2);
                        }
                    }
                }
                cVar2.a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (this.h == null && !a()) {
            this.i.animationsRunning();
            this.h = new Timer();
            this.h.scheduleAtFixedRate(new TimerTask() { // from class: com.artifex.sonui.editor.SlideShowConductor.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.artifex.sonui.editor.SlideShowConductor.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SlideShowConductor.this.f();
                        }
                    });
                }
            }, 0L, 33L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(Date date, Date date2) {
        float fD = d(date, date2);
        if (fD > 0.0f) {
            return 1;
        }
        return fD < 0.0f ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float d(Date date, Date date2) {
        return (date2.getTime() - date.getTime()) / 1000.0f;
    }

    private boolean e() {
        int i2 = this.e;
        SOAnimationCommand[] sOAnimationCommandArr = this.f;
        if (i2 >= sOAnimationCommandArr.length) {
            return true;
        }
        this.e = i2 + 1;
        SOAnimationCommand sOAnimationCommand = sOAnimationCommandArr[i2];
        c cVarC = c(sOAnimationCommand.f4190b);
        if (sOAnimationCommand instanceof SOAnimationRenderCommand) {
            b((SOAnimationRenderCommand) sOAnimationCommand, cVarC);
        } else if (sOAnimationCommand instanceof SOAnimationDisposeCommand) {
            a((SOAnimationDisposeCommand) sOAnimationCommand, cVarC);
        } else if (sOAnimationCommand instanceof SOAnimationWaitForTimeCommand) {
            a((SOAnimationWaitForTimeCommand) sOAnimationCommand, cVarC);
        } else if (sOAnimationCommand instanceof SOAnimationWaitForLayerCommand) {
            a((SOAnimationWaitForLayerCommand) sOAnimationCommand, cVarC);
        } else {
            if (sOAnimationCommand instanceof SOAnimationWaitForEventCommand) {
                a((SOAnimationWaitForEventCommand) sOAnimationCommand, cVarC);
                return true;
            }
            if (sOAnimationCommand instanceof SOAnimationPlotCommand) {
                a((SOAnimationPlotCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationSetVisibilityCommand) {
                a((SOAnimationSetVisibilityCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationSetPositionCommand) {
                a((SOAnimationSetPositionCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationSetOpacityCommand) {
                a((SOAnimationSetOpacityCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationSetTransformCommand) {
                a((SOAnimationSetTransformCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationMoveCommand) {
                a((SOAnimationMoveCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationFadeCommand) {
                a((SOAnimationFadeCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationScaleCommand) {
                a((SOAnimationScaleCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationRotateCommand) {
                a((SOAnimationRotateCommand) sOAnimationCommand, cVarC);
            } else if (sOAnimationCommand instanceof SOAnimationColourEffectCommand) {
                a((SOAnimationColourEffectCommand) sOAnimationCommand, cVarC);
            }
        }
        return this.e == this.f.length;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        a(new Date());
        g();
        if (a()) {
            c();
            int i2 = this.e;
            int length = this.f.length;
            SlideShowConductorViewManager slideShowConductorViewManager = this.i;
            if (i2 == length) {
                slideShowConductorViewManager.animationsCompleted();
            } else {
                slideShowConductorViewManager.animationsWaiting();
            }
        }
    }

    private void g() {
        ArrayList<c> arrayList = this.g;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            c cVar = arrayList.get(i2);
            i2++;
            cVar.b();
        }
    }

    public void endAllCurrentTasks() {
        ArrayList<c> arrayList = this.g;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            c cVar = arrayList.get(i2);
            i2++;
            cVar.g();
        }
    }

    public boolean getPaused() {
        return this.d;
    }

    public boolean getRunning() {
        return this.c;
    }

    public void pause() {
        if (this.d) {
            return;
        }
        this.d = true;
    }

    public void resume() {
        if (this.d) {
            this.d = false;
        }
    }

    public boolean sendEvent(int i2) {
        ArrayList<c> arrayList = this.g;
        if (arrayList == null) {
            return false;
        }
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            c cVar = arrayList.get(i3);
            i3++;
            if (cVar.b(i2)) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        if (this.c) {
            return;
        }
        SOAnimationCommand[] animations = this.f4791b.getAnimations();
        this.f = animations;
        if (animations == null || animations.length == 0) {
            return;
        }
        this.g = new ArrayList<>();
        this.c = true;
        this.d = false;
        d();
        this.i.animationsStarted();
    }

    public void stop() {
        if (this.c) {
            this.c = false;
            this.d = true;
            c();
            this.g.clear();
        }
    }

    public abstract class l {
        protected float c = 0.0f;
        protected float d = 0.0f;
        protected boolean e = false;
        protected int f = 1;
        protected boolean g = false;
        protected float h = 0.0f;

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private boolean f4817a = false;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private Date f4818b = new Date();

        public l() {
        }

        public abstract void a(c cVar);

        public void b(c cVar) {
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorTask.end(), layer: %d", Integer.valueOf(cVar.f4799b)));
            }
            if (this.g && this.f % 2 == 0) {
                this.e = !this.e;
            }
            this.h = this.e ? 0.0f : 1.0f;
            this.f4817a = true;
            a(cVar);
        }
    }

    public final class o {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final ArrayList<l> f4824b;
        private Date c;
        private c d;
        private c e;
        private int f;
        private Date g;

        private o() {
            this.f4824b = new ArrayList<>();
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = -1;
            this.g = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a() {
            return (this.c == null && this.d == null && this.e == null && this.f == -1) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(c cVar) {
            if (this.g == null) {
                this.g = new Date();
                this.d = cVar;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i) {
            if (this.g == null) {
                this.g = new Date();
                this.f = i;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(c cVar) {
            if (this.g == null) {
                this.g = new Date();
                this.e = cVar;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(l lVar) {
            this.f4824b.add(lVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Date date) {
            if (this.g == null) {
                this.g = new Date();
                this.c = date;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        while (!e()) {
        }
    }

    private c c(int i2) {
        c cVarB = b(i2);
        return cVarB == null ? a(i2) : cVarB;
    }

    private void c() {
        Timer timer = this.h;
        if (timer == null) {
            return;
        }
        timer.cancel();
        this.h = null;
    }

    public final class c {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final int f4799b;
        private boolean c = false;
        private final PointF d = new PointF();
        private int e = 0;
        private float f = 0.0f;
        private float g = 1.0f;
        private float h = 1.0f;
        private float i = 1.0f;
        private com.artifex.solib.i j = new com.artifex.solib.i();
        private final SOAnimationRenderCommand k = null;
        private SlideShowConductorView l = null;
        private final ArrayList<l> m = new ArrayList<>();
        private final ArrayList<o> n = new ArrayList<>();
        private boolean o = true;
        private boolean p = true;
        private boolean q = true;
        private boolean r = true;
        private boolean s = true;
        private boolean t = true;
        private boolean u = true;

        public c(int i) {
            this.f4799b = i;
        }

        private boolean c(int i) {
            if (this.n.isEmpty() || this.n.get(0).f != i) {
                return false;
            }
            this.n.get(0).f = -1;
            return true;
        }

        private void h() {
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "scheduleCommandConsumption()");
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.artifex.sonui.editor.SlideShowConductor.c.1
                @Override // java.lang.Runnable
                public void run() {
                    SlideShowConductor.this.d();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            if (d()) {
                return;
            }
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, String.format("unpause(), layer: %d", Integer.valueOf(this.f4799b)));
            }
            if (this.n.isEmpty()) {
                return;
            }
            o oVar = this.n.get(0);
            ArrayList arrayList = oVar.f4824b;
            if (arrayList.size() > 0) {
                float fD = SlideShowConductor.d(oVar.g, new Date());
                oVar.g = null;
                if (SlideShowConductor.this.k) {
                    Log.i(SlideShowConductor.this.j, "Copy deferred tasks over");
                }
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    l lVar = (l) obj;
                    lVar.f4818b = SlideShowConductor.b(lVar.f4818b, fD);
                }
                this.m.addAll(arrayList);
            }
            this.n.remove(0);
            if (SlideShowConductor.this.a()) {
                return;
            }
            SlideShowConductor.this.b();
        }

        private float j() {
            ArrayList<l> arrayList = !this.n.isEmpty() ? ((o) e6.g(1, this.n)).f4824b : this.m;
            int size = arrayList.size();
            int i = 0;
            float f = 0.0f;
            while (i < size) {
                l lVar = arrayList.get(i);
                i++;
                l lVar2 = lVar;
                float f2 = (lVar2.d * lVar2.f) + lVar2.c;
                if (f2 > f) {
                    f = f2;
                }
            }
            return f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void k(c cVar) {
            cVar.h(this);
            o oVar = new o();
            oVar.b(cVar);
            this.n.add(oVar);
        }

        public void a() {
            SlideShowConductorView slideShowConductorView;
            if ((this.o || this.p || this.q || this.r || this.s || this.t || this.u) && (slideShowConductorView = this.l) != null) {
                slideShowConductorView.begin();
                if (this.o) {
                    this.l.setVisibility(this.c);
                    this.o = false;
                }
                if (this.p) {
                    this.l.setOpacity(this.g);
                    this.p = false;
                }
                if (this.q) {
                    this.l.setPosition(this.d);
                    this.q = false;
                }
                if (this.r) {
                    this.l.setZPosition(this.e);
                    this.r = false;
                }
                if (this.s) {
                    this.l.setScale(this.h, this.i);
                    this.s = false;
                }
                if (this.t) {
                    this.l.setRotation(this.f);
                    this.t = false;
                }
                if (this.u) {
                    this.l.setTransform(this.j.a());
                    this.u = false;
                }
                this.l.commit();
            }
        }

        public boolean b(int i) {
            if (!c(i)) {
                return false;
            }
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "Layer.sendEvent()");
            }
            SlideShowConductor.this.endAllCurrentTasks();
            i();
            h();
            return true;
        }

        public boolean d() {
            return !this.n.isEmpty() && this.n.get(0).a();
        }

        public boolean e() {
            return (this.n.isEmpty() || this.n.get(0).f == -1) ? false : true;
        }

        public boolean f() {
            o oVar = this.n.isEmpty() ? null : this.n.get(0);
            if (oVar != null && oVar.c != null) {
                if (SlideShowConductor.c(oVar.c, new Date()) == 1) {
                    oVar.c = null;
                    return true;
                }
            }
            return false;
        }

        public void g() {
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, String.format("runCurrentTasksToEnd(), layer: %d", Integer.valueOf(this.f4799b)));
            }
            ArrayList<l> arrayList = this.m;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                l lVar = arrayList.get(i);
                i++;
                l lVar2 = lVar;
                if (!lVar2.f4817a) {
                    lVar2.b(this);
                }
            }
            b();
            ArrayList<o> arrayList2 = this.n;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                o oVar = arrayList2.get(i2);
                i2++;
                ArrayList arrayList3 = oVar.f4824b;
                int size3 = arrayList3.size();
                int i3 = 0;
                while (i3 < size3) {
                    Object obj = arrayList3.get(i3);
                    i3++;
                    ((l) obj).b(this);
                }
            }
            this.n.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(int i) {
            o oVar = new o();
            oVar.a(i);
            this.n.add(oVar);
        }

        private void h(c cVar) {
            m mVar = SlideShowConductor.this.new m(cVar);
            mVar.c = j();
            mVar.d = 0.001f;
            a(mVar);
        }

        public boolean c() {
            ArrayList<l> arrayList = this.m;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                l lVar = arrayList.get(i);
                i++;
                if (!lVar.f4817a) {
                    return true;
                }
            }
            return false;
        }

        public void b() {
            ArrayList arrayList = new ArrayList();
            ArrayList<l> arrayList2 = this.m;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                l lVar = arrayList2.get(i);
                i++;
                l lVar2 = lVar;
                if (lVar2.f4817a) {
                    arrayList.add(lVar2);
                }
            }
            this.m.removeAll(arrayList);
        }

        public void b(float f) {
            if (f == this.f) {
                return;
            }
            this.f = f;
            this.t = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(c cVar) {
            if (this.n.isEmpty() || cVar != this.n.get(0).d) {
                return;
            }
            this.n.get(0).d = null;
            i();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j(c cVar) {
            cVar.i(this);
            o oVar = new o();
            oVar.a(cVar);
            this.n.add(oVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g(c cVar) {
            if (this.n.isEmpty() || cVar != this.n.get(0).e) {
                return;
            }
            this.n.get(0).e = null;
            i();
        }

        public void a(float f) {
            if (f == this.g) {
                return;
            }
            this.g = f;
            this.p = true;
        }

        public void a(float f, float f2) {
            if (f == this.h && f2 == this.i) {
                return;
            }
            this.h = f;
            this.i = f2;
            this.s = true;
        }

        public void a(int i) {
            if (i == this.e) {
                return;
            }
            this.e = i;
            this.r = true;
        }

        public void a(PointF pointF) {
            if (pointF.equals(this.d)) {
                return;
            }
            PointF pointF2 = this.d;
            pointF2.x = pointF.x;
            pointF2.y = pointF.y;
            this.q = true;
        }

        public void a(com.artifex.solib.i iVar) {
            if (iVar.equals(this.j)) {
                return;
            }
            this.j = iVar;
            this.u = true;
        }

        public void a(l lVar) {
            if (d()) {
                ((o) e6.g(1, this.n)).a(lVar);
            } else {
                this.m.add(lVar);
            }
            SlideShowConductor.this.b();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Date date) {
            o oVar = new o();
            oVar.a(date);
            this.n.add(oVar);
        }

        public void a(boolean z) {
            if (z == this.c) {
                return;
            }
            this.c = z;
            this.o = true;
        }

        private void i(c cVar) {
            n nVar = SlideShowConductor.this.new n(cVar);
            nVar.c = 0.0f;
            nVar.d = 0.001f;
            a(nVar);
        }
    }

    public final class b extends l {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        SlideShowConductorView f4796a;
        private float j;
        private float k;
        private int l;
        private com.artifex.sonui.editor.a.h m;

        /* JADX WARN: Code duplicated, block: B:13:0x0061 A[PHI: r2
  0x0061: PHI (r2v12 com.artifex.sonui.editor.a.h) = (r2v3 com.artifex.sonui.editor.a.h), (r2v13 com.artifex.sonui.editor.a.h) binds: [B:36:0x0115, B:12:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Code duplicated, block: B:7:0x0033 A[PHI: r2
  0x0033: PHI (r2v16 com.artifex.sonui.editor.a.h) = 
  (r2v1 com.artifex.sonui.editor.a.h)
  (r2v2 com.artifex.sonui.editor.a.h)
  (r2v4 com.artifex.sonui.editor.a.h)
  (r2v5 com.artifex.sonui.editor.a.h)
  (r2v6 com.artifex.sonui.editor.a.h)
  (r2v7 com.artifex.sonui.editor.a.h)
  (r2v8 com.artifex.sonui.editor.a.h)
  (r2v9 com.artifex.sonui.editor.a.h)
  (r2v10 com.artifex.sonui.editor.a.h)
  (r2v14 com.artifex.sonui.editor.a.h)
  (r2v17 com.artifex.sonui.editor.a.h)
 binds: [B:43:0x0147, B:40:0x0130, B:33:0x00fe, B:30:0x00e7, B:27:0x00d0, B:24:0x00b9, B:21:0x00a3, B:18:0x008d, B:15:0x0077, B:9:0x0049, B:6:0x0031] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0019. Please report as an issue. */
        public b(SOAnimationFadeCommand sOAnimationFadeCommand, SlideShowConductorView slideShowConductorView) {
            com.artifex.sonui.editor.a.h aVar;
            com.artifex.sonui.editor.a.h gVar;
            super();
            this.m = null;
            this.j = 0.0f;
            this.k = 1.0f;
            int i = 0;
            this.l = 0;
            this.f4796a = slideShowConductorView;
            switch (sOAnimationFadeCommand.f4191a) {
                case 1:
                    aVar = new com.artifex.sonui.editor.a.a(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 2:
                    aVar = new com.artifex.sonui.editor.a.c(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 3:
                    gVar = new com.artifex.sonui.editor.a.g(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = gVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    gVar.b(i);
                    break;
                case 4:
                    aVar = new com.artifex.sonui.editor.a.j(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 5:
                    aVar = new com.artifex.sonui.editor.a.d(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 6:
                    aVar = new com.artifex.sonui.editor.a.b(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 7:
                    aVar = new com.artifex.sonui.editor.a.f(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 8:
                    aVar = new com.artifex.sonui.editor.a.i(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 9:
                    aVar = new com.artifex.sonui.editor.a.l(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 10:
                    aVar = new com.artifex.sonui.editor.a.m(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 11:
                    gVar = new com.artifex.sonui.editor.a.n(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = gVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    gVar.b(i);
                    break;
                case 12:
                    aVar = new com.artifex.sonui.editor.a.o(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
                case 13:
                    aVar = new p(sOAnimationFadeCommand.g, sOAnimationFadeCommand.i, (int) (sOAnimationFadeCommand.k * 1000.0f), slideShowConductorView);
                    this.m = aVar;
                    if (sOAnimationFadeCommand.e == 0.0f) {
                        i = 1;
                    }
                    aVar.b(i);
                    this.m.a(sOAnimationFadeCommand.c);
                    break;
            }
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorFadeTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            com.artifex.sonui.editor.a.h hVar = this.m;
            if (hVar == null || hVar.hasStarted() || this.m.hasEnded()) {
                int i = this.l;
                float f = this.j;
                cVar.a(SOAnimationEasings.a(i, f, this.k - f, this.h));
            } else {
                this.f4796a.startAnimation(this.m);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorFadeTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void b(c cVar) {
            com.artifex.sonui.editor.a.h hVar = this.m;
            if (hVar == null || !hVar.hasStarted() || this.m.hasEnded()) {
                super.b(cVar);
            } else {
                this.m.a();
                this.m = null;
            }
        }
    }

    private c b(int i2) {
        ArrayList<c> arrayList = this.g;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            c cVar = arrayList.get(i3);
            i3++;
            c cVar2 = cVar;
            if (cVar2.f4799b == i2) {
                return cVar2;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Date b(Date date, float f2) {
        return new Date(date.getTime() + ((long) (f2 * 1000.0f)));
    }

    private void b(SOAnimationRenderCommand sOAnimationRenderCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeRender(), layer: %d", Integer.valueOf(cVar.f4799b)));
        }
        a(sOAnimationRenderCommand, cVar);
    }

    public final class h extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private float f4810b;

        public h() {
            super();
            this.f4810b = 0.0f;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorSetOpacityTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                cVar.a(this.f4810b);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorSetOpacityTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class i extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private PointF f4812b;

        public i() {
            super();
            this.f4812b = new PointF();
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorSetPositionTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                cVar.a(this.f4812b);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorSetPositionTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class j extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private final com.artifex.solib.i f4814b;

        public j() {
            super();
            this.f4814b = new com.artifex.solib.i();
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorSetTransformTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                cVar.a(this.f4814b);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorSetTransformTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class k extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private boolean f4816b;

        public k() {
            super();
            this.f4816b = false;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorSetVisibilityTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                cVar.a(this.f4816b);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorSetVisibilityTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class f extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private PointF f4806b;
        private float j;
        private float k;
        private int l;

        public f() {
            super();
            this.f4806b = new PointF();
            this.j = 0.0f;
            this.k = 1.0f;
            this.l = 0;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorRotateTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            int i = this.l;
            float f = this.j;
            cVar.b(SOAnimationEasings.a(i, f, this.k - f, this.h));
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorRotateTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class g extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private float f4808b;
        private float j;
        private float k;
        private float l;
        private final PointF m;
        private int n;

        public g() {
            super();
            this.j = 0.0f;
            this.f4808b = 0.0f;
            this.l = 1.0f;
            this.k = 1.0f;
            this.m = new PointF();
            this.n = 0;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorScaleTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            int i = this.n;
            float f = this.f4808b;
            float fA = SOAnimationEasings.a(i, f, this.k - f, this.h);
            int i2 = this.n;
            float f2 = this.j;
            cVar.a(fA, SOAnimationEasings.a(i2, f2, this.l - f2, this.h));
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorScaleTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class e extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private PointF f4804b;
        private int j;

        public e() {
            super();
            this.f4804b = new PointF();
            this.j = 0;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorPlotTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            if (this.h >= 1.0f) {
                cVar.a(this.f4804b);
                cVar.a(true);
                cVar.a(this.j);
                SlideShowConductor.this.i.add(cVar.l);
            }
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorPlotTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    public final class d extends l {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private PointF f4802b;
        private PointF j;
        private int k;

        public d() {
            super();
            this.f4802b = new PointF();
            this.j = new PointF();
            this.k = 0;
            if (SlideShowConductor.this.k) {
                Log.i(SlideShowConductor.this.j, "SlideShowConductorMoveTask()");
            }
        }

        @Override // com.artifex.sonui.editor.SlideShowConductor.l
        public void a(c cVar) {
            int i = this.k;
            float f = this.f4802b.x;
            float fA = SOAnimationEasings.a(i, f, this.j.x - f, this.h);
            int i2 = this.k;
            float f2 = this.f4802b.y;
            cVar.a(new PointF(fA, SOAnimationEasings.a(i2, f2, this.j.y - f2, this.h)));
            if (SlideShowConductor.this.l) {
                Log.i(SlideShowConductor.this.j, String.format("SlideShowConductorMoveTask.execute(), progress:%f, layer: %d", Float.valueOf(this.h), Integer.valueOf(cVar.f4799b)));
            }
        }
    }

    private c a(int i2) {
        c cVar = new c(i2);
        this.g.add(cVar);
        return cVar;
    }

    private void a(SOAnimationColourEffectCommand sOAnimationColourEffectCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeColourEffect(), layer: %d, effect:%d", Integer.valueOf(cVar.f4799b), Integer.valueOf(sOAnimationColourEffectCommand.f4189a)));
        }
        a aVar = new a(sOAnimationColourEffectCommand, cVar.l);
        aVar.g = sOAnimationColourEffectCommand.i;
        aVar.e = sOAnimationColourEffectCommand.h;
        aVar.f = sOAnimationColourEffectCommand.g;
        aVar.c = sOAnimationColourEffectCommand.j;
        aVar.d = sOAnimationColourEffectCommand.k;
        cVar.a(aVar);
    }

    private void a(SOAnimationDisposeCommand sOAnimationDisposeCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeDispose(), layer: %d", Integer.valueOf(cVar.f4799b)));
        }
        if (cVar.l != null) {
            cVar.l.dispose();
            this.i.remove(cVar.l);
        }
    }

    private void a(SOAnimationFadeCommand sOAnimationFadeCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeFade(), layer: %d", Integer.valueOf(cVar.f4799b)));
        }
        b bVar = new b(sOAnimationFadeCommand, cVar.l);
        bVar.g = sOAnimationFadeCommand.i;
        bVar.e = sOAnimationFadeCommand.h;
        bVar.f = sOAnimationFadeCommand.g;
        bVar.c = sOAnimationFadeCommand.j;
        bVar.d = sOAnimationFadeCommand.k;
        bVar.j = sOAnimationFadeCommand.d;
        bVar.k = sOAnimationFadeCommand.e;
        bVar.l = sOAnimationFadeCommand.f;
        cVar.a(bVar);
    }

    private void a(SOAnimationMoveCommand sOAnimationMoveCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeMove(), layer: %d, x: %f, y:%f", Integer.valueOf(cVar.f4799b), Float.valueOf(sOAnimationMoveCommand.c.x), Float.valueOf(sOAnimationMoveCommand.c.y)));
        }
        d dVar = new d();
        dVar.g = sOAnimationMoveCommand.i;
        dVar.e = sOAnimationMoveCommand.h;
        dVar.f = sOAnimationMoveCommand.g;
        dVar.c = sOAnimationMoveCommand.j;
        dVar.d = sOAnimationMoveCommand.k;
        dVar.f4802b = sOAnimationMoveCommand.f4193a;
        dVar.j = sOAnimationMoveCommand.c;
        dVar.k = sOAnimationMoveCommand.d;
        cVar.a(dVar);
    }

    private void a(SOAnimationPlotCommand sOAnimationPlotCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumePlot(), layer: %d", Integer.valueOf(cVar.f4799b)));
        }
        e eVar = new e();
        eVar.c = sOAnimationPlotCommand.f4192a;
        eVar.d = 0.001f;
        eVar.f4804b = sOAnimationPlotCommand.c;
        eVar.j = sOAnimationPlotCommand.d;
        cVar.a(eVar);
    }

    private void a(SOAnimationRenderCommand sOAnimationRenderCommand, c cVar) {
        if (cVar.l != null) {
            cVar.l.dispose();
            this.i.remove(cVar.l);
        }
        PointF pointF = sOAnimationRenderCommand.d;
        cVar.l = this.i.newLayer(this.f4790a, this.f4791b, sOAnimationRenderCommand.f4194a, new PointF(pointF.x, pointF.y), new RectF(sOAnimationRenderCommand.e, sOAnimationRenderCommand.f, sOAnimationRenderCommand.g, sOAnimationRenderCommand.h));
        cVar.l.render();
    }

    private void a(SOAnimationRotateCommand sOAnimationRotateCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeRotate(), layer: %d, start: %f, end:%f", Integer.valueOf(cVar.f4799b), Float.valueOf(sOAnimationRotateCommand.c), Float.valueOf(sOAnimationRotateCommand.d)));
        }
        f fVar = new f();
        fVar.g = sOAnimationRotateCommand.i;
        fVar.e = sOAnimationRotateCommand.h;
        fVar.f = sOAnimationRotateCommand.g;
        fVar.c = sOAnimationRotateCommand.j;
        fVar.d = sOAnimationRotateCommand.k;
        fVar.j = sOAnimationRotateCommand.c;
        fVar.k = sOAnimationRotateCommand.d;
        fVar.f4806b = sOAnimationRotateCommand.f4195a;
        fVar.l = sOAnimationRotateCommand.e;
        cVar.a(fVar);
    }

    private void a(SOAnimationScaleCommand sOAnimationScaleCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeScale(), layer: %d, x-scale: %f, y-scale:%f", Integer.valueOf(cVar.f4799b), Float.valueOf(sOAnimationScaleCommand.d), Float.valueOf(sOAnimationScaleCommand.e)));
        }
        g gVar = new g();
        gVar.g = sOAnimationScaleCommand.i;
        gVar.e = sOAnimationScaleCommand.h;
        gVar.f = sOAnimationScaleCommand.g;
        gVar.c = sOAnimationScaleCommand.j;
        gVar.d = sOAnimationScaleCommand.k;
        gVar.f4808b = sOAnimationScaleCommand.f4196a;
        gVar.j = sOAnimationScaleCommand.c;
        gVar.k = sOAnimationScaleCommand.d;
        gVar.l = sOAnimationScaleCommand.e;
        gVar.n = sOAnimationScaleCommand.l;
        cVar.a(gVar);
    }

    private void a(SOAnimationSetOpacityCommand sOAnimationSetOpacityCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeSetOpacity(), layer: %d, visible: %f", Integer.valueOf(cVar.f4799b), Float.valueOf(sOAnimationSetOpacityCommand.c)));
        }
        h hVar = new h();
        hVar.c = sOAnimationSetOpacityCommand.f4192a;
        hVar.d = 0.001f;
        hVar.f4810b = sOAnimationSetOpacityCommand.c;
        cVar.a(hVar);
    }

    private void a(SOAnimationSetPositionCommand sOAnimationSetPositionCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeSetPosition(), layer: %d, x: %f, y:%f", Integer.valueOf(cVar.f4799b), Float.valueOf(sOAnimationSetPositionCommand.c.x), Float.valueOf(sOAnimationSetPositionCommand.c.y)));
        }
        i iVar = new i();
        iVar.c = sOAnimationSetPositionCommand.f4192a;
        iVar.d = 0.001f;
        iVar.f4812b = sOAnimationSetPositionCommand.c;
        cVar.a(iVar);
    }

    private void a(SOAnimationSetTransformCommand sOAnimationSetTransformCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeSetTransform(), layer: %d", Integer.valueOf(cVar.f4799b)));
        }
        j jVar = new j();
        jVar.f4814b.f4238a = sOAnimationSetTransformCommand.c;
        jVar.f4814b.f4239b = sOAnimationSetTransformCommand.d;
        jVar.f4814b.c = sOAnimationSetTransformCommand.e;
        jVar.f4814b.d = sOAnimationSetTransformCommand.f;
        jVar.f4814b.e = sOAnimationSetTransformCommand.g;
        jVar.f4814b.f = sOAnimationSetTransformCommand.h;
        cVar.a(jVar);
    }

    private void a(SOAnimationSetVisibilityCommand sOAnimationSetVisibilityCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeSetVisibility(), layer: %d, visible: %b", Integer.valueOf(cVar.f4799b), Boolean.valueOf(sOAnimationSetVisibilityCommand.c)));
        }
        k kVar = new k();
        kVar.c = sOAnimationSetVisibilityCommand.f4192a;
        kVar.d = 0.001f;
        kVar.f4816b = sOAnimationSetVisibilityCommand.c;
        cVar.a(kVar);
    }

    private void a(SOAnimationWaitForEventCommand sOAnimationWaitForEventCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeWaitForEvent(), layer: %d, event: %d", Integer.valueOf(cVar.f4799b), Integer.valueOf(sOAnimationWaitForEventCommand.f4197a)));
        }
        cVar.d(sOAnimationWaitForEventCommand.f4197a);
    }

    private void a(SOAnimationWaitForLayerCommand sOAnimationWaitForLayerCommand, c cVar) {
        if (this.k) {
            Log.i(this.j, String.format("consumeWaitForLayer(), this layer: %d, waitee:%d, whence: %d", Integer.valueOf(cVar.f4799b), Integer.valueOf(sOAnimationWaitForLayerCommand.f4190b), Integer.valueOf(sOAnimationWaitForLayerCommand.c)));
        }
        int i2 = sOAnimationWaitForLayerCommand.c;
        c cVarC = c(sOAnimationWaitForLayerCommand.f4198a);
        if (i2 == 1) {
            cVar.k(cVarC);
        } else {
            cVar.j(cVarC);
        }
    }

    private void a(SOAnimationWaitForTimeCommand sOAnimationWaitForTimeCommand, c cVar) {
        Date date = new Date();
        if (this.k) {
            Log.i(this.j, String.format("consumeWaitForTime(), layer: %d, time: %f", Integer.valueOf(cVar.f4799b), Float.valueOf(sOAnimationWaitForTimeCommand.f4199a)));
        }
        cVar.a(b(date, sOAnimationWaitForTimeCommand.f4199a));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        ArrayList<c> arrayList = this.g;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            c cVar = arrayList.get(i2);
            i2++;
            c cVar2 = cVar;
            if (cVar2.m.size() > 0 || (cVar2.n.size() > 0 && !cVar2.e())) {
                return false;
            }
        }
        return true;
    }

    private boolean a(c cVar) {
        if (cVar.c() || !cVar.d()) {
            return false;
        }
        if (!cVar.f()) {
            return true;
        }
        cVar.i();
        return cVar.d();
    }
}
