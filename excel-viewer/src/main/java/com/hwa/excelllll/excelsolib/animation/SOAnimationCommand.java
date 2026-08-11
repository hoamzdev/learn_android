package com.hwa.excelllll.excelsolib.animation;

/**
 * Base value returned by the native document engine for a page animation.
 * Concrete animation commands are populated by JNI.
 */
public class SOAnimationCommand {
    protected long internalHandle;

    public SOAnimationCommand(long internalHandle) {
        this.internalHandle = internalHandle;
    }
}
