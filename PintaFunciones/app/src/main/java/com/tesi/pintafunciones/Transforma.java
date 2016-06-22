package com.tesi.pintafunciones;

import android.graphics.PointF;

/**
 * Created by TESI on 28/01/15.
 */
public class Transforma {
    private float xmin, xmax, ymin, ymax;     // Ventana
    private float umin, umax, vmin, vmax;     // Viewport

    public void setParams(float x1, float y1, float x2, float y2,   // Ventana
                          float u1, float v1, float u2, float v2)   // Viewport
    {
        xmin = x1; ymin = y1;
        xmax = x2; ymax = y2;

        umin = u1; vmin = v1;
        umax = u2; vmax = v2;
    }

    public PointF Window2Viewport(float x, float y)
    {
        float u = (umax - umin) / (xmax - xmin) * (x - xmin) + umin;
        float v = vmax - (vmax - vmin) / (ymax - ymin) * (y - ymin);

        return new PointF(u, v);
    }
}
