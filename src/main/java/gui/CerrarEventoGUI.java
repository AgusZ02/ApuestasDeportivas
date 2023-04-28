package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
	public CerrarEventoGUI(Event ev) {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		comboBoxPreguntas.setBounds(116, 101, 237, 21);
		this.getContentPane().add(comboBoxPreguntas);
	



		comboBoxPronosticos = new JComboBox<Pronostico>();
		comboBoxPronosticos.setBounds(116, 132, 237, 21);
        comboBoxPronosticos.setModel(modeloPronosticos);
		this.getContentPane().add(comboBoxPronosticos);
		
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                AdminGUI ventana = new AdminGUI();
                ventana.setVisible(true);
				dispose();
                
			}
		});
		btnVolver.setBounds(116, 232, 164, 21);
		this.getContentPane().add(btnVolver);
		
		lblMarcaRespuestas = new JLabel("Marca los pronósticos correctos de cada pregunta.");
		lblMarcaRespuestas.setBounds(49, 40, 265, 13);
		this.getContentPane().add(lblMarcaRespuestas);
		
		lblPreguntas = new JLabel("Preguntas");
		lblPreguntas.setBounds(49, 105, 107, 13);
		this.getContentPane().add(lblPreguntas);
		
		lblPronostico = new JLabel("Pronostico");
		lblPronostico.setBounds(49, 132, 107, 13);
		this.getContentPane().add(lblPronostico);
		
		btnResolver = new JButton("Resolver pregunta");
		btnResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
            }
		});
		btnResolver.setBounds(49, 163, 304, 21);
		this.getContentPane().add(btnResolver);
	}
	

}
