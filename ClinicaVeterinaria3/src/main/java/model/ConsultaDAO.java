package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.getConnection;

/**
 *
 * @author Manko
 */
public class ConsultaDAO extends DAO {
    private static ConsultaDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton - Serve para criar uma instancia de cliente DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

    // CRUD
    public Consulta create(Calendar data, int horario, String comentario, int id_animal, int id_vet, int id_tratamento, int terminado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, horario, comentario, id_animal, id_vet, id_tratamento, terminado) VALUES (?,?,?,?,?,?,?)");
            stmt.setDate(1, new Date(data.getTimeInMillis()));
            stmt.setInt(2, horario);
            stmt.setString(3, comentario);
            stmt.setInt(4, id_animal);
            stmt.setInt(5, id_vet);
            stmt.setInt(6, id_tratamento);
            stmt.setInt(7, terminado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","id"));
    }


    // Cria uma instancia Consulta com as informações trazidas do banco de dados
    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            Calendar dt = Calendar.getInstance();            
            consulta = new Consulta(rs.getInt("id"), dt, rs.getInt("horario"), rs.getString("comentario"), rs.getInt("id_animal"), rs.getInt("id_vet"), rs.getInt("id_tratamento"), rs.getInt("terminado")==1);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }
        

    // Generic Retriever
    public List retrieve(String query) {
        List<Consulta> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }

    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta ORDER BY data, horario");
    }

    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta","id"));
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }

    // RetrieveByIdAnimal
    public List retrieveByIdAnimal(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id_animal = " + id);
        return consultas;
    }

    // RetrieveByIdVet
    public List retrieveByIdVet(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id_vet = " + id);
        return consultas;
    }

    // RetrieveByIdTratamento
    public List retrieveByIdTratamento(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id_tratamento = " + id);
        return consultas;
    }
    

    // Update
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, horario=?, comentario=?, id_animal=?, id_vet=?, id_tratamento=?, terminado=? WHERE id=?");
            stmt.setDate(1, new Date(consulta.getData().getTimeInMillis()));
            stmt.setInt(2, consulta.getHora());
            stmt.setString(3, consulta.getComentarios());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdVeterinario());
            stmt.setInt(6, consulta.getIdTratamento());
            stmt.setBoolean(7, consulta.getTerminou());
            stmt.setInt(8, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteByIdAnimal(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id_animal =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteByIdVet(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id_vet =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteByIdTratamento(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id_tratamento =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
