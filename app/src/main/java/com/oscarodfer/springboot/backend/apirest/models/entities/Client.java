package com.oscarodfer.springboot.backend.apirest.models.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@Table(name = "clients")
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	@NotEmpty(message="can not be empty.")
	@Size(min=4, max=20, message="must have a length between 4 and 20 characters.")
	private String name;
	
	@Column(nullable=false)
	@NotEmpty(message="can not be empty.")
	private String surname;
	
	@Column(nullable=false, unique=true)
	@NotEmpty(message="can not be empty.")
	@Email(message="must be typed in a correct email format.")
	private String email;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

	private static final long serialVersionUID = 1L;
}