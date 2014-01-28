package br.com.vraptorstack.form;

import javax.validation.constraints.NotNull;

import br.com.vraptorstack.form.annotation.DefaultValue;

public class User {
	protected static final String NAME_DEFAULT_TEXT = "User email";

	@NotNull
	@DefaultValue(NAME_DEFAULT_TEXT)
	private String name;

	@NotNull
	private String email;

	private Task task;

	public User() {
	}

	public User(String email, String nome) {
		super();
		this.email = email;
		this.name = nome;
	}

	public User(String nome, String email, Task task) {
		super();
		this.name = nome;
		this.email = email;
		this.task = task;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public Task getTask() {
		return task;
	}

}