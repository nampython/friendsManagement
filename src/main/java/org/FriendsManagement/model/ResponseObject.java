package org.FriendsManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseObject {
    private String timestamp = String.valueOf(System.currentTimeMillis());
    private int status;
    private String error;
    private String warnings;
    private String path;
    private Object data;
}
