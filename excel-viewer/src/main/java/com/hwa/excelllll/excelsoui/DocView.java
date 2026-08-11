package com.hwa.excelllll.excelsoui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;

import com.hwa.excelllll.R;
import com.hwa.excelllll.excelsolib.ConfigOptions;
import com.hwa.excelllll.excelsolib.SOBitmap;
import com.hwa.excelllll.excelsolib.SODoc;
import com.hwa.excelllll.excelsolib.SOSelectionLimits;
import java.util.ArrayList;
public class DocView extends AdapterView<Adapter> implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, DragHandleListener, Runnable {
    protected static final int UNSCALED_GAP = 20;
    private static int an = 10;
    private static int ao = 250;
    private static float ap = 2.5f;
    private static float aq = 20.0f;
    private DragHandle A;
    private DragHandle B;
    private DragHandle C;
    private DragHandle D;
    private DragHandle E;
    private DragHandle F;
    private DragHandle G;
    private ImageView H;
    private Rect I;
    private Rect J;
    private SOBitmap K;
    private Point L;
    private Point M;
    private float N;
    private Point O;
    private RectF P;
    private float Q;
    private float rotationAngleOffset;
    private PointF S;
    private DocPageView T;
    private int U;
    private final Rect V;
    private NoteEditor W;
    private PageAdapter pageAdapter;
    private Point aA;
    private DocPageView aB;
    private boolean aC;
    private KeyboardVisibilityListener keyboardVisibilityListener;
    private boolean aE;
    private int aF;
    private int aG;
    private SOSelectionLimits aH;
    private boolean aI;
    private boolean aJ;
    private boolean aK;
    private boolean aL;
    private boolean aM;
    private int aN;
    private boolean aa;
    private float ab;
    private int ac;
    private int ad;
    private boolean ae;
    private boolean af;
    private boolean ag;
    private float ah;
    private int ai;
    private int aj;
    private int ak;
    private int al;
    private DocPageView am;
    private boolean ar;
    private int as;
    private int at;
    private int au;
    private int av;
    private History aw;
    private boolean ax;
    private boolean ay;
    private boolean az;
    private boolean finished;
    private final SparseArray<View> c;
    private int d;
    private int e;
    private boolean f;
    private GestureDetector g;
    private ScaleGestureDetector h;
    private SOBitmap[] i;
    private int j;
    private boolean k;
    private int l;
    protected int lastMostVisibleChild;
    private final Rect m;
    protected final Rect mAllPagesRect;
    protected ConfigOptions mConfigOptions;
    protected int mForceColumnCount;
    protected DocViewHost mHostActivity;
    protected int mLastLayoutColumns;
    public float mLastReflowWidth;
    protected boolean mPressing;
    protected float mScale;
    protected boolean mScaling;
    protected DocPageView mSelectionStartPage;
    private final Rect n;
    private final Point o;
    private final Rect p;
    private Scroller q;
    private int r;
    private int s;
    private Smoother t;
    private long u;
    private float v;
    private float w;
    private int x;
    private SODoc y;
    private DragHandle z;

    public class Smoother {
        ArrayList<Integer> samples = new ArrayList<>();
        private int c;

        public Smoother(int i) {
            this.c = i;
        }

        public void addValue(int i) {
            if (this.samples.size() == this.c) {
                this.samples.remove(0);
            }
            this.samples.add(Integer.valueOf(i));
        }

        public void clear() {
            this.samples.clear();
        }

        public int getAverage() {
            int i = 0;
            if (this.samples.size() == 0) {
                return 0;
            }
            int iIntValue = 0;
            while (true) {
                int size = this.samples.size();
                ArrayList<Integer> arrayList = this.samples;
                if (i >= size) {
                    return iIntValue / arrayList.size();
                }
                iIntValue += arrayList.get(i).intValue();
                i++;
            }
        }
    }

    public interface KeyboardVisibilityListener {
        void onKeyboardVisibilityChanged(boolean visible);
    }

    public DocView(Context context) {
        super(context);
        this.finished = false;
        this.c = new SparseArray<>(3);
        this.mScale = 1.0f;
        this.f = false;
        this.j = 0;
        this.k = false;
        this.l = 0;
        this.m = new Rect();
        this.n = new Rect();
        this.o = new Point();
        this.mAllPagesRect = new Rect();
        this.p = new Rect();
        this.mLastLayoutColumns = 1;
        this.lastMostVisibleChild = -1;
        this.u = 0L;
        this.x = 0;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.I = new Rect();
        this.J = new Rect();
        this.K = null;
        this.O = new Point();
        this.Q = 0.0f;
        this.rotationAngleOffset = 0.0f;
        this.U = -1;
        this.V = new Rect();
        this.W = null;
        this.aa = false;
        this.ab = 0.0f;
        this.ac = 0;
        this.ad = 0;
        this.mPressing = false;
        this.ae = false;
        this.af = false;
        this.ag = false;
        this.ar = true;
        this.as = 0;
        this.at = -1;
        this.au = -1;
        this.av = -1;
        this.mConfigOptions = null;
        this.mForceColumnCount = -1;
        this.mLastReflowWidth = 0.0f;
        this.mHostActivity = null;
        this.ax = false;
        this.ay = false;
        this.az = false;
        this.aC = false;
        this.keyboardVisibilityListener = null;
        this.aE = true;
        this.aF = 0;
        this.aG = -1;
        this.aH = null;
        this.aI = false;
        this.aJ = false;
        this.aK = false;
        this.aL = false;
        this.aM = false;
        this.aN = -1;
        initialize(context);
    }

    private void updateSelectionPreviewBounds(int x, int y, int width, int height) {
        Rect rectScreenRect = this.mSelectionStartPage.screenRect();
        Rect rect = new Rect(x, y, x + width, y + height);
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        rect.offset(iArr[0], iArr[1]);
        int i5 = rect.left;
        int i6 = rectScreenRect.left;
        if (i5 < i6) {
            x += i6 - i5;
        }
        int i7 = rect.right;
        int i8 = rectScreenRect.right;
        if (i7 > i8) {
            x -= i7 - i8;
        }
        int i9 = rect.top;
        int i10 = rectScreenRect.top;
        if (i9 < i10) {
            y += i10 - i9;
        }
        int i11 = rect.bottom;
        int i12 = rectScreenRect.bottom;
        if (i11 > i12) {
            y -= i11 - i12;
        }
        int i13 = x + width;
        int i14 = y + height;
        this.I.set(x, y, i13, i14);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.H.getLayoutParams();
        int overflowHeight = i14 - getHeight();
        int i15 = overflowHeight > 0 ? -overflowHeight : 0;
        int overflowWidth = i13 - getWidth();
        layoutParams.setMargins(x, y, overflowWidth > 0 ? -overflowWidth : 0, i15);
        layoutParams.width = width;
        layoutParams.height = height;
        this.H.setLayoutParams(layoutParams);
        this.H.invalidate();
        this.H.setVisibility(View.VISIBLE);
    }

    private void updateResizePreview(DragHandle dragHandle) {
        int i;
        int iA;
        PointF pointF;
        int iA2;
        int kind;
        int i2;
        int i3 = 0;
        Point point = this.L;
        int measuredHeight = point.y;
        int measuredWidth = point.x;
        Point point2 = this.M;
        int measuredHeight2 = point2.y;
        int measuredWidth2 = point2.x;
        int kind2 = dragHandle.getKind();
        if (kind2 != 3) {
            if (kind2 != 4) {
                if (kind2 == 5) {
                    measuredHeight2 = (dragHandle.getMeasuredHeight() / 2) + dragHandle.getPosition().y;
                } else if (kind2 == 6) {
                    measuredHeight2 = (dragHandle.getMeasuredHeight() / 2) + dragHandle.getPosition().y;
                }
                i = measuredWidth2 - measuredWidth;
                iA = measuredHeight2 - measuredHeight;
                pointF = this.S;
                if (pointF.x > 0.0f || pointF.y <= 0.0f) {
                    if (i < 100) {
                        i = 100;
                    }
                    iA2 = i;
                    if (iA < 100) {
                        iA = 100;
                    }
                } else {
                    Point point3 = this.M;
                    int i4 = point3.x;
                    Point point4 = this.L;
                    int i5 = i4 - point4.x;
                    int i6 = point3.y - point4.y;
                    double dSqrt = Math.sqrt((iA * iA) + (i * i)) / Math.sqrt((i6 * i6) + (i5 * i5));
                    double d = i5;
                    Double.isNaN(d);
                    iA2 = (int) ExcelMath.scale(d, d, d, dSqrt);
                    double d2 = i6;
                    Double.isNaN(d2);
                    iA = (int) ExcelMath.scale(d2, d2, d2, dSqrt);
                    if (iA2 < 100 || iA < 100) {
                        return;
                    }
                }
                kind = dragHandle.getKind();
                if (kind != 3) {
                    if (kind != 4) {
                        if (kind == 5) {
                            i2 = this.M.x - iA2;
                        } else if (kind != 6) {
                            i2 = 0;
                        } else {
                            i2 = this.L.x;
                        }
                        i3 = this.L.y;
                    } else {
                        i2 = this.L.x;
                    }
                    updateSelectionPreviewBounds(i2, i3, iA2, iA);
                }
                i2 = this.M.x - iA2;
                i3 = this.M.y - iA;
                updateSelectionPreviewBounds(i2, i3, iA2, iA);
            }
            measuredHeight = dragHandle.getPosition().y + (dragHandle.getMeasuredHeight() / 2);
            measuredWidth2 = dragHandle.getPosition().x + (dragHandle.getMeasuredWidth() / 2);
            i = measuredWidth2 - measuredWidth;
            iA = measuredHeight2 - measuredHeight;
            pointF = this.S;
            if (pointF.x > 0.0f) {
                if (i < 100) {
                    i = 100;
                }
                iA2 = i;
                if (iA < 100) {
                    iA = 100;
                }
            } else {
                if (i < 100) {
                    i = 100;
                }
                iA2 = i;
                if (iA < 100) {
                    iA = 100;
                }
            }
            kind = dragHandle.getKind();
            if (kind != 3) {
                if (kind != 4) {
                    if (kind == 5) {
                        i2 = this.M.x - iA2;
                    } else if (kind != 6) {
                        i2 = 0;
                        i3 = 0;
                    } else {
                        i2 = this.L.x;
                    }
                    i3 = this.L.y;
                } else {
                    i2 = this.L.x;
                }
                updateSelectionPreviewBounds(i2, i3, iA2, iA);
            }
            i2 = this.M.x - iA2;
            i3 = this.M.y - iA;
            updateSelectionPreviewBounds(i2, i3, iA2, iA);
        }
        measuredHeight = (dragHandle.getMeasuredHeight() / 2) + dragHandle.getPosition().y;
        measuredWidth = dragHandle.getPosition().x + (dragHandle.getMeasuredWidth() / 2);
        i = measuredWidth2 - measuredWidth;
        iA = measuredHeight2 - measuredHeight;
        pointF = this.S;
        if (i < 100) {
            i = 100;
        }
        iA2 = i;
        if (iA < 100) {
            iA = 100;
        }
        kind = dragHandle.getKind();
        if (kind != 3) {
            if (kind != 4) {
                if (kind == 5) {
                    i2 = this.M.x - iA2;
                } else if (kind != 6) {
                    i2 = 0;
                    i3 = 0;
                } else {
                    i2 = this.L.x;
                }
                i3 = this.L.y;
            } else {
                i2 = this.L.x;
            }
            updateSelectionPreviewBounds(i2, i3, iA2, iA);
        }
        i2 = this.M.x - iA2;
        i3 = this.M.y - iA;
        updateSelectionPreviewBounds(i2, i3, iA2, iA);
    }

