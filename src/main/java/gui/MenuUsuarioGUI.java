package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Usuario;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		
		btnApostar = new JButton("Apostar");
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
		
		btnConsultar = new JButton("Ver mis apuestas");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultarApuestasUsuarioGUI ventana = new ConsultarApuestasUsuarioGUI(user);
				ventana.setVisible(true);
				dispose();
							
			}
		});
		btnConsultar.setBounds(10, 90, 208, 23);
		contentPane.add(btnConsultar);
		
		btnRecargar = new JButton("Recargar saldo");
		btnRecargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnRecargar.setBounds(10, 123, 208, 23);
		contentPane.add(btnRecargar);
		
		lblBienvenida = new JLabel("Bienvenid@, " + user.getNombre());
		lblBienvenida.setBounds(10, 11, 208, 14);
		contentPane.add(lblBienvenida);
		
		lblSaldo = new JLabel("Tu saldo es de: " + user.getSaldo());
		lblSaldo.setBounds(10, 31, 208, 14);
		contentPane.add(lblSaldo);
		
		btnCerrar = new JButton("Cerrar sesión");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
