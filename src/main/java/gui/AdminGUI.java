package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import com.toedter.calendar.JCalendar;
import domain.Usuario;
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
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();

	private DefaultTableModel tableModelEvents;

	private BLFacade facade = LoginGUI.getBusinessLogic();

	private JTextField tfNewEvent;

	private JButton btnVerPreguntasPronosticos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VerPreguntasPronosticos"));
	private final JButton btnCerrarEvento = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"), };

	public AdminGUI(Usuario u) {
		try {
			jbInit(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Usuario u) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(748, 600));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEvents.setBounds(292, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		jButtonClose.setBounds(new Rectangle(269, 482, 130, 30));

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
							// Si en JCalendar esta en 30 de enero y se avanza al mes siguiente, devolvera 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este codigo se dejara como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(4); // another column added to allocate ev objects

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
							row.add(ev.isClosed());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {
						//jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 406, 150));

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableEvents.setDefaultEditor(Object.class, null);

		this.getContentPane().add(scrollPaneEvents, null);

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

		btnNewEvent.setBounds(548, 277, 111, 25);
		getContentPane().add(btnNewEvent);

		JLabel lblNewEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblNewEvent"));
		lblNewEvent.setBounds(69, 250, 111, 13);
		getContentPane().add(lblNewEvent);

		tfNewEvent = new JTextField();
		tfNewEvent.setBounds(210, 246, 449, 19);
		getContentPane().add(tfNewEvent);
		tfNewEvent.setColumns(10);

		//JButton cerrar evento
		btnCerrarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(), 2);
				CerrarEventoGUI ventana = new CerrarEventoGUI(u, ev);
				ventana.setBussinessLogic(facade);
				ventana.setVisible(true);
			}
		});
		btnCerrarEvento.setBounds(368, 374, 130, 27);
		getContentPane().add(btnCerrarEvento);
		
		//JButton Ver preguntas y pronosticos
		btnVerPreguntasPronosticos.setBounds(124, 374, 199, 25);
		btnVerPreguntasPronosticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int i=tableEvents.getSelectedRow();
					domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i, 3);
					AdminGUI2 ventana = new AdminGUI2(u, ev);
					ventana.setBussinessLogic(facade);
					ventana.setVisible(true);
					dispose();
				}catch(Exception ex) {
					VentanaAvisos ventana = new VentanaAvisos(ResourceBundle.getBundle("Etiquetas").getString("errorSelecEvento"), null);
					ventana.setVisible(true);
				}				
			}
		});
		this.getContentPane().add(btnVerPreguntasPronosticos);

	}

	public void setBussinessLogic(BLFacade b) {
		this.facade = b;
	}

	public BLFacade getBusinessLogic() {
		return facade;
	}

}
