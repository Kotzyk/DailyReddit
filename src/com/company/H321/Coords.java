package com.company.H321;

class Coords {
    private double x, y;

    Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static double getDistance(Coords p1, Coords p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static Coords midpoint(Coords p1, Coords p2) {
        return new Coords((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    public boolean isPointInsideSquare() {
        if (this.y > 1 || this.x > 1 || this.y < 0 || this.x < 0) {
            return false;
        }
        return true;
    }

    double getY() {
        return y;
    }

    double getX() {
        return x;
    }

}
