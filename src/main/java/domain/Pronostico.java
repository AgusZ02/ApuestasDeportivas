package domain;

import java.io.Serializable;
import java.util.Vector;
//import java.util.HashMap;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Pronostico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer predictionNumber;
	private String pronostico;
	private boolean finalizado;
	private double cuotaGanancia;
	@XmlIDREF
	private Question pregunta;
	// private HashMap<Usuario, Double> apuestas;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Apuesta> apuestas = new Vector<Apuesta>();

	public Pronostico(String pronostico, boolean finalizado, double cuotaGanancia) {
		super();
		this.pronostico = pronostico;
		this.finalizado = finalizado;
		this.cuotaGanancia = cuotaGanancia;
	}

	public Pronostico(String pronostico, boolean finalizado, double cuotaGanancia, Question pregunta) {
		this(pronostico, finalizado, cuotaGanancia);
		this.pregunta = pregunta;

	}

	public String getPronostico() {
		return pronostico;
	}

	public void setPronostico(String pronostico) {
		this.pronostico = pronostico;
	}

	public boolean isFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	public double getCuotaGanancia() {
		return cuotaGanancia;
	}

	public void setCuotaGanancia(double cuotaGanancia) {
		this.cuotaGanancia = cuotaGanancia;
	}

	public Integer getPronNumber() {
		return predictionNumber;
	}

	public void setPregunta(Question pregunta) {
		this.pregunta = pregunta;
	}

	public boolean equals(String pronostico) {
		return this.pronostico.equals(pronostico);
	}

	public Question getPregunta() {
		return this.pregunta;
	}

	/*public void apostar(Usuario user, double cantidad) {
		this.apuestas.put(user, cantidad);
	}*/

	public void addApuesta(Apuesta ap) {
		apuestas.add(ap);
	}

}