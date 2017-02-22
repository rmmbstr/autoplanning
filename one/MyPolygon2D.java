package one;

import math.geom2d.Point2D;
import math.geom2d.polygon.SimplePolygon2D;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ME on 2016/4/25.
 */
public class MyPolygon2D extends SimplePolygon2D {
    public MyPolygon2D(ArrayList<? extends java.awt.geom.Point2D.Double> points) {
        ArrayList pointsfinal = new ArrayList<Point2D>();
        for (int i = 0; i < points.size(); i++) {
            pointsfinal.add(new Point2D(points.get(i)));
        }
        this.vertices = new ArrayList(points.size());
        this.vertices.addAll(pointsfinal);
    }
}
