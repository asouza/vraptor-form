package br.com.vraptorstack.form;

import ognl.Ognl;
import ognl.OgnlException;
import net.vidageek.mirror.dsl.Mirror;
import br.com.vraptorstack.form.annotation.DefaultValue;

public class ObjectContent {

	private String field;
	private Object object;
	private Object content;

	public ObjectContent(Object object, String propertyPath) {
		this.object = object;
		this.field = propertyPath;
		try {
			this.content = Ognl.getValue(propertyPath,object);
		} catch (OgnlException e) {
			throw new RuntimeException(e);
		}
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
