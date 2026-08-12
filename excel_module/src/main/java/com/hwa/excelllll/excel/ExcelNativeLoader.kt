package com.hwa.excelllll.excel

import com.hwa.excelllll.SODoc

/**
 * Implement this using the exact static/native document-open method found
 * in the supplied Artifex source/JNI bridge.
 *
 * Keeping it as an adapter means the rest of the rewritten Kotlin code does
 * not depend on obfuscated class or method names.
 */
class ExcelNativeLoader(
    private val openDocument: (String) -> SODoc?
) : ExcelDocumentLoader.NativeOpener {

    override fun open(path: String): SODoc? {
        return openDocument(path)
    }
}
