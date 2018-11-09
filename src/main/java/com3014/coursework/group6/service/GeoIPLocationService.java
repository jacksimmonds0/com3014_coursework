package com3014.coursework.group6.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com3014.coursework.group6.model.GeoIP;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class GeoIPLocationService {
    private DatabaseReader dbReader;

    public  GeoIPLocationService() throws IOException{
        File database = new ClassPathResource("GeoLite2-City.mmdb").getFile();
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public GeoIP getLocation(String ip) throws IOException, GeoIp2Exception{
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        String cityName = response.getCity().getName();
        String latitude = response.getLocation().getLatitude().toString();
        String longitude = response.getLocation().getLongitude().toString();
        return new GeoIP(ip,cityName,latitude,longitude);
    }
}
