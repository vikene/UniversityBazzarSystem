package com.team5.ubs;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tp on 14/02/18.
 */

@IgnoreExtraProperties
public class user {

    public String username;
    public String email;
    public String phone;
    public Map<String, String> myclubs = new HashMap<>();


    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user(String username, String email, String Phone) {
        this.username = username;
        this.email = email;
        this.phone = Phone;
    }

}