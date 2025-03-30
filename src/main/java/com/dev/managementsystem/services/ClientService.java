package com.dev.managementsystem.services;

import com.dev.managementsystem.dtos.StatisticsDto;
import com.dev.managementsystem.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Client saveClient(Client client);
    Page<Client> getAllClients(Pageable pageable);
    StatisticsDto getClientStatistics();
}
