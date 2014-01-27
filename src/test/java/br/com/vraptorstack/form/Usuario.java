package br.com.vraptorstack.form;

import javax.validation.constraints.NotNull;

public class Usuario {
	@NotNull
	private String email;
	@NotNull
	private String nome;

	public Usuario(String email, String nome) {
		super();
		this.email = email;
		this.nome = nome;
	}

}