package br.com.caelum.vraptor.form;

import java.util.HashSet;
import java.util.Map;

import com.google.common.collect.Sets;

public class MassAssignmentValidatorConfig {

	private Map<String, ?> params;

	public MassAssignmentValidatorConfig(Map<String, ?> params) {
		this.params = params;
	}

	public boolean blackList(String... notAllowedParams) {
		HashSet<String> notAllowed = Sets.newHashSet(notAllowedParams);
		return Sets.intersection(notAllowed,params.keySet()).isEmpty();
	}
	
	public boolean whiteList(String... allowedParams) {
		HashSet<String> allowed = Sets.newHashSet(allowedParams);
		for (String param : params.keySet()) {
			if(!allowed.contains(param)){
				return false;
			}
		}
		return true;
	}
	
	

}
