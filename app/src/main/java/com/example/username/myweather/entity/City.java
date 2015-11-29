package com.example.username.myweather.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class City {
    @Attribute
    private  String title;
    @Attribute
    private String id;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
