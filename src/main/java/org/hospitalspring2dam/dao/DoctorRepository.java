package org.hospitalspring2dam.dao;



import org.hospitalspring2dam.dao.model.Doctor;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> getAll();

    int add(Doctor doctor);

    void update(Doctor doctorDatabase);

    void delete(int idDelete, boolean answer);
}
