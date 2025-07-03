package com.devsu.msclientes.controller;

import com.devsu.msclientes.dto.ClienteDto;
import com.devsu.msclientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/obtener")
    @Operation(summary = "Obtener todos los cliente", description = "Retorna todos los cliente")
    public List<ClienteDto> getaAll(){
        return clienteService.findAll();
    };

    @GetMapping("/obtener/{personaId}")
    @Operation(summary = "Obtener cliente por ID", description = "Retorna el cliente según su identificador")
    public ClienteDto getById(@PathVariable Integer personaId){
        return clienteService.findById(personaId);
    };

    @PostMapping("/crear")
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente")
    public ClienteDto create(ClienteDto clienteDto){
        return clienteService.create(clienteDto);
    };

    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualiza cliente", description = "Actualiza un cliente")
    public ClienteDto update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto){
        return clienteService.update(id, clienteDto);
    };

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina cliente", description = "Elimina un cliente")
    public void delete(@PathVariable Integer id){
        clienteService.delete(id);
    }
}
