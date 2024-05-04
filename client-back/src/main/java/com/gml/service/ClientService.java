package com.gml.service;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;
import com.gml.exception.ResourceNotFoundException;
import com.gml.repository.ClientJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientService implements ClientServiceI {


    private final ClientJpaRepository clientJpaRepository;

    private static Long idCounter = 0L;

    public ClientService(ClientJpaRepository clientJpaRepository) {
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public List<ClienteDto> findAll() {

        //if (personaLst.isEmpty()) {
         //   throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tiene Personas registradas", null);
       // }
        return clientJpaRepository.findAll().stream()
                .map(this::toClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto findByEmail(String email) {


        var clients = clientJpaRepository.findByEmail(email);
        if (Objects.isNull(clients)) {
            throw new ResourceNotFoundException( "V-102", "Cliente No Encontrado");
        }
        var personLst = clientJpaRepository.findByEmail(email);
        return toClientDto(personLst);
                /*.stream()
                .map(this::toClientDto)
                .collect(Collectors.toList());*/
    }

    @Override
    public ClienteDto findBySharedKey(String sharedKey) {


        var clients = clientJpaRepository.findById(sharedKey);
        if (clients.isEmpty()) {
            throw new ResourceNotFoundException( "V-102", "Cliente No Encontrado");
        }

        return toClientDto(clients.get());
                /*.stream()
                .map(this::toClientDto)
                .collect(Collectors.toList());*/
    }

    @Override
    public Client save(ClienteDto clienteDto) {
        if (Objects.isNull(clienteDto.getSharedKey())) {
            clienteDto.setSharedKey(clienteDto.getSharedKey());
        }
        var clients = clientJpaRepository
                .findByEmail(clienteDto.getEmail());
        if (Objects.nonNull(clients)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente Ya Creado");
        }
        String[]names = clienteDto.getBussinessId().split(" ");
        String lastName="";
        if(names.length==1){
          lastName  = names[0];
        }else if(names.length==2){
            lastName  = names[1];
        }else if(names.length>2){
            lastName  = names[2];
        }
         clienteDto.setAdded(new Date());
        String initial= names[0].substring(0, 1);
        clienteDto.setSharedKey((initial+lastName).toLowerCase(Locale.ROOT));

        return clientJpaRepository.save(toClient(clienteDto));
    }

    @Override
    public Client update(ClienteDto clienteDto) {

        var client = clientJpaRepository
                .findById(clienteDto.getSharedKey());

        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente No existe");
        }
        var clientEmail = clientJpaRepository
                .findByEmail(clienteDto.getEmail());

        if(Objects.nonNull(clientEmail) && (!client.get().getSharedKey().equals(clientEmail.getSharedKey()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email pertenece a otro cliente");
        }


        return clientJpaRepository.save(toClientUpdate(clienteDto,client.get()));
    }

    private Client toClient(ClienteDto clienteDto) {
        return Client.builder()
                .sharedKey(clienteDto.getSharedKey())
                .bussinessId(clienteDto.getBussinessId())
                .email(clienteDto.getEmail())
                .phone(clienteDto.getPhone())
                .added(clienteDto.getAdded())
                .started(clienteDto.getStarted())
                .ended(clienteDto.getEnded())

                .build();
    }

    private Client toClientUpdate(ClienteDto from ,Client to) {
      to.setEnded(from.getEnded());
      to.setEmail(from.getEmail());
      to.setPhone(from.getPhone());
      to.setStarted(from.getStarted());
      to.setBussinessId(from.getBussinessId());
      to.setUpdated(new Date());
return to;
    }


    private ClienteDto toClientDto(Client client) {
        return ClienteDto.builder()
                .sharedKey(client.getSharedKey())
                .bussinessId(client.getBussinessId())
                .email(client.getEmail())
                .phone(client.getPhone())
                .added(client.getAdded())
                .started(client.getStarted())
                .ended(client.getEnded())

                .build();
    }
}
