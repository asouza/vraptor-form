package br.com.vraptorstack.form;

import javax.validation.constraints.NotNull;

import br.com.vraptorstack.form.annotation.DefaultValue;

public class User {
	protected static final String NAME_DEFAULT_TEXT = "User email";

	@NotNull
	@DefaultValue(NAME_DEFAULT_TEXT)
	private String nome;

	@NotNull
	private String email;

	public User() {
	}
	
	public User(String email, String nome) {
		super();
		this.email = email;
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}

}