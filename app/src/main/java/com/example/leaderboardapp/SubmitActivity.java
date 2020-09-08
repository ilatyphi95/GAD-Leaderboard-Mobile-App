package com.example.leaderboardapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.leaderboardapp.fragments.ConfirmFragment;
import com.example.leaderboardapp.fragments.PopupDialogFragment;
import com.example.leaderboardapp.routes.SubmitProjectService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmitActivity extends AppCompatActivity {

    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_submit_toolbar);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etLink = findViewById(R.id.etLink);

        ImageView backButton = getSupportActionBar().getCustomView().findViewById(R.id.imgViewBack);
        backButton.setOnClickListener(v -> finish());

        Button submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(v -> {
            if (validateFields())
                new ConfirmFragment(this).show(getSupportFragmentManager(), ConfirmFragment.TAG);
        });
    }

    private boolean validateFields() {
        String errMessage = "Field cannot be empty";
        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError(errMessage);
            return false;
        }
        else if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError(errMessage);
            return false;
        }
        else if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError(errMessage);
            return false;
        }
        else if (!etEmail.getText().toString().matches("\\w+@\\w+\\.\\w+")) {
            etEmail.setError("Invalid email");
            return false;
        }
        else if (etLink.getText().toString().isEmpty()) {
            etLink.setError(errMessage);
            return false;
        }
        else if (!etLink.getText().toString().matches("https*://.+")) {
            etLink.setError("Invalid url");
            return false;
        }
        return true;
    }

    public void submit() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String link = etLink.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.92:8080")
                .build();

        SubmitProjectService service = retrofit.create(SubmitProjectService.class);
        Call<Void> submitTask = service.submit(firstName, lastName, email, link);
        submitTask.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new PopupDialogFragment(SubmitActivity.this, PopupDialogFragment.SUCCESS_POPUP)
                        .show(getSupportFragmentManager(), "success");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new PopupDialogFragment(SubmitActivity.this, PopupDialogFragment.ERROR_POPUP)
                        .show(getSupportFragmentManager(), "error");
            }
        });
    }
}
