package org.FriendsManagement.model;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Setter
public class ResponseObject {
    private String status;
    private String message;
    private List<Mono<User>> data;

    public ResponseObject(String status, String message, List<Mono<User>> object) {
        this.status = status;
        this.message = message;
        this.data = object;
    }
}
