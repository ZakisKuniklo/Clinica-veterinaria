/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.Cliente;
import model.ClienteDAO;
import java.util.List;
import model.Veterinario;
import model.VeterinarioDAO;

/**
 *
 * @author zakis
 */
public class VeterinarioTableModel extends GenericTableModel{

    public VeterinarioTableModel(List vDados) {
        super(vDados,new String[]{"Nome","Email","Telefone"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
  
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return veterinario.getNome();
            case 1:
                return veterinario.getEmail();
            case 2:
                return veterinario.getTelefone();
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                veterinario.setNome((String)aValue);
                break;
            case 1:
                veterinario.setEmail((String)aValue);
                break;
            case 2:
                veterinario.setTelefone((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
        
        VeterinarioDAO.getInstance().update(veterinario);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
}
