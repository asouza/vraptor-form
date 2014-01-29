package br.com.vraptorstack.form;

import java.util.List;

import com.google.common.collect.Lists;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.SimpleMessage;

public class Project {

	private String name;

	public Project(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public List<Message> validate(){
		return Lists.<Message>newArrayList(new SimpleMessage("","project creation has a problem"));				
	}
}
