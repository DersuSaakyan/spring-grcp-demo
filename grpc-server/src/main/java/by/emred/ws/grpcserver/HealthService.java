package by.emred.ws.grpcserver;

import build.buf.protovalidate.ValidationResult;
import build.buf.protovalidate.Validator;
import build.buf.protovalidate.Violation;
import build.buf.protovalidate.exceptions.ValidationException;
import by.emred.ws.grpcproto.proto.library.HealthRequest;
import by.emred.ws.grpcproto.proto.library.HealthResponse;
import by.emred.ws.grpcproto.proto.library.HealthServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@GrpcService
public class HealthService extends HealthServiceGrpc.HealthServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(HealthService.class);

    @Override
    public void health(HealthRequest request, StreamObserver<HealthResponse> responseObserver) {
        final HealthResponse setResponseMessage = HealthResponse.newBuilder()
                .setResponseMessage("My name is " + request.getClientName() + " !!!. I am health")
                .build();

        Validator validator = new Validator();


        try {
            ValidationResult result = validator.validate(request);
            if (!CollectionUtils.isEmpty(result.getViolations())) {
                if (!result.isSuccess()) {
                    throw new RuntimeException();
                }
            }
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

        logger.info("Client health check started: Send a message to the server: {}, {}", request.getClientName(),
                request.getRequestMessage());

        responseObserver.onNext(setResponseMessage);
        responseObserver.onCompleted();
    }
}
