package Class;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conectar {

    Connection conection;

    public Connection conexion(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            conection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca_de_itch","root","");
            System.out.println("Coneccion establecida");
        }
        catch(Exception e){
            System.err.println(e.getMessage());

        }
        return conection;
    }
}
