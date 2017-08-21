package com.company.H321;

import java.util.List;

public class Circle {

    private double radius;
    private Coords center;
    private int nPoints = 0;
    private double x_max, y_max, x_min, y_min;
    private List<Coords> points;

    Circle() {
        this.radius = 0.5;
        this.center = new Coords(0.5, 0.5);
        x_max = center.getX() + radius;
        x_min = center.getX() - radius;
        y_max = center.getY() + radius;
        y_min = center.getY() - radius;
    }

    Circle(Coords point1, Coords point2, Coords point3, List<Coords> points) {

        double x1 = point1.getX();
        double x2 = point2.getX();
        double x3 = point3.getX();
        double y1 = point1.getY();
        double y2 = point2.getY();
        double y3 = point3.getY();

        double ma = (y2 - y1) / (x2 - x1);
        double mb = (y3 - y2) / (x3 - x2);
        double x_centre = (ma * mb * (y1 - y3) + mb * (x1 + x2) - ma * (x2 + x3)) / (2 * (mb - ma));
        double y_centre = ((-1) / ma) * (x_centre - (x1 + x2) / 2) + ((y1 + y2) / 2);

        this.center = new Coords(x_centre, y_centre);
        this.radius = Coords.getDistance(center, point1);
        x_max = center.getX() + radius;
        x_min = center.getX() - radius;
        y_max = center.getY() + radius;
        y_min = center.getY() - radius;
        for (Coords coords : this.points = points) {
            if (isPointInside(coords)) {
                nPoints += 1;
            }
        }
    }

    private boolean isPointInside(Coords point) {
        return Coords.getDistance(point, center) < radius;
    }

    boolean validateCircle() {
        return this.center.isPointInsideSquare() && x_min >= 0 && y_max <= 1 && y_min > 0 && x_max < 1 && nPoints == (points.size() / 2);
    }

    double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return String.format("%.10f %.10f\n%.10f", this.center.getX(), this.center.getY(), this.radius);
    }
}
