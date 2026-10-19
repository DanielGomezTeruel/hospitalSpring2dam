package org.hospitalspring2dam.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialDTO {
    private String username;
    private String password;
}
