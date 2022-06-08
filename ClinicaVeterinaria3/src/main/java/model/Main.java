package model;


import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import view.Principal;

/**
 *
 * @author vilela
 */
public class Main {
    public static void main(String[] args) {        
        //Cliente c1 = new Cliente(1,"Plinio","Rua dos Bobos","70707070","00234-900","prvilela@unicamp.br");

        //ClienteDAO.getInstance().create("Plinio","Rua dos Bobos","70707070","00234-900","prvilela@unicamp.br");
        
        //Animal a1 = new Animal(1,"Toto",2,0);
        //Animal a2 = new Animal(2,"Mimi",3,1);
        
        //Cliente c1 = ClienteDAO.getInstance().retrieveById(1);
        
        //c1.addAnimal(a1);
        //c1.addAnimal(a2);
        
        //System.out.println(c1);
        
        //João Pedro Coelho de Sousa - 174456
        //códigos usados para a atividade
        
        //ClienteDAO.getInstance().create("Plinio","Rua dos Bobos","70707070","00234-900","prvilela@unicamp.br");
        //ClienteDAO.getInstance().create("Spiderman","New York City","921456789","00234-900","spider@avengers.com");
        /*Cliente c2= new Cliente(2,"Spiderman","New York City","921456789","00234-900","spider@avengers.com");
        ClienteDAO.getInstance().delete(c2);*/
        //AnimalDAO.getInstance().delete(AnimalDAO.getInstance().retrieveById(2));
        // sem querer cadastrei 2 spiderman, então criei um objeto spiderman e usei para deletar do banco
        //também criei 2 deadpool
       //Cliente c3 = ClienteDAO.getInstance().retrieveById(3);
       //Cliente c4 = ClienteDAO.getInstance().retrieveById(1);
       //AnimalDAO.getInstance().create("Belle", 2000, "Femea", 1, c4);
       //AnimalDAO.getInstance().create("Dolly", 2010, "Femea", 1, c4);
        //System.out.println(AnimalDAO.getInstance().retrieveByIdCliente(3));
        
        //System.out.println(ClienteDAO.getInstance().retrieveAll());
        //System.out.println(AnimalDAO.getInstance().retrieveAll());
        
        //VeterinarioDAO.getInstance().create("Algusto","Algusto.nogueira@gmail.com","972516782");
        
        //System.out.println(VeterinarioDAO.getInstance().retrieveAll());
        /*
        
        List<Animal> listaExterna = c1.getAnimais();
        Animal a3 = new Animal(3,"    ",5,0);
        listaExterna.add(a3);
        
        System.out.println(c1);
        
        System.out.println(listaExterna);
        */
        //Calendar cal = Calendar.getInstance();
        //ConsultaDAO.getInstance().create(cal, 8, "", 1, 1, 0, 0);
        //System.out.println(ConsultaDAO.getInstance().retrieveAll());
        Principal.main(args);
        
    }
}
