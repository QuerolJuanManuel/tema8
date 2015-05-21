package ejercicio_1;

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

public class Ejercicio_1 extends JFrame {

	private JPanel contentPane;
	private static JTextField textField_5;
	private static JTextField textField_4;
	private static JTextField textField_3;
	private static JTextField textField_2;
	private static JTextField textField_1;
	private static JTextField textField;
	private static JButton btnSiguiente;
	private static Connection con = null;
	private static ResultSet rs = null;
	private static Statement stmt = null;
	
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
	
	public static void connectDB(){
		if(con == null){
			try {
				con =DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "micoche", "micoche");
			} catch (SQLException e) {
				// error al conectar con la DB
				con = null;
				printSQLException(e);
			}
		}
	}
	
	public static void disconnectDB(){
		try{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(stmt !=null){
				stmt.close();
				stmt = null;
			}
			if(con != null){
				con.close();
				con = null;
			}
		}catch(SQLException e){
			printSQLException(e);
		}
	}
	
	public static void verTablaCoches(String BDNombre) throws SQLException{
		connectDB();
		String query= "select MATRICULA, MARCA, MODELO, COLOR, ANIO, PRECIO from "+ BDNombre+".coches";
		try {
			
			if(rs == null){
				stmt =con.createStatement();
				rs=stmt.executeQuery(query);
				rs.beforeFirst();
			}
			if( rs.next()){
				String matricula=rs.getString("MATRICULA");
				textField.setText(matricula);
				String marca=rs.getString("MARCA");
				textField_1.setText(marca);
				String modelo=rs.getString("MODELO");
				textField_2.setText(modelo);
				String color=rs.getString("COLOR");
				textField_3.setText(color);
				int anio=rs.getInt("ANIO");
				textField_4.setText(String.valueOf(anio));
				int precio=rs.getInt("PRECIO");
				textField_5.setText(String.valueOf(precio));
			}
			if(rs.isLast()){
				disconnectDB();
				btnSiguiente.setEnabled(false);
			}
		}catch(SQLException e){
				printSQLException(e);
				disconnectDB();
		}
	}		

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio_1 frame = new Ejercicio_1();
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
	public Ejercicio_1() {
		setTitle("Coches en stock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMatricula = new JLabel("Matricula:");
		lblMatricula.setBounds(84, 28, 78, 14);
		contentPane.add(lblMatricula);
		
		JLabel lblNewLabel = new JLabel("Marca:");
		lblNewLabel.setBounds(84, 53, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(84, 78, 46, 14);
		contentPane.add(lblModelo);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(84, 103, 46, 14);
		contentPane.add(lblColor);
		
		JLabel lblAo = new JLabel("A\u00F1o:");
		lblAo.setBounds(84, 128, 46, 14);
		contentPane.add(lblAo);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(84, 157, 46, 14);
		contentPane.add(lblPrecio);
		
		textField = new JTextField();
		textField.setBounds(172, 25, 117, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(172, 50, 117, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(172, 75, 117, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(172, 100, 117, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(172, 125, 117, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(172, 154, 117, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					verTablaCoches("concesionario");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSiguiente.setBounds(84, 185, 205, 23);
		contentPane.add(btnSiguiente);
	}
}
