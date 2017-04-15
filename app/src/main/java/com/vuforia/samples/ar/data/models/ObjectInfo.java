package com.vuforia.samples.ar.data.models;

/**
 * Created by grishberg on 15.04.17.
 * какая то мета инфа по распознанному объекту
 */

public class ObjectInfo {
    private long id;
    private String name;

    public ObjectInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ObjectInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
