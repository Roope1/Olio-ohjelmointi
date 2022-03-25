package com.example.week9;

public class Movie {
    String title;
    int id;
    String startTime;


    public Movie (String title, int id, String startTime) {
        this.title = title;
        this.id = id;

        String [] dateAndTime = startTime.split("T");
        startTime = dateAndTime[1];
        String [] splitStartTime = dateAndTime[1].split(":");
        this.startTime = splitStartTime[0] + ":" + splitStartTime[1];

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }
}
