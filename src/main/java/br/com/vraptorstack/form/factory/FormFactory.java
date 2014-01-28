package br.com.vraptorstack.form.factory;

import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import br.com.vraptorstack.form.Form;

@RequestScoped
public class FormFactory {
	@Inject private Validator validator;
	@Inject private MessageInterpolator interpolator;
	@Inject private Locale locale;

	
	@Produces
	@Dependent
	public <T> Form<T> create(InjectionPoint injectionPoint) {

	    ParameterizedType type = (ParameterizedType) injectionPoint.getType();
	    Class clazz = (Class) type.getActualTypeArguments()[0];
	    
		return new Form(validator, interpolator, locale, clazz);
		
	}
}
