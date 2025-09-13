package br.com.alura.ScreenSound.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  //CONFIGURANDO COMO ÚNICO
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoArtista tipo;

    @OneToMany(mappedBy = "artista") //estabelecendo quem está mapeando a lista de música no relacionamento bidirecional
    private List<Musica> musicas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    public void setTipo(TipoArtista tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    @Override
    public String toString() {
        return
                "Artista = " + nome + '\'' +
                ", tipo=" + tipo +
                ", musicas=" + musicas;
    }
}
