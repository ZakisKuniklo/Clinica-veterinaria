package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class ExameDAO extends DAO {
    private static ExameDAO instance;

    private ExameDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ExameDAO getInstance() {
        return (instance==null?(instance = new ExameDAO()):instance);
    }

// CRUD    
    public Exame create(Calendar data,String nome, int idAnimal) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO exame (data,descricao, id_animal) VALUES (?,?,?)");
            stmt.setDate(1, new Date(data.getTimeInMillis()));
            stmt.setString(2, nome);
            stmt.setInt(3, idAnimal);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("exame","id"));
    }
    

    private Exame buildObject(ResultSet rs) {
        Exame exame = null;
        try {
            Calendar dt = Calendar.getInstance();            
            dt.setTime(rs.getDate("data"));    
            exame = new Exame(rs.getInt("id"), dt, rs.getString("descricao"), rs.getInt("id_animal"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exame;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Exame> exames = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exames;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM exame");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM exame WHERE id = " + lastId("exame","id"));
    }

    // RetrieveById
    public Exame retrieveById(int id) {
        List<Exame> exames = this.retrieve("SELECT * FROM exame WHERE id = " + id);
        return (exames.isEmpty()?null:exames.get(0));
    }
    
    // RetrieveByIdConsulta
    public List retrieveByIdAnimal(int id_animal) {
        return this.retrieve("SELECT * FROM exame WHERE id_animal = " + id_animal);
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM exame WHERE descricao LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Exame exame) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE exame SET data=?,descricao=?, id_animal= ? WHERE id=?");
            stmt.setDate(1, new Date(exame.getData().getTimeInMillis()));
            stmt.setString(2, exame.getDescricao());
            stmt.setInt(3, exame.getIdAnimal());
            stmt.setInt(4, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Exame exame) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exame WHERE id = ?");
            stmt.setInt(1, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
