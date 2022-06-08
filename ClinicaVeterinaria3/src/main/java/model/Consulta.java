package model;

import java.util.Calendar;

public class Consulta {
    private final int id;
    private Calendar data;
    private int hora;
    private String comentarios;
    private int idAnimal;
    private int idVeterinario;
    private int idTratamento;
    private boolean terminou;

    public Consulta(int id, Calendar data, int hora, String comentarios, int idAnimal, int idVeterinario, int idTratamento, boolean terminou) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.comentarios = comentarios;
        this.idAnimal = idAnimal;
        this.idVeterinario = idVeterinario;
        this.idTratamento = idTratamento;
        this.terminou = terminou;
    }

    public Calendar getData() { return data; }
    public void setData(Calendar data) { this.data = data; }

    public int getHora() { return hora; }
    public void setHora(int hora) { this.hora = hora; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public int getIdAnimal() { return idAnimal; }

    public int getIdVeterinario() { return idVeterinario; }

    public int getIdTratamento() { return idTratamento; }

    public boolean getTerminou() { return terminou; }
    public void setTerminou(boolean terminou) { this.terminou = terminou;}

    public int getId() {return id;}

    @Override
    public String   toString() {
        return "Consulta{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", idAnimal=" + idAnimal +
                ", idVeterinario=" + idVeterinario +
                ", idTratamento=" + idTratamento +
                ", terminou=" + terminou +
                '}';
    }
}
