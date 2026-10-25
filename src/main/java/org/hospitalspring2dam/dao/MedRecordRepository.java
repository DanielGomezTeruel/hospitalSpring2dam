package org.hospitalspring2dam.dao;



import org.hospitalspring2dam.dao.model.MedRecord;

import java.util.List;

public interface MedRecordRepository {
    List<MedRecord> getAllbyPatientId(int idPatient);

    int add(MedRecord medRecord);

    void update(MedRecord medRecordDatabase);

    void delete(int idDelete);

    void deleteByPatient(int idDelete);
}
