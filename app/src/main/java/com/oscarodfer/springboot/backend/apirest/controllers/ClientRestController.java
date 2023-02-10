package com.oscarodfer.springboot.backend.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscarodfer.springboot.backend.apirest.models.entities.Client;
import com.oscarodfer.springboot.backend.apirest.models.services.IClientService;

import jakarta.validation.Valid;

@CrossOrigin(origins = { "http://localhost:4200" })
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
	public ResponseEntity<?> getClientWithId(@PathVariable Long id) {
		Client client = null;
		Map<String, Object> response = new HashMap<>();

		try {
			client = clientService.findById(id);
		}

		catch (DataAccessException e) {
			response.put("message", "Error in the database query.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (client == null) {
			response.put("message",
					"The client with id ".concat(id.toString().concat(" does not exist in the database.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	@PostMapping("/clients")
	public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result) {
		Client newClient = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			newClient = clientService.save(client);
		}

		catch (DataAccessException e) {
			response.put("message", "Error while trying to insert a new client in the database.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "Client created successfully:");
		response.put("client", newClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/clients/{id}")
	public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult result,
			@PathVariable Long id) {
		Client currentClient = clientService.findById(id);
		Client updatedClient = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "The field '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (currentClient == null) {
			response.put("message", "Could not update, the client with id "
					.concat(id.toString().concat(" does not exist in the database.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			currentClient.setName(client.getName());
			currentClient.setSurname(client.getSurname());
			currentClient.setEmail(client.getEmail());
			currentClient.setCreatedAt(client.getCreatedAt());
			updatedClient = clientService.save(currentClient);
		}

		catch (DataAccessException e) {
			response.put("message", "Error while trying to update a client in the database.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "Client updated successfully:");
		response.put("client", updatedClient);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/clients/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			clientService.delete(id);
		}

		catch (DataAccessException e) {
			response.put("message", "Error while trying to delete client in the database.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "Client deleted successfully.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
