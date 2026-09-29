package br.com.nutriexpress.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.nutriexpress.demo.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    Optional<Categoria> findByNomeIgnoreCase(String nome);
    
}
