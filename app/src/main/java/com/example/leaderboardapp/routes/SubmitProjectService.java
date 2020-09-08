package com.example.leaderboardapp.routes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubmitProjectService {

    @FormUrlEncoded
    @POST("/submit")
    Call<Void> submit(
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String email,
            @Field("entry.284483984") String link);
}
