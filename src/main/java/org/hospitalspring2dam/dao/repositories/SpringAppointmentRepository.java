package org.hospitalspring2dam.dao.repositories;

import org.hospitalspring2dam.dao.AppointmentRepository;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class SpringAppointmentRepository implements AppointmentRepository {


    private final JdbcClient jdbcClient;

    public SpringAppointmentRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void deleteByPatient(int idDelete) {
        jdbcClient.sql(SQLQueries.DELETE_patient_appointments)
                .param("patient_id", idDelete)
                .update();
    }
}
