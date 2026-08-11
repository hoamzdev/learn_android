package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOLinkData;
import com.artifex.solib.k;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class a implements PopupWindow.OnDismissListener {
    private static a f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final Context f4915a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final View f4916b;
    private final SODoc c;
    private final b d;
    private Button e;
    private NUIPopupWindow g;

    /* JADX INFO: renamed from: com.artifex.sonui.editor.a$a, reason: collision with other inner class name */
    public class C0058a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f4922a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        int f4923b;
        String c;
        String d;
        int e;
        float f;
        float g;
        int h;

        private C0058a(int i, int i2, int i3, String str, String str2, float f, float f2) {
            this.f4922a = i;
            this.f4923b = i2;
            this.c = str;
            this.d = str2;
            this.f = f;
            this.g = f2;
            this.h = i3;
        }
    }

    public interface b {
        void a(SOLinkData sOLinkData);
    }

    public class c extends BaseAdapter {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private ArrayList<C0058a> f4925b = new ArrayList<>();
        private Map<Integer, C0058a> c = new HashMap();
        private Context d;

        public c(Context context) {
            this.d = context;
        }

        public void a(C0058a c0058a) {
            this.c.put(Integer.valueOf(c0058a.f4922a), c0058a);
            int i = 0;
            C0058a c0058a2 = c0058a;
            while (c0058a2 != null) {
                int i2 = c0058a2.f4923b;
                if (i2 == 0) {
                    break;
                }
                i++;
                c0058a2 = this.c.get(Integer.valueOf(i2));
            }
            c0058a.e = i;
            this.f4925b.add(c0058a);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.f4925b.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.f4925b.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            C0058a c0058a = this.f4925b.get(i);
            View viewInflate = ((Activity) this.d).getLayoutInflater().inflate(R.layout.sodk_editor_toc_list_item, viewGroup, false);
            SOTextView sOTextView = (SOTextView) viewInflate.findViewById(R.id.toc_item);
            sOTextView.setText(c0058a.c);
            sOTextView.setPadding((Utilities.convertDpToPixel(40.0f) * c0058a.e) + sOTextView.getPaddingLeft(), sOTextView.getPaddingTop(), sOTextView.getPaddingRight(), sOTextView.getPaddingBottom());
            return viewInflate;
        }
    }

    public a(Context context, SODoc sODoc, View view, b bVar) {
        this.f4915a = context;
        this.f4916b = view;
        this.c = sODoc;
        this.d = bVar;
    }

    public static void b() {
        a aVar = f;
        if (aVar != null) {
            aVar.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.g.dismiss();
        f = null;
    }

    public void a() {
        if (f != null) {
            return;
        }
        f = this;
        View viewInflate = LayoutInflater.from(this.f4915a).inflate(R.layout.sodk_editor_table_of_contents, (ViewGroup) null);
        ListView listView = (ListView) viewInflate.findViewById(R.id.List);
        final c cVar = new c(this.f4915a);
        listView.setAdapter((ListAdapter) cVar);
        k.a(this.c, new k.a() { // from class: com.artifex.sonui.editor.a.1
            @Override // com.artifex.solib.k.a
            public void a(int i, int i2, int i3, String str, String str2, float f2, float f3) {
                cVar.a(new C0058a(i, i2, i3, str, str2, f2, f3));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.artifex.sonui.editor.a.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                C0058a c0058a = (C0058a) cVar.getItem(i);
                if (c0058a.h >= 0) {
                    float f2 = c0058a.f;
                    float f3 = c0058a.g;
                    SOLinkData sOLinkData = new SOLinkData(c0058a.h, new RectF(f2, f3, f2 + 1.0f, 1.0f + f3));
                    if (Utilities.isPhoneDevice(a.this.f4915a)) {
                        a.this.d();
                    }
                    a.this.d.a(sOLinkData);
                }
            }
        });
        Button button = (Button) viewInflate.findViewById(R.id.cancel_button);
        this.e = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.artifex.sonui.editor.a.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                a.this.d();
            }
        });
        NUIPopupWindow nUIPopupWindow = new NUIPopupWindow(viewInflate);
        this.g = nUIPopupWindow;
        nUIPopupWindow.setFocusable(true);
        this.g.setClippingEnabled(false);
        this.g.setOnDismissListener(this);
        c();
    }

    public void c() {
        int i;
        int i2;
        int i3;
        int i4;
        Point screenSize = Utilities.getScreenSize(this.f4915a);
        if (Utilities.isPhoneDevice(this.f4915a)) {
            i3 = screenSize.x;
            i = screenSize.y;
            this.e.setVisibility(0);
            this.g.setBackgroundDrawable(null);
            i2 = 0;
            i4 = 0;
        } else {
            int dimension = (int) this.f4915a.getResources().getDimension(R.dimen.sodk_editor_toc_offsetx);
            int dimension2 = (int) this.f4915a.getResources().getDimension(R.dimen.sodk_editor_toc_offsety);
            int i5 = screenSize.x / 2;
            i = screenSize.y / 2;
            this.e.setVisibility(8);
            this.g.setBackgroundDrawable(this.f4915a.getDrawable(R.drawable.sodk_editor_table_of_contents));
            i2 = dimension;
            i3 = i5 - dimension;
            i4 = dimension2;
        }
        boolean zIsShowing = this.g.isShowing();
        NUIPopupWindow nUIPopupWindow = this.g;
        if (zIsShowing) {
            nUIPopupWindow.update(i2, i4, i3, i);
            return;
        }
        nUIPopupWindow.setWidth(i3);
        this.g.setHeight(i);
        this.g.showAtLocation(this.f4916b, 0, i2, i4);
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        d();
    }
}
