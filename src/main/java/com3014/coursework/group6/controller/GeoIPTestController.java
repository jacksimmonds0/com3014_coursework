package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.GeoIP;
import com3014.coursework.group6.service.GeoIPLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class GeoIPTestController {
    private GeoIPLocationService locationService;

    public GeoIPTestController() throws IOException{
        locationService = new GeoIPLocationService();
    }

    @RequestMapping(value ="/GeoIPTest", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody String getLocation(@RequestParam(value="ipAddress",required = true) String ipAddress, HttpServletRequest request) throws Exception {
        GeoIPLocationService locationService = new GeoIPLocationService();
        GeoIP geoIP = locationService.getLocation(ipAddress);
        return geoIP.toString();
    }
}
