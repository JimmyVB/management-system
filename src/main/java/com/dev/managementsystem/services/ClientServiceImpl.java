package com.dev.managementsystem.services;

import com.dev.managementsystem.dtos.StatisticsDto;
import com.dev.managementsystem.entities.Client;
import com.dev.managementsystem.exceptions.NoDataFoundException;
import com.dev.managementsystem.exceptions.TooManyRequestsException;
import com.dev.managementsystem.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import com.google.common.util.concurrent.RateLimiter;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final RateLimiter rateLimiter;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RateLimiter rateLimiter) {
        this.clientRepository = clientRepository;
        this.rateLimiter = rateLimiter;
    }

    @Override
    @CacheEvict(value = "clients", allEntries = true)
    public Client saveClient(Client client) {
        logger.info("Saving client: {}", client);
        return clientRepository.save(client);
    }

    @Override
    @Cacheable("clients") // Habilita caché en esta consulta
    public Page<Client> getAllClients(Pageable pageable) {
        logger.info("Fetching all clients.");
        if (!rateLimiter.tryAcquire()) {
            throw new TooManyRequestsException("Too many requests. Please wait a few seconds.");
        }
        return clientRepository.findAll(pageable);
    }

    @Override
    public StatisticsDto getClientStatistics() {
        logger.info("Calculating client statistics.");
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            logger.warn("Don't meet with clients to calculate statistics.");
            throw new NoDataFoundException("No clients found.");
        }

        return calculateStatistics(clients);
    }

    private StatisticsDto calculateStatistics(List<Client> clients) {
        List<Integer> ages = clients.stream().map(Client::getAge).toList();
        double average = ages.stream().mapToInt(age -> age).average().orElse(0);
        double variance = ages.stream()
                .mapToDouble(age -> Math.pow(age - average, 2))
                .average()
                .orElse(0);

        double standardDeviation = Math.sqrt(variance);
        logger.info("Average: {}, Standard Deviation: {}", average, standardDeviation);
        return new StatisticsDto(average, standardDeviation);
    }
}
