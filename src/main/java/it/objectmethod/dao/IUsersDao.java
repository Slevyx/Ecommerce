package it.objectmethod.dao;

import it.objectmethod.models.User;

public interface IUsersDao {
	
	public User getUser(String username, String password);
}
