package com.example.assinatura.rsa.controller;

import com.example.assinatura.rsa.domain.Tarefa;
import com.example.assinatura.rsa.domain.TarefaRequest;
import com.example.assinatura.rsa.domain.TarefaResponse;
import com.example.assinatura.rsa.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaRepository repository;

    @PostMapping
    public ResponseEntity<TarefaResponse> save(@RequestBody TarefaRequest request) {
        var tarefa = new Tarefa();
        tarefa.setNome(request.nome());
        tarefa.setDescricao(request.descricao());
        tarefa.setDataCadastro(LocalDateTime.now());
        this.repository.save(tarefa);

        var response = new TarefaResponse(tarefa.getId(), tarefa.getNome(), tarefa.getDescricao(), tarefa.getDataCadastro());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    private ResponseEntity<List<TarefaResponse>> listAll() {
        var responseList = new ArrayList<TarefaResponse>();
        var tarefaList = this.repository.findAll();
        tarefaList.forEach(tar -> {
            var tarefa = new TarefaResponse(tar.getId(), tar.getNome(), tar.getDescricao(), tar.getDataCadastro());
            responseList.add(tarefa);
        });
        return ResponseEntity.ok(responseList);
    }
}
