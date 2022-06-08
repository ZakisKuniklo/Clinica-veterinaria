package model;

/**
 *
 * @author Plinio Vilela
 */
public class Especie {
    private int id;
    private String nome;

    public Especie(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public Especie(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
