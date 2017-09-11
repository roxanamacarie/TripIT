package com.example.roxana.tripit;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Roxana on 5/13/2017.
 */

@IgnoreExtraProperties
public class Upload{

    public String name;
    public String url;

    public Upload() {
    }

    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}