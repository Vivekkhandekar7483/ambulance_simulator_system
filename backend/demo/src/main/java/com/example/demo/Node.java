package com.example.demo;

public class Node {

    private String name;

    private double lat;

    private double lng;

    public Node(
            String name,
            double lat,
            double lng
    ) {

        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}