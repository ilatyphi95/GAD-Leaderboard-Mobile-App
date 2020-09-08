package com.example.leaderboardapp.routes;

import com.example.leaderboardapp.models.TopLearners;
import com.example.leaderboardapp.models.TopSkillPoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET(TopLearners.URL)
    Call<List<TopLearners>> getTopLearners();

    @GET(TopSkillPoints.URL)
    Call<List<TopSkillPoints>> getTopSkillIQs();
}
