package com.dev.managementsystem.service;

import com.dev.managementsystem.dtos.StatisticsDto;
import com.dev.managementsystem.entities.Client;
import com.dev.managementsystem.exceptions.NoDataFoundException;
import com.dev.managementsystem.repositories.ClientRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.dev.managementsystem.services.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientServiceImpl clientService;

    @MockitoBean
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveClient() {
        Client client = new Client(null, "Juan", "Pérez", 30, LocalDate.of(1994, 5, 10), null);

        Client savedClientMock = new Client(1L, "Juan", "Pérez", 30, LocalDate.of(1994, 5, 10), LocalDateTime.now());

        when(clientRepository.save(any(Client.class))).thenReturn(savedClientMock);

        Client savedClient = clientService.saveClient(client);

        assertNotNull(savedClient.getId());
        assertEquals(1L, savedClient.getId());
        assertEquals("Juan", savedClient.getFirstName());
        assertEquals("Pérez", savedClient.getLastName());
    }

    @Test
    void testGetClientStatistics() {
        List<Client> clients = List.of(
                new Client(1L, "Juan", "Pérez", 30, LocalDate.of(1994, 5, 10), null),
                new Client(2L, "Ana", "Gómez", 25, LocalDate.of(1999, 3, 22), null),
                new Client(3L, "Luis", "Torres", 35, LocalDate.of(1989, 8, 15), null)
        );

        when(clientRepository.findAll()).thenReturn(clients);

        StatisticsDto stats = clientService.getClientStatistics();

        assertNotNull(stats);
        assertEquals(30.0, stats.getAverageAge(), 0.01);
        assertEquals(4.08, stats.getStandardDeviation(), 0.01); // Se usa delta para comparar double
    }

    @Test
    void testGetClientStatistics_NoClients() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(NoDataFoundException.class, () -> clientService.getClientStatistics());

        assertEquals("No clients found.", exception.getMessage());
    }
}