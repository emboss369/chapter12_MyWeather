package com.example.username.myweather.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict=false)
public class LdWeatherSource {
    @ElementList( entry="pref", inline=true)
    private List<Pref> pref;

    public List<Pref> getPref() {
        return pref;
    }
}
