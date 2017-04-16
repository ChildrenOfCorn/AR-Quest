package com.vuforia.samples.VuforiaSamples.app.ImageTargets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vuforia.samples.VuforiaSamples.R;
import com.vuforia.samples.ar.di.DiContainer;
import com.vuforia.samples.ar.repository.AuthTokenRepository;
import com.vuforia.samples.ar.repository.SimpleCallback;
import com.vuforia.samples.ar.repository.auth.AuthRepository;

import javax.inject.Inject;

/**
 * Created by grishberg on 16.04.17.
 */

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, SimpleCallback<Boolean> {

    private Button buttonAuth;
    private EditText loginEdit;
    private ProgressBar progressBar;

    @Inject
    AuthRepository authRepository;

    @Inject
    AuthTokenRepository authTokenRepository;

    public static void start(final Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        DiContainer.getAppComponent().inject(this);

        buttonAuth = (Button) findViewById(R.id.auth_screen_button_enter);
        buttonAuth.setOnClickListener(this);
        loginEdit = (EditText) findViewById(R.id.auth_screen_login);
        progressBar = (ProgressBar) findViewById(R.id.auth_screen_progress);
        setEnabled(true);

        if (!TextUtils.isEmpty(authTokenRepository.getLogin())) {
            onSuccess(true);
        }
    }

    public void showProgress() {
        setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    public void showSuccess() {
        final Intent intent = new Intent(this, ImageTargets.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void showFail(final String message) {
        Toast.makeText(this, R.string.auth_network_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(final View view) {
        authRepository.auth(loginEdit.getText().toString(), this);
        showProgress();
        setEnabled(false);
    }

    private void setEnabled(final boolean enabled) {
        loginEdit.setEnabled(enabled);
        buttonAuth.setEnabled(enabled);
    }

    @Override
    public void onSuccess(Boolean result) {
        hideProgress();
        showSuccess();
    }

    @Override
    public void onFail(Throwable error) {
        showFail(error.getMessage() != null ? error.getMessage() : "error!!!");
    }
}
