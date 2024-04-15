package com.example.assinatura.rsa.controller;

import com.example.assinatura.rsa.domain.Tarefa;
import com.example.assinatura.rsa.domain.TarefaRequest;
import com.example.assinatura.rsa.domain.TarefaResponse;
import com.example.assinatura.rsa.repository.TarefaRepository;
import com.example.assinatura.rsa.security.AssinaturaDigitalRSA;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    private final AssinaturaDigitalRSA rsa;

    private final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5nkja2IZzAfAxjPBZeN6K7wickxBwGALmylb8WqE6c94iWaUEk9x3fI5ymHzhTAKdV5XjiIkw7EuHHqi6fZHvAV2BV/AfV2N/4Gnkn/6Cb/U2/fuF6XnnRWeggDsJAgEbHcy53c8Zbd9OqhRO0NFugUJpO5e92R+ydqYyHMzJa+O6S7+/jf6oLs1nwyDb06INpMI1WktRvhep/V0r3O7EXICN8qKPOMnMTYVSmYOiQk60aEFskR9nEGspWpkRGvp57CbAZN9NV2prUKBf21VXPlT5+JGqWUPwEsAjjMFjyFUic7Xv4aPo7A+zcRBsoq8LG66Gh9UvRsXUrI4plWMoQIDAQAB";

    @PostMapping
    public ResponseEntity save(@RequestBody TarefaRequest request) throws Exception {
        boolean isValid = rsa.validaAssinatura(BASE64_PUBLIC_KEY);

        if (isValid) {
            var tarefa = new Tarefa();
            tarefa.setNome(request.nome());
            tarefa.setDescricao(request.descricao());
            tarefa.setDataCadastro(LocalDateTime.now());
            this.repository.save(tarefa);

            var response = new TarefaResponse(tarefa.getId(), tarefa.getNome(), tarefa.getDescricao(), tarefa.getDataCadastro());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assinatura Inválida");
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
