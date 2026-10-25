package org.hospitalspring2dam.dao.repositories;


import org.hospitalspring2dam.dao.*;
import org.hospitalspring2dam.dao.common.DBConnection;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.PatientSQLMapper;
import org.hospitalspring2dam.dao.model.Patient;
import org.hospitalspring2dam.domain.exceptions.DuplicatedUsernameException;
import org.hospitalspring2dam.domain.exceptions.ForeignKeyException;
import org.hospitalspring2dam.domain.exceptions.PatientDBException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class SpringPatientRepository implements PatientRepository {
    List<Patient> patients = new ArrayList<>();

    private final DBConnection db;
    private final PatientSQLMapper patientSQLMapper;
    private final JdbcClient jdbcClient;
    private final CredentialRepository credentialRepository;
    private final MedRecordRepository medRecordRepository;
    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;



    public SpringPatientRepository(DBConnection db, PatientSQLMapper patientSQLMapper, JdbcClient jdbcClient, CredentialRepository credentialRepository, MedRecordRepository medRecordRepository, PaymentRepository paymentRepository, AppointmentRepository appointmentRepository) {
        this.db = db;
        this.patientSQLMapper = patientSQLMapper;
        this.jdbcClient = jdbcClient;
        this.credentialRepository = credentialRepository;
        this.medRecordRepository = medRecordRepository;
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Patient> getAll() {
        return jdbcClient
                .sql(SQLQueries.SELECT_patients_QUERY)
                .query(patientSQLMapper).list();
    }

    @Override
    public int add(Patient patient){
        int id = 0;
        try (Connection con = db.getConnection()){
            try(PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.INSERT_patient, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.INSERT_patient_credentials)) {

                con.setAutoCommit(false);

                preparedStatement1.setString(1, patient.getName());
                preparedStatement1.setDate(2, Date.valueOf(patient.getBirthDate()));
                preparedStatement1.setString(3, patient.getPhone());

                int rowsAffected = preparedStatement1.executeUpdate();
                if(rowsAffected>0){
                    ResultSet rs = preparedStatement1.getGeneratedKeys();
                    if(rs.next()){
                        id=rs.getInt(1);
                        preparedStatement2.setString(1, patient.getCredential().getUsername());
                        preparedStatement2.setString(2, patient.getCredential().getPassword());
                        preparedStatement2.setInt(3, id);
                        preparedStatement2.executeUpdate();
                        con.commit();
                    }
                }else{
                    throw new DuplicatedUsernameException();
                }


            }catch(SQLException sqle) {
                Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
                try {
                    con.rollback();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }finally{
                con.setAutoCommit(true);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new PatientDBException();
        }
        return id;
    }

    @Override
    public void update(Patient patient) {
        jdbcClient.sql(SQLQueries.UPDATE_PATIENT)
                .param("name", patient.getName())
                .param("date_of_birth", Date.valueOf(patient.getBirthDate()))
                .param("phone", patient.getPhone())
                .param("patient_id", patient.getId())
                .update();
    }

    @Override
    @Transactional
    public void delete(int idDelete, boolean answer) {
        paymentRepository.deleteByPatient(idDelete);
        appointmentRepository.deleteByPatient(idDelete);
        credentialRepository.deleteByPatient(idDelete);
        if(answer){
            medRecordRepository.deleteByPatient(idDelete);
        }
        try {
            jdbcClient.sql(SQLQueries.DELETE_patient).param("patient_id", idDelete);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyException("Patient deletion incomplete");
        }
    }

    public Patient get(int id){
        return getAll().stream().filter(patient -> patient.getId()==id).findFirst().orElse(null);
    }
}
