package com.hwa.excelllll.excelsoui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.ActionMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/** Small viewer-only utility replacement; the original app-wide Utilities class was intentionally removed. */
public final class Utilities {
    private Utilities() {}

    public static ActionMode.Callback editFieldDlpHandler = null;

    public static int convertDpToPixel(float dp) {
        return Math.round(dp * android.content.res.Resources.getSystem().getDisplayMetrics().density);
    }

    public static Point getScreenSize(Context context) {
        android.util.DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    public static void hideKeyboard(Context context) {
        if (context instanceof Activity) {
            View v = ((Activity) context).getCurrentFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    public static void showKeyboard(Context context) {
        if (context instanceof Activity) {
            View v = ((Activity) context).getCurrentFocus();
            if (v != null) {
                v.post(() -> {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                });
            }
        }
    }

    public static boolean isLandscapePhone(Context context) {
        android.content.res.Configuration c = context.getResources().getConfiguration();
        return c.smallestScreenWidthDp < 600 && c.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isPhoneDevice(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp < 600;
    }

    public static boolean isRTL(Context context) {
        return context.getResources().getConfiguration().getLayoutDirection() == android.view.View.LAYOUT_DIRECTION_RTL;
    }

    public static int[] screenToWindow(int[] point, Context context) {
        if (!(context instanceof Activity)) return point;
        View decor = ((Activity) context).getWindow().getDecorView();
        int[] loc = new int[2];
        decor.getLocationOnScreen(loc);
        return new int[]{point[0] - loc[0], point[1] - loc[1]};
    }

    public static String formatDateForLocale(Context context, String date, String format) {
        return date == null ? "" : date;
    }

    public static SODataLeakHandlers getDataLeakHandlers() {
        return null;
    }
}
