package org.hospitalspring2dam.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class PatientDTO {

        private int id;
        private String name;
        private LocalDate birthDate;
        private String phone;
        private int paid;
        private CredentialDTO credentials;



    public PatientDTO(int id, String name, LocalDate birthDate, String phone, int paid, CredentialDTO credentials) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.paid = paid;
        this.credentials = credentials;
    }


}

