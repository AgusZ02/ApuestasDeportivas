package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ADMGUI {

	private JFrame frame;

	public ADMGUI() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI ventana = new AdminGUI();
				ventana.setVisible(true);
			}
		});
		btnCrear.setBounds(10, 73, 414, 67);
		frame.getContentPane().add(btnCrear);
		
		JButton btnResolver = new JButton("Resolver");
		btnResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO resolver
			}
		});
		btnResolver.setBounds(10, 151, 414, 67);
		frame.getContentPane().add(btnResolver);
		
		JLabel lblMenuAdministrador = new JLabel("New label");
		lblMenuAdministrador.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenuAdministrador.setBounds(10, 11, 414, 14);
		frame.getContentPane().add(lblMenuAdministrador);
	
	
	}
}
