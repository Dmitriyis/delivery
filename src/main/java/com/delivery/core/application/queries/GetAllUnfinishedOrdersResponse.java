package com.delivery.core.application.queries;

import lombok.Data;

import java.util.UUID;

@Data
public class GetAllUnfinishedOrdersResponse {
    private UUID id;

    private Integer volume;

    private String orderStatus;

    private UUID courierId;

    private LocationOrderResponse location;

    @Data
    public static class LocationOrderResponse {
        private Integer x;
        private Integer y;
    }
}
