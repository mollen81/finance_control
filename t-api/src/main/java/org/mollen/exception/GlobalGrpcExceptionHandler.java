package org.mollen.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tinkoff.piapi.core.exception.ApiRuntimeException;

@GrpcAdvice
public class GlobalGrpcExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalGrpcExceptionHandler.class);

    @GrpcExceptionHandler(ApiRuntimeException.class)
    public StatusRuntimeException handleApiRuntimeException(ApiRuntimeException e) {
        String code = e.getCode();
        String message = e.getMessage();

        log.error("T-Invest API error occurred. Code: {}, Message: {}", code, message, e);

        Status status = switch (code) {
            case "30002", "30039", "INSTRUMENT_NOT_FOUND" ->
                Status.NOT_FOUND.withDescription("Instrument not found in T-Invest base");
            case "40001", "40002", "UNAUTHENTICATED", "INSTRUMENT_PRIVILEGES" ->
                Status.UNAUTHENTICATED.withDescription("T-Invest API authentication error. Check the token: " + message);
            case "40029", "RESOURCE_EXHAUSTED" ->
                Status.RESOURCE_EXHAUSTED.withDescription("Rate limit hit T-Invest API: " + message);
            case "40011", "INVALID_ARGUMENT" ->
                Status.INVALID_ARGUMENT.withDescription("T-Invest API incorrect query parameters: " + message);
            default ->
                Status.FAILED_PRECONDITION.withDescription("T-Invest API error [" + code + "]: " + message);
        };

        return status.withCause(e).asRuntimeException();
    }


    @GrpcExceptionHandler(NullPointerException.class)
    public StatusRuntimeException handleNullPointerException(NullPointerException e) {
        log.error("Null pointer exception handled", e);

        return Status.NOT_FOUND
                .withDescription("The requested object or its dependency not found (NPE)")
                .withCause(e)
                .asRuntimeException();
    }


    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleAllExceptions(Exception e) {
        log.error("Unexpected error occurred in gRPC service", e);

        return Status.INTERNAL
                .withDescription("Internal service error: " + e.getMessage())
                .withCause(e)
                .asRuntimeException();
    }
}
