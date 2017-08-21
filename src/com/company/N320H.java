package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class N320H {
    private static final String url = "http://en.wikipedia.org/wiki/";
    public static final String RANDOM = "Special:Random";
    private final String goal;

    public N320H(String goal) {
        this.goal = goal;
    }

    //główna funkcja szukająca drogi
     ArrayList<String> findPath(String name) {
        WikiArt current = search(name);
        ArrayList<String> results = new ArrayList<>();
        while (current != null) {
            results.add(0, current.getTitle());
            current = current.getFrom();
        }
        return results;
    }
//Breadth-first search
    private WikiArt search(String name) {

        HashSet<String> visited = new HashSet<>();
        visited.add(name);

        ArrayDeque<WikiArt> queue = new ArrayDeque<>();
        WikiArt start = new WikiArt(name);
        WikiArt from, next;

        queue.add(start);

        while((from = queue.poll())!= null) {
            name = from.getTitle();
            for(String link : call(name)){
                if(visited.contains(link))
                    continue;
                next = new WikiArt(link, from);
                if(next.getTitle().equals(goal))
                    return next;
                visited.add(link);
                queue.addLast(next);
            }
        }
        return null;
    }
    private static final Pattern pattern = Pattern.compile("href=\"wiki/([A-Za-z0-9]*?)\".*?>\\w");
    private static ArrayList<String> call(String name) {
        ArrayList<String> resu = new ArrayList<>();
        try{
            URL link = new URL(url + name);
            HttpURLConnection client = (HttpURLConnection) link.openConnection();
            client.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            String html = response.toString();
            Matcher m = pattern.matcher(html);
            while(m.find())
                resu.add(m.group(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resu;

    }
}

class WikiArt {
    private String title;
    private WikiArt from;

    WikiArt(String title, WikiArt linkedFrom) {
        this.title = title;
        from = linkedFrom;
    }

    WikiArt(String title) {
        this(title, null);
    }

    public String getTitle() {
        return title;
    }

    public WikiArt getFrom() {
        return from;
    }
}