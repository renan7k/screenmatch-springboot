package br.com.alura.tabelafipe.service;

import java.util.List;

public interface iConverteDados {
    //estrutura genérica que irá converter dados de uma classe para a classe que escolhermos.(Generics)
    <T> T obterDados (String json, Class<T> classe);

    //Preparando a interface, para possuir um metodo q aceite uma lista como um retorno.
    //Em screenMatch, retonava apenas 1 série, então o metodo acima era suficiente
    //Já a primeira consulta dessa aplicação, retorna uma lista de marcas e posteriormente modelos
    <T> List<T> obterLista(String json, Class<T> classe);

}
