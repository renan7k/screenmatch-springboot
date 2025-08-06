package br.com.alura.desafios;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Multiplicacao { int multiplicacao(int a, int b);}
@FunctionalInterface
interface Primo { boolean verificarPrimo(int n);}
interface Transformador { String transformar(String s);}
@FunctionalInterface
interface Palindromo { boolean verificarPalindromo(String str);}

interface Divisor {
    int dividir(int a, int b) throws ArithmeticException;
}

public class FunctionLambda {
    public static void main(String[] args) {
        //1 - Crie uma expressão lambda que multiplique dois números inteiros.
        // A expressão deve ser implementada dentro de uma interface funcional com o metodo multiplicacao(int a, int b).
        Multiplicacao multiplicacao = (a, b) -> a * b;
        System.out.println(multiplicacao.multiplicacao(10, 5));

        // 2 - Implemente uma expressão lambda que verifique se um número é primo.
        Primo primo = n -> {
            if (n <= 1) return false;
            for (int i = 2; i <= Math.sqrt(n); i++){
                if (n % i == 0) return false;
            }
            return true;
        };
        System.out.println(primo.verificarPrimo(11));
        System.out.println(primo.verificarPrimo(12));

        //3 - Crie uma função lambda que receba uma string e a converta para letras maiúsculas.
        //Transformador toUpperCase = s -> s.toUpperCase();
        Transformador toUpperCase = String::toUpperCase;
        System.out.println(toUpperCase.transformar("java"));

        //4 - Crie uma expressão lambda que verifique se uma string é um palíndromo. A expressão deve ser implementada dentro de uma interface funcional com o metodo boolean verificarPalindromo(String str).
        // Dica: utilize o metodo reverse da classe StringBuilder.
        Palindromo palindromo = str -> str.equals(new StringBuilder(str).reverse().toString());
        System.out.println(palindromo.verificarPalindromo("radar"));  // Resultado: true
        System.out.println(palindromo.verificarPalindromo("java"));   // Resultado: false

        //5 - Implemente uma expressão lambda que recebe uma lista de inteiros e retorna uma nova lista onde cada número foi multiplicado por 3.
        // Dica: a função replaceAll, das Collections, recebe uma interface funcional como parâmetro, assim como vimos na função forEach.
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        numeros.replaceAll(n -> n * 3);
        System.out.println(numeros);

        //6 - Crie uma expressão lambda que ordene uma lista de strings em ordem alfabética.
        // Dica: a função sort, das Collections, recebe uma interface funcional como parâmetro, assim como vimos na função forEach.
        List<String> nomes = Arrays.asList("Renan", "Abner", "Thaís", "Carlos");
        nomes.sort((a, b) -> a.compareTo(b));
        System.out.println(nomes);

        //7 - Crie uma função lambda que recebe dois números e divide o primeiro pelo segundo.
        // A função deve lançar uma exceção de tipo ArithmeticException se o divisor for zero.
        Divisor divisor = (a, b) -> {
            if (b == 0) throw new ArithmeticException("Divisão por zero");
            return a / b;
        };
        try {
            System.out.println(divisor.dividir(10, 2)); // Esperado: 5
            System.out.println(divisor.dividir(10, 0)); // Esperado: Exceção
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
