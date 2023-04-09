package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Apuesta {

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer betNumber;
	private double bet;
	@XmlIDREF
	private Pronostico pronostico;
	//@XmlIDREF
	private Usuario user;

	public Apuesta(Integer betNumber, double bet, Pronostico pronostico, Usuario user) {
		super();
		this.betNumber = betNumber;
		this.bet = bet;
		this.pronostico = pronostico;
		this.user = user;
	}

	public Apuesta(Integer betNumber, double bet, Pronostico pronostico) {
		super();
		this.betNumber = betNumber;
		this.bet = bet;
		this.pronostico = pronostico;
	}

	public Apuesta(double bet, Pronostico pronostico) {
		super();
		this.bet = bet;
		this.pronostico = pronostico;
	}

	/**
	 * @return the betNumber
	 */
	public Integer getBetNumber() {
		return betNumber;
	}

	/**
	 * @param betNumber the betNumber to set
	 */
	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}

	/**
	 * @return the bet
	 */
	public double getBet() {
		return bet;
	}

	/**
	 * @param bet the bet to set
	 */
	public void setBet(double bet) {
		this.bet = bet;
	}

	/**
	 * @return the pronostico
	 */
	public Pronostico getPronostico() {
		return pronostico;
	}

	/**
	 * @param pronostico the pronostico to set
	 */
	public void setPronostico(Pronostico pronostico) {
		this.pronostico = pronostico;
	}

	/**
	 * @return the user
	 */
	public Usuario getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Usuario user) {
		this.user = user;
	}

}
