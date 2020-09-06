package com.example.leaderboardapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET(TopLearners.URL)
    Call<List<TopLearners>> getTopLearners();

    @GET(TopSkillPoints.URL)
    Call<List<TopSkillPoints>> getTopSkillIQs();
}
