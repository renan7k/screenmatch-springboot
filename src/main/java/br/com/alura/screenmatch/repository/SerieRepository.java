package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

//extendendo de jpaRepository para herdar metodos de crud
//e temos que passar a entidade, e o tipo da chave primária
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
