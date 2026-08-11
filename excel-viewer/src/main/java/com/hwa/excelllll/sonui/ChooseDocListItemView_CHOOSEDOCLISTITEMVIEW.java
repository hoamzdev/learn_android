package com.hwa.excelllll.sonui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.a.o.Res;
import com.artifex.solib.ConfigOptions;

/* JADX INFO: loaded from: classes.dex */
public class ChooseDocListItemView extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private float f4372a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f4373b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;

    public ChooseDocListItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4373b = false;
        this.c = false;
        this.d = false;
        this.e = true;
        this.f = true;
        b();
    }

    private void a(View view, ViewGroup viewGroup) {
        a(viewGroup);
        final View viewFindViewById = view.findViewById(Res.g("controls"));
        f fVar = (f) getTag();
        if (!fVar.f4938a.d() || fVar.f4938a.h()) {
            boolean zH = fVar.f4938a.h();
            int iG = Res.g("control_logout");
            int iG2 = Res.g("control_share");
            int iG3 = Res.g("control_rename");
            int iG4 = Res.g("control_delete");
            int iG5 = Res.g("control_copy");
            if (zH) {
                findViewById(iG5).setVisibility(8);
                findViewById(iG4).setVisibility(8);
                findViewById(iG3).setVisibility(8);
                findViewById(iG2).setVisibility(8);
                findViewById(iG).setVisibility(0);
            } else {
                ConfigOptions configOptionsA = ConfigOptions.a();
                findViewById(iG5).setVisibility(0);
                findViewById(iG4).setVisibility(0);
                findViewById(iG3).setVisibility(0);
                findViewById(iG2).setVisibility(configOptionsA.h() ? 0 : 8);
                findViewById(iG).setVisibility(8);
            }
            viewFindViewById.setTranslationX(viewGroup.getWidth());
            viewFindViewById.animate().translationXBy(-viewGroup.getWidth()).setDuration(getContext().getResources().getInteger(Res.h("sodk_editor_explorer_swipe_controls_duration"))).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() { // from class: com.artifex.sonui.ChooseDocListItemView.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    viewFindViewById.setVisibility(0);
                }
            });
        }
    }

    private boolean b(ViewGroup viewGroup) {
        ListView listView = (ListView) viewGroup;
        int childCount = listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (listView.getChildAt(i).findViewById(Res.g("controls")).getVisibility() == 0) {
                return true;
            }
        }
        return false;
    }

    private void c() {
        final f fVar = (f) getTag();
        final ListView listView = (ListView) getParent();
        if (this.d) {
            setHighlight(false);
            listView.performItemClick(this, fVar.f4939b, 0L);
        } else {
            setHighlight(true);
            new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.ChooseDocListItemView.3
                @Override // java.lang.Runnable
                public void run() {
                    this.setHighlight(false);
                    listView.performItemClick(this, fVar.f4939b, 0L);
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHighlight(boolean z) {
        Context context;
        String str;
        this.d = false;
        if (z) {
            context = getContext();
            str = "sodk_so_ui_doc_list_highlight";
        } else {
            context = getContext();
            str = "sodk_so_ui_doc_list_unhighlight";
        }
        setBackgroundColor(context.getColor(Res.d(str)));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ListView listView = (ListView) getParent();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f4372a = motionEvent.getX();
            this.f4373b = false;
            this.c = true;
            this.e = true;
            new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.ChooseDocListItemView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!ChooseDocListItemView.this.c || ChooseDocListItemView.this.f4373b) {
                        return;
                    }
                    ChooseDocListItemView.this.setHighlight(true);
                    ChooseDocListItemView.this.e = false;
                }
            }, 100L);
        } else if (action == 1) {
            if (this.f4373b) {
                setHighlight(false);
            } else if (this.c) {
                if (b(listView)) {
                    a(listView);
                    setHighlight(false);
                } else {
                    c();
                }
            }
            this.c = false;
            this.e = true;
        } else if (action == 2) {
            float x = motionEvent.getX() - this.f4372a;
            if (this.e && !this.f4373b && Math.abs(x) > 50.0f) {
                this.f4373b = true;
            }
            boolean z = this.f4373b;
            if (z && x < 0.0f && this.f) {
                this.e = false;
                a(this, (ListView) getParent());
            } else if (z && x > 0.0f) {
                a(listView);
                setHighlight(false);
            }
        } else if (action != 3) {
            Log.i("ChooseDocListItemView", "DEFAULT: " + motionEvent.getAction());
        } else {
            this.f4373b = false;
            this.c = false;
            this.e = true;
            setHighlight(false);
        }
        return true;
    }

    public void setUseControls(boolean z) {
        this.f = z;
    }

    private void b() {
        this.f4372a = 0.0f;
        this.f4373b = false;
        this.c = false;
        this.e = true;
        setHighlight(false);
    }

    public void a() {
        b();
        findViewById(Res.g("controls")).setVisibility(8);
    }

    public static void a(ViewGroup viewGroup) {
        ListView listView = (ListView) viewGroup;
        int childCount = listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            listView.getChildAt(i).findViewById(Res.g("controls")).setVisibility(8);
        }
    }
}
