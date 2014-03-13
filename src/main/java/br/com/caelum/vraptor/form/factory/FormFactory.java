package br.com.caelum.vraptor.form.factory;

import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import br.com.caelum.vraptor.form.Form;
import br.com.caelum.vraptor.form.MassAssignmentValidatorConfig;
import br.com.caelum.vraptor.form.WithMassAssignment;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.reflection.MethodExecutor;

@RequestScoped
public class FormFactory {
	@Inject private Validator validator;
	@Inject private MessageInterpolator interpolator;
	@Inject private Locale locale;
	@Inject private MethodExecutor methodExecutor;
	@Inject private MutableRequest mutableRequest;

	
	@Produces
	@Dependent
	public <T> Form<T> create(InjectionPoint injectionPoint) {

	    ParameterizedType type = (ParameterizedType) injectionPoint.getType();
	    Class clazz = (Class) type.getActualTypeArguments()[0];
	    Form form = new Form(validator, interpolator, locale, methodExecutor,clazz);
	    
	    Annotated field = injectionPoint.getAnnotated();
	    WithMassAssignment massAssignment = field.getAnnotation(WithMassAssignment.class);
	    if(massAssignment!=null){
	    	form.setMassAssignmentValidatorConfig(new MassAssignmentValidatorConfig(mutableRequest.getParameterMap()));
	    }
	    
		return form;
		
	}
}
