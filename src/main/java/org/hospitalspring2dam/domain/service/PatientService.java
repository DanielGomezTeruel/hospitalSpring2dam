package org.hospitalspring2dam.domain.service;


import org.hospitalspring2dam.dao.PatientRepository;
import org.hospitalspring2dam.domain.mappers.PatientDTOMapper;
import org.hospitalspring2dam.domain.model.PatientDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientDTOMapper patientDTOMapper;


    public PatientService(PatientRepository patientRepository, PatientDTOMapper patientDTOMapper){
        this.patientRepository = patientRepository;
        this.patientDTOMapper = patientDTOMapper;
    }
    public List<PatientDTO> getAll(){
        return patientRepository.getAll().stream().map(patientDTOMapper::entityToDto).collect(Collectors.toList());
    }

    public int add(PatientDTO patientDTO){
        return patientRepository.add(patientDTOMapper.dtoToEntity(patientDTO));
    }

    public void update(PatientDTO patientDTO){
        patientRepository.update(patientDTOMapper.dtoToEntity(patientDTO));
    }

    public void delete(int idDelete, boolean answer){
        patientRepository.delete(idDelete, answer);
    }
}
