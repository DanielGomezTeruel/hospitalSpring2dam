package org.hospitalspring2dam.dao.repositories;

import org.hospitalspring2dam.dao.PaymentRepository;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class SpringPaymentRepository implements PaymentRepository {

    private final JdbcClient jdbcClient;

    public SpringPaymentRepository(JdbcClient jdbcClient) {

        this.jdbcClient = jdbcClient;
    }

    @Override
    public void deleteByPatient(int idDelete) {
        jdbcClient.sql(SQLQueries.DELETE_patient_payments)
                .param("patient_id", idDelete)
                .update();
    }
}
