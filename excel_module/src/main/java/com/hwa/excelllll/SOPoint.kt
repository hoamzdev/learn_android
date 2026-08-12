package com.hwa.excelllll

import android.graphics.PointF

class SOPoint : PointF {
    @JvmField
    var type: Int = 0

    constructor(point: PointF, type: Int) : super(point.x, point.y) {
        if (type in 0..1) this.type = type
    }

    constructor(x: Int, y: Int, type: Int) : super(x.toFloat(), y.toFloat()) {
        if (type in 0..1) this.type = type
    }

    constructor(x: Float, y: Float, type: Int) : super(x, y) {
        if (type in 0..1) this.type = type
    }
}
