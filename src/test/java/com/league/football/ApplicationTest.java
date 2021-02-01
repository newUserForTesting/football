package com.league.football;

import com.league.football.controller.ApplicationController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApplicationContext.class})
@WebAppConfiguration
public class ApplicationTest {

	@Test
	public void getStandings() throws Exception{
        ApplicationController applicationController = new ApplicationController();

	}

}
