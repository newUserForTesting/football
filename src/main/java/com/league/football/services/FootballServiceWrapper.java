package com.league.football.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Wrapper class for calling Football service APIs
 *
 * @author Saurabh Kumar
 */
@Component
public class FootballServiceWrapper {
    public final String STANDINGS_URI = "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
    public final String COUNTRIES_URI = "https://apiv2.apifootball.com/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    public String getStandings() {
        String standings = restTemplate.getForObject(STANDINGS_URI, String.class);
        return standings;
    }

    public String getCountries() {
        String countries = restTemplate.getForObject(COUNTRIES_URI, String.class);
        return countries;
    }
}
