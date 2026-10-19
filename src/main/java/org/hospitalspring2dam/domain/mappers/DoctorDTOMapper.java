package org.hospitalspring2dam.domain.mappers;


import org.hospitalspring2dam.dao.model.Credentials;
import org.hospitalspring2dam.dao.model.Doctor;
import org.hospitalspring2dam.domain.model.CredentialDTO;
import org.hospitalspring2dam.domain.model.DoctorDTO;
import org.springframework.stereotype.Component;


@Component
public class DoctorDTOMapper {
    public Doctor dtoToEntity(DoctorDTO doctorDTO) {

        return new Doctor(doctorDTO.getId(),doctorDTO.getName(),null, null, new Credentials(doctorDTO.getId(), doctorDTO.getCredentials().getUsername(),doctorDTO.getCredentials().getPassword()));

    }

    public DoctorDTO entityToDto(Doctor doctor) {
        return new DoctorDTO(doctor.getId(),doctor.getName(), new CredentialDTO(doctor.getCredentials().getUsername(), doctor.getCredentials().getPassword()));
    }
}
