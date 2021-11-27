package Frames;


// Imports from the libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Class.Conectar;

import java.awt.event.*;
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
        private String selecciónTabla = "";
        private String[] campos;
        private JTextField[] jfields;
        private String metodoAltaSeleccion;
        private String metodoBajaSeleccion;
        private String metodoModifcacionSeleccion;

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

                jfields = new JTextField[11];
                jfields[0] = txtID;
                jfields[1] = txtIsbn;
                jfields[2] = txtTitle;
                jfields[3] = txtAuthor;
                jfields[4] = txtLastName;
                jfields[5] = txtLastName2;
                jfields[6] = txtDatePub;
                jfields[7] = txtEditorial;
                jfields[8] = txtEdition;
                jfields[9] = txtGenere;
                jfields[10] = textField11;

                mostrarMenu();

                // adding an action to the save button
                saveBtn.addActionListener(new ActionListener()
                {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                                try
                                {
                                        // Preparing the call to store the inputs.
                                        CallableStatement ps = cn.prepareCall("{call "+ metodoAltaSeleccion + generarQueryModificar() +"}");

                                        for (int i = 0; i < campos.length; i++){
                                                ps.setString(i+1,jfields[i].getText());
                                                System.out.print(i+1+" "+jfields[i].getText());
                                        }

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
                                        CallableStatement ps = cn.prepareCall("{call "+ metodoModifcacionSeleccion + generarQueryModificar() +"}");

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

                // The main functions when a user press the DELETE button.
                deleteBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                                try{
                                // Create a string with the SQLStatement
                                CallableStatement ps = cn.prepareCall("{call "+ metodoBajaSeleccion + "(?)}");

                                int respuesta = ps.executeUpdate();

                                if(respuesta > 0)
                                {

                                        JOptionPane.showMessageDialog(null,"Registro eliminado");
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

                // Adding a mouse click listener to select the row.
                table.addMouseListener(new MouseAdapter()
                {

                        public void mouseClicked(MouseEvent e)
                        {
                                super.mouseClicked(e);
                                selecionarFila();
                        }
                });

                //Buscar
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
                                                campos[0] = "Id_Libro";
                                                campos[1] = "ISBD_Libro";
                                                campos[2] = "Titulo_Libro";
                                                campos[3] = "Nombre_Autor_Libro";
                                                campos[4] = "Pimer_Apellido_Autor_Libro";
                                                campos[5] = "Segundo_Apellido_Autor_Libro";
                                                campos[6] = "Fecha_Pub_Libro";
                                                campos[7] = "Editorial_Libro";
                                                campos[8] = "Edicion_Libro";
                                                campos[9] = "Genero_Libro";
                                                // Colocar las labels correspondientes a la tabla
                                                label1.setText("Id");
                                                label2.setText("ISBD");
                                                label3.setText("Titulo");
                                                label4.setText("Nombre");
                                                label5.setText("Primer Apellido");
                                                label6.setText("Segundo Apellido");
                                                label7.setText("Fecha");
                                                label8.setText("Editorial");
                                                label9.setText("Edicion");
                                                label10.setText("Genero");

                                                // Cambiar el titulo
                                                labelTitulo.setText("Registro de libros");
                                                // Quitar lo restante.
                                                label11.setVisible(false);
                                                textField11.setVisible(false);

                                                String sqlPrueba = generarQueryModificar();

                                                // Setear las llamadas para el caso de libros
                                                metodoAltaSeleccion = "altaLibros";
                                                metodoBajaSeleccion = "bajaLibros";
                                                metodoModifcacionSeleccion = "modificarLibros";

                                                System.out.println(sqlPrueba);
                                                System.out.println("{call "+ metodoAltaSeleccion + generarQueryModificar() +"}");
                                                mostrarTabla("");
                                        break;
                                        case "Revistas":
                                                campos = new String[11];
                                                campos[0] = "Id_Revista";
                                                campos[1] = "ISBN_Revista";
                                                campos[2] = "Nombre_Revista";
                                                campos[3] = "Anio_Revista";
                                                campos[4] = "Editorial_Revista";
                                                campos[5] = "Ciudad_Revista";
                                                campos[6] = "Volumen_Revista";
                                                campos[7] = "Numero_Revista";
                                                campos[8] = "Nombre_Autor_Revista";
                                                campos[9] = "Primer_Apellido_Autor_Revista";
                                                campos[10] = "Segundo_Apellido_Autor_Revista";

                                                // Colocar visibles el text field y el label
                                                label11.setVisible(true);
                                                textField11.setVisible(true);

                                                // Colocar las labels correspondientes a la tabla
                                                label1.setText("ID");
                                                label2.setText("ISBN");
                                                label3.setText("Nombre");
                                                label4.setText("Año");
                                                label5.setText("Editorial");
                                                label6.setText("Ciudad");
                                                label7.setText("Volumen");
                                                label8.setText("Numero");
                                                label9.setText("Autor");
                                                label10.setText("Primer apellido");
                                                label11.setText("Segundo apellido");

                                                // Cambiar el titulo del registro.
                                                labelTitulo.setText("Registro de revistas");


                                                // Setear las llamadas para el caso de revistas
                                                metodoAltaSeleccion = "altaRevistas";
                                                metodoBajaSeleccion = "bajaRevistas";
                                                metodoModifcacionSeleccion = "modificarRevistas";

                                                mostrarTabla("");
                                        break;
                                        case "Investigaciones":
                                                campos = new String[9];
                                                campos[0] = "Id_Investigacion";
                                                campos[1] = "Fecha_Investigacion";
                                                campos[2] = "Nombre_Investigacion";
                                                campos[3] = "Tema_Investigacion";
                                                campos[4] = "Nombre_Autor_Principal";
                                                campos[5] = "Apellido_Paterno_Autor_Principal";
                                                campos[6] = "Apellido_Materno_Autor_Principal";
                                                campos[7] = "Edicion_Investigacion";
                                                campos[8] = "Fecha_Terminacion_Investigacion";
                                                //Colocar los labels correspondientes a esta tabla
                                                label1.setText("Id");
                                                label2.setText("Fecha");
                                                label3.setText("Nombre");
                                                label4.setText("Tema");
                                                label5.setText("Autor");
                                                label6.setText("Primer Apellido");
                                                label7.setText("Segundo Apellido");
                                                label8.setText("Edicion");
                                                label9.setText("Finalizacion");
                                                //Aparecer y desaparecer labels y textField necesarios
                                                label10.setVisible(false);
                                                txtGenere.setVisible(false);
                                                textField11.setVisible(false);
                                                label11.setVisible(false);

                                                //Cambiar titulo del registro
                                                labelTitulo.setText("Registro de investigaciones");

                                                // Setear las llamadas para el caso de revistas
                                                metodoAltaSeleccion = "altaInvestigaciones";
                                                metodoBajaSeleccion = "bajaInvestigaciones";
                                                metodoModifcacionSeleccion = "modificarInvestigaciones";


                                                mostrarTabla("");
                                        break;
                                        case "Software":
                                                campos = new String[8];
                                                campos[0] = "Id_Software";
                                                campos[1] = "Nombre_Software";
                                                campos[2] = "Empresa_Software";
                                                campos[3] = "Desarrollador_Principal";
                                                campos[4] = "Fecha_Lanzamiento";
                                                campos[5] = "Version_Software";
                                                campos[6] = "Tipo_Software";
                                                campos[7] = "Compatibilidad_SO";
                                                //Colocar los labels correspondientes a esta tabla
                                                label1.setText("Id");
                                                label2.setText("Nombre");
                                                label3.setText("Empresa");
                                                label4.setText("Derrollador(a)");
                                                label5.setText("Lanzamiento");
                                                label6.setText("Version");
                                                label7.setText("Tipo");
                                                label8.setText("Compatibilidad");
                                                //Aparecer y desaparecer labels y textField necesarios

                                                label9.setVisible(false);
                                                txtGenere.setVisible(false);
                                                txtEdition.setVisible(false);
                                                textField11.setVisible(false);
                                                label11.setVisible(false);
                                                //Cambiar titulo del registro
                                                labelTitulo.setText("Registro de software");

                                                // Setear las llamadas para el caso de revistas
                                                metodoAltaSeleccion = "altaSoftware";
                                                metodoBajaSeleccion = "bajaSoftware";
                                                metodoModifcacionSeleccion = "modificarSoftware";


                                                mostrarTabla("");
                                        break;

                                }

                        }
                });
        }

        private String generarQueryModificar() {
                String sqlPrueba = " (";
                for (int i = 0; i <= campos.length - 1; i++)
                {
                        if(i ==0)
                        {
                                sqlPrueba = sqlPrueba.concat("?");
                        }else
                                sqlPrueba = sqlPrueba.concat(",?");

                        if (i == campos.length - 1){
                                sqlPrueba = sqlPrueba.concat(")");
                        }

                }
                return sqlPrueba;
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
                textField11.setText("");
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

                for(int i = 0; i < campos.length; i++)
                {
                        modelo.addColumn(campos[i]);
                }
                table.setModel(modelo);

                String sql = "select * from "+ selecciónTabla + " where concat ("+campos[3]+",' ',"+campos[4]+") LIKE '%"+string+"%'";

                String datos[] = new String[campos.length];

                Statement st;

                try {
                        st = cn.createStatement();
                        ResultSet rs =st.executeQuery(sql);
                        while(rs.next())
                        {
                                // For que rellana la tabla dependiendo de la tabla seleccionada en SQL.
                                for(int i = 0; i < datos.length; i++)
                                {
                                        datos[i]=rs.getString(  i+1);
                                }
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
        private JPanel Jpanel;
        private JLabel labelTitulo;
        private JPanel PanelRegistro;
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
        private JLabel label1;
        private JLabel label2;
        private JLabel label3;
        private JLabel label4;
        private JLabel label5;
        private JLabel label6;
        private JLabel label7;
        private JLabel label8;
        private JLabel label9;
        private JLabel label10;
        private JLabel label11;
        private JTextField textField11;
        // End of the elements used on the form
        
        Conectar con = new Conectar();
        Connection cn = con.conexion();

        private void createUIComponents() {
                // TODO: place custom component creation code here
        }
}



