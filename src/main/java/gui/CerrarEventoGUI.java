package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.Usuario;

public class CerrarEventoGUI extends JFrame {

	private JPanel contentPane;    
	private JComboBox<Question> comboBoxPreguntas;
    private JComboBox<Pronostico> comboBoxPronosticos;
	private JButton btnVolver, btnResolver;
    private JLabel lblMarcaRespuestas, lblPreguntas, lblPronostico;
    private DefaultComboBoxModel<Question> modeloPreguntas;
    private DefaultComboBoxModel<Pronostico> modeloPronosticos = new DefaultComboBoxModel<Pronostico>();
	
    
    private  BLFacade facade;

	public  BLFacade getBusinessLogic() {
		return facade;
	}

	public void setBussinessLogic(BLFacade b){
        this.facade = b;
    }
    
	public CerrarEventoGUI(Usuario u, Event ev) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		modeloPreguntas = new DefaultComboBoxModel<Question>();
		

        comboBoxPreguntas = new JComboBox<Question>();
		
		for (Question q : ev.getQuestions()) { //llena el combobox de preguntas
			
			if (q.getResult()==null) {
				modeloPreguntas.addElement(q);
			}
		}
		comboBoxPreguntas.setModel(modeloPreguntas);
		comboBoxPreguntas.setSelectedItem(null);
        comboBoxPreguntas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (comboBoxPreguntas.getItemCount()==0) {
					System.out.println("combobox vacio");
				} else{
					comboBoxPronosticos.removeAllItems();
					comboBoxPronosticos.setSelectedItem(null);
					Question q = (Question) comboBoxPreguntas.getSelectedItem();
					if (q != null) {
						for (Pronostico p : q.getPronosticos()) {
							comboBoxPronosticos.addItem(p);
						}	
					}
					
				}
				
        	}
        });
		contentPane.setLayout(null);
		comboBoxPreguntas.setBounds(132, 101, 237, 21);
		this.getContentPane().add(comboBoxPreguntas);
	



		comboBoxPronosticos = new JComboBox<Pronostico>();
		comboBoxPronosticos.setBounds(132, 130, 237, 21);
        comboBoxPronosticos.setModel(modeloPronosticos);
		this.getContentPane().add(comboBoxPronosticos);
		
		
		btnVolver = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblSalir"));
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                AdminGUI ventana = new AdminGUI(u);
                ventana.setVisible(true);
				dispose();
                
			}
		});
		btnVolver.setBounds(116, 232, 164, 21);
		this.getContentPane().add(btnVolver);
		
		lblMarcaRespuestas = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblCerrarEvento"));
		lblMarcaRespuestas.setBounds(49, 40, 304, 31);
		this.getContentPane().add(lblMarcaRespuestas);
		
		lblPreguntas = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
		lblPreguntas.setBounds(49, 105, 107, 13);
		this.getContentPane().add(lblPreguntas);
		
		lblPronostico = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("predictions"));
		lblPronostico.setBounds(49, 132, 107, 13);
		this.getContentPane().add(lblPronostico);
		
		btnResolver = new JButton(ResourceBundle.getBundle("Etiquetas").getString("resolverPregunta"));
		btnResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					System.out.println(comboBoxPreguntas.getSelectedItem().toString());
					System.out.println(comboBoxPronosticos.getSelectedItem().toString());
					Question qu = (Question) comboBoxPreguntas.getSelectedItem();
					Pronostico pron = (Pronostico) comboBoxPronosticos.getSelectedItem();
					if (comboBoxPreguntas.getItemCount()==1) {
						facade.cerrarEvento(ev, qu, pron, true);
						dispose();
					}
					facade.cerrarEvento(ev, qu, pron, false);
					int index = comboBoxPreguntas.getSelectedIndex();
					
					modeloPreguntas.removeElementAt(index);
					comboBoxPreguntas.setModel(modeloPreguntas);
					comboBoxPreguntas.setSelectedItem(null);
					
					comboBoxPronosticos.removeAllItems();
					//refillComboBoxQ(ev);
				}catch(NullPointerException e1) {
					VentanaAvisos ventana = new VentanaAvisos(ResourceBundle.getBundle("Etiquetas").getString("errorSelecQuestion"), null);
					ventana.setVisible(true);
				}
            }
		});
		btnResolver.setBounds(49, 163, 320, 21);
		this.getContentPane().add(btnResolver);
	}
	

}
