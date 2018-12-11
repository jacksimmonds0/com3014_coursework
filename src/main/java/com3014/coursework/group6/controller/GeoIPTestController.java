package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.GeoIP;
import com3014.coursework.group6.service.GeoIPLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * A RESTful controller for the location based on the IP address for nearest cinema
 */
@RestController
public class GeoIPTestController {

    private GeoIPLocationService locationService;

    /**
     * Constructor for to instantiate a new location service
     */
    public GeoIPTestController() throws IOException{
        locationService = new GeoIPLocationService();
    }

    /**
     * The REST endpoint for getting the response from the geolocation based on the IP address provided
     *
     * @param ipAddress
     *          the IP address to find the location
     * @return the response JSON for the IP location
     */
    @RequestMapping(value ="/GeoIPTest", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody String getLocation(@RequestParam(value="ipAddress",required = true) String ipAddress) throws Exception {
        GeoIPLocationService locationService = new GeoIPLocationService();
        GeoIP geoIP = locationService.getLocation(ipAddress);
        return geoIP.toString();
    }
}
