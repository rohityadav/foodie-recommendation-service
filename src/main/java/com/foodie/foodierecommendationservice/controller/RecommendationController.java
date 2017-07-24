package com.foodie.foodierecommendationservice.controller;

import com.foodie.foodierecommendationservice.model.Recommendation;
import com.foodie.foodierecommendationservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by ryadav1 on 7/23/17.
 */
@RestController
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;
    @RequestMapping(method = RequestMethod.GET, value = "/recommendations/{username}")
    public List<Recommendation> getRecommendationsFor(@PathVariable("username") String username){
        return recommendationService.fetch(username);
    }

}
