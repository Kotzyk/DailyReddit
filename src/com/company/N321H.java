package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//"Circle splitter"
class N321H {
    private final int N;
    private List<String> list;
    private List<Coords> points = new ArrayList<>();
    private String fileName;

    N321H(String fileName) throws IOException {
        this.fileName = fileName;
        this.list = Files.readAllLines(Paths.get("C:/Users/Mateusz.DELL/Documents/DailyReddit/src/com/company/", this.fileName),
                Charset.forName("UTF-8"));
        N = Integer.parseInt(list.get(0));
        for (int i = 1; i < N; i++) {
            String[] xy_s = list.get(i).split(" ");
            points.add(new Coords(
                    Double.parseDouble(xy_s[0]),
                    Double.parseDouble(xy_s[1])
            ));
        }
    }

    public Circle findBestCircle() {
        List<Circle> circles = new ArrayList<>();
        Combinator combinator = new Combinator(points);
        List<List<Coords>> sets = combinator.generate(3);
        for (int a = 0; a < sets.size(); a++) {
            Coords p1, p2, p3;
            p1 = sets.get(a).get(0);
            p2 = sets.get(a).get(1);
            p3 = sets.get(a).get(2);

            Circle currentCircle = new Circle(p1, p2, p3, this.points);
            if (currentCircle.validateCircle())
                circles.add(currentCircle);

        }

        double minRadius = 0.5;
        Circle bestCircle = new Circle();
        for (Circle c : circles){
            if(c.getRadius() < minRadius) {
                minRadius = c.getRadius();
                bestCircle = c;
            }
        }
        return bestCircle;
    }
}

class Circle {

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

    public boolean isPointInside(Coords point) {
        return Coords.getDistance(point, center) < radius;
    }

    public boolean validateCircle() {
        if (this.getCenter().isPointInsideSquare() && x_min >= 0 && y_max <= 1 && y_min > 0 && x_max < 1 && nPoints == (points.size() / 2)) {
            return true;
        }
        return false;
    }

    public Coords getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
    @Override
    public String toString(){
        return String.format("%.10f %.10f\n%.10f", this.center.getX(), this.center.getY(), this.radius);
    }
}