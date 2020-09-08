package com.example.leaderboardapp;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

public class LeaderboardJobService extends JobService {
    public LeaderboardJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        @SuppressLint("StaticFieldLeak") AsyncTask<JobParameters, Void, Void> task = new AsyncTask<JobParameters, Void, Void>() {
            @Override
            protected Void doInBackground(JobParameters... jobParameters) {
                while (true) {
                    if (MainActivity.mViewPagerAdapter.hasFinished)
                        break;
                }
                MainActivity.mViewPagerAdapter.hasFinished = false;
                jobFinished(params, false);
                return null;
            }
        };

        MainActivity.mViewPagerAdapter.fetchLeaderboard();
        task.execute(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
