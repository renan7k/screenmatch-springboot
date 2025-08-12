package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//a api q busca por modelos, retorna 2 chaves: modelos e ano.
//A lista que queremos, está dentro de modelos, e possui a mesma estrutura de Dados (codigo, nome)

@JsonIgnoreProperties(ignoreUnknown = true) //ignorando a propriedade do response "anos"
public record Modelos(List<Dados> modelos) {
}
