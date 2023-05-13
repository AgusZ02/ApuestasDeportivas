package businessLogic;

import java.util.Vector;
import java.util.Date;

//import domain.Booking;
import domain.Question;
import domain.Usuario;
import domain.Apuesta;
import domain.Event;
import domain.Pronostico;
import exceptions.EventExpired;
import exceptions.EventFinished;
import exceptions.NotEnoughMoney;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

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
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	/**
	 * crea el usuario en la base de datos
	 * 
	 * @param user     el nombre de usuario
	 * @param password la contraseña del usuario
	 */
	@WebMethod
	public void registrarUsuario(String user, String password);

	/**
	 * Devuelve true si la combinación de usuario y contraseña del parámetro
	 * corresponde a un usuario de la base de datos
	 * 
	 * @param user     el nombre de usuario introducido en la app
	 * @param password la contraseña introducida en la app
	 * @return true o false
	 */
	@WebMethod
	public boolean login(String name, String pass);

	/**
	 * Devuelve todos los usuarios existentes
	 * 
	 * @return un vector con los usuarios
	 */
	@WebMethod
	public Vector<Usuario> getUsuarios();

	@WebMethod
	public boolean existsUser(String us);

	@WebMethod
	void createEvent(String evDesc, Date date);

	void createPron(Event ev, Question qu, String pron, double mul) throws PredictionAlreadyExists;

	public Usuario getUsuario(String nombreUsuario);

	@WebMethod
	public Usuario createUser(Usuario user);

	public Event findEvent(int numEvento);

	@WebMethod
	public void createApuesta(double bet,Event ev, Question qu, Pronostico pronostico, Usuario us) throws NotEnoughMoney, EventExpired;
	
	@WebMethod
	public Apuesta getApuesta(Integer betNumber);
	
	@WebMethod
	public Pronostico getPron(Integer predNumber);

    public Pronostico getPronostico(String pronostico, Question pregunta);

    public void cerrarEvento(Event ev, Question q, Pronostico p, boolean b);

	public Question findQuestion(int q);

    public Vector<domain.Apuesta> getApuestasFrom(Usuario u);

	public Vector<domain.Question> getQuestions(Event ev);
}
