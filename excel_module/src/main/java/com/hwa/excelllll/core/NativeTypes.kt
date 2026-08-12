package com.hwa.excelllll

fun interface SODocSaveListener {
    fun onComplete(result: Int, extra: Int)
}

class SOSelectionTableRange private constructor(
    @Suppress("unused")
    private var internal: Long
) {
    private external fun destroy()
    external fun firstRow(): Int
    external fun firstColumn(): Int
    external fun rowCount(): Int
    external fun columnCount(): Int
}
