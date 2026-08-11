package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.artifex.solib.k;

/* JADX INFO: loaded from: classes.dex */
public class SlideShowActivity extends BaseActivity implements SlideShowViewListener {
    private static SODocSession d;
    private SODocSession c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f4785a = false;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private String f4786b = "SlideShowActivity";
    private SlideShowView e = null;

    public class a extends GestureDetector.SimpleOnGestureListener {
        public a() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            float y = motionEvent2.getY();
            float y2 = motionEvent.getY();
            float x = motionEvent2.getX() - motionEvent.getX();
            if (Math.abs(x) <= Math.abs(y - y2) || Math.abs(x) <= 100.0f || Math.abs(f) <= 100.0f) {
                return true;
            }
            SlideShowView slideShowView = SlideShowActivity.this.e;
            if (x > 0.0f) {
                slideShowView.goBack();
                return true;
            }
            slideShowView.goForward();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            SlideShowActivity.this.finish();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            SlideShowActivity.this.e.goForward();
            return false;
        }
    }

    public static void setSession(SODocSession sODocSession) {
        d = sODocSession;
    }

    @Override // android.app.Activity
    public void finish() {
        findViewById(R.id.sodk_editor_mask).setOnTouchListener(null);
        this.c = null;
        this.e.dispose();
        this.e = null;
        super.finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.artifex.sonui.editor.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SODocSession sODocSession = d;
        this.c = sODocSession;
        SlideShowView.setDoc(sODocSession.getDoc());
        SlideShowView.setLib(k.b((Activity) this));
        setContentView(R.layout.sodk_editor_slide_show);
        SODocSession sODocSession2 = this.c;
        if (sODocSession2 == null || sODocSession2.getDoc() == null) {
            super.finish();
            return;
        }
        View viewFindViewById = findViewById(R.id.sodk_editor_mask);
        final GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new a());
        viewFindViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.artifex.sonui.editor.SlideShowActivity.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        SlideShowView slideShowView = (SlideShowView) findViewById(R.id.sodk_editor_slide_show_view);
        this.e = slideShowView;
        slideShowView.setListener(this);
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideAnimating(int i) {
        if (this.f4785a) {
            Log.i(this.f4786b, String.format("Page %d is currently animating", Integer.valueOf(i)));
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideAnimationsCompleted(int i) {
        if (this.f4785a) {
            Log.i(this.f4786b, String.format("Animations completed for page %d", Integer.valueOf(i)));
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideAnimationsStarted(int i) {
        if (this.f4785a) {
            Log.i(this.f4786b, String.format("Animations started for page %d", Integer.valueOf(i)));
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideAnimationsWaiting(int i) {
        if (this.f4785a) {
            Log.i(this.f4786b, String.format("Animations waiting for an event on page %d", Integer.valueOf(i)));
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideEnded(int i) {
        if (this.f4785a) {
            Log.i(this.f4786b, String.format("Slide %d ended", Integer.valueOf(i)));
        }
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideShowCompleted() {
        if (this.f4785a) {
            Log.i(this.f4786b, "Slide show completed");
        }
        finish();
    }

    @Override // com.artifex.sonui.editor.SlideShowViewListener
    public void slideStarted(int i) {
        if (this.f4785a) {
            Log.i(this.f4786b, String.format("Slide %d started", Integer.valueOf(i)));
        }
    }
}
