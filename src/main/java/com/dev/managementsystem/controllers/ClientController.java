package com.dev.managementsystem.controllers;

import com.dev.managementsystem.dtos.ClientCreateDto;
import com.dev.managementsystem.dtos.ClientDto;
import com.dev.managementsystem.dtos.StatisticsDto;
import com.dev.managementsystem.entities.Client;
import com.dev.managementsystem.exceptions.InvalidRequestException;
import com.dev.managementsystem.mapper.ClientMapper;
import com.dev.managementsystem.services.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Client API", description = "Customer Management")
public class ClientController {

    @Autowired
    private ClientService clientService;
    private final ClientMapper clientMapper;


    /**
     * Creates a new client.
     * @param clientCreateDto DTO containing client information.
     * @return ClientDto containing the saved client data.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient(@Valid @RequestBody ClientCreateDto clientCreateDto) {
        Client client = clientMapper.toEntity(clientCreateDto);
        Client savedClient = clientService.saveClient(client);
        return clientMapper.toDto(savedClient);
    }

    /**
     * Retrieves a paginated list of clients.
     * @param page Page number (0-based index).
     * @param size Number of clients per page.
     * @return A paginated list of clients.
     * @throws InvalidRequestException if page or size values are invalid.
     */
    @GetMapping
    public Page<ClientDto> listClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        if (page < 0 || size <= 0) {
            throw new InvalidRequestException("Page number and size must be positive");
        }
        Pageable pageable = PageRequest.of(page, size);
        return clientService.getAllClients(pageable)
                .map(clientMapper::toDto);
    }

    /**
     * Retrieves statistical data about the registered clients.
     * @return StatisticsDto containing average age and standard deviation.
     */
    @GetMapping("/statistics")
    public StatisticsDto getStatistics() {
        return clientService.getClientStatistics();
    }
}

