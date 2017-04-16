package com.vuforia.samples.ar.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.vuforia.samples.ar.data.beans.AuthContainer;

/**
 * Created by grishberg on 16.04.17.
 */

public class AuthTokenRepositoryImpl implements AuthTokenRepository {
    private static final String DEF_VALUE = "";
    private static final long DEF_VALUE_LONG = 0;
    private static final String PREF_USER_ID = "pref_user_id";
    private static final String PREF_LOGIN = "pref_login";

    private AuthContainer authContainer;
    private String login;
    private final SharedPreferences preferences;

    public AuthTokenRepositoryImpl(final Context context) {
        preferences = context.getSharedPreferences("mainPreferences", Context.MODE_PRIVATE);
    }

    @Override
    public AuthContainer getAuthInfo() {
        if (authContainer == null) {
            final long userId = getLong(PREF_USER_ID);
            final String login = get(PREF_LOGIN);
            if (!TextUtils.isEmpty(login)) {
                authContainer = new AuthContainer(userId, login);
            }
        }
        return authContainer;
    }

    @Override
    public void setAuthInfo(final AuthContainer authContainer) {
        put(PREF_LOGIN, authContainer.getName());
        putLong(PREF_USER_ID, authContainer.getId());
        this.authContainer = authContainer;
    }

    @Override
    public void setCurrentLogin(final String login) {
        this.login = login;
        put(PREF_LOGIN, login);
    }

    @Override
    public String getLogin() {
        if (login == null) {
            login = get(PREF_LOGIN);
        }
        return login;
    }

    private void put(final String name, final String val) {
        final SharedPreferences.Editor ed = preferences.edit();
        ed.putString(name, val);
        ed.apply();
    }

    private void putLong(final String name, final long val) {
        final SharedPreferences.Editor ed = preferences.edit();
        ed.putLong(name, val);
        ed.apply();
    }

    private String get(final String name) {
        return preferences.getString(name, DEF_VALUE);
    }

    private long getLong(final String name) {
        return preferences.getLong(name, DEF_VALUE_LONG);
    }
}
