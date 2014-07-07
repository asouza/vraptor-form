package br.com.caelum.vraptor.form.factory;

import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.form.Form;
import br.com.caelum.vraptor.form.MassAssignmentValidatorConfig;
import br.com.caelum.vraptor.form.MirrorMethodExecutor;
import br.com.caelum.vraptor.http.MutableRequest;

@RequestScoped
public class FormFactory {
	@Inject
	private Validator validator;
	@Inject
	private MessageInterpolator interpolator;
	@Inject
	private Locale locale;
	@Inject
	private MirrorMethodExecutor methodExecutor;
	@Inject
	private MutableRequest mutableRequest;
	@Inject
	private br.com.caelum.vraptor.validator.Validator vraptorValidator;
	@Inject
	private Result result;

	@Produces
	@Dependent
	public <T> Form<T> create(InjectionPoint injectionPoint) {
		if (injectionPoint != null) {
			ParameterizedType type = (ParameterizedType) injectionPoint.getType();
			Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
			Form<T> form = new Form<T>(validator, interpolator, locale, methodExecutor, vraptorValidator, clazz);

			form.setMassAssignmentValidatorConfig(new MassAssignmentValidatorConfig(mutableRequest.getParameterMap()));

			result.include("form", form);
			return form;
		}

		return null;

	}
}
