package com.example.username.myweather.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Rss {
    @Element
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
