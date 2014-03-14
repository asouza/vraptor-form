package br.com.caelum.vraptor.form;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import br.com.caelum.vraptor.reflection.MethodExecutor;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.beanvalidation.BeanValidatorContext;

@Vetoed
public class Form<T> {

	private T object;
	private Validator validator;
	private MessageInterpolator interpolator;
	private Locale locale;
	private ValidationErrors fieldErrors = new ValidationErrors();
	private List<Message> globalErrors = new ArrayList<>();
	private List<Message> allErrors = new ArrayList<>();
	private MethodExecutor methodExecutor;
	private Method customValidatorMethod;
	private static Objenesis objenesis = new ObjenesisStd();
	private MassAssignmentValidatorConfig massAssignmentValidatorConfig;
	private br.com.caelum.vraptor.validator.Validator vraptorValidator;

	@SuppressWarnings("unchecked")
	public Form(Validator validator, MessageInterpolator interpolator, Locale locale, MethodExecutor methodExecutor,
			br.com.caelum.vraptor.validator.Validator vraptorValidator, Class<?> clazz) {

		this.validator = validator;
		this.interpolator = interpolator;
		this.locale = locale;
		this.methodExecutor = methodExecutor;
		this.vraptorValidator = vraptorValidator;

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

	public void setMassAssignmentValidatorConfig(MassAssignmentValidatorConfig massAssignmentValidatorConfig) {
		this.massAssignmentValidatorConfig = massAssignmentValidatorConfig;
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
			reject(constraintViolation.getPropertyPath().toString(), msg);
		}

		if (customValidatorMethod != null) {
			List<Message> errors = methodExecutor.invoke(customValidatorMethod, object);
			errors = errors == null ? errors = Collections.<Message> emptyList() : errors;
			for (Message message : errors) {
				if (message.getCategory() == null || message.getCategory().trim().equals("")) {
					addGlobaldError(message);
				} else {
					addFieldError(message);
				}
			}
		}
	}

	public void reject(String field, String message) {
		addFieldError(new SimpleMessage(field, message));
	}

	public void reject(String message) {
		addGlobaldError(new SimpleMessage("", message));
	}

	private void addFieldError(Message message) {
		dirtyValidation(true);
		allErrors.add(message);
		fieldErrors.add(message);
	}

	private void addGlobaldError(Message message) {
		dirtyValidation(true);
		allErrors.add(message);
		globalErrors.add(message);
	}
	
	private void dirtyValidation(boolean needsToDirty) {
		if (needsToDirty && !vraptorValidator.hasErrors()) {
			vraptorValidator.add(new FakeMessage());
		}
	}


	public List<Message> getAllErrors() {
		return allErrors;
	}

	public boolean hasBlackListedFields(String... notAllowedParams) {
		checkNonNull(massAssignmentValidatorConfig, MassAssignmentValidatorConfig.class.getSimpleName()
				+ " should be set to use checkBlackList");
		boolean hasBlackListParam = massAssignmentValidatorConfig.hasBlackListedFields(notAllowedParams);
		dirtyValidation(hasBlackListParam);
		return hasBlackListParam;
	}

	private void checkNonNull(Object object, String message) {
		if (object == null) {
			throw new IllegalStateException(message);
		}
	}

	public boolean hasOnlyAllowedFields(String... allowedParams) {
		checkNonNull(massAssignmentValidatorConfig, MassAssignmentValidatorConfig.class.getSimpleName()
				+ " should be set to use checkWhiteList");
		boolean whiteListFailed = massAssignmentValidatorConfig.hasOnlyAllowedFields(allowedParams);
		dirtyValidation(whiteListFailed);
		return whiteListFailed;
	}

	public br.com.caelum.vraptor.validator.Validator navigate() {
		//TODO Should create other object, but I(Alberto) don't want. Be my guess :).
		return vraptorValidator;
	}
	
	

}
