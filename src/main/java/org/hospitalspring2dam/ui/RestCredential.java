package org.hospitalspring2dam.ui;


import org.hospitalspring2dam.domain.model.CredentialDTO;
import org.hospitalspring2dam.domain.service.CredentialService;
import org.springframework.web.bind.annotation.*;


@RestController
public class RestCredential {

    private final CredentialService credentialService;

    public RestCredential(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    public boolean login(@RequestBody CredentialDTO userCredentialsUI) {
        return credentialService.login(userCredentialsUI);
    }
}