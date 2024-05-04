package com.gml.controller;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;
import com.gml.entity.ResponseError;
import com.gml.exception.RequestException;
import com.gml.exception.ResourceNotFoundException;
import com.gml.service.ClientServiceI;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@CrossOrigin(origins = "http://localhost:4200" ,maxAge = 360000)
@RestController
public class ClienteController {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final ClientServiceI personService;

    public ClienteController(ClientServiceI personService) {
        this.personService = personService;
    }

    @GetMapping("/v1/client")
    public List<ClienteDto> findAll() {
        return personService.findAll();
    }

    @GetMapping("/v1/client/email/{email}")
    public ResponseEntity<Object> getClientByEmail(@Valid @NotNull @PathVariable String email) {
        Objects.requireNonNull(email, "Email es requerido");
    //    Assert.isTrue(numeroDocumento<=0, "Numero documento no puede ser null  ");

            ClienteDto byEmail = personService.findByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(byEmail);


    }

    @PostMapping("/v1/client")
    public ResponseEntity<Object> addClient(@Valid  @RequestBody ClienteDto clienteDto) {
        Client createdTodo = null;
        try {
            if(clienteDto.getStarted().compareTo(clienteDto.getEnded()) >0){
               throw new RequestException("002","fecha inicio no puede ser mayor que la fecha de fin");

            }

            ResponseEntity<Object> BAD_REQUEST = validateEmailFormat(clienteDto);

            if (BAD_REQUEST != null) return BAD_REQUEST;

            createdTodo = personService.save(clienteDto);
       }catch (ResponseStatusException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
       }
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    private static ResponseEntity<Object> validateEmailFormat(ClienteDto clienteDto) {
        Matcher matcher = EMAIL_PATTERN.matcher(clienteDto.getEmail());
        if(!matcher.matches()){
            throw new RequestException("003","Email no tiene un formato correcto");

        }
        return null;
    }

    @PutMapping ("/v1/client")
    public ResponseEntity<Object> updClient(@Valid  @RequestBody ClienteDto clienteDto) {
        Client createdTodo = null;
        try {
            if(clienteDto.getStarted().compareTo(clienteDto.getEnded()) <0){
                  throw new RequestException("v-001","fecha inicio no puede ser mayor que la fecha de fin");
            }
            ResponseEntity<Object> BAD_REQUEST = validateEmailFormat(clienteDto);
            if (BAD_REQUEST != null) return BAD_REQUEST;

            createdTodo = personService.update(clienteDto);
        }catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }
}
