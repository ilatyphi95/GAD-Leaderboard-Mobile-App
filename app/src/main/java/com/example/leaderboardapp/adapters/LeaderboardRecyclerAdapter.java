package com.example.leaderboardapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leaderboardapp.R;
import com.example.leaderboardapp.models.GADLeaderboard;
import com.example.leaderboardapp.models.TopLearners;
import com.example.leaderboardapp.models.TopSkillPoints;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.LeaderboardRecyclerViewHolder> {

    private GADLeaderboard mLeaderboard = new GADLeaderboard();

    public void setTopLearners(List<TopLearners> topLearnersList) {
        mLeaderboard.mTopLearners = topLearnersList;
        notifyDataSetChanged();
    }

    public void setTopSkillPoints(List<TopSkillPoints> topSkillPointsList) {
        mLeaderboard.mTopSkillPoints = topSkillPointsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeaderboardRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        return new LeaderboardRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRecyclerViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (mLeaderboard.mTopLearners != null)
            return mLeaderboard.mTopLearners.size();
        else if (mLeaderboard.mTopSkillPoints != null)
            return mLeaderboard.mTopSkillPoints.size();
        else
            return 0;
    }

    class LeaderboardRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvDetails;
        private ImageView imageView;

        LeaderboardRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bindView(int position) {
            String name;
            String details;
            String badgeUrl;
            if (mLeaderboard.mTopLearners != null) {
                TopLearners topLearner = mLeaderboard.mTopLearners.get(position);
                name = topLearner.getName();
                details = topLearner.getDetails();
                badgeUrl = topLearner.getBadgeUrl();
            }
            else {
                TopSkillPoints topSkillPoints = mLeaderboard.mTopSkillPoints.get(position);
                name = topSkillPoints.getName();
                details = topSkillPoints.getDetails();
                badgeUrl = topSkillPoints.getBadgeUrl();
            }

            setViewData(name, details, badgeUrl);
        }

        private void setViewData(String name, String details, String badgeUrl) {
            tvName.setText(name);
            tvDetails.setText(details);
            Uri badge = Uri.parse(badgeUrl);
            Picasso.get().load(badge).into(imageView);
        }
    }
}
