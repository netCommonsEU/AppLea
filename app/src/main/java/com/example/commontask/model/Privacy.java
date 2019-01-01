package com.example.commontask.model;

import java.io.Serializable;
/**
 * Created by Αρης on 27/2/2018.
 */

public class Privacy implements Serializable{


    private String name = "";
    private boolean checked = false;

    public Privacy() {
    }

    public Privacy(String name) {
        this.name = name;
    }

    public Privacy(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return name;
    }

    public void toggleChecked() {
        checked = !checked;
    }

    }
