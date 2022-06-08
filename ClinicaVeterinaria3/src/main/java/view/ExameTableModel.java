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
import model.Exame;
import model.ExameDAO;

/**
 *
 * @author zakis
 */
public class ExameTableModel extends GenericTableModel{
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ExameTableModel(List vDados) {
        super(vDados,new String[]{"Data","Descrição"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
  
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Exame exame = (Exame) vDados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dateFormat.format(exame.getData().getTime());
            case 1:
                return exame.getDescricao();
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Exame consulta = (Exame) vDados.get(rowIndex);
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
                consulta.setDescricao((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
        
        ExameDAO.getInstance().update(consulta);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex){
         return true;  
    }
}
