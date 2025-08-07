package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=fcf2f961";


    public void exibeMenu(){
        System.out.println("Digite o nome da série: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        //como queremos que o retorno da api seja um DadosSerie, criamos uma variável que recebe o json transformado
        //No caso enviamos o retorno da api, e qual o tipo de retorno queremos
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

		List<DadosTemporada> temporadas = new ArrayList<>();
		//Dados temporada, for com a qtd de temp exibidas em dadosSerie
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
        //Para simplificar o for de cima, vamos usar um recurso do java 8: function lambda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//        System.out.println("---------Exemplo de stream ------------------------------");
//        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico");
//        nomes.stream()
//                .sorted() //ordenando
//                .limit(3)//limitando aos 3 primeiros
//                .filter(n -> n.startsWith("N")) //dos 3, pegar apenas quem começa com N
//                .map( n -> n.toUpperCase())
//                .forEach(System.out::println);

        //O desafio agr é elencar os 5 melhores episodios da série. Mas a estrututa no momento é, Série tem uma lista de temporada, e cada temporada tem uma lista de episódios
        //Para nn precisar ficar percorrendo em mais de um lugar, vamos transformar tudo em uma lista de DadosEpisódios (contendo eps de todas as temporadas)
        // Nisso o stream pode nos ajudar, evitando de usar 2 for's.
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())  //Recurso para quando temos uma lista dentro de outra lista. neste caso, gerando um fluxo de dados dos eps de todas as temporadas
                .collect(Collectors.toList()); //Aqui poderiamos usar apenas o ".toList()". Mas ele geraria uma lista imutável, e não poderíamos acrescentar mais nada

        System.out.println("\nOs 5 melhores episódios da série " + dadosSerie.titulo() + " :");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")) //removendo da lista todos os episódio que não possuem avaliação
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) //ordenando de forma decrescente a lista comparando avaliação
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)) //transformando cada DadoEpisódios em novos Episódios. Criamos um construtor específico para isso
                )
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = leitura.nextInt();
        leitura.nextLine(); //é necessário após usarmos um nextInt

        LocalDate dataBusca = LocalDate.of(ano, 1, 1); //definindo uma data de busca com base no ano digitado
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //setando a configuração do formatador

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data de lançamento: " + e.getDataLancamento().format(formatador)
                ));

    }
}
