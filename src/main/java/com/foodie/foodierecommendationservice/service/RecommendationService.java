package com.foodie.foodierecommendationservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodie.foodierecommendationservice.model.Recommendation;
import com.foodie.foodierecommendationservice.model.Restuarent;
import com.foodie.foodierecommendationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created by ryadav1 on 7/23/17.
 */
@Service
public class RecommendationService {
    @Autowired
    private RestTemplate restTemplate;

    public List<Recommendation> fetch(String username) {
        return buildRecommendations(username, buildUserDetails(), buildRestuarentDetails());
    }

    private List<Recommendation> buildRecommendations(String username, List<User> users, Map<String, List<Restuarent>> restuarentsMap) {
        List<Recommendation> recommendations = new ArrayList<>();
        for (User user : users){
            if (username.equals(user.getUserId())){
                List<Restuarent> restuarents = new ArrayList<>();
                for (String choice : user.getChoices()){
                    if (restuarentsMap.containsKey(choice)){
                        restuarents.addAll(restuarentsMap.get(choice));
                    }
                }
                recommendations.add(new Recommendation(user.getUserId(), user.getChoices(), restuarents));
            }
        }
        return recommendations;
    }

    private Map<String, List<Restuarent>> buildRestuarentDetails() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Restuarent>> restuarentMap = new HashMap<>();
        ResponseEntity<String> exchange = restTemplate.getForEntity("http://foodie-restuarent-service/restuarents", String.class);
        try {
            Iterator<JsonNode> elements = mapper.readTree(exchange.getBody()).get("_embedded").get("restuarents").elements();
            while (elements.hasNext()){
                JsonNode node = elements.next();
                String name = node.get("name").asText();
                String type = node.get("type").asText();
                if (restuarentMap.containsKey(type)){
                    restuarentMap.get(type).add(new Restuarent(name, type));
                }else{
                    List<Restuarent> restuarents = new ArrayList<>();
                    restuarents.add(new Restuarent(name, type));
                    restuarentMap.put(type, restuarents);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restuarentMap;

    }

    private List<User> buildUserDetails() {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<>();
        ResponseEntity<String> exchange = restTemplate.getForEntity("http://foodie-user-service/users", String.class);
        try {
            Iterator<JsonNode> elements = mapper.readTree(exchange.getBody()).get("_embedded").get("users").elements();
            while (elements.hasNext()){
                JsonNode node = elements.next();
                List<String> values = new ArrayList<>();

                if (node.get("foodChoices").isArray()){
                    for (final JsonNode nd : node.get("foodChoices")){
                        values.add(nd.asText());
                    }
                }
                users.add(new User(node.get("userId").asText(), buildArrayFrom(values)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return users;
    }

    private String[] buildArrayFrom(List<String> values) {
        String[] array = new String[values.size()];
        int i=0;
        for (String value : values){
            array[i] = value;
            i++;
        }
        return array;
    }
}
