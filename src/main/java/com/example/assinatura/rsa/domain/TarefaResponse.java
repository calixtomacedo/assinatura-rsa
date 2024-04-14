package com.example.assinatura.rsa.domain;

import java.time.LocalDateTime;

public record TarefaResponse(String id, String nome, String descricao, LocalDateTime dataCadastro) {
}
