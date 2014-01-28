package br.com.vraptorstack.form;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectContentTest {

	@Test
	public void shouldUseContentIfItsNotNull() {
		String name = "leo";
		User user = new User("leo@leo.com", name);
		
		ObjectContent objectContent = objectContent(user);
		
		assertEquals(name, objectContent.toString());
	}

	@Test
	public void shouldDefaultValueIfContentIsNull() {
		User user = new User("leo@leo.com", null);
		
		ObjectContent objectContent = objectContent(user);
		
		assertEquals(User.NAME_DEFAULT_TEXT, objectContent.toString());
	}

	private ObjectContent objectContent(User user) {
		return  new ObjectContent(user, "nome").orDefault();
	}

}
