package br.com.caelum.vraptor.form;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import static org.junit.Assert.*;

public class MassAssignmentValidatorConfigTest {

	@Test
	public void shouldBlockByBlackList() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertFalse(validator.blackList("user.login","user.email"));
	}
	
	@Test
	public void shouldBlockByWhiteList() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertFalse(validator.whiteList("user.name","user.password","user.email"));
	}
	
	@Test
	public void shouldAllowIfAllWhiteListAreSet() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertTrue(validator.whiteList("user.name","user.password","user.login","user.email"));
	}
}
