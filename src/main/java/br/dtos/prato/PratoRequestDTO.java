package br.dtos.prato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PratoRequestDTO(
    @NotBlank(message = "O nome do prato é obrigatório.")
    @Size(max = 100, message = "O nome do prato deve ter no máximo 100 caracteres.")
    String nome,

    @Size(max = 255, message = "A descrição do prato deve ter no máximo 255 caracteres.")
    String descricao,

    @NotNull(message = "O preço do prato é obrigatório.")
    @Positive(message = "O preço do prato deve ser um valor positivo.")
    Double preco,

    @NotNull(message = "As calorias do prato são obrigatórias.")
    @Positive(message = "As calorias do prato devem ser um valor positivo.")
    Integer calorias,

    @NotNull(message = "A disponibilidade do prato é obrigatória.")
    Boolean disponivel,

    @NotNull(message = "O ID da categoria é obrigatório.")
    Long categoriaId
) {}