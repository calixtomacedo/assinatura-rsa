package com.example.assinatura.rsa.repository;

import com.example.assinatura.rsa.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, String> {
}
