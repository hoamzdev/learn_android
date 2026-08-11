package com.hwa.excelllll.excelsolib;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class ArtifexBitmapImpl extends SOBitmap {
    public ArtifexBitmapImpl(int width, int height) {
        int nextId = com.hwa.excelllll.excelsolib.SOBitmap.instanceCounter + 1;
        com.hwa.excelllll.excelsolib.SOBitmap.instanceCounter = nextId;
        this.instanceId = nextId;
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.rect = new Rect(0, 0, width, height);
    }
}

