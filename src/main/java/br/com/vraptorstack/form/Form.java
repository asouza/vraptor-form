package br.com.vraptorstack.form;

import java.util.Locale;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.beanvalidation.BeanValidatorContext;

@Dependent
public class Form<T> {

	private T object;
	private Validator validator;
	private MessageInterpolator interpolator;
	private Locale locale;
	private ValidationErrors errors = new ValidationErrors();

	public Form(Validator validator, MessageInterpolator interpolator, Locale locale) {
		this.validator = validator;
		this.interpolator = interpolator;
		this.locale = locale;

	}

	public Form<T> bind(T object) {
		this.object = object;
		validate();
		return this;
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	@SuppressWarnings("rawtypes")
	private void validate() {
			Set<ConstraintViolation<T>> violations = validator.validate(this.object);
			for (ConstraintViolation constraintViolation : violations) {
				BeanValidatorContext ctx = new BeanValidatorContext(constraintViolation);
				String msg = interpolator.interpolate(constraintViolation.getMessageTemplate(), ctx, locale);
				errors.add(new SimpleMessage(constraintViolation.getPropertyPath().toString(), msg));
			}
	}
	
	public ValidationErrors getErrors() {
		return errors;
	}

}
