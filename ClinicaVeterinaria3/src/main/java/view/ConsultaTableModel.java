/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Animal;
import model.AnimalDAO;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.VeterinarioDAO;

/**
 *
 * @author zakis
 */
public class ConsultaTableModel  extends GenericTableModel{
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultaTableModel(List vDados) {
        super(vDados,new String[]{"Data","Hora","Cliente","Animal","Veterinario","Obs","Fim"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
  
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);
        Animal animal;
        switch (columnIndex) {
            case 0:
                return dateFormat.format(consulta.getData().getTime());
            case 1:
                return consulta.getHora();
            case 2:
                animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                return ClienteDAO.getInstance().retrieveById(animal.getIdCliente()).getNome();
            case 3:
                animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                return animal.getNome();
            case 4:
                return VeterinarioDAO.getInstance().retrieveById(consulta.getIdVeterinario()).getNome();
            case 5:
                return consulta.getComentarios();
            case 6:
                return consulta.getTerminou();
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(dateFormat.parse((String)aValue));
                } catch (Exception ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE,null, ex);
                }
                consulta.setData(cal);
                break;
            case 1:
                consulta.setHora((Integer)aValue);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                consulta.setComentarios((String)aValue);
                break;
            case 6:
                consulta.setTerminou((Boolean)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
        
        ConsultaDAO.getInstance().update(consulta);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex){
        if ((columnIndex<2)||(columnIndex>4)) {
            return true;
        }else{
            return false;
        }
    }
}