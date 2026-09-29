package br.com.nutriexpress.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.nutriexpress.demo.exception.PratoNaoEncontradoException;
import br.com.nutriexpress.demo.model.Categoria;
import br.com.nutriexpress.demo.model.Prato;
import br.com.nutriexpress.demo.repository.CategoriaRepository;
import br.com.nutriexpress.demo.repository.PratoRepository;
import br.dtos.prato.PratoRequestDTO;
import br.dtos.prato.PratoResponseDTO;

@Service
public class PratoService {

    private final PratoRepository pratoRepository;
    private final CategoriaRepository categoriaRepository;

    // Injeção de dependências via construtor
    public PratoService(PratoRepository pratoRepository, CategoriaRepository categoriaRepository) {
        this.pratoRepository = pratoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public PratoResponseDTO criar(PratoRequestDTO dto) {
        // Validação da categoria
        if (!categoriaRepository.existsById(dto.categoriaId())) {
            throw new RuntimeException("Categoria não encontrada com o ID: " + dto.categoriaId());
        }

        // =========================================================================
        // REGRA DE NEGÓCIO: Não permitir o cadastro de dois pratos com o mesmo nome
        // dentro da mesma categoria.
        // Justificativa: Evita pratos duplicados no mesmo cardápio/seção.
        // =========================================================================
        if (pratoRepository.existsByNomeAndCategoriaId(dto.nome(), dto.categoriaId())) {
            throw new RuntimeException("Já existe um prato com o nome '" + dto.nome() + "' nesta categoria.");
        }

        Prato prato = toEntity(dto);
        Prato pratoSalvo = pratoRepository.save(prato);
        return toDTO(pratoSalvo);
    }

    @Transactional(readOnly = true)
    public List<PratoResponseDTO> listarTodos() {
        return pratoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PratoResponseDTO buscarPorId(Long id) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new PratoNaoEncontradoException(id));
        return toDTO(prato);
    }

    @Transactional(readOnly = true)
    public List<PratoResponseDTO> listarPorCategoria(String categoria) {
        // Busca a categoria pelo nome (ex: "Bebidas", "Sobremesas")
        Optional<Categoria> catOpt = categoriaRepository.findByNomeIgnoreCase(categoria);

        Long categoriaId;
        if (catOpt.isPresent()) {
            categoriaId = catOpt.get().getId();
        } else {
            // Caso o chamador passe o próprio ID em formato de texto (ex: "1")
            try {
                categoriaId = Long.parseLong(categoria);
            } catch (NumberFormatException e) {
                return List.of();
            }
        }

        return pratoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PratoResponseDTO> listarPorCaloriasMaximas(Integer max) {
        return pratoRepository.findByCaloriasLessThanEqual(max)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public PratoResponseDTO atualizar(Long id, PratoRequestDTO dto) {
        Prato pratoExistente = pratoRepository.findById(id)
                .orElseThrow(() -> new PratoNaoEncontradoException(id));

        // Validação da categoria
        if (!categoriaRepository.existsById(dto.categoriaId())) {
            throw new RuntimeException("Categoria não encontrada com o ID: " + dto.categoriaId());
        }

        // REGRA DE NEGÓCIO: Se o nome ou a categoria foram alterados, valida se já não existe outro prato igual
        boolean nomeMudou = !pratoExistente.getNome().equalsIgnoreCase(dto.nome());
        boolean categoriaMudou = !pratoExistente.getCategoriaId().equals(dto.categoriaId());
        if ((nomeMudou || categoriaMudou) && pratoRepository.existsByNomeAndCategoriaId(dto.nome(), dto.categoriaId())) {
            throw new RuntimeException("Já existe um prato com o nome '" + dto.nome() + "' nesta categoria.");
        }

        pratoExistente.setNome(dto.nome());
        pratoExistente.setDescricao(dto.descricao());
        pratoExistente.setPreco(dto.preco());
        pratoExistente.setCalorias(dto.calorias());
        pratoExistente.setDisponivel(dto.disponivel());
        pratoExistente.setCategoriaId(dto.categoriaId());

        Prato pratoAtualizado = pratoRepository.save(pratoExistente);
        return toDTO(pratoAtualizado);
    }

    @Transactional
    public PratoResponseDTO atualizarValor(Long id, Double novoValor) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new PratoNaoEncontradoException(id));

        // Atualiza SOMENTE o preço (valor), mantendo todos os outros atributos intactos
        prato.setPreco(novoValor);

        Prato pratoAtualizado = pratoRepository.save(prato);
        return toDTO(pratoAtualizado);
    }

    @Transactional
    public void remover(Long id) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new PratoNaoEncontradoException(id));
        pratoRepository.delete(prato);
    }

    // =========================================================================
    // Métodos privados de conversão (Entity <-> DTO) exigidos pelo professor
    // =========================================================================
    private Prato toEntity(PratoRequestDTO dto) {
        Prato prato = new Prato();
        prato.setNome(dto.nome());
        prato.setDescricao(dto.descricao());
        prato.setPreco(dto.preco());
        prato.setCalorias(dto.calorias());
        prato.setDisponivel(dto.disponivel());
        prato.setCategoriaId(dto.categoriaId());
        return prato;
    }

    private PratoResponseDTO toDTO(Prato prato) {
        return new PratoResponseDTO(prato);
    }
}

