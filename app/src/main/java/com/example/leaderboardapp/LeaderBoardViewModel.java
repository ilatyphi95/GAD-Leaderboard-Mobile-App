package com.example.leaderboardapp;

import androidx.lifecycle.ViewModel;

import com.example.leaderboardapp.models.TopLearner;
import com.example.leaderboardapp.models.TopSkillPoints;

import java.util.List;

public class LeaderBoardViewModel extends ViewModel {
    public boolean isNewlyCreated = true;

    private List<TopLearner> mTopLearners;
    private List<TopSkillPoints> mTopSkillPoints;

    public List<TopLearner> getTopLearners() {
        return mTopLearners;
    }

    public void setTopLearners(List<TopLearner> topLearners) {
        mTopLearners = topLearners;
    }

    public List<TopSkillPoints> getTopSkillPoints() {
        return mTopSkillPoints;
    }

    public void setTopSkillPoints(List<TopSkillPoints> topSkillPoints) {
        mTopSkillPoints = topSkillPoints;
    }
}
