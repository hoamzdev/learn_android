package com.hwa.excelllll.editor

import com.hwa.excelllll.SODoc

/**
 * Formatting commands for the current Excel selection.
 */
class ExcelFormattingController(
    private val document: SODoc
) {
    fun bold(enabled: Boolean) = document.setSelectionIsBold(enabled)
    fun italic(enabled: Boolean) = document.setSelectionIsItalic(enabled)
    fun underline(enabled: Boolean) = document.setSelectionIsUnderlined(enabled)
    fun strikeThrough(enabled: Boolean) = document.setSelectionIsLinethrough(enabled)

    fun fontName(name: String) = document.setSelectionFontName(name)
    fun fontSize(size: Double) = document.setSelectionFontSize(size)
    fun fontColor(color: String) = document.setSelectionFontColor(color)

    fun backgroundColor(color: String) =
        document.setSelectionBackgroundColor(color)

    fun backgroundTransparent() =
        document.setSelectionBackgroundTransparent()

    fun fillColor(color: String) =
        document.setSelectionFillColor(color)

    fun alignment(value: Int) =
        document.setSelectionAlignment(value)

    fun verticalAlignment(value: Int) =
        document.setSelectionAlignmentV(value)

    fun rotation(degrees: Float) =
        document.setSelectionRotation(degrees)

    fun lineColor(color: String) =
        document.setSelectionLineColor(color)

    fun lineType(type: Int) =
        document.setSelectionLineType(type)

    fun lineWidth(width: Float) =
        document.setSelectionLineWidth(width)
}
