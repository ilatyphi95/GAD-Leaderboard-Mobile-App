package com.example.leaderboardapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.leaderboardapp.R;

public class PopupDialogFragment extends DialogFragment {

    public static final int ERROR_POPUP = 0;
    public static final int SUCCESS_POPUP = 1;

    private Context mContext;
    private int mPopupConst;

    public PopupDialogFragment(Context context, int popupType) {
        mContext = context;
        mPopupConst = popupType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (mPopupConst == ERROR_POPUP)
            view =  inflater.inflate(R.layout.dialog_failure, container, false);
        else
            view = inflater.inflate(R.layout.dialog_success, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        ConstraintLayout parentLayout = view.findViewById(R.id.popupParent);
        parentLayout.setMinWidth(displayMetrics.widthPixels - getResources().getInteger(R.integer.dialog_margin));

        setRetainInstance(true);
        return view;
    }

}
