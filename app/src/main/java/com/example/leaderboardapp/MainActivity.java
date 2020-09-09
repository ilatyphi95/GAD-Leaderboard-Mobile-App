package com.example.leaderboardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.leaderboardapp.adapters.LeaderboardViewPagerAdapter;
import com.example.leaderboardapp.fragments.RecyclerViewFragment;
import com.example.leaderboardapp.models.TopLearner;
import com.example.leaderboardapp.models.TopSkillPoints;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSwipeRefreshListener {

    @SuppressLint("StaticFieldLeak")
    public static LeaderboardViewPagerAdapter mViewPagerAdapter;
    public ProgressBar mProgressBar;
    public LeaderBoardViewModel mViewModel;

    private boolean restoringFromCache = false;
    private boolean queriedTopLearners = false;
    private boolean queriedTopSkillPoints = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button btnSubmit = findViewById(R.id.btnSubmit);

        LeaderboardDatabaseUtils.init(this);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        mViewModel = viewModelProvider.get(LeaderBoardViewModel.class);

        mProgressBar = findViewById(R.id.progressBar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        mViewPagerAdapter = new LeaderboardViewPagerAdapter(getSupportFragmentManager(), 0, this);
        viewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        btnSubmit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SubmitActivity.class);
            startActivity(intent);
        });

        if (savedInstanceState != null && mViewModel.isNewlyCreated) {
            restoringFromCache = true;
            populateViewModelsFromCache();
            mViewModel.isNewlyCreated = false;
        }
    }

    private void populateViewModelsFromCache() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<TopLearner>> topLearnersQueryTask = new AsyncTask<Void, Void, List<TopLearner>>() {
            @Override
            protected List<TopLearner> doInBackground(Void... voids) {
                return LeaderboardDatabaseUtils.sLeaderboardDatabase.mLeaderboardDao().getAllTopLearners();
            }

            @Override
            protected void onPostExecute(List<TopLearner> topLearners) {
                mViewModel.setTopLearners(topLearners);
                queriedTopLearners = true;
            }
        };

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<TopSkillPoints>> topSkillPointsQueryTask = new AsyncTask<Void, Void, List<TopSkillPoints>>() {
            @Override
            protected List<TopSkillPoints> doInBackground(Void... voids) {
                return LeaderboardDatabaseUtils.sLeaderboardDatabase.mLeaderboardDao().getAllTopSkillPoints();
            }

            @Override
            protected void onPostExecute(List<TopSkillPoints> topSkillPoints) {
                mViewModel.setTopSkillPoints(topSkillPoints);
                queriedTopSkillPoints = true;
            }
        };

        topLearnersQueryTask.execute();
        topSkillPointsQueryTask.execute();
    }

    private void restoreFragmentDataFromViewModel() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                while (true) {
                    if (queriedTopLearners && queriedTopSkillPoints)
                        break;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                restoreFragmentData();
                showSnackBar();
            }
        };
        task.execute();
    }

    public void restoreCachedData() {
        populateViewModelsFromCache();
        restoreFragmentDataFromViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateAdapters();
    }

    public void populateAdapters() {
        if (mViewModel.isNewlyCreated) {
            scheduleRequest();
            mViewModel.isNewlyCreated = false;
        }
        else if (restoringFromCache) {
            restoringFromCache = false;
            restoreFragmentDataFromViewModel();
        }
        else {
            restoreFragmentData();
        }
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(R.id.viewPager), "Network error. Leaderbord might be outdated",
                BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    private void restoreFragmentData() {
        RecyclerViewFragment topLearnersFragment = (RecyclerViewFragment) mViewPagerAdapter.getFragment(
                LeaderboardViewPagerAdapter.TOP_LEARNERS);
        topLearnersFragment.setTopLearnersList(mViewModel.getTopLearners());

        RecyclerViewFragment topSkillPointsFragment = (RecyclerViewFragment) mViewPagerAdapter.getFragment(
                LeaderboardViewPagerAdapter.TOP_SKILL_IQS);
        topSkillPointsFragment.setTopSkillPointsList(mViewModel.getTopSkillPoints());
    }

    private void scheduleRequest() {
        ComponentName componentName = new ComponentName(this, LeaderboardJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        jobScheduler.schedule(jobInfo);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (!connectivityManager.isDefaultNetworkActive())
                restoreCachedData();
        }, 2000);
    }

    private void scheduleEndRefreshing(SwipeRefreshLayout refreshLayout) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void refresh(SwipeRefreshLayout refreshLayout) {
        scheduleRequest();
        scheduleEndRefreshing(refreshLayout);
    }
}
