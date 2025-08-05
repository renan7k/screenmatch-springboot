package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
