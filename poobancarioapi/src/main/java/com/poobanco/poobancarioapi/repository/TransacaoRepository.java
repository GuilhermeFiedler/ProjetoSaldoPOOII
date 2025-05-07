package com.poobanco.poobancarioapi.repository;

import com.poobanco.poobancarioapi.classes.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
