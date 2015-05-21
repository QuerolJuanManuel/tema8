package ejercicio_2;

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

public class Ejercicio_2 extends JFrame {

	private JPanel contentPane;
	private static JTextField textField_5;
	private static JTextField textField_4;
	private static JTextField textField_3;
	private static JTextField textField_2;
	private static JTextField textField_1;
	private static JTextField textField;
	private static JLabel lblNewLabel_1;
	private static JLabel label;
	private static Connection con = null;
	private static ResultSet rs = null;
	private static Statement stmt = null;
	private static JButton btnSiguiente;
	
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
	
	public static void insertarCoches(Connection con, String BDNombre, String matricula, String marca,String modelo, String color, int anio, int precio) throws SQLException{
		Statement stmt=null;
			try{
				stmt=con.createStatement();
				stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs=stmt.executeQuery("SELECT * FROM "+BDNombre+ ".coches");
				rs.moveToInsertRow();
				rs.updateString("MATRICULA", matricula);
				rs.updateString("MARCA", marca);
				rs.updateString("MODELO", modelo);
				rs.updateString("COLOR", color);
				rs.updateInt("ANIO", anio);
				rs.updateInt("PRECIO", precio);
				rs.insertRow();
				rs.beforeFirst();
				lblNewLabel_1.setText("Coche insertado");
			}catch(SQLException e){
				printSQLException(e);
				lblNewLabel_1.setText("El coche no se puede insertar");
			}finally {
				stmt.close();
			}
	}
	
	public static void borrarCoches(Connection con,String BDNombre, String matricula) throws SQLException{
		Statement stmt=null;
		try{
			stmt=con.createStatement();
			stmt.execute("DELETE FROM "+BDNombre+".coches"+" WHERE MATRICULA = '"+matricula+"'");
				label.setText("Coche borrado");
		}catch(SQLException e){
			printSQLException(e);
			label.setText("El coche no se puede borrar");
		}finally {
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
					Ejercicio_2 frame = new Ejercicio_2();
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
	public Ejercicio_2() {
		setTitle("Coches en stock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMatricula = new JLabel("Matricula:");
		lblMatricula.setBounds(84, 11, 78, 14);
		contentPane.add(lblMatricula);
		
		JLabel lblNewLabel = new JLabel("Marca:");
		lblNewLabel.setBounds(84, 36, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(84, 61, 46, 14);
		contentPane.add(lblModelo);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(84, 86, 46, 14);
		contentPane.add(lblColor);
		
		JLabel lblAo = new JLabel("A\u00F1o:");
		lblAo.setBounds(84, 111, 46, 14);
		contentPane.add(lblAo);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(84, 136, 46, 14);
		contentPane.add(lblPrecio);
		
		textField = new JTextField();
		textField.setBounds(172, 11, 117, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(172, 33, 117, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(172, 58, 117, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(172, 83, 117, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(172, 108, 117, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(172, 136, 117, 20);
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
		btnSiguiente.setBounds(84, 167, 205, 23);
		contentPane.add(btnSiguiente);
		
		JButton btnAadir = new JButton("Insertar");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection con = null;
				try {
					con =DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "micoche", "micoche");
					insertarCoches(con, "concesionario", textField.getText(),textField_1.getText(),textField_2.getText(), textField_3.getText(),Integer.parseInt(textField_4.getText()), Integer.parseInt(textField_5.getText() ));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnAadir.setBounds(84, 201, 205, 23);
		contentPane.add(btnAadir);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection con = null;
				try {
					con =DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "micoche", "micoche");
					borrarCoches(con,"concesionario", textField.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBorrar.setBounds(84, 235, 205, 23);
		contentPane.add(btnBorrar);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(309, 205, 209, 14);
		contentPane.add(lblNewLabel_1);
		
		label = new JLabel("");
		label.setBounds(309, 239, 209, 14);
		contentPane.add(label);
	}
}
