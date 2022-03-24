package com.example.week9;

public class Theater {
    private int id;
    private String name;

    public Theater(int id, String name) {
        this.id = id;
        this.name = name;

        System.out.println("Added theater with id " + id + " and name " + name);

    }

    String getName(){
        return name;
    }

    int getId(){
        return id;
    }
}
