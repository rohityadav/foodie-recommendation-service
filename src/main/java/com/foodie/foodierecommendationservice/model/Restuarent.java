package com.foodie.foodierecommendationservice.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ryadav1 on 7/23/17.
 */
@Getter
@Setter
@ToString
public class Restuarent {
    private String name;
    private String type;

    public Restuarent(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
