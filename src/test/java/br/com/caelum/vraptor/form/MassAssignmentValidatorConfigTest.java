package br.com.caelum.vraptor.form;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import static org.junit.Assert.*;

public class MassAssignmentValidatorConfigTest {

	@Test
	public void shouldBlockByBlackList() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertTrue(validator.hasBlackListedFields("user.login","user.email"));
	}
	
	@Test
	public void shouldBlockByBlackList2() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertFalse(validator.hasBlackListedFields("user.admin"));
	}
	
	@Test
	public void shouldBlockByWhiteList() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertFalse(validator.hasOnlyAllowedFields("user.name","user.password","user.email"));
	}
	
	@Test
	public void shouldAllowIfAllWhiteListAreSet() throws Exception {
		ImmutableMap<String, String> params = ImmutableMap.<String,String>of("user.login", "value", "user.email", "value", "user.name" ,"value", "user.password","value");
		MassAssignmentValidatorConfig validator = new MassAssignmentValidatorConfig(params);
		assertTrue(validator.hasOnlyAllowedFields("user.name","user.password","user.login","user.email"));
	}
}
