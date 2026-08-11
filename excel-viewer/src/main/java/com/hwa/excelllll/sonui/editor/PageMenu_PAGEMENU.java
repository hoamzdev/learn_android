package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public class PageMenu implements PopupWindow.OnDismissListener {
    private static PageMenu e;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f4720a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final View f4721b;
    private ActionListener c;
    private boolean d;
    private NUIPopupWindow f;

    public interface ActionListener {
        void onDelete();

        void onDuplicate();
    }

    public PageMenu(Context context, View view, boolean z, ActionListener actionListener) {
        this.f4720a = context;
        this.f4721b = view;
        this.c = actionListener;
        this.d = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.f.dismiss();
        e = null;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        a();
    }

    public void show() {
        View viewFindViewById;
        e = this;
        View viewInflate = LayoutInflater.from(this.f4720a).inflate(R.layout.sodk_editor_page_menu, (ViewGroup) null);
        ((Button) viewInflate.findViewById(R.id.delete_button)).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.PageMenu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PageMenu.this.a();
                PageMenu.this.c.onDelete();
            }
        });
        ((Button) viewInflate.findViewById(R.id.duplicate_button)).setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.PageMenu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PageMenu.this.a();
                PageMenu.this.c.onDuplicate();
            }
        });
        if (!this.d && (viewFindViewById = viewInflate.findViewById(R.id.delete_button_wrapper)) != null) {
            viewFindViewById.setVisibility(8);
        }
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
        this.f = nUIPopupWindow;
        nUIPopupWindow.setFocusable(true);
        this.f.setOnDismissListener(this);
        viewInflate.measure(0, 0);
        this.f.showAsDropDown(this.f4721b, 0, (-this.f4721b.getHeight()) - viewInflate.getMeasuredHeight());
    }
}
