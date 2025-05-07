package com.poobanco.poobancarioapi.service;

import com.poobanco.poobancarioapi.classes.Conta;
import com.poobanco.poobancarioapi.classes.Transacao;
import com.poobanco.poobancarioapi.classes.Usuario;
import com.poobanco.poobancarioapi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario.getConta() == null) {
            Conta conta = new Conta();
            conta.setSaldo(BigDecimal.ZERO);
            conta.setUsuario(usuario);
            usuario.setConta(conta);
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public BigDecimal getSaldo(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        return usuario.getConta().getSaldo();
    }

    @Transactional
    public void depositar(Long id, BigDecimal valor) {
        Usuario usuario = buscarUsuarioPorId(id);
        Conta conta = usuario.getConta();
        conta.setSaldo(conta.getSaldo().add(valor));

        Transacao transacao = new Transacao();
        transacao.setTipo("DEPÓSITO");
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now());
        transacao.setConta(conta);

        conta.getTransacoes().add(transacao);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void sacar(Long id, BigDecimal valor) {
        Usuario usuario = buscarUsuarioPorId(id);
        Conta conta = usuario.getConta();

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));

        Transacao transacao = new Transacao();
        transacao.setTipo("SAQUE");
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now());
        transacao.setConta(conta);

        conta.getTransacoes().add(transacao);
        usuarioRepository.save(usuario);
    }

    public List<Transacao> extrato(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        return usuario.getConta().getTransacoes();
    }

    private Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}