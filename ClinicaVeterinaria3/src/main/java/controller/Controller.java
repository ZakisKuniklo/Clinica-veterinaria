/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Date;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import view.GenericTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import model.*;
import view.AnimalTableModel;
import view.ClienteTableModel;
import view.ConsultaTableModel;
import view.EspecieTableModel;
import view.ExameTableModel;
import view.VeterinarioTableModel;

/**
 *
 * @author zakis
 */
public class Controller {
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Veterinario veterinarioSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField veterinarioSelecionadoTextField = null;
    
    public static void setTextFields(JTextField cliente, JTextField animal, JTextField veterinario){
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
        veterinarioSelecionadoTextField = veterinario;
    }
            
    public static void setTableModel(JTable table, GenericTableModel tableModel){
        table.setModel(tableModel);
    }

    public static Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public static Animal getAnimalSelecionado() {
        return animalSelecionado;
    }
    
    public static void setSelected(Object selected){
        if(selected == null){
                clienteSelecionado = null;
                animalSelecionado = null;
                veterinarioSelecionado =null;
        }
        if (selected instanceof Cliente) {
            clienteSelecionado = (Cliente) selected;
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
            animalSelecionadoTextField.setText("");
        }else if(selected instanceof Animal){
            animalSelecionado = (Animal) selected;
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        }else if(selected instanceof Veterinario){
            veterinarioSelecionado = (Veterinario) selected;
            veterinarioSelecionadoTextField .setText(veterinarioSelecionado.getNome());
        }
    }
    
    //jradioButtons:
    
