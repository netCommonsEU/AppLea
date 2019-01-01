package com.example.commontask.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class Place {
    private Double latitude;
    private Double longitude;
    private String place_name;
    private String email;
    private String numbers_field;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private  String type;

    public void setId(String id) {
        this.id = id;
    }

    private String kind;

    public String getId() {
        return id;
    }

    private String id;

    public Place(Double latitude, Double longitude, String place_name,String email,String numbers_field,String kind,String id,String type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.place_name = place_name;
        this.email = email;
        this.numbers_field = numbers_field;
        this.kind = kind;
        this.id=id;
        this.type=type;
    }
    public Place(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setNumbers_field(String numbers_field) {
        this.numbers_field = numbers_field;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNumbers_field() {
        return numbers_field;
    }

    public String getKind() {
        return kind;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public Double getLatitude() {

        return latitude;
    }

    public Place() {

    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPlace_name() {
        return place_name;
    }



    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("place_name", place_name);
        result.put("email", email);
        return result;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
    // [END post_to_map]
}
