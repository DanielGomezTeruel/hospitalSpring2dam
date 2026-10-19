package org.hospitalspring2dam.dao.repositories;



import org.hospitalspring2dam.dao.DoctorRepository;
import org.hospitalspring2dam.dao.common.DBConnection;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.DoctorSQLMapper;
import org.hospitalspring2dam.dao.model.Doctor;
import org.hospitalspring2dam.domain.exceptions.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class SpringDoctorRepository implements DoctorRepository {
    List<Doctor> doctors = new ArrayList<>();

    private final DBConnection db;
    private final DoctorSQLMapper doctorSQLMapper;


    public SpringDoctorRepository(DBConnection db, DoctorSQLMapper doctorSQLMapper) {
        this.db = db;
        this.doctorSQLMapper = doctorSQLMapper;
    }

    @Override
    public List<Doctor> getAll() {
        try (Connection con = db.getConnection();
             Statement statement = con.createStatement()) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_doctors_QUERY);
            doctors=doctorSQLMapper.mapDoctor(rs);

        } catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException("Error in doctor database");
        }
        return doctors;
    }

    @Override
    public int add(Doctor doctor) {
        int id = 0;
        try (Connection con = db.getConnection()){
            try(PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.INSERT_doctor, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.INSERT_doctor_credentials)) {

                con.setAutoCommit(false);

                preparedStatement1.setString(1, doctor.getName());
                preparedStatement1.setString(2, doctor.getSpecialization());
                preparedStatement1.setString(3, doctor.getPhone());

                int rowsAffected = preparedStatement1.executeUpdate();
                if(rowsAffected>0){
                    ResultSet rs = preparedStatement1.getGeneratedKeys();
                    if(rs.next()){
                        id=rs.getInt(1);
                        preparedStatement2.setString(1, doctor.getCredentials().getUsername());
                        preparedStatement2.setString(2, doctor.getCredentials().getPassword());
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
    public void update(Doctor doctor) {
        int id = 0;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.UPDATE_DOCTOR, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.UPDATE_DOCTOR_CREDENTIALS)) {

                con.setAutoCommit(false);

                preparedStatement1.setString(1, doctor.getName());
                preparedStatement1.setString(2, doctor.getSpecialization());
                preparedStatement1.setString(3, doctor.getPhone());

                int rowsAffected = preparedStatement1.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet rs = preparedStatement1.getGeneratedKeys();
                    if (rs.next()) {
                        id = rs.getInt(1);
                        preparedStatement2.setString(1, doctor.getCredentials().getUsername());
                        preparedStatement2.setString(2, doctor.getCredentials().getPassword());
                        preparedStatement2.setInt(3, id);
                        preparedStatement2.executeUpdate();
                        con.commit();
                    }
                } else {
                    throw new DatabaseException("User doesn't exist");
                }

            } catch (SQLException sqle) {
                Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
                try {
                    con.rollback();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new DoctorDBException();
        }
    }

    @Override
    public void delete(int idDelete, boolean answer) {
        try (Connection con = db.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.DELETE_doctor_cred);
                 PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.DELETE_doctor_appointments);
                 PreparedStatement preparedStatement3 = con.prepareStatement(SQLQueries.DELETE_doctor_medications);
                 PreparedStatement preparedStatement4 = con.prepareStatement(SQLQueries.DELETE_doctor_medRecords);
                 PreparedStatement preparedStatement5 = con.prepareStatement(SQLQueries.DELETE_doctor)) {

                preparedStatement1.setInt(1, idDelete);
                preparedStatement2.setInt(1, idDelete);
                preparedStatement5.setInt(1, idDelete);
                if(!answer) {
                    preparedStatement3.setInt(1, idDelete);
                    preparedStatement4.setInt(1, idDelete);

                    preparedStatement3.executeQuery();
                    preparedStatement4.executeQuery();
                }


                preparedStatement1.executeQuery();
                preparedStatement2.executeQuery();
                preparedStatement5.executeQuery();


                con.commit();

            } catch (SQLIntegrityConstraintViolationException e) {

                con.rollback();
                throw new ForeignKeyException("Doctor deletion incomplete");

            }finally{
                con.setAutoCommit(true);
            }

        }catch(SQLException sqle) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
}
