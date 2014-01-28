package br.com.vraptorstack.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.beanvalidation.MessageInterpolatorFactory;
import br.com.caelum.vraptor.validator.beanvalidation.ValidatorFactoryCreator;

public class FormTest {
	
	private Validator validator;
	private MessageInterpolator interpolator;
	private Locale locale;

	@Before
	public void setup(){
		ValidatorFactory factory = new ValidatorFactoryCreator().getInstance();
		validator = spy(factory.getValidator());
		interpolator = new MessageInterpolatorFactory(factory).getInstance();
		locale = Locale.US;
	}

	@Test
	public void shouldValidateBased() {
		Form<User> form = newForm();

		User usuario = new User("email@gmail.com", "Jonny");
		form.bind(usuario);
		assertFalse(form.hasErrors());
	}

	@Test
	public void shouldHasErrorsWhenValidationFail() {
		Form<User> form = newForm();
		
		User usuario = new User(null,null);
		form.bind(usuario);
		assertTrue(form.hasErrors());	
	}
	
	@Test
	public void shouldCountErrorsWhenValidationFail() {
		Form<User> form = newForm();
		
		User usuario = new User(null,null);
		form.bind(usuario);
		
		assertEquals(2,form.getErrors().count());	
	}
	
	@Test
	public void shouldAccessFailedProperty(){
		Form<User> form = newForm();
		
		User usuario = new User(null,null);
		form.bind(usuario);
		
		FormField field = form.getErrors().get("email");
		
		assertEquals(1,field.getErrors().size());
		assertEquals("may not be null",field.getMessage(0));
	}

	private Form<User> newForm() {
		return new Form<User>(validator, interpolator,locale, User.class);
	}

}
