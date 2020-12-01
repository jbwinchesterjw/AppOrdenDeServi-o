package com.projetojava.domain;

import java.io.Serializable;

public class UsuarioDto implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String userLogin;
	
	private String userNome;
	
	public UsuarioDto(Usuario user) {
		this.userLogin = user.getLogin();
		this.userNome = user.getNome();
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserNome() {
		return userNome;
	}

	public void setUserNome(String userNome) {
		this.userNome = userNome;
	}

	
	
	
}
