package com.league.football.controller;

import com.league.football.services.FootballServiceWrapper;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.simple.*;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Football application controller.
 *
 * @author Saurabh Kumar
 */
@RestController
public class ApplicationController {

	@Autowired
	private RestTemplate restTemplate;

	private FootballServiceWrapper footballServiceWrapper = new FootballServiceWrapper();

	private static HashMap<String, String> countriesMap = new HashMap();

	/**
	 * Gets standing value for a combination of country, league and team.
	 *
	 * @param country
	 * @param league
	 * @param team
	 * @return
	 */
	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public JSONObject getStandings(@RequestParam(value = "country_name")String country,
							   @RequestParam(value = "league_name") String league,
							   @RequestParam(value = "team_name") String team) {

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
		return result;
	}

	/**
	 *  Get country Id for a give country name.
	 *
	 * @param countryName
	 * @return
	 */
	public String getCountryIdFromCountryName(String countryName) {
		if (countriesMap == null || countriesMap.get(countryName) == null) {
			String countries = footballServiceWrapper.getCountries();
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