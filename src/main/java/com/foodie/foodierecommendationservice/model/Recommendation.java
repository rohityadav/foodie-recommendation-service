package com.foodie.foodierecommendationservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by ryadav1 on 7/23/17.
 */
@Getter @Setter
@ToString
public class Recommendation {
    private String username;
    private String[] interests;
    private List<Restuarent> restuarents;

    public Recommendation() {
    }

    public Recommendation(String username, String[] interests, List<Restuarent> restuarents) {
        this.username = username;
        this.interests = interests;
        this.restuarents = restuarents;
    }
}
