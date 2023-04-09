package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import com.toedter.calendar.JCalendar;
import domain.Pronostico;
import domain.Question;
import exceptions.EventFinished;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

public class AdminGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel lblPronosticos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblPronosticos"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePron = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableProns = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelProns;
	private BLFacade facade = LoginGUI.getBusinessLogic();
	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private String[] columnNamesProns = new String[] { ResourceBundle.getBundle("Etiquetas").getString("PronN"),
			ResourceBundle.getBundle("Etiquetas").getString("Pron"),
			ResourceBundle.getBundle("Etiquetas").getString("Multip")

	};

	private JTextField tfQuestion;
	private JTextField tfMinBet;
	private JTextField tfNewEvent;
	private JTextField tfNewPron;
	private JTextField tfMultip;
	private final JButton btnCerrarEvento = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$

	public AdminGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 600));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 248, 300, 14);
		jLabelEvents.setBounds(292, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(195, 523, 130, 30));

		this.getContentPane().add(jButtonClose, null);
		jCalendar1.setBorder(new LineBorder(new Color(0, 0, 0)));

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					// jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));

						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
						// in
						// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 358, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 272, 300, 116));
		scrollPanePron.setBounds(new Rectangle(350, 272, 300, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.toString());
					row.add(q);
					tableModelQueries.addRow(row);
				}
				// tableModelQueries.setColumnCount(2);
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(200);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not

			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				Question qu = (domain.Question) tableModelQueries.getValueAt(i, 2); // obtain ev object

				Vector<Pronostico> pronosticos1 = qu.getPronosticos();

				tableModelProns.setDataVector(null, columnNamesProns);
				tableModelProns.setColumnCount(3);

				if (pronosticos1.isEmpty())
					lblPronosticos.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoPredictions") + ": " + qu.toString());
				else
					lblPronosticos.setText(
							ResourceBundle.getBundle("Etiquetas").getString("SelectedPron") + " " + qu.toString());

				for (domain.Pronostico p : pronosticos1) {
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPronNumber());
					row.add(p.toString());
					row.add(p.getCuotaGanancia());
					tableModelProns.addRow(row);
				}
				tableProns.getColumnModel().getColumn(0).setPreferredWidth(10);
				// tableProns.getColumnModel().getColumn(2).setPreferredWidth(10);

			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableEvents.setDefaultEditor(Object.class, null);
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableQueries.setDefaultEditor(Object.class, null);
		scrollPanePron.setViewportView(tableProns);
		tableModelProns = new DefaultTableModel(null, columnNamesProns);

		tableProns.setModel(tableModelProns);
		tableProns.getColumnModel().getColumn(0).setPreferredWidth(10);
		tableProns.getColumnModel().getColumn(2).setPreferredWidth(10);
		tableProns.setDefaultEditor(Object.class, null);
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePron, null);

		lblPronosticos.setBounds(350, 249, 300, 13);
		getContentPane().add(lblPronosticos);

		JButton btnNewEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewEvent"));
		btnNewEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String evDesc = tfNewEvent.getText();

				Date date = jCalendar1.getDate();

				if (date != null) {

					if (evDesc.length() > 0) {

						jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("EventCreated"));

						facade.createEvent(evDesc, date);
					} else
						jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEvent"));
				}
			}
		});

		btnNewEvent.setBounds(565, 210, 85, 21);
		getContentPane().add(btnNewEvent);

		JButton btnNewQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewQuestion"));
		btnNewQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String quest = tfQuestion.getText();
				float min = Float.parseFloat(tfMinBet.getText());
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2);
				if (quest.length() > 0) {

					try {
						facade.createQuestion(ev, quest, min);
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
		btnNewQuestion.setBounds(230, 426, 110, 21);

		getContentPane().add(btnNewQuestion);

		tfQuestion = new JTextField();
		tfQuestion.setBounds(135, 397, 205, 19);
		getContentPane().add(tfQuestion);
		tfQuestion.setColumns(10);

		tfMinBet = new JTextField();
		tfMinBet.setBounds(135, 427, 85, 19);
		getContentPane().add(tfMinBet);
		tfMinBet.setColumns(10);

		JLabel lblQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblQuestion"));
		lblQuestion.setBounds(40, 400, 85, 13);
		getContentPane().add(lblQuestion);

		JLabel lblMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblMinBet"));
		lblMinBet.setBounds(40, 430, 85, 13);
		getContentPane().add(lblMinBet);

		JLabel lblNewEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblNewEvent"));
		lblNewEvent.setBounds(292, 214, 85, 13);
		getContentPane().add(lblNewEvent);

		tfNewEvent = new JTextField();
		tfNewEvent.setBounds(387, 210, 168, 19);
		getContentPane().add(tfNewEvent);
		tfNewEvent.setColumns(10);

		JButton btnNewPron = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnNewPron"));
		btnNewPron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pron = tfNewPron.getText();
				double mul = (double)Double.parseDouble(tfMultip.getText());
				int i = tableQueries.getSelectedRow();
				Question qu = (domain.Question) tableModelQueries.getValueAt(i, 2);
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(), 2);

				if (pron.length() > 0) {

					try {
						facade.createPron(ev, qu, pron, mul);
					} catch (PredictionAlreadyExists e1) {

					}
				} else
					lblPronosticos.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPron"));

			}
		});
		btnNewPron.setBounds(540, 426, 110, 21);
		getContentPane().add(btnNewPron);

		tfNewPron = new JTextField();
		tfNewPron.setBounds(445, 398, 205, 19);
		getContentPane().add(tfNewPron);
		tfNewPron.setColumns(10);

		tfMultip = new JTextField();
		tfMultip.setBounds(445, 427, 85, 19);
		getContentPane().add(tfMultip);
		tfMultip.setColumns(10);

		JLabel lblNewPron = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblNewPron"));
		lblNewPron.setBounds(350, 400, 85, 13);
		getContentPane().add(lblNewPron);

		JLabel lblMultip = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblMultip"));
		lblMultip.setBounds(350, 430, 85, 13);
		getContentPane().add(lblMultip);
		btnCerrarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: cerrar el evento
				
				
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(), 2);
				CerrarEventoGUI ventana = new CerrarEventoGUI(ev);
				ventana.setBussinessLogic(facade);
				ventana.setVisible(true);
				
				// int q = (int) tableModelQueries.getValueAt(tableQueries.getSelectedRow(), 0);
				// Question qu = facade.findQuestion(q);
				// String p = (String) tableModelProns.getValueAt(tableProns.getSelectedRow(), 1);
				// Pronostico pron = facade.getPronostico(p, qu);
				// facade.cerrarEvento(ev, qu, pron);
				
				

			}
		});
		btnCerrarEvento.setBounds(350, 523, 110, 30);

		getContentPane().add(btnCerrarEvento);

	}

	public void setBussinessLogic(BLFacade b) {
		this.facade = b;
	}

}
