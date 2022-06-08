package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.dateFormat;
import static model.DAO.getConnection;

/**
 *
 * @author Manko
 */
public class TratamentoDAO extends DAO {
    private static TratamentoDAO instance;

    // Construtor: Cria a conexão E as tabelas do banco, caso nao estejam criadas
    private TratamentoDAO() {
        getConnection();
        createTable();
    }


    // Singleton - Serve para criar uma instancia de cliente DAO dentro de si mesma, para que classes não abstratas possam ser chamadas
    public static TratamentoDAO getInstance() {
        return (instance==null?(instance = new TratamentoDAO()):instance);
    }

    // CRUD
    public Tratamento create(String nome, Calendar dataIni, Calendar dataFim, int id_animal, int terminado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO tratamento (id_animal, nome, dataIni, dataFim, terminado) VALUES (?,?,?,?,?)");
            stmt.setInt(1, id_animal);
            stmt.setString(2, nome);
            stmt.setString(3, dateFormat.format(dataIni.getTime()));
            stmt.setString(4, dateFormat.format(dataFim.getTime()));
            stmt.setInt(5, terminado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("tratamento","id"));
    }

    // Cria uma instancia Cliente com as informações trazidas do banco de dados
    private Tratamento buildObject(ResultSet rs) {
        Tratamento tratamento = null;
        try {
            Calendar dt1= Calendar.getInstance();
            dt1.setTime(dateFormat.parse(rs.getString("dataIni")));
            Calendar dt2= Calendar.getInstance();
            dt2.setTime(dateFormat.parse(rs.getString("dataFim")));
            tratamento = new Tratamento(rs.getInt("id"), rs.getString("nome"), dt1, dt2, rs.getInt("id_animal"), rs.getInt("terminado"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tratamento;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }

    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM tratamento");
    }

    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM tratamento WHERE id = " + lastId("tratamento","id"));
    }

    // RetrieveById
    public Tratamento retrieveById(int id) {
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE id = " + id);
        return (tratamentos.isEmpty()?null:tratamentos.get(0));
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM tratamento WHERE nome LIKE '%" + nome + "%'");
    }

    // Updade
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamento SET nome=?, dataIni=?, dataFim=?, id_animal=?, terminado=? WHERE id=?");
            stmt.setString(1, tratamento.getNome());
            stmt.setString(2,  dateFormat.format(tratamento.getDtInicio().getTime()));
            stmt.setString(3, dateFormat.format(tratamento.getDtFim().getTime()));
            stmt.setInt(4, tratamento.getIdAnimal());
            stmt.setInt(5, tratamento.getTerminou());
            stmt.setInt(6, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id =" + id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
