package com.example.leaderboardapp.models;

public class TopSkillPoints {
    public static final String URL = "/api/skilliq";

    private String name;
    private String score;
    private String country;
    private String badgeUrl;

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
