package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import com.toedter.calendar.JCalendar;
import domain.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;


public class ApostarUsuarioGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton btnVerPreguntasPronosticos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VerPreguntasPronosticos")); 
	
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	//Variables para el calendario
	private Date firstDay = new Date();
	private int monthAnt, monthAct;
	private Date dateCalendar = new Date();

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JTable tableEvents= new JTable();
	private DefaultTableModel tableModelEvents;

	private BLFacade facade = LoginGUI.getBusinessLogic();;
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
			ResourceBundle.getBundle("Etiquetas").getString("FinalizedEvent"),
	};	


	public ApostarUsuarioGUI(Usuario u, Date d) //viene de apostar a pronosticos
	{
		try
		{
			this.dateCalendar = d;
			jCalendar1.setDate(d);
			jbInit(u);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void jbInit(Usuario u) throws Exception
	{
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 353));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jLabelEventDate.setBounds(new Rectangle(40, 24, 225, 16));
		jLabelEvents.setBounds(292, 24, 346, 16);
		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setBounds(new Rectangle(260, 271, 140, 32));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuarioGUI ventana = new MenuUsuarioGUI(u);
				ventana.setVisible(true);
				dispose();
			}
		});
		this.getContentPane().add(jButtonClose, null);

		
		btnVerPreguntasPronosticos.setBounds(226, 228, 207, 32);
		btnVerPreguntasPronosticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int i=tableEvents.getSelectedRow();
					domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i, 3);
					PreguntasApuestasUsuarioGUI ventana = new PreguntasApuestasUsuarioGUI(u, ev);
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
		

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));
		jCalendar1.setDate(dateCalendar);
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{
				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					//					jCalendar1.setCalendar(calendarAct);
					firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));
					monthAnt = calendarAnt.get(Calendar.MONTH);
					monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						jCalendar1.setCalendar(calendarAct);
					}
					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate()));
					inicializarTablaEvents(firstDay);
				}
			} 
		});
		jCalendar1.getMonthChooser().getComboBox().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate()));
			}
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneEvents.setViewportView(tableEvents);
		
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		inicializarTablaEvents(dateCalendar);
		tableEvents.setModel(tableModelEvents);
		
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableEvents.getColumnModel().getColumn(2).setPreferredWidth(150);
		tableEvents.setDefaultEditor(Object.class, null);

		this.getContentPane().add(scrollPaneEvents, null);
		
	}
	
	public void setBussinessLogic(BLFacade b) {
		this.facade = b;
	}
	
	public BLFacade getBusinessLogic() {
		return facade;
	}
	
	private void inicializarTablaEvents(Date thisDate){
		if (thisDate.getHours()!=0) { //Corrige las horas
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			thisDate = calendar.getTime();
		}
		try {
			tableModelEvents.setDataVector(null, columnNamesEvents);
			tableModelEvents.setColumnCount(4); // another column added to allocate ev objects
			
			Vector<domain.Event> events=facade.getEvents(thisDate);
			if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents"));
			else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events"));
			for (domain.Event ev:events){
				Vector<Object> row = new Vector<Object>();
				if (true) { //!ev.isClosed()
					row.add(ev.getEventNumber());
					row.add(ev.getDescription());
					row.add(ev.isClosed());
					row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,3)
					tableModelEvents.addRow(row);	
				}
				System.out.println("Events "+ev);	
			}
			tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
			tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
			tableEvents.getColumnModel().getColumn(2).setPreferredWidth(150);
			tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(3)); // not shown in JTable
		} catch (Exception e1) {
			//jLabelQueries.setText(e1.getMessage());
		}
	}
}
