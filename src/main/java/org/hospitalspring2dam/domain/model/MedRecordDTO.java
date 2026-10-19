package org.hospitalspring2dam.domain.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedRecordDTO {

        private int id;
        private String description;
        private String date;
        private int idPatient;
        private int idDoctor;
        private List<String> medications;

    }

