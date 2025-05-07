package com.poobanco.poobancarioapi.controller;

import com.poobanco.poobancarioapi.classes.Transacao;
import com.poobanco.poobancarioapi.classes.Usuario;
import com.poobanco.poobancarioapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid Usuario usuario) {
        Usuario salvo = service.cadastrarUsuario(usuario);
        return ResponseEntity.status(201).body(salvo);
        //return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario); //testar
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return service.listarUsuarios();
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok((BigDecimal) service.getSaldo(id));
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        service.depositar(id, valor);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        service.sacar(id, valor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<Transacao>> extrato(@PathVariable Long id) {
        return ResponseEntity.ok((List<Transacao>) service.extrato(id));
    }
}
