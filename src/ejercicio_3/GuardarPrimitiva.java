package ejercicio_3;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JButton btnNewButton = new JButton("Generar");
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
			}
		});
		btnNewButton.setBounds(145, 131, 157, 53);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(10, 78, 44, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(64, 78, 44, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(118, 78, 44, 28);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(172, 78, 44, 28);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(226, 78, 44, 28);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(303, 78, 44, 28);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblComplementario = new JLabel("Complementario");
		lblComplementario.setBounds(278, 58, 116, 14);
		contentPane.add(lblComplementario);
	}
}

