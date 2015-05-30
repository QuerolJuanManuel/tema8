package ejercicio_3;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GuardarPrimitiva extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private static JTextField textField_7;
	private static JTextField textField_6;
	private static JLabel lblNewLabel;

	
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
	
	public static void insertarPrimitiva(Connection con, String BDNombre, int num1, int num2, int num3, int num4, int num5, int complementario, int jornada, String fecha) throws SQLException{
		Statement stmt=null;
			try{
				stmt=con.createStatement();
				stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs=stmt.executeQuery("SELECT * FROM "+BDNombre+ ".primitiva");
				rs.moveToInsertRow();
				rs.updateInt("num1", num1);
				rs.updateInt("num2", num2);
				rs.updateInt("num3", num3);
				rs.updateInt("num4", num4);
				rs.updateInt("num5", num5);
				rs.updateInt("complementario", complementario);
				rs.updateInt("jornada", jornada);
				rs.updateString("fecha", fecha);
				rs.insertRow();
				rs.beforeFirst();
				lblNewLabel.setText("Primitiva guardada");
			}catch(SQLException e){
				printSQLException(e);
				lblNewLabel.setText("La primitiva no se pudo guardar");
			}finally {
				stmt.close();
			}
	}
	
	public static void comprobarJornada(Connection con, String BDNombre)throws SQLException{
		Statement stmt =null;
		String query ="select jornada from "+BDNombre+".primitiva";
		try{
			stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.beforeFirst();
			if(rs.next()==false){
				textField_7.setText(String.valueOf(1));
			}else{
			rs.last();
				textField_7.setText(String.valueOf( rs.getInt("jornada")+1));
			}
		}catch (SQLException e){
			printSQLException(e);
		}finally{
			stmt.close();
		}
			}
	
	public static void insertarFechaHoy(){
		GregorianCalendar c = new GregorianCalendar();
		textField_6.setText(Integer.toString(c.get(Calendar.DAY_OF_MONTH))+"/"+Integer.toString(c.get(Calendar.MONTH)+1)+"/"+Integer.toString(c.get(Calendar.YEAR)));
	}
			
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuardarPrimitiva frame = new GuardarPrimitiva();
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
	public GuardarPrimitiva() {
		setTitle("Generador de primitivas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Generar y guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					Integer []vector;
					int contador=0;
					vector =new Integer[6];
					while(contador<6){
						Integer num=(int)(Math.random()*49+1);
						boolean encontrado=false;
						for(int i=0;i<contador;i++){
							if(vector[i]==num){
								encontrado=true;
								break;
							}
						}
						if(!encontrado){
							vector[contador]=num;
							contador++;
						}
					}	
				textField.setText(vector[0].toString());
				textField_1.setText(vector[1].toString());
				textField_2.setText(vector[2].toString());
				textField_3.setText(vector[3].toString());
				textField_4.setText(vector[4].toString());
				textField_5.setText(vector[5].toString());
			
			/*Insertamos la fecha de hoy y la primitiva ya generada*/
			Connection con = null;
			try {
				con =DriverManager.getConnection("jdbc:mysql://localhost:3306/primitiva", "primitiva", "primitiva");
				insertarFechaHoy();
				comprobarJornada(con,"primitiva");
				insertarPrimitiva(con, "primitiva", vector[0],vector[1],vector[2],vector[3], vector[4],vector[5],Integer.parseInt(textField_7.getText()), textField_6.getText() );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}
		});
		btnNewButton.setBounds(145, 198, 157, 53);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(10, 111, 44, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(64, 111, 44, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(118, 111, 44, 28);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(172, 111, 44, 28);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(226, 111, 44, 28);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(300, 111, 44, 28);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblComplementario = new JLabel("Complementario");
		lblComplementario.setBounds(291, 86, 116, 14);
		contentPane.add(lblComplementario);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(28, 173, 46, 14);
		contentPane.add(lblFecha);
		
		textField_6 = new JTextField();
		textField_6.setBounds(84, 167, 116, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblJornada = new JLabel("Jornada");
		lblJornada.setBounds(238, 173, 52, 14);
		contentPane.add(lblJornada);
		
		textField_7 = new JTextField();
		textField_7.setBounds(306, 167, 86, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(64, 37, 285, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNum = new JLabel("Num1");
		lblNum.setBounds(10, 86, 46, 14);
		contentPane.add(lblNum);
		
		JLabel lblNum_1 = new JLabel("Num2");
		lblNum_1.setBounds(64, 86, 46, 14);
		contentPane.add(lblNum_1);
		
		JLabel lblNum_2 = new JLabel("Num3");
		lblNum_2.setBounds(118, 86, 46, 14);
		contentPane.add(lblNum_2);
		
		JLabel lblNum_3 = new JLabel("Num4");
		lblNum_3.setBounds(172, 86, 46, 14);
		contentPane.add(lblNum_3);
		
		JLabel lblNum_4 = new JLabel("Num5");
		lblNum_4.setBounds(226, 86, 46, 14);
		contentPane.add(lblNum_4);
	}
}

