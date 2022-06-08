/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.Animal;
import model.AnimalDAO;
import model.Especie;
import model.EspecieDAO;
import java.util.List;

/**
 *
 * @author zakis
 */
public class AnimalTableModel extends GenericTableModel{

    public AnimalTableModel(List vDados) {
        super(vDados,new String[]{"Nome","AnoNasc","Sexo","Espécie"});
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
            case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
  
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return animal.getNome();
            case 1:
                return animal.getAnoNasc();
            case 2:
                return animal.getSexo();
            case 3:
                Especie species = EspecieDAO.getInstance().retrieveById(animal.getIdEspecie());
                if(species != null){
                    return species.getNome();
                }
                return "";
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);
        switch (columnIndex) {
            case 0:
                animal.setNome((String)aValue);
                break;
            case 1:
                animal.setAnoNasc(Integer.parseInt((String)aValue));
                break;
            case 2:
                animal.setSexo((String)aValue);
                break;
            case 3:
                Especie species = EspecieDAO.getInstance().retrieveByName((String)aValue);
                if(species == null){
                    species = EspecieDAO.getInstance().create((String)aValue);
                }
                animal.setIdEspecie(species.getId());
                break;
            default:
                throw new IndexOutOfBoundsException("ColumnIndex out of bounds");
        }
        
        AnimalDAO.getInstance().update(animal);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
}
