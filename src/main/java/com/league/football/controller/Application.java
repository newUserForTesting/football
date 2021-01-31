package com.league.football.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Application {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

		final String uri = "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";

		//TODO: Autowire the RestTemplate in all the examples
		RestTemplate restTemplate = new RestTemplate();

		String result = restTemplate.getForObject(uri, String.class);
		System.out.println(result);

		return result;
	}
	
}