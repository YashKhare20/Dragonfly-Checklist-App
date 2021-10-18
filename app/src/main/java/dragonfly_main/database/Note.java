package com.example.dragonfly_main;

public class Note {
    private String title;
    private String description;
    private int priority;
    private String gender;

    public Note() {
        //empty constructor needed
    }

    public Note(String title, String description, int priority, String gender) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getGender() {
        return gender;
    }
}