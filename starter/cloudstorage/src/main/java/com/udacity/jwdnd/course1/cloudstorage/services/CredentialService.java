package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    @Autowired
    private CredentialsMapper mapper;
    @Autowired
    private EncryptionService encryptionService;

    public void createCredential(Credential credential) {
        String encodedSalt = encryptionService.generateKey();
        credential.setKey(encodedSalt);
        String encryptedPassword = encryptionService.encryptValue(credential.getDecodedPassword(), encodedSalt);
        credential.setPassword(encryptedPassword);

        this.mapper.insert(credential);
    }

    public void updateCredential(Credential credential) {
        String encodedSalt = encryptionService.generateKey();
        credential.setKey(encodedSalt);
        credential.setPassword(encryptionService.encryptValue(credential.getDecodedPassword(), encodedSalt));

        this.mapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId) {
        this.mapper.deleteCredential(credentialId);
    }

    public List<Credential> getUserCredentials(int userId) {
        List<Credential> credentialList = this.mapper.getCredentials(userId);
        return credentialList.stream()
                .map(this::wrapCredential)
                .collect(Collectors.toList());
    }

    private Credential wrapCredential(Credential c) {
        return Credential.builder()
                .credentialId(c.getCredentialId())
                .url(c.getUrl())
                .username(c.getUsername())
                .password(c.getPassword())
                .userId(c.getUserId())
                .decodedPassword(c.getDecodedPassword())
                .build();
    }

    private String getDecodedPassword(Credential credential) {
        return this.encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public Credential getCredentialById(int credentialId) {
        return wrapCredential(this.mapper.getCredentialById(credentialId));
    }
}
