package Frames;


// Imports from the libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Class.Conectar;
import com.sun.source.tree.TryTree;

import java.awt.event.*;
import java.beans.PropertyEditorSupport;
import java.sql.*;
import java.sql.Connection;


/**
 *
 * Class that allow to a user insert new information on the DB
 * Version: 10/11/2021
 *
 */
public class Principal extends javax.swing.JFrame{

        // Declaration and initialization of the attributes of the class
        private String sentenciaSqlInsert;
        private String sentenciaSqlUpdate;
        private String sentenciaSqlDelete;
        private String selecciónTabla = "";
        private String[] campos;

        /**
         * Constructor of the Principal class
         */
        public Principal (){
                
                
                
                //Vital variables and declaration of methods
                this.setLocationRelativeTo(null);
                limpiar();

                campos = new String[11];
                for(int i = 0; i < campos.length; i++){
                        campos[i] = "";
                }

                mostrarTabla("");
                mostrarMenu();

                // adding an action to the save button
                saveBtn.addActionListener(new ActionListener()
                {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                                try
                                {
                                        String sqlSentence1 ="INSERT INTO + libros+ (Id_Libro,ISBD_Libro,Titulo_Libro,Nombre_Autor_Libro,Pimer_Apellido_Autor_Libro,Segundo_Apellido_Autor_Libro,Fecha_Pub_Libro,Editorial_Libro,Edicion_Libro,Genero_Libro) VALUES (?,?,?,?,?,?,?,?,?,?)";

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
                                        JOptionPane.showMessageDialog(null,"Los datos ah sido guardados");

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

                // adding an action to the update button
                updateBtn.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                                try{

                                        PreparedStatement ps =cn.prepareStatement("UPDATE libros SET Id_Libro = '"+txtID.getText()+"',ISBD_Libro = '"+txtIsbn.getText()+"',Titulo_Libro = '"+txtTitle.getText()+"',Nombre_Autor_Libro = '"+txtAuthor.getText()+"',Pimer_Apellido_Autor_Libro = '"+txtLastName.getText()+"',Segundo_Apellido_Autor_Libro = '"+txtLastName2.getText()+"',Fecha_Pub_Libro = '"+txtDatePub.getText()+"',Editorial_Libro = '"+txtEditorial.getText()+"',Edicion_Libro = '"+txtEdition.getText()+"',Genero_Libro = '"+txtGenere.getText()+"' WHERE Id_libro='"+txtID.getText()+"'");

                                        int respuesta = ps.executeUpdate();

                                        if(respuesta>0){
                                                JOptionPane.showMessageDialog(null, "El registro ah sido actualizado");
                                                limpiar();
                                                mostrarTabla("");
                                        }
                                        else{
                                                JOptionPane.showMessageDialog(null,"No ah seleccionado fila");
                                        }

                                }
                                catch (SQLException exception){
                                        System.err.println("Error al actualizar registro. . . " + exception);
                                        JOptionPane.showMessageDialog(null,"Error al actualizar registro. . .");
                                }
                        }

                });

                // Adding a mouse click listener to select the row.
                table.addMouseListener(new MouseAdapter()
                {

                        public void mouseClicked(MouseEvent e)
                        {
                                super.mouseClicked(e);
                                selecionarFila();
                        }
                });

                // The main functions when a user press the DELETE button.
                deleteBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                                try{
                                // Create a string with the SQLStatement
                                String sqlStatement ="DELETE FROM libros WHERE Id_Libro = '"+ txtID.getText()+ "'";
                                PreparedStatement ps = cn.prepareStatement(sqlStatement);

                                int respuesta = ps.executeUpdate();

                                if(respuesta > 0)
                                {

                                        JOptionPane.showMessageDialog(null,"Libro eliminado");
                                        limpiar();
                                        mostrarTabla("");
                                }
                                else {

                                        JOptionPane.showMessageDialog(null,"No ha seleccionado una fila");
                                }
                                } catch (SQLException exception) {
                                        System.err.println("Error al eliminar. . ." + exception);
                                        JOptionPane.showMessageDialog(null,"Error al eliminar");
                                }
                        }
                });

                
                txtSearch.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                                mostrarTabla(txtSearch.getText());
                        }
                });

                // Cancel button
                cancelBtn.addActionListener(new ActionListener() {
                        @Override
                        public void  actionPerformed(ActionEvent e){
                              limpiar();
                        }
                });

                // Adding a listener to change into the different tables on the DB
                comboBox1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                                selecciónTabla = comboBox1.getSelectedItem().toString();
                                System.out.println("Se ha seleccionado: "+ selecciónTabla);
                                switch (selecciónTabla)
                                {
                                        case"Libros":
                                                campos = new String[10];
                                                campos[0] = "ID";
                                                campos[1] = "ISBN";
                                                campos[2] = "TITULO";
                                                campos[3] = "NOMBRE";
                                                campos[4] = "APELLIDO1";
                                                campos[5] = "APELLIDO2";
                                                campos[6] = "PUBLICACION";
                                                campos[7] = "EDITORIAL";
                                                campos[8] = "EDICION";
                                                campos[9] = "GENERO";

                                                mostrarTabla("");
                                        break;
                                        case "Revistas":
                                                campos = new String[10];
                                                campos[0] = "ID";
                                                campos[1] = "ISBN";
                                                campos[2] = "NOMBRE";
                                                campos[3] = "ANIO";
                                                campos[4] = "EDITORIAL";
                                                campos[5] = "CIUDAD";
                                                campos[6] = "VOLUMEN";
                                                campos[7] = "NUMERO";
                                                campos[8] = "AUTOR";
                                                campos[9] = "APELLIDO1";
                                                campos[10] = "APELLIDO2";

                                                mostrarTabla("");
                                        break;
                                        case "Investigaciones":
                                                campos = new String[10];
                                                campos[0] = "ID";
                                                campos[1] = "FECHA";
                                                campos[2] = "NOMBRE";
                                                campos[3] = "TEMA";
                                                campos[4] = "AUTOR1";
                                                campos[5] = "APELLIDO1";
                                                campos[6] = "APELLIDO2";
                                                campos[7] = "EDICION";
                                                campos[8] = "FECHATERMINADO";

                                                mostrarTabla("");
                                        break;
                                        case "Software":
                                                campos = new String[10];
                                                campos[0] = "ID";
                                                campos[1] = "NOMBRE";
                                                campos[2] = "EMPRESA";
                                                campos[3] = "DESARROLLADOR";
                                                campos[4] = "FECHA";
                                                campos[5] = "VERSION";
                                                campos[6] = "TIPO";
                                                campos[7] = "COMPATIBILIDAD";

                                                mostrarTabla("");
                                        break;

                                }

                        }
                });
        }

        /**
         * Function that clean the txt fields.
         */
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

        /**
         * Method that helps with the selection of the row
         */
        void selecionarFila()
        {
                // This section help to the view that do not generate a very large err
                // This do not break anything is just for debug proposes
                try {

                        int fila = this.table.getSelectedRow();
                        this.txtID.setText(this.table.getValueAt(fila, 0).toString());
                        this.txtIsbn.setText(this.table.getValueAt(fila, 1).toString());
                        this.txtTitle.setText(this.table.getValueAt(fila, 2).toString());
                        this.txtAuthor.setText(this.table.getValueAt(fila, 3).toString());
                        this.txtLastName.setText(this.table.getValueAt(fila, 4).toString());
                        this.txtLastName2.setText(this.table.getValueAt(fila, 5).toString());
                        this.txtDatePub.setText(this.table.getValueAt(fila, 6).toString());
                        this.txtEditorial.setText(this.table.getValueAt(fila, 7).toString());
                        this.txtEdition.setText(this.table.getValueAt(fila, 8).toString());
                        this.txtGenere.setText(this.table.getValueAt(fila, 9).toString());
                }catch (IndexOutOfBoundsException index){
                        System.err.println("Se ha seleccionado fuera de la tabla. . ." + index);
                }
        }

        //Metodo que muestra informacion de la tabla
        void mostrarTabla(String string){

                DefaultTableModel modelo = new DefaultTableModel();

                modelo.addColumn(campos[0]);
                modelo.addColumn(campos[1]);
                modelo.addColumn(campos[2]);
                modelo.addColumn(campos[3]);
                modelo.addColumn(campos[4]);
                modelo.addColumn(campos[5]);
                modelo.addColumn(campos[6]);
                modelo.addColumn(campos[7]);
                modelo.addColumn(campos[8]);
                modelo.addColumn(campos[9]);

                table.setModel(modelo);

                String sql = "select * from "+ selecciónTabla + " where concat (Titulo_Libro,' ',Nombre_Autor_Libro) LIKE '%"+string+"%'";

                String datos[] = new String[10];

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

        void mostrarMenu()
        {
                comboBox1.addItem("Libros");
                comboBox1.addItem("Revistas");
                comboBox1.addItem("Investigaciones");
                comboBox1.addItem("Software");
        }

        /**
         * Main function of the class to view the form.
         * @param args
         */
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
        private JButton deleteBtn;
        private JTextField txtSearch;
        private JComboBox comboBox1;
        // End of the elements used on the form
        
        Conectar con = new Conectar();
        Connection cn = con.conexion();

        private void createUIComponents() {
                // TODO: place custom component creation code here
        }
}



