package com.example.testBaseApp.utils

class Context {
    companion object {
        private var inited = false
        init { init() }

        @JvmStatic
        fun init() {
            if (inited) {
                return
            }
            inited = true
            try {
                try {
                    System.loadLibrary("mupdf_java")
                } catch (_: UnsatisfiedLinkError) {
                    System.loadLibrary("mupdf_java64")
                }
            } catch (_: UnsatisfiedLinkError) {
                System.loadLibrary("mupdf_java32")
            }
            if (initNative() >= 0) {
                return
            }
            throw Error("cannot initialize mupdf library")
        }

        @JvmStatic
        private external fun initNative(): Int
    }
}