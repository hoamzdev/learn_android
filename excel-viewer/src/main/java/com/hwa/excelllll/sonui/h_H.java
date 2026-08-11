package com.hwa.excelllll.sonui;

import android.content.Context;
import android.content.SharedPreferences;
import com.artifex.sonui.editor.SOPersistentStorage;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class h implements SOPersistentStorage {
    @Override // com.artifex.sonui.editor.SOPersistentStorage
    public Map<String, ?> getAllStringPreferences(Object obj) {
        return ((SharedPreferences) obj).getAll();
    }

    @Override // com.artifex.sonui.editor.SOPersistentStorage
    public Object getStorageObject(Context context, String str) {
        return context.getSharedPreferences(str, 0);
    }

    @Override // com.artifex.sonui.editor.SOPersistentStorage
    public String getStringPreference(Object obj, String str, String str2) {
        return ((SharedPreferences) obj).getString(str, str2);
    }

    @Override // com.artifex.sonui.editor.SOPersistentStorage
    public void removePreference(Object obj, String str) {
        SharedPreferences.Editor editorEdit = ((SharedPreferences) obj).edit();
        editorEdit.remove(str);
        editorEdit.commit();
    }

    @Override // com.artifex.sonui.editor.SOPersistentStorage
    public void setStringPreference(Object obj, String str, String str2) {
        SharedPreferences.Editor editorEdit = ((SharedPreferences) obj).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }
}
