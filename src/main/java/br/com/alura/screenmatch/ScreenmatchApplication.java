package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
//É uma aplicação por linha de comando, portanto, é necessário implementar "CommandLineRunner"
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//A interface CommandLineRunner representa uma tarefa a ser executada após a inicialização do Spring Boot,
	// ou seja, permite definir código para ser executado automaticamente quando o aplicativo é iniciado.
	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?apikey=fcf2f961&t=gilmore+girls");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
			//como queremos que o retorno da api seja um DadosSerie, criamos uma variável que recebe o json transformado
		//No caso enviamos o retorno da api, e qual o tipo de retorno queremos
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		//Dados episodio
		json = consumoAPI.obterDados("https://www.omdbapi.com/?apikey=fcf2f961&t=gilmore+girls&Season=1&episode=2");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();
		//Dados temporada, for com a qtd de temp exibidas em dadosSerie
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json = consumoAPI.obterDados("https://www.omdbapi.com/?apikey=fcf2f961&t=gilmore+girls&Season=" + i);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
