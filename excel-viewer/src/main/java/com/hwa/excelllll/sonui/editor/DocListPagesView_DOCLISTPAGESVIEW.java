package com.hwa.excelllll.sonui.editor;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: classes.dex */
public class DocListPagesView extends DocView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f4534a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    boolean f4535b;
    float c;
    float d;
    float e;
    float f;
    private int g;
    private DocView h;
    private ImageView i;
    private Bitmap j;
    private int k;
    private MotionEvent l;
    private DocPageView m;
    private boolean n;
    private boolean o;
    private boolean p;

    public DocListPagesView(Context context) {
        super(context);
        this.g = 0;
        this.j = null;
        this.f4535b = false;
        this.k = -1;
        this.m = null;
        this.n = false;
        this.o = false;
        this.p = false;
        b();
    }

    private Bitmap a(View view) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bitmapCreateBitmap;
    }

    private void b() {
        this.g = Utilities.convertDpToPixel(8.0f);
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean allowTouchWithoutChildren() {
        return true;
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean allowXScroll() {
        return false;
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean centerPagesHorizontally() {
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x003d  */
    @Override // com.artifex.sonui.editor.DocView
    public Point constrainScrollBy(int i, int i2) {
        int i3;
        int i4;
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int iHeight = rect.height();
        int iHeight2 = this.mAllPagesRect.height();
        int scrollY = getScrollY();
        if (iHeight2 <= iHeight) {
            i4 = -scrollY;
        } else {
            if (scrollY + i2 < 0) {
                i2 = -scrollY;
            }
            if (isMovingPage()) {
                int i5 = iHeight2 + (-scrollY);
                iHeight -= this.i.getHeight();
                if (i5 - i2 < iHeight) {
                    i3 = i5;
                    i4 = -(iHeight - i3);
                } else {
                    i4 = i2;
                }
            } else {
                i3 = (-scrollY) + iHeight2;
                if (i3 - i2 < iHeight) {
                    i4 = -(iHeight - i3);
                } else {
                    i4 = i2;
                }
            }
        }
        return new Point(0, i4);
    }

    @Override // com.artifex.sonui.editor.DocView
    public void doPageMenu(final int i) {
        new PageMenu(getContext(), (DocPageView) getOrCreateChild(i), getPageCount() > 1, new PageMenu.ActionListener() { // from class: com.artifex.sonui.editor.DocListPagesView.3
            @Override // com.artifex.sonui.editor.PageMenu.ActionListener
            public void onDelete() {
                DocListPagesView.this.getDoc().deletePage(i);
                DocListPagesView.this.onPageMoved(i);
            }

            @Override // com.artifex.sonui.editor.PageMenu.ActionListener
            public void onDuplicate() {
                DocListPagesView.this.getDoc().duplicatePage(i);
                DocListPagesView.this.onPageMoved(i);
            }
        }).show();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void doSingleTap(float f, float f2) {
        Point pointEventToScreen = eventToScreen(f, f2);
        DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
        if (docPageViewFindPageViewContainingPoint != null) {
            DocView docView = this.h;
            docView.addHistory(docView.getScrollX(), this.h.getScrollY(), this.h.getScale(), true);
            int pageNumber = docPageViewFindPageViewContainingPoint.getPageNumber();
            Point pointScrollToPageAmounts = this.h.scrollToPageAmounts(pageNumber);
            DocView docView2 = this.h;
            docView2.addHistory(docView2.getScrollX(), this.h.getScrollY() - pointScrollToPageAmounts.y, this.h.getScale(), false);
            setCurrentPage(pageNumber);
            this.h.scrollToPage(pageNumber, false);
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void finishDrop() {
        this.i.setVisibility(8);
        super.finishDrop();
    }

    public void fitToColumns() {
        this.mLastLayoutColumns = 1;
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int iMax = 0;
        for (int i = 0; i < getPageCount(); i++) {
            iMax = Math.max(iMax, ((DocPageView) getOrCreateChild(i)).getUnscaledWidth());
        }
        int i2 = this.mLastLayoutColumns;
        this.mScale = rect.width() / (((i2 - 1) * 20) + (iMax * i2));
        scaleChildren();
        requestLayout();
    }

    public int getCurrentPage() {
        for (int i = 0; i < getPageCount(); i++) {
            if (((DocPageView) getOrCreateChild(i)).isCurrent()) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.artifex.sonui.editor.DocView
    public int getMovingPageNumber() {
        return this.k;
    }

    @Override // com.artifex.sonui.editor.DocView
    public boolean isMovingPage() {
        return this.f4535b;
    }

    @Override // com.artifex.sonui.editor.DocView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // com.artifex.sonui.editor.DocView, android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        if (this.p) {
            this.o = false;
            LayoutTransition layoutTransition = new LayoutTransition();
            layoutTransition.setDuration(200L);
            layoutTransition.enableTransitionType(4);
            setLayoutTransition(layoutTransition);
            Point pointEventToScreen = eventToScreen(motionEvent.getX(), motionEvent.getY());
            DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
            this.m = docPageViewFindPageViewContainingPoint;
            if (docPageViewFindPageViewContainingPoint != null) {
                setCurrentPage(-1);
                this.m.setCurrent(true);
                this.mPressing = true;
                this.f4535b = true;
                this.k = this.m.getPageNumber();
                this.c = motionEvent.getX();
                this.d = motionEvent.getY();
                this.e = this.m.getX() - getScrollX();
                this.f = this.m.getY() - getScrollY();
                startMovingPage(this.k);
                this.l = motionEvent;
            }
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onLongPressMoving(MotionEvent motionEvent) {
        if (this.p && this.i != null) {
            float x = motionEvent.getX() - this.c;
            float y = motionEvent.getY() - this.d;
            float dimension = (int) getContext().getResources().getDimension(R.dimen.sodk_editor_drag_slop);
            if (Math.abs(x) > dimension || Math.abs(y) > dimension) {
                this.o = true;
            }
            this.n = true;
            float f = this.e;
            float f2 = this.f;
            int iConvertDpToPixel = Utilities.convertDpToPixel(10.0f);
            float f3 = iConvertDpToPixel;
            float f4 = (f2 + y) - f3;
            final int i = 0;
            if (this.i.getVisibility() == 8) {
                Bitmap bitmapA = a((View) this.m);
                this.j = bitmapA;
                this.i.setImageBitmap(bitmapA);
                this.i.setVisibility(0);
            }
            this.i.setX(f + x + f3);
            this.i.setY(f4);
            this.i.invalidate();
            if (a(motionEvent)) {
                View view = (View) getParent();
                float y2 = (motionEvent.getY() - view.getTop()) / view.getHeight();
                if (y2 <= 0.15f && getScrollY() > 0) {
                    i = -this.g;
                } else if (y2 >= 0.85f) {
                    i = this.g;
                }
                if (i != 0) {
                    Handler handler = new Handler();
                    super.forceLayout();
                    handler.postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.DocListPagesView.2
                        @Override // java.lang.Runnable
                        public void run() {
                            DocListPagesView.this.scrollBy(0, i);
                        }
                    }, 5L);
                } else {
                    onMovePage(this.k, (this.i.getHeight() / 2) + (((int) f4) - iConvertDpToPixel));
                }
            }
            this.l = motionEvent;
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onLongPressRelease() {
        if (this.p) {
            setLayoutTransition(null);
            ImageView imageView = this.i;
            if (imageView != null) {
                imageView.setVisibility(8);
                this.i.setImageBitmap(null);
                Bitmap bitmap = this.j;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.j = null;
                }
            }
            if (a(this.l)) {
                dropMovingPage(this.o);
            } else {
                finishDrop();
            }
            this.n = false;
            this.mPressing = false;
            this.f4535b = false;
            this.k = -1;
            this.m = null;
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onOrientationChange() {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int iWidth = rect.width();
        rect.height();
        int iWidth2 = this.mAllPagesRect.width();
        getScrollY();
        if (iWidth2 <= 0) {
            return;
        }
        this.mScale *= iWidth / iWidth2;
        scaleChildren();
        requestLayout();
        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocListPagesView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                final int currentPage = DocListPagesView.this.getCurrentPage();
                if (currentPage >= 0) {
                    new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.DocListPagesView.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DocListPagesView.this.scrollToPage(currentPage, false);
                        }
                    }, 100L);
                }
                DocListPagesView.this.mForceColumnCount = -1;
            }
        });
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onPageMoved(int i) {
        setCurrentPage(i);
        this.h.scrollToPage(i, false);
    }

    @Override // com.artifex.sonui.editor.DocView, android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onSelectionChanged() {
        forceLayout();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onShowKeyboard(boolean z) {
        if (!isShown() || z) {
            return;
        }
        forceLayout();
    }

    public void setBorderColor(int i) {
        this.f4534a = i;
    }

    public void setCanManipulatePages(boolean z) {
        this.p = z;
    }

    public void setCurrentPage(int i) {
        int i2 = 0;
        while (i2 < getPageCount()) {
            DocPageView docPageView = (DocPageView) getOrCreateChild(i2);
            if (i == -1) {
                docPageView.setCurrent(false);
            } else {
                docPageView.setCurrent(i2 == i);
            }
            docPageView.setSelectedBorderColor(getBorderColor());
            i2++;
        }
    }

    public void setMainView(DocView docView) {
        this.h = docView;
    }

    @Override // com.artifex.sonui.editor.DocView
    public void setup(RelativeLayout relativeLayout) {
        if (this.i == null) {
            ImageView imageView = new ImageView(getContext());
            this.i = imageView;
            relativeLayout.addView(imageView);
            this.i.setVisibility(8);
        }
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onConfigurationChange() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onEndFling() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onHidePages() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void onShowPages() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void setMostVisiblePage() {
    }

    @Override // com.artifex.sonui.editor.DocView
    public void showHandles() {
    }

    public DocListPagesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.g = 0;
        this.j = null;
        this.f4535b = false;
        this.k = -1;
        this.m = null;
        this.n = false;
        this.o = false;
        this.p = false;
        b();
    }

    public DocListPagesView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.g = 0;
        this.j = null;
        this.f4535b = false;
        this.k = -1;
        this.m = null;
        this.n = false;
        this.o = false;
        this.p = false;
        b();
    }

    @Override // com.artifex.sonui.editor.DocView
    public void doDoubleTap(float f, float f2) {
    }

    private boolean a(MotionEvent motionEvent) {
        Rect rect = new Rect();
        getHitRect(rect);
        return rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }
}
