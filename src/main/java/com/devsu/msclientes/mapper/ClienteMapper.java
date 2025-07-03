package com.devsu.msclientes.mapper;

import com.devsu.msclientes.dto.ClienteDto;
import com.devsu.msclientes.model.Cliente;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toEntity(ClienteDto clienteDto);

    ClienteDto toDto(Cliente cliente);

    List<ClienteDto> toDtoList(List<Cliente> cliente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cliente partialUpdate(ClienteDto clienteDto, @MappingTarget Cliente cliente);
}