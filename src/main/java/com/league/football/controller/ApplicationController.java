package com.league.football.controller;

import com.league.football.services.FootballServiceWrapper;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.simple.*;

import java.util.HashMap;
import java.util.Iterator;

@RestController
public class ApplicationController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FootballServiceWrapper footballServiceWrapper;

	private static HashMap<String, String> countriesMap = new HashMap();

	@GetMapping("/")
	public String greeting(@RequestParam(value = "country_name")String country,
						   @RequestParam(value = "league_name") @Nullable String league,
						   @RequestParam(value = "team_name") @Nullable String team) {

		if (country == null || league == null || team == null) {
			JSONObject result = new JSONObject();
			result.put("error", "Invalid input provided. country_name, team_name and league_name are required.");
		}

		String standings = footballServiceWrapper.getStandings();

		JSONObject result = new JSONObject();

		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(standings);

			Iterator<JSONObject> iterator = jsonArray.iterator();
			while(iterator.hasNext()) {
				JSONObject standing = iterator.next();
				if (team.equals(standing.get("team_name"))
						&& country.equals(standing.get("country_name"))
						&& league.equals(standing.get("league_name"))) {
					result.put("country_id", getCountryIdFromCountryName((String) standing.get("country_name")));
					result.put("country_name", standing.get("country_name"));
					result.put("team_id", standing.get("team_id"));
					result.put("team_name", standing.get("team_name"));
					result.put("league_id", standing.get("league_id"));
					result.put("league_name", standing.get("league_name"));
					result.put("overall_league_position", standing.get("overall_league_position"));
				}
			}

		} catch (Exception e) {
			result = new JSONObject();
			result.put("error", "An error occurred. Try after sometime.");
		}
		if (result.isEmpty()) {
			result = new JSONObject();
			result.put("error", "One or more input parameters is invalid. Provide valid values for country_name, team_name and league_name.");
		}
		return result.toJSONString();
	}

	private String getCountryIdFromCountryName(String countryName) {
		if (countriesMap == null || countriesMap.get(countryName) == null) {
			RestTemplate restTemplate = new RestTemplate();
			final String countries_uri = "https://apiv2.apifootball.com/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
			String countries = restTemplate.getForObject(countries_uri, String.class);
			try {
				JSONParser jsonParser = new JSONParser();
				JSONArray jsonArray = (JSONArray) jsonParser.parse(countries);

				Iterator<JSONObject> iterator = jsonArray.iterator();
				while(iterator.hasNext()) {
					JSONObject country = iterator.next();
					countriesMap.put((String)country.get("country_name"), (String)country.get("country_id"));
				}
			} catch (Exception e) {

			}
		}
		return countriesMap.get(countryName);
	}

}