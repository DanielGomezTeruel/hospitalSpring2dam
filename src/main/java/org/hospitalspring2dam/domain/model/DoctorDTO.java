package org.hospitalspring2dam.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
public class DoctorDTO {
    private int id;
    private String name;
    private CredentialDTO credentials;
}
