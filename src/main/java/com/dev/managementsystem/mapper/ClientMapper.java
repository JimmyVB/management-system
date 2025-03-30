package com.dev.managementsystem.mapper;

import com.dev.managementsystem.dtos.ClientCreateDto;
import com.dev.managementsystem.dtos.ClientDto;
import com.dev.managementsystem.entities.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    private final ModelMapper modelMapper;

    public ClientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ClientDto toDto(Client client) {
        ClientDto dto = modelMapper.map(client, ClientDto.class);
        dto.setEstimatedLifeExpectancy(client.getBirthDate().plusYears(80));
        return dto;
    }

    public Client toEntity(ClientCreateDto clientCreateDto) {
        return modelMapper.map(clientCreateDto, Client.class);
    }
}

