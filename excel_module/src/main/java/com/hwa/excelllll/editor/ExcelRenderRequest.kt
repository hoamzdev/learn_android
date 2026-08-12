package com.hwa.excelllll.editor

data class ExcelRenderRequest(
    val pageIndex: Int,
    val width: Int,
    val height: Int,
    val scale: Double = 1.0,
    val offsetX: Int = 0,
    val offsetY: Int = 0
)
