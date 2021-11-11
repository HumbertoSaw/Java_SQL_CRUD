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

                //mostrarTabla("");
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
                                                campos[0] = "Id_Libros";
                                                campos[1] = "ISBD_Libro";
                                                campos[2] = "Titulo_Libro";
                                                campos[3] = "Nombre_Autor_Libro";
                                                campos[4] = "Pimer_Apellido_Autor_Libro";
                                                campos[5] = "Segundo_Apellido_Autor_Libro";
                                                campos[6] = "Fecha_Pub_Libro";
                                                campos[7] = "Editorial_Libro";
                                                campos[8] = "Edicion_Libro";
                                                campos[9] = "Genero_Libro";

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
                                                campos[8] = "Autor_Revista";
                                                campos[9] = "Primer_Apellido_Autor_Revista";
                                                campos[10] = "Segundo_Apellido_Autor_Revista";

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



