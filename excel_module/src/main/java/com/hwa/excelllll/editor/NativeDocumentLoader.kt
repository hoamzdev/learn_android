package com.hwa.excelllll.editor

import android.content.Context
import android.net.Uri
import com.hwa.excelllll.SODoc

/**
 * Adapter between Android file access and the native Artifex open routine.
 *
 * The actual implementation must call the existing native/open API from your .so.
 * Do not invent a new JNI symbol just to satisfy this interface.
 */
interface NativeDocumentLoader {
    fun open(context: Context, uri: Uri): SODoc
}
