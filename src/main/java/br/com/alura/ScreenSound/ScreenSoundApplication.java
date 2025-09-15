package br.com.alura.ScreenSound;

import br.com.alura.ScreenSound.principal.Principal;
import br.com.alura.ScreenSound.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//nn separamos em outro projeto, pois a ideia era apenas rever conceitos
//portanto, se executar, vai criar tabelas no banco do scrrenmatch (configurado no application.properties)

@SpringBootApplication
public class ScreenSoundApplication implements CommandLineRunner {

    @Autowired
    private ArtistaRepository repositorio;

    public static void main(String[] args) {
        SpringApplication.run(ScreenSoundApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorio); //passando o repositoria para a classe principal
        principal.exibeMenu();
    }
}
