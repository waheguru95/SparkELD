package com.example.eld;

import com.google.firebase.Timestamp;

public class Eventmodel {
    String x, y, graph, status, location, odometer, eh, orign;
    Timestamp time;
    public Eventmodel(String x, String y, Timestamp time, String graph, String status, String location, String odometer, String eh, String orign) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.graph = graph;
        this.status = status;
        this.location = location;
        this.odometer = odometer;
        this.eh = eh;
        this.orign = orign;
    }

    public Eventmodel() {
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getEh() {
        return eh;
    }

    public void setEh(String eh) {
        this.eh = eh;
    }

    public String getOrign() {
        return orign;
    }

    public void setOrign(String orign) {
        this.orign = orign;
    }
}


