package org.hospitalspring2dam.domain.service;




import org.hospitalspring2dam.dao.DoctorRepository;
import org.hospitalspring2dam.domain.mappers.DoctorDTOMapper;
import org.hospitalspring2dam.domain.model.DoctorDTO;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorDTOMapper doctorDTOMapper;

    public DoctorService(DoctorRepository doctorRepository, DoctorDTOMapper doctorDTOMapper){
        this.doctorRepository = doctorRepository;
        this.doctorDTOMapper = doctorDTOMapper;
    }

    public List<DoctorDTO> getAll(){
        return doctorRepository.getAll().stream().map(doctorDTOMapper::entityToDto).collect(Collectors.toList());
    }

    public void add(DoctorDTO doctorDTO){
        doctorRepository.add(doctorDTOMapper.dtoToEntity(doctorDTO));
    }

    public void update(DoctorDTO doctorDTO){doctorRepository.update(doctorDTOMapper.dtoToEntity(doctorDTO));}

    public void delete(int idDelete, boolean answer){doctorRepository.delete(idDelete, answer);}
}
