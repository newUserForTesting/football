package com.league.football.controller;

import org.json.simple.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Custom Error Controller
 *
 * @author Saurabh Kumar
 */
@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH )
    public String error() {
        JSONObject result = new JSONObject();
        result.put("error", "Invalid input provided. country_name, team_name and league_name are required.");
        return result.toJSONString();
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
