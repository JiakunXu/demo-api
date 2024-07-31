package com.example.demo.google.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.google.api.GeocodingService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeocodingServiceImpl implements GeocodingService {

    private static final Logger logger = LoggerFactory.getLogger(GeocodingServiceImpl.class);

    @Value("${google.maps.base.url}")
    private String              baseUrl;

    @Value("${google.maps.api.key}")
    private String              apiKey;

    @Override
    public String geocode(String address) {
        GeoApiContext context = new GeoApiContext.Builder().baseUrlOverride(baseUrl).apiKey(apiKey)
            .build();

        GeocodingApiRequest request = GeocodingApi.geocode(context, address);

        try {
            GeocodingResult[] results = request.await();
            return JSON.toJSONString(results[0].geometry.location);
        } catch (ApiException | InterruptedException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            context.shutdown();
        }
    }

    @Override
    public String reverseGeocode(double lat, double lng) {
        GeoApiContext context = new GeoApiContext.Builder().baseUrlOverride(baseUrl).apiKey(apiKey)
            .build();

        GeocodingApiRequest request = GeocodingApi.reverseGeocode(context, new LatLng(lat, lng));

        try {
            GeocodingResult[] results = request.await();
            return results[0].formattedAddress;
        } catch (ApiException | InterruptedException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            context.shutdown();
        }
    }

}