    private void updateRotationPreview(DragHandle dragHandle) {
        int i = dragHandle.getPosition().x;
        int i2 = dragHandle.getPosition().y;
        Point point = this.L;
        int i3 = point.x;
        Point point2 = this.M;
        float angle = getAngle(i, i2, (i3 + point2.x) / 2, (point.y + point2.y) / 2);
        if (Math.abs(angle - this.rotationAngleOffset) > 2.0f) {
            this.rotationAngleOffset = angle;
            this.H.setRotation(angle);
            ImageView imageView = this.H;
            imageView.setLayoutParams(imageView.getLayoutParams());
        }
    }
    public void renderVisiblePages() {
        int i = 0;
        this.k = false;
        if (canRenderPages()) {
            int i2 = this.j + 1;
            this.j = i2;
            if (i2 >= this.i.length) {
                this.j = 0;
            }
            final ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < getPageCount(); i3++) {
                DocPageView docPageView = (DocPageView) getOrCreateChild(i3);
                if (docPageView.getParent() != null && docPageView.isShown()) {
                    arrayList.add(docPageView);
                    docPageView.startRenderPass();
                }
            }
            final long jCurrentTimeMillis = System.currentTimeMillis();
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                DocPageView docPageView2 = (DocPageView) obj;
                if (!canRenderPages()) {
                    return;
                }
                this.l++;
                docPageView2.render(this.i[this.j], new RenderCallback() { // from class: com.artifex.sonui.editor.DocView.4
                    @Override // com.hwa.excel.excelsolib.o
                    public void onRenderProgress(int i4) {
                        DocView.decrementPendingRenderCount(DocView.this);
                        if (DocView.this.l == 0) {
                            ArrayList arrayList2 = arrayList;
                            int size2 = arrayList2.size();
                            int i5 = 0;
                            while (i5 < size2) {
                                Object obj2 = arrayList2.get(i5);
                                i5++;
                                DocPageView docPageView3 = (DocPageView) obj2;
                                docPageView3.endRenderPass();
                                docPageView3.invalidate();
                            }
                            if (DocView.this.k) {
                                DocView.this.renderVisiblePages();
                            }
                        }
                    }
                });
            }
        }
    }

    private void updateSelectionLimits() {
        this.mSelectionStartPage = null;
        this.T = null;
        this.aH = null;
        int iO = getDoc().getTrackedPageCount();
        int iP = getDoc().getActivePageIndex();
        if (iO <= iP && getDoc().getDocType() > 0) {
            this.mSelectionStartPage = (DocPageView) getOrCreateChild(iO);
            this.T = (DocPageView) getOrCreateChild(iP);
            while (iO < iP + 1) {
                SOSelectionLimits selectionLimits = ((DocPageView) getOrCreateChild(iO)).getSelectionLimits();
                if (selectionLimits != null) {
                    SOSelectionLimits sOSelectionLimits = this.aH;
                    if (sOSelectionLimits == null) {
                        this.aH = selectionLimits;
                    } else {
                        sOSelectionLimits.combineWith(selectionLimits);
                    }
                }
                iO++;
            }
        }
    }

    private void positionSelectionHandles() {
        SOSelectionLimits selectionLimits;
        if (finished() || (selectionLimits = getSelectionLimits()) == null) {
            return;
        }
        boolean z = selectionLimits.getIsActive() && !selectionLimits.getIsCaret();
        boolean z2 = selectionLimits.getIsActive() && getDoc().getSelectionCanBeResized() && !selectionLimits.getIsCaret();
        boolean z3 = selectionLimits.getIsActive() && getDoc().getSelectionCanBeAbsolutelyPositioned();
        boolean z4 = selectionLimits.getIsActive() && getDoc().getSelectionCanBeRotated();
        if (z2) {
            positionDragHandle(this.B, this.mSelectionStartPage, (int) selectionLimits.getBox().left, (int) selectionLimits.getBox().top);
            positionDragHandle(this.C, this.mSelectionStartPage, (int) selectionLimits.getBox().right, (int) selectionLimits.getBox().top);
            positionDragHandle(this.D, this.mSelectionStartPage, (int) selectionLimits.getBox().left, (int) selectionLimits.getBox().bottom);
            positionDragHandle(this.E, this.mSelectionStartPage, (int) selectionLimits.getBox().right, (int) selectionLimits.getBox().bottom);
        }
        if (z) {
            positionDragHandle(this.z, this.mSelectionStartPage, (int) selectionLimits.getStart().x, (int) selectionLimits.getStart().y);
            positionDragHandle(this.A, this.T, (int) selectionLimits.getEnd().x, (int) selectionLimits.getEnd().y);
        }
        if (z3) {
            positionDragHandle(this.F, this.mSelectionStartPage, ((int) (selectionLimits.getBox().left + selectionLimits.getBox().right)) / 2, (int) selectionLimits.getBox().bottom);
        }
        if (z4) {
            positionDragHandle(this.G, this.mSelectionStartPage, ((int) (selectionLimits.getBox().left + selectionLimits.getBox().right)) / 2, (int) selectionLimits.getBox().top);
        }
    }

    private void hideAllDragHandles() {
        showSelectionHandles(false);
        showResizeHandles(false);
        showDragHandle(false);
        showRotateHandle(false);
    }

    private View getCached() {
        return null;
    }

    public static /* synthetic */ int decrementPendingRenderCount(DocView docView) {
        int i = docView.l;
        docView.l = i - 1;
        return i;
    }

    public void addComment() {
        this.aJ = true;
        getDoc().addHighlightAnnotation();
    }

    public void addHistory(int i, int i2, float f, boolean z) {
        if (!z || shouldAddHistory(i, i2, f)) {
            getHistory().add(i, i2, f);
        }
        this.mHostActivity.selectionupdated();
    }

    public Point adjustDragHandle(DragHandle dragHandle, Point point) {
        return new Point(point);
    }

    public boolean allowTouchWithoutChildren() {
        return false;
    }

    public boolean allowXScroll() {
        return !this.aM;
    }

    public boolean canEditText() {
        ConfigOptions configOptions = this.mConfigOptions;
        if (configOptions != null) {
            return configOptions.isEditingEnabled();
        }
        return true;
    }

    public boolean canSelectionSpanPages() {
        return true;
    }

    public boolean centerPagesHorizontally() {
        return false;
    }

    public boolean clearAreaSelection() {
        SOSelectionLimits selectionLimits = getSelectionLimits();
        if (selectionLimits == null || selectionLimits.getIsCaret()) {
            return false;
        }
        if (!selectionLimits.getHasSelectionStart() && !selectionLimits.getHasSelectionEnd()) {
            return false;
        }
        this.y.clearSelection();
        return true;
    }

    public void clearChildViews() {
        this.c.clear();
    }

    public Point constrainScrollBy(int i, int i2) {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int iHeight = rect.height();
        int iWidth = rect.width();
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int iWidth2 = this.mAllPagesRect.width();
        Rect rect2 = this.mAllPagesRect;
        if (iWidth2 <= iWidth) {
            if ((rect2.width() - scrollX) - i > iWidth) {
                i = 0;
            }
            if (scrollX + i > 0) {
                i = -scrollX;
            }
        } else {
            if (rect2.width() < scrollX + iWidth + i) {
                i = 0;
            }
            if (scrollX + i < 0) {
                i = -scrollX;
            }
            int iWidth3 = (this.mAllPagesRect.width() - scrollX) + i;
            if (iWidth3 < iWidth) {
                i = iWidth3 - iWidth;
            }
        }
        if (this.mAllPagesRect.height() <= iHeight) {
            if ((this.mAllPagesRect.height() - scrollY) - i2 > iHeight) {
                i2 = 0;
            }
            if (scrollY + i2 > 0) {
                i2 = -scrollY;
            }
        } else {
            if (scrollY + i2 < 0) {
                i2 = -scrollY;
            }
            int i3 = -scrollY;
            if ((this.mAllPagesRect.height() + i3) - i2 < iHeight) {
                i2 = -(iHeight - (this.mAllPagesRect.height() + i3));
            }
        }
        return new Point(i, i2);
    }

    public void doDoubleTap(float f, float f2) {
        if (canEditText()) {
            doDoubleTap2(f, f2);
        }
    }

    public void doDoubleTap2(float f, float f2) {
        Point pointEventToScreen = eventToScreen(f, f2);
        DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
        if (docPageViewFindPageViewContainingPoint == null || !docPageViewFindPageViewContainingPoint.canDoubleTap(pointEventToScreen.x, pointEventToScreen.y)) {
            return;
        }
        docPageViewFindPageViewContainingPoint.onDoubleTap(pointEventToScreen.x, pointEventToScreen.y);
        focusInputView();
        resetInputView();
        showKeyboardAfterDoubleTap(pointEventToScreen);
    }

    public void doSingleTap(float f, float f2) {
        getDoc().closeSearch();
        NoteEditor noteEditor = this.W;
        if (noteEditor != null && noteEditor.isVisible()) {
            Utilities.hideKeyboard(getContext());
            this.W.hide();
            return;
        }
        if (shouldPreclearSelection() && clearAreaSelection()) {
            Utilities.hideKeyboard(getContext());
            return;
        }
        final Point pointEventToScreen = eventToScreen(f, f2);
        final DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
        if (docPageViewFindPageViewContainingPoint == null || onSingleTap(pointEventToScreen.x, pointEventToScreen.y, docPageViewFindPageViewContainingPoint)) {
            return;
        }
        if (canEditText() && tapToFocus()) {
            focusInputView();
        }
        if (docPageViewFindPageViewContainingPoint.onSingleTap(pointEventToScreen.x, pointEventToScreen.y, canEditText(), new DocPageView.ExternalLinkListener() { // from class: com.artifex.sonui.editor.DocView.9
            @Override // com.artifex.sonui.editor.DocPageView.ExternalLinkListener
            public void handleExternalLink(int i, Rect rect) {
                DocView docView = DocView.this;
                docView.addHistory(docView.getScrollX(), DocView.this.getScrollY(), DocView.this.mScale, true);
                int iScrollBoxToTopAmount = DocView.this.scrollBoxToTopAmount(i, new RectF(rect.left, rect.top, rect.right, rect.bottom));
                DocView docView2 = DocView.this;
                docView2.addHistory(docView2.getScrollX(), DocView.this.getScrollY() - iScrollBoxToTopAmount, DocView.this.mScale, false);
                DocView.this.scrollBoxToTop(i, new RectF(rect.left, rect.top, rect.right, rect.bottom));
            }
        })) {
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.DocView.10
            @Override // java.lang.Runnable
            public void run() {
                if (DocView.this.finished()) {
                    return;
                }
                if (DocView.this.x == 1 && DocView.this.canEditText()) {
                    boolean isActive = docPageViewFindPageViewContainingPoint.selectionLimits().getIsActive();
                    DocView docView = DocView.this;
                    if (isActive) {
                        docView.resetInputView();
                        DocView docView2 = DocView.this;
                        docView2.aC = docView2.mHostActivity.showKeyboard();
                    } else {
                        docView.showKeyboardAndScroll(pointEventToScreen);
                    }
                } else if (Utilities.isLandscapePhone(DocView.this.getContext())) {
                    Utilities.hideKeyboard(DocView.this.getContext());
                }
                DocView.this.x = 0;
            }
        }, 500L);
    }

    public void dropMovingPage(boolean z) {
        final int movingPageNumber = getMovingPageNumber();
        if (isValidPage(movingPageNumber)) {
            if (getPageMoveTarget(movingPageNumber) == movingPageNumber) {
                z = false;
            }
            if (z) {
                movePage(movingPageNumber, getPageMoveTarget(movingPageNumber));
                finishDrop();
            } else {
                if (canEditText()) {
                    final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocView.5
                        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                        public void onGlobalLayout() {
                            viewTreeObserver.removeOnGlobalLayoutListener(this);
                            DocView.this.doPageMenu(movingPageNumber);
                            DocView.this.mHostActivity.setCurrentPage(movingPageNumber);
                        }
                    });
                }
                finishDrop();
            }
        }
    }

    public Point eventToScreen(float f, float f2) {
        int iRound = Math.round(f);
        int iRound2 = Math.round(f2);
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        return new Point(iRound + rect.left, iRound2 + rect.top);
    }

    public DocPageView findPageContainingSelection() {
        SOSelectionLimits sOSelectionLimitsSelectionLimits;
        for (int i = 0; i < getPageCount(); i++) {
            DocPageView docPageView = (DocPageView) getOrCreateChild(i);
            if (docPageView != null && (sOSelectionLimitsSelectionLimits = docPageView.selectionLimits()) != null && sOSelectionLimitsSelectionLimits.getIsActive() && sOSelectionLimitsSelectionLimits.getHasSelectionStart()) {
                return docPageView;
            }
        }
        return null;
    }

    public DocPageView findPageViewContainingPoint(int i, int i2, boolean z) {
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            Rect rect = new Rect();
            childAt.getGlobalVisibleRect(rect);
            if (z) {
                float f = rect.left;
                float f2 = this.mScale;
                rect.left = (int) (f - ((f2 * 20.0f) / 2.0f));
                rect.right = (int) (((f2 * 20.0f) / 2.0f) + rect.right);
                rect.top = (int) (rect.top - ((f2 * 20.0f) / 2.0f));
                rect.bottom = (int) (((f2 * 20.0f) / 2.0f) + rect.bottom);
            }
            if (rect.contains(i, i2)) {
                return (DocPageView) childAt;
            }
        }
        return null;
    }

    public void finish() {
        this.finished = true;
        for (int i = 0; i < getChildCount(); i++) {
            ((DocPageView) getChildAt(i)).finish();
        }
        clearChildViews();
        this.y = null;
    }

    public void finishDrop() {
        this.av = -1;
        this.aa = true;
        requestLayout();
    }

    public boolean finished() {
        return this.finished;
    }

    public void focusInputView() {
        // Standalone Excel editor uses SOEditText instead of the original hidden InputView.
    }

    @Override // android.view.View
    public void forceLayout() {
        if (finished()) {
            return;
        }
        this.aa = true;
        requestLayout();
    }

    @Override // android.widget.AdapterView
    public Adapter getAdapter() {
        return this.pageAdapter;
    }

    public float getAngle(int i, int i2, int i3, int i4) {
        float degrees = ((float) Math.toDegrees(Math.atan2(i4 - i2, i3 - i))) - 90.0f;
        return degrees < 0.0f ? degrees + 360.0f : degrees;
    }

    public int getBorderColor() {
        return this.mHostActivity.getBorderColor();
    }

    public SODoc getDoc() {
        return this.y;
    }

    public History getHistory() {
        return this.aw;
    }

    public int getMostVisiblePage() {
        return this.U;
    }

    public int getMovingPageNumber() {
        return -1;
    }

    public View getOrCreateChild(int i) {
        View view = this.c.get(i);
        if (view != null) {
            return view;
        }
        View viewFromAdapter = getViewFromAdapter(i);
        this.c.append(i, viewFromAdapter);
        setChildScale(viewFromAdapter, Float.valueOf(this.mScale));
        return viewFromAdapter;
    }

    public int getPageCount() {
        if (getAdapter() == null) {
            return 0;
        }
        return getAdapter().getCount();
    }

    public boolean getReflowMode() {
        return this.aM;
    }

    public int getReflowWidth() {
        return ((DocPageView) getOrCreateChild(0)).getReflowWidth();
    }

    public float getScale() {
        return this.mScale;
    }

    @Override // android.widget.AdapterView
    public View getSelectedView() {
        return null;
    }

    public SOSelectionLimits getSelectionLimits() {
        return this.aH;
    }

    public int getStartPage() {
        return this.aF;
    }

    public View getViewFromAdapter(int i) {
        return getAdapter().getView(i, getCached(), this);
    }

    public void goToFirstPage() {
        this.aG = 0;
        forceLayout();
    }

    public void goToLastPage() {
        this.aG = getPageCount() - 1;
        forceLayout();
    }

    public boolean handleFullscreenTap(float f, float f2) {
        return false;
    }

    public void handleStartPage() {
        final int startPage = getStartPage();
        if (startPage > 0) {
            setStartPage(0);
            new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.DocView.3
                @Override // java.lang.Runnable
                public void run() {
                    DocView.this.scrollBy(0, ((DocPageView) DocView.this.getOrCreateChild(startPage - 1)).getChildRect().top);
                    DocView.this.requestLayout();
                }
            });
        }
    }

    public void initialize(Context context) {
        setBackgroundColor(getContext().getColor(R.color.sodk_editor_doc_background));
        this.g = new GestureDetector(context, this);
        this.h = new ScaleGestureDetector(context, this);
        this.q = new Scroller(context);
        this.t = new Smoother(3);
        this.aw = new History();
        setScrollContainer(false);
    }

    public boolean isAtRest() {
        return (this.f || this.mScaling || !this.q.isFinished()) ? false : true;
    }

    public boolean isDocModified() {
        SODoc sODoc = this.y;
        if (sODoc != null) {
            return sODoc.getHasBeenModified();
        }
        return false;
    }

    public boolean isMovingPage() {
        return false;
    }

    public boolean isValidPage(int i) {
        return i >= 0 && i < getPageCount();
    }

    public void layoutNow() {
        this.aa = true;
        requestLayout();
    }

    public float maxScale() {
        return 5.0f;
    }

    public float minScale() {
        return 0.15f;
    }

    public void movePage(int i, int i2) {
        if (isValidPage(i) && isValidPage(i2)) {
            getDoc().movePage(i, i2);
            onPageMoved(i2);
        }
    }

    public void onConfigurationChange() {
        this.mForceColumnCount = this.mLastLayoutColumns;
        requestLayout();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        if (!this.q.isFinished()) {
            this.ay = true;
            this.q.forceFinished(true);
        }
        return true;
    }

    @Override // com.artifex.sonui.editor.DragHandleListener
    public void onDrag(DragHandle dragHandle) {
        int pageNumber;
        Point pointA = localPointToScreen(dragHandle.getPosition());
        DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointA.x, pointA.y, false);
        if (docPageViewFindPageViewContainingPoint == null) {
            return;
        }
        if (!dragHandle.isSelectionKind()) {
            if (dragHandle.isResizeKind()) {
                updateResizePreview(dragHandle);
                return;
            } else if (dragHandle.isDragKind()) {
                updateDragPreview(dragHandle);
                return;
            } else {
                if (dragHandle.isRotateKind()) {
                    updateRotationPreview(dragHandle);
                    return;
                }
                return;
            }
        }
        if (canSelectionSpanPages() || ((pageNumber = docPageViewFindPageViewContainingPoint.getPageNumber()) == this.mSelectionStartPage.getPageNumber() && pageNumber == this.T.getPageNumber())) {
            Point pointAdjustDragHandle = adjustDragHandle(dragHandle, pointA);
            if (dragHandle.getKind() == 1) {
                pointAdjustDragHandle.x = dragHandle.getWidth() + pointAdjustDragHandle.x;
                pointAdjustDragHandle.y = dragHandle.getHeight() + pointAdjustDragHandle.y;
                docPageViewFindPageViewContainingPoint.setSelectionStart(pointAdjustDragHandle);
            } else if (dragHandle.getKind() == 2) {
                docPageViewFindPageViewContainingPoint.setSelectionEnd(pointAdjustDragHandle);
            }
        }
    }

    @Override // com.artifex.sonui.editor.DragHandleListener
    public void onEndDrag(DragHandle dragHandle) {
        if (!dragHandle.isResizeKind() || this.P == null) {
            if (dragHandle.isDragKind() && this.P != null && this.aK) {
                Rect rect = this.I;
                int i = rect.left;
                Rect rect2 = this.J;
                Point pointViewToPage = this.mSelectionStartPage.viewToPage(i - rect2.left, rect.top - rect2.top);
                this.P.offset(pointViewToPage.x, pointViewToPage.y);
            } else if (dragHandle.isRotateKind()) {
                getDoc().setSelectionRotation(this.Q + this.rotationAngleOffset);
            } else if (dragHandle.isSelectionKind()) {
                positionSelectionHandles();
            }
            this.P = null;
            this.aK = false;
        }
        Rect rect3 = this.I;
        int i2 = rect3.left;
        Rect rect4 = this.J;
        int i3 = rect4.left;
        int i4 = rect3.top;
        int i5 = rect4.top;
        int i6 = rect3.right;
        int i7 = rect4.right;
        int i8 = rect3.bottom;
        int i9 = rect4.bottom;
        Point pointViewToPage2 = this.mSelectionStartPage.viewToPage(i2 - i3, i4 - i5);
        Point pointViewToPage3 = this.mSelectionStartPage.viewToPage(i6 - i7, i8 - i9);
        RectF rectF = this.P;
        rectF.left += pointViewToPage2.x;
        rectF.top += pointViewToPage2.y;
        rectF.right += pointViewToPage3.x;
        rectF.bottom += pointViewToPage3.y;
        this.mSelectionStartPage.getPage().setSelectionLimitsBox(this.P);
        this.P = null;
        this.aK = false;
    }

    public void onEndFling() {
        int i;
        if (!finished() && (i = this.U) >= 0) {
            this.mHostActivity.setCurrentPage(i);
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mScaling || !this.q.isFinished()) {
            return true;
        }
        this.az = true;
        int i = (int) f2;
        int i2 = !allowXScroll() ? 0 : (int) f;
        this.t.clear();
        this.q.forceFinished(true);
        this.s = 0;
        this.r = 0;
        this.q.fling(0, 0, i2, i, -2147483647, Integer.MAX_VALUE, -2147483647, Integer.MAX_VALUE);
        post(this);
        return true;
    }

    public void onFoundText(int i, RectF rectF) {
        scrollBoxIntoView(i, rectF, true);
    }

    public void onFullscreen(boolean z) {
        if (z) {
            getDoc().clearSelection();
        }
        for (int i = 0; i < getPageCount(); i++) {
            ((DocPageView) getOrCreateChild(i)).onFullscreen(z);
        }
    }

    public void onHidePages() {
        int integer = getContext().getResources().getInteger(R.integer.sodk_editor_page_width_percentage);
        applyScaleFactor((float) (getContext().getResources().getInteger(R.integer.sodk_editor_pagelist_width_percentage) + integer) / integer);
        this.ax = false;
    }

    public void onHistoryItem(History.HistoryItem historyItem) {
        this.d = 0;
        this.e = 0;
        setScrollX(historyItem.getScrollX());
        setScrollY(historyItem.getScrollY());
        this.mScale = historyItem.getScale();
        forceLayout();
        new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.DocView.6
            @Override // java.lang.Runnable
            public void run() {
                if (DocView.this.U >= 0) {
                    DocView docView = DocView.this;
                    docView.mHostActivity.setCurrentPage(docView.U);
                }
            }
        });
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iZ;
        int unscaledHeight;
        boolean z2;
        int i5;
        float f;
        int i6 = 0;
        int i7 = 0;
        int iHeight;
        boolean z3;
        if (!finished() && isShown()) {
            int pageCount = getPageCount();
            if (this.aM) {
                pageCount = this.y.getDocType();
            }
            if (getPageCount() == 0) {
                return;
            }
            scrollBy(-this.d, -this.e);
            this.e = 0;
            this.d = 0;
            if (shouldLayout()) {
                this.o.set(getScrollX(), getScrollY());
                getGlobalVisibleRect(this.n);
                Rect rect = this.n;
                Point point = this.o;
                rect.offsetTo(point.x, point.y);
                this.as = 0;
                for (int i8 = 0; i8 < getPageCount(); i8++) {
                    this.as = Math.max(this.as, ((DocPageView) getOrCreateChild(i8)).getUnscaledWidth());
                }
                float f2 = this.as;
                float f3 = this.mScale;
                int i9 = (int) (f2 * f3);
                float f4 = 20.0f;
                if (this.mPressing) {
                    iZ = this.mLastLayoutColumns;
                } else {
                    int i10 = this.mForceColumnCount;
                    if (i10 != -1) {
                        iZ = i10;
                    } else {
                        int i11 = (int) (f3 * 20.0f);
                        double dWidth = this.n.width() + i11;
                        double d = i9 + i11;
                        Double.isNaN(dWidth);
                        Double.isNaN(d);
                        Double.isNaN(dWidth);
                        Double.isNaN(d);
                        iZ = (int) ExcelMath.unscale(dWidth, d, dWidth, d);
                        if (this.aM) {
                            iZ = 1;
                        }
                    }
                }
                if (iZ > pageCount) {
                    iZ = pageCount;
                }
                this.U = -1;
                this.mAllPagesRect.setEmpty();
                if (isMovingPage()) {
                    unscaledHeight = ((DocPageView) getOrCreateChild(getMovingPageNumber())).getUnscaledHeight();
                    this.at = -1;
                    this.au = -1;
                } else {
                    unscaledHeight = 0;
                }
                int iMax = Math.max(getPageCount(), getChildCount());
                int i12 = 0;
                int iMax2 = 0;
                int i13 = 0;
                int i14 = 0;
                int i15 = -1;
                int i16 = -1;
                while (i12 < iMax) {
                    DocPageView docPageView = (DocPageView) getOrCreateChild(i12);
                    if (docPageView == null) {
                        removeViewAt(i12);
                        f = f4;
                    } else {
                        docPageView.setDocView(this);
                        f = f4;
                        if (isMovingPage() && docPageView.getPageNumber() == getMovingPageNumber()) {
                            removeViewInLayout(docPageView);
                        } else {
                            int unscaledWidth = docPageView.getUnscaledWidth();
                            int unscaledHeight2 = docPageView.getUnscaledHeight();
                            int i17 = (this.as + 20) * i13;
                            int i18 = i14 + unscaledHeight2;
                            iMax2 = Math.max(iMax2, unscaledHeight2);
                            if (isMovingPage()) {
                                float f5 = this.mScale;
                                int i19 = (int) (i14 * f5);
                                i6 = unscaledHeight;
                                int i20 = (int) (i18 * f5);
                                if (i15 == -1) {
                                    z3 = this.av <= i19;
                                    i15 = i12;
                                } else {
                                    z3 = false;
                                }
                                int i21 = this.av;
                                if (i21 >= i19 && i21 <= (i19 + i20) / 2 || z3) {
                                    this.at = i12;
                                    i14 += i6;
                                    i18 += i6;
                                }
                            } else {
                                i6 = unscaledHeight;
                            }
                            float f6 = this.mScale;
                            int i22 = (int) (i17 * f6);
                            int i23 = (int) (i14 * f6);
                            int i24 = (int) ((unscaledWidth + i17) * f6);
                            int i25 = (int) (i18 * f6);
                            i7 = iMax;
                            this.m.set(i22, i23, i24, i25);
                            docPageView.setChildRect(this.m);
                            boolean zIsEmpty = this.mAllPagesRect.isEmpty();
                            Rect rect2 = this.mAllPagesRect;
                            if (zIsEmpty) {
                                rect2.set(this.m);
                            } else {
                                rect2.union(this.m);
                            }
                            if (!this.m.intersect(this.n) || i12 >= pageCount) {
                                removeViewInLayout(docPageView);
                            } else {
                                if (docPageView.getParent() == null) {
                                    docPageView.clearContent();
                                    addChildViewInLayout(docPageView);
                                }
                                int width = centerPagesHorizontally() ? (getWidth() - this.m.width()) / 2 : 0;
                                docPageView.layout(i22 + width, i23, i24 + width, i25);
                                docPageView.invalidate();
                                if (docPageView.getGlobalVisibleRect(this.V) && (iHeight = this.V.height()) > i16) {
                                    this.U = i12;
                                    i16 = iHeight;
                                }
                            }
                            i13++;
                            if (i13 >= iZ) {
                                i14 = (getReflowMode() ? i14 + unscaledHeight2 : i14 + iMax2) + 20;
                                iMax2 = 0;
                                i13 = 0;
                            }
                            if (isMovingPage()) {
                                int i26 = (int) (f * this.mScale);
                                int i27 = this.av;
                                if (i27 >= (i23 + i25) / 2 && i27 <= i25 + i26) {
                                    this.au = i12;
                                    i14 += i6;
                                }
                            }
                        }
                        i12++;
                        iMax = i7;
                        unscaledHeight = i6;
                    }
                    i6 = unscaledHeight;
                    i7 = iMax;
                    i12++;
                }
                setMostVisiblePage();
                if (!this.mScaling || iZ < 1 || (i5 = this.mLastLayoutColumns) < 1 || i5 == iZ) {
                    z2 = false;
                } else {
                    scrollBy(this.mAllPagesRect.centerX() - this.n.centerX(), 0);
                    int i28 = this.lastMostVisibleChild;
                    if (i28 != -1) {
                        scrollToPage(i28, true);
                    }
                    z2 = true;
                }
                this.mLastLayoutColumns = iZ;
                this.p.set(this.mAllPagesRect);
                this.lastMostVisibleChild = this.U;
                positionSelectionHandles();
                NoteEditor noteEditor = this.W;
                if (noteEditor != null) {
                    noteEditor.move();
                }
                if (this.aE) {
                    this.aE = false;
                }
                handleStartPage();
                triggerRender();
                if (z2) {
                    new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.DocView.14
                        @Override // java.lang.Runnable
                        public void run() {
                            DocView.this.layoutNow();
                        }
                    });
                }
                final int i29 = this.aG;
                if (i29 != -1) {
                    this.aG = -1;
                    new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.DocView.2
                        @Override // java.lang.Runnable
                        public void run() {
                            DocView.this.scrollToPage(i29, true, false);
                        }
                    });
                }
            }
        }
    }

    public void onLongPress(MotionEvent motionEvent) {
        Point pointA = localPointToScreen(new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
        if (findPageViewContainingPoint(pointA.x, pointA.y, false) == null) {
            return;
        }
        Utilities.hideKeyboard(getContext());
        this.mPressing = true;
        this.ah = this.mScale;
        this.ai = (int) motionEvent.getX();
        this.aj = (int) motionEvent.getY();
        this.ak = getScrollX() + this.ai;
        this.al = getScrollY() + this.aj;
        float f = this.mScale;
        zoomWhilePressing(f, ap * f, false);
    }

    public void onLongPressMoving(MotionEvent motionEvent) {
        if (canEditText() && !this.af) {
            Point pointEventToScreen = eventToScreen((int) motionEvent.getX(), (int) motionEvent.getY());
            DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(pointEventToScreen.x, pointEventToScreen.y, false);
            this.am = docPageViewFindPageViewContainingPoint;
            if (docPageViewFindPageViewContainingPoint == null) {
                return;
            }
            if (!this.ae) {
                float fConvertDpToPixel = Utilities.convertDpToPixel(aq);
                if (Math.abs(motionEvent.getX() - this.ai) >= fConvertDpToPixel || Math.abs(motionEvent.getY() - this.aj) >= fConvertDpToPixel) {
                    this.ae = true;
                }
            }
            if (this.ae) {
                this.am.setCaret(pointEventToScreen.x, pointEventToScreen.y - Utilities.convertDpToPixel(35.0f));
                forceLayout();
                focusInputView();
                resetInputView();
            }
        }
    }

    public void onLongPressRelease() {
        zoomWhilePressing(this.mScale, this.ah, true);
    }

    public void onLongPressReleaseDone() {
        SOSelectionLimits selectionLimits = getSelectionLimits();
        if (selectionLimits == null) {
            Log.e("DocView", "NULL selection limits in onLongPressReleaseDone");
        } else {
            if (!selectionLimits.getIsActive() || this.am == null) {
                return;
            }
            this.aC = this.mHostActivity.showKeyboard();
        }
    }

    public void onMovePage(int i, int i2) {
        if (isMovingPage()) {
            this.av = getScrollY() + i2;
            this.aa = true;
            requestLayout();
        }
    }

    public void onNextPrevTrackedChange() {
        this.aI = true;
        Utilities.hideKeyboard(getContext());
    }

    public void onOrientationChange() {
        resetInputView();
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int iWidth = rect.width();
        int iWidth2 = this.mAllPagesRect.width();
        if (iWidth2 <= 0 || iWidth <= 0) {
            requestLayout();
            final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocView.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                    DocView.this.onOrientationChange();
                }
            });
        } else {
            if (!this.aM && this.mLastLayoutColumns == 0 && iWidth2 >= iWidth) {
                this.mForceColumnCount = -1;
                return;
            }
            final float f = (float) iWidth / iWidth2;
            final int scrollY = getScrollY();
            this.mScale *= f;
            scaleChildren();
            requestLayout();
            final ViewTreeObserver viewTreeObserver2 = getViewTreeObserver();
            viewTreeObserver2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocView.7
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    viewTreeObserver2.removeOnGlobalLayoutListener(this);
                    int i = scrollY;
                    DocView.this.scrollBy(0, -(i - ((int) (f * i))));
                    DocView docView = DocView.this;
                    docView.mForceColumnCount = -1;
                    docView.layoutNow();
                }
            });
        }
    }

    public void onReflowScale() {
        if (this.aM) {
            DocPageView docPageView = (DocPageView) getOrCreateChild(0);
            int iR = this.y.getDocType();
            for (int i = 1; i < iR; i++) {
                ((DocPageView) getOrCreateChild(i)).onReflowScale(docPageView);
            }
        }
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        if (this.mPressing) {
            return true;
        }
        float f = this.mScale;
        float fMin = Math.min(Math.max(scaleGestureDetector.getScaleFactor() * f, minScale()), maxScale());
        this.mScale = fMin;
        if (fMin == f) {
            return true;
        }
        scaleChildren();
        float focusX = scaleGestureDetector.getFocusX();
        float focusY = scaleGestureDetector.getFocusY();
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        float f2 = ((int) focusX) + scrollX;
        this.d = (int) ((f2 - (scaleGestureDetector.getScaleFactor() * f2)) + this.d);
        float f3 = ((int) focusY) + scrollY;
        this.e = (int) ((f3 - (scaleGestureDetector.getScaleFactor() * f3)) + this.e);
        requestLayout();
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(@NonNull ScaleGestureDetector scaleGestureDetector) {
        this.mScaling = true;
        hideAllDragHandles();
        if (this.aN == -1) {
            this.aN = getReflowWidth();
        }
        this.e = 0;
        this.d = 0;
        return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(@NonNull ScaleGestureDetector scaleGestureDetector) {
        DocView docView;
        showHandles();
        Rect rect = this.mAllPagesRect;
        if (rect == null || rect.width() == 0 || this.mAllPagesRect.height() == 0) {
            return;
        }
        Rect rect2 = new Rect();
        getGlobalVisibleRect(rect2);
        if (!this.aM && this.mLastLayoutColumns == 0 && this.mAllPagesRect.width() >= rect2.width()) {
            this.mScaling = false;
            return;
        }
        SODoc doc = getDoc();
        int pageCount = getPageCount();
        if (this.aM) {
            pageCount = this.y.getDocType();
        }
        if (!this.aM && pageCount == 1 && this.mLastLayoutColumns == 1 && this.mAllPagesRect.height() > rect2.height()) {
            this.mScaling = false;
            return;
        }
        float fWidth = (float) rect2.width() / this.mAllPagesRect.width();
        float fHeight = (float) rect2.height() / this.mAllPagesRect.height();
        if (this.aM) {
            NUIDocView.currentNUIDocView().onReflowScale();
            final int scrollX = getScrollX();
            final int scrollY = getScrollY();
            final float integer = ((NUIDocView.currentNUIDocView().isPageListVisible() ? getContext().getResources().getInteger(R.integer.sodk_editor_page_width_percentage) / 100.0f : 1.0f) * this.aN) / this.mScale;
            doc.setFlowMode(2, integer);
            final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            docView = this;
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocView.11
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                    DocView docView2 = DocView.this;
                    float f = docView2.mLastReflowWidth / integer;
                    int i = scrollY;
                    docView2.scrollBy(-scrollX, -(i - ((int) (f * i))));
                    DocView.this.mLastReflowWidth = integer;
                }
            });
        } else {
            docView = this;
            float fMin = Math.min(fWidth, fHeight);
            int i = docView.as;
            int i2 = docView.mLastLayoutColumns;
            docView.mScale = (float) rect2.width() / (((i2 - 1) * 20) + (i * i2));
            docView.scaleChildren();
            docView.d = 0;
            docView.e = 0;
            int scaledScrollY = (int) (((((float) rect2.height() / 2) + docView.getScrollY()) * fWidth) - (rect2.height() / 2));
            if (((int) (fMin * docView.p.height())) < rect2.height()) {
                scaledScrollY = 0;
            }
            final int targetScrollY = scaledScrollY;
            final ViewTreeObserver viewTreeObserver2 = docView.getViewTreeObserver();
            viewTreeObserver2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocView.12
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    viewTreeObserver2.removeOnGlobalLayoutListener(this);
                    DocView.this.scrollAfterScaleEnd(0, targetScrollY);
                    DocView.this.requestLayout();
                }
            });
            docView.requestLayout();
        }
        docView.mScaling = false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mScaling || this.mPressing || this.aK || !this.q.isFinished()) {
            return true;
        }
        if (this.aM) {
            f = 0.0f;
        }
        this.d = (int) (this.d - f);
        this.e = (int) (this.e - f2);
        requestLayout();
        return true;
    }

    public void onSelectionChanged() {
        if (getDoc() == null) {
            return;
        }
        updateSelectionLimits();
        scrollSelectionIntoView();
        boolean z = false;
        for (int i = 0; i < getPageCount(); i++) {
            DocPageView docPageView = (DocPageView) getOrCreateChild(i);
            if (docPageView.sizeViewToPage()) {
                z = true;
            }
            docPageView.setNewScale(this.mScale);
        }
        if (z) {
            forceLayout();
        }
        updateDragHandles();
        updateReview();
    }

    public void onShowKeyboard(boolean z) {
        if (z) {
            Point point = this.aA;
            if (point != null && this.aB != null) {
                float f = point.x;
                Point point2 = this.aA;
                int i = point2.y;
                scrollBoxIntoView(this.aB.getPageNumber(), new RectF(f, i, point2.x + 1, i + 1));
                this.aA = null;
                this.aB = null;
            }
            if (this.aC) {
                scrollSelectionIntoView();
                this.aC = false;
            }
        } else {
            NoteEditor noteEditor = this.W;
            if (noteEditor != null) {
                noteEditor.preMoving();
            }
            this.aa = true;
            requestLayout();
        }
        KeyboardVisibilityListener listener = this.keyboardVisibilityListener;
        if (listener != null) {
            listener.onKeyboardVisibilityChanged(z);
        }
    }

    public void onShowPages() {
        int integer = getContext().getResources().getInteger(R.integer.sodk_editor_page_width_percentage);
        applyScaleFactor(integer / (integer + getContext().getResources().getInteger(R.integer.sodk_editor_pagelist_width_percentage)));
        this.ax = true;
    }

    public boolean onSingleTap(float f, float f2, DocPageView docPageView) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.u;
        if (j == 0 || jCurrentTimeMillis - j >= 300) {
            this.u = jCurrentTimeMillis;
            this.v = motionEvent.getX();
            float y = motionEvent.getY();
            this.w = y;
            if (!this.ay) {
                doSingleTap(this.v, y);
            }
            this.x = 1;
        } else {
            this.x = 2;
            if (!this.ay) {
                doDoubleTap(this.v, this.w);
            }
            this.u = 0L;
        }
        this.ay = false;
        return false;
    }

    @Override // com.artifex.sonui.editor.DragHandleListener
    public void onStartDrag(DragHandle dragHandle) {
        this.aK = true;
        if (dragHandle.isSelectionKind()) {
            return;
        }
        DocPageView docPageView = this.mSelectionStartPage;
        if (docPageView == null || docPageView.getSelectionLimits() == null) {
            this.aK = false;
            return;
        }
        this.P = this.mSelectionStartPage.getSelectionLimits().getBox();
        SOBitmap selectionAsBitmap = getDoc().getSelectionAsBitmap();
        this.K = selectionAsBitmap;
        this.H.setImageBitmap(selectionAsBitmap != null ? selectionAsBitmap.getBitmap() : null);
        SOSelectionLimits selectionLimits = getSelectionLimits();
        Point pointPageToView = this.mSelectionStartPage.pageToView((int) selectionLimits.getBox().left, (int) selectionLimits.getBox().top);
        this.L = pointPageToView;
        pointPageToView.offset(this.mSelectionStartPage.getLeft(), this.mSelectionStartPage.getTop());
        this.L.offset(-getScrollX(), -getScrollY());
        Point pointPageToView2 = this.mSelectionStartPage.pageToView((int) selectionLimits.getBox().right, (int) selectionLimits.getBox().bottom);
        this.M = pointPageToView2;
        pointPageToView2.offset(this.mSelectionStartPage.getLeft(), this.mSelectionStartPage.getTop());
        this.M.offset(-getScrollX(), -getScrollY());
        this.S = getDoc().getSelectionNaturalDimensions();
        Point point = this.M;
        int i = point.y;
        Point point2 = this.L;
        this.N = (i - point2.y) / (point.x - point2.x);
        this.O.set(dragHandle.getPosition().x, dragHandle.getPosition().y);
        this.Q = getDoc().getSelectionRotation();
        this.rotationAngleOffset = 0.0f;
        this.H.setRotation(0.0f);
        Point point3 = this.L;
        int i2 = point3.x;
        int i3 = point3.y;
        Point point4 = this.M;
        updateSelectionPreviewBounds(i2, i3, point4.x - i2, point4.y - i3);
        this.J.set(this.I);
        hideAllDragHandles();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (finished()) {
            return true;
        }
        if (!allowTouchWithoutChildren() && getChildCount() <= 0) {
            return true;
        }
        this.ar = true;
        if ((motionEvent.getAction() & 255) == 0) {
            this.f = true;
        }
        if ((motionEvent.getAction() & 255) == 1) {
            if (this.mPressing) {
                onLongPressRelease();
            } else {
                this.f = false;
            }
        }
        if ((motionEvent.getAction() & 255) == 2 && this.mPressing) {
            onLongPressMoving(motionEvent);
        }
        this.h.onTouchEvent(motionEvent);
        this.g.onTouchEvent(motionEvent);
        return true;
    }

    public boolean pagesShowing() {
        return this.ax;
    }

    public void preNextPrevTrackedChange() {
        NoteEditor noteEditor = this.W;
        if (noteEditor != null) {
            noteEditor.preMoving();
        }
    }

    public void releaseBitmaps() {
        this.i = null;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (finished()) {
            return;
        }
        onPreLayout();
        super.requestLayout();
    }

    public void resetInputView() {
        // Standalone Excel editor uses SOEditText instead of the original hidden InputView.
    }

    @Override // java.lang.Runnable
    public void run() {
        int average;
        if (this.q.isFinished()) {
            requestLayout();
            onEndFling();
            this.az = false;
            return;
        }
        this.q.computeScrollOffset();
        int currX = this.q.getCurrX();
        int currY = this.q.getCurrY();
        int i = currX - this.r;
        int i2 = currY - this.s;
        if (this.az) {
            this.t.addValue(i2);
            average = this.t.getAverage();
        } else {
            average = i2;
        }
        this.d += i;
        this.e += average;
        requestLayout();
        this.r += i;
        this.s += i2;
        post(this);
    }

    public void scaleChildren() {
        for (int i = 0; i < getPageCount(); i++) {
            ((DocPageView) getOrCreateChild(i)).setNewScale(this.mScale);
        }
    }

    public void scrollAfterScaleEnd(int i, int i2) {
        scrollTo(i, i2);
    }

    public void scrollBoxIntoView(int i, RectF rectF, boolean z, int i2) {
        Point pointA = calculateScrollOffsetForBox(i, rectF, z, i2);
        smoothScrollBy(pointA.x, pointA.y);
    }

    public void scrollBoxToTop(int i, RectF rectF) {
        smoothScrollBy(0, scrollBoxToTopAmount(i, rectF));
    }

    public int scrollBoxToTopAmount(int i, RectF rectF) {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        rect.offset(0, -rect.top);
        DocPageView docPageView = (DocPageView) getOrCreateChild(i);
        Point pointPageToView = docPageView.pageToView((int) rectF.left, (int) rectF.top);
        int i2 = pointPageToView.y + docPageView.getChildRect().top;
        pointPageToView.y = i2;
        int scrollY = i2 - getScrollY();
        pointPageToView.y = scrollY;
        return rect.top - scrollY;
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        Point pointConstrainScrollBy = this.ar ? constrainScrollBy(i, i2) : new Point(i, i2);
        super.scrollBy(pointConstrainScrollBy.x, pointConstrainScrollBy.y);
    }

    public void scrollEditorIntoView() {
        if (this.W.isVisible()) {
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.offset(0, -rect.top);
            Rect rect2 = this.W.getRect();
            int i = rect2.top;
            int i2 = rect.top;
            int i3 = i < i2 ? i2 - i : 0;
            int i4 = rect2.bottom;
            int i5 = rect.bottom;
            if (i4 > i5) {
                i3 = i5 - i4;
            }
            smoothScrollBy(0, i3);
        }
    }

    public void scrollPointVisible(Point point) {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int i = point.y;
        int i2 = rect.top;
        if (i < i2 || i > (rect.bottom + i2) / 2) {
            smoothScrollBy(0, ((i2 + rect.bottom) / 2) - i);
        }
    }

    public void scrollSelectionIntoView() {
        DocPageView docPageView;
        SOSelectionLimits selectionLimits;
        RectF box;
        RectF rectF;
        if (this.aH == null || (docPageView = this.mSelectionStartPage) == null || (selectionLimits = docPageView.getSelectionLimits()) == null || (box = selectionLimits.getBox()) == null) {
            return;
        }
        int pageNumber = this.mSelectionStartPage.getPageNumber();
        if (this.mHostActivity == null || !this.mHostActivity.showKeyboard()) {
            scrollBoxIntoView(pageNumber, box);
            return;
        }
        if (Utilities.isRTL(getContext())) {
            float f = box.left;
            rectF = new RectF(f, box.top, 1.0f + f, box.bottom);
        } else {
            float f2 = box.right;
            rectF = new RectF(f2 - 1.0f, box.top, f2, box.bottom);
        }
        scrollBoxIntoView(pageNumber, rectF, true);
    }

    public void scrollToPage(int i, boolean z) {
        if (isValidPage(i)) {
            scrollToPage(i, i == getPageCount() - 1, z);
        }
    }

    public Point scrollToPageAmounts(int i, boolean z) {
        int scrollY;
        int iHeight;
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        Point point = new Point();
        point.set(getScrollX(), getScrollY());
        rect.offsetTo(point.x, point.y);
        Rect childRect = ((DocPageView) getOrCreateChild(i)).getChildRect();
        if (childRect.height() > rect.height()) {
            int scrollY2 = getScrollY();
            if (z) {
                scrollY2 -= childRect.top;
                iHeight = childRect.height() - rect.height();
            } else {
                iHeight = childRect.top;
            }
            scrollY = scrollY2 - iHeight;
        } else {
            int i2 = childRect.top;
            if (i2 < rect.top || childRect.bottom > rect.bottom) {
                scrollY = getScrollY();
                if (i2 != 0) {
                    scrollY = ((rect.height() / 2) + scrollY) - ((childRect.bottom + childRect.top) / 2);
                }
            } else {
                scrollY = 0;
            }
        }
        return new Point(0, scrollY);
    }

    public void selectTopLeft() {
        ((DocPageView) getChildAt(0)).selectTopLeft();
    }

    @Override // android.widget.AdapterView
    public void setAdapter(Adapter adapter) {
        this.pageAdapter = (PageAdapter) adapter;
        requestLayout();
    }

    public void setBitmaps(SOBitmap[] sOBitmapArr) {
        setValid(sOBitmapArr[0] != null);
        this.i = sOBitmapArr;
    }

    public void setConfigOptions(ConfigOptions configOptions) {
        this.mConfigOptions = configOptions;
    }

    public void setDoc(SODoc sODoc) {
        this.y = sODoc;
    }

    public void setHost(DocViewHost docViewHost) {
        this.mHostActivity = docViewHost;
    }

    public void setMostVisiblePage() {
        int i;
        if (!this.f || (i = this.U) < 0 || this.mScaling) {
            return;
        }
        this.mHostActivity.setCurrentPage(i);
    }

    public void setReflowMode(boolean z) {
        this.aL = this.aM;
        this.aM = z;
        this.aN = -1;
    }

    public void setReflowWidth() {
        this.aN = getReflowWidth();
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        throw new UnsupportedOperationException(getContext().getString(R.string.sodk_editor_not_supported));
    }

    public void setShowKeyboardListener(KeyboardVisibilityListener listener) {
        this.keyboardVisibilityListener = listener;
    }

    public void setStartPage(int i) {
        this.aF = i;
    }

    public void setValid(boolean z) {
        for (int i = 0; i < getPageCount(); i++) {
            ((DocPageView) getOrCreateChild(i)).setValid(z);
        }
    }

    public void setup(RelativeLayout relativeLayout) {
        setupHandles(relativeLayout);
        setupNoteEditor();
    }

    public void setupHandles(RelativeLayout relativeLayout) {
        this.z = createDragHandle(relativeLayout, 1);
        this.A = createDragHandle(relativeLayout, 2);
        ImageView imageView = new ImageView(getContext());
        this.H = imageView;
        imageView.setAlpha(0.5f);
        relativeLayout.addView(this.H);
        this.H.setVisibility(View.GONE);
        this.H.setAdjustViewBounds(false);
        this.H.setScaleType(ImageView.ScaleType.FIT_XY);
        this.B = createDragHandle(relativeLayout, 3);
        this.C = createDragHandle(relativeLayout, 4);
        this.D = createDragHandle(relativeLayout, 5);
        this.E = createDragHandle(relativeLayout, 6);
        this.F = createDragHandle(relativeLayout, 7);
        this.G = createDragHandle(relativeLayout, 8);
    }

    public void setupNoteEditor() {
        this.W = new NoteEditor((Activity) getContext(), this, this.mHostActivity, new NoteEditor.NoteDataHandler() { // from class: com.artifex.sonui.editor.DocView.13
            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public String getAuthor() {
                return DocView.this.getDoc().getSelectedTrackedChangeAuthor();
            }

            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public String getComment() {
                Context context;
                int i;
                boolean z = false;
                String selectedTrackedChangeComment = DocView.this.getDoc().getSelectedTrackedChangeComment();
                int selectedTrackedChangeType = DocView.this.getDoc().getSelectedTrackedChangeType();
                if (selectedTrackedChangeType == 5) {
                    context = DocView.this.getContext();
                    i = R.string.sodk_editor_deleted_text;
                } else if (selectedTrackedChangeType == 24) {
                    context = DocView.this.getContext();
                    i = R.string.sodk_editor_changed_paragraph_properties;
                } else if (selectedTrackedChangeType == 26) {
                    context = DocView.this.getContext();
                    i = R.string.sodk_editor_changed_run_properties;
                } else if (selectedTrackedChangeType == 28) {
                    context = DocView.this.getContext();
                    i = R.string.sodk_editor_changed_section_properties;
                } else {
                    if (selectedTrackedChangeType != 36) {
                        switch (selectedTrackedChangeType) {
                            case 15:
                                context = DocView.this.getContext();
                                i = R.string.sodk_editor_inserted_text;
                                break;
                            case 16:
                                context = DocView.this.getContext();
                                i = R.string.sodk_editor_inserted_paragraph;
                                break;
                            case 17:
                                context = DocView.this.getContext();
                                i = R.string.sodk_editor_inserted_table_cell;
                                break;
                            case 18:
                                context = DocView.this.getContext();
                                i = R.string.sodk_editor_inserted_table_row;
                                break;
                            default:
                                switch (selectedTrackedChangeType) {
                                    case 31:
                                        context = DocView.this.getContext();
                                        i = R.string.sodk_editor_changed_table_cell_properties;
                                        break;
                                    case 32:
                                        context = DocView.this.getContext();
                                        i = R.string.sodk_editor_changed_table_grid;
                                        break;
                                    case 33:
                                        context = DocView.this.getContext();
                                        i = R.string.sodk_editor_changed_table_properties;
                                        break;
                                    default:
                                        z = true;
                                        break;
                                }
                                break;
                        }
                        DocView.this.W.setCommentEditable(z);
                        return selectedTrackedChangeComment;
                    }
                    context = DocView.this.getContext();
                    i = R.string.sodk_editor_changed_table_row_properties;
                }
                selectedTrackedChangeComment = context.getString(i);
                z = false;
                DocView.this.W.setCommentEditable(z);
                return selectedTrackedChangeComment;
            }

            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public String getDate() {
                return DocView.this.getDoc().getSelectedTrackedChangeDate();
            }

            @Override // com.artifex.sonui.editor.NoteEditor.NoteDataHandler
            public void setComment(String str) {
                SODoc doc = DocView.this.getDoc();
                if (doc != null) {
                    doc.setSelectionAnnotationComment(str);
                }
            }
        });
    }

    public boolean shouldAddHistory(int i, int i2, float f) {
        History.HistoryItem historyItemCurrent = getHistory().current();
        if (historyItemCurrent == null) {
            return true;
        }
        Rect rect = new Rect();
        getLocalVisibleRect(rect);
        rect.offset(historyItemCurrent.getScrollX(), historyItemCurrent.getScrollY());
        Rect rect2 = new Rect();
        getLocalVisibleRect(rect2);
        rect2.offset(i, i2);
        return !new Rect(rect).intersect(rect2);
    }

    public boolean shouldLayout() {
        boolean z = true;
        boolean z2 = this.aL != this.aM;
        if (this.mScale != this.ab || this.ac != getScrollX() || this.ad != getScrollY()) {
            z2 = true;
        }
        if (this.aa) {
            this.aa = false;
        } else {
            z = z2;
        }
        if (z) {
            this.ab = this.mScale;
            this.ac = getScrollX();
            this.ad = getScrollY();
        }
        return z;
    }

    public boolean shouldPreclearSelection() {
        return true;
    }

    public void showHandles() {
        SOSelectionLimits selectionLimits = getSelectionLimits();
        boolean z = false;
        if (selectionLimits != null) {
            boolean z2 = selectionLimits.getIsActive() && !selectionLimits.getIsCaret() && !NUIDocView.currentNUIDocView().getIsComposing();
            boolean z3 = selectionLimits.getIsActive() && getDoc().getSelectionCanBeResized() && !selectionLimits.getIsCaret();
            boolean z4 = selectionLimits.getIsActive() && getDoc().getSelectionCanBeAbsolutelyPositioned();
            if (selectionLimits.getIsActive() && getDoc().getSelectionCanBeRotated()) {
                z = true;
            }
            showSelectionHandles(z2);
            showResizeHandles(z3);
            showDragHandle(z4);
        } else {
            showSelectionHandles(false);
            showResizeHandles(false);
            showDragHandle(false);
        }
            showRotateHandle(z);
    }

    public void showKeyboardAfterDoubleTap(Point point) {
        if (Utilities.isLandscapePhone(getContext())) {
            return;
        }
        showKeyboardAndScroll(point);
    }

    public void showKeyboardAndScroll(Point point) {
        DocPageView docPageViewFindPageViewContainingPoint = findPageViewContainingPoint(point.x, point.y, false);
        if (docPageViewFindPageViewContainingPoint == null) {
            return;
        }
        this.aB = null;
        this.aA = null;
        if (this.mHostActivity.showKeyboard()) {
            this.aB = docPageViewFindPageViewContainingPoint;
            this.aA = docPageViewFindPageViewContainingPoint.screenToPage(point);
        }
    }

    public void smoothScrollBy(int i, int i2, int i3) {
        if (i == 0 && i2 == 0) {
            return;
        }
        this.s = 0;
        this.r = 0;
        this.q.startScroll(0, 0, i, i2, i3);
        post(this);
    }

    public boolean tapToFocus() {
        return true;
    }

    public void triggerRender() {
        if (this.i[0] == null) {
            return;
        }
        this.k = true;
        if (this.l == 0) {
            renderVisiblePages();
        }
    }

    public void updateDragHandles() {
        this.H.setImageBitmap(null);
        this.H.setVisibility(View.GONE);
        SOBitmap sOBitmap = this.K;
        if (sOBitmap != null && sOBitmap.getBitmap() != null) {
            this.K.getBitmap().recycle();
            this.K = null;
        }
        showHandles();
        positionSelectionHandles();
    }

    public void updateReview() {
        boolean z = this.aI;
        this.aI = false;
        boolean z2 = this.aJ;
        this.aJ = false;
        if (!getDoc().getSelectionHasAssociatedPopup() && !getDoc().selectionIsReviewable()) {
            if (this.W.isVisible()) {
                Utilities.hideKeyboard(getContext());
                this.W.hide();
                if (z) {
                    scrollSelectionIntoView();
                    return;
                }
                return;
            }
            return;
        }
        this.x = 0;
        this.W.show(getSelectionLimits(), this.mSelectionStartPage);
        this.W.move();
        if (z) {
            scrollEditorIntoView();
            scrollSelectionIntoView();
        }
        if (z2) {
            this.W.focus();
            this.mHostActivity.showKeyboard();
        }
    }

    public void zoomWhilePressing(final float f, final float f2, final boolean z) {
        this.af = true;
        this.ag = false;
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() { // from class: com.artifex.sonui.editor.DocView.8
            @Override // java.lang.Runnable
            public void run() {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                if (!DocView.this.ag && jCurrentTimeMillis2 > jCurrentTimeMillis + ((long) DocView.ao)) {
                    jCurrentTimeMillis2 = jCurrentTimeMillis + ((long) DocView.ao);
                    DocView.this.ag = true;
                }
                if (jCurrentTimeMillis2 > jCurrentTimeMillis + ((long) DocView.ao)) {
                    DocView.this.af = false;
                    if (z) {
                        DocView docView = DocView.this;
                        docView.mPressing = false;
                        docView.f = false;
                        DocView.this.onLongPressReleaseDone();
                        return;
                    }
                    return;
                }
                float f3 = (float) (jCurrentTimeMillis2 - jCurrentTimeMillis) / DocView.ao;
                DocView docView2 = DocView.this;
                float f4 = f;
                docView2.mScale = ExcelMath.lerp(f2, f4, f3, f4);
                docView2.scaleChildren();
                float f5 = DocView.this.ak;
                DocView docView3 = DocView.this;
                int i = (int) ((f5 * docView3.mScale) / docView3.ah);
                float f6 = DocView.this.al;
                DocView docView4 = DocView.this;
                int i2 = (int) ((f6 * docView4.mScale) / docView4.ah);
                int i3 = DocView.this.ai;
                int i4 = DocView.this.aj;
                int scrollX = DocView.this.getScrollX();
                int scrollY = DocView.this.getScrollY();
                DocView.this.d -= (i - i3) - scrollX;
                DocView.this.e -= (i2 - i4) - scrollY;
                DocView.this.requestLayout();
                final Runnable animationStep = this;
                final ViewTreeObserver viewTreeObserver = DocView.this.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.artifex.sonui.editor.DocView.8.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                        handler.post(animationStep);
                    }
                });
            }
        }, an);
    }

    public void scrollBoxIntoView(int i, RectF rectF, boolean z) {
        scrollBoxIntoView(i, rectF, z, 0);
    }

    public void scrollBoxIntoView(int i, RectF rectF) {
        scrollBoxIntoView(i, rectF, false);
    }

    public void onLayoutChanged() {
    }

    public void onPreLayout() {
    }

    public void onReloadFile() {
    }

    public void scrollToPage(int i, boolean z, boolean z2) {
        int i2 = scrollToPageAmounts(i, z).y;
        if (i2 != 0) {
            if (z2) {
                scrollBy(0, i2);
            } else {
                smoothScrollBy(0, i2);
            }
        }
    }

    public void doPageMenu(int i) {
    }

    public void onPageMoved(int i) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    public void startMovingPage(int i) {
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy(i, i2, 400);
    }

    private void showDragHandle(boolean visible) {
        setDragHandleVisible(this.F, visible);
    }

    private boolean canRenderPages() {
        if (this.finished || this.y == null || this.i == null) {
            return false;
        }
        int i = 0;
        while (true) {
            SOBitmap[] sOBitmapArr = this.i;
            if (i >= sOBitmapArr.length) {
                return true;
            }
            SOBitmap sOBitmap = sOBitmapArr[i];
            if (sOBitmap == null || sOBitmap.getBitmap() == null || this.i[i].getBitmap().isRecycled()) {
                return false;
            }
            i++;
        }
    }

    private void showRotateHandle(boolean visible) {
        setDragHandleVisible(this.G, visible);
    }

    public Point scrollToPageAmounts(int i) {
        return scrollToPageAmounts(i, i == getPageCount() - 1);
    }

    private int getPageMoveTarget(int pageIndex) {
        int i2 = this.at;
        if (i2 != -1) {
            return pageIndex > i2 ? i2 : i2 - 1;
        }
        int i3 = this.au;
        if (i3 == -1) {
            return getDoc().getDocType() - 1;
        }
        return pageIndex <= i3 ? i3 : i3 + 1;
    }

    private Point calculateScrollOffsetForBox(int pageIndex, RectF box, boolean scrollHorizontally, int margin) {
        int i3;
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        int i4 = 0;
        rect.offset(0, -rect.top);
        rect.inset(margin, margin);
        DocPageView docPageView = (DocPageView) getOrCreateChild(pageIndex);
        Point pointPageToView = docPageView.pageToView((int) box.left, (int) box.bottom);
        Rect childRect = docPageView.getChildRect();
        int i5 = pointPageToView.y + childRect.top;
        pointPageToView.y = i5;
        pointPageToView.y = i5 - getScrollY();
        int i6 = pointPageToView.x + childRect.left;
        pointPageToView.x = i6;
        int scrollX = i6 - getScrollX();
        pointPageToView.x = scrollX;
        int i7 = pointPageToView.y;
        int i8 = rect.top;
        int i9 = (i7 < i8 || i7 > rect.bottom) ? ((i8 + rect.bottom) / 2) - i7 : 0;
        if (scrollHorizontally && (scrollX < (i3 = rect.left) || scrollX > rect.right)) {
            i4 = ((i3 + rect.right) / 2) - scrollX;
        }
        return new Point(i4, i9);
    }

    private Point localPointToScreen(Point point) {
        Point point2 = new Point(point);
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        point2.offset(rect.left, rect.top);
        return point2;
    }

    private DragHandle createDragHandle(RelativeLayout parent, int handleKind) {
        DragHandle dragHandle;
        if (handleKind == 7) {
            dragHandle = new DragHandle(getContext(), R.layout.sodk_editor_drag_handle, handleKind);
        } else {
            dragHandle = handleKind == 8 ? new DragHandle(getContext(), R.layout.sodk_editor_rotate_handle, handleKind) : new DragHandle(getContext(), R.layout.sodk_editor_resize_handle, handleKind);
        }
        parent.addView(dragHandle);
        dragHandle.show(false);
        dragHandle.setDragHandleListener(this);
        return dragHandle;
    }

    private void applyScaleFactor(float scaleFactor) {
        this.mScale *= scaleFactor;
        scaleChildren();
        scrollTo(getScrollX(), (int) (getScrollY() * scaleFactor));
        requestLayout();
    }

    private void addChildViewInLayout(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-2, -2);
        }
        addViewInLayout(view, 0, layoutParams, true);
    }

    private void setChildScale(View view, Float scale) {
        ((DocPageView) view).setNewScale(scale.floatValue());
    }

    private void updateDragPreview(DragHandle dragHandle) {
        int i = (dragHandle.getPosition().x - this.O.x) + this.L.x;
        int i2 = (dragHandle.getPosition().y - this.O.y) + this.L.y;
        Point point = this.M;
        int i3 = point.x;
        Point point2 = this.L;
        updateSelectionPreviewBounds(i, i2, i3 - point2.x, point.y - point2.y);
    }

    private void positionDragHandle(DragHandle dragHandle, DocPageView pageView, int pageX, int pageY) {
        if (dragHandle != null) {
            Point pointPageToView = pageView.pageToView(pageX, pageY);
            pointPageToView.offset(pageView.getChildRect().left, pageView.getChildRect().top);
            pointPageToView.offset(-getScrollX(), -getScrollY());
            dragHandle.measure(0, 0);
            pointPageToView.offset((-dragHandle.getMeasuredWidth()) / 2, (-dragHandle.getMeasuredHeight()) / 2);
            Point pointOffsetCircleToEdge = dragHandle.offsetCircleToEdge();
            pointPageToView.offset(pointOffsetCircleToEdge.x, pointOffsetCircleToEdge.y);
            dragHandle.moveTo(pointPageToView.x, pointPageToView.y);
        }
    }

    private void setDragHandleVisible(DragHandle dragHandle, boolean visible) {
        if (dragHandle != null) {
            dragHandle.show(visible);
        }
    }

    private void showSelectionHandles(boolean visible) {
        setDragHandleVisible(this.z, visible);
        setDragHandleVisible(this.A, visible);
    }

    public DocView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.finished = false;
        this.c = new SparseArray<>(3);
        this.mScale = 1.0f;
        this.f = false;
        this.j = 0;
        this.k = false;
        this.l = 0;
        this.m = new Rect();
        this.n = new Rect();
        this.o = new Point();
        this.mAllPagesRect = new Rect();
        this.p = new Rect();
        this.mLastLayoutColumns = 1;
        this.lastMostVisibleChild = -1;
        this.u = 0L;
        this.x = 0;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.I = new Rect();
        this.J = new Rect();
        this.K = null;
        this.O = new Point();
        this.Q = 0.0f;
        this.rotationAngleOffset = 0.0f;
        this.U = -1;
        this.V = new Rect();
        this.W = null;
        this.aa = false;
        this.ab = 0.0f;
        this.ac = 0;
        this.ad = 0;
        this.mPressing = false;
        this.ae = false;
        this.af = false;
        this.ag = false;
        this.ar = true;
        this.as = 0;
        this.at = -1;
        this.au = -1;
        this.av = -1;
        this.mConfigOptions = null;
        this.mForceColumnCount = -1;
        this.mLastReflowWidth = 0.0f;
        this.mHostActivity = null;
        this.ax = false;
        this.ay = false;
        this.az = false;
        this.aC = false;
        this.keyboardVisibilityListener = null;
        this.aE = true;
        this.aF = 0;
        this.aG = -1;
        this.aH = null;
        this.aI = false;
        this.aJ = false;
        this.aK = false;
        this.aL = false;
        this.aM = false;
        this.aN = -1;
        initialize(context);
    }

    public DocView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.finished = false;
        this.c = new SparseArray<>(3);
        this.mScale = 1.0f;
        this.f = false;
        this.j = 0;
        this.k = false;
        this.l = 0;
        this.m = new Rect();
        this.n = new Rect();
        this.o = new Point();
        this.mAllPagesRect = new Rect();
        this.p = new Rect();
        this.mLastLayoutColumns = 1;
        this.lastMostVisibleChild = -1;
        this.u = 0L;
        this.x = 0;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.I = new Rect();
        this.J = new Rect();
        this.K = null;
        this.O = new Point();
        this.Q = 0.0f;
        this.rotationAngleOffset = 0.0f;
        this.U = -1;
        this.V = new Rect();
        this.W = null;
        this.aa = false;
        this.ab = 0.0f;
        this.ac = 0;
        this.ad = 0;
        this.mPressing = false;
        this.ae = false;
        this.af = false;
        this.ag = false;
        this.ar = true;
        this.as = 0;
        this.at = -1;
        this.au = -1;
        this.av = -1;
        this.mConfigOptions = null;
        this.mForceColumnCount = -1;
        this.mLastReflowWidth = 0.0f;
        this.mHostActivity = null;
        this.ax = false;
        this.ay = false;
        this.az = false;
        this.aC = false;
        this.keyboardVisibilityListener = null;
        this.aE = true;
        this.aF = 0;
        this.aG = -1;
        this.aH = null;
        this.aI = false;
        this.aJ = false;
        this.aK = false;
        this.aL = false;
        this.aM = false;
        this.aN = -1;
        initialize(context);
    }


    private void showResizeHandles(boolean visible) {
        setDragHandleVisible(this.B, visible);
        setDragHandleVisible(this.C, visible);
        setDragHandleVisible(this.D, visible);
        setDragHandleVisible(this.E, visible);
    }
}
