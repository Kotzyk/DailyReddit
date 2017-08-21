package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class N320Hard{
private static final String url = "https://en.wikipedia.org/wiki/";
private final String goal;
        N320Hard(String goal) {
        this.goal = goal;
        }

// Function to call
        ArrayList<String> findPath(String title){
        // Does the leg-work
        Link at = solve(title);
        // Listifies the result
        ArrayList<String> res = new ArrayList<>();
        while(at != null){
        res.add(0, at.getName());
        at = at.getFrom();
        }
        return res;
        }

// BFS
private Link solve(String title){
        HashSet<String> visited = new HashSet<>();
        visited.add(title);
        Link start = new Link(title);
        ArrayDeque<Link> queue = new ArrayDeque<>();
        queue.add(start);
        Link from, next;
        while( (from = queue.poll()) != null ){
        title = from.getName();
        for(String link : call(title)){
        if(visited.contains(link)) continue;
        next = new Link(link, from);
        if(next.getName().equals(goal))
        return next;
        visited.add(link);
        queue.addLast(next);
        }
        }
        return null;
        }
// Regex to find all wiki links on the page, requres \w after the tag to avoid parenthesis
private static final Pattern pattern = Pattern.compile("href=\"/wiki/([a-zA-Z0-9 ]*?)\".*?>\\w");
private static ArrayList<String> call(String title){
        ArrayList<String> res = new ArrayList<>();
        try {
        URL link = new URL(url + title);
        HttpURLConnection conn = (HttpURLConnection) link.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
        response.append(inputLine);
        in.close();
        String html = response.toString();
        Matcher m = pattern.matcher(html);
        while(m.find())
        res.add(m.group(1));
        } catch (Exception e) {
        e.printStackTrace();
        }
        return res;
        }

        }

class Link{
    private String name;
    private Link from;
    Link(String name, Link from){
        this.name = name;
        this.from = from;
    }

    public Link(String title) {
        this.name = title;
    }

    public Link getFrom() {
        return from;
    }

    public String getName() {
        return name;
    }

}
