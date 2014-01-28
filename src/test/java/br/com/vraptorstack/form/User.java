package br.com.vraptorstack.form;

import javax.validation.constraints.NotNull;

public class User {
	@NotNull
	private String email;
	@NotNull
	private String nome;

	public User(String email, String nome) {
		super();
		this.email = email;
		this.nome = nome;
	}

}