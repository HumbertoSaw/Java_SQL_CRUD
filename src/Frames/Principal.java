package Frames;


// Imports from the libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Class.Conectar;
import com.sun.source.tree.TryTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                //txtID.setEnabled(false);

                // adding a action to the save button
                saveBtn.addActionListener(new ActionListener()
                {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                                try
                                {
                                        String sqlSentence1 ="INSERT INTO libros (Id_Libro,ISBD_LIBRO,Titulo_Libro,Nombre_Autor_Libro,Pimer_Apellido_Autor_Libro,Segundo_Apellido_Autor_Libro,Fecha_Pub_Libro,Editorial_Libro,Edicion_Libro,Genero_Libro) VALUES (?,?,?,?,?,?,?,?,?,?)";

                                        PreparedStatement ps = cn.prepareStatement(sqlSentence1);
                                        ps.setString(1, txtID.getText() );
                                        ps.setString(2, txtIsbn.getText());
                                        ps.setString(3, txtTitle.getText());
                                        ps.setString(4, txtAuthor.getText());
                                        ps.setString(5, txtLastName.getText());
                                        ps.setString(6, txtLastName2.getText());
                                        ps.setString(7, txtDatePub.getText());
                                        ps.setString(8, txtEditorial.getText());
                                        ps.setString(9, txtEdition.getText());
                                        ps.setString(10,txtGenere.getText());

                                        ps.executeUpdate();

                                        limpiar();
                                        mostrarTabla("");


                                }
                                catch (SQLException exception)
                                {
                                        System.err.println("Error al guardar. . . " + exception);
                                        JOptionPane.showMessageDialog(null,"Error al llenado de la tabla");


                                }
                        }
                });


        }
        //Metodos usados en el constructor
        //Metodo limpiar campos de escritura
        void limpiar(){
                txtID.setText("");
                txtIsbn.setText("");
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
                modelo.addColumn("ISBN");
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

                String datos[]=new String[10];

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
                                datos[9]=rs.getString(10);

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



