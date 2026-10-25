package org.hospitalspring2dam.dao.common;

public class SQLQueries {

    public static final String SELECT_patients_QUERY = "select * from patients";
    public static final String SELECT_doctors_QUERY = "select * from doctors";
    public static final String SELECT_getCredentials_QUERY = "select * from user_login where username= ?";
    public static final String SELECT_getMedRecordsById_QUERY = "select * from medical_records where patient_id= ?";
    public static final String SELECT_getMedicationsByMedRecord_QUERY = "select * from prescribed_medications where record_id= ?";

    public static final String INSERT_patient = "INSERT INTO patients (patient_id, name, date_of_birth, phone) VALUES (null, ?, ?, ?)";
    public static final String INSERT_doctor = "INSERT INTO doctors (doctor_id, name, specialization, phone) VALUES (null, ?, ?, ?)";
    public static final String INSERT_patient_credentials = "INSERT INTO user_login (username, password, patient_id) VALUES (?, ?, ?)";
    public static final String INSERT_doctor_credentials = "INSERT INTO user_login (username, password, doctor_id) VALUES (?, ?, ?)";
    public static final String INSERT_medRecord = "INSERT INTO medical_records (record_id, patient_id, doctor_id, diagnosis, admission_date) VALUES (null, :patient_id, :doctor_id, :diagnosis, :admission_date)";
    public static final String INSERT_medications = "INSERT INTO prescribed_medications (prescription_id, record_id, medication_name) VALUES (null, ?, ?)";



    public static final String DELETE_patient = "delete from patients where patient_id = :patient_id";
    public static final String DELETE_patient_cred = "delete from user_login where patient_id = :patient_id";
    public static final String DELETE_patient_medRecords = "delete from medical_records where patient_id = ?";
    public static final String DELETE_patient_medications = "delete from prescribed_medications where record_id in (select record_id from medical_records where patient_id = :id)";
    public static final String DELETE_patient_appointments = "delete from appointments where patient_id = :patient_id";
    public static final String DELETE_patient_payments = "delete from patient_payments where patient_id = :patient_id";

    public static final String DELETE_doctor = "delete from doctors where doctor_id = ?";
    public static final String DELETE_doctor_cred = "delete from user_login where doctor_id = ?";
    public static final String DELETE_doctor_medRecords = "delete from medical_records where doctor_id = ?";
    public static final String DELETE_doctor_medications = "delete from prescribed_medications where record_id in (select doctor_id from medical_records where patient_id = ?)";
    public static final String DELETE_doctor_appointments = "delete from appointments where doctor_id = ?";

    public static final String DELETE_medRecord_medications = "delete from prescribed_medications where record_id = ?";
    public static final String DELETE_medRecord = "delete from medical_records where record_id = :record_id";


    public static final String UPDATE_PATIENT = "update patients set name = :name, date_of_birth = :date_of_birth, phone = :phone where patient_id = :patient_id";
    public static final String UPDATE_PATIENT_CREDENTIALS = "update user_login set username = ?, password = ? where patient_id = ?";
    public static final String UPDATE_DOCTOR_CREDENTIALS = "update user_login set username = ?, password = ? where doctor_id = ?";
    public static final String UPDATE_MEDRECORD = "update medical_records set doctor_id = :doctor_id, patient_id = :patient_id, diagnosis = :diagnosis, admission_date = :admission_date where record_id = :record_id";
    public static final String UPDATE_DOCTOR = "update doctors set name = ?, specialization = ?, phone = ? where doctor_id = ?";

}
