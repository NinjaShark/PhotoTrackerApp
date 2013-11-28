package edu.vt.cs5744.phototracker.service;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -1092933581605602430L;

	String name ;
	String dateOfBirth ;
	String userName ;
	String password ;
	String email ;

	public User(String name, String dateOfBirth, String userName,
			String password, String email) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", dateOfBirth=" + dateOfBirth
				+ ", userName=" + userName + ", password=" + password
				+ ", email=" + email + "]";
	}

	public String getName() {
		return name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	

}
