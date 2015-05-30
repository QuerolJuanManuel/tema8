package ejercicio_4;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;

public class ComprobadorPrimitivas extends JFrame {

	private JPanel contentPane;
	private static JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JTextField textField_5;
	private static JTextField textField_7;
	private static JLabel lblE;
	private static JLabel label;

	
	public static void printSQLException(SQLException ex) {
		ex.printStackTrace(System.err);
		System.err.println("SQLState: " + ex.getSQLState());
		System.err.println("Error code: " + ex.getErrorCode());
		System.err.println("Message: " + ex.getMessage());
		Throwable t = ex.getCause();
		while (t != null) {
			System.out.println("Cause: " + t);
			t = ex.getCause();
		}
	}
	
	public static void comprobarPrimitiva(Connection con, String BDNombre)throws SQLException{
		Statement stmt =null;
		String query ="select num1, num2, num3, num4, num5, complementario, jornada, fecha from "+BDNombre+".primitiva";
		try{
			stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.beforeFirst();
			rs.next();
			while(rs.getInt("jornada")!=Integer.parseInt(textField_7.getText())){
			rs.next();
			}
			if(rs.getInt("jornada")==Integer.parseInt(textField_7.getText())){
				label.setText(rs.getString("fecha"));
				if(rs.getInt("num1")==Integer.parseInt(textField.getText()) && rs.getInt("num2")==Integer.parseInt(textField_1.getText()) && rs.getInt("num3")==Integer.parseInt(textField_2.getText()) && rs.getInt("num4")==Integer.parseInt(textField_3.getText()) && rs.getInt("num5")==Integer.parseInt(textField_4.getText()) && rs.getInt("complementario")==Integer.parseInt(textField_5.getText()) && rs.getInt("jornada")==Integer.parseInt(textField_7.getText()) ){
					lblE.setText("Boleto GANADOR!!");
				}else {
					lblE.setText("Boleto no premiado");
				}
			}
		}catch (SQLException e){
			printSQLException(e);
		}finally{
			stmt.close();
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComprobadorPrimitivas frame = new ComprobadorPrimitivas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ComprobadorPrimitivas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Inserta los numeros y la jornada del boleto a comprobar");
		lblNewLabel.setBounds(10, 76, 318, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 116, 43, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNum = new JLabel("Num1");
		lblNum.setBounds(10, 91, 43, 14);
		contentPane.add(lblNum);
		
		textField_1 = new JTextField();
		textField_1.setBounds(63, 116, 43, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNum_1 = new JLabel("Num2");
		lblNum_1.setBounds(60, 91, 46, 14);
		contentPane.add(lblNum_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(116, 116, 43, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNum_2 = new JLabel("Num3");
		lblNum_2.setBounds(116, 91, 46, 14);
		contentPane.add(lblNum_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(169, 116, 43, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNum_3 = new JLabel("Num4");
		lblNum_3.setBounds(166, 91, 46, 14);
		contentPane.add(lblNum_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(222, 116, 43, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNum_4 = new JLabel("Num5");
		lblNum_4.setBounds(222, 91, 46, 14);
		contentPane.add(lblNum_4);
		
		textField_5 = new JTextField();
		textField_5.setBounds(312, 116, 43, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblComplementario = new JLabel("Complementario");
		lblComplementario.setBounds(299, 91, 96, 14);
		contentPane.add(lblComplementario);
		
		JButton btnNewButton = new JButton("Comprobar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				Connection con = null;
				try {
					con =DriverManager.getConnection("jdbc:mysql://localhost:3306/primitiva", "primitiva", "primitiva");
					comprobarPrimitiva(con, "primitiva");
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		btnNewButton.setBounds(134, 208, 148, 43);
		contentPane.add(btnNewButton);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(7, 167, 46, 14);
		contentPane.add(lblFecha);
		
		JLabel lblJornada = new JLabel("Jornada");
		lblJornada.setBounds(253, 167, 59, 14);
		contentPane.add(lblJornada);
		
		textField_7 = new JTextField();
		textField_7.setBounds(312, 164, 43, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		lblE = new JLabel("");
		lblE.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblE.setBounds(33, 11, 337, 54);
		contentPane.add(lblE);
		
		label = new JLabel("");
		label.setBounds(63, 167, 96, 14);
		contentPane.add(label);
	}
}
