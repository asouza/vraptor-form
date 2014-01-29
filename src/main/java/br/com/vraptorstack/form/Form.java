package br.com.vraptorstack.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.beanvalidation.BeanValidatorContext;

@Dependent
public class Form<T> {

	private T object;
	private Validator validator;
	private MessageInterpolator interpolator;
	private Locale locale;
	private ValidationErrors fieldErrors = new ValidationErrors();
	private List<Message> globalErrors = new ArrayList<>();


	@SuppressWarnings("unchecked")
	public Form(Validator validator, MessageInterpolator interpolator,
			Locale locale, Class<?> clazz) {
		this.validator = validator;
		this.interpolator = interpolator;
		this.locale = locale;
		this.object = (T) new Mirror().on(clazz).invoke().constructor().withoutArgs();
	}

	public Form<T> bind(T object) {
		this.object = object;
		validate();
		return this;
	}

	public boolean hasErrors() {
		return !fieldErrors.isEmpty();
	}

	public FormField get(String field) {
		return new FormField(fieldErrors.get(field),new ObjectContent(object, field));
		
	}
	
	public List<Message> getGlobalErrors() {
		return globalErrors;
	}

	@SuppressWarnings("rawtypes")
	private void validate() {
			Set<ConstraintViolation<T>> violations = validator.validate(this.object);
			for (ConstraintViolation constraintViolation : violations) {
				BeanValidatorContext ctx = new BeanValidatorContext(constraintViolation);
				String msg = interpolator.interpolate(constraintViolation.getMessageTemplate(), ctx, locale);
				fieldErrors.add(new SimpleMessage(constraintViolation.getPropertyPath().toString(), msg));
			}
	}
	
	public void reject(String field, String message) {
		fieldErrors.add(new SimpleMessage(field,message));
	}

	public void reject(String message) {
		globalErrors.add(new SimpleMessage("",message));
	}
		

}
