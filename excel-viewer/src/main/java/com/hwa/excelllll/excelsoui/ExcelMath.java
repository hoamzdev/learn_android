package com.hwa.excelllll.excelsoui;
/** Replaces the decompiler-generated numeric helper used for page/view scaling. */
public final class ExcelMath {
    private ExcelMath() {}
    public static double scale(double value, double ignored1, double ignored2, double factor) { return value * factor; }
    public static double unscale(double value, double ignored1, double ignored2, double factor) { return factor == 0.0 ? 0.0 : value / factor; }
    public static float lerp(float start, float end, float progress, float ignored) { return start + ((end - start) * progress); }
}
