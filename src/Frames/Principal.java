package Frames;


// Imports from the libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Class.Conectar;
import com.sun.source.tree.TryTree;

import java.beans.PropertyEditorSupport;
import java.sql.*;
import java.sql.Connection;


/**
 *
 * Class that allow to a user insert new information on the DB
 * Version: 08/11/2021
 *
 */
public class Principal extends javax.swing.JFrame{

        //Constructor pricipal de la clase interface principal
        public Principal (){

                this.setLocationRelativeTo(null);
                limpiar();
                mostrarTabla("");
                txtID.setEnabled(false);

        }
        //Metodos usados en el constructor
        //Metodo limpiar campos de escritura
        void limpiar(){
                txtID.setText("");
                txtTitle.setText("");
                txtAuthor.setText("");
                txtLastName.setText("");
                txtLastName2.setText("");
                txtDatePub.setText("");
                txtEditorial.setText("");
                txtEdition.setText("");
                txtGenere.setText("");
        }
        //Metodo que muestra informacion de la tabla
        void mostrarTabla(String string){
                DefaultTableModel modelo = new DefaultTableModel();

                modelo.addColumn("ID");
                modelo.addColumn("TITULO");
                modelo.addColumn("NOMBRE");
                modelo.addColumn("PRIMER APELLIDO");
                modelo.addColumn("SEGUNDO APELLIDO");
                modelo.addColumn("PUBLICACION");
                modelo.addColumn("EDITORIAL");
                modelo.addColumn("EDICION");
                modelo.addColumn("GENERO");

                table.setModel(modelo);
                String sql = "select * from Libros where concat (Titulo_Libro,' ',Nombre_Autor_Libro) LIKE '%"+string+"%'";

                String datos[]=new String[9];

                Statement st;

                try {
                        st = cn.createStatement();
                        ResultSet rs =st.executeQuery(sql);
                        while(rs.next()){
                                datos[0]=rs.getString(1);
                                datos[1]=rs.getString(2);
                                datos[2]=rs.getString(3);
                                datos[3]=rs.getString(4);
                                datos[4]=rs.getString(5);
                                datos[5]=rs.getString(6);
                                datos[6]=rs.getString(7);
                                datos[7]=rs.getString(8);
                                datos[8]=rs.getString(9);

                                modelo.addRow(datos);
                        }

                        table.setModel(modelo);
                }
                catch (SQLException e){
                        System.err.println("Error al mostrar contenido de la tabla");
                        JOptionPane.showMessageDialog(null,"Error al mostrar contenido de la tabla");
                }
        }


        public static void main(String[] args) {
                JFrame frame = new JFrame("Principal");
                frame.setContentPane(new Principal().Jpanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
        }

        // Begin of the elements of the form
        private JTextField txtID;
        private JTextField txtAuthor;
        private JTable table;
        private JTextField txtIsbn;
        private JButton saveBtn;
        private JButton updateBtn;
        private JButton cancelBtn;
        private JButton reportButton;
        private JPanel Jpanel;
        private JLabel LabelRegistroLibros;
        private JPanel PanelRegistroLibros;
        private JTextField txtLastName;
        private JTextField txtLastName2;
        private JTextField txtDatePub;
        private JTextField txtEditorial;
        private JTextField txtEdition;
        private JTextField txtGenere;
        private JTextField txtTitle;
        // End of the elements used on the form

        Conectar con = new Conectar();
        Connection cn = con.conexion();

        /**
         * Main method that allow run the form called: Principal
         * @param args
         */


}



