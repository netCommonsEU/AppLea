package com.example.commontask;

/**
 * Created by Αρης on 18/11/2017.
 */

public class Bucket {

    private String name;
    private String firstImageContainedPath;

    public Bucket(String name, String firstImageContainedPath) {
        this.name = name;
        this.firstImageContainedPath = firstImageContainedPath;
    }
    public Bucket() {

    }
    public String getName() {
        return name;
    }

    public String getFirstImageContainedPath() {
        return firstImageContainedPath;
    }
}

