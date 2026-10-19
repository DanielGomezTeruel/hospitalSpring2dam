package org.hospitalspring2dam.dao.mappers;


import org.hospitalspring2dam.dao.model.Credentials;
import org.hospitalspring2dam.dao.model.Doctor;
import org.hospitalspring2dam.dao.repositories.SpringPatientRepository;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DoctorSQLMapper {

    public List<Doctor> mapDoctor(ResultSet rs){
        List<Doctor> doctors = new ArrayList<>();
        try {
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("doctor_id")); // also with indexes: rs.getInt(1)
                doctor.setName(rs.getString("name")); //also with indexes: rs.getString(2)
                doctor.setSpecialization(rs.getString("specialization"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setCredentials(new Credentials(rs.getInt("doctor_id"),null,null));
                doctors.add(doctor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doctors;
    }
}
