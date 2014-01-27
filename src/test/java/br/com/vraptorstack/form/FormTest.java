package br.com.vraptorstack.form;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.vraptor.validator.beanvalidation.MessageInterpolatorFactory;
import br.com.caelum.vraptor.validator.beanvalidation.ValidatorFactoryCreator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
		Form<Usuario> form = new Form<Usuario>(validator, interpolator,locale);

		Usuario usuario = new Usuario("email@gmail.com", "Jonny");
		form.bind(usuario);
		assertFalse(form.hasErrors());
	}
	
	@Test
	public void shouldFailValidateBased() {
		Form<Usuario> form = new Form<Usuario>(validator, interpolator,locale);
		
		Usuario usuario = new Usuario(null,null);
		form.bind(usuario);
		assertTrue(form.hasErrors());
	}
	
	@Test
	public void shouldNotValidateTwice() {
		Form<Usuario> form = new Form<Usuario>(validator, interpolator,locale);
		
		Usuario usuario = new Usuario(null,null);
		form.bind(usuario);
		form.hasErrors();
		form.hasErrors();
		
		//find why this verification is not being executed.
		verify(validator,times(1)).validate(Mockito.same(usuario));
	}
	
	
	
	
}
