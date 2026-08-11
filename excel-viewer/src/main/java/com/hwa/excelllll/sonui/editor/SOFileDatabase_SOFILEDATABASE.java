package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import defpackage.e6;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class SOFileDatabase {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static Context f4761a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static Object f4762b;
    private static SOFileDatabase c;
    private static String d;
    private static String e;

    public class StateAndKey {
        public String key;
        public SOFileState state;

        public StateAndKey() {
        }
    }

    private void a(String str) {
        Utilities.removePreference(a(), str);
    }

    private String b(String str) {
        if (!com.artifex.solib.a.b(d)) {
            com.artifex.solib.a.c(d);
        }
        if (!com.artifex.solib.a.b(d)) {
            return "";
        }
        return d + UUID.randomUUID().toString() + str;
    }

    public static SOFileDatabase getDatabase() {
        return c;
    }

    public static void init(Context context) {
        if (c == null) {
            f4761a = context;
            c = new SOFileDatabase();
            f4762b = Utilities.getPreferencesObject(f4761a, "fileDatabase2");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(com.artifex.solib.a.c(f4761a));
        String str = File.separator;
        d = e6.q(sb, str, ".tmpint", str);
        e = com.artifex.solib.a.c(f4761a) + str + ".thumbs" + str;
        if (!com.artifex.solib.a.b(d)) {
            com.artifex.solib.a.c(d);
        }
        if (com.artifex.solib.a.b(e)) {
            return;
        }
        com.artifex.solib.a.c(e);
    }

    public static String uniqueThumbFilePath() {
        if (!com.artifex.solib.a.b(e)) {
            com.artifex.solib.a.c(e);
        }
        if (!com.artifex.solib.a.b(e)) {
            return "";
        }
        return e + UUID.randomUUID().toString() + ".png";
    }

    public void clearAll() {
        for (Map.Entry<String, ?> entry : Utilities.getAllStringPreferences(a()).entrySet()) {
            SOFileState value = getValue(entry.getKey());
            if (value != null) {
                com.artifex.solib.a.e(value.getInternalPath());
                com.artifex.solib.a.e(value.getThumbnail());
                a(entry.getKey());
            }
        }
    }

    public void deleteEntry(String str) {
        a(str);
    }

    public ArrayList<StateAndKey> getStatesAndKeys() {
        ArrayList<StateAndKey> arrayList = new ArrayList<>();
        Map<String, ?> allStringPreferences = Utilities.getAllStringPreferences(a());
        if (allStringPreferences != null) {
            for (Map.Entry<String, ?> entry : allStringPreferences.entrySet()) {
                StateAndKey stateAndKey = new StateAndKey();
                stateAndKey.state = getValue(entry.getKey());
                stateAndKey.key = entry.getKey();
                arrayList.add(stateAndKey);
            }
        }
        return arrayList;
    }

    public SOFileState getValue(String str) {
        return SOFileState.fromString(Utilities.getStringPreference(a(), str, ""), this);
    }

    public void setValue(String str, SOFileState sOFileState) {
        Utilities.setStringPreference(a(), str, SOFileState.toString(sOFileState));
    }

    public SOFileState stateForPath(String str, boolean z, boolean z2) {
        SOFileState value = !z2 ? getValue(str) : null;
        if (value == null || value.getUserPath().isEmpty()) {
            String strB = b("." + com.artifex.solib.a.g(str));
            if (!strB.equals("")) {
                value = new SOFileState(str, strB, this, 0);
                com.artifex.solib.a.e(strB);
                if (!z) {
                    setValue(str, value);
                }
            }
        }
        return value;
    }

    private Object a() {
        return f4762b;
    }

    public SOFileState stateForPath(String str, boolean z) {
        return stateForPath(str, z, false);
    }
}
