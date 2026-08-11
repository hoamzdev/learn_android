package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.e;
import kankan.wheel.widget.d;

/* JADX INFO: loaded from: classes.dex */
public class InkLineWidthDialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final float[] f4650a = {0.25f, 0.5f, 1.0f, 1.5f, 3.0f, 4.5f, 6.0f, 8.0f, 12.0f, 18.0f, 24.0f};

    public interface WidthChangedListener {
        void onWidthChanged(float f);
    }

    public static void show(Context context, View view, float f, final WidthChangedListener widthChangedListener) {
        View viewInflate = View.inflate(context, R.layout.sodk_editor_line_width_dialog, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.wheel);
        wheelView.setViewAdapter(new LineWidthAdapter(context, f4650a));
        wheelView.setVisibleItems(5);
        int i = 0;
        wheelView.setCurrentItem(0);
        while (true) {
            float[] fArr = f4650a;
            if (i >= fArr.length) {
                wheelView.d(new d() { // from class: com.artifex.sonui.editor.InkLineWidthDialog.1
                    @Override // kankan.wheel.widget.d
                    public void b(WheelView wheelView2) {
                        widthChangedListener.onWidthChanged(InkLineWidthDialog.f4650a[wheelView2.getCurrentItem()]);
                    }

                    @Override // kankan.wheel.widget.d
                    public void a(WheelView wheelView2) {
                    }
                });
                NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
                nUIPopupWindow.setFocusable(true);
                nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.InkLineWidthDialog.2
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        wheelView.b();
                    }
                });
                nUIPopupWindow.showAsDropDown(view, 30, 30);
                return;
            }
            if (fArr[i] == f) {
                wheelView.setCurrentItem(i);
            }
            i++;
        }
    }

    public static class LineWidthAdapter implements e {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private Context f4653a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private float[] f4654b;

        public LineWidthAdapter(Context context, float[] fArr) {
            this.f4654b = fArr;
            this.f4653a = context;
        }

        @Override // kankan.wheel.widget.apps.e
        public View getEmptyItem(View view, ViewGroup viewGroup) {
            return null;
        }

        @Override // kankan.wheel.widget.apps.e
        public View getItem(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) this.f4653a.getSystemService("layout_inflater")).inflate(R.layout.sodk_editor_line_width_item, viewGroup, false);
            }
            float f = InkLineWidthDialog.f4650a[i];
            ((SOTextView) view.findViewById(R.id.value)).setText(Utilities.formatFloat(f) + " pt");
            int i2 = (int) ((f * 3.0f) / 2.0f);
            if (i2 < 1) {
                i2 = 1;
            }
            View viewFindViewById = view.findViewById(R.id.bar);
            viewFindViewById.getLayoutParams().height = i2;
            viewFindViewById.setLayoutParams(viewFindViewById.getLayoutParams());
            return view;
        }

        @Override // kankan.wheel.widget.apps.e
        public int getItemsCount() {
            return this.f4654b.length;
        }

        @Override // kankan.wheel.widget.apps.e
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        }

        @Override // kankan.wheel.widget.apps.e
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        }
    }
}
