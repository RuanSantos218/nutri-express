package br.com.nutriexpress.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import br.dtos.prato.AtualizarValorRequestDTO;
import br.com.nutriexpress.demo.service.PratoService;
import br.dtos.prato.PratoRequestDTO;
import br.dtos.prato.PratoResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    private final PratoService pratoService;

    // Injeção de dependência via construtor
    public PratoController(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    // GET /pratos  OU  GET /pratos?categoria=vegano
    @GetMapping
    public ResponseEntity<List<PratoResponseDTO>> listar(@RequestParam(required = false) String categoria) {
        if (categoria != null && !categoria.isBlank()) {
            return ResponseEntity.ok(pratoService.listarPorCategoria(categoria));
        }
        return ResponseEntity.ok(pratoService.listarTodos());
    }

    // GET /pratos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PratoResponseDTO> buscarPorId(@PathVariable Long id) {
        PratoResponseDTO prato = pratoService.buscarPorId(id);
        return ResponseEntity.ok(prato);
    }

        // GET /pratos/calorias?max=500
    @GetMapping("/calorias")
    public ResponseEntity<List<PratoResponseDTO>> listarPorCalorias(@RequestParam(name = "max") Integer max) {
        return ResponseEntity.ok(pratoService.listarPorCaloriasMaximas(max));
    }

    // POST /pratos
    @PostMapping
    public ResponseEntity<PratoResponseDTO> criar(@Valid @RequestBody PratoRequestDTO dto) {
        PratoResponseDTO pratoCriado = pratoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pratoCriado);
    }

    // PUT /pratos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PratoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PratoRequestDTO dto) {
        PratoResponseDTO pratoAtualizado = pratoService.atualizar(id, dto);
        return ResponseEntity.ok(pratoAtualizado);
    }

        // PATCH /pratos/{id}/valor
    @PatchMapping("/{id}/valor")
    public ResponseEntity<PratoResponseDTO> atualizarValor(
            @PathVariable Long id, 
            @Valid @RequestBody AtualizarValorRequestDTO dto) {
        PratoResponseDTO prato = pratoService.atualizarValor(id, dto.valor());
        return ResponseEntity.ok(prato);
    }

    // DELETE /pratos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pratoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}