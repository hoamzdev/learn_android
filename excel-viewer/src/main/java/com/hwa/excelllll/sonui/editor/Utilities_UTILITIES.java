package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SODoc;
import com.artifex.solib.k;
import com.artifex.solib.m;
import com.artifex.solib.n;
import com.facebook.ads.AdError;
import defpackage.e6;
import defpackage.h;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class Utilities {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String f4891a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static AlertDialog f4892b = null;
    private static SODataLeakHandlers c = null;
    private static SOPersistentStorage d = null;
    private static SODocSession.SODocSessionLoadListenerCustom e = null;
    public static ActionMode.Callback editFieldDlpHandler = null;
    private static final Set<String> f;
    public static final String generalStore = "general";
    public static SigningFactoryListener mSigningFactory;

    public interface SigningFactoryListener {
        NUIPKCS7Signer getSigner(Activity activity);

        NUIPKCS7Verifier getVerifier(Activity activity);
    }

    public interface passwordDialogListener {
        void onCancel();

        void onOK(String str);
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add("ar");
        hashSet.add("dv");
        hashSet.add("fa");
        hashSet.add("ha");
        hashSet.add("he");
        hashSet.add("iw");
        hashSet.add("ji");
        hashSet.add("ps");
        hashSet.add("ur");
        hashSet.add("yi");
        f = Collections.unmodifiableSet(hashSet);
        editFieldDlpHandler = new ActionMode.Callback() { // from class: com.artifex.sonui.editor.Utilities.6
            @Override // android.view.ActionMode.Callback
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                ConfigOptions configOptionsA = ConfigOptions.a();
                if (!configOptionsA.h()) {
                    menu.removeItem(android.R.id.shareText);
                }
                if (!configOptionsA.j()) {
                    menu.removeItem(android.R.id.cut);
                    menu.removeItem(android.R.id.copy);
                }
                if (configOptionsA.i()) {
                    return true;
                }
                menu.removeItem(android.R.id.paste);
                return true;
            }

            @Override // android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
            }
        };
    }

    private static String a(String str) {
        String strG = com.artifex.solib.a.g(str);
        String str2 = strG.compareToIgnoreCase("xps") == 0 ? "application/vnd.ms-xpsdocument" : null;
        if (strG.compareToIgnoreCase("cbz") == 0) {
            str2 = "application/x-cbz";
        }
        return strG.compareToIgnoreCase("svg") == 0 ? "image/svg+xml" : str2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:100:0x0150  */
    /* JADX WARN: Code duplicated, block: B:106:0x0164  */
    /* JADX WARN: Code duplicated, block: B:109:0x016f  */
    /* JADX WARN: Code duplicated, block: B:115:0x0185  */
    /* JADX WARN: Code duplicated, block: B:116:0x0188  */
    /* JADX WARN: Code duplicated, block: B:64:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:82:0x0112  */
    public static int colorForDocExt(Context context, String str) {
        int i;
        switch (str) {
            case "bmp":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "cbz":
                i = R.color.sodk_editor_header_cbz_color;
                break;
            case "csv":
                i = R.color.sodk_editor_header_txt_color;
                break;
            case "doc":
                i = R.color.sodk_editor_header_doc_color;
                break;
            case "emf":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "fb2":
                i = R.color.sodk_editor_header_fb2_color;
                break;
            case "gif":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "hwp":
                i = R.color.sodk_editor_header_hwp_color;
                break;
            case "jpg":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "pdf":
                i = R.color.sodk_editor_header_pdf_color;
                break;
            case "png":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "pps":
            case "ppt":
                i = R.color.sodk_editor_header_ppt_color;
                break;
            case "svg":
                i = R.color.sodk_editor_header_svg_color;
                break;
            case "tif":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "txt":
                i = R.color.sodk_editor_header_txt_color;
                break;
            case "wmf":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "xls":
                i = R.color.sodk_editor_header_xls_color;
                break;
            case "xps":
                i = R.color.sodk_editor_header_xps_color;
                break;
            case "docm":
            case "docx":
            case "dotm":
            case "dotx":
                i = R.color.sodk_editor_header_doc_color;
                break;
            case "epub":
                i = R.color.sodk_editor_header_epub_color;
                break;
            case "jpeg":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "potm":
            case "potx":
            case "ppsm":
            case "ppsx":
            case "pptm":
            case "pptx":
                i = R.color.sodk_editor_header_ppt_color;
                break;
            case "tiff":
                i = R.color.sodk_editor_header_image_color;
                break;
            case "xlsm":
            case "xlsx":
            case "xltm":
            case "xltx":
                i = R.color.sodk_editor_header_xls_color;
                break;
            case "xhtml":
                i = R.color.sodk_editor_header_fb2_color;
                break;
            default:
                i = R.color.sodk_editor_header_unknown_color;
                break;
        }
        return context.getColor(i);
    }

    public static int colorForDocType(Context context, String str) {
        return colorForDocExt(context, com.artifex.solib.a.g(str));
    }

    public static int convertDpToPixel(float f2) {
        return Math.round((Resources.getSystem().getDisplayMetrics().densityDpi / 160.0f) * f2);
    }

    public static ProgressDialog createAndShowWaitSpinner(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException unused) {
        }
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        progressDialog.setContentView(R.layout.sodk_editor_wait_spinner);
        return progressDialog;
    }

    public static void dismissCurrentAlert() {
        AlertDialog alertDialog = f4892b;
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } catch (Exception unused) {
            }
            f4892b = null;
        }
    }

    public static ProgressDialog displayPleaseWaitWithCancel(Context context, final Runnable runnable) {
        ProgressDialogDelayed progressDialogDelayed = new ProgressDialogDelayed(context, AdError.NETWORK_ERROR_CODE);
        progressDialogDelayed.setIndeterminate(true);
        progressDialogDelayed.setCancelable(false);
        progressDialogDelayed.setTitle(context.getString(R.string.sodk_editor_please_wait));
        progressDialogDelayed.setMessage(null);
        if (runnable != null) {
            progressDialogDelayed.setButton(-2, context.getString(R.string.sodk_editor_cancel), new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.7
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    runnable.run();
                }
            });
        }
        progressDialogDelayed.show();
        return progressDialogDelayed;
    }

    public static String formatDateForLocale(Context context, String str, String str2) {
        try {
            Date date = new SimpleDateFormat(str2).parse(str);
            String string = Settings.System.getString(context.getContentResolver(), "date_format");
            return (TextUtils.isEmpty(string) ? DateFormat.getDateFormat(context) : new SimpleDateFormat(string)).format(date) + " " + DateFormat.getTimeFormat(context).format(date);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String formatFloat(float f2) {
        if (f2 % 1.0f == 0.0f) {
            return String.format("%.0f", Float.valueOf(f2));
        }
        String string = Double.toString(Math.abs(f2));
        int iIndexOf = string.indexOf(46);
        int length = string.length();
        StringBuilder sb = new StringBuilder("%.");
        sb.append((length - iIndexOf) - 1);
        sb.append("f");
        return String.format(sb.toString(), Float.valueOf(f2));
    }

    public static Map<String, ?> getAllStringPreferences(Object obj) {
        SOPersistentStorage sOPersistentStorage = d;
        if (sOPersistentStorage != null) {
            return sOPersistentStorage.getAllStringPreferences(obj);
        }
        Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
        throw new RuntimeException();
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int i = applicationInfo.labelRes;
        return i == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(i);
    }

    public static SODataLeakHandlers getDataLeakHandlers() {
        return c;
    }

    public static File getDownloadDirectory(Context context) {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    public static String getFileStateForPrint() {
        return f4891a;
    }

    public static String getMimeType(String str) {
        String strG = com.artifex.solib.a.g(str);
        String mimeTypeFromExtension = strG != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(strG) : null;
        return mimeTypeFromExtension == null ? a(str) : mimeTypeFromExtension;
    }

    public static String getOpenErrorDescription(Context context, int i) {
        return i != 5 ? String.format(context.getString(R.string.sodk_editor_doc_open_error), Integer.valueOf(i)) : context.getString(R.string.sodk_editor_doc_uses_unsupported_enc);
    }

    public static SOPersistentStorage getPersistentStorage() {
        return d;
    }

    public static Object getPreferencesObject(Context context, String str) {
        SOPersistentStorage sOPersistentStorage = d;
        if (sOPersistentStorage != null) {
            return sOPersistentStorage.getStorageObject(context, str);
        }
        Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
        throw new RuntimeException();
    }

    public static Point getRealScreenSize(Context context) {
        return a((WindowManager) context.getSystemService("window"));
    }

    public static File getRootDirectory(Context context) {
        return Environment.getExternalStorageDirectory();
    }

    public static String getSDCardPath(Context context) {
        String strConcat;
        File file;
        File[] externalFilesDirs = context.getExternalFilesDirs(null);
        Log.d("sdcard", String.format("getSDCardPath: there are %d external locations", Integer.valueOf(externalFilesDirs.length)));
        if (externalFilesDirs.length <= 1 || (file = externalFilesDirs[1]) == null) {
            strConcat = "getSDCardPath: too few external locations";
        } else {
            String absolutePath = file.getAbsolutePath();
            if (absolutePath != null) {
                Log.d("sdcard", "getSDCardPath: possible sd card path is ".concat(absolutePath));
                int iIndexOf = absolutePath.toLowerCase().indexOf("/android/");
                if (iIndexOf > 0) {
                    String strSubstring = absolutePath.substring(0, iIndexOf);
                    Log.d("sdcard", "getSDCardPath: SD card is at ".concat(strSubstring));
                    return strSubstring;
                }
                strConcat = "getSDCardPath: did not find /Android/ in ".concat(absolutePath);
            } else {
                strConcat = "getSDCardPath: 2nd path is null";
            }
        }
        Log.d("sdcard", strConcat);
        return null;
    }

    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public static String getSelectionFontName(SODoc sODoc) {
        String selectionFontName = sODoc.getSelectionFontName();
        if (selectionFontName != null) {
            return (selectionFontName.startsWith("-") || selectionFontName.startsWith("+")) ? "" : selectionFontName;
        }
        return selectionFontName;
    }

    public static SODocSession.SODocSessionLoadListenerCustom getSessionLoadListener() {
        return e;
    }

    public static NUIPKCS7Signer getSigner(Activity activity) {
        SigningFactoryListener signingFactoryListener = mSigningFactory;
        if (signingFactoryListener != null) {
            return signingFactoryListener.getSigner(activity);
        }
        return null;
    }

    public static String getStringPreference(Object obj, String str, String str2) {
        SOPersistentStorage sOPersistentStorage = d;
        if (sOPersistentStorage != null) {
            return sOPersistentStorage.getStringPreference(obj, str, str2);
        }
        Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
        throw new RuntimeException();
    }

    public static NUIPKCS7Verifier getVerifier(Activity activity) {
        SigningFactoryListener signingFactoryListener = mSigningFactory;
        if (signingFactoryListener != null) {
            return signingFactoryListener.getVerifier(activity);
        }
        return null;
    }

    public static void hideKeyboard(Context context) {
        Activity activity;
        View currentFocus;
        if (!Activity.class.isInstance(context) || (currentFocus = (activity = (Activity) context).getCurrentFocus()) == null) {
            return;
        }
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0133, code lost:
    
        if (r1.equals("jpg") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0147, code lost:
    
        if (r1.equals("gif") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0150, code lost:
    
        if (r1.equals("fb2") != false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0154, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_fb2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x015b, code lost:
    
        if (r1.equals("emf") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x016f, code lost:
    
        if (r1.equals("csv") != false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0173, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_txt;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0022, code lost:
    
        if (r1.equals("xltm") != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0185, code lost:
    
        if (r1.equals("bmp") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0189, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_image;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
    
        if (r1.equals("xlsx") != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
    
        if (r1.equals("xlsm") != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0038, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_xlsx;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003f, code lost:
    
        if (r1.equals("tiff") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0049, code lost:
    
        if (r1.equals("pptx") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0053, code lost:
    
        if (r1.equals("ppts") != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005d, code lost:
    
        if (r1.equals("pptm") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0067, code lost:
    
        if (r1.equals("ppsx") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0071, code lost:
    
        if (r1.equals("ppsm") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007b, code lost:
    
        if (r1.equals("potx") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0085, code lost:
    
        if (r1.equals("potm") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x008f, code lost:
    
        if (r1.equals("jpeg") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a4, code lost:
    
        if (r1.equals("dotx") != false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ad, code lost:
    
        if (r1.equals("dotm") != false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b6, code lost:
    
        if (r1.equals("docx") != false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00bf, code lost:
    
        if (r1.equals("docm") != false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00c3, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_docx;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x000f, code lost:
    
        if (r1.equals("xhtml") != false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e0, code lost:
    
        if (r1.equals("wmf") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00ea, code lost:
    
        if (r1.equals("txt") != false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00f4, code lost:
    
        if (r1.equals("tif") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0109, code lost:
    
        if (r1.equals("ppt") != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x010d, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_ppt;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0114, code lost:
    
        if (r1.equals("pps") != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0118, code lost:
    
        return com.artifex.sonui.editor.R.drawable.sodk_editor_icon_pptx;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x011f, code lost:
    
        if (r1.equals("png") != false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0019, code lost:
    
        if (r1.equals("xltx") != false) goto L19;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int iconForDocExt(String r1) {
        /*
            Method dump skipped, instruction units count: 552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.artifex.sonui.editor.Utilities.iconForDocExt(java.lang.String):int");
    }

    public static int iconForDocType(String str) {
        return iconForDocExt(com.artifex.solib.a.g(str));
    }

    public static boolean isChromebook(Context context) {
        return context.getPackageManager().hasSystemFeature("org.chromium.arc.device_management");
    }

    public static boolean isDocTypeSupported(Context context, String str) {
        return !k.b((Activity) context, str);
    }

    public static boolean isEmulator() {
        String str = Build.HARDWARE;
        return "goldfish".equals(str) || "ranchu".equals(str) || Build.FINGERPRINT.contains("generic");
    }

    public static boolean isLandscapePhone(Context context) {
        if (!isPhoneDevice(context)) {
            return false;
        }
        Point realScreenSize = getRealScreenSize(context);
        return realScreenSize.x > realScreenSize.y;
    }

    public static boolean isPhoneDevice(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return configuration.smallestScreenWidthDp < context.getResources().getInteger(R.integer.sodk_editor_minimum_tablet_width);
    }

    public static boolean isRTL(Context context) {
        InputMethodSubtype currentInputMethodSubtype = ((InputMethodManager) context.getSystemService("input_method")).getCurrentInputMethodSubtype();
        if (currentInputMethodSubtype == null) {
            return false;
        }
        return f.contains(currentInputMethodSubtype.getLanguageTag());
    }

    public static boolean isValidFilename(String str) {
        return (str == null || str.isEmpty() || str.startsWith(".") || str.startsWith(" ") || str.endsWith(" ")) ? false : true;
    }

    public static void passwordDialog(final Activity activity, final passwordDialogListener passworddialoglistener) {
        activity.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.Utilities.5
            @Override // java.lang.Runnable
            public void run() {
                Utilities.dismissCurrentAlert();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.sodk_editor_alert_dialog_style);
                View viewInflate = LayoutInflater.from(activity).inflate(R.layout.sodk_editor_password_prompt, (ViewGroup) null);
                builder.setCancelable(false);
                final SOEditText sOEditText = (SOEditText) viewInflate.findViewById(R.id.editTextDialogUserInput);
                builder.setView(viewInflate);
                builder.setTitle("");
                builder.setPositiveButton(activity.getResources().getString(R.string.sodk_editor_ok), new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utilities.hideKeyboard(activity, sOEditText);
                        dialogInterface.dismiss();
                        AlertDialog unused = Utilities.f4892b = null;
                        if (passworddialoglistener != null) {
                            passworddialoglistener.onOK(sOEditText.getText().toString());
                        }
                    }
                });
                builder.setNegativeButton(activity.getResources().getString(R.string.sodk_editor_cancel), new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.5.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utilities.hideKeyboard(activity, sOEditText);
                        dialogInterface.dismiss();
                        AlertDialog unused = Utilities.f4892b = null;
                        passwordDialogListener passworddialoglistener2 = passworddialoglistener;
                        if (passworddialoglistener2 != null) {
                            passworddialoglistener2.onCancel();
                        }
                    }
                });
                AlertDialog unused = Utilities.f4892b = builder.create();
                Utilities.f4892b.show();
            }
        });
    }

    public static String preInsertImage(Context context, String str) {
        int i;
        int i2;
        int i3;
        int i4;
        m mVar = new m(str);
        Bitmap bitmapCreateScaledBitmap = null;
        try {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(mVar, null, options);
                i = options.outHeight;
                try {
                    int i5 = options.outWidth;
                    mVar.close();
                    i2 = i5;
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    mVar.close();
                    i2 = 0;
                }
            } catch (Exception e3) {
                e = e3;
                i = 0;
            }
            m mVar2 = new m(str);
            try {
                int iA = new android.supportv1.d.a(mVar2).a();
                if (iA == 3) {
                    i4 = 180;
                } else if (iA != 6) {
                    i4 = iA != 8 ? 0 : 270;
                } else {
                    i4 = 90;
                }
                mVar2.close();
                i3 = i4;
            } catch (IOException unused) {
                mVar2.close();
                i3 = 0;
            } catch (Throwable th) {
                mVar2.close();
                throw th;
            }
            if (i3 == 0 && i2 <= 1536 && i <= 1536) {
                return str;
            }
            m mVar3 = new m(str);
            try {
                try {
                    new BitmapFactory.Options().inPreferredConfig = Bitmap.Config.RGB_565;
                    bitmapCreateScaledBitmap = BitmapFactory.decodeStream(mVar3);
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                mVar3.close();
                if (bitmapCreateScaledBitmap == null) {
                    return str;
                }
                if (i2 > 1536 || i > 1536) {
                    float f2 = i2;
                    float f3 = i;
                    float fMin = Math.min(1536.0f / f2, 1536.0f / f3);
                    i2 = (int) (f2 * fMin);
                    i = (int) (fMin * f3);
                    bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateScaledBitmap, i2, i, false);
                }
                int i6 = i2;
                Bitmap bitmapCreateBitmap = bitmapCreateScaledBitmap;
                int i7 = i;
                if (i3 != 0) {
                    Matrix matrix = new Matrix();
                    matrix.preRotate(i3);
                    bitmapCreateBitmap = Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, i6, i7, matrix, true);
                }
                String str2 = com.artifex.solib.a.c(context) + File.separator + "scaled_or_rotated_" + UUID.randomUUID() + ".png";
                n nVar = new n(str2);
                bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, nVar);
                nVar.flush();
                nVar.close();
                return str2;
            } catch (Throwable th2) {
                mVar3.close();
                throw th2;
            }
        } catch (Throwable th3) {
            mVar.close();
            throw th3;
        }
    }

    public static String removeExtension(String str) {
        String name;
        int iLastIndexOf;
        File file = new File(str);
        return (!file.isDirectory() && (iLastIndexOf = (name = file.getName()).lastIndexOf(46)) > 0) ? new File(file.getParent(), name.substring(0, iLastIndexOf)).getPath() : str;
    }

    public static void removePreference(Object obj, String str) {
        SOPersistentStorage sOPersistentStorage = d;
        if (sOPersistentStorage != null) {
            sOPersistentStorage.removePreference(obj, str);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static int[] screenToWindow(int[] iArr, Context context) {
        int[] iArr2 = new int[2];
        ((Activity) context).getWindow().getDecorView().getLocationOnScreen(iArr2);
        return new int[]{iArr[0] - iArr2[0], iArr[1] - iArr2[1]};
    }

    public static void setDataLeakHandlers(SODataLeakHandlers sODataLeakHandlers) {
        c = sODataLeakHandlers;
    }

    public static void setFileStateForPrint(String str) {
        f4891a = str;
    }

    public static void setFilenameText(SOTextView sOTextView, String str) {
        String strK;
        Context context = sOTextView.getContext();
        int iLastIndexOf = str.lastIndexOf(".");
        String strA = a(R.color.sodk_editor_filename_textcolor, context);
        String strA2 = a(R.color.sodk_editor_extension_textcolor, context);
        if (iLastIndexOf >= 0) {
            StringBuilder sbU = e6.u("<font color='", strA, "'>");
            sbU.append(str.substring(0, iLastIndexOf));
            sbU.append("</font><font color='");
            sbU.append(strA2);
            sbU.append("'>");
            sbU.append(str.substring(iLastIndexOf));
            sbU.append("</font>");
            strK = sbU.toString();
        } else {
            strK = h.k("<font color='", strA, "'>", str, "</font>");
        }
        sOTextView.setText(Html.fromHtml(strK, 0), TextView.BufferType.SPANNABLE);
    }

    public static void setPersistentStorage(SOPersistentStorage sOPersistentStorage) {
        d = sOPersistentStorage;
    }

    public static void setSessionLoadListener(SODocSession.SODocSessionLoadListenerCustom sODocSessionLoadListenerCustom) {
        e = sODocSessionLoadListenerCustom;
    }

    public static void setSigningFactoryListener(SigningFactoryListener signingFactoryListener) {
        mSigningFactory = signingFactoryListener;
    }

    public static void setStringPreference(Object obj, String str, String str2) {
        SOPersistentStorage sOPersistentStorage = d;
        if (sOPersistentStorage != null) {
            sOPersistentStorage.setStringPreference(obj, str, str2);
        } else {
            Log.d("Utilities", "No implementation of the SOPersistentStorage interface found");
            throw new RuntimeException();
        }
    }

    public static void showKeyboard(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(2, 1);
    }

    public static void showMessage(Activity activity, String str, String str2) {
        showMessage(activity, str, str2, activity.getResources().getString(R.string.sodk_editor_ok));
    }

    public static void showMessageAndFinish(final Activity activity, final String str, final String str2) {
        activity.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.Utilities.2
            @Override // java.lang.Runnable
            public void run() {
                Utilities.dismissCurrentAlert();
                AlertDialog unused = Utilities.f4892b = new AlertDialog.Builder(activity, R.style.sodk_editor_alert_dialog_style).setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton(R.string.sodk_editor_ok, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AlertDialog unused2 = Utilities.f4892b = null;
                        activity.finish();
                    }
                }).create();
                Utilities.f4892b.show();
            }
        });
    }

    public static void showMessageAndWait(final Activity activity, final String str, final String str2, final Runnable runnable) {
        activity.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.Utilities.3
            @Override // java.lang.Runnable
            public void run() {
                Utilities.dismissCurrentAlert();
                AlertDialog unused = Utilities.f4892b = new AlertDialog.Builder(activity, R.style.sodk_editor_alert_dialog_style).setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton(R.string.sodk_editor_ok, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AlertDialog unused2 = Utilities.f4892b = null;
                        runnable.run();
                    }
                }).create();
                Utilities.f4892b.show();
            }
        });
    }

    public static void yesNoMessage(final Activity activity, final String str, final String str2, final String str3, final String str4, final Runnable runnable, final Runnable runnable2) {
        activity.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.Utilities.4
            @Override // java.lang.Runnable
            public void run() {
                Utilities.dismissCurrentAlert();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.sodk_editor_alert_dialog_style);
                builder.setTitle(str);
                builder.setMessage(str2);
                builder.setCancelable(false);
                builder.setPositiveButton(str3, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AlertDialog unused = Utilities.f4892b = null;
                        Runnable runnable3 = runnable;
                        if (runnable3 != null) {
                            runnable3.run();
                        }
                    }
                });
                builder.setNegativeButton(str4, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.4.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AlertDialog unused = Utilities.f4892b = null;
                        Runnable runnable3 = runnable2;
                        if (runnable3 != null) {
                            runnable3.run();
                        }
                    }
                });
                AlertDialog unused = Utilities.f4892b = builder.create();
                Utilities.f4892b.show();
            }
        });
    }

    public static Point getRealScreenSize(Activity activity) {
        return a(activity.getWindowManager());
    }

    public static void showMessage(final Activity activity, final String str, final String str2, final String str3) {
        activity.runOnUiThread(new Runnable() { // from class: com.artifex.sonui.editor.Utilities.1
            @Override // java.lang.Runnable
            public void run() {
                Utilities.dismissCurrentAlert();
                AlertDialog unused = Utilities.f4892b = new AlertDialog.Builder(activity, R.style.sodk_editor_alert_dialog_style).setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton(str3, new DialogInterface.OnClickListener() { // from class: com.artifex.sonui.editor.Utilities.1.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AlertDialog unused2 = Utilities.f4892b = null;
                    }
                }).create();
                Utilities.f4892b.show();
            }
        });
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private static Point a(WindowManager windowManager) {
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    private static String a(int i, Context context) {
        String hexString = Integer.toHexString(context.getResources().getColor(i));
        int length = hexString.length();
        return length > 6 ? "#".concat(hexString.substring(length - 6)) : "rgba(0, 0, 0, 0);";
    }
}
