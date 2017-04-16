package com.vuforia.samples.ar.repository;

import com.vuforia.samples.ar.data.beans.AuthContainer;

/**
 * Created by grishberg on 16.04.17.
 */

public interface AuthTokenRepository {
    AuthContainer getAuthInfo();

    void setAuthInfo(AuthContainer authContainer);

    void updateAccessToken(String newAccessToken);

    void setCurrentLogin(String login);

    String getLogin();
}