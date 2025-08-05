package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Usando record pois não é uma classe que vamos criar metodos, etc, serve apenas como representacão de dados
//E o JsonAlias, serve como um "apelido" para identificar que titulo corresponde ao Title do Json de resposta da api

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {
}
