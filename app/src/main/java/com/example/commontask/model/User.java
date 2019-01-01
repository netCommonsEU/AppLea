package com.example.commontask.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User  implements Parcelable {

    private String username;
    private String email;
    private String kind;
    private String profilePicLocation;
    public int score;
    public String field;
    public String location;
    public String age;
    public String job;
    private int position;
    private String user_id;
    private String use;

    protected User(Parcel in) {
        user_id = in.readString();
        field = in.readString();
        email = in.readString();
        username = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public  void setPosition(int position){
        this.position=position;
    }
    public  int getPosition(){
        return this.position;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getField() {
        return field;
    }

    public String getJob() {
        return job;
    }

    public void setProfilePicLocation(String profilePicLocation) {
        this.profilePicLocation = profilePicLocation;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocation() {
        return location;
    }

    public String getAge() {
        return age;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setLocation(String location) {
        this.location = location;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public User(){

    }



    public User(String name, String email){
        this.username = name;
        this.email = email;
    }


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public User(String user_id,String email, String name, String profilePicLocation, String location, String age, int score, String kind,String job,String use) {
        this.user_id = user_id;
        this.email = email;
        this.username = name;
        this.profilePicLocation=profilePicLocation;
        this.location = location;
        this.age = age;
        this.score=score;
        this.kind=kind;
        this.job=job;
        this.use=use;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public String getProfilePicLocation() {
        return profilePicLocation;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", field='" + field + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_id);
        parcel.writeString(field);
        parcel.writeString(email);
        parcel.writeString(username);
    }
}