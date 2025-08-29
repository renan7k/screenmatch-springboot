package br.com.alura.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

//No projeto já temos a DadosEpisodio como Record, mas ela existe principalmente por conta do retorno da api, para mapear os dados
//Pensando em negócio, vamos criar uma classe Episodio , pois vamos ter métodos, validações, tratamentos, e provével que se torne uma entidade no nosso banco de dados
@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    //criando atributo serie, para informar também a relação para o JPA
    //no banco, devido ao relacionamento, vai ser criado uma chave estrangeira serie_id
    @ManyToOne
    private Serie serie;


    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodios) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numeroEpisodio = dadosEpisodios.numero();

        //Temos que fazer tratamento, pois estava estourando erro para os episódios que possuíam N/A por exemplo:
        try{
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao()); //Coversão: Lê de uma string e converte para o double
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento()); // o parse espera que esteja no padrão ISO_LOCAL_DATE
        } catch (DateTimeParseException e) {
            //this.dataLancamento = LocalDate.of(2000, 01, 01);
            this.dataLancamento = null;
        }

    }
    public Episodio (){ };
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo=" + titulo +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }
}
