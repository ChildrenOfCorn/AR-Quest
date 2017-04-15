package com.vuforia.samples.ar.data.models;

import java.util.List;

import lombok.Data;
import lombok.Setter;

/**
 * Created by grishberg on 15.04.17.
 * Данные о продукте
 */
@Data
@Setter
public class ProductInfo {

	private long id;
	private String name; // название продукта
	private List<UserInfo> users; //  массив юзеров, которые просмотрели
	private List<Comment> comments; // комментарии
	private String url; // урл объекта
	private String briefDesc; //описание объекта

    public ProductInfo(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
