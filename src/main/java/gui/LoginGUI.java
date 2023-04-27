package gui;

/**
 * @author Software Engineering teachers
 */

import javax.swing.*;

import domain.Usuario;
import businessLogic.BLFacade;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnLogin;
	private JLabel lblPassword;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JButton btnNoAcc;
	private JLabel lblChooseLang;
	private JLabel lblError;
	private JLabel lblUsername;

	private JPanel jContentPane = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public static void setBussinessLogic(BLFacade afi) {
		appFacadeInterface = afi;
	}

	protected JLabel jLabelSelectOption = new JLabel();
	private JPanel panel;

	/**
	 * This is the default constructor
	 */
	public LoginGUI() {
		super();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(new Dimension(441, 290));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI"));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initialises jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblChooseLang());
			jContentPane.add(lblPassword());
			jContentPane.add(btnLogin());
			jContentPane.add(lblChooseLang());
			jContentPane.add(btnNoAcc());
			jContentPane.add(lblError());
			jContentPane.add(textField());
			jContentPane.add(passwordField());
			jContentPane.add(btnNoAcc());
			jContentPane.add(lblError());
			jContentPane.add(getPanel());
			jContentPane.add(lblUsername());
		}
		return jContentPane;
	}

	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("eng"));
			rdbtnNewRadioButton.setBounds(46, 6, 103, 21);
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}

	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("eus"));
			rdbtnNewRadioButton_1.setBounds(162, 6, 103, 21);
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}

	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("esp"));
			rdbtnNewRadioButton_2.setBounds(318, 6, 103, 21);
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 221, 427, 32);
			panel.setLayout(null);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());

		}
		return panel;
	}

	private JLabel lblError() {
		if (lblError == null) {
			lblError = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblError"));
			lblError.setHorizontalAlignment(SwingConstants.CENTER);
			lblError.setBounds(83, 106, 269, 13);
			lblError.setForeground(Color.red);
			lblError.setVisible(false);

		}
		return lblError;
	}

	private void redibujar() {
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("btnLogin"));
		lblPassword.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		btnNoAcc.setText(ResourceBundle.getBundle("Etiquetas").getString("noAcc"));
		lblChooseLang.setText(ResourceBundle.getBundle("Etiquetas").getString("ChooseLang"));
		lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("lblError"));
		lblUsername.setText(ResourceBundle.getBundle("Etiquetas").getString("Username"));
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	private JTextField textField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(224, 39, 159, 19);
			textField.setColumns(10);
		}
		return textField;
	}

	private JLabel lblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password"));
			lblPassword.setBounds(36, 84, 145, 13);
			lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblPassword;
	}

	private JButton btnNoAcc() {
		if (btnNoAcc == null) {
			btnNoAcc = new JButton(ResourceBundle.getBundle("Etiquetas").getString("noAcc"));
			btnNoAcc.setBounds(250, 129, 102, 21);
			btnNoAcc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RegisterGUI ventana = new RegisterGUI();
					ventana.setUserText(textField.getText());
					ventana.setPasswordText(new String(passwordField.getPassword()));
					ventana.setVisible(true);
					dispose();
					
				}
			});
		}
		return btnNoAcc;
	}

	private JLabel lblChooseLang() {
		if (lblChooseLang == null) {
			lblChooseLang = new JLabel();
			lblChooseLang.setBounds(145, 190, 159, 13);
			lblChooseLang.setText(ResourceBundle.getBundle("Etiquetas").getString("ChooseLang"));
			lblChooseLang.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblChooseLang;
	}

	private JPasswordField passwordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(224, 81, 159, 19);
		}
		return passwordField;
	}

	private JLabel lblUsername() {
		if (lblUsername == null) {
			lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username"));

			lblUsername.setBounds(36, 42, 145, 13);
			lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblUsername;
	}

	private JButton btnLogin() {
		if (btnLogin == null) {
			btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnLogin"));
			btnLogin.setBounds(56, 129, 111, 21);
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblError.setVisible(false);
					String us = textField.getText();
					String ps = new String(passwordField.getPassword());
					BLFacade logicaNegocio = LoginGUI.getBusinessLogic();
					if (logicaNegocio.login(us, ps)) {
						Usuario user = logicaNegocio.getUsuario(us);
						if (user.esAdmin()) {
							AdminGUI a = new AdminGUI();
							a.setBussinessLogic(logicaNegocio);
							a.setVisible(true);
							
						} else {
							UsuarioGUI b = new UsuarioGUI();
							b.setBussinessLogic(logicaNegocio);
							b.setVisible(true);
						}
						dispose();
					} else {
						lblError.setVisible(true);
					}

				}
			});

		}
		return btnLogin;
	}

}
