package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
//É uma aplicação por linha de comando, portanto, é necessário implementar "CommandLineRunner"

	@Autowired //injeção de dependência da interface
	private SerieRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//A interface CommandLineRunner representa uma tarefa a ser executada após a inicialização do Spring Boot,
	// ou seja, permite definir código para ser executado automaticamente quando o aplicativo é iniciado.
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();

	}
}
