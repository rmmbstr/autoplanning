package one;

import math.geom2d.Point2D;
import math.geom2d.polygon.SimplePolygon2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ME on 2016/4/25.
 */
class MyPolygon2D extends SimplePolygon2D {
     MyPolygon2D(ArrayList<? extends java.awt.geom.Point2D.Double> points) {
        List<Point2D> pointsfinal = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            pointsfinal.add(new Point2D(points.get(i)));
        }
        this.vertices = new ArrayList<>(points.size());
        this.vertices.addAll(pointsfinal);
    }
}
