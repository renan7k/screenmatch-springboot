package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaChatGPT;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
//Classe para tratar as informações , converter dados , para exibir da melhor forma ao cliente
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Categoria genero;  //usando um ENUM, pois as categorias sempre são padrões/constantes. Assim limitamos as opções
    private String atores;
    private String sinopse;
    private String poster;

    //1 série tem vários episodios, por isso temos que criar um relacionamento.
    //mappedBy = serie, indica o atributo da outra classe (episodi) que vai fazer o relacionamento
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //fetch = tipo de carregamento das entidades
    //cascade - indica que se modificar serie, modifica episodia , e vice versa
    private List<Episodio> episodios = new ArrayList<>();


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

    //construtor padrao, exigido pela JPA
    public Serie(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this)); //setando a chave estrangeira em episodios
        //o this acima, especifica que essa serie é dona do episódio
        this.episodios = episodios;
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
                ", poster=" + poster + '\'' +
                ", episodios=" + episodios + '\'';
    }
}
