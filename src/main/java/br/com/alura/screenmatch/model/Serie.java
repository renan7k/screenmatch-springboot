package br.com.alura.screenmatch.model;

import java.util.OptionalDouble;

//Classe para tratar as informações , converter dados , para exibir da melhor forma ao cliente
public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;  //usando um ENUM, pois as categorias sempre são padrões/constantes. Assim limitamos as opções
    private String atores;
    private String sinopse;
    private String poster;

    //construtor, que recebe DadosSerie, faz a associação de valores e transforma algumas informqções (avaliacao)
    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        //Para avaliacao Serie, estamos fazendo diferente de Episodio, e estamos usando a Optional
        //tentando salvar o q chega, caso nn der, setamos como 0
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero() //nn precisamos instanciar,pois é um metodo static
                .split(",")[0]  //quebrando a string em 3, separando por ",", e pegando apenas a primeira, posição 0
                .trim()); //retornando apenas os caracteres. Tudo que for espaço em branco, quebra de linha, vai ser removido
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = dadosSerie.sinopse();
    }

}
