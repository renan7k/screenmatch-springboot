package br.com.alura.desafios;

import java.util.*;
import java.util.stream.Collectors;

public class StreamSummaryStatistics {
    public static void main(String[] args) {
        //1 - Dada a lista de números inteiros a seguir, encontre o maior número dela.
        System.out.println("--------------Exercício 1----------------------");
        List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);
        IntSummaryStatistics est = numeros.stream()
                .collect(Collectors.summarizingInt(Integer::intValue));

        Optional<Integer> max = numeros.stream() //solução 2
                .max(Integer::compare);

        System.out.println("Array: " + numeros.toString());
        System.out.println("O maior número da lista é: " + est.getMax());
        max.ifPresent(System.out::println);

        //2 -Dada a lista de palavras (strings) abaixo, agrupe-as pelo seu tamanho.
        // No código a seguir, há um exemplo prático do resultado esperado.
        System.out.println("--------------Exercício 2----------------------");
        List<String> palavras = Arrays.asList("java", "stream", "lambda", "code");
        Map<Integer, List<String>> agrupamento = palavras.stream()
                .collect(Collectors.groupingBy(String::length, //groupingBy → cria um Map onde a chave é o valor retornado pelo String::length (Integer)
                        Collectors.toList())); //toList() → diz que o valor de cada chave vai ser uma lista de Strings

        Map<Integer, List<String>> agrupamento1 = palavras.stream() // solução 2
                .collect(Collectors.groupingBy(String::length));
        System.out.println(agrupamento);
        System.out.println(agrupamento1);

        //3 - Dada a lista de nomes abaixo, concatene-os separados por vírgula.
        // No código a seguir, há um exemplo prático do resultado esperado.
        System.out.println("--------------Exercício 3----------------------");
        List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");
        String resultado = nomes.stream()
                .collect(Collectors.joining(", "));
        
        System.out.println(resultado);

        //4 - Dada a lista de números inteiros abaixo, calcule a soma dos quadrados dos números pares.
        System.out.println("--------------Exercício 4----------------------");
        List<Integer> numeros1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        int somaDosQuadrados = numeros1.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);
        //0 → é o valor identidade, ou seja, o valor inicial da soma. Se a lista estiver vazia, o resultado será 0.
        //Integer::sum → é uma method reference que aponta para o metodo sum da classe Integer, que basicamente faz a + b.
        System.out.println("A soma dos quadrados dos números pares é: " + somaDosQuadrados);

        //5 - Dada uma lista de números inteiros, separe os números pares dos ímpares.
        System.out.println("--------------Exercício 5----------------------");
        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> particionado = numeros2.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));

        System.out.println("Pares: " + particionado.get(true));  // Esperado: [2, 4, 6]
        System.out.println("Ímpares: " + particionado.get(false)); // Esperado: [1, 3, 5]



        List<Produto1> produtos = Arrays.asList(
                new Produto1("Smartphone", 800.0, "Eletrônicos"),
                new Produto1("Notebook", 1500.0, "Eletrônicos"),
                new Produto1("Teclado", 200.0, "Eletrônicos"),
                new Produto1("Cadeira", 300.0, "Móveis"),
                new Produto1("Monitor", 900.0, "Eletrônicos"),
                new Produto1("Mesa", 700.0, "Móveis")
        );
        //6 - Dada a lista de produtos acima, agrupe-os por categoria em um Map<String, List<Produto>>.
        System.out.println("--------------Exercício 6----------------------");
        Map<String, List<Produto1>> listaPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto1::getCategoria,
                        Collectors.toList()));
        System.out.println(listaPorCategoria);

        //7 - Dada a lista de produtos acima, conte quantos produtos há em cada categoria e armazene em um Map<String, Long>.
        System.out.println("--------------Exercício 7----------------------");
        Map<String, Long> qtdProdutosPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto1::getCategoria,
                        Collectors.counting()));
        System.out.println(qtdProdutosPorCategoria);

        //8 - Dada a lista de produtos acima, encontre o produto mais caro de cada categoria e
        // armazene o resultado em um Map<String, Optional<Produto>>.
        System.out.println("--------------Exercício 8----------------------");
        Map<String, Optional<Produto1>> maisCaroPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto1::getCategoria,
                        Collectors.maxBy(Comparator.comparingDouble(Produto1::getPreco))));
        System.out.println(maisCaroPorCategoria);

        //9 - Dada a lista de produtos acima, calcule o total dos preços dos produtos em cada categoria
        // e armazene o resultado em um Map<String, Double>.
        System.out.println("--------------Exercício 9----------------------");
        Map<String, Double> valorTotalPorCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto1::getCategoria,
                        Collectors.summingDouble(Produto1::getPreco)));
        System.out.println(valorTotalPorCategoria);
    }
}

class Produto1 {
    private String nome;
    private double preco;
    private String categoria;

    public Produto1(String nome, double preco, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}