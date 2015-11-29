package com.example.username.myweather.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict=false)
public class Pref {

    @Attribute
    private String title;

    @ElementList( entry="city", inline=true)
    private List<City> city;

    public String getTitle() {
        return title;
    }

    public List<City> getCity() {
        return city;
    }
}
