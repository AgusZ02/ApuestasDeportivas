package domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Apuesta {

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer betNumber;
	private int bet;
	@XmlIDREF
	private Pronostico pronostico;
	@XmlIDREF
	private Usuario user;
	
}
