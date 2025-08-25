package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaChatGPT;

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
        this.sinopse = dadosSerie.sinopse(); //imprime sem traduzir

        //conecta com a api do chatgpt, e traduz a sinopse. Necessário ter crédito, e atualizar o api key na classe ConsultaChatGPt
        //this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                ", titulo=" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores=" + atores + '\'' +
                ", sinopse=" + sinopse + '\'' +
                ", poster=" + poster + '\''
                ;
    }
}
