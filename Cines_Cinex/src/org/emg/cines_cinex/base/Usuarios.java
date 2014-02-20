package org.emg.cines_cinex.base;

// Generated 17-feb-2014 15:52:26 by Hibernate Tools 4.0.0



/**
 * Usuarios generated by hbm2java
 */

public class Usuarios implements java.io.Serializable {

	private Integer id;
	private String login;
	private String password;
	private String nivel;

	public Usuarios() {
	}

	public Usuarios(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public Usuarios(String login, String password, String nivel) {
		this.login = login;
		this.password = password;
		this.nivel = nivel;
	}

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

}
