package com.vuforia.samples.ar.data.models;

import lombok.Data;

/**
 * Created by grishberg on 15.04.17.
 */
@Data
public class Comment {
    private String text;
    private UserInfo owner;
}
