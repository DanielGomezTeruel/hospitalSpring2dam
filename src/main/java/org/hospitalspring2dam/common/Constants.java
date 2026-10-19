package org.hospitalspring2dam.common;

public class Constants {

    public static final String DATABASE_ERROR = "Database Error";
    public static final String CREDENTIAL_DB_ERROR = "Credential DB Error";
    public static final String PATIENT_DB_ERROR = "Patient DB Error";
    public static final String DOCTOR_DB_ERROR = "Doctor DB Error";
    public static final String RECORD_DB_ERROR = "Record DB Error";

    public static final String MAIN_MENU = """
            1. Get all patients
            2. Add Patient
            3. Update Patient
            4. Delete Patient
            5. Get all Doctors
            6. Get all MedRecords by patient
            7. Add MedRecord
            8. Update MedRecord
            9. Delete MedRecord
            10. Exit
            """;
    public static final String WRONG_CREDENTIALS = "Wrong credentials";
    public static final String WELCOME = "Welcome, ";
    public static final String PASSWORD = "Password:";
    public static final String USERNAME = "Username:";
    public static final String ID = "ID:";
    public static final String NOMBRE_DUPLICADO = "Nombre de usuario duplicado. Elige otro:";
    public static final String ERROR_ANYADIR = "No se ha podido añadir";
    public static final String ASK_CONFIRMATION_MEDRECS = "This patient has medical records. Do you still want to delete? (Y/N)";
    public static final String ASK_CONFIRMATION_DOCTOR = "This doctor has medical records. Do you still want to delete? (Y/N)";
}
