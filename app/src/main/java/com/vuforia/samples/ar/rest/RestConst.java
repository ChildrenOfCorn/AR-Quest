package com.vuforia.samples.ar.rest;

/**
 * Created by grishberg on 16.04.17.
 */

public final class RestConst {
    public static final long PAGE_LIMIT = 2000;
    public static final int MAX_POINTS_PER_SCREEN = 500;

    private RestConst() {
    }

    // Ошибки
    static final class Errors {
        static final int WRONG_CREDENTIALS = 1000;
        static final int TOKEN_EXPIRED = 1001;

        private Errors() {
        }
    }
}
