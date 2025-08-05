package br.com.alura.screenmatch.service;

public interface IConverteDados {

    //criamos a interface para criar um contrato do metodo obterDados, e não precisar especificar o tipo de retorno do metodo
    //Pois poderíamos precidar converter dados da serie, filmes, atores, etc.
    //E se chumbarmos na classe ConverteDados um tipo de retorno, precisaríamos criar um metodo para cada retorno
    //Para contornar isso, vamos usar o GENERICS, em que não especificamos o retorno do metodo
    //Só no momento de chamar, que vamos passar o json e informar o tipo de retorno
    <T> T obterDados(String json, Class<T> classe);
}
