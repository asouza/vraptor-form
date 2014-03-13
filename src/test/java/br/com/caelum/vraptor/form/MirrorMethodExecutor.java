package br.com.caelum.vraptor.form;

import java.lang.reflect.Method;

import javax.enterprise.inject.Vetoed;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.reflection.MethodExecutor;

@Vetoed
public class MirrorMethodExecutor implements MethodExecutor{

	@Override
	public <T> T invoke(Method method, Object instance, Object... args) {
		return (T) new Mirror().on(instance).invoke().method(method).withArgs(args);
	}

}
