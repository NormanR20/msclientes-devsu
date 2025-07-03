package com.devsu.msclientes;

import com.devsu.msclientes.dto.ClienteDto;
import com.devsu.msclientes.mapper.ClienteMapper;
import com.devsu.msclientes.model.Cliente;
import com.devsu.msclientes.model.Persona;
import com.devsu.msclientes.repository.ClienteRepository;
import com.devsu.msclientes.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(ClienteMapper.class)
class ClienteRepositoryTest {

//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private PersonaRepository personaRepository;
//
//    @Test
//    void testFindByClientId() {
//        Persona persona = new Persona();
//        persona.setNombre("testuser");
//        persona.setGenero("M");
//        persona.setDireccion("test direccion");
//        persona.setTelefono("9999999");
//        persona.setIdentificacion("ABC123");
//        personaRepository.save(persona);
//
//        Cliente cliente = new Cliente();
//        cliente.setPassword("pass");
//        cliente.setEstado(true);
//        cliente.setPersona(persona);
//        clienteRepository.save(cliente);
//
//        ClienteDto result = clienteRepository.obtenerClientesConDatosPersonaById(cliente.getId());
//
//        assertThat(result).isNotNull();
//        assertThat(result.getNombre()).isEqualTo("testuser");
//    }

}
