package br.com.vraptorstack.form;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.beanvalidation.MessageInterpolatorFactory;
import br.com.caelum.vraptor.validator.beanvalidation.ValidatorFactoryCreator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormTest {
	
	private Validator validator;
	private MessageInterpolator interpolator;
	private Locale locale;

	@Before
	public void setup(){
		ValidatorFactory factory = new ValidatorFactoryCreator().getInstance();
		validator = factory.getValidator();
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

	private static class Usuario {
		@NotNull
		private String email;
		@NotNull
		private String nome;

		public Usuario(String email, String nome) {
			super();
			this.email = email;
			this.nome = nome;
		}

	}
}
