package org.hospitalspring2dam.domain.service;


import org.hospitalspring2dam.dao.MedRecordRepository;
import org.hospitalspring2dam.domain.mappers.MedRecordDTOMapper;
import org.hospitalspring2dam.domain.model.MedRecordDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedRecordService {
    private final MedRecordRepository medRecordRepository;
    private final MedRecordDTOMapper medRecordDTOMapper;


    public MedRecordService(MedRecordRepository medRecordRepository, MedRecordDTOMapper medRecordDTOMapper){
        this.medRecordRepository = medRecordRepository;
        this.medRecordDTOMapper = medRecordDTOMapper;
    }

    public List<MedRecordDTO> getAllbyPatientId(int patientId){
        return medRecordRepository.getAllbyPatientId(patientId).stream().map(medRecordDTOMapper::entityToDto).collect(Collectors.toList());
    }

    public int add(MedRecordDTO medRecordDTO){
        return medRecordRepository.add(medRecordDTOMapper.dtoToEntity(medRecordDTO));
    }

    public void update(MedRecordDTO medRecordDTO){medRecordRepository.update(medRecordDTOMapper.dtoToEntity(medRecordDTO));}

    public void delete(int idDelete){medRecordRepository.delete(idDelete);}
}