    public static void jradioButtonClienteSelecionado(JTable table){
        setTableModel(table,new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
    }
    
    public static boolean jradioButtonAnimaisSelecionado(JTable table){
        if (getClienteSelecionado()!= null) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByIdCliente(getClienteSelecionado().getId())));
            return true;
        }else{
            Controller.setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        }
    }
    
    public static void jradioButtonEspeciesSelecionado(JTable table){
        setTableModel(table, new EspecieTableModel(EspecieDAO.getInstance().retrieveAll()));
    }
    
    public static void jradioButtonVeterinarioSelecionado(JTable table){
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
    }
    
    //filtros
    
    public static void atualizaNomeParecido(JTable table, String nome){
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(ClienteDAO.getInstance().retrieveBySimilarName(nome));
        }else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveBySimilarName(nome));
        }else if (table.getModel() instanceof AnimalTableModel){
            if(getClienteSelecionado() != null){
                ((GenericTableModel)table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveBySimilarName(getClienteSelecionado().getId(),nome));
            }
        }else if (table.getModel() instanceof EspecieTableModel){
            ((GenericTableModel)table.getModel()).addListOfItems(EspecieDAO.getInstance().retrieveBySimilarName(nome));
        }
    } 
    
    public static void limparBusca(JTable table){
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(ClienteDAO.getInstance().retrieveAll());
        }else if (table.getModel() instanceof AnimalTableModel) {
            if(getClienteSelecionado() != null){
                ((GenericTableModel)table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveByIdCliente(getClienteSelecionado().getId()));
            }
        }else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(EspecieDAO.getInstance().retrieveAll());
        }else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveAll());
        }else if (table.getModel() instanceof ConsultaTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(ConsultaDAO.getInstance().retrieveAll());
        }else if (table.getModel() instanceof ExameTableModel) {
            if(animalSelecionado!= null){
                ((GenericTableModel)table.getModel()).addListOfItems(ExameDAO.getInstance().retrieveByIdAnimal(animalSelecionado.getId()));
            }
        }
    }
    
    public static void filtraConsulta(JTable table, JToggleButton b1,JToggleButton b2,JToggleButton b3){
        if(table.getModel() instanceof ConsultaTableModel){
            String where = "";
            if(!b1.isSelected()){
                where = "WHERE data >= date('now')";
            }
            if(b2.isSelected()){
                Calendar calendar = Calendar.getInstance();
                where = "WHERE data BETWEEN '"+(calendar.getTimeInMillis()-86400000)+"' AND '"+(calendar.getTimeInMillis()+86400000)+"'";
                if(b3.isSelected()){
                    if(veterinarioSelecionado != null){
                        where += "AND id_vet = " + veterinarioSelecionado.getId();
                    }
                }
            }else if(b3.isSelected()){
                if(veterinarioSelecionado != null){
                    where = "WHERE id_vet = " + veterinarioSelecionado.getId() ;
                }
            }
            
            String query = "SELECT * FROM consulta "+ where+" ORDER BY data, horario";
            System.out.println(query);
            ((GenericTableModel)table.getModel()).addListOfItems(ConsultaDAO.getInstance().retrieve(query));
        }
        
    }
    
    public static void filtraExame(JTable table, JToggleButton b1,JToggleButton b2){
        if(table.getModel() instanceof ExameTableModel){
            String where = "WHERE";
            if(!b1.isSelected()){
                where = "WHERE data >= date('now') AND ";
            }
            if(b2.isSelected()){
                Calendar calendar = Calendar.getInstance();
                where = "WHERE data BETWEEN '"+(calendar.getTimeInMillis()-86400000)+"' AND '"+(calendar.getTimeInMillis()+86400000)+"' AND ";
            }
            
            String query = "SELECT * FROM exame "+ where +"  id_animal ="+animalSelecionado.getId()+" ORDER BY data";
            ((GenericTableModel)table.getModel()).addListOfItems(ExameDAO.getInstance().retrieve(query));
        }
    }
    //adicionar
    
    public static boolean adicionarCadastro(JTable table){
        if (table.getModel() instanceof ClienteTableModel) {
            ((GenericTableModel)table.getModel()).addItem(adicionaCliente("", "", "", "", ""));
        }else if (table.getModel() instanceof AnimalTableModel) {
            ((GenericTableModel)table.getModel()).addItem(adicionaAnimal("",0,"",""));
        }else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel)table.getModel()).addItem(adicionaEspecie(""));
        }else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel)table.getModel()).addItem(adicionaVeterinario("", "", ""));
        }else if (table.getModel() instanceof ConsultaTableModel) {
            if ((clienteSelecionado != null) ||(animalSelecionado != null) ||(veterinarioSelecionado!=null)){
                ((GenericTableModel)table.getModel()).addItem(adicionarConsulta());
            }else{
                return false;
            }
        }else if(table.getModel() instanceof ExameTableModel){
            if ((clienteSelecionado != null) ||(animalSelecionado != null)){
                ((GenericTableModel)table.getModel()).addItem(adicionarExame());
            }else{
                return false;
            }
        }
        return true;
    }
    
    public static Cliente adicionaCliente(String nome, String end, String cep, String email, String telefone){
        return ClienteDAO.getInstance().create(nome, end, cep, email, telefone);
    }

    public static Animal adicionaAnimal(String nome,int anoNasc, String sexo, String especie){
        return AnimalDAO.getInstance().create(nome, anoNasc, sexo, 0, getClienteSelecionado());
    }
    
    public static Especie adicionaEspecie(String nome){
        return EspecieDAO.getInstance().create(nome);
    }
    
    public static Veterinario adicionaVeterinario(String nome, String email, String telefone){
        return VeterinarioDAO.getInstance().create(nome, email, telefone);
    }
    
    public static Consulta adicionarConsulta(){
        return ConsultaDAO.getInstance().create(Calendar.getInstance(), 8, "", animalSelecionado.getId(), veterinarioSelecionado.getId(), 0, 0);
    }
    
    public static Exame adicionarExame(){
        return ExameDAO.getInstance().create(Calendar.getInstance(), "", animalSelecionado.getId());
    }
    
    //apagar
    public static void apagarCadastro(JTable table,Object selected){
        if (table.getModel() instanceof ClienteTableModel) {
            if (selected != null) {
                apagaCliente((Cliente)selected);
            }    
        }else if (table.getModel() instanceof AnimalTableModel) {
            apagaAnimal((Animal)selected);
        }else if (table.getModel() instanceof EspecieTableModel) {
            List <Animal> animais = AnimalDAO.getInstance().retrieveByIdEspecie(((Especie)selected).getId());
            if (animais.isEmpty()) {
                apagaEspecie((Especie)selected);
            }
        }else if (table.getModel() instanceof VeterinarioTableModel) {
            apagaVeterinario((Veterinario)selected);
        }else if (table.getModel() instanceof ConsultaTableModel) {
            apagaConsulta((Consulta)selected);
        }else if (table.getModel() instanceof ExameTableModel) {
            apagaExame((Exame)selected);
        }
        limparBusca(table);
    }
    
    public static void apagaCliente(Cliente cliente){
        List <Animal> animais = AnimalDAO.getInstance().retrieveByIdCliente(cliente.getId());
        for(Animal animal : animais ) {
            AnimalDAO.getInstance().delete(animal);
        } 
        ClienteDAO.getInstance().delete(cliente);
    }
    
    public static void apagaAnimal(Animal animal){
        AnimalDAO.getInstance().delete(animal);
    }
    
    public static void apagaEspecie(Especie especie){
        EspecieDAO.getInstance().delete(especie);
    }
    
    public static void apagaVeterinario(Veterinario vet){
        VeterinarioDAO.getInstance().delete(vet);
    }
    
    public static void apagaConsulta(Consulta consulta){
        ConsultaDAO.getInstance().delete(consulta);
    }
    
    public static void apagaExame(Exame exame){
        ExameDAO.getInstance().delete(exame);
    }
}
