package by.emred.ws.grpcserver;

import by.emred.ws.grpcproto.HealthRequest;
import by.emred.ws.grpcproto.HealthResponse;
import by.emred.ws.grpcproto.HealthServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class HealthService extends HealthServiceGrpc.HealthServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(HealthService.class);

    @Override
    public void health(HealthRequest request, StreamObserver<HealthResponse> responseObserver) {
        final HealthResponse setResponseMessage = HealthResponse.newBuilder()
                .setResponseMessage("My name is " + request.getClientName() + " !!!. I am health")
                .build();

        logger.info("Client health check started: Send a message to the server: {}, {}", request.getClientName(),
                request.getRequestMessage());

        responseObserver.onNext(setResponseMessage);
        responseObserver.onCompleted();
    }
}
