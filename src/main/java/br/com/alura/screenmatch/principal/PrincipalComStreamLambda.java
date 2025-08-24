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

public class PrincipalComStreamLambda {
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
                //.peek(e -> System.out.println("Primeiro filtro(n/a): " + e)) //peek serve mostrar o resultado de cada filtro/operação realizada no stream
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) //ordenando de forma decrescente a lista comparando avaliação
                //.peek(e -> System.out.println("Ordenação: " + e))
                .limit(5)
                //.peek(e -> System.out.println("Limitanto a 5: " + e))
                .map(e -> e.titulo().toUpperCase())
                //.peek(e -> System.out.println("Convertendo para maiúscula: " + e))
                .forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------------");

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)) //transformando cada DadoEpisódios em novos Episódios. Criamos um construtor específico para isso
                )
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("BUSCA DE QUAL TEMPORADA PERTENCE O EPISÓDIO");
        System.out.println("Digite o nome/trecho do título do episódio:");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream() //trabalhando com optional, pois pode ser q não encontre, nesse caso ficaria null
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase())) //ambas informações convertidas para maiúscula, para não dar diferença na busca
                .findFirst();
        if (episodioBuscado.isPresent()){ //comando do optional, para verificar se o "conteiner" possui a informação
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada()); //o .get() é para acessar o elemento dentro do conteiner
        }else{
            System.out.println("Episódio não encontrado!");
        }

        System.out.println("--------------------------------------------------------------------------");
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

        //Agr o desafioo, é criar uma média de avaliação por temporada. A api consultada não possui essa informação
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Média de avaliações por Temporada: ");
        Map<Integer, Double> avaliacesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, //agrupando por temporada
                        Collectors.averagingDouble(Episodio::getAvaliacao))); //fazendo média da avaliação
        System.out.println(avaliacesPorTemporada);

        //DoubleSummaryStatistics é uma classe que gera estatísticas
        System.out.println("Estatísticas das temporadas:");
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao)); //informando qual atributo queremos as estatísticas

        System.out.println("Média de avaliação dos episódios: " + est.getAverage());
        System.out.println("Melhor avaliação: " + est.getMax());
        System.out.println("Menor avaliação: " + est.getMin());
        System.out.println("Quantidade de episódios avaliadas: " + est.getCount());
    }
}
