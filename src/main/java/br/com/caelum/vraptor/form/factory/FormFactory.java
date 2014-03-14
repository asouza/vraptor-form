package br.com.caelum.vraptor.form.factory;

import java.lang.reflect.ParameterizedType;
import java.util.Locale;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.events.ReadyToExecuteMethod;
import br.com.caelum.vraptor.form.Form;
import br.com.caelum.vraptor.form.MassAssignmentValidatorConfig;
import br.com.caelum.vraptor.form.WithMassAssignment;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.Parameter;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.http.ParametersProvider;
import br.com.caelum.vraptor.http.ValuedParameter;
import br.com.caelum.vraptor.reflection.MethodExecutor;

@RequestScoped
public class FormFactory {
	@Inject
	private Validator validator;
	@Inject
	private MessageInterpolator interpolator;
	@Inject
	private Locale locale;
	@Inject
	private MethodExecutor methodExecutor;
	@Inject
	private MutableRequest mutableRequest;
	@Inject
	private br.com.caelum.vraptor.validator.Validator vraptorValidator; 
	
	private Object needsFormParameter;
	private static final Logger logger = LoggerFactory.getLogger(FormFactory.class);

	@Produces
	@Dependent
	public <T> Form<T> create(InjectionPoint injectionPoint) {

		ParameterizedType type = (ParameterizedType) injectionPoint.getType();
		Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
		Form<T> form = new Form<T>(validator, interpolator, locale, methodExecutor,vraptorValidator,clazz);

		Annotated field = injectionPoint.getAnnotated();
		WithMassAssignment massAssignment = field.getAnnotation(WithMassAssignment.class);
		if (massAssignment != null) {
			logger.debug("Defining Mass Assignment for Form<"+clazz.getSimpleName()+">");
			form.setMassAssignmentValidatorConfig(new MassAssignmentValidatorConfig(mutableRequest.getParameterMap()));
		}

		if (needsFormParameter != null) {
			logger.debug("Binding first parameter for Form<"+clazz.getSimpleName()+">");
			form.bind((T) needsFormParameter);
		}

		return form;

	}

	public void observes(@Observes ReadyToExecuteMethod readyToExecuteMethod, MethodInfo methodInfo) {
		ValuedParameter[] valuedParameters = methodInfo.getValuedParameters();
		if (valuedParameters.length > 0) {
			this.needsFormParameter = valuedParameters[0].getValue();
		}
	}
}
