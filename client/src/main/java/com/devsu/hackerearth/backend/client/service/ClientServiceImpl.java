package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.exception.EntityAlreadyExistsException;
import com.devsu.hackerearth.backend.client.exception.NotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.model.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	// private final ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
		// this.clientMapper = clientMapper;
	}

	@Override
	public List<ClientDto> getAll() {
		log.info("Fetching all clients");
		return clientRepository.findAll().stream().map(ClientMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		// Get clients by id
		Client client = getClientById(id);
		return ClientMapper.toDto(client);
	}

	private Client getClientById(Long id) {
		// Get clients by id
		log.info("Fetching client with id " + id);
		return clientRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Client", id));
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		log.info("Creating client with dni "   + clientDto.getDni());
		Client client = ClientMapper.toEntity(clientDto);

		try {
			clientRepository.save(client);
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyExistsException("Client", "dni", clientDto.getDni());
		}
		return ClientMapper.toDto(client);
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		// public ClientDto update(Long id, ClientDto clientDto) {
		// Client client = getClientById(id);
		Client client = getClientById(clientDto.getId());
		ClientMapper.toEntityUpdated(client, clientDto);
		clientRepository.save(client);

		try {
			clientRepository.save(client);
			clientRepository.flush();
			// clientRepository.saveAndFlush(client);

		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new EntityAlreadyExistsException("Client", "dni", clientDto.getDni());
		}

		return ClientMapper.toDto(client);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Partial update account
		Client client = getClientById(id);

		client.setActive(partialClientDto.isActive());
		clientRepository.save(client);
		return ClientMapper.toDto(client);
	}

	@Override
	public void deleteById(Long id) {
		// if (!clientRepository.existsById(id))
		// 	throw new NotFoundException("Client", id);
		Client client = getClientById(id);
		
		client.setActive(false);
		clientRepository.save(client);

		// clientRepository.deleteById(id);
	}
}
