package br.com.alura.ScreenSound.principal;

import br.com.alura.ScreenSound.model.Artista;
import br.com.alura.ScreenSound.model.Musica;
import br.com.alura.ScreenSound.model.TipoArtista;
import br.com.alura.ScreenSound.repository.ArtistaRepository;
import br.com.alura.ScreenSound.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final ArtistaRepository repositorio;
    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {

    }

    private void pesquisarDadosDoArtista() {
        System.out.println("Pesquisar dados sobre qual artista? ");
        var nome = leitura.nextLine();
        var resposta = ConsultaChatGPT.obterInformacao(nome);
        System.out.println(resposta.trim());
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Buscar músicas de que artista? ");
        var nome = leitura.nextLine();
        List<Musica> musicas = repositorio.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar música de que artista? ");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()) {
            System.out.println("Informe o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else {
            System.out.println("Artista não encontrado");
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo = "S";
        while (cadastrarNovo.equalsIgnoreCase("s")){
            System.out.println("Informe o nome desse artista: ");
            var nome = leitura.nextLine();
            System.out.println("Informe o tipo desse artista (solo, dupla ou banda): ");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase()); //convertendo para enum
            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);

            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }
}
