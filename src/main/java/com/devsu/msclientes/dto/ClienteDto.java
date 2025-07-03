package com.devsu.msclientes.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class ClienteDto implements Serializable {
    private Integer id;
    private String nombre;
    private String genero;
    private String direccion;
    private String telefono;
    private String identificacion;
    private Integer clientId;
    private Boolean estado;

    public ClienteDto(Integer id, String nombre, String genero, String direccion, String telefono, String identificacion, Integer clientId, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.direccion = direccion;
        this.telefono = telefono;
        this.identificacion = identificacion;
        this.clientId = clientId;
        this.estado = estado;
    }
}