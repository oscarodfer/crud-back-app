package com.oscarodfer.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oscarodfer.springboot.backend.apirest.models.entities.Client;
import com.oscarodfer.springboot.backend.apirest.models.services.IClientService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestController {
	
	@Autowired
	private IClientService clientService;
	
	@GetMapping("/clients")
	public List<Client> getAllClients() {
		return clientService.findAll();
	}

	@GetMapping("/clients/{id}")
	public Client getClientWithId(@PathVariable Long id) {
		return clientService.findById(id);
	}
	
	@PostMapping("/clients")
	@ResponseStatus(HttpStatus.CREATED)
	public Client createClient(@RequestBody Client client) {
		return clientService.save(client);
	}
	
	@PutMapping("/clients/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Client updateClient(@RequestBody Client client, @PathVariable Long id) {
		Client currentClient = clientService.findById(id);
		currentClient.setName(client.getName());
		currentClient.setSurname(client.getSurname());
		currentClient.setEmail(client.getEmail());
		return clientService.save(currentClient);
	}
	
	@DeleteMapping("/clients/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteClient(@PathVariable Long id) {
		clientService.delete(id);
	}
}
