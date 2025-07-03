package com.devsu.msclientes.service;

import com.devsu.msclientes.dto.ClienteDto;
import com.devsu.msclientes.mapper.ClienteMapper;
import com.devsu.msclientes.model.Cliente;
import com.devsu.msclientes.model.Persona;
import com.devsu.msclientes.repository.ClienteRepository;
import com.devsu.msclientes.repository.PersonaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final ClienteMapper clienteMapper;

    public List<ClienteDto> findAll(){
        List<ClienteDto> clienteDto = clienteRepository.obtenerClientesConDatosPersona();
        return clienteDto;
    }

    public ClienteDto findById(Integer personaId){
        Cliente cliente = clienteRepository.findByPersonaId(personaId);
        return clienteMapper.toDto(cliente);
    }

    @Transactional
    public ClienteDto create(ClienteDto clienteDto){
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
    }

    @Transactional
    public ClienteDto update(Integer id, ClienteDto clienteDto){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Persona persona = cliente.getPersona();

        persona.setNombre(clienteDto.getNombre());
        persona.setGenero(clienteDto.getGenero());
        persona.setDireccion(clienteDto.getDireccion());
        persona.setTelefono(clienteDto.getTelefono());
        persona.setIdentificacion(clienteDto.getIdentificacion());
        personaRepository.save(persona);

        cliente.setEstado(clienteDto.getEstado());
        clienteRepository.save(cliente);

        return clienteRepository.obtenerClientesConDatosPersonaById(id);
    }

    @Transactional
    public void delete(Integer id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Persona persona = cliente.getPersona();
        clienteRepository.delete(cliente);
        personaRepository.delete(persona);
    }
}
