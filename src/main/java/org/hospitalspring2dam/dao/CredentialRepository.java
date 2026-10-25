package org.hospitalspring2dam.dao;


import org.hospitalspring2dam.dao.model.Credentials;

public interface CredentialRepository {
    Credentials getByUsername(String username);

    int add(Credentials credentials);

    void update(Credentials credentialsDatabase);
    void deleteByPatient(int idDelete);
}
