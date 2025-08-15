package br.com.alura.tabelafipe.principal;

import br.com.alura.tabelafipe.model.Dados;
import br.com.alura.tabelafipe.model.Modelos;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ConsumoAPI;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados convesor = new ConverteDados();

    public void exibeMenu() {
        var menu = """
                **** OPÇÕES ****
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consulta:
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;
        //queremos entender a escolha do usuário, e incrementar na primeira busca da api
        //carros/marcas, ou motos/marcas ou caminhoes/marcas
        if(opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        }else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else { //supondo que nn sendo carro e moto, será caminhão
            endereco = URL_BASE + "caminhoes/marcas";
        }

        //consumindo a classe que faz requisição, enviando o endereço com o tipo, e salvando o json de resposta na var
        var json = consumo.obterDados(endereco);
        System.out.println(json);

        //transformando o json em uma coleção
        List<Dados> marcas = convesor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))//ordenando pelo código (string)
                .forEach(System.out::println);

        System.out.println("Digite o código da marca do carro que deseja consultar: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco); //realizando a 2º consulta na api

        //nesse caso, estamos usando o obterDados, pq o modelo JÁ é uma lista
        //No caso de busca da marca, o modelo não foi definido como uma lista, por isso usamos o obterLista
        //Aqui foi necessário definir o modelo como uma lista, pq a api retornava 2 chaves com listas: modelo e ano
        var modeloLista = convesor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");

        //A classe modelos que tem uma lista de modelos, por isso chamamos a instância da classe.modelos() ->Lista
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        //realizando 3º consulta, por modelos
        System.out.println("Digite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = leitura.nextLine();
        //crianod uma lista com os modelos existentes
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo:");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);

        List<Dados> anos = convesor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            //buscando modelos para cada ano que o carro existe. Ex: buscando celta 2011 e 2012
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = convesor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nVeículos disponíveis: ");
        veiculos.forEach(System.out::println);
    }
}
