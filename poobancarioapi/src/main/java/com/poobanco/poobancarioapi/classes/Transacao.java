package com.poobanco.poobancarioapi.classes;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Transacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private BigDecimal valor;

    private LocalDateTime data;

    @ManyToOne
    private Conta conta;
}
