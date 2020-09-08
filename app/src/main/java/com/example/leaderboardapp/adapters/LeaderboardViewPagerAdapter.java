package com.example.leaderboardapp.adapters;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.leaderboardapp.MainActivity;
import com.example.leaderboardapp.fragments.RecyclerViewFragment;
import com.example.leaderboardapp.models.TopLearners;
import com.example.leaderboardapp.models.TopSkillPoints;
import com.example.leaderboardapp.routes.GetDataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderboardViewPagerAdapter extends FragmentPagerAdapter {

    private final String BASE_URL = "https://gadsapi.herokuapp.com";
    public static final int TOP_LEARNERS = 0;
    public static final int TOP_SKILL_IQS = 1;
    public boolean hasFinished = false;

    private boolean mTopLearnersFinished;
    private boolean mTopSkillPointsFinished;

    private FragmentManager mFragmentManager;
    private ProgressBar mProgressBar;
    private MainActivity mActivity;

    public LeaderboardViewPagerAdapter(@NonNull FragmentManager fm, int behavior, MainActivity activity) {
        super(fm, behavior);
        mFragmentManager = fm;
        mProgressBar = activity.mProgressBar;
        mActivity = activity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new RecyclerViewFragment(mActivity);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == TOP_LEARNERS)
            return "Learning Leaders";
        else
            return "Skill IQ Leaders";
    }

    public Fragment getFragment(int index) {
        return mFragmentManager.getFragments().get(index);
    }

    public void fetchLeaderboard() {
        mTopLearnersFinished = false;
        mTopSkillPointsFinished = false;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDataService service = retrofit.create(GetDataService.class);
        Call<List<TopLearners>> topLearners = service.getTopLearners();
        Call<List<TopSkillPoints>> topSkillPoints = service.getTopSkillIQs();

        mTopLearnersFinished = false;
        mTopSkillPointsFinished = false;

        mProgressBar.setVisibility(View.VISIBLE);

        topLearners.enqueue(new Callback<List<TopLearners>>() {
            @Override
            public void onResponse(Call<List<TopLearners>> call, Response<List<TopLearners>> response) {
                RecyclerViewFragment topLearnersFragment = (RecyclerViewFragment) getFragment(TOP_LEARNERS);
                List<TopLearners> topLearnersList = response.body();
                topLearnersFragment.setTopLearnersList(topLearnersList);
                mActivity.mViewModel.setTopLearners(topLearnersList);
                mTopLearnersFinished = true;
                dismiss();
            }

            @Override
            public void onFailure(Call<List<TopLearners>> call, Throwable t) {
                mTopLearnersFinished = true;
                dismiss();
            }
        });

        topSkillPoints.enqueue(new Callback<List<TopSkillPoints>>() {
            @Override
            public void onResponse(Call<List<TopSkillPoints>> call, Response<List<TopSkillPoints>> response) {
                RecyclerViewFragment topSkillPointsFragment = (RecyclerViewFragment) getFragment(TOP_SKILL_IQS);
                List<TopSkillPoints> topSkillPointsList = response.body();
                topSkillPointsFragment.setTopSkillPointsList(topSkillPointsList);
                mActivity.mViewModel.setTopSkillPoints(topSkillPointsList);
                mTopSkillPointsFinished = true;
                dismiss();
            }

            @Override
            public void onFailure(Call<List<TopSkillPoints>> call, Throwable t) {
                mTopSkillPointsFinished = true;
                dismiss();
            }
        });
    }

    private void dismiss() {
        if (mTopLearnersFinished && mTopSkillPointsFinished) {
            mProgressBar.setVisibility(View.INVISIBLE);
            hasFinished = true;
        }
    }
}
