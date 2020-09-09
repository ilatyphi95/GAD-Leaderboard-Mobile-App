package com.example.leaderboardapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.leaderboardapp.models.TopLearner;
import com.example.leaderboardapp.models.TopSkillPoints;

@Database(entities = {TopLearner.class, TopSkillPoints.class}, version = 1)
public abstract class LeaderboardDatabase extends RoomDatabase {
    public abstract LeaderboardDoa mLeaderboardDoa();

    private static LeaderboardDatabase INSTANCE;

    static LeaderboardDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    LeaderboardDatabase.class, "Leaderboard_database").build();
        }
        return INSTANCE;
    }
}
