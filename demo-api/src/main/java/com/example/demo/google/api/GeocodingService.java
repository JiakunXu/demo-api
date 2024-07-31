package com.example.demo.google.api;

/**
 * @author JiakunXu
 */
public interface GeocodingService {

    String geocode(String address);

    String reverseGeocode(double lat, double lng);

}
