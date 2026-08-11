package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Outline {
    public Outline[] down;
    public int page;
    public String title;
    public String uri;
    public float x;
    public float y;

    public Outline(String str, int i, String str2, float f, float f2, Outline[] outlineArr) {
        this.title = str;
        this.page = i;
        this.uri = str2;
        this.down = outlineArr;
        this.x = f;
        this.y = f2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.page);
        stringBuffer.append(": ");
        stringBuffer.append(this.title);
        stringBuffer.append(' ');
        stringBuffer.append(this.uri);
        stringBuffer.append('\n');
        if (this.down != null) {
            for (int i = 0; i < this.down.length; i++) {
                stringBuffer.append('\t');
                stringBuffer.append(this.down[i]);
                stringBuffer.append('\n');
            }
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }
}
