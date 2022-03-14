package br.com.lucasladeira.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasladeira.entities.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, UUID>{

	Cambio findByFromAndTo(String from, String to);
}
