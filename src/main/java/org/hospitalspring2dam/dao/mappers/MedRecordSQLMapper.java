package org.hospitalspring2dam.dao.mappers;


import org.hospitalspring2dam.dao.model.MedRecord;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MedRecordSQLMapper implements RowMapper {



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

    @Override
    public @Nullable Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        MedRecord record=new MedRecord();
        record.setId(rs.getInt("record_id"));
        record.setPatientID(rs.getInt("patient_id"));
        record.setDoctorID(rs.getInt("doctor_id"));
        record.setDiagnosis(rs.getString("diagnosis"));
        record.setAdmission_date(rs.getDate("admission_date").toLocalDate());
        return record;
    }
}