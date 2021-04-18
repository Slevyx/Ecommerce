package it.objectmethod.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.objectmethod.dao.ICartDao;
import it.objectmethod.dao.impl.CartDaoImpl;

@WebServlet("/ArticlesToBeBought")
public class ArticlesToBeBoughtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cartCounter = 0;
		ICartDao cartDao = new CartDaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedUser");
		try {
			cartCounter = cartDao.getUserArticlesNumber(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("user_articles", cartCounter);
		request.getRequestDispatcher("UserCartList").forward(request, response);
	}
}
