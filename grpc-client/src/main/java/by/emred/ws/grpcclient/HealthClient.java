package by.emred.ws.grpcclient;

import by.emred.ws.grpcproto.HealthRequest;
import by.emred.ws.grpcproto.HealthResponse;
import by.emred.ws.grpcproto.HealthServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class HealthClient {

    private static final Logger logger = LoggerFactory.getLogger(HealthClient.class);

    @GrpcClient(value = "grpc-server-test")
    private HealthServiceGrpc.HealthServiceBlockingStub healthServiceBlockingStub;

    public void ping() {
        final HealthRequest request = HealthRequest.newBuilder()
                .setClientName("Dersu")
                .setRequestMessage("I am healthy")
                .build();

        final HealthResponse health = healthServiceBlockingStub.health(request);
        logger.info("Server sent a response: {}", health.getResponseMessage());
    }
}
