package com.delivery.core.ports;

import com.delivery.core.domain.model.karnel.Location;

public interface GeoClient {
    public Location getGeolocation(String street);
}
