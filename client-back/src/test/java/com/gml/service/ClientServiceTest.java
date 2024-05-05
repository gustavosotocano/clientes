package com.gml.service;

import com.gml.dto.ClienteDto;
import com.gml.entity.Client;
import com.gml.exception.ResourceNotFoundException;
import com.gml.repository.ClientJpaRepository;
import com.gml.util.Util;
import com.gml.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ClientServiceTest {
    @Mock
    ClientJpaRepository clientJpaRepository;
    ClientServiceI clientServiceI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock
        clientServiceI = new ClientService(clientJpaRepository);
    }

    @Test
    public void testFindAll(){
        Mockito.when( clientJpaRepository.findAll()).thenReturn(getClientDtos());
        var lstPerson= clientServiceI.findAll();

        Assertions.assertAll("test",
                () -> assertEquals(lstPerson.size(), 1, "Cantidad debe ser 1"),
                () -> assertEquals(lstPerson.get(0).getBussinessId(), "segundoNombre", "Segundo Nombre no es igual"));
    }
    @Test
    public void testFindByEmail(){
        Mockito.when( clientJpaRepository.findByEmail("jdoe@gmail.com"))
                .thenReturn(getClient());
        var lstPerson=clientServiceI.findByEmail("jdoe@gmail.com");

        Assertions.assertAll("test",
                () -> assertEquals(lstPerson.getSharedKey(), "jdoe", "Shared key It's not equals"),
                () -> assertEquals(lstPerson.getBussinessId(), "jhon doe", "Business ID It's not equals")
                );

    }

    @Test
    public void testFindByEmailThrow(){
        Mockito.when( clientJpaRepository.findByEmail("jdoes@gmail.com"))
.thenReturn(null);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientServiceI.findByEmail("jdoes@gmail.com");
        });


        String expectedMessage = "Client Not Found";
        String actualMessage = exception.getMessage();
        Assertions.assertAll("test",
                () -> assertEquals (exception.getMessage(), "Client Not Found", "Exception error")


        );
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testAddPerson(){
        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);
        Mockito.when( clientJpaRepository.save(client.capture()))
                .thenReturn(getClientReturn());


        var lstClient= clientServiceI.save(getClientDtoAdd());

        Assertions.assertAll("test",
                () -> assertEquals (String.valueOf(lstClient.getSharedKey()), "jdoe", "Shared key must be jdoe"),
                () -> assertEquals(lstClient.getBussinessId(), "jhon doe", "Business ID It's not equals")

        );
    }
    private ClienteDto getClientDtoAdd(){
        return ClienteDto.builder()

                .bussinessId("jhon doe")
                .email("gustavo@domain.com")
                .phone("11234567890")
                .added(new Date())
                .ended(Util.stringToDate("Jul-07-2013"))
                .started(Util.stringToDate("Jun-07-2013"))
                .build();

    }
    private ClienteDto getClientDto(){
        return ClienteDto.builder()
                .sharedKey("jdoe")
                .bussinessId("jhon doe")
                .email("gustavo@domain.com")
                .phone("11234567890")
                .added(Util.stringToDate("Jun-07-2013"))
                .ended(Util.stringToDate("Jul-07-2013"))
                .build();

    }


    private Client getClient(){
        return Client.builder()
                .sharedKey("jdoe")
                .bussinessId("jhon doe")
                .email("gustavo@domain.com")
                .phone("11234567890")
                .added(new Date())
                .ended(Util.stringToDate("Jul-07-2013"))
                .started(Util.stringToDate("Jun-07-2013"))
                .build();

    }

    private Client getClientReturn(){
        return Client.builder()
                .sharedKey("jdoe")
                .bussinessId("jhon doe")
                .email("gustavo@domain.com")
                .phone("11234567890")
                .added(Util.stringToDate("Jun-07-2013"))
                .ended(Util.stringToDate("Jul-07-2013"))
                .build();

    }
    private List<Client> getClientDtos() {
        List<Client> clients = new ArrayList<>();
        clients.add(Client.builder()
                .bussinessId("segundoNombre")
                .phone ("C")
                .ended(new Date())
                .started(new Date())
                .added(new Date())

                .build());
        return clients;
    }
}
