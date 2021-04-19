package it.objectmethod.servlets;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/Buy")
public class BuyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ICartDao cartDao = new CartDaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedUser");
		List<CartArticle> cartList = (List<CartArticle>) session.getAttribute("cartList");
		try {
			cartDao.buyArticles(username);
			cartList.clear();
			session.setAttribute("user_articles", 0);
			request.setAttribute("purchaseMessage", "Your products have been purchased successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("cartList", cartList);
		request.getRequestDispatcher("pages/CartPage.jsp").forward(request, response);
	}
}
