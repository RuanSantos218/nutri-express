package br.com.nutriexpress.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.nutriexpress.demo.model.Categoria;
import br.com.nutriexpress.demo.model.Prato;
import br.com.nutriexpress.demo.repository.CategoriaRepository;
import br.com.nutriexpress.demo.repository.PratoRepository;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CategoriaRepository categoriaRepository, PratoRepository pratoRepository) {
        return args -> {
            // Garante as categorias cadastradas
            Categoria saladas = categoriaRepository.findByNomeIgnoreCase("Saladas e Bowls")
                    .orElseGet(() -> categoriaRepository.save(new Categoria(null, "Saladas e Bowls", "Saladas leves e nutritivas")));

            Categoria lanches = categoriaRepository.findByNomeIgnoreCase("Lanches Saudáveis")
                    .orElseGet(() -> categoriaRepository.save(new Categoria(null, "Lanches Saudáveis", "Sanduíches e wraps funcionais")));

            Categoria principais = categoriaRepository.findByNomeIgnoreCase("Pratos Principais")
                    .orElseGet(() -> categoriaRepository.save(new Categoria(null, "Pratos Principais", "Refeições completas e balanceadas")));

            Categoria massas = categoriaRepository.findByNomeIgnoreCase("Massas Integrais")
                    .orElseGet(() -> categoriaRepository.save(new Categoria(null, "Massas Integrais", "Massas funcionais de baixo índice glicêmico")));

            Categoria sobremesas = categoriaRepository.findByNomeIgnoreCase("Sobremesas Fit")
                    .orElseGet(() -> categoriaRepository.save(new Categoria(null, "Sobremesas Fit", "Doces nutritivos sem açúcar refinado")));

            log.info("Categorias verificadas com sucesso.");

            // Se ainda não houver pratos cadastrados, cadastra os 6 pratos com diferentes categorias e calorias
            if (pratoRepository.count() == 0) {
                // 1. Salada Verde Detox (180 kcal) - Baixíssima caloria
                Prato p1 = new Prato();
                p1.setNome("Salada Verde Detox");
                p1.setDescricao("Mix de folhas verdes, pepino, tomate cereja, sementes de girassol e molho cítrico");
                p1.setPreco(24.90);
                p1.setCalorias(180);
                p1.setDisponivel(true);
                p1.setCategoriaId(saladas.getId());
                pratoRepository.save(p1);

                // 2. Mousse de Cacau com Frutas Vermelhas (210 kcal) - Baixa caloria
                Prato p2 = new Prato();
                p2.setNome("Mousse de Cacau Fit");
                p2.setDescricao("Mousse 70% cacau com abacate, leite vegetal e calda de frutas vermelhas");
                p2.setPreco(18.00);
                p2.setCalorias(210);
                p2.setDisponivel(true);
                p2.setCategoriaId(sobremesas.getId());
                pratoRepository.save(p2);

                // 3. Wrap de Frango com Ricota (320 kcal) - Média-baixa caloria
                Prato p3 = new Prato();
                p3.setNome("Wrap de Frango com Ricota");
                p3.setDescricao("Massa integral, peito de frango desfiado, ricota fresca e cenoura ralada");
                p3.setPreco(28.50);
                p3.setCalorias(320);
                p3.setDisponivel(true);
                p3.setCategoriaId(lanches.getId());
                pratoRepository.save(p3);

                // 4. Poke de Salmão com Quinoa (450 kcal) - Média caloria (abaixo de 500)
                Prato p4 = new Prato();
                p4.setNome("Poke de Salmão com Quinoa");
                p4.setDescricao("Cubos de salmão fresco, quinoa real, edamame, manga e gergelim tostado");
                p4.setPreco(46.00);
                p4.setCalorias(450);
                p4.setDisponivel(true);
                p4.setCategoriaId(saladas.getId());
                pratoRepository.save(p4);

                // 5. Filé de Tilápia com Purê de Mandioquinha (520 kcal) - Acima de 500 kcal
                Prato p5 = new Prato();
                p5.setNome("Filé de Tilápia com Mandioquinha");
                p5.setDescricao("Tilápia grelhada, purê de mandioquinha cremoso e brócolis ao vapor");
                p5.setPreco(39.90);
                p5.setCalorias(520);
                p5.setDisponivel(true);
                p5.setCategoriaId(principais.getId());
                pratoRepository.save(p5);

                // 6. Spaghetti Integral à Bolonhesa (650 kcal) - Alta caloria
                Prato p6 = new Prato();
                p6.setNome("Spaghetti Integral à Bolonhesa");
                p6.setDescricao("Massa grano duro integral com molho de tomate rústico e patinho moído");
                p6.setPreco(42.00);
                p6.setCalorias(650);
                p6.setDisponivel(true);
                p6.setCategoriaId(massas.getId());
                pratoRepository.save(p6);

                log.info("6 Pratos pré-carregados no banco com sucesso!");
            }
        };
    }
}
