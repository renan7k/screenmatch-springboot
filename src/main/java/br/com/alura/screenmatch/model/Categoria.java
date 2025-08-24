package br.com.alura.screenmatch.model;

public enum Categoria {
    //Sempre definimos com letra maiúscula, pq é basicamente uma constante
    ACAO("Action"), //passando tbm como a api do omdb vai devolver o genero
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoriaOmdb;

    //constutor, que adiciona a categoria do omdb que recebe como parâmetro ao ENUM
    Categoria (String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
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
}
