package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String nombreUsuario;
	private String contrasena;
	private boolean esAdmin;
	private String dni;
	private String nombre;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Apuesta> apuestas = new Vector<Apuesta>();

	public Usuario(String nombreUsuario, String contrasena, boolean esAdmin) {
		this(nombreUsuario, contrasena);
		this.esAdmin = esAdmin;
	}

	public Usuario(String nombreUsuario, String contrasena) {
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public boolean esAdmin() {
		return esAdmin;
	}

	public void setAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	public boolean equals(Usuario u) {
		return this.nombreUsuario.equals(u.getNombreUsuario()) && this.contrasena.equals(u.getContrasena());
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void addApuesta(Apuesta apuesta) {
		apuestas.add(apuesta);
	}
}
