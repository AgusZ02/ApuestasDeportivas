package dataAccess;

//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.Usuario;
import exceptions.PredictionAlreadyExists;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			//month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			Pronostico p1 = q1.addPronostico("atletic ganara", 0.3);
			Pronostico p2 = q1.addPronostico("athletic ganara", 0.3);
			Pronostico p3 = q1.addPronostico("empataran", 0.2);

			db.persist(p3);
			db.persist(p2);
			db.persist(p1);

			Usuario user1 = new Usuario("user1", "user1", 100,false);
			Usuario admin1 = new Usuario("admin1", "admin1", 100,true);

			db.persist(admin1);
			db.persist(user1);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		db.persist(q);
						// db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * Añade el usuario a la base de datos (al registrarse)
	 * 
	 * @param user el usuario a añadir
	 */
	public Usuario addUsuario(Usuario user) {
		db.getTransaction().begin();
		if (getUsuario(user.getNombreUsuario()) != null) {
			return null;
		}
		db.persist(user);
		db.getTransaction().commit();
		System.out.println("Insertado: " + user.toString());
		return user;
	}

	/**
	 * Retorna el usuario indicado de la base de datos
	 * 
	 * @param user el nombre de usuario del deseado
	 * @return El usuario si existe, de lo contrario null.
	 */
	public Usuario getUsuario(String user) {
		db.getTransaction().begin();
		TypedQuery<Usuario> query = db.createQuery("SELECT u from Usuario u WHERE u.getNombreUsuario()=?1",
				Usuario.class);
		query.setParameter(1, user);
		Usuario us = null;
		try {
			us = query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("El usuario no existe"); // TODO: mostrar este error en la app
		}
		db.getTransaction().commit();
		return us;
	}
	
	/**
	 * Busca un pronostico en base a su descripcion y pregunta respectiva.
	 * 
	 * @param pronostico
	 * @param pregunta
	 * @return el pronostico buscado, nulo sino existe o no ha sido encontrado.
	 */
	public Pronostico getPronostico(String pronostico, Question pregunta) {
		Pronostico pronosticoBuscado = null;
		Question preguntaBuscada = findQuestion(pregunta.getQuestionNumber());
		for (Pronostico pr : preguntaBuscada.getPronosticos())
			if (pr.toString().equals(pronostico))
				pronosticoBuscado = pr;

		return pronosticoBuscado;
	}

	public Vector<Usuario> getUsuarios() {

		TypedQuery<Usuario> query = db.createQuery("SELECT f FROM Flight f", Usuario.class); // Selecciona todos los
																								// usuarios en una
																								// consulta
		List<Usuario> usuarios = query.getResultList();
		Vector<Usuario> resultado = new Vector<Usuario>();
		for (Usuario usuario : usuarios) {
			resultado.add(usuario);
		}
		return resultado;
	}
	public Usuario getUser(String us, String ps) {
		Usuario user = db.find(Usuario.class, us);
		if (user != null && user.getContrasena().equals(ps))
			return user;
		return null;
	}
	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * Este método retorna el evento con el número introducido en el parámetro
	 * @param eventNumber el numero de evento
	 * @return el evento buscado
	 */
	public Event getEvent(Integer eventNumber) {
		TypedQuery<Event> query = db.createQuery("SELECT e from Event e WHERE e.getEventNumber()=?1", Event.class);
		query.setParameter(1, eventNumber);
		Event eventObtained = null;
		try {
			eventObtained = query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("El evento no existe");
		}
		return eventObtained;
	}

	public Event createEvent(String desc, Date date) {
		db.getTransaction().begin();
		TypedQuery<Integer> query = db.createQuery("SELECT ev.eventNumber FROM Event ev", Integer.class);
		List<Integer> lis = query.getResultList();
		Integer num = lis.get(lis.size() - 1);
		Event event = new Event(num + 1, desc, date);
		
		db.persist(event);
		db.getTransaction().commit();
		
		System.out.println("Nuevo evento insertado: " + event.getEventNumber());
		return event;
	}

	/**
	 * Crea un pronostico en una pregunta
	 * 
	 */
	public Pronostico createPron(Event ev, Question qu, String desc, double mul) throws PredictionAlreadyExists {
		System.out.println(">> DataAccess: createPron=> question= " + qu + " pron= " + desc + " quote="
		+ mul);
		Event event = findEvent(ev.getEventNumber());
		
		if (qu.DoesPredictionExist(desc)){
			throw new PredictionAlreadyExists();
		}
		Pronostico pron = null;
		db.getTransaction().begin();	
		for (Question question : event.getQuestions()) {
			if (question.getQuestionNumber() == qu.getQuestionNumber()) {
				pron = question.addPronostico(desc, mul);
			}
		}
		db.getTransaction().commit();
		return pron;
	}

	/**
	 * Abre la base de datos
	 * @param initializeMode
	 */
	public void open(boolean initializeMode) {


		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	/**
	 * Cierra la base de datos y muestra un mensaje por consola indicativo.
	 */
	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	/**
	 * Busca una pregunta de un evento en la base de datos
	 * @param event el evento al que pertenece la pregunta
	 * @param question la pregunta
	 * @return true si existe esa pregunta en el evento, de lo contrario false
	 */
	public boolean existQuestion(Event event, String question) {

		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	/**
	 * Indica si existe el usuario del parámetro
	 * @param us el nombre de usuario
	 * @return true si existe un usuario con ese nombre, false de lo contrario
	 */
	public boolean existsUser(String us) {
		System.out.println(">> DataAccess: existsUser=> user= " + us);
		TypedQuery<Usuario> query = db.createQuery("SELECT Us FROM Usuario us WHERE us.nombreUsuario=?1",
				Usuario.class);
		query.setParameter(1, us);
		List<Usuario> user = query.getResultList();
		if (!user.isEmpty())
			return true;
		return false;
	}

	/**
	 * Añade un evento a la base de datos
	 * @param evento el evento a añadir
	 * @return el evento si se ha añadido, de lo contrario null
	 */
	public Event addEvent(Event evento) {
		System.out.println(">> DataAccess: addEvent=> event= " + evento.getDescription());
		db.getTransaction().begin();
		if (this.getEvent(evento.getEventNumber()) != null)
			return null; // el evento ya existe
		db.persist(evento);
		db.getTransaction().commit();
		System.out.println("El evento se ha insertado correctamente: " + evento);
		return evento;
	}

	/**
	 * Elimina un evento de la base de datos.
	 * @param eventNumber el numero de evento a eliminar
	 * @return el evento eliminado
	 */
	public Event removeEvent(Integer eventNumber) {
		
		Event eventToRemove = this.getEvent(eventNumber);
		if (eventToRemove == null)
			return eventToRemove; //

		return eventToRemove;
	}

	/**
	 * Crea un usuario con las credenciales en la base de datos
	 * @param us: el nombre de usuario
	 * @param ps: la contraseña
	 * @return el usuario creado
	 */
	public Usuario createUser(String us, String ps) {
		System.out.println(">> DataAccess: createUser=> user= " + us + " password= " + ps);
		db.getTransaction().begin();
		Usuario user = new Usuario(us, ps, 0, false);
		db.persist(user);
		db.getTransaction().commit();
		return user;
	}

	/**
	 * Este método comprueba si existe un objeto usuario con la combinación de name y pass en sus credenciales en la base de datos.
	 * @param name: el nombre de usuario
	 * @param pass: la contraseña del usuario
	 * @return true si existe combinación, de lo contrario false
	 */
	public boolean hacerLogin(String name, String pass) {
		System.out.println(">> DataAccess: hacerLogin=> user= " + name + " password= " + pass);
		Usuario user = db.find(Usuario.class, name);
		if (user == null)
			return false;
		return (user.getContrasena().equals(pass) && user.getNombreUsuario().equals(name));
	}

	/**
	 * Busca en la base de datos el evento por su numero
	 * @param numEvento el numero del evento
	 * @return el evento si existe, sino, devuelve null
	 */
	public Event findEvent(int numEvento) {
		System.out.println(">> DataAccess: findEvent=> numEvento= " + numEvento);
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventNumber=?1", Event.class);
		query.setParameter(1, numEvento);
		Event evento = query.getSingleResult();
		return evento;
	}


    public void apostar(Pronostico pron, Usuario u, double apuesta) {
		db.getTransaction().begin();
		Usuario user = db.find(Usuario.class, u.getNombreUsuario());
		user.setSaldo(user.getSaldo()-apuesta);
		Pronostico pronostico = db.find(Pronostico.class, pron.getPronNumber());
		pronostico.apostar(user, apuesta);
		db.getTransaction().commit();


    }


	/**
	 * Asigna el pronóstico resultado a la pregunta del evento, resolviendo así la pregunta y cerrando el evento
	 * @param ev el evento a cerrar
	 * @param q la pregunta a resolver
	 * @param p el pronóstico correcto
	 * @param ultimaPregunta parámetro que indica si es la última pregunta del evento a resolver. El evento no se cerrará hasta que se hayan resuelto todas las preguntas.
	 */
    public void cerrarEvento(Event ev, Question q, Pronostico p, boolean ultimaPregunta) {
		System.out.println(">> DataAccess: cerrarEvento=> event= " + ev.getDescription() + "question= " + q.toString() + "pronostico= " + p.toString());
		db.getTransaction().begin();
		if (ultimaPregunta) {
			ev.setClosed(true);
		}
		for (Question qu : ev.getQuestions()) {
			if (q.getQuestionNumber()==qu.getQuestionNumber()) {
				qu.setResult(p.toString());
				q.setResult(p.toString());
				this.findQuestion(q.getQuestionNumber()).setResult(p.toString());
				for (Pronostico pr : qu.getPronosticos()) {
					if (pr.getPronNumber()==p.getPronNumber()) {
						pr.setFinalizado(true);
						p.setFinalizado(true);
						this.getPronostico(p.toString(), q).setFinalizado(true);

						for (Usuario u : p.getApuestas().keySet()) { //Actualiza el saldo de los que apostaron
							u.setSaldo(u.getSaldo()+p.getApuestas().get(u)*p.getCuotaGanancia()*10);
						}
					}
				}
			}
		}
		db.getTransaction().commit();
    }

	/**
	 * Este método busca una pregunta por su número de pregunta.
	 * @param q el número de la pregunta a buscar
	 * @return la pregunta si existe, sino devuelve null.
	 */
	public Question findQuestion(int qNum) {
		System.out.println(">> DataAccess: findQuestion=> numQuestion= " + qNum);
		Question resultado = null;
		resultado = db.find(Question.class, qNum);
		return resultado;
	}

}
