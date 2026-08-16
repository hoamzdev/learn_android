package com.hwa.excelllll

fun SODoc.saveDocument(path: String, listener: SODocSaveListener) {
    save(path, listener)
}

fun SODoc.saveAsPdf(
    path: String,
    includeAnnotations: Boolean,
    listener: SODocSaveListener
) {
    savePdf(path, includeAnnotations, listener)
}

fun SODoc.closeDocument() {
    close()
}
