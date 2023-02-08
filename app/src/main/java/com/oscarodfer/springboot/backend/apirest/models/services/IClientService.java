package com.oscarodfer.springboot.backend.apirest.models.services;

import java.util.List;

import com.oscarodfer.springboot.backend.apirest.models.entities.Client;

public interface IClientService {

	public List<Client> findAll();

	public Client findById(Long id);

	public Client save(Client client);

	public void delete(Long id);

}