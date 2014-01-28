package br.com.vraptorstack.form;

import java.util.List;

import br.com.caelum.vraptor.validator.Message;

public class FormField {

	private List<Message> errors;

	public FormField(List<Message> errors) {
		this.errors = errors;
	}
	
	public List<Message> getErrors() {
		return errors;
	}

	public String getMessage(int i) {
		return errors.get(i).getMessage();
	}
}
