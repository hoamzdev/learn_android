package com.hwa.excelllll

interface SODocLoadListenerInternal {
    fun a(doc: SODoc)
    fun error(code: Int, detail: Int)
    fun onLayoutCompleted()
    fun onSelectionChanged(start: Int, end: Int)
    fun progress(progress: Int, finished: Boolean)
}
