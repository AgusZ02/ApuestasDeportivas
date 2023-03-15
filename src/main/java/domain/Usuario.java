package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String nombreUsuario;
	private String contrasena;
	private boolean esAdmin;
	
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

	public boolean equals(Usuario u){
		return this.nombreUsuario.equals(u.getNombreUsuario()) && this.contrasena.equals(u.getContrasena());
	}
	
	
}

