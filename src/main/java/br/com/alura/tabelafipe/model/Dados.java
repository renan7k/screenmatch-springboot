package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// nn usamos JsonAlias, pq o nome de retorno da api é igual
//Deixamos genérica, pois ela será aproveitada, tanto para marca, quanto modelo
public record Dados(String codigo,
                    String nome) {
}
