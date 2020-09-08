package com.example.leaderboardapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.leaderboardapp.R;
import com.example.leaderboardapp.SubmitActivity;

public class ConfirmFragment extends DialogFragment {

    public static final String TAG = "com.example.leaderboardapp.fragments.ConfirmFragment";
    private Context mContext;

    public ConfirmFragment(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        ConstraintLayout parentLayout = view.findViewById(R.id.constrLayout);
        parentLayout.setMinWidth(displayMetrics.widthPixels - getResources().getInteger(R.integer.dialog_margin));

        setRetainInstance(true);
        setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button okButton = view.findViewById(R.id.okBtn);
        ImageView imgCancel = view.findViewById(R.id.imgViewCancel);

        okButton.setOnClickListener(v -> {
            ((SubmitActivity)getActivity()).submit();
            dismiss();
        });
        imgCancel.setOnClickListener(v -> dismiss());
    }
}
