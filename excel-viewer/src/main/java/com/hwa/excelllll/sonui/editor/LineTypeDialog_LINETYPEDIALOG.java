package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.apps.e;
import kankan.wheel.widget.d;

/* JADX INFO: loaded from: classes.dex */
public class LineTypeDialog {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final int[] f4663a = {0, 2, 5, 1, 6, 7, 3, 8, 9, 4, 10};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static final float[][] f4664b = {new float[]{1.0f, 0.0f}, new float[]{1.0f, 1.0f}, new float[]{1.0f, 3.0f}, new float[]{3.0f, 1.0f}, new float[]{4.0f, 3.0f}, new float[]{8.0f, 3.0f}, new float[]{3.0f, 1.0f, 1.0f, 1.0f}, new float[]{4.0f, 3.0f, 1.0f, 3.0f}, new float[]{8.0f, 3.0f, 1.0f, 3.0f}, new float[]{3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f}, new float[]{8.0f, 3.0f, 1.0f, 3.0f, 1.0f, 3.0f}};

    public static void show(Context context, View view, final SODoc sODoc) {
        int selectionLineType = sODoc.getSelectionLineType();
        View viewInflate = View.inflate(context, R.layout.sodk_editor_line_width_dialog, null);
        final WheelView wheelView = (WheelView) viewInflate.findViewById(R.id.wheel);
        wheelView.setViewAdapter(new LineTypeAdapter(context, f4663a));
        wheelView.setVisibleItems(5);
        int i = 0;
        wheelView.setCurrentItem(0);
        while (true) {
            int[] iArr = f4663a;
            if (i >= iArr.length) {
                wheelView.d(new d() { // from class: com.artifex.sonui.editor.LineTypeDialog.1
                    @Override // kankan.wheel.widget.d
                    public void b(WheelView wheelView2) {
                        sODoc.setSelectionLineType(LineTypeDialog.f4663a[wheelView2.getCurrentItem()]);
                    }

                    @Override // kankan.wheel.widget.d
                    public void a(WheelView wheelView2) {
                    }
                });
                NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate, -2, -2);
                nUIPopupWindow.setFocusable(true);
                nUIPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.artifex.sonui.editor.LineTypeDialog.2
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        wheelView.b();
                    }
                });
                nUIPopupWindow.showAsDropDown(view, 30, 30);
                return;
            }
            if (iArr[i] == selectionLineType) {
                wheelView.setCurrentItem(i);
            }
            i++;
        }
    }

    public static class LineTypeAdapter implements e {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private Context f4667a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private int[] f4668b;

        public LineTypeAdapter(Context context, int[] iArr) {
            this.f4668b = iArr;
            this.f4667a = context;
        }

        @Override // kankan.wheel.widget.apps.e
        public View getEmptyItem(View view, ViewGroup viewGroup) {
            return null;
        }

        @Override // kankan.wheel.widget.apps.e
        public View getItem(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) this.f4667a.getSystemService("layout_inflater")).inflate(R.layout.sodk_editor_line_type_item, viewGroup, false);
            }
            int i2 = this.f4668b[i];
            ((DottedLineView) view.findViewById(R.id.bar)).setPattern(LineTypeDialog.f4664b[i]);
            return view;
        }

        @Override // kankan.wheel.widget.apps.e
        public int getItemsCount() {
            return this.f4668b.length;
        }

        @Override // kankan.wheel.widget.apps.e
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        }

        @Override // kankan.wheel.widget.apps.e
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        }
    }
}
