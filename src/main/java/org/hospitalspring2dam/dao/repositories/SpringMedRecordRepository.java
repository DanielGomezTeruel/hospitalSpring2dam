package org.hospitalspring2dam.dao.repositories;



import org.hospitalspring2dam.dao.MedRecordRepository;
import org.hospitalspring2dam.dao.MedicationRepository;
import org.hospitalspring2dam.dao.common.DBConnection;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.MedRecordSQLMapper;
import org.hospitalspring2dam.dao.model.MedRecord;
import org.hospitalspring2dam.domain.exceptions.RecordBException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class SpringMedRecordRepository implements MedRecordRepository {

    private final DBConnection db;
    private final MedRecordSQLMapper medRecordSQLMapper;
    private final MedicationRepository medicationRepository;
    private final JdbcClient jdbcClient;



    public SpringMedRecordRepository(DBConnection db, MedRecordSQLMapper medRecordSQLMapper, MedicationRepository medicationRepository, JdbcClient jdbcClient) {
        this.db = db;
        this.medRecordSQLMapper = medRecordSQLMapper;
        this.medicationRepository = medicationRepository;
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<MedRecord> getAllbyPatientId(int idPatient) {
        List<MedRecord> records=null;
        try(Connection con = db.getConnection()) {
            try (PreparedStatement getMedRecords = con.prepareStatement(SQLQueries.SELECT_getMedRecordsById_QUERY);
                 PreparedStatement getMeds = con.prepareStatement(SQLQueries.SELECT_getMedicationsByMedRecord_QUERY)) {


                getMedRecords.setInt(1, idPatient);
//                records = medRecordSQLMapper.mapMeds(getMedRecords.executeQuery());


                records.forEach(record->
                {
                    try {
                        getMeds.setInt(1, record.getId());
                        record.setMedications(medRecordSQLMapper.mapMeds(getMeds.executeQuery()));

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

            } catch (SQLException e) {
                Logger.getLogger(SpringMedRecordRepository.class.getName()).log(Level.SEVERE, null, e);
                throw new RecordBException();

            }
            finally {
                con.setAutoCommit(true);

            }
        }catch(SQLException e){
            Logger.getLogger(SpringMedRecordRepository.class.getName()).log(Level.SEVERE, null, e);
            throw new RecordBException();

        }
        return records;
    }

    @Override
    public int add(MedRecord medRecord) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(SQLQueries.INSERT_medRecord)
                .param("patient_id",medRecord.getPatientID())
                .param("doctor_id", medRecord.getDoctorID())
                .param("diagnosis", medRecord.getDiagnosis())
                .param("admission_date", medRecord.getAdmission_date())
                .update(keyHolder);
        int id = (int) keyHolder.getKey();

        return id;
    }

    @Override
    public void update(MedRecord medRecord) {
        jdbcClient.sql(SQLQueries.UPDATE_MEDRECORD)
                .param("doctor_id", medRecord.getDoctorID())
                .param("patient_id", medRecord.getPatientID())
                .param("diagnosis", medRecord.getDiagnosis())
                .param("admission_date", medRecord.getAdmission_date())
                .param("record_id", medRecord.getId())
                .update();
    }

    @Override
    @Transactional
    public void delete(int idDelete) {
        medicationRepository.deleteByMedRecord(idDelete);
        jdbcClient.sql(SQLQueries.DELETE_medRecord)
                .param("record_id", idDelete)
                .update();
    }

    @Override
    @Transactional
    public void deleteByPatient(int idDelete){
        medicationRepository.deleteByPatient(idDelete);
        jdbcClient.sql(SQLQueries.DELETE_patient_medRecords)
                .param("record_id", idDelete)
                .update();
    }
}
