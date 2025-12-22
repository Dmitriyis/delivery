package com.delivery.adapters.out.grpc;

import clients.geo.GeoProto;
import com.delivery.core.domain.model.karnel.Location;
import com.delivery.core.ports.GeoClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class GeoClientImpl implements GeoClient {

    private final Environment env;
    private final ManagedChannel channel;
    private final clients.geo.GeoGrpc.GeoBlockingStub stub;

    public GeoClientImpl(Environment env) {
        this.env = env;
        this.channel = ManagedChannelBuilder
                .forAddress(
                        env.getProperty("app.grpc.geo-service.host"),
                        Integer.parseInt(env.getProperty("app.grpc.geo-service.port")))
                .usePlaintext()
                .build();
        this.stub = clients.geo.GeoGrpc.newBlockingStub(channel);
    }


    @Override
    public Location getGeolocation(String street) {
        GeoProto.GetGeolocationRequest request = GeoProto.GetGeolocationRequest.newBuilder().setStreet(street).build();

        GeoProto.GetGeolocationReply response = stub.getGeolocation(request);

        GeoProto.Location location = response.getLocation();

        return new Location(location.getX(), location.getY());
    }
}
