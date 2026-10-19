package org.hospitalspring2dam.domain.service;





import org.hospitalspring2dam.dao.CredentialRepository;
import org.hospitalspring2dam.domain.mappers.CredentialDTOMapper;
import org.hospitalspring2dam.domain.model.CredentialDTO;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final CredentialDTOMapper credentialDTOMapper;


    public CredentialService(CredentialRepository credentialRepository, CredentialDTOMapper credentialDTOMapper){
        this.credentialRepository=credentialRepository;
        this.credentialDTOMapper=credentialDTOMapper;
    }

    public boolean login(CredentialDTO credentialDTO){
        return credentialRepository.getByUsername(credentialDTO.getUsername())!=null&&credentialDTO.getPassword().equals(credentialRepository.getByUsername(credentialDTO.getUsername()).getPassword());
    }
}
