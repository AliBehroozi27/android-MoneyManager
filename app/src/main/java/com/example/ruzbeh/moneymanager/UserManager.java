package com.example.ruzbeh.moneymanager;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class UserManager {
    private String DATABASE_NAME = "Users";
    private String name;
    private String password;
    private SharedPreferences usersDb;

    public UserManager(Context cnxt, String name, String password) {
        usersDb = cnxt.getSharedPreferences(DATABASE_NAME, MODE_PRIVATE);
        this.name = name;
        this.password = password;
    }

    public boolean checkUser() {
        Map<String,?> keys = usersDb.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.e(entry.getKey(),name);
            if (entry.getKey().equals(name) && entry.getValue().toString().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean createUser() {
        if (!checkUser()) {
            SharedPreferences.Editor editor = usersDb.edit();
            editor.putString(name, password);
            editor.apply();
            return true;
        }
        return false;
    }
}
