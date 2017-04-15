package com.vuforia.samples.ar.data.models;

import lombok.Data;

/**
 * Created by grishberg on 15.04.17.
 */
@Data
public class Comment {
    public Comment(final String text, final UserInfo owner) {
        this.text = text;
        this.owner = owner;
    }
    private String text;
    private UserInfo owner;
}
