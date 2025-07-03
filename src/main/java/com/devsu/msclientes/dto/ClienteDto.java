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
    private String password;
    private Integer clientId;
    private Boolean estado;
}