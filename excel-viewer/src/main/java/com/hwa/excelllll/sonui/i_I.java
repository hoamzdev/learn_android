package com.hwa.excelllll.sonui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.supportv1.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.a.o.Res;
import com.artifex.solib.SOBitmap;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;
import com.artifex.solib.SOPageListener;
import com.artifex.solib.SORender;
import com.artifex.solib.o;
import com.artifex.sonui.editor.SODocSession;
import com.artifex.sonui.editor.SOFileDatabase;
import com.artifex.sonui.editor.SOFileState;
import com.artifex.sonui.editor.Utilities;
import defpackage.e6;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/* JADX INFO: loaded from: classes.dex */
public class i extends RecyclerView.Adapter<d> {
    private SORender e;
    private b g;
    private c h;
    private final RecyclerView i;
    private Activity j;
    private a k;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private SODocSession f4971a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private ArrayList<SOPage> f4972b = new ArrayList<>();
    private ArrayList<Bitmap> c = new ArrayList<>();
    private Queue<Integer> d = new LinkedList();
    private boolean f = false;
    private int l = 6;
    private double m = 2.0d;
    private int n = 0;
    private int o = 0;
    private int p = 0;

    public interface a {
        void a();

        void a(int i, int i2);
    }

    public interface b {
        void a(SODocSession sODocSession, int i);
    }

    public interface c {
        void a(int i, int i2, int i3);
    }

    public class d extends RecyclerView.ViewHolder implements View.OnClickListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final ImageView f4983a;

        public d(View view) {
            super(view);
            this.f4983a = (ImageView) view.findViewById(Res.g("preview_item_image"));
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (i.this.g != null) {
                i.this.g.a(i.this.f4971a, adapterPosition + 1);
            }
        }
    }

    public class e {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f4985a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f4986b;
        int c;

        private e() {
        }
    }

    public i(RecyclerView recyclerView, b bVar, c cVar, a aVar) {
        this.g = null;
        this.h = null;
        this.k = null;
        this.j = (Activity) recyclerView.getContext();
        this.g = bVar;
        this.h = cVar;
        this.i = recyclerView;
        this.k = aVar;
        recyclerView.getItemAnimator().getClass();
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() { // from class: com.artifex.sonui.i.1
            public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
                ((d) viewHolder).f4983a.setImageBitmap(null);
            }
        });
        new RecyclerView.OnChildAttachStateChangeListener() { // from class: com.artifex.sonui.i.2
            public void onChildViewAttachedToWindow(View view) {
                i.this.d();
            }

            public void onChildViewDetachedFromWindow(View view) {
                i.this.d();
            }
        };
        throw null;
    }

    private e i() {
        int iWidth;
        MyLinearLayoutManager myLinearLayoutManager = (MyLinearLayoutManager) this.i.getLayoutManager();
        if (myLinearLayoutManager.a() == null) {
            myLinearLayoutManager.a(new MyLinearLayoutManager.a() { // from class: com.artifex.sonui.i.3
                @Override // com.artifex.sonui.MyLinearLayoutManager.a
                public void a() {
                    i.this.d();
                }
            });
        }
        int iFindFirstCompletelyVisibleItemPosition = myLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
        if (iFindFirstCompletelyVisibleItemPosition == -1) {
            iFindFirstCompletelyVisibleItemPosition = myLinearLayoutManager.findFirstVisibleItemPosition();
        }
        int iFindLastCompletelyVisibleItemPosition = myLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        if (iFindLastCompletelyVisibleItemPosition == -1) {
            iFindLastCompletelyVisibleItemPosition = myLinearLayoutManager.findLastVisibleItemPosition();
        }
        int itemCount = myLinearLayoutManager.getItemCount();
        if (iFindFirstCompletelyVisibleItemPosition == -1) {
            iFindFirstCompletelyVisibleItemPosition = this.n;
        }
        if (iFindLastCompletelyVisibleItemPosition == -1) {
            iFindLastCompletelyVisibleItemPosition = this.o;
        }
        if (iFindFirstCompletelyVisibleItemPosition >= 0 && iFindLastCompletelyVisibleItemPosition >= 0 && iFindLastCompletelyVisibleItemPosition == iFindFirstCompletelyVisibleItemPosition + 1) {
            Rect rect = new Rect();
            View viewFindViewByPosition = myLinearLayoutManager.findViewByPosition(iFindFirstCompletelyVisibleItemPosition);
            int iWidth2 = 0;
            if (viewFindViewByPosition != null) {
                viewFindViewByPosition.getGlobalVisibleRect(rect);
                iWidth = rect.width();
            } else {
                iWidth = 0;
            }
            View viewFindViewByPosition2 = myLinearLayoutManager.findViewByPosition(iFindLastCompletelyVisibleItemPosition);
            if (viewFindViewByPosition2 != null) {
                viewFindViewByPosition2.getGlobalVisibleRect(rect);
                iWidth2 = rect.width();
            }
            if (iWidth2 > iWidth) {
                iFindFirstCompletelyVisibleItemPosition = iFindLastCompletelyVisibleItemPosition;
            }
        }
        e eVar = new e();
        eVar.f4985a = iFindFirstCompletelyVisibleItemPosition;
        eVar.f4986b = iFindLastCompletelyVisibleItemPosition;
        eVar.c = itemCount;
        return eVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        Integer numPoll;
        if (this.f && this.e == null && (numPoll = this.d.poll()) != null) {
            final int iIntValue = numPoll.intValue();
            SOPage sOPage = this.f4972b.get(iIntValue);
            Point pointSizeAtZoom = sOPage.sizeAtZoom(1.0d);
            int i = pointSizeAtZoom.y;
            int i2 = pointSizeAtZoom.x;
            int i3 = this.l;
            if (i > i2 * i3) {
                pointSizeAtZoom.y = i2 * i3;
            }
            int i4 = pointSizeAtZoom.y;
            if (i2 > i4 * i3) {
                pointSizeAtZoom.x = i4 * i3;
            }
            double height = ((LinearLayout) this.i.getParent()).getHeight();
            Double.isNaN(height);
            Double.isNaN(height);
            Double.isNaN(height);
            double d2 = pointSizeAtZoom.y;
            Double.isNaN(d2);
            double dZ = e6.z(d2, d2, (height * 8.0d) / 10.0d, d2) / this.m;
            double d3 = pointSizeAtZoom.y;
            Double.isNaN(d3);
            pointSizeAtZoom.y = (int) e6.a(d3, d3, d3, dZ);
            double d4 = pointSizeAtZoom.x;
            Double.isNaN(d4);
            int iA = (int) e6.a(d4, d4, d4, dZ);
            pointSizeAtZoom.x = iA;
            if (iA <= 0 || pointSizeAtZoom.y <= 0) {
                System.out.println(String.format("PreviewAdapter encountered a bad page size for page %d, retrying.", numPoll));
                this.d.add(numPoll);
            } else {
                final SOBitmap sOBitmapA = com.artifex.solib.k.a(this.f4971a.getUserPath(), pointSizeAtZoom.x, pointSizeAtZoom.y);
                this.e = sOPage.a(dZ, new PointF(0.0f, 0.0f), sOBitmapA, new o() { // from class: com.artifex.sonui.i.4
                    @Override // com.artifex.solib.o
                    public void a(int i5) {
                        if (!i.this.f) {
                            return;
                        }
                        Bitmap bitmapA = sOBitmapA.a();
                        i.this.c.ensureCapacity(iIntValue + 1);
                        while (true) {
                            int size = i.this.c.size();
                            int i6 = iIntValue + 1;
                            i iVar = i.this;
                            if (size >= i6) {
                                iVar.c.add(iIntValue, bitmapA);
                                i.this.j.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.i.4.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                        i.this.notifyItemChanged(iIntValue);
                                        i.this.e = null;
                                        i.this.j();
                                    }
                                });
                                return;
                            }
                            iVar.c.add(null);
                        }
                    }
                });
            }
        }
    }

    @Override // android.supportv1.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onBindViewHolder(d dVar, int i) {
        SOFileState sOFileStateStateForPath;
        Bitmap bitmap;
        if (this.f) {
            d();
            ImageView imageView = dVar.f4983a;
            if (i >= this.c.size() || (bitmap = this.c.get(i)) == null) {
                this.d.add(Integer.valueOf(i));
                j();
                if (i != 0 || this.f4971a == null || (sOFileStateStateForPath = SOFileDatabase.getDatabase().stateForPath(this.f4971a.getUserPath(), false)) == null) {
                    return;
                }
                this.f4971a.createThumbnail(sOFileStateStateForPath);
                return;
            }
            imageView.setImageBitmap(bitmap);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            double width = bitmap.getWidth();
            double d2 = this.m;
            Double.isNaN(width);
            layoutParams.width = (int) e6.a(width, width, width, d2);
            ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
            double height = bitmap.getHeight();
            double d3 = this.m;
            Double.isNaN(height);
            layoutParams2.height = (int) e6.a(height, height, height, d3);
        }
    }

    public int c() {
        return i().f4985a;
    }

    public void d() {
        e eVarI = i();
        int i = eVarI.f4985a;
        if ((i == -1 || i == this.n) && eVarI.f4986b == this.o && this.p == eVarI.c) {
            return;
        }
        this.n = i;
        int i2 = eVarI.f4986b;
        this.o = i2;
        int i3 = eVarI.c;
        this.p = i3;
        this.h.a(i + 1, i2 + 1, i3);
    }

    public void e() {
        this.c = new ArrayList<>();
        this.d = new LinkedList();
        notifyDataSetChanged();
    }

    public SODoc f() {
        SODocSession sODocSession = this.f4971a;
        if (sODocSession == null) {
            return null;
        }
        return sODocSession.getDoc();
    }

    public void g() {
        this.f = false;
        this.d.clear();
        SORender sORender = this.e;
        if (sORender != null) {
            sORender.abort();
            this.e.destroy();
            this.e = null;
        }
        for (int i = 0; i < this.c.size(); i++) {
            this.c.remove(i);
        }
        int size = this.f4972b.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.f4972b.get(0).m();
            this.f4972b.remove(0);
        }
        if (f() != null) {
            f().M();
        }
        notifyItemRangeRemoved(0, size);
    }

    @Override // android.supportv1.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.f) {
            return this.f4972b.size();
        }
        return 0;
    }

    public void h() {
        SODocSession sODocSession = this.f4971a;
        if (sODocSession != null) {
            sODocSession.abort();
            this.f4971a.destroy();
            this.f4971a = null;
        }
    }

    public boolean b() {
        return this.f;
    }

    public SODocSession a() {
        return this.f4971a;
    }

    @Override // android.supportv1.v7.widget.RecyclerView.Adapter
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public d onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new d(LayoutInflater.from(viewGroup.getContext()).inflate(Res.i("sodk_preview_item"), (ViewGroup) null));
    }

    public void a(final Activity activity, String str) {
        this.f4972b = new ArrayList<>();
        this.c = new ArrayList<>();
        this.d = new LinkedList();
        SODocSession sODocSession = new SODocSession(activity, com.artifex.solib.k.a(activity, str));
        this.f4971a = sODocSession;
        sODocSession.setSODocSessionLoadListener(new SODocSession.SODocSessionLoadListener() { // from class: com.artifex.sonui.i.5
            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
            public void onCancel() {
                i.this.g();
                if (i.this.k != null) {
                    i.this.k.a();
                }
            }

            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
            public void onDocComplete() {
                if (!i.this.f || i.this.f4972b.size() > 0) {
                    return;
                }
                String openErrorDescription = Utilities.getOpenErrorDescription(activity, 17);
                Activity activity2 = activity;
                Utilities.showMessage(activity2, activity2.getString(Res.j("sodk_editor_error")), openErrorDescription);
            }

            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
            public void onError(int i, int i2) {
                if (i.this.k != null) {
                    i.this.k.a(i, i2);
                }
            }

            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
            public void onPageLoad(int i) {
                if (!i.this.f) {
                    return;
                }
                final int size = i.this.f4972b.size();
                while (true) {
                    int i2 = size + 1;
                    if (i2 > i) {
                        return;
                    }
                    i.this.f4972b.add(size, i.this.f4971a.getDoc().getPage(size, new SOPageListener() { // from class: com.artifex.sonui.i.5.1
                        @Override // com.artifex.solib.SOPageListener
                        public void update(RectF rectF) {
                            i.this.d.add(Integer.valueOf(size));
                            i.this.j();
                        }
                    }));
                    i.this.c.add(size, null);
                    i.this.d();
                    i.this.notifyItemInserted(size);
                    size = i2;
                }
            }

            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
            public void onLayoutCompleted() {
            }

            @Override // com.artifex.sonui.editor.SODocSession.SODocSessionLoadListener
            public void onSelectionChanged(int i, int i2) {
            }
        });
        this.f4971a.open(str);
        this.f = this.f4971a.isOpen();
    }
}
