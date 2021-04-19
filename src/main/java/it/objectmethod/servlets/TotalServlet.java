package it.objectmethod.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.objectmethod.models.CartArticle;

@WebServlet("/Total")
public class TotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<CartArticle> cartList = (List<CartArticle>) session.getAttribute("cartList");
		double total = 0;
		for(CartArticle article : cartList) {
			total = total + (article.getPrice() * article.getQuantity());
		}
		session.setAttribute("total", total);
		request.getRequestDispatcher("pages/CartPage.jsp").forward(request, response);
	}
}
