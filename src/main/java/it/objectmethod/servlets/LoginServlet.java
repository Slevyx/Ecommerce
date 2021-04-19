package it.objectmethod.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.objectmethod.dao.IUsersDao;
import it.objectmethod.dao.impl.UsersDaoImpl;
import it.objectmethod.models.User;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String forwardTo = "pages/LoginPage.jsp";
		User user = null;
		HttpSession session = request.getSession();
		IUsersDao usersDao = new UsersDaoImpl();
		if(username == null || password == null || username.isBlank() || password.isBlank()) {
			request.setAttribute("error", "Username and Password cannot be empty.");
		}
		else{
			try {
				user = usersDao.getUser(username, password);
				if(user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
					session.setAttribute("loggedUser", username);
					forwardTo = "Articles";
				}
				else {
					request.setAttribute("error", "Wrong Username or Password.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher(forwardTo).forward(request, response);
	}
}
