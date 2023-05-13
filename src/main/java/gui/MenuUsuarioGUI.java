package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;
import domain.Usuario;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class MenuUsuarioGUI extends JFrame {

	private JPanel contentPane;
	private static BLFacade businessLogic = LoginGUI.getBusinessLogic();
	private JButton btnApostar, btnConsultar, btnCerrar, btnRecargar;
	private JLabel lblBienvenida, lblSaldo;

	public MenuUsuarioGUI(Usuario user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnApostar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblApostar"));
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApostarUsuarioGUI ventana = new ApostarUsuarioGUI(user); //TODO: cambiar por nueva ventana
				ventana.setBussinessLogic(businessLogic);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnApostar.setBounds(10, 56, 208, 23);
		contentPane.add(btnApostar);
		
		btnConsultar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnVerAPuestas"));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarApuestasUsuarioGUI ventana = new ConsultarApuestasUsuarioGUI(user);
				ventana.setVisible(true);
				dispose();
							
			}
		});
		btnConsultar.setBounds(10, 90, 208, 23);
		contentPane.add(btnConsultar);
		
		btnRecargar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnRecargarSaldo")); //
		btnRecargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecargarSaldoUsuarioGUI ventana = new RecargarSaldoUsuarioGUI(user);
				ventana.setVisible(true);
				dispose();
				
			}
		});
		btnRecargar.setBounds(10, 123, 208, 23);
		contentPane.add(btnRecargar);
		
		lblBienvenida = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblBienvenida") + " " + user.getNombre());
		lblBienvenida.setBounds(10, 11, 208, 14);
		contentPane.add(lblBienvenida);
		
		lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblSaldo") + " " + user.getSaldo());
		lblSaldo.setBounds(10, 31, 208, 14);
		contentPane.add(lblSaldo);
		
		btnCerrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnCerrar"));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnCerrar.setBounds(10, 157, 208, 23);
		contentPane.add(btnCerrar);
	}

    public void setBussinessLogic(BLFacade logicaNegocio) {
		businessLogic = logicaNegocio;

    }



	public static BLFacade getBusinessLogic() {
		return MenuUsuarioGUI.businessLogic;
	}
}
