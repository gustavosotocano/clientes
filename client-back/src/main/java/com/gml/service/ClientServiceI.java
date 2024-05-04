package com.gml.service;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;

import java.util.List;

public interface ClientServiceI  {
    List<ClienteDto> findAll();

    ClienteDto findByEmail(String email) ;

    ClienteDto findBySharedKey(String email) ;

    Client save(ClienteDto clienteDto);

    Client update(ClienteDto clienteDto);
}
