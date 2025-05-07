package com.poobanco.poobancarioapi.repository;

import com.poobanco.poobancarioapi.classes.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
}
