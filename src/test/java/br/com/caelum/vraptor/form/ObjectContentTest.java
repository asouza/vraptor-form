package br.com.caelum.vraptor.form;

import ognl.OgnlException;

import org.junit.Test;

import br.com.caelum.vraptor.form.ObjectContent;

import static org.junit.Assert.assertEquals;

public class ObjectContentTest {

	@Test
	public void shouldUseContentIfItsNotNull() {
		String name = "leo";
		User user = new User("leo@leo.com", name);
		
		ObjectContent objectContent = objectContent(user);
		
		assertEquals(name, objectContent.toString());
	}
	
	@Test
	public void shouldUseContentNavigatingIfItsNotNull() throws OgnlException {
		String projectName = "project";
		User user = new User("leo@leo.com", projectName, new Task("task",new Project(projectName)));
		
		ObjectContent objectContent = objectContent(user,"task.project.name");
		
		assertEquals(projectName, objectContent.toString());
	}

	@Test
	public void shouldDefaultValueIfContentIsNull() {
		User user = new User("leo@leo.com", null);
		
		ObjectContent objectContent = objectContent(user);
		
		assertEquals(User.NAME_DEFAULT_TEXT, objectContent.toString());
	}

	private ObjectContent objectContent(User user) {
		return  new ObjectContent(user, "name").orDefault();
	}
	
	private ObjectContent objectContent(User user,String propertyPath) {
		return  new ObjectContent(user, propertyPath).orDefault();
	}

}
