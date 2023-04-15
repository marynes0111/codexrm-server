package io.github.codexrm.server.payload.response;

import java.util.List;

public class UserInfoResponse {
	private Integer id;
	private String username;
	private String email;
	private String name;
	private String lastName;
	private boolean enabled;
	private List<String> roles;

	public UserInfoResponse(Integer id, String username, String email, String name, String lastName,
		boolean enabled, List<String> roles) {

		this.id = id;
		this.username = username;
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.enabled = enabled;
		this.roles = roles;
	}

	public Integer getId() { return id; }

	public void setId(Integer id) { this.id = id; }

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public boolean isEnabled() { return enabled; }

	public void setEnabled(boolean enabled) { this.enabled = enabled; }

	public List<String> getRoles() { return roles; }

	public void setRoles(List<String> roles) { this.roles = roles; }
}