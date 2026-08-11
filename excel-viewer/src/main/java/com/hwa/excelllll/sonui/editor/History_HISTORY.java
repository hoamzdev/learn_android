package com.hwa.excelllll.sonui.editor;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class History {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private ArrayList<HistoryItem> f4644a = new ArrayList<>();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private int f4645b = -1;

    public class HistoryItem {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private int f4647b;
        private int c;
        private float d;

        public HistoryItem(int i, int i2, float f) {
            this.f4647b = i;
            this.c = i2;
            this.d = f;
        }

        public float getScale() {
            return this.d;
        }

        public int getScrollX() {
            return this.f4647b;
        }

        public int getScrollY() {
            return this.c;
        }
    }

    public void add(int i, int i2, float f) {
        HistoryItem historyItem = new HistoryItem(i, i2, f);
        if (this.f4645b + 1 != this.f4644a.size()) {
            this.f4644a = new ArrayList<>(this.f4644a.subList(0, this.f4645b + 1));
        }
        this.f4644a.add(historyItem);
        this.f4645b = this.f4644a.size() - 1;
    }

    public boolean canNext() {
        return this.f4645b < this.f4644a.size() - 1;
    }

    public boolean canPrevious() {
        return this.f4645b > 0;
    }

    public HistoryItem current() {
        int i = this.f4645b;
        if (i < 0) {
            return null;
        }
        return this.f4644a.get(i);
    }

    public HistoryItem next() {
        if (!canNext()) {
            return null;
        }
        int i = this.f4645b + 1;
        this.f4645b = i;
        return this.f4644a.get(i);
    }

    public HistoryItem previous() {
        if (!canPrevious()) {
            return null;
        }
        int i = this.f4645b - 1;
        this.f4645b = i;
        return this.f4644a.get(i);
    }
}
