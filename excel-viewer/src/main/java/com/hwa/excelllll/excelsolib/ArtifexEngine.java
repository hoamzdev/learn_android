package com.hwa.excelllll.excelsolib;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.hwa.excelllll.SOLib;

/**
 * Common Excel document engine utilities.
 * Non-Excel document dispatch and PDF support were removed.
 */
public abstract class ArtifexEngine {
    protected static ClipboardHandler clipboardHandler;
    private static ClipboardManager clipboardManager;
    private static int clipboardGeneration;

    public static void setClipboardText(String text) {
        if (text == null) return;
        if (clipboardHandler != null) {
            clipboardHandler.setText(text);
        } else if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", text));
        }
    }

    public static String getClipboardText(Context context) {
        if (clipboardHandler != null) return clipboardHandler.getText();
        if (clipboardManager == null || !clipboardManager.hasPrimaryClip()) return "";
        CharSequence value = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(context);
        return value == null ? "" : value.toString();
    }


    public static boolean hasClipboardText() {
        return clipboardHandler != null
                ? clipboardHandler.hasText()
                : clipboardManager != null && clipboardManager.hasPrimaryClip();
    }

    public static void initializeClipboard(Context context) {
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.addPrimaryClipChangedListener(() -> clipboardGeneration++);
        }
    }

    public static int getClipboardGeneration() {
        return clipboardGeneration;
    }

    public abstract SOBitmap createBitmap(int width, int height);
    public abstract SODoc openDocument(String path, DocLoadListener listener, Context context);

    public static SOLib getLibrary(Activity activity) {
        return SOLib.a(activity);
    }

    public static int incrementClipboardGeneration() {
        return clipboardGeneration++;
    }

    public static boolean isExcelDocument(Activity activity, String path) {
        return getLibrary(activity).isDocTypeExcel(path);
    }

    public static String[] getVersionInfo(Activity activity) {
        return getLibrary(activity).getVersionInfo();
    }

    public static boolean isSupportedSpreadsheet(Activity activity, String path) {
        return !isExcelDocument(activity, path) && ArtifexFileUtils.hasExtension(path, "xlsx")
                || ArtifexFileUtils.hasExtension(path, "xls");
    }

    public static boolean isSupportedPresentation(Activity activity, String path) {
        return false;
    }

    public static boolean isSupportedTextDocument(Activity activity, String path) {
        return false;
    }

    public static boolean supportsPdf(Activity activity) {
        return false;
    }
}
