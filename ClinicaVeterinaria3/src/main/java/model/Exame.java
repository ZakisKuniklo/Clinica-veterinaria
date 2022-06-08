package model;

import java.util.Calendar;

/**
 *
 * @author vilela
 */
public class Exame {
    private int id;
    private Calendar data;
    private String descricao;
    private int idAnimal;    

    public Exame(int id, Calendar data, String descricao, int idConsulta) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.idAnimal = idConsulta;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
    
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idConsulta) {
        this.idAnimal = idConsulta;
    }


    
}
