package com.gml.service;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;
import com.gml.exception.RequestException;
import com.gml.exception.ResourceNotFoundException;
import com.gml.repository.ClientJpaRepository;
import com.gml.util.Utils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


        return clientJpaRepository.findAll().stream()
                .map(this::toClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto findByEmail(String email) {


        var clients = clientJpaRepository.findByEmail(email);
        if (Objects.isNull(clients)) {
            throw new ResourceNotFoundException( "001", "Client Not Found");
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

        var clients = clientJpaRepository
                .findByEmail(clienteDto.getEmail());
        if (Objects.nonNull(clients)) {
            throw new RequestException("005", "Cliente Ya Creado");
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

        return clientJpaRepository.save(Utils.toClient(clienteDto));
    }

    @Override
    public Client update(ClienteDto clienteDto) {

        var client = clientJpaRepository
                .findById(clienteDto.getSharedKey());

        if (client.isEmpty()) {

            throw new RequestException("005", "Client Does not exist");
        }
        var clientEmail = clientJpaRepository
                .findByEmail(clienteDto.getEmail());

        if(Objects.nonNull(clientEmail) && (!client.get().getSharedKey().equals(clientEmail.getSharedKey()))){
            throw new RequestException("005", "Email pertenece a otro cliente");
        }


        return clientJpaRepository.save(Utils.toClientUpdate(clienteDto,client.get()));
    }



    private ClienteDto toClientDto(Client client) {


        return ClienteDto.builder()
                .sharedKey(client.getSharedKey())
                .bussinessId(client.getBussinessId())
                .email(client.getEmail())
                .phone(client.getPhone())
                .added(changeFormatDate(client.getAdded()))
                .started(changeFormatDate(client.getStarted()))
                .ended(client.getEnded())

                .build();
    }

    private Date changeFormatDate(Date date) {

        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM-dd-yyyy");

        try {
            date = outputFormat.parse(date.toString());
            System.out.println("Parsed Date: " + date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

return date;
    }
}
