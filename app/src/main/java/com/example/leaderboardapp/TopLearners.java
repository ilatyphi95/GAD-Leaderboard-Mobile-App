package com.example.leaderboardapp;

public class TopLearners {
    public static final String URL = "/api/hours";

    private String name;
    private String hours;
    private String country;
    private String badgeUrl;

    public String getName() {
        return name;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public String getDetails() {
        return hours + " learning hours, " + country;
    }
}
