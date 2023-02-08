package com.oscarodfer.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.oscarodfer.springboot.backend.apirest.models.entities.Client;

public interface IClientDao extends CrudRepository<Client, Long> {

}