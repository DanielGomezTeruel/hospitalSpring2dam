package org.hospitalspring2dam.domain.mappers;




import org.hospitalspring2dam.dao.model.Credentials;
import org.hospitalspring2dam.domain.model.CredentialDTO;
import org.springframework.stereotype.Component;

@Component
public class CredentialDTOMapper {

    public Credentials dtoToEntity(CredentialDTO credentialDTO) {

        return credentialDTO==null?null:new Credentials(0, credentialDTO.getUsername(),credentialDTO.getPassword());

    }

    public CredentialDTO entityToDto(Credentials credentials) {
        return credentials==null?null:new CredentialDTO(credentials.getUsername(),credentials.getPassword());
    }
}
