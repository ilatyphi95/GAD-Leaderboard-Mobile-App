package com.example.leaderboardapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.leaderboardapp.models.TopLearner;
import com.example.leaderboardapp.models.TopSkillPoints;

import java.util.List;

@Dao
public interface LeaderboardDao {

    @Query("SELECT * FROM TopLearner")
    List<TopLearner> getAllTopLearners();

    @Query("SELECT * FROM TopSkillPoints")
    List<TopSkillPoints> getAllTopSkillPoints();

    @Insert
    void insertTopLearners(TopLearner... topLearners);

    @Insert
    void insertTopSkillPoints(TopSkillPoints... topSkillPoints);

    @Query("DELETE FROM TopLearner")
    void deleteAllTopLearners();

    @Query("DELETE FROM TopSkillPoints")
    void deleteAllTopSkillPoints();
}
