package com.hwa.excelllll.sonui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public class ExplorerListView extends ListView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private float f4473a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private float f4474b;

    public ExplorerListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4473a = 0.0f;
        this.f4474b = 0.0f;
        a(context);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f4473a = motionEvent.getX();
            this.f4474b = motionEvent.getY();
        } else if (action == 2) {
            if (Math.abs(motionEvent.getY() - this.f4474b) < Math.abs(motionEvent.getX() - this.f4473a)) {
                return false;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private void a(Context context) {
    }
}
