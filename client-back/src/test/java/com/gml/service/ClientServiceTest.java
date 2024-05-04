package com.gml.service;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;
import com.gml.repository.ClientJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    ClientJpaRepository clientJpaRepository;
    ClientServiceI personService ;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock
        personService = new ClientService(clientJpaRepository);
    }

    @Test
    public void testFindAll(){
        Mockito.when( clientJpaRepository.findAll()).thenReturn(getPersonaDtos());
        var lstPerson=personService.findAll();

        Assertions.assertAll("test",
                () -> assertEquals(lstPerson.size(), 1, "Cantidad debe ser 1"),
                () -> assertEquals(lstPerson.get(0).getName(), "segundoNombre", "Segundo Nombre no es igual"));
    }
    @Test
    public void testFindBytipoDocumentoNumeroDocumento(){
     /*   Mockito.when( clientJpaRepository.findByEmail("C"))
                .thenReturn(getPersonaDtos());
        var lstPerson=personService.findByEmail("C");

        Assertions.assertAll("test",
                () -> assertEquals(lstPerson.size(), 1, "Cantidad debe ser 1"),
                () -> assertEquals(lstPerson.get(0).getName(), "segundoNombre", "Segundo Nombre no es igual")
                );
        */
    }

    @Test
    public void testAddPerson(){
        Mockito.when( clientJpaRepository.save(getPersonaDtos().get(0)))
                .thenReturn(getPersonaDtos().get(0));
        var lstPerson=personService.save(getPersonaDto());

        Assertions.assertAll("test",
                () -> assertEquals (String.valueOf(lstPerson.getSharedKey()), "1", "Id debe ser 1"),
                () -> assertEquals(lstPerson.getName(), "primerApellido", "Primer Apellido  no es igual")
        );
    }
    private ClienteDto getPersonaDto(){
        return ClienteDto.builder()
                .name("C")
                .phone("123L")

                .ended(new Date())
                .started(new Date())
                .added(new Date())
                .build();
    }
    private List<Client> getPersonaDtos() {
        List<Client> clients = new ArrayList<>();
        clients.add(Client.builder()
                .name("segundoNombre")
                .phone ("C")
                .ended(new Date())
                .started(new Date())
                .added(new Date())

                .build());
        return clients;
    }
}
