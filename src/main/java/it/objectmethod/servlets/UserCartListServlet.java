package it.objectmethod.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.objectmethod.dao.ICartDao;
import it.objectmethod.dao.impl.CartDaoImpl;
import it.objectmethod.models.CartArticle;

@WebServlet("/UserCartList")
public class UserCartListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CartArticle> cartList = new ArrayList<>();
		ICartDao cartDao = new CartDaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedUser");
		try {
			cartList = cartDao.getUserCartList(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("cartList", cartList);
		request.getRequestDispatcher("pages/ShopPage.jsp").forward(request, response);
	}
}
