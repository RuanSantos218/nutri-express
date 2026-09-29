package br.com.nutriexpress.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pratos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Prato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;
    
    @Column(nullable = false)
    private Double preco;
    @Column(nullable = false)
    private Integer calorias;
    @Column(nullable = false)
    private Boolean disponivel;
    @Column(nullable = false)
    private Long categoriaId;

    public Prato(String nome, String descricao, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

}
