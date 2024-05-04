package com.gml.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gml.dto.ClienteDto;
import com.gml.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest()
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService personService;

    @Test
    public void findAllTest() throws Exception {
/*
        List<ClienteDto> personas = getPersonaDtos();
        Mockito.when(personService.findAll()).thenReturn(personas);
        */

        //  RequestBuilder request= MockMvcRequestBuilders
      /*  var result = mockMvc.perform(get("/persona"))
                .andExpect(status().isOk())
                .andReturn();
        List<ClienteDto> objeto = stringToObject(result);
        Assertions.assertAll("test",
                () -> assertEquals(objeto.size(), 1, "Cantidad debe ser 1"),
                () -> assertEquals(objeto.get(0).getName(), "segundoNombre", "Segundo Nombre no es igual"));

*/
    }

    private List<ClienteDto> stringToObject(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    private ClienteDto getPersonaDtos() {
         return ClienteDto.builder().name("segundoNombre")
                .email("C")
                .build();

    }

    @Test
    public void findAllNotFoundTest() throws Exception {


        Mockito.when(personService.findAll()).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No tiene Personas registradas", null));
        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/persona"))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertAll("test",
                () -> assertEquals(((ResponseStatusException) Objects.requireNonNull(result.getResolvedException())).getReason(),
                        "No tiene Personas registradas", "No tiene Personas registradas"));
    }

    @Test
    public void findbyTipoDctoNumerdocumentoNotFoundTest() throws Exception {


        Mockito.when(personService.findByEmail("C")).thenReturn(getPersonaDtos());
        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/persona/C/dcto/234"))

                .andExpect(status().isOk())
                .andReturn();
        List<ClienteDto> objeto = stringToObject(result);
        Assertions.assertAll("test",
                () -> assertEquals(objeto.get(0).getName(), "segundoNombre", "No tiene personas Registradas"));


    }

    @Test
    public void findbyTipoDctoNumerodocumentothrowTest() throws Exception {


        Mockito.when(personService.findByEmail("C"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona No Encontrada", null));
        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/persona/C/dcto/123"))

                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertAll("test",
                () -> assertEquals(((ResponseStatusException) Objects.requireNonNull(result.getResolvedException())).getReason()
                        , "Persona No Encontrada", "Persona No Encontrada"));


    }

    @Test
    public void addPersonTest() throws Exception {
        String json= """
                {
                "tipoDocumento":"C",
                "numeroDocumento":"16829228",
                "primerNombre":"gustavos",
                "segundoNombre":"adolfos",
                "primerApellido":"soto",
                "segundoApellido":"cano",
                "telefono":"3113397499",
                "direccion":"cra 1a113 #72-84",
                "ciudad":"cali"

                }""";
         mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON)
                        .content(json))

                .andExpect(status().isCreated())
                .andReturn();

    }
    @Test
    public void addPersonTrowTest() throws Exception {
        String json= """
                {
                "numberDocumento":"16829228",
                "primerNombre":"gustavos",
                "segundoNombre":"adolfos",
                "primerApellido":"soto",
                "segundoApellido":"cano",
                "telefono":"3113397499",
                "direccion":"cra 1a113 #72-84",
                "ciudad":"cali"

                }""";
       mockMvc.perform(post("/persona").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}
