package br.com.caelum.vraptor.form;

import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

public class MirrorMethodExecutor{

	public <T> T invoke(Method method, Object instance, Object... args) {
		return (T) new Mirror().on(instance).invoke().method(method).withArgs(args);
	}

}
