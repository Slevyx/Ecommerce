package it.objectmethod.dao;

import java.sql.SQLException;

import it.objectmethod.models.User;

public interface IUsersDao {
	
	public User getUser(String username, String password) throws SQLException;
}
