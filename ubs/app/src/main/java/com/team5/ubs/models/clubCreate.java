package com.team5.ubs.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tp on 28/03/18.
 */

public class clubCreate {
    public String clubName;
    public String clubInfo;
    public String clubType;
    public Map<String, String> members = new HashMap<>();


    public clubCreate(){}
    public clubCreate(String cn,String ci, String clI){
        this.clubName = cn;
        this.clubInfo = ci;
        this.clubType = clI;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("clubName", clubName);
        result.put("clubInfo", clubInfo);
        result.put("clubType", clubType);
        result.put("members", members);
        return result;
    }
}
