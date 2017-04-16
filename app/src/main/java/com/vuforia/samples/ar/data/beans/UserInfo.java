package com.vuforia.samples.ar.data.beans;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by grishberg on 15.04.17.
 */

@Data
public class UserInfo implements Serializable{
    private long id;
    private String name;
}
