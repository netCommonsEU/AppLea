package com.example.commontask.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {
    public void setImagepic(String imagepic) {
        this.imagepic = imagepic;
    }

    public String imagepic=" ";
    public String uid;
    public String author;
    public String title;
    public String body;
    public String body1;
    public String body2;
    public String body3;
    public String body4;
    public String timestamp;
    public int starCount = 0;
    private String profilePicLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public String getTimestamp() {
        return timestamp;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody3() {
        return body3;
    }

    public String getBody4() {
        return body4;
    }

    public String getBody() {
        return body;
    }
    public String getBody1() {
        return body1;
    }
    public String getBody2() {
        return body2;
    }

    public String getProfilePicLocation() {
        return profilePicLocation;
    }

    public Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String profilePicLocation,String imagepic,String title, String body,String body1,String body2,String body3,String body4,String timestamp,String id) {
        this.uid = uid;
        this.author = author;
        this.profilePicLocation = profilePicLocation;
        this.imagepic=imagepic;
        this.title = title;
        this.body = body;
        this.body1 = body1;
        this.body2 = body2;
        this.body3 = body3;
        this.body4= body4;
        this.timestamp=timestamp;
        this.id=id;
    }

    public String getImagepic() {
        return imagepic;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("profilePicLocation", profilePicLocation);
        result.put("imagepic", imagepic);
        result.put("title", title);
        result.put("body", body);
        result.put("body1", body1);
        result.put("body2", body2);
        result.put("body3", body3);
        result.put("body4", body4);
        result.put("timestamp", timestamp);
        result.put("id", id);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
