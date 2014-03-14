package br.com.caelum.vraptor.form;

import java.util.ResourceBundle;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Severity;

public class FakeMessage implements Message {

	@Override
	public String getCategory() {
		return "";
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Severity getSeverity() {
		return Severity.ERROR;
	}

	@Override
	public void setBundle(ResourceBundle arg0) {
		// TODO Auto-generated method stub

	}

}
