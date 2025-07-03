package com.devsu.msclientes.repository;

import com.devsu.msclientes.dto.ClienteDto;
import com.devsu.msclientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    @Query("SELECT new com.devsu.msclientes.dto.ClienteDto(" +
            "c.id, " +
            "c.persona.nombre, " +
            "c.persona.genero, " +
            "c.persona.direccion, " +
            "c.persona.telefono, " +
            "c.persona.identificacion, " +
            "c.password," +
            "c.persona.id, " +
            "c.estado) " +
            "FROM Cliente c WHERE c.persona.id = :personaId")
    ClienteDto findClienteDtoByPersonaId(@Param("personaId") Integer personaId);

    @Query("SELECT new com.devsu.msclientes.dto.ClienteDto(c.id, c.persona.nombre, c.persona.genero, c.persona.direccion, c.persona.telefono, c.persona.identificacion, c.password, c.persona.id, c.estado) FROM Cliente c")
    List<ClienteDto> obtenerClientesConDatosPersona();

    @Query("SELECT new com.devsu.msclientes.dto.ClienteDto(c.id, c.persona.nombre, c.persona.genero, c.persona.direccion, c.persona.telefono, c.persona.identificacion, c.password, c.persona.id, c.estado) " +
            "FROM Cliente c WHERE c.id = :idCliente")
    ClienteDto obtenerClientesConDatosPersonaById(@Param("idCliente") Integer idCliente);
}
