package org.FriendsManagement.handler;

import org.FriendsManagement.model.ResponseObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {
    public Mono<ServerResponse> findCustomerById(ServerRequest request) {
        String id = request.queryParam("data").orElse(null);
        if (id == null) {
            ResponseObject responseObject = new ResponseObject();
            responseObject.setStatus(204);
            responseObject.setError("No id provided");
            return preparedResponse(responseObject);
        }
        return null;
    }

    public Mono<ServerResponse> preparedResponse(ResponseObject responseObject) {
        return ServerResponse
                .status(responseObject.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(responseObject);
    }
}
