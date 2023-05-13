package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import businessLogic.BLFacade;
import domain.Usuario;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame{
	private JTextField textUser;
	private JTextField textName;
	private JTextField textDNI;
	private JPasswordField passwordField;
    private JLabel lblError;

	public RegisterGUI() {
		getContentPane().setLayout(null);
		this.setSize(new Dimension(474, 365));
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("noAcc"));
		textUser = new JTextField();
		textUser.setBounds(40, 65, 369, 20);
		getContentPane().add(textUser);
		textUser.setColumns(10);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(40, 165, 369, 20);
		getContentPane().add(textName);
		
		textDNI = new JTextField();
		textDNI.setColumns(10);
		textDNI.setBounds(40, 215, 369, 20);
		getContentPane().add(textDNI);
		
		JLabel lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username"));
		lblUser.setBounds(40, 50, 369, 10);
		getContentPane().add(lblUser);
		
		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setBounds(40, 200, 369, 10);
		getContentPane().add(lblDNI);
		
		JLabel lblName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Nombre"));
		lblName.setBounds(40, 150, 369, 10);
		getContentPane().add(lblName);
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPassword.setBounds(40, 100, 369, 10);
		getContentPane().add(lblPassword);
		
		JButton btnAceptar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
		btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setVisible(false);
                String us = textUser.getText();
                String ps = new String(passwordField.getPassword());
                BLFacade logicaNegocio = LoginGUI.getBusinessLogic();
                
                if (textDNI.getText().isEmpty() || textName.getText().isEmpty() || textUser.getText().isEmpty() || passwordField.getPassword().length == 0) {
                    lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("CampoVacio"));
                    lblError.setVisible(true);
                    lblError.setForeground(Color.RED);
                } else {
                	boolean bool = logicaNegocio.existsUser(us);
                    if (bool) {
                        lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("UserInvalido"));
                        lblError.setVisible(true);
                        lblError.setForeground(Color.RED);
                    } else {
						if (textDNI.getText().length() != 9 || !Character.isLetter(textDNI.getText().charAt(textDNI.getText().length()-1))) {
							
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DniInvalido"));
							lblError.setVisible(true);
							lblError.setForeground(Color.RED);
						} else{
							try {
								System.out.println(Integer.parseInt(textDNI.getText().substring(0, textDNI.getText().length()-1))); 
								lblError.setForeground(Color.GREEN);
								lblError.setVisible(false);
								Usuario newUser = new Usuario(us, ps);
								newUser.setDni(textDNI.getText());							
								newUser.setNombre(textName.getText());
								logicaNegocio.createUser(newUser);
								lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("UsuarioCreado"));
								lblError.setVisible(true);
								
							} catch (Exception e2) {
								lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DniInvalido"));
								lblError.setVisible(true);
								lblError.setForeground(Color.RED);
							}
						}
                    }
                }                
            }
        });
		btnAceptar.setBounds(238, 286, 171, 32);
		getContentPane().add(btnAceptar);
		
		JButton btnSalir = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblSalir"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI ventana = new LoginGUI();
				ventana.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(41, 286, 171, 32);
		getContentPane().add(btnSalir);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(40, 115, 369, 20);
		getContentPane().add(passwordField);
		
		lblError = new JLabel("");
		lblError.setBounds(40, 163, 326, 14);
		getContentPane().add(lblError);
	}
    public void setUserText(String string) {
    	textUser.setText(string);
    }
    public void setPasswordText(String string) {
        passwordField.setText(string);
    }
    
}
