package com.hwa.excelllll

import android.graphics.PointF
import android.graphics.RectF

class SOSelectionLimits {
    @Suppress("unused")
    private var internal: Long = 0

    constructor()
    constructor(handle: Long) {
        internal = handle
    }

    private external fun destroy()
    external fun combineWith(other: SOSelectionLimits)
    external fun getBox(): RectF?
    external fun getEnd(): PointF?
    external fun getHasSelectionEnd(): Boolean
    external fun getHasSelectionStart(): Boolean
    external fun getIsActive(): Boolean
    external fun getIsCaret(): Boolean
    external fun getStart(): PointF?
}
