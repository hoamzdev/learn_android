package com.hwa.excelllll.excelsoui;

import java.util.ArrayList;

public class History {
    private ArrayList<HistoryItem> itemList = new ArrayList<>();
    private int currentIndex = -1;

    public static class HistoryItem {
        private final int scrollX;
        private final int scrollY;
        private final float scale;

        public HistoryItem(int scrollX, int scrollY, float scale) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            this.scale = scale;
        }

        public float getScale() {
            return this.scale;
        }

        public int getScrollX() {
            return this.scrollX;
        }

        public int getScrollY() {
            return this.scrollY;
        }
    }

    public void add(int scrollX, int scrollY, float scale) {
        HistoryItem historyItem = new HistoryItem(scrollX, scrollY, scale);
        if (this.currentIndex + 1 != this.itemList.size()) {
            this.itemList = new ArrayList<>(this.itemList.subList(0, this.currentIndex + 1));
        }
        this.itemList.add(historyItem);
        this.currentIndex = this.itemList.size() - 1;
    }

    public boolean canNext() {
        return this.currentIndex < this.itemList.size() - 1;
    }

    public boolean canPrevious() {
        return this.currentIndex > 0;
    }

    public HistoryItem current() {
        int index = this.currentIndex;
        if (index < 0) {
            return null;
        }
        return this.itemList.get(index);
    }

    public HistoryItem next() {
        if (!canNext()) {
            return null;
        }
        int index = this.currentIndex + 1;
        this.currentIndex = index;
        return this.itemList.get(index);
    }

    public HistoryItem previous() {
        if (!canPrevious()) {
            return null;
        }
        int index = this.currentIndex - 1;
        this.currentIndex = index;
        return this.itemList.get(index);
    }
}
