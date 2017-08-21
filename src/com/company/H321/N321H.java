package com.company.H321;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//"Circle splitter"
public class N321H {
    private final int N;
    private List<String> list;
    private List<Coords> points = new ArrayList<>();
    private String fileName;

    public N321H(String fileName) throws IOException {
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
        for (List<Coords> set : sets) {
            Coords p1, p2, p3;
            p1 = set.get(0);
            p2 = set.get(1);
            p3 = set.get(2);

            Circle currentCircle = new Circle(p1, p2, p3, this.points);
            if (currentCircle.validateCircle())
                circles.add(currentCircle);

        }

        double minRadius = 0.5;
        Circle bestCircle = new Circle();
        for (Circle c : circles) {
            if (c.getRadius() < minRadius) {
                minRadius = c.getRadius();
                bestCircle = c;
            }
        }
        return bestCircle;
    }
}