package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.Usuario;
import exceptions.EventExpired;
import exceptions.NotEnoughMoney;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.Vector;

public class PreguntasApuestasUsuarioGUI extends JFrame {
	
	//private static final long serialVersionUID = 1L;

	private static BLFacade businessLogic = LoginGUI.getBusinessLogic();
	private JPanel contentPane;

	private DefaultTableModel tableModelQueries;
	private JTable tableQueries = new JTable();
	private JTable tableProns = new JTable();
	private DefaultTableModel tableModelProns;

	private JScrollPane scrollPaneQueries = new JScrollPane();
	private final JScrollPane scrollPanePron = new JScrollPane();

	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel lblPronosticos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblPronosticos"));

	private JLabel lblApostar;
	private JTextField textFieldApuesta;
	private JButton btnApostar;
	private JLabel lblSaldo;

	private String[] columnNamesQueries = new String[] { 
			ResourceBundle.getBundle("Etiquetas").getString("N"),
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("BetMin"),
			ResourceBundle.getBundle("Etiquetas").getString("result")
	};

	private String[] columnNamesProns = new String[] { 
			ResourceBundle.getBundle("Etiquetas").getString("N"),
			ResourceBundle.getBundle("Etiquetas").getString("Pron"),
			ResourceBundle.getBundle("Etiquetas").getString("Multip")
	};


	public PreguntasApuestasUsuarioGUI(Usuario u, Event evento) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 459);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		scrollPaneQueries.setBounds(new Rectangle(12, 41, 540, 117));
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		
		scrollPanePron.setBounds(new Rectangle(12, 195, 540, 116));
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
					if(q.getResult()==null)
						row.add("...");
					else
						row.add(q.getResult());
					row.add(q);
					tableModelQueries.addRow(row);	
				}
				System.out.println("Questions " + q);
			}
			tableQueries.getColumnModel().getColumn(0).setPreferredWidth(30);
			tableQueries.getColumnModel().getColumn(1).setPreferredWidth(250);
			tableQueries.getColumnModel().getColumn(2).setPreferredWidth(100);
			tableQueries.getColumnModel().getColumn(3).setPreferredWidth(160);
			tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(4));
		} catch(Exception e) {
			//VentanaAvisos vent = new VentanaAvisos("Error en tableQueries/tableModelQueries", null);
			//vent.setVisible(true);
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
					tableModelProns.setColumnCount(3);
					for (domain.Pronostico p : pronosticos1) {
						Vector<Object> row = new Vector<Object>();
						row.add(p.getPronNumber());
						row.add(p.toString());
						row.add(p.getCuotaGanancia());
						//row.add(p);
						tableModelProns.addRow(row);
						System.out.println("Pron " + p);
					}
					tableProns.getColumnModel().getColumn(0).setPreferredWidth(30);
					tableProns.getColumnModel().getColumn(1).setPreferredWidth(400);
					tableProns.getColumnModel().getColumn(2).setPreferredWidth(110);
				} catch(Exception exc) {
					VentanaAvisos vent = new VentanaAvisos("Error en tableProns/tableModelProns", null);
					vent.setVisible(true);
				}				
			}
		});

		tableQueries.setModel(tableModelQueries);
		
		
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(250);
		tableQueries.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableQueries.getColumnModel().getColumn(3).setPreferredWidth(160);
		tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(4));
		tableQueries.setDefaultEditor(Object.class, null);
		
		this.getContentPane().add(scrollPaneQueries, null);


		tableProns.setModel(tableModelProns);
		tableProns.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableProns.getColumnModel().getColumn(1).setPreferredWidth(400);
		tableProns.getColumnModel().getColumn(2).setPreferredWidth(110);
		//tableProns.getColumnModel().removeColumn(tableProns.getColumnModel().getColumn(3));
		tableProns.setDefaultEditor(Object.class, null);

		this.getContentPane().add(scrollPanePron, null);

		// JLabel (Questions)
		jLabelQueries.setBounds(12, 25, 330, 14);
		this.getContentPane().add(jLabelQueries);
		
		// JLabel (Pronosticos)
		lblPronosticos.setBounds(12, 180, 300, 14);
		this.getContentPane().add(lblPronosticos, null);


		// ***************************************************************************
		// *****************************REALIZAR APUESTA******************************
		// ***************************************************************************

		// JLabel (Saldo)
		//lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSaldo = new JLabel();
		lblSaldo.setText("Saldo disponible: " + u.getSaldo());
		lblSaldo.setBounds(395, 12, 159, 21);
		getContentPane().add(lblSaldo);
		
		
		// JLabel (Apostar)
		lblApostar = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblApostar")); //$NON-NLS-1$ //$NON-NLS-2$
		lblApostar.setBounds(80, 323, 81, 20);
		this.getContentPane().add(lblApostar, null);

		// JTextField
		textFieldApuesta = new JTextField(ResourceBundle.getBundle("Etiquetas").getString("textFieldCantidad"));
		textFieldApuesta.setBounds(179, 323, 181, 22);
		textFieldApuesta.setColumns(10);
		this.getContentPane().add(textFieldApuesta, null);

		// JButton
		btnApostar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnApostar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double betRealizada = Double.parseDouble(textFieldApuesta.getText());
					Integer pr = (Integer) tableProns.getValueAt(tableProns.getSelectedRow(), 0);
					Question qu = (domain.Question) tableModelQueries.getValueAt(tableQueries.getSelectedRow(), 4);

					if (betRealizada < qu.getBetMinimum()) {
						VentanaAvisos error = new VentanaAvisos(
								"<html>Error: La apuesta no llega al importe minimo.<br/>No es posible realizar la apuesta.</html>",
								"");
						error.setVisible(true);
					} else {
						Pronostico pred = businessLogic.getPron(pr);
						VentanaAvisos vAvisos;
						try {
							businessLogic.createApuesta(betRealizada, ev, qu, pred, u);
							u.setSaldo(u.getSaldo() - betRealizada);
							lblSaldo.setText("Saldo disponible: " + u.getSaldo());
							repaint();
						} catch (NotEnoughMoney NEM) {
							vAvisos = new VentanaAvisos("El usuario no tiene suficiente dinero", "NotEnoughMoney");
							vAvisos.setVisible(true);
						} catch (EventExpired EE) {
							vAvisos = new VentanaAvisos("El evento ha terminado.", "EventExpired");
							vAvisos.setVisible(true);
						}
						lblPronosticos.setText(ResourceBundle.getBundle("Etiquetas").getString("ApuestaRealizada"));
					}
				} catch (NumberFormatException e1) {
					VentanaAvisos vAvisos = new VentanaAvisos(ResourceBundle.getBundle("Etiquetas").getString("errorNumFormatBtnApostar"), null);
					vAvisos.setVisible(true);
				} catch (ArrayIndexOutOfBoundsException e2) {
					VentanaAvisos vAvisos = new VentanaAvisos(ResourceBundle.getBundle("Etiquetas").getString("errorPredBtnApostar"), null);
					vAvisos.setVisible(true);
				}
				
			}
		});
		btnApostar.setBounds(371, 322, 89, 23);
		this.getContentPane().add(btnApostar, null);
		
		
		JButton btnAtras = new JButton(ResourceBundle.getBundle("Etiquetas").getString("lblSalir"));
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApostarUsuarioGUI ventana = new ApostarUsuarioGUI(u, ev.getEventDate());
				ventana.setBussinessLogic(businessLogic);
				ventana.setVisible(true);
				dispose();
				
			}
		});
		
		
		btnAtras.setBounds(165, 377, 232, 32);
		contentPane.add(btnAtras);

	}

	public void setBussinessLogic(BLFacade logicaNegocio) {
		businessLogic = logicaNegocio;

	}

	public static BLFacade getBusinessLogic() {
		return PreguntasApuestasUsuarioGUI.businessLogic;
	}
}
