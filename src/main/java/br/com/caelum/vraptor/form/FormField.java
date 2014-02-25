package br.com.caelum.vraptor.form;

import java.util.List;

import br.com.caelum.vraptor.validator.Message;

public class FormField {

	private List<Message> errors;
	private ObjectContent objectContent;

	public FormField(List<Message> errors, ObjectContent objectContent) {
		this.errors = errors;
		this.objectContent = objectContent;
	}
	
	public List<Message> getErrors() {
		return errors;
	}

	public String getError(int i) {
		return errors.get(i).getMessage();
	}
	
	public Object getValue(){
		return objectContent.getContent();
	}
}
