package org.hospitalspring2dam.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
        private int id;
        private String name;
        private LocalDate birthDate;
        private String phone;
        private Credentials credential;
}

