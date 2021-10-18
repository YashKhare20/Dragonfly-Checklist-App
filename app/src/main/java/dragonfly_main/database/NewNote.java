package com.example.dragonfly_main;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NewNote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String common_name;
    private String description;
    private int priority;

    public NewNote(String title, String common_name,String description, int priority) {
        this.title = title;
        this.common_name = common_name;
        this.description = description;
        this.priority = priority;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getCommon_name() {return common_name;}
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }
}