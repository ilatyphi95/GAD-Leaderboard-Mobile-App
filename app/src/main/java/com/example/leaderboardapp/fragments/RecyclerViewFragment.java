package com.example.leaderboardapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.leaderboardapp.adapters.LeaderboardRecyclerAdapter;
import com.example.leaderboardapp.OnSwipeRefreshListener;
import com.example.leaderboardapp.R;
import com.example.leaderboardapp.models.TopLearners;
import com.example.leaderboardapp.models.TopSkillPoints;

import java.util.List;

public class RecyclerViewFragment extends Fragment {

    private LeaderboardRecyclerAdapter mAdapter;
    private OnSwipeRefreshListener mRefreshListener;

    public RecyclerViewFragment() {}

    public RecyclerViewFragment(OnSwipeRefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public void setTopLearnersList(List<TopLearners> topLearnersList) {
        if (mAdapter != null)
            mAdapter.setTopLearners(topLearnersList);
    }

    public void setTopSkillPointsList(List<TopSkillPoints> topSkillPointsList) {
        if (mAdapter != null)
            mAdapter.setTopSkillPoints(topSkillPointsList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(() -> mRefreshListener.refresh(refreshLayout));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new LeaderboardRecyclerAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
