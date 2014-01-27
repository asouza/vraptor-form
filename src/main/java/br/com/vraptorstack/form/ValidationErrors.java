package br.com.vraptorstack.form;

import java.util.ArrayList;

import br.com.caelum.vraptor.validator.Message;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ValidationErrors {

	private Multimap<String,Message> errors = ArrayListMultimap.create();

	public void add(Message message) {
		errors.put(message.getCategory(),message);
	}

	public boolean isEmpty() {
		return errors.isEmpty();
	}

	public int count() {
		return errors.size();
	}

	public FormField get(String property) {
		return new FormField(new ArrayList<Message>(errors.get(property)));
	}
	
	
}
