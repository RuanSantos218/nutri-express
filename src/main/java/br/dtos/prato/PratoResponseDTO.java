package br.dtos.prato;

import br.com.nutriexpress.demo.model.Prato;

public record PratoResponseDTO(
    Long id,
    String nome,
    String descricao,
    Double preco,
    Integer calorias,
    Boolean disponivel,
    Long categoriaId
) {
    public PratoResponseDTO(Prato prato) {
       this(
        prato.getId(),
        prato.getNome(),
        prato.getDescricao(),
        prato.getPreco(),
        prato.getCalorias(),
        prato.getDisponivel(),
        prato.getCategoriaId()
       );
    
    }

    // Getters e Setters
}