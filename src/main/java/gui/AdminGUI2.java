package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.Usuario;
import exceptions.EventExpired;
import exceptions.EventFinished;
import exceptions.NotEnoughMoney;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;

public class AdminGUI2 extends JFrame {
	private static BLFacade businessLogic = ApostarUsuarioGUI.getBusinessLogic();
	private JPanel contentPane;

	private DefaultTableModel tableModelQueries;
	private JTable tableQueries = new JTable();
	private DefaultTableModel tableModelProns;
	private JTable tableProns = new JTable();

	private JScrollPane scrollPaneQueries = new JScrollPane();
	private final JScrollPane scrollPanePron = new JScrollPane();

	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel lblPronosticos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblPronosticos"));
	private JLabel lblQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblQuestion"));
	private JLabel lblMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblMinBet"));
	private JLabel lblNewPron = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblNewPron"));
	private JLabel lblMultip = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblMultip"));
	
	private JButton btnNewQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewQuestion"));
	private JButton btnNewPron = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewPron"));
	private JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblSalir")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private JTextField tfQuestion;
	private JTextField tfMinBet;
	private JTextField tfNewPron;
	private JTextField tfMultip;
	
	private String[] columnNamesQueries = new String[] { 
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("BetMin"), 
	};

	private String[] columnNamesProns = new String[] { 
			ResourceBundle.getBundle("Etiquetas").getString("PronN"),
			ResourceBundle.getBundle("Etiquetas").getString("Pron"),
			ResourceBundle.getBundle("Etiquetas").getString("Multip")
	};
	
	public AdminGUI2(Usuario u, Event evento) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		scrollPaneQueries.setBounds(new Rectangle(12, 41, 542, 117));
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		
		scrollPanePron.setBounds(new Rectangle(12, 257, 542, 116));
		scrollPanePron.setViewportView(tableProns);
		tableModelProns = new DefaultTableModel(null, columnNamesProns);
		
		domain.Event ev = businessLogic.findEvent(evento.getEventNumber());
		
