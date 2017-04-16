package com.vuforia.samples.ar.data.beans;

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
	public static int NO_RATING = -1;
	public static float MAX_RATING = 5.0f;

	private long id;
	private String name; // название продукта
	private List<UserInfo> users; //  массив юзеров, которые просмотрели
	private List<Comment> comments; // комментарии
	private String url; // урл объекта
	private String briefDesc; //описание объекта
	private float rating;


    public ProductInfo(long id) {
        this.id = id;
    }

    public int getRatingInPercents() {
		if (rating == NO_RATING) {
			return 0;
		}

		if (rating > MAX_RATING) {
			return 100;
		}

		return (int)(100 / MAX_RATING * rating);
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
