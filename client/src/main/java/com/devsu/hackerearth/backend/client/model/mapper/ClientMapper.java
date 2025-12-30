package com.devsu.hackerearth.backend.client.model.mapper;

import org.mapstruct.Mapper;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

// @Mapper(componentModel = "spring")
// public interface ClientMapper {
public class ClientMapper {

    // ClientDto toDto(Client client);

    // List<ClientDto> toDto(List<Client> clients);
    // private static ClientRepository clientRepository;

    // public ClientMapper(ClientRepository clientRepository) {
	// 	ClientMapper.clientRepository = clientRepository;
	// 	// this.clientMapper = clientMapper;
	// }

    public static ClientDto toDto(Client client) {
        if (client == null)
            return null;

        var dto = new ClientDto();
        dto.setActive(client.isActive());
        dto.setAddress(client.getAddress());
        dto.setAge(client.getAge());
        dto.setDni(client.getDni());
        dto.setGender(client.getGender());
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setPassword(client.getPassword());
        dto.setPhone(client.getPhone());

        return dto;
    }

    public static Client toEntity(ClientDto dto) {
        if (dto == null)
            return null;

        var entity = new Client();
        entity.setActive(dto.isActive());
        entity.setAddress(dto.getAddress());
        entity.setAge(dto.getAge());
        entity.setDni(dto.getDni());
        entity.setGender(dto.getGender());
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());

        return entity;
    }

    public static Client toEntityUpdated(Client entity, ClientDto dto) {
        if (dto == null)
            return null;

        entity.setActive(dto.isActive());
        entity.setAddress(dto.getAddress());
        entity.setAge(dto.getAge());
        entity.setDni(dto.getDni());
        entity.setGender(dto.getGender());
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());

        return entity;
    }
}
