package com.hwa.excelllll.sonui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.a.o.Res;
import com.artifex.sonui.editor.ToolbarButton;

/* JADX INFO: loaded from: classes.dex */
public class SortOrderMenu extends LinearLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    boolean f4479a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    int f4480b;
    private ToolbarButton c;
    private ToolbarButton d;
    private ToolbarButton e;
    private ToolbarButton f;
    private int g;
    private View h;
    private SortOrderListener i;

    public interface SortOrderListener {
        void a(int i);
    }

    public SortOrderMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = 1;
        this.f4479a = false;
        this.f4480b = 300;
        this.i = null;
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(Res.i("sodk_sort_order_menu"), this);
        ToolbarButton toolbarButton = (ToolbarButton) findViewById(Res.g("sort_name_button"));
        this.c = toolbarButton;
        toolbarButton.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.SortOrderMenu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                sortOrderMenu.a(sortOrderMenu.c);
            }
        });
        ToolbarButton toolbarButton2 = this.c;
        Resources resources = getResources();
        int iD = Res.d("sodk_sort_button_icon_color");
        toolbarButton2.setDrawableColor(resources.getColor(iD));
        ToolbarButton toolbarButton3 = (ToolbarButton) findViewById(Res.g("sort_date_button"));
        this.d = toolbarButton3;
        toolbarButton3.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.SortOrderMenu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                sortOrderMenu.a(sortOrderMenu.d);
            }
        });
        this.d.setDrawableColor(getResources().getColor(iD));
        ToolbarButton toolbarButton4 = (ToolbarButton) findViewById(Res.g("sort_type_button"));
        this.e = toolbarButton4;
        toolbarButton4.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.SortOrderMenu.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                sortOrderMenu.a(sortOrderMenu.e);
            }
        });
        this.e.setDrawableColor(getResources().getColor(iD));
        ToolbarButton toolbarButton5 = (ToolbarButton) findViewById(Res.g("sort_size_button"));
        this.f = toolbarButton5;
        toolbarButton5.setOnClickListener(new OnClickListener() { // from class: com.artifex.sonui.SortOrderMenu.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                sortOrderMenu.a(sortOrderMenu.f);
            }
        });
        this.f.setDrawableColor(getResources().getColor(iD));
        View viewFindViewById = findViewById(Res.g("sort_tab"));
        this.h = viewFindViewById;
        viewFindViewById.setOnTouchListener(new a(context) { // from class: com.artifex.sonui.SortOrderMenu.5
            @Override // com.artifex.sonui.SortOrderMenu.a
            public void a() {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                if (sortOrderMenu.f4479a) {
                    return;
                }
                sortOrderMenu.a();
            }

            @Override // com.artifex.sonui.SortOrderMenu.a
            public void b() {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                if (sortOrderMenu.f4479a) {
                    sortOrderMenu.a();
                }
            }

            @Override // com.artifex.sonui.SortOrderMenu.a
            public void c() {
                SortOrderMenu sortOrderMenu = SortOrderMenu.this;
                if (sortOrderMenu.f4479a) {
                    return;
                }
                sortOrderMenu.a();
            }
        });
    }

    private void b(int i) {
        animate().translationX(0.0f).setDuration(i);
        this.f4479a = true;
    }

    private void setNewState(ToolbarButton toolbarButton) {
        String str;
        ToolbarButton toolbarButton2;
        String str2;
        String str3;
        String str4;
        ToolbarButton toolbarButton3 = this.c;
        int iD = Res.d("sodk_sort_button_icon_color");
        if (toolbarButton == toolbarButton3) {
            if (this.g == 1) {
                this.g = 2;
                str4 = "sodk_icon_sort_by_name_desc";
            } else {
                this.g = 1;
                str4 = "sodk_icon_sort_by_name_asc";
            }
            toolbarButton3.setImageResource(Res.f(str4));
            toolbarButton2 = this.c;
        } else {
            ToolbarButton toolbarButton4 = this.d;
            if (toolbarButton == toolbarButton4) {
                if (this.g == 3) {
                    this.g = 4;
                    str3 = "sodk_icon_sort_by_date_desc";
                } else {
                    this.g = 3;
                    str3 = "sodk_icon_sort_by_date_asc";
                }
                toolbarButton4.setImageResource(Res.f(str3));
                toolbarButton2 = this.d;
            } else {
                ToolbarButton toolbarButton5 = this.e;
                if (toolbarButton == toolbarButton5) {
                    if (this.g == 5) {
                        this.g = 6;
                        str2 = "sodk_icon_sort_by_type_desc";
                    } else {
                        this.g = 5;
                        str2 = "sodk_icon_sort_by_type_asc";
                    }
                    toolbarButton5.setImageResource(Res.f(str2));
                    toolbarButton2 = this.e;
                } else {
                    ToolbarButton toolbarButton6 = this.f;
                    if (toolbarButton != toolbarButton6) {
                        return;
                    }
                    if (this.g == 7) {
                        this.g = 8;
                        str = "sodk_icon_sort_by_size_desc";
                    } else {
                        this.g = 7;
                        str = "sodk_icon_sort_by_size_asc";
                    }
                    toolbarButton6.setImageResource(Res.f(str));
                    toolbarButton2 = this.f;
                }
            }
        }
        toolbarButton2.setDrawableColor(getResources().getColor(iD));
    }

    public void setSortOrderListener(SortOrderListener sortOrderListener) {
        this.i = sortOrderListener;
    }

    public class a implements OnTouchListener {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final GestureDetector f4486a;

        /* JADX INFO: renamed from: com.artifex.sonui.SortOrderMenu$a$a, reason: collision with other inner class name */
        public final class C0042a extends GestureDetector.SimpleOnGestureListener {
            private C0042a() {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                float x = motionEvent2.getX() - motionEvent.getX();
                if (Math.abs(x) <= Math.abs(motionEvent2.getY() - motionEvent.getY()) || Math.abs(x) <= 20.0f || Math.abs(f) <= 100.0f) {
                    return false;
                }
                a aVar = a.this;
                if (x > 0.0f) {
                    aVar.b();
                    return true;
                }
                aVar.a();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                a.this.c();
                return true;
            }
        }

        public a(Context context) {
            this.f4486a = new GestureDetector(context, new C0042a());
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return this.f4486a.onTouchEvent(motionEvent);
        }

        public void a() {
        }

        public void b() {
        }

        public void c() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        boolean z = this.f4479a;
        int i = this.f4480b;
        if (z) {
            a(i);
        } else {
            b(i);
        }
    }

    public void a(int i) {
        animate().translationX(getWidth() - this.h.getWidth()).setDuration(i);
        this.f4479a = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ToolbarButton toolbarButton) {
        ToolbarButton toolbarButton2 = this.c;
        toolbarButton2.setSelected(toolbarButton == toolbarButton2);
        ToolbarButton toolbarButton3 = this.d;
        toolbarButton3.setSelected(toolbarButton == toolbarButton3);
        ToolbarButton toolbarButton4 = this.e;
        toolbarButton4.setSelected(toolbarButton == toolbarButton4);
        ToolbarButton toolbarButton5 = this.f;
        toolbarButton5.setSelected(toolbarButton == toolbarButton5);
        setNewState(toolbarButton);
        SortOrderListener sortOrderListener = this.i;
        if (sortOrderListener != null) {
            sortOrderListener.a(this.g);
        }
    }
}
