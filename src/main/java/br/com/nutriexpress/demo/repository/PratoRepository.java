package br.com.nutriexpress.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nutriexpress.demo.model.Prato;

public interface PratoRepository extends JpaRepository<Prato, Long> {

    List<Prato> findByCategoriaId(Long categoriaId);

    boolean existsByNomeAndCategoriaId(String nome, Long categoriaId);

    List<Prato> findByCaloriasLessThanEqual(Integer calorias);
    
}