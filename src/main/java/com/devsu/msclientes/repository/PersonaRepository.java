package com.devsu.msclientes.repository;

import com.devsu.msclientes.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{

}
