package com.league.football;

import org.junit.jupiter.api.Test;
import com.league.football.controller.ApplicationController;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationTest {

	ApplicationController applicationController = new ApplicationController();

	@Test
	public void getStandings() {
		String response = applicationController.getStandings(Constants.COUNTRY, Constants.LEAGUE, Constants.TEAM).toJSONString();

		Assert.assertTrue(response.contains(Constants.COUNTRY));
		Assert.assertTrue(response.contains(Constants.LEAGUE));
		Assert.assertTrue(response.contains("overall_league_position"));
	}

	@Test
	public void getStandingsInvalidCountry() {
		String response = applicationController.getStandings("InvalidCountry", Constants.LEAGUE, Constants.TEAM).toJSONString();

		Assert.assertEquals("Invalid input", Constants.INVALID_INPUT_ERROR, response);
	}

	@Test
	public void getStandingsInvalidLeague() {
		String response = applicationController.getStandings(Constants.LEAGUE, "InvalidLeague", Constants.TEAM).toJSONString();

		Assert.assertEquals("Invalid input", Constants.INVALID_INPUT_ERROR, response);
	}

	@Test
	public void getStandingsInvalidTeam() {
		String response = applicationController.getStandings(Constants.LEAGUE, Constants.LEAGUE, "InvalidTeam").toJSONString();

		Assert.assertEquals("Invalid input", Constants.INVALID_INPUT_ERROR, response);
	}
}
