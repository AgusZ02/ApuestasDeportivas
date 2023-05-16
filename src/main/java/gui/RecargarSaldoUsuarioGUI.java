package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Usuario;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class RecargarSaldoUsuarioGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldSaldo;
	private JLabel lblSaldoActual, lblAnadir;
	private JButton btnAnadir, btnSalir;
	private double saldo;
	private BLFacade bl = LoginGUI.getBusinessLogic();

	public RecargarSaldoUsuarioGUI(Usuario u) {
		saldo = u.getSaldo();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblSaldoActual = new JLabel();
		lblSaldoActual.setText(String.format(ResourceBundle.getBundle("Etiquetas").getString("lblSaldo")+"%f",saldo));
		lblSaldoActual.setBounds(20, 11, 252, 14);
		contentPane.add(lblSaldoActual);
		
		lblAnadir = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("btnRecargarSaldo"));
		lblAnadir.setBounds(20, 39, 120, 14);
		contentPane.add(lblAnadir);
		
		textFieldSaldo = new JTextField();
		textFieldSaldo.setBounds(140, 36, 132, 20);
		contentPane.add(textFieldSaldo);
		textFieldSaldo.setColumns(10);

		btnAnadir = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnRecargarSaldo"));
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(Double.parseDouble(textFieldSaldo.getText())<=0){
						VentanaAvisos ventana = new VentanaAvisos(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"), 
								ResourceBundle.getBundle("Etiquetas").getString("cantidadIncorrecta"));
						ventana.setVisible(true);
					}
					else{
						bl.addSaldo(u,Double.parseDouble(textFieldSaldo.getText()));
						saldo = u.getSaldo();
						lblSaldoActual.setText(String.format(ResourceBundle.getBundle("Etiquetas").getString("lblSaldo"),saldo));
					}
				} catch (NumberFormatException e1) {
					VentanaAvisos vAvisos = new VentanaAvisos(ResourceBundle.getBundle("Etiquetas").getString("errorNumeroFormato"), null);
					vAvisos.setVisible(true);
				}	
			}
		});
		btnAnadir.setBounds(57, 72, 177, 23);
		contentPane.add(btnAnadir);
		
		
		
		btnSalir = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblSalir"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuarioGUI ventana = new MenuUsuarioGUI(u);
				ventana.setBussinessLogic(bl);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnSalir.setBounds(86, 127, 111, 23);
		contentPane.add(btnSalir);
	}
}
