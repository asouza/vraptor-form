package br.com.vraptorstack.form;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.reflection.MethodExecutor;
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
	private MethodExecutor methodExecutor;
	private Method customValidatorMethod;
	private static Objenesis objenesis = new ObjenesisStd();

	@SuppressWarnings("unchecked")
	public Form(Validator validator, MessageInterpolator interpolator, Locale locale, MethodExecutor methodExecutor,
			Class<?> clazz) {
		this.validator = validator;
		this.interpolator = interpolator;
		this.locale = locale;
		this.methodExecutor = methodExecutor;
		
		ObjectInstantiator<?> instantiator = Form.objenesis.getInstantiatorOf(clazz);
		this.object = (T) instantiator.newInstance();
		try {
			customValidatorMethod = clazz.getDeclaredMethod("validate");
		} catch (NoSuchMethodException | SecurityException e) {
			// leave method null
		}
	}

	public Form<T> bind(T object) {
		this.object = object;
		validate();
		return this;
	}

	public boolean hasErrors() {
		return !fieldErrors.isEmpty() || !globalErrors.isEmpty();
	}

	public FormField get(String field) {
		return new FormField(fieldErrors.get(field), new ObjectContent(object, field));

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

		if (customValidatorMethod != null) {
			List<Message> errors = methodExecutor.invoke(customValidatorMethod, object);
			for (Message message : errors) {
				if (message.getCategory() == null || message.getCategory().trim().equals("")) {
					globalErrors.add(message);
				} else {
					fieldErrors.add(message);
				}
			}
		}
	}

	public void reject(String field, String message) {
		fieldErrors.add(new SimpleMessage(field, message));
	}

	public void reject(String message) {
		globalErrors.add(new SimpleMessage("", message));
	}

}
