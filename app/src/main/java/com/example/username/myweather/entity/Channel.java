package com.example.username.myweather.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(strict=false)
@Namespace(reference="http://weather.livedoor.com/%5C/ns/rss/2.0", prefix="ldWeather")
public class Channel {

    @Element
    @Namespace(reference = "http://weather.livedoor.com/%5C/ns/rss/2.0")
    private LdWeatherSource source;

    public LdWeatherSource getSource() {
        return source;
    }
}
