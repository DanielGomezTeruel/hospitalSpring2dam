package org.hospitalspring2dam.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedRecord {
    private int id;
    private int patientID;
    private int doctorID;
    private String diagnosis;
    private LocalDate admission_date;
    private List<String> medications;
}
