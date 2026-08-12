package com.hwa.excelllll.editor

import android.graphics.Bitmap
import com.hwa.excelllll.core.SOBitmap

/**
 * Creates the SOBitmap object expected by SOPage's render bridge.
 *
 * The implementation must use the SOBitmap constructor/factory that exists
 * in the actual Artifex Java/Kotlin bridge. This interface intentionally
 * contains no invented native method.
 */
interface ExcelBitmapProvider {
    fun create(bitmap: Bitmap): SOBitmap
}
