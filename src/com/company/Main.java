package com.company;

import com.company.H321.Circle;
import com.company.H321.N321H;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        N321H r = new N321H("n321h.txt");
        Circle best = r.findBestCircle();
        System.out.println(best.toString());
        }
    }
