package com.example.dragonfly_main;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class MyList {
    public String uid;
    private String Location;
    private String Date;
    private String Time;
    private String SpeciesName;
    private String Details;
    private String count;
    private String HabAnswer;
    private String ObsType;
    private String ObsCount;



    public MyList() {
        //empty constructor needed
    }

    public MyList(String uid, String location, String date, String time, String speciesName, String details, String count , String habAnswer, String obsType, String obsCount) {
        this.uid = uid;
        this.Location = location;
        this.Date = date;
        this.Time = time;
        this.SpeciesName = speciesName;
        this.Details = details;
        this.count = count;
        this.HabAnswer = habAnswer;
        this.ObsType = obsType;
        this.ObsCount = obsCount;
    }


    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("location", Location);
        result.put("date", Date);
        result.put("time", Time);
        result.put("spName", SpeciesName);
        result.put("details", Details);
        result.put("count", count);
        result.put("habAns", HabAnswer);
        result.put("obsType", ObsType);
        result.put("obsCount", ObsCount);

        return result;
    }
    // [END post_to_map]

}