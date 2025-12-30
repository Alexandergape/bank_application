package com.devsu.hackerearth.backend.client.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		// clientService.getAll();
		return ResponseEntity.ok(clientService.getAll());
		// return ResponseEntity.ok(Collections.emptyList());
	}

	@GetMapping("{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		// api/clients/{id}
		// Get clients by id
		// return null;
		// return ResponseEntity.ok(clientService.getById(id));
		var res = clientService.getById(id);
		if (res != null)
			return ResponseEntity.ok(res);
		else
			return ResponseEntity.notFound().build();
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(a);

		// try {
		// return ResponseEntity.ok(clientService.getById(id));
		// } catch (Exception e) {
		// // TODO: handle exception
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// }
	}

	@PostMapping
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
		// api/clients
		// Create client
		// return null;
		// return ResponseEntity.ok(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientDto));
	}

	@PutMapping("{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		// api/clients/{id}
		// Update client
		// return ResponseEntity.ok(clientService.update(id, clientDto));
		clientDto.setId(id);
		// return ResponseEntity.ok(clientService.update(clientDto));

		var res = clientService.update(clientDto);
		if (res != null)
			return ResponseEntity.ok(res);
		else
			return ResponseEntity.notFound().build();
	}

	@PatchMapping("{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {
		var res = clientService.partialUpdate(id, partialClientDto);
		if (res != null)
			return ResponseEntity.ok(res);
		else
			return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
