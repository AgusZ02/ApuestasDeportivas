package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAvisos extends JFrame {

	private JPanel contentPane;



	public VentanaAvisos(String mensaje, String titulo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle(titulo);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(155, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblMsj = new JLabel(mensaje);
		lblMsj.setVerticalAlignment(SwingConstants.TOP);
		lblMsj.setBounds(10, 11, 424, 205);
		contentPane.add(lblMsj);
	}
}
