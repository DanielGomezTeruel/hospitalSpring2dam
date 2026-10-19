package org.hospitalspring2dam.dao;

import org.hospitalspring2dam.dao.model.Patient;

import java.util.List;

public interface PatientRepository {
    List<Patient> getAll();

    int add(Patient patient);

    void update(Patient patientDatabase);

    void delete(int idDelete, boolean answer);
}
