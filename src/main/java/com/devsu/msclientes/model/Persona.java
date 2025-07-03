package com.devsu.msclientes.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "persona", schema = "devsubd")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "genero", nullable = false, length = 45)
    private String genero;

    @Column(name = "direccion", nullable = false, length = 45)
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 45)
    private String telefono;

    @Column(name = "identificacion", nullable = false, length = 45)
    private String identificacion;

}