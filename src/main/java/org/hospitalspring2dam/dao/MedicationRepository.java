package org.hospitalspring2dam.dao;


import org.hospitalspring2dam.dao.model.Medication;

import java.util.List;

public interface MedicationRepository {
    List<Medication> getAllByMedRecord(int record_id);

    int add(Medication medication);

    void update(Medication medication);

    void deleteByMedRecord(int idDelete);

    void deleteByPatient(int idDelete);
}
