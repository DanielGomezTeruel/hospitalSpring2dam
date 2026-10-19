package org.hospitalspring2dam.dao.repositories;


import org.hospitalspring2dam.dao.CredentialRepository;
import org.hospitalspring2dam.dao.common.DBConnection;
import org.hospitalspring2dam.dao.common.SQLQueries;
import org.hospitalspring2dam.dao.mappers.CredentialsSQLMapper;
import org.hospitalspring2dam.dao.model.Credentials;
import org.hospitalspring2dam.domain.exceptions.CredentialsDBException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class SpringCredentialRepository implements CredentialRepository {


    private final DBConnection db;
    private final CredentialsSQLMapper credentialsSQLMapper;

    public SpringCredentialRepository(DBConnection db, CredentialsSQLMapper credentialsSQLMapper) {
        this.db = db;
        this.credentialsSQLMapper = credentialsSQLMapper;
    }

    @Override
    public Credentials getByUsername(String username){
        Credentials credential;
        try (Connection con = db.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_getCredentials_QUERY)){
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            credential=credentialsSQLMapper.mapCredentials(rs).getFirst();

        }catch (SQLException ex) {
            Logger.getLogger(SpringPatientRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new CredentialsDBException();
        }
        return credential;
    }


    @Override
    public int add(Credentials credentials) {
        return 0;
    }

    @Override
    public void update(Credentials credentialsDatabase) {

    }

    @Override
    public void delete(int idDelete) {

    }
}
