package br.com.alura.screenmatch.model;

public enum Categoria {
    //Sempre definimos com letra maiúscula, pq é basicamente uma constante
    ACAO("Action", "Ação"), //passando tbm como a api do omdb vai devolver o genero
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaPortugues; //atributo para mapear o que vai ser digitado pelo cliente

    //constutor, que adiciona a categoria do omdb que recebe como parâmetro ao ENUM
    Categoria (String categoriaOmdb, String categoriaPortugues){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    //basicamente, o metodo recebe o que vem de genero omdb, interpreta e transformar na categoria de enum
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

}
