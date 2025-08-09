package br.com.alura.desafios;

import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        //1 - Dada a lista de números inteiros abaixo, filtre apenas os números pares e imprima-os.
        System.out.println("---------------------------EXERCÍCIO 1-----------------------------");
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println("Dado o array: " + numeros.toString() + " , os números pares são: ");
        numeros.stream()
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);
        System.out.println("---------------------------EXERCÍCIO 2-----------------------------");
        //2 - Dada a lista de strings abaixo, converta todas para letras maiúsculas e imprima-as.
        List<String> palavras = Arrays.asList("java", "stream", "lambda");
        System.out.println("Array convertido para letras maiúsculas: ");
        palavras.stream()
                .map(String::toUpperCase) // poderia ser com lambda: .map(s -> s.toUpperCase())
                .forEach(System.out::println);
        System.out.println("---------------------------EXERCÍCIO 3-----------------------------");
        //3 - Dada a lista de números inteiros abaixo, filtre os números ímpares,
        // multiplique cada um por 2 e colete os resultados em uma nova lista.
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println("Array original: " + numbers.toString());
        List<Integer> novaLista = numbers.stream()
                .filter(n -> n % 2 != 0) // filtra apenas os ímpares
                .map(n -> n * 2)
                .collect(Collectors.toList()); // retorna uma nova lista
        System.out.println("Array filtrado e transformado: " + novaLista.toString());
        System.out.println("---------------------------EXERCÍCIO 4-----------------------------");
        //4 - Dada a lista de strings abaixo, remova as duplicatas (palavras que aparecem mais de uma vez) e imprima o resultado.
        List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        System.out.println("Array com remoção das palavras duplicadas:");
        words.stream()
                .distinct()
                .forEach(System.out::println);
        System.out.println("---------------------------EXERCÍCIO 5-----------------------------");
        //5 - Dada a lista de sublistas de números inteiros abaixo, extraia todos os números primos em
        // uma única lista e os ordene em ordem crescente.
        List<List<Integer>> listaDeNumeros = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(5, 6, 7, 8),
                Arrays.asList(9, 10, 11, 12)
        );
        //transformando listas de listas em uma nova e única lista
        List<Integer> newList = listaDeNumeros.stream()
                .flatMap(n -> n.stream())
                .filter(Stream::ehPrimo)  // Filtrar apenas números primos, usando o metodo criado
                .sorted()
                .collect(Collectors.toList());
        System.out.println(newList.toString());
        System.out.println("---------------------------EXERCÍCIO 6-----------------------------");
        //6 - Dado um objeto Pessoa com os campos nome e idade, filtre as pessoas com mais de 18 anos,
        // extraia os nomes e imprima-os em ordem alfabética. A classe Pessoa está definida abaixo.
        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("Alice", 22),
                new Pessoa("Bob", 17),
                new Pessoa("Charlie", 19)
        );
        pessoas.stream()
                .filter(p -> p.idade >= 18)
                .map(p -> p.getNome()) //poderia ter usado: .map(Pessoa::getNome)
                .sorted()
                .forEach(System.out::println);
        System.out.println("---------------------------EXERCÍCIO 7-----------------------------");
        //7 - Você tem uma lista de objetos do tipo Produto, onde cada produto possui os atributos nome (String),
        // preco (double) e categoria (String). Filtre todos os produtos da categoria "Eletrônicos"
        // com preço menor que R$ 1000, ordene-os pelo preço em ordem crescente e colete o resultado em uma nova lista.
        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Teclado", 200.0, "Eletrônicos"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Monitor", 900.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis")
        );

        Comparator<? super Produto> Comparator;
        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase("Eletrônicos"))
                .filter(p -> p.getPreco() <= 1000)
                .sorted((p1, p2) -> Double.compare(p1.getPreco(), p2.getPreco()))
                .collect(Collectors.toList());
        System.out.println(produtosFiltrados.toString());

    }

    private static boolean ehPrimo(int numero) {
        if (numero < 2) return false; // Números menores que 2 não são primos
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false; // Divisível por outro número que não 1 e ele mesmo
            }
        }
        return true;
    }
}
class Pessoa {
    String nome;
    int idade;

    Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}

class Produto {
    private String nome;
    private double preco;
    private String categoria;

    public Produto(String nome, double preco, String categoria) {
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