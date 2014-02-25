package br.com.caelum.vraptor.form;

public class Task {

	private String name;
	private Project project;

	public Task(String name, Project project) {
		super();
		this.name = name;
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public Project getProject() {
		return project;
	}
}
