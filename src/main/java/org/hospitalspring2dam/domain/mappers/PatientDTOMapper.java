package org.hospitalspring2dam.domain.mappers;



import org.hospitalspring2dam.dao.model.Credentials;
import org.hospitalspring2dam.dao.model.Patient;
import org.hospitalspring2dam.domain.model.CredentialDTO;
import org.hospitalspring2dam.domain.model.PatientDTO;
import org.springframework.stereotype.Component;

@Component
public class PatientDTOMapper {
    public Patient dtoToEntity(PatientDTO patientDTO) {

        return new Patient(patientDTO.getId(),patientDTO.getName(),patientDTO.getBirthDate(),patientDTO.getPhone(),new Credentials(patientDTO.getId(), patientDTO.getCredentials().getUsername(), patientDTO.getCredentials().getPassword()));

    }

    public PatientDTO entityToDto(Patient patient) {
        return new PatientDTO(patient.getId(),patient.getName(),
                patient.getBirthDate(),patient.getPhone(),0,new CredentialDTO(patient.getCredential().getUsername(), patient.getCredential().getPassword()));
    }

}
