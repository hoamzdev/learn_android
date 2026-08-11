package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.util.Base64;
import defpackage.e6;
import defpackage.h;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class SOFileState {
    public static boolean mDontAutoOpen;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f4764a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private String f4765b;
    private String c;
    private final String d;
    private boolean e;
    private long f;
    private String g;
    private final SOFileDatabase h;
    private int i;

    public SOFileState(SOFileState sOFileState) {
        this.i = 0;
        this.f4764a = sOFileState.f4764a;
        this.d = sOFileState.d;
        this.f = sOFileState.f;
        this.e = sOFileState.e;
        this.h = sOFileState.h;
        this.g = sOFileState.g;
        this.f4765b = sOFileState.f4765b;
        this.c = sOFileState.getForeignData();
        this.i = sOFileState.i;
    }

    private static String a(String str) {
        if (str == null) {
            return "--null--";
        }
        if (str.isEmpty()) {
            return "--empty--";
        }
        try {
            return Base64.encodeToString(str.getBytes("UTF-8"), 0);
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    private static String b(String str) {
        if (str.equals("--null--")) {
            return null;
        }
        if (str.equals("--empty--")) {
            return "";
        }
        try {
            return new String(Base64.decode(str, 0), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static void clearAutoOpen(Context context) {
        Utilities.setStringPreference(Utilities.getPreferencesObject(context, Utilities.generalStore), "autoOpen", "");
    }

    public static SOFileState fromString(String str, SOFileDatabase sOFileDatabase) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] strArrSplit = str.split("\\|");
        if (strArrSplit.length < 6) {
            return null;
        }
        String strB = strArrSplit.length >= 1 ? b(strArrSplit[0]) : "";
        String strB2 = strArrSplit.length >= 2 ? b(strArrSplit[1]) : "";
        long j = strArrSplit.length >= 3 ? Long.parseLong(strArrSplit[2], 10) : 0L;
        boolean zEquals = strArrSplit.length >= 4 ? strArrSplit[3].equals("TRUE") : false;
        String strB3 = strArrSplit.length >= 5 ? b(strArrSplit[4]) : "";
        String strB4 = strArrSplit.length >= 6 ? b(strArrSplit[5]) : "";
        String strB5 = strArrSplit.length >= 7 ? b(strArrSplit[6]) : "";
        SOFileState sOFileState = new SOFileState(strB, strB2, strB4, j, zEquals, strB3, sOFileDatabase, strArrSplit.length >= 8 ? Integer.parseInt(strArrSplit[7], 10) : 0);
        sOFileState.setForeignData(strB5);
        return sOFileState;
    }

    public static SOFileState getAutoOpen(Context context) {
        String stringPreference = Utilities.getStringPreference(Utilities.getPreferencesObject(context, Utilities.generalStore), "autoOpen", "");
        if (stringPreference == null || stringPreference.isEmpty()) {
            return null;
        }
        return fromString(stringPreference, SOFileDatabase.getDatabase());
    }

    public static void setAutoOpen(Context context, SOFileState sOFileState) {
        if (mDontAutoOpen) {
            return;
        }
        Utilities.setStringPreference(Utilities.getPreferencesObject(context, Utilities.generalStore), "autoOpen", toString(sOFileState));
    }

    public static String toString(SOFileState sOFileState) {
        StringBuilder sbT = e6.t(h.p(e6.t(h.p(new StringBuilder(""), a(sOFileState.f4764a), "|")), a(sOFileState.d), "|"));
        sbT.append(String.valueOf(sOFileState.f));
        sbT.append("|");
        StringBuilder sbT2 = e6.t(h.p(e6.t(h.p(e6.t(h.p(e6.t(h.p(e6.t(sbT.toString()), sOFileState.e ? "TRUE" : "FALSE", "|")), a(sOFileState.g), "|")), a(sOFileState.f4765b), "|")), a(sOFileState.c), "|"));
        sbT2.append(String.valueOf(sOFileState.i));
        sbT2.append("|");
        return sbT2.toString();
    }

    public void closeFile() {
        com.artifex.solib.a.e(this.d);
        updateAccess();
        a();
    }

    public SOFileState copy() {
        return fromString(toString(this), SOFileDatabase.getDatabase());
    }

    public void deleteThumbnailFile() {
        if (com.artifex.solib.a.b(this.g)) {
            com.artifex.solib.a.e(this.g);
        }
    }

    public String getForeignData() {
        return this.c;
    }

    public String getInternalPath() {
        return this.d;
    }

    public long getLastAccess() {
        return this.f;
    }

    public String getOpenedPath() {
        return this.f4765b;
    }

    public int getPageNumber() {
        return this.i;
    }

    public String getThumbnail() {
        return this.g;
    }

    public String getUserPath() {
        return this.f4764a;
    }

    public boolean hasChanges() {
        return this.e;
    }

    public boolean isTemplate() {
        String str = this.f4764a;
        return str == null || str.isEmpty();
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0019  */
    public void openFile(boolean z) {
        if (com.artifex.solib.a.b(this.d)) {
            String str = this.f4764a;
            if (str == null) {
                str = this.f4765b;
            }
            if (com.artifex.solib.a.a(str) > this.f) {
                com.artifex.solib.a.a(this.f4764a, this.d, true);
            }
        } else {
            com.artifex.solib.a.a(this.f4764a, this.d, true);
        }
        if (z) {
            this.f4764a = null;
        } else {
            updateAccess();
            a();
        }
    }

    public void saveFile() {
        if (com.artifex.solib.a.b(this.d)) {
            com.artifex.solib.a.b(this.d, this.f4764a);
            this.f4765b = this.f4764a;
            this.e = false;
            updateAccess();
            a();
        }
    }

    public void setForeignData(String str) {
        this.c = str;
        a();
    }

    public void setHasChanges(boolean z) {
        this.e = z;
    }

    public void setOpenedPath(String str) {
        this.f4765b = str;
    }

    public void setPageNumber(int i) {
        this.i = i;
    }

    public void setThumbnail(String str) {
        this.g = str;
        a();
    }

    public void setUserPath(String str) {
        this.f4764a = str;
    }

    public void updateAccess() {
        this.f = System.currentTimeMillis();
    }

    private void a() {
        String str;
        SOFileDatabase sOFileDatabase = this.h;
        if (sOFileDatabase == null || (str = this.f4764a) == null) {
            return;
        }
        sOFileDatabase.setValue(str, this);
    }

    public SOFileState(String str, String str2, SOFileDatabase sOFileDatabase, int i) {
        this(str, str2, str, 0L, false, "", sOFileDatabase, i);
    }

    private SOFileState(String str, String str2, String str3, long j, boolean z, String str4, SOFileDatabase sOFileDatabase, int i) {
        this.f4764a = str;
        this.d = str2;
        this.f = j;
        this.e = z;
        this.h = sOFileDatabase;
        this.g = str4;
        this.f4765b = str3;
        this.c = null;
        this.i = i;
    }
}
