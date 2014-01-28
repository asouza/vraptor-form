package br.com.vraptorstack.form;

import net.vidageek.mirror.dsl.Mirror;
import br.com.vraptorstack.form.annotation.DefaultValue;

public class ObjectContent<T> {

	private String field;
	private T object;
	private Object content;

	public ObjectContent(T object, String field) {
		this.object = object;
		this.field = field;
		this.content = new Mirror().on(object).invoke().getterFor(field);
	}
	
	public ObjectContent<T> orDefault() {
		if(content == null)
			content = ((DefaultValue) new Mirror().on(object.getClass()).reflect().annotation(DefaultValue.class)).value();
		return this;
	}

	@Override
	public String toString() {
		return content.toString();
	}

}
