package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credential {
    private int credentialId;
    private String url;
    private String username;
    private String password;
    private String decodedPassword;
    private String key;
    private int userId;
}
