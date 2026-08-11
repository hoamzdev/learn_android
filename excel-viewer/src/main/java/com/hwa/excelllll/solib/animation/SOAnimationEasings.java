package com.hwa.excelllll.solib.animation;

import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationEasings {
    public static float A(float f) {
        float f2 = 1.0f - f;
        double d = f2;
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        return 1.0f - (((f2 * f2) * f2) - (f2 * ((float) Math.sin(d * 3.141592653589793d))));
    }

    public static float B(float f) {
        double d = f;
        float f2 = f * 2.0f;
        if (d < 0.5d) {
            double d2 = f2;
            Double.isNaN(d2);
            Double.isNaN(d2);
            Double.isNaN(d2);
            return (((f2 * f2) * f2) - (f2 * ((float) Math.sin(d2 * 3.141592653589793d)))) * 0.5f;
        }
        float f3 = 1.0f - (f2 - 1.0f);
        double d3 = f3;
        Double.isNaN(d3);
        Double.isNaN(d3);
        Double.isNaN(d3);
        return ((1.0f - (((f3 * f3) * f3) - (f3 * ((float) Math.sin(d3 * 3.141592653589793d))))) * 0.5f) + 0.5f;
    }

    public static float C(float f) {
        return 1.0f - D(1.0f - f);
    }

    public static float D(float f) {
        double d = f;
        if (d < 0.36363636363636365d) {
            return ((121.0f * f) * f) / 16.0f;
        }
        if (d < 0.7272727272727273d) {
            return (((9.075f * f) * f) - (f * 9.9f)) + 3.4f;
        }
        return d < 0.9d ? (((12.066482f * f) * f) - (f * 19.635458f)) + 8.898061f : (((10.8f * f) * f) - (f * 20.52f)) + 10.72f;
    }

    public static float E(float f) {
        double d = f;
        float f2 = f * 2.0f;
        return d < 0.5d ? C(f2) * 0.5f : (D(f2 - 1.0f) * 0.5f) + 0.5f;
    }

    public static float a(int i, float f, float f2, float f3) {
        float fB;
        switch (i) {
            case 1:
                fB = b(f3);
                break;
            case 2:
                fB = c(f3);
                break;
            case 3:
                fB = d(f3);
                break;
            case 4:
                fB = e(f3);
                break;
            case 5:
                fB = f(f3);
                break;
            case 6:
                fB = g(f3);
                break;
            case 7:
                fB = h(f3);
                break;
            case 8:
                fB = i(f3);
                break;
            case 9:
                fB = j(f3);
                break;
            case 10:
                fB = k(f3);
                break;
            case 11:
                fB = l(f3);
                break;
            case 12:
                fB = m(f3);
                break;
            case 13:
                fB = n(f3);
                break;
            case 14:
                fB = o(f3);
                break;
            case 15:
                fB = p(f3);
                break;
            case 16:
                fB = q(f3);
                break;
            case 17:
                fB = r(f3);
                break;
            case 18:
                fB = s(f3);
                break;
            case 19:
                fB = t(f3);
                break;
            case 20:
                fB = u(f3);
                break;
            case 21:
                fB = v(f3);
                break;
            case 22:
                fB = w(f3);
                break;
            case 23:
                fB = x(f3);
                break;
            case 24:
                fB = y(f3);
                break;
            case 25:
                fB = C(f3);
                break;
            case 26:
                fB = D(f3);
                break;
            case 27:
                fB = E(f3);
                break;
            case 28:
                fB = z(f3);
                break;
            case 29:
                fB = A(f3);
                break;
            case 30:
                fB = B(f3);
                break;
            default:
                fB = a(f3);
                break;
        }
        return (fB * f2) + f;
    }

    public static float b(float f) {
        return f * f;
    }

    public static float c(float f) {
        return -((f - 2.0f) * f);
    }

    public static float d(float f) {
        if (f < 0.5d) {
            return 2.0f * f * f;
        }
        return ((f * 4.0f) + (((-2.0f) * f) * f)) - 1.0f;
    }

    public static float e(float f) {
        return f * f * f;
    }

    public static float f(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }

    public static float g(float f) {
        if (f < 0.5d) {
            return 4.0f * f * f * f;
        }
        float f2 = (f * 2.0f) - 2.0f;
        return (0.5f * f2 * f2 * f2) + 1.0f;
    }

    public static float h(float f) {
        return f * f * f * f;
    }

    public static float i(float f) {
        float f2 = f - 1.0f;
        return e6.A(1.0f, f, f2 * f2 * f2, 1.0f);
    }

    public static float j(float f) {
        if (f < 0.5d) {
            return 8.0f * f * f * f * f;
        }
        float f2 = f - 1.0f;
        return ((-8.0f) * f2 * f2 * f2 * f2) + 1.0f;
    }

    public static float k(float f) {
        return f * f * f * f * f;
    }

    public static float l(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }

    public static float m(float f) {
        if (f < 0.5d) {
            return 16.0f * f * f * f * f * f;
        }
        float f2 = (f * 2.0f) - 2.0f;
        return (0.5f * f2 * f2 * f2 * f2 * f2) + 1.0f;
    }

    public static float n(float f) {
        double d = f - 1.0f;
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        return ((float) Math.sin((d * 3.141592653589793d) / 2.0d)) + 1.0f;
    }

    public static float o(float f) {
        double d = f;
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        return (float) Math.sin((d * 3.141592653589793d) / 2.0d);
    }

    public static float p(float f) {
        double d = f;
        Double.isNaN(d);
        return ((float) (1.0d - Math.cos(e6.a(d, d, d, 3.141592653589793d)))) * 0.5f;
    }

    public static float q(float f) {
        return 1.0f - ((float) Math.sqrt(1.0f - (f * f)));
    }

    public static float r(float f) {
        return (float) Math.sqrt((2.0f - f) * f);
    }

    public static float s(float f) {
        if (f < 0.5d) {
            return (1.0f - ((float) Math.sqrt(1.0f - ((f * f) * 4.0f)))) * 0.5f;
        }
        float f2 = f * 2.0f;
        return (((float) Math.sqrt((f2 - 1.0f) * (-(f2 - 3.0f)))) + 1.0f) * 0.5f;
    }

    public static float t(float f) {
        return ((double) f) == 0.0d ? f : (float) Math.pow(2.0d, (f - 1.0f) * 10.0f);
    }

    public static float u(float f) {
        return ((double) f) == 1.0d ? f : 1.0f - ((float) Math.pow(2.0d, f * (-10.0f)));
    }

    public static float v(float f) {
        double d = f;
        if (d == 0.0d || d == 1.0d) {
            return f;
        }
        return d < 0.5d ? ((float) Math.pow(2.0d, (f * 20.0f) - 10.0f)) * 0.5f : (((float) Math.pow(2.0d, (f * (-20.0f)) + 10.0f)) * (-0.5f)) + 1.0f;
    }

    public static float w(float f) {
        double d = f;
        Double.isNaN(d);
        return (float) (Math.pow(2.0d, (f - 1.0f) * 10.0f) * Math.sin(e6.a(d, d, d, 20.420352248333657d)));
    }

    public static float x(float f) {
        double d = 1.0f + f;
        Double.isNaN(d);
        return (float) ((Math.pow(2.0d, f * (-10.0f)) * Math.sin(e6.a(d, d, d, -20.420352248333657d))) + 1.0d);
    }

    public static float y(float f) {
        double dPow;
        double d = f;
        float f2 = f * 2.0f;
        if (d < 0.5d) {
            double d2 = f2;
            Double.isNaN(d2);
            dPow = Math.pow(2.0d, (f2 - 1.0f) * 10.0f) * Math.sin(e6.a(d2, d2, d2, 20.420352248333657d));
        } else {
            float f3 = f2 - 1.0f;
            double d3 = 1.0f + f3;
            Double.isNaN(d3);
            dPow = (Math.pow(2.0d, f3 * (-10.0f)) * Math.sin(e6.a(d3, d3, d3, -20.420352248333657d))) + 2.0d;
        }
        return ((float) dPow) * 0.5f;
    }

    public static float z(float f) {
        double d = f;
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        return ((f * f) * f) - (f * ((float) Math.sin(d * 3.141592653589793d)));
    }

    public static float a(float f) {
        return f;
    }
}
