package com.example.testBaseApp.utils

import android.graphics.ColorSpace
import android.graphics.Matrix
import android.graphics.Rect
import java.util.Date

open class PDFAnnotation(
    protected var pointer: Long
) {

    companion object {

        const val IS_INVISIBLE = 1
        const val IS_HIDDEN = 2
        const val IS_PRINT = 4
        const val IS_NO_ZOOM = 8
        const val IS_NO_ROTATE = 16
        const val IS_NO_VIEW = 32
        const val IS_READ_ONLY = 64
        const val IS_LOCKED = 128
        const val IS_TOGGLE_NO_VIEW = 256
        const val IS_LOCKED_CONTENTS = 512

        const val LINE_ENDING_NONE = 0
        const val LINE_ENDING_SQUARE = 1
        const val LINE_ENDING_CIRCLE = 2
        const val LINE_ENDING_DIAMOND = 3
        const val LINE_ENDING_OPEN_ARROW = 4
        const val LINE_ENDING_CLOSED_ARROW = 5
        const val LINE_ENDING_BUTT = 6
        const val LINE_ENDING_R_OPEN_ARROW = 7
        const val LINE_ENDING_R_CLOSED_ARROW = 8
        const val LINE_ENDING_SLASH = 9

        const val TYPE_TEXT = 0
        const val TYPE_LINK = 1
        const val TYPE_FREE_TEXT = 2
        const val TYPE_LINE = 3
        const val TYPE_SQUARE = 4
        const val TYPE_CIRCLE = 5
        const val TYPE_POLYGON = 6
        const val TYPE_POLY_LINE = 7
        const val TYPE_HIGHLIGHT = 8
        const val TYPE_UNDERLINE = 9
        const val TYPE_SQUIGGLY = 10
        const val TYPE_STRIKE_OUT = 11
        const val TYPE_REDACT = 12
        const val TYPE_STAMP = 13
        const val TYPE_CARET = 14
        const val TYPE_INK = 15
        const val TYPE_POPUP = 16
        const val TYPE_FILE_ATTACHMENT = 17
        const val TYPE_SOUND = 18
        const val TYPE_MOVIE = 19
        const val TYPE_WIDGET = 20
        const val TYPE_SCREEN = 21
        const val TYPE_PRINTER_MARK = 22
        const val TYPE_TRAP_NET = 23
        const val TYPE_WATERMARK = 24
        const val TYPE_3D = 25
        const val TYPE_UNKNOWN = -1

        init {
            Context.init()
        }
    }

    fun destroy() {
        finalize()
        pointer = 0L
    }

    fun equals(annotation: PDFAnnotation): Boolean {
        return pointer == annotation.pointer
    }

    external fun eventBlur()

    external fun eventDown()

    external fun eventEnter()

    external fun eventExit()

    external fun eventFocus()

    external fun eventUp()

    external fun finalize()

    external fun getAuthor(): String?

    external fun getBorder(): Float

    external fun getBounds(): Rect

    external fun getColor(): FloatArray?

    external fun getContents(): String?

    external fun getFlags(): Int

    external fun getIcon(): String?

    external fun getInkList(): Array<FloatArray>?

    external fun getInteriorColor(): FloatArray?

    external fun getLineEndingStyles(): IntArray

    fun getModificationDate(): Date {
        return Date(getModificationDateNative())
    }

    external fun getModificationDateNative(): Long

    external fun getQuadPoints(): Array<FloatArray>?

    external fun getRect(): Rect

    external fun getType(): Int

    external fun getVertices(): FloatArray?

    external fun isOpen(): Boolean

    external fun setAuthor(value: String?)

    external fun setBorder(value: Float)

    external fun setColor(value: FloatArray?)

    external fun setContents(value: String?)

    external fun setFlags(value: Int)

    external fun setIcon(value: String?)

    external fun setInkList(value: Array<FloatArray>?)

    external fun setInteriorColor(value: FloatArray?)

    external fun setIsOpen(value: Boolean)

    external fun setLineEndingStyles(
        start: Int,
        end: Int
    )

    fun setLineEndingStyles(styles: IntArray) {
        setLineEndingStyles(styles[0], styles[1])
    }

    external fun setModificationDate(time: Long)

    fun setModificationDate(date: Date) {
        setModificationDate(date.time)
    }

    external fun setQuadPoints(value: Array<FloatArray>?)

    external fun setRect(rect: Rect)

    external fun setVertices(value: FloatArray?)

//    external fun toDisplayList(): DisplayList
//
//    external fun toPixmap(
//        matrix: Matrix,
//        colorSpace: ColorSpace,
//        alpha: Boolean
//    ): Pixmap

    external fun update(): Boolean

    fun equals(pointer: Long): Boolean {
        return this.pointer == pointer
    }
}
