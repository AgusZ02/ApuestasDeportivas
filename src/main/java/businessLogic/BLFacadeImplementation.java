package businessLogic;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.jws.WebMethod;
import javax.jws.WebService;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Usuario;
import domain.Apuesta;
import domain.Event;
import domain.Pronostico;
import exceptions.EventExpired;
import exceptions.EventFinished;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	

	@WebMethod
	public void createPron(Event ev,Question qu, String desc, double d) throws PredictionAlreadyExists {
		dbManager.open(false);
		dbManager.createPron(ev, qu, desc, d);
		dbManager.close();
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * crea el usuario en la base de datos
	 * 
	 * @param user     el nombre de usuario
	 * @param password la contraseña del usuario
	 */
	@WebMethod
	public void registrarUsuario(String user, String password) {
		dbManager.open(false);
		if (this.dbManager.addUsuario(new Usuario(user, password)) == null) {
			System.out.println("El usuario ya existe en la base de datos");
			return;
		}
		System.out.println("Usuario registrado");
		dbManager.close();
	}

	/**
	 * Devuelve true si la combinación de usuario y contraseña del parámetro
	 * corresponde a un usuario de la base de datos
	 * 
	 * @param user     el nombre de usuario introducido en la app
	 * @param password la contraseña introducida en la app
	 * @return true o false
	 */
	@WebMethod
	public boolean login(String name, String pass) {
		dbManager.open(false);
		boolean res = dbManager.hacerLogin(name, pass);
		dbManager.close();
		return res;
	}

	/**
	 * Devuelve todos los usuarios existentes
	 * 
	 * @return un vector con los usuarios
	 */
	@WebMethod
	public Vector<Usuario> getUsuarios() {
		dbManager.open(false);
		Vector<Usuario> usuarios = dbManager.getUsuarios();
		dbManager.close();
		return usuarios;
	}

	public Usuario getUsuario(String nombreUsuario) {
		dbManager.open(false);
		Usuario resultado = dbManager.getUsuario(nombreUsuario);
		dbManager.close();
		return resultado;

	}


	/**
	 * Busca un pronostico en base a su descripcion y pregunta respectiva.
	 * 
	 * @param pronostico
	 * @param pregunta
	 * @return el pronostico buscado, si no existe devuelve nulo.
	 */
	@WebMethod
	public Pronostico getPronostico(String pronostico, Question pregunta) {
		dbManager.open(false);
		Pronostico pronosticoSearched = dbManager.getPronostico(pronostico, pregunta);
		dbManager.close();
		return pronosticoSearched;
	}


	@WebMethod
	public void createEvent(String eventDesc, Date date) {
		dbManager.open(false);
		if (new Date().compareTo(date) > 0);
		dbManager.createEvent(eventDesc, date);
		dbManager.close();
	}



	public Event getEvent(Integer eventNumber) {
		dbManager.open(false);
		Event eventSearched = dbManager.getEvent(eventNumber);
		dbManager.close();
		return eventSearched;
	}

	public boolean isAdmin(Usuario user) {
		return user.esAdmin();
	}

	@WebMethod
	public boolean existsUser(String us) {
		dbManager.open(false);
		boolean bool = dbManager.existsUser(us);
		dbManager.close();
		return bool;
	}

	@WebMethod
	public Usuario createUser(Usuario userP) {
		dbManager.open(false);
		Usuario user = dbManager.createUser(userP.getNombreUsuario(), userP.getContrasena());
		dbManager.close();
		return user;
	}

	@Override
	public Event findEvent(int numEvento) {
		Event evento = null; 
		dbManager.open(false);
		evento = dbManager.findEvent(numEvento);
		dbManager.close();
		return evento;
	}

	@WebMethod
	public void createApuesta(double bet, Event ev, Question qu, Pronostico pronostico, Usuario us) throws NotEnoughMoney, EventExpired {
		if (bet <=0){
			System.out.println("Cantidad apostada incorrecta"); // TODO: mostrar este error en la app
		} if (us.getSaldo()<bet) {
			throw new NotEnoughMoney();
		} if (ev.getEventDate().after(new Date())) {
			throw new EventExpired();
		} 
		else {
			dbManager.open(false);
			dbManager.createApuesta(bet, ev, qu, pronostico, us);
			dbManager.close();
		}
	}
	
	@WebMethod
	public Apuesta getApuesta(Integer betNumber) {
		dbManager.open(false);
		Apuesta betSearched = dbManager.getApuesta(betNumber);
		dbManager.close();
		return betSearched;
	}
	
	@WebMethod
	public Pronostico getPron(Integer predNumber) {
		dbManager.open(false);
		Pronostico predSearched = dbManager.getPronostico(predNumber);
		dbManager.close();
		return predSearched;
	}
	

	@Override
	public void cerrarEvento(Event ev, Question q, Pronostico p, boolean b) {
		dbManager.open(false);
		if (ev.getQuestions().isEmpty()) {
			return;
			//TODO:throw new NoQuestions;
		}
		dbManager.cerrarEvento(ev, q, p, b);
		dbManager.close();

	}

	public Question findQuestion(int q){
		Question qu = null;
		dbManager.open(false);
		qu = dbManager.findQuestion(q);
		dbManager.close();
		return qu;

	}

}
