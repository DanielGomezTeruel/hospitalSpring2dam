package org.hospitalspring2dam.dao.repositories;

import org.hospitalspring2dam.dao.MedicationRepository;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.MedicationsSQLMapper;
import org.hospitalspring2dam.dao.model.Medication;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpringMedicationRepository implements MedicationRepository {


    private final MedicationsSQLMapper medicationsSQLMapper;
    private final JdbcClient jdbcClient;



    public SpringMedicationRepository(MedicationsSQLMapper medicationsSQLMapper, JdbcClient jdbcClient) {

        this.medicationsSQLMapper = medicationsSQLMapper;
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Medication> getAllByMedRecord(int record_id) {
        return List.of();
    }

    @Override
    public int add(Medication medication) {
        return 0;
    }

    @Override
    public void update(Medication medication) {

    }

    @Override
    public void deleteByMedRecord(int idDelete) {

    }

    @Override
    public void deleteByPatient(int idDelete) {
        jdbcClient.sql(SQLQueries.DELETE_patient_medications)
                .param("id", idDelete)
                .update();
    }
}
