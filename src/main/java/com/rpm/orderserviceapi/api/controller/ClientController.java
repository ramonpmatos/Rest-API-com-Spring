package com.rpm.orderserviceapi.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rpm.orderserviceapi.domain.model.Client;
import com.rpm.orderserviceapi.domain.repository.ClientRepository;
import com.rpm.orderserviceapi.domain.service.CrudClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private CrudClientService crudClientService;
	
	@Autowired
	private ClientRepository clientRepository;

	@GetMapping
	public List<Client> list() {
		return clientRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Client> read(@PathVariable Long id) {
		Optional<Client> client = clientRepository.findById(id);

		if (client.isPresent()) {
			return ResponseEntity.ok(client.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Client created(@Valid @RequestBody Client client) {
		return crudClientService.save(client);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@Valid @PathVariable Long id, @RequestBody Client client) {
		if (!clientRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		client.setId(id);
		client = crudClientService.save(client);
		return ResponseEntity.ok(client);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Client> delete(@PathVariable Long id){
		if (!clientRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		crudClientService.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
}
