package com.devsu.msclientes.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "cliente", schema = "devsubd")
public class Cliente {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "estado", nullable = false)
    private Boolean estado = false;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;

}