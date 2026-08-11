package com.hwa.excelllll.excelsolib;

import android.graphics.RectF;
public interface SearchListener {
    void onTextNotFound();

    void onSearchProgress(int pageIndex);

    void onTextFound(int pageIndex, RectF resultBounds);

    boolean onSearchComplete();

    boolean onSearchCancelled();

    void onSearchError();

    void onSearchEnded();
}
