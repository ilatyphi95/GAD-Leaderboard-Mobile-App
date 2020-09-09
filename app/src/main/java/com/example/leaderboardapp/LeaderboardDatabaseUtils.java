package com.example.leaderboardapp;

import android.content.Context;
import android.os.AsyncTask;

import com.example.leaderboardapp.models.TopLearner;
import com.example.leaderboardapp.models.TopSkillPoints;

import java.util.List;

public class LeaderboardDatabaseUtils {
    public static LeaderboardDatabase sLeaderboardDatabase;

    static void init(Context context) {
        sLeaderboardDatabase = LeaderboardDatabase.getDatabase(context);
    }

    public static void insertTopLearners(List<TopLearner> topLearners) {
        TopLearner[] topLearnersArray = new TopLearner[topLearners.size()];
        topLearners.toArray(topLearnersArray);


        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sLeaderboardDatabase.mLeaderboardDao().deleteAllTopLearners();
                sLeaderboardDatabase.mLeaderboardDao().insertTopLearners(topLearnersArray);
                return null;
            }
        };
        task.execute();
    }

    public static void insertTopSkillPoints(List<TopSkillPoints> topSkillPoints) {
        TopSkillPoints[] topSkillPointsArray = new TopSkillPoints[topSkillPoints.size()];
        topSkillPoints.toArray(topSkillPointsArray);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                sLeaderboardDatabase.mLeaderboardDao().deleteAllTopSkillPoints();
                sLeaderboardDatabase.mLeaderboardDao().insertTopSkillPoints(topSkillPointsArray);
                return null;
            }
        };

        task.execute();
    }
}
