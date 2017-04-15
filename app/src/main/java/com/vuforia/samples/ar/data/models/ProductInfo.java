package com.vuforia.samples.ar.data.models;

import java.util.List;

/**
 * Created by grishberg on 15.04.17.
 * Данные о продукте
 */

public class ProductInfo {
    private long id;
    private String name; // название продукта
    private List<UserInfo> users; //  массив юзеров, которые просмотрели
    private List<Comment> comments; // комментарии
    private String url; // урл объекта
}