		// ******************************************************************************
		// *****************************CONSULTAR PREGUNTAS******************************
		// ******************************************************************************
		Vector<domain.Question> queries = businessLogic.getQuestions(ev);
		if (queries.isEmpty())
			jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
		else
			jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());
		
		try {
			tableModelQueries.setDataVector(null, columnNamesQueries);
			tableModelQueries.setColumnCount(5);
			for (domain.Question q:queries) {
				Vector<Object> row = new Vector<Object>();
				if (q.getResult() == null) {
					row.add(q.getQuestionNumber());
					row.add(q.toString());
					row.add(q.getBetMinimum());
					row.add(q.getResult());
					row.add(q);
					tableModelQueries.addRow(row);	
				}
				System.out.println("Questions " + q);
			}
		} catch(Exception e) {
			VentanaAvisos vent = new VentanaAvisos("Error en tableQueries/tableModelQueries", null);
			vent.setVisible(true);
		}
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				Question qu = (domain.Question) tableModelQueries.getValueAt(i, 4); // obtain q object

				Vector<Pronostico> pronosticos1 = qu.getPronosticos();
				if (pronosticos1.isEmpty())
					lblPronosticos.setText(ResourceBundle.getBundle("Etiquetas").getString("NoPredictions"));
				else
					lblPronosticos.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedPron"));

				try {
					tableModelProns.setDataVector(null, columnNamesProns);
					tableModelProns.setColumnCount(4);
					for (domain.Pronostico p : pronosticos1) {
						Vector<Object> row = new Vector<Object>();
						row.add(p.getPronNumber());
						row.add(p.toString());
						row.add(p.getCuotaGanancia());
						//row.add(p);
						tableModelProns.addRow(row);
						System.out.println("Pron " + p);
					}
				} catch(Exception exc) {
					VentanaAvisos vent = new VentanaAvisos("Error en tableProns/tableModelProns", null);
					vent.setVisible(true);
				}				
			}
		});

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableQueries.getColumnModel().getColumn(2).setPreferredWidth(85);
		tableQueries.getColumnModel().getColumn(3).setPreferredWidth(150);
		tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(4));
		tableQueries.setDefaultEditor(Object.class, null);
		
		this.getContentPane().add(scrollPaneQueries, null);


		tableProns.setModel(tableModelProns);
		tableProns.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableProns.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableProns.getColumnModel().getColumn(2).setPreferredWidth(15);
		//tableProns.getColumnModel().removeColumn(tableProns.getColumnModel().getColumn(3));
		tableProns.setDefaultEditor(Object.class, null);

		this.getContentPane().add(scrollPanePron, null);

		// JLabel (Questions)
		jLabelQueries.setBounds(12, 25, 330, 14);
		this.getContentPane().add(jLabelQueries);
		
		// JLabel (Pronosticos)
		lblPronosticos.setBounds(12, 242, 300, 14);
		this.getContentPane().add(lblPronosticos, null);
		
		// ******************************************************************************
		// ***************************CREAR NUEVA PREGUNTA*****************************
		// ******************************************************************************	
		btnNewQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewQuestion"));
		btnNewQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String quest = tfQuestion.getText();
				float min = Float.parseFloat(tfMinBet.getText());
				if (quest.length() > 0) {

					try {
						businessLogic.createQuestion(ev, quest, min);
						jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCreated"));
					} catch (EventFinished e1) {
						VentanaAvisos error = new VentanaAvisos(
								"<html>Error: evento finalizado.<br/>No es posible añadir una pregunta a un evento finalizado.</html>",
								"EventFinished");
						error.setVisible(true);
						// e1.printStackTrace();
					} catch (QuestionAlreadyExist e1) {
						VentanaAvisos error = new VentanaAvisos(
								"<html>Error: La pregunta ya existe.<br/>No es posible añadir una pregunta duplicada.</html>",
								"QuestionAlreadyExist");
						error.setVisible(true);
						// e1.printStackTrace();
					}
				} else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQuest"));
			}

		});
		btnNewQuestion.setBounds(270, 196, 142, 21);

		getContentPane().add(btnNewQuestion);

		tfQuestion = new JTextField();
		tfQuestion.setBounds(173, 169, 239, 19);
		getContentPane().add(tfQuestion);
		tfQuestion.setColumns(10);

		tfMinBet = new JTextField();
		tfMinBet.setBounds(173, 196, 85, 19);
		getContentPane().add(tfMinBet);
		tfMinBet.setColumns(10);

		lblQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblQuestion"));
		lblQuestion.setBounds(79, 170, 85, 13);
		getContentPane().add(lblQuestion);

		lblMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblMinBet"));
		lblMinBet.setBounds(79, 200, 85, 13);
		getContentPane().add(lblMinBet);
		
		
		
		
		
		
		
		// ******************************************************************************
		// ***************************CREAR NUEVO PRONOSTICO*****************************
		// ******************************************************************************		
		btnNewPron = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewPron"));
		btnNewPron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pron = tfNewPron.getText();
				double mul = (double)Double.parseDouble(tfMultip.getText());
				int i = tableQueries.getSelectedRow();
				Question qu = (domain.Question) tableModelQueries.getValueAt(i, 4);

				if (pron.length() > 0) {

					try {
						businessLogic.createPron(ev, qu, pron, mul);
					} catch (PredictionAlreadyExists e1) {

					}
				} else
					lblPronosticos.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPron"));

			}
		});
		btnNewPron.setBounds(270, 410, 142, 21);
		getContentPane().add(btnNewPron);

		tfNewPron = new JTextField();
		tfNewPron.setBounds(173, 380, 239, 19);
		getContentPane().add(tfNewPron);
		tfNewPron.setColumns(10);

		tfMultip = new JTextField();
		tfMultip.setBounds(173, 410, 85, 19);
		getContentPane().add(tfMultip);
		tfMultip.setColumns(10);

		lblNewPron = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblNewPron"));
		lblNewPron.setBounds(79, 384, 85, 13);
		getContentPane().add(lblNewPron);

		lblMultip = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblMultip"));
		lblMultip.setBounds(79, 414, 85, 13);
		getContentPane().add(lblMultip);
		
		
		// JButton para volver hacia atras 
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblSalir")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI ventana = new AdminGUI(u);
				ventana.setVisible(true);
				dispose();
			}
		});
		btnClose.setBounds(182, 477, 160, 26);
		contentPane.add(btnClose);
		
	}

	public void setBussinessLogic(BLFacade logicaNegocio) {
		businessLogic = logicaNegocio;

	}

	public static BLFacade getBusinessLogic() {
		return AdminGUI2.businessLogic;
	}


}
