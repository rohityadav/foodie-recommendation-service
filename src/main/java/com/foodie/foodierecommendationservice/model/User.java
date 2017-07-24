package com.foodie.foodierecommendationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ryadav1 on 7/24/17.
 */
@Getter
@Setter
@ToString
public class User {
    private String userId;
    private String[] choices;

    public User(String userId, String[] choices) {
        this.userId = userId;
        this.choices = choices;
    }
}
