package com.example.leaderboardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.leaderboardapp.adapters.LeaderboardViewPagerAdapter;
import com.example.leaderboardapp.fragments.RecyclerViewFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnSwipeRefreshListener {

    @SuppressLint("StaticFieldLeak")
    public static LeaderboardViewPagerAdapter mViewPagerAdapter;
    public ProgressBar mProgressBar;
    public LeaderBoardViewModel mViewModel;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Button btnSubmit = findViewById(R.id.btnSubmit);

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
        else {
            RecyclerViewFragment topLearnersFragment = (RecyclerViewFragment) mViewPagerAdapter.getFragment(
                    LeaderboardViewPagerAdapter.TOP_LEARNERS);
            topLearnersFragment.setTopLearnersList(mViewModel.getTopLearners());

            RecyclerViewFragment topSkillPointsFragment = (RecyclerViewFragment) mViewPagerAdapter.getFragment(
                    LeaderboardViewPagerAdapter.TOP_SKILL_IQS);
            topSkillPointsFragment.setTopSkillPointsList(mViewModel.getTopSkillPoints());
        }
    }

    private void scheduleRequest() {
        ComponentName componentName = new ComponentName(this, LeaderboardJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        jobScheduler.schedule(jobInfo);
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
