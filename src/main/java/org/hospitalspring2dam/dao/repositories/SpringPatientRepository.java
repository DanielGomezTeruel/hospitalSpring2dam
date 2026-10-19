package org.hospitalspring2dam.dao.repositories;


import org.hospitalspring2dam.dao.PatientRepository;
import org.hospitalspring2dam.dao.common.DBConnection;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.PatientSQLMapper;
import org.hospitalspring2dam.dao.model.Patient;
import org.hospitalspring2dam.domain.exceptions.DatabaseException;
import org.hospitalspring2dam.domain.exceptions.DuplicatedUsernameException;
import org.hospitalspring2dam.domain.exceptions.ForeignKeyException;
import org.hospitalspring2dam.domain.exceptions.PatientDBException;
import org.springframework.stereotype.Repository;

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



    public SpringPatientRepository(DBConnection db, PatientSQLMapper patientSQLMapper) {
        this.db = db;
        this.patientSQLMapper = patientSQLMapper;
    }

    @Override
    public List<Patient> getAll() {
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement()) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_patients_QUERY);
            patients=patientSQLMapper.mapPatient(rs);

        } catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new PatientDBException();
        }
        return patients;
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
        int id = 0;
        try (Connection con = db.getConnection()){
            try(PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.UPDATE_PATIENT, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.UPDATE_PATIENT_CREDENTIALS)) {

                con.setAutoCommit(false);

                preparedStatement1.setString(1, patient.getName());
                preparedStatement1.setDate(2, Date.valueOf(patient.getBirthDate()));
                preparedStatement1.setString(3, patient.getPhone());
                preparedStatement1.setInt(4, patient.getId());

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
                    throw new DatabaseException("User doesn't exist");
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
    }

    @Override
    public void delete(int idDelete, boolean answer) {
        try (Connection con = db.getConnection()) {

                con.setAutoCommit(false);

                try (PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.DELETE_patient_cred);
                     PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.DELETE_patient_appointments);
                     PreparedStatement preparedStatement3 = con.prepareStatement(SQLQueries.DELETE_patient_payments);
                     PreparedStatement preparedStatement4 = con.prepareStatement(SQLQueries.DELETE_patient_medications);
                     PreparedStatement preparedStatement5 = con.prepareStatement(SQLQueries.DELETE_patient_medRecords);
                     PreparedStatement preparedStatement6 = con.prepareStatement(SQLQueries.DELETE_patient)) {

                    preparedStatement1.setInt(1, idDelete);
                    preparedStatement2.setInt(1, idDelete);
                    preparedStatement3.setInt(1, idDelete);

                    if(!answer) {
                        preparedStatement4.setInt(1, idDelete);
                        preparedStatement5.setInt(1, idDelete);

                        preparedStatement4.executeQuery();
                        preparedStatement5.executeQuery();
                    }
                    preparedStatement6.setInt(1, idDelete);

                    preparedStatement1.executeQuery();
                    preparedStatement2.executeQuery();
                    preparedStatement3.executeQuery();
                    preparedStatement6.executeQuery();

                    con.commit();

                } catch (SQLIntegrityConstraintViolationException e) {
                    con.rollback();
                    throw new ForeignKeyException("Patient deletion incomplete");

                }finally{
                    con.setAutoCommit(true);
                }

        }catch(SQLException sqle) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    public Patient get(int id){
        return getAll().stream().filter(patient -> patient.getId()==id).findFirst().orElse(null);
    }
}
