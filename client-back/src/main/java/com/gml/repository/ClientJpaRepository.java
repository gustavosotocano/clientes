package com.gml.repository;

import com.gml.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJpaRepository extends JpaRepository<Client, String> {

    Client findByEmail(String email);


}
