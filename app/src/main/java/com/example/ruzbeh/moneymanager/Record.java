package com.example.ruzbeh.moneymanager;


import java.io.Serializable;

public class Record implements Serializable{
    String name, price, date, kind, describe;
    int id;

    public Record(String name, String price, String date, String kind, String describe) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.kind = kind;
        this.describe = describe;
    }
}