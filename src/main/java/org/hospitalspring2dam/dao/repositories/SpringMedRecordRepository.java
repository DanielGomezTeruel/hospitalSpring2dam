package org.hospitalspring2dam.dao.repositories;



import org.hospitalspring2dam.dao.MedRecordRepository;
import org.hospitalspring2dam.dao.common.DBConnection;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.MedRecordSQLMapper;
import org.hospitalspring2dam.dao.model.MedRecord;
import org.hospitalspring2dam.domain.exceptions.RecordBException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class SpringMedRecordRepository implements MedRecordRepository {

    private final DBConnection db;
    private final MedRecordSQLMapper medRecordSQLMapper;



    public SpringMedRecordRepository(DBConnection db, MedRecordSQLMapper medRecordSQLMapper) {
        this.db = db;
        this.medRecordSQLMapper = medRecordSQLMapper;
    }

    @Override
    public List<MedRecord> getAllbyPatientId(int idPatient) {
        List<MedRecord> records=null;
        try(Connection con = db.getConnection()) {
            try (PreparedStatement getMedRecords = con.prepareStatement(SQLQueries.SELECT_getMedRecordsById_QUERY);
                 PreparedStatement getMeds = con.prepareStatement(SQLQueries.SELECT_getMedicationsByMedRecord_QUERY)) {


                getMedRecords.setInt(1, idPatient);
                records = medRecordSQLMapper.mapMedRecord(getMedRecords.executeQuery());


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
        int id = 0;
        try (Connection con = db.getConnection()){
            try(PreparedStatement preparedStatement1 = con.prepareStatement(SQLQueries.INSERT_medRecord, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement preparedStatement2 = con.prepareStatement(SQLQueries.INSERT_medications)) {

                con.setAutoCommit(false);

                preparedStatement1.setInt(1, medRecord.getPatientID());
                preparedStatement1.setInt(2, medRecord.getDoctorID());
                preparedStatement1.setString(3, medRecord.getDiagnosis());
                preparedStatement1.setDate(4, Date.valueOf(medRecord.getAdmission_date()));

                int rowsAffected = preparedStatement1.executeUpdate();
                if(rowsAffected>0){
                    ResultSet rs = preparedStatement1.getGeneratedKeys();
                    if(rs.next()){
                        id=rs.getInt(1);
                        for (int i = 0; i < medRecord.getMedications().size(); i++) {
                            preparedStatement2.setInt(1, id);
                            preparedStatement2.setString(2, medRecord.getMedications().get(i));
                            preparedStatement2.executeUpdate();
                        }
                        con.commit();
                    }
                }


            }catch(SQLException sqle) {
                Logger.getLogger(SpringMedRecordRepository.class.getName()).log(Level.SEVERE, null, sqle);
                con.rollback();

            }finally{
                con.setAutoCommit(true);

            }
        }catch (SQLException ex) {
            Logger.getLogger(SpringMedRecordRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RecordBException();

        }
        return id;
    }

    @Override
    public void update(MedRecord medRecord) {
        int id = 0;
        try (Connection con = db.getConnection()){
            try(PreparedStatement updateMedRecord = con.prepareStatement(SQLQueries.UPDATE_MEDRECORD, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement deleteMeds = con.prepareStatement(SQLQueries.DELETE_medRecord_medications);
                PreparedStatement insertMeds = con.prepareStatement(SQLQueries.INSERT_medications)) {

                con.setAutoCommit(false);

                updateMedRecord.setInt(1, medRecord.getPatientID());
                updateMedRecord.setInt(2, medRecord.getDoctorID());
                updateMedRecord.setString(3, medRecord.getDiagnosis());
                updateMedRecord.setDate(4, Date.valueOf(medRecord.getAdmission_date()));
                updateMedRecord.setInt(5, medRecord.getId());

                int rowsAffected = updateMedRecord.executeUpdate();
                if(rowsAffected>0){
                    ResultSet rs = updateMedRecord.getGeneratedKeys();
                    if(rs.next()){
                        id=rs.getInt(1);
                        deleteMeds.setInt(1,id);
                        for (int i = 0; i < medRecord.getMedications().size(); i++) {
                            insertMeds.setInt(1, id);
                            insertMeds.setString(2, medRecord.getMedications().get(i));
                            insertMeds.executeUpdate();
                        }
                        con.commit();
                    }
                }

            }catch(SQLException sqle) {
                Logger.getLogger(SpringMedRecordRepository.class.getName()).log(Level.SEVERE, null, sqle);
                con.rollback();

            }finally{
                con.setAutoCommit(true);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RecordBException();
        }
    }

    @Override
    public void delete(int idDelete) {
        try (Connection con = db.getConnection()){
            con.setAutoCommit(false);
            try(PreparedStatement deleteMeds = con.prepareStatement(SQLQueries.DELETE_medRecord_medications);
                PreparedStatement deleteRecord = con.prepareStatement(SQLQueries.DELETE_medRecord);
            ){
                deleteMeds.setInt(1, idDelete);
                deleteMeds.executeUpdate();

                deleteRecord.setInt(1, idDelete);
                deleteRecord.executeUpdate();

                con.commit();
            } catch (SQLException ex) {
                Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
                con.rollback();
                throw new RecordBException();
            }finally {
                con.setAutoCommit(true);
            }
        }catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RecordBException();
        }
    }
}
