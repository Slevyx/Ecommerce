package it.objectmethod.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.objectmethod.dao.ICartDao;
import it.objectmethod.dao.impl.CartDaoImpl;
import it.objectmethod.models.CartArticle;

@WebServlet("/Availability")
public class AvailabilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardTo = "pages/CartPage.jsp";
		ICartDao cartDao = new CartDaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedUser");
		List<CartArticle> cartList = (List<CartArticle>) session.getAttribute("cartList");
		try {
			cartList = cartDao.getUserCartList(username);
			List<CartArticle> filteredCartList = cartList.stream().filter(article -> article.getQuantity() > article.getAvailability()).collect(Collectors.toList());
			if(filteredCartList.isEmpty()) {
				forwardTo = "Buy";
			}
			else {
				request.setAttribute("articlesAvailabilityMessage", "One or more selected articles are not available. Please remove unavailable products to complete the purchase.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("cartList", cartList);
		request.getRequestDispatcher(forwardTo).forward(request, response);
	}
}
