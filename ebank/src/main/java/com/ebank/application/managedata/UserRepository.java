package com.ebank.application.managedata;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    User findByNomUtilisateur(String nomUtilisateur);
}
