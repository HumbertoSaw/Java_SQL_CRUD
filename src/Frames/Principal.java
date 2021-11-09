package Frames;


// Imports from the libraries
import Clases.Conection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * Class that allow to a user insert new information on the DB
 * Version: 08/11/2021
 *
 */
public class Principal extends javax.swing.JFrame{


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
        private JTextField txtGender;
        private JTextField txtTitle;
        // End of the elements used on the form


        /**
         * Main method that allow run the form called: Principal
         * @param args
         */

        public static void main(String[] args) {
                JFrame frame = new JFrame("Principal");
                frame.setContentPane(new Principal().Jpanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
        }
}



