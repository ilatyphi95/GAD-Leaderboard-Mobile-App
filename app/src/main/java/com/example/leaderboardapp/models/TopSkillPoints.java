package com.example.leaderboardapp.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity
public class TopSkillPoints {
    @Ignore
    public static final String URL = "/api/skilliq";

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Expose
    private String name;
    @Expose
    private String score;
    @Expose
    private String country;
    @Expose
    private String badgeUrl;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public String getDetails() {
        return score + " skill IQ Score, " + country;
    }
}
