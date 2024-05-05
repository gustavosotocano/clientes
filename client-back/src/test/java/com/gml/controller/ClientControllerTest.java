package com.gml.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gml.dto.ClienteDto;
import com.gml.exception.ResourceNotFoundException;
import com.gml.repository.ClientJpaRepository;
import com.gml.service.ClientService;
import com.gml.util.Util;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientJpaRepository clientJpaRepository;

    @MockBean
    private ClientService clientService;

    @Test
    public void findAllTest() throws Exception {

        List<ClienteDto> personas = getClientDtos();
        Mockito.when(clientService.findAll()).thenReturn(personas);


        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/v1/client"))
                .andExpect(status().isOk())
                .andReturn();
        List<ClienteDto> objeto = stringToObject(result);
        Assertions.assertAll("test",
                () -> assertEquals(objeto.size(), 1, "Cantidad debe ser 1"),
                () -> assertEquals(objeto.get(0).getBussinessId(), "John Doe", "Business Id no es igual"));


    }

    private List<ClienteDto> stringToObject(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    private List<ClienteDto> getClientDtos() {
        List<ClienteDto> clienteDtos = new ArrayList<>();
        clienteDtos.add(ClienteDto.builder()
                        .sharedKey("jdoe")
                        .bussinessId("John Doe")
                        .email("jdoe@gmail.com")
                        .phone("1111111111")
                        .started(Util.stringToDate( "Jun-07-2013"))
                        .ended(Util.stringToDate("Jul-07-2013"))
               .build());
return clienteDtos;
 }



    @Test
    public void findAllNotFoundTest() throws Exception {
        Mockito.when(clientService.findAll())
                .thenReturn(Collections.emptyList());
        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/v1/client"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Assertions.assertAll("test",
                () -> assertEquals(HttpStatus.OK.value(),
                        result.getResponse().getStatus(), "Esperamos una lista vacia"));
    }

    @Test
    public void findbyEmailNfotFoundTest() throws Exception {

        /*
        Mockito.when(personService.findByEmail("C")).thenReturn(getPersonaDtos());
        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/persona/C/dcto/234"))

                .andExpect(status().isOk())
                .andReturn();
        List<ClienteDto> objeto = stringToObject(result);
        Assertions.assertAll("test",
                () -> assertEquals(objeto.get(0).getName(), "segundoNombre", "No tiene personas Registradas"));

*/
    }

    @Test
    public void findbyEmailNotFoundTest() throws Exception {


        Mockito.when(clientService.findByEmail("jdsoe@gmail.com"))
                .thenThrow(new ResourceNotFoundException("001", "Client Not Found"));
        //  RequestBuilder request= MockMvcRequestBuilders
        var result = mockMvc.perform(get("/v1/client/email/jdsoe@gmail.com"))

                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertAll("test",
                () -> assertEquals(((ResourceNotFoundException) Objects.requireNonNull(result.getResolvedException())).getMessage()
                        , "Client Not Found", "Client Not Found"));


    }

    @Test
    public void addClientTest() throws Exception {
        String json= """
                {
                
                "bussinessId":"jhon doe",
                "email":"gustavo@domain.com",
                "phone":"adolfo",
                "added":"2024-05-01",
                "started":"2024-05-25",
                "ended":"2024-05-25",
               
                }
                """;
         mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON)
                        .content(json))

                .andExpect(status().isCreated())
                .andReturn();

    }
    @Test
    public void addClientTrowTest() throws Exception {
        String json= """
                {
                "email":"gustavo@domain.com",
                "phone":"adolfo",
                "added":"2024-05-01",
                "started":"2024-05-25",
                "ended":"2024-05-25",
                "name":"cra 1a113 #72-84"
                }
                """;
       mockMvc.perform(post("/v1/client").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}
