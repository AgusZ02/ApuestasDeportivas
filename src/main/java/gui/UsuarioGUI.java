package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import com.toedter.calendar.JCalendar;
import domain.Pronostico;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;


public class UsuarioGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel lblPronosticos = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblPronosticos"));

	
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JButton btnApostar;
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableProns = new JTable();


	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelProns;
	private final JScrollPane scrollPanePron = new JScrollPane();
	private JTextField textFieldApuesta;
	private JLabel lblApostar;
	private BLFacade facade = LoginGUI.getBusinessLogic();;
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	
	private String[] columnNamesProns = new String[] { ResourceBundle.getBundle("Etiquetas").getString("PronN"),
			ResourceBundle.getBundle("Etiquetas").getString("Pron"),
			ResourceBundle.getBundle("Etiquetas").getString("Multip")

	};
	
	

	public UsuarioGUI()
	{
		
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{

		
		
		lblApostar = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblApostar")); //$NON-NLS-1$ //$NON-NLS-2$
		lblApostar.setBounds(338, 354, 39, 20);
		this.getContentPane().add(lblApostar, null);
		
		textFieldApuesta = new JTextField(ResourceBundle.getBundle("Etiquetas").getString("textFieldCantidad"));
		textFieldApuesta.setBounds(413, 354, 122, 20);
		textFieldApuesta.setColumns(10);
		this.getContentPane().add(textFieldApuesta, null);
		
		btnApostar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnApostar")); //$NON-NLS-1$ //$NON-NLS-2$
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: cálculo del resultado del pronostico
				
				double betRealizada = Double.parseDouble(textFieldApuesta.getText());
				Integer pr = (Integer) tableProns.getValueAt(tableProns.getSelectedRow(), 0);
				Question qu = (domain.Question) tableModelQueries.getValueAt(tableQueries.getSelectedRow(), 2);
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(tableEvents.getSelectedRow(),2);
				
				if (betRealizada < qu.getBetMinimum()) {
					VentanaAvisos error = new VentanaAvisos("<html>Error: La apuesta no llega al importe m�nimo.<br/>No es posible realizar la apuesta.</html>", "");
					error.setVisible(true);
				} else {
					Pronostico pred = facade.getPron(pr);
					facade.createApuesta(betRealizada, ev, qu, pred);
					//lblPronosticos.setText(ResourceBundle.getBundle("Etiquetas").getString("Apuesta realizada"));
					lblPronosticos.setText("Apuesta realizada correctamente");
				}
				//String pronosticoSeleccionado = (String) tableProns.getValueAt(tableProns.getSelectedRow(), 1);
				
			}
		});
		btnApostar.setBounds(549, 353, 89, 23);
		this.getContentPane().add(btnApostar, null);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 227, 288, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(264, 419, 140, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		
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
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = LoginGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=LoginGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 251, 288, 91));
		scrollPanePron.setBounds(new Rectangle(350, 272, 300, 116));


		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
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
							ResourceBundle.getBundle("Etiquetas").getString("NoPredictions") + ": " + qu.getQuestion());
				else
					lblPronosticos.setText(
							ResourceBundle.getBundle("Etiquetas").getString("SelectedPron") + " " + qu.getQuestion());

				for (domain.Pronostico p : pronosticos1) {
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPronNumber());
					row.add(p.getPronostico());
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
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
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

		scrollPanePron.setBounds(new Rectangle(350, 272, 300, 116));
		scrollPanePron.setBounds(338, 251, 300, 91);
		
		lblPronosticos.setBounds(338, 227, 300, 14);
		this.getContentPane().add(lblPronosticos, null);
		
		
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		System.exit(0);
	}
	public void setBussinessLogic(BLFacade b){
		this.facade = b;
	}
}
