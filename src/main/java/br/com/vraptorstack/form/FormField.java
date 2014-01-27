package br.com.vraptorstack.form;

import java.util.List;

import br.com.caelum.vraptor.validator.Message;

public class FormField {

	private List<Message> messages;

	public FormField(List<Message> messages) {
		this.messages = messages;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
}
