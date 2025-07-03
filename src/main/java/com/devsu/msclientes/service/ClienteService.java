package com.devsu.msclientes.service;

import com.devsu.msclientes.dto.ClienteDto;
import com.devsu.msclientes.exception.ClienteDuplicadoException;
import com.devsu.msclientes.mapper.ClienteMapper;
import com.devsu.msclientes.model.Cliente;
import com.devsu.msclientes.model.Persona;
import com.devsu.msclientes.repository.ClienteRepository;
import com.devsu.msclientes.repository.PersonaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final ClienteMapper clienteMapper;

    public List<ClienteDto> findAll() {
        try {
            return clienteRepository.obtenerClientesConDatosPersona();
        } catch (Exception ex) {
            throw new RuntimeException("Error al obtener la lista de clientes", ex);
        }
    }

    public ClienteDto findById(Integer personaId) {
        try {
            ClienteDto cliente = clienteRepository.findClienteDtoByPersonaId(personaId);
            if (cliente == null) {
                throw new RuntimeException("Cliente no encontrado con personaId: " + personaId);
            }
            return cliente;
        } catch (Exception ex) {
            throw new RuntimeException("Error al buscar cliente por personaId: " + personaId, ex);
        }
    }

    @Transactional
    public ClienteDto create(ClienteDto clienteDto) {
        try {
            Persona personaExistente = personaRepository.findByIdentificacion(clienteDto.getIdentificacion());
            if (personaExistente != null) {
                throw new ClienteDuplicadoException("Ya existe un cliente con esa identificación: " + clienteDto.getIdentificacion());
            }

            Persona persona = new Persona();
            persona.setNombre(clienteDto.getNombre());
            persona.setGenero(clienteDto.getGenero());
            persona.setDireccion(clienteDto.getDireccion());
            persona.setTelefono(clienteDto.getTelefono());
            persona.setIdentificacion(clienteDto.getIdentificacion());

            persona = personaRepository.save(persona);

            Cliente cliente = new Cliente();
            cliente.setPersona(persona);
            cliente.setPassword("1234");
            cliente.setEstado(clienteDto.getEstado());
            cliente = clienteRepository.save(cliente);

            return clienteRepository.obtenerClientesConDatosPersonaById(cliente.getId());
        } catch (Exception ex) {
            throw new RuntimeException("Error al crear cliente", ex);
        }
    }

    @Transactional
    public ClienteDto update(Integer id, ClienteDto clienteDto) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            Persona persona = cliente.getPersona();

            persona.setNombre(clienteDto.getNombre());
            persona.setGenero(clienteDto.getGenero());
            persona.setDireccion(clienteDto.getDireccion());
            persona.setTelefono(clienteDto.getTelefono());
            persona.setIdentificacion(clienteDto.getIdentificacion());
            personaRepository.save(persona);

            cliente.setPassword(clienteDto.getPassword());
            cliente.setEstado(clienteDto.getEstado());
            clienteRepository.save(cliente);

            return clienteRepository.obtenerClientesConDatosPersonaById(id);
        } catch (Exception ex) {
            throw new RuntimeException("Error al actualizar cliente con id: " + id, ex);
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            Persona persona = cliente.getPersona();
            clienteRepository.delete(cliente);
            personaRepository.delete(persona);
        } catch (Exception ex) {
            throw new RuntimeException("Error al eliminar cliente con id: " + id, ex);
        }
    }
}
