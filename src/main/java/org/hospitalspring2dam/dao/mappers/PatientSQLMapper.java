package org.hospitalspring2dam.dao.mappers;


import org.hospitalspring2dam.dao.model.Credentials;
import org.hospitalspring2dam.dao.model.Patient;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;



import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PatientSQLMapper implements RowMapper<Patient> {
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("patient_id")); // also with indexes: rs.getInt(1)
        patient.setName(rs.getString("name")); //also with indexes: rs.getString(2)
        patient.setBirthDate(rs.getDate("date_of_birth").toLocalDate());
        patient.setPhone(rs.getString("phone"));
        patient.setCredential(new Credentials(rs.getInt("patient_id"),null, null));
        return patient;
    }
}
