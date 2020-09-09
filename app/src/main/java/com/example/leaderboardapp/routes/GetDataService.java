package com.example.leaderboardapp.routes;

import com.example.leaderboardapp.models.TopLearner;
import com.example.leaderboardapp.models.TopSkillPoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET(TopLearner.URL)
    Call<List<TopLearner>> getTopLearners();

    @GET(TopSkillPoints.URL)
    Call<List<TopSkillPoints>> getTopSkillIQs();
}
