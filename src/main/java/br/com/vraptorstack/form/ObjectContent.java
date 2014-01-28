package br.com.vraptorstack.form;

import net.vidageek.mirror.dsl.Mirror;
import br.com.vraptorstack.form.annotation.DefaultValue;

public class ObjectContent {

	private String field;
	private Object object;
	private Object content;

	public ObjectContent(Object object, String field) {
		this.object = object;
		this.field = field;
		this.content = new Mirror().on(object).invoke().getterFor(field);
	}
	
	public ObjectContent orDefault() {
		if(content == null) {
			DefaultValue annotation = new Mirror().on(object.getClass()).reflect().annotation(DefaultValue.class).atField(field);
			content = annotation.value();
		}
		return this;
	}

	@Override
	public String toString() {
		return content.toString();
	}

}
