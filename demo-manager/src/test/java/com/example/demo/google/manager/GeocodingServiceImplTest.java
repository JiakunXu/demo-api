package com.example.demo.google.manager;

import com.alibaba.fastjson2.JSON;
import com.google.maps.*;
import com.google.maps.model.*;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.IOException;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeocodingServiceImplTest {
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void mapsCoordinatesOrAddressAndShutsDown(boolean reverse) throws Exception {
        var context = mock(GeoApiContext.class);
        var request = mock(GeocodingApiRequest.class);
        GeocodingResult result = new GeocodingResult();
        result.geometry = new Geometry();
        result.geometry.location = new LatLng(31.2, 121.5);
        result.formattedAddress = "Shanghai";
        when(request.await()).thenReturn(new GeocodingResult[] {result});
        var service = new GeocodingServiceImpl();
        ReflectionTestUtils.setField(service, "baseUrl", "https://maps.example.com");
        ReflectionTestUtils.setField(service, "apiKey", "key");
        try (MockedConstruction<GeoApiContext.Builder> builders = mockConstruction(GeoApiContext.Builder.class, withSettings().defaultAnswer(RETURNS_SELF), (builder, creation) -> when(builder.build()).thenReturn(context));
             MockedStatic<GeocodingApi> api = mockStatic(GeocodingApi.class)) {
            api.when(() -> GeocodingApi.geocode(context, "Shanghai")).thenReturn(request);
            api.when(() -> GeocodingApi.reverseGeocode(eq(context), argThat(location -> location.lat == 31.2 && location.lng == 121.5))).thenReturn(request);
            if (reverse) assertThat(service.reverseGeocode(31.2, 121.5)).isEqualTo("Shanghai");
            else {
                var location = JSON.parseObject(service.geocode("Shanghai"));
                assertThat(location.getDoubleValue("lat")).isEqualTo(31.2);
                assertThat(location.getDoubleValue("lng")).isEqualTo(121.5);
            }
            verify(builders.constructed().getFirst()).apiKey("key");
            verify(builders.constructed().getFirst()).baseUrlOverride("https://maps.example.com");
            verify(context).shutdown();
            verify(request).await();
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void failedQueriesStillCloseContext(boolean reverse) throws Exception {
        var context = mock(GeoApiContext.class);
        var request = mock(GeocodingApiRequest.class);
        var failure = new IOException("network");
        when(request.await()).thenThrow(failure);
        try (MockedConstruction<GeoApiContext.Builder> builders = mockConstruction(GeoApiContext.Builder.class, withSettings().defaultAnswer(RETURNS_SELF), (builder, creation) -> when(builder.build()).thenReturn(context));
             MockedStatic<GeocodingApi> api = mockStatic(GeocodingApi.class)) {
            api.when(() -> GeocodingApi.geocode(context, "Shanghai")).thenReturn(request);
            api.when(() -> GeocodingApi.reverseGeocode(eq(context), any(LatLng.class))).thenReturn(request);
            assertThatThrownBy(() -> {
                if (reverse) new GeocodingServiceImpl().reverseGeocode(31.2, 121.5);
                else new GeocodingServiceImpl().geocode("Shanghai");
            }).hasCause(failure);
            verify(context).shutdown();
        }
    }
}
