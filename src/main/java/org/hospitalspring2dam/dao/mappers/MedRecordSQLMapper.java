package org.hospitalspring2dam.dao.mappers;


import org.hospitalspring2dam.dao.model.MedRecord;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MedRecordSQLMapper {

    public List<MedRecord> mapMedRecord(ResultSet rsRecords){
        List<MedRecord> records = new ArrayList<>();
        try {
            while (rsRecords.next()) {
                MedRecord record=new MedRecord();
                record.setId(rsRecords.getInt("record_id"));
                record.setPatientID(rsRecords.getInt("patient_id"));
                record.setDoctorID(rsRecords.getInt("doctor_id"));
                record.setDiagnosis(rsRecords.getString("diagnosis"));
                record.setAdmission_date(rsRecords.getDate("admission_date").toLocalDate());
                records.add(record);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedRecordSQLMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public List<String> mapMeds(ResultSet rsMeds){
        List<String> meds = new ArrayList<>();
        try {
            while (rsMeds.next()) {
                meds.add(rsMeds.getString("medication_name"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MedRecordSQLMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meds;
    }
}