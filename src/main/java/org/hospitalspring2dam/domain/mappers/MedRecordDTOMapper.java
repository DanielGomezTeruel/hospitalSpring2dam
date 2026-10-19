package org.hospitalspring2dam.domain.mappers;



import org.hospitalspring2dam.dao.model.MedRecord;
import org.hospitalspring2dam.domain.model.MedRecordDTO;
import org.springframework.stereotype.Component;


import java.time.LocalDate;


@Component
public class MedRecordDTOMapper {
    public MedRecord dtoToEntity(MedRecordDTO medRecordDTO) {

        return new MedRecord(medRecordDTO.getId(),medRecordDTO.getIdPatient(),medRecordDTO.getIdDoctor(),medRecordDTO.getDescription(), LocalDate.parse(medRecordDTO.getDate()), medRecordDTO.getMedications());

    }

    public MedRecordDTO entityToDto(MedRecord medRecord) {
        return new MedRecordDTO(medRecord.getId(),medRecord.getDiagnosis(),
                medRecord.getAdmission_date().toString(),medRecord.getPatientID(),medRecord.getDoctorID(), medRecord.getMedications());
    }
}
