package com.delivery.core.application.queries;

import lombok.Data;

import java.util.UUID;

@Data
public class GetAllCouriersResponse {

    private UUID id;

    private String name;

    private LocationCourierResponse location;

    @Data
    public static class LocationCourierResponse {

        private Integer x;
        private Integer y;
    }
}
