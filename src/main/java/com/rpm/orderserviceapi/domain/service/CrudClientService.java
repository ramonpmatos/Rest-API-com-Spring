package com.rpm.orderserviceapi.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpm.orderserviceapi.domain.exception.BusinessException;
import com.rpm.orderserviceapi.domain.model.Client;
import com.rpm.orderserviceapi.domain.repository.ClientRepository;

@Service
public class CrudClientService {

	@Autowired
	private ClientRepository clientReposiroty;
	
	public Client save(Client client) {
		Optional<Client> clientExisting = clientReposiroty.findByEmail(client.getEmail());
		if(clientExisting.isPresent() && !clientExisting.get().equals(client)) {
			throw new BusinessException("Já existe um cliente cadastrado com este e-mail.");
		}
		return clientReposiroty.save(client);
	}
	
	public void delete(Long id) {
		clientReposiroty.deleteById(id);
	}
}
