package it.objectmethod.servlets;

import java.io.IOException;
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

@WebServlet("/AddToCart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ICartDao cartDao = new CartDaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedUser");
		List<CartArticle> cartList = (List<CartArticle>) session.getAttribute("cartList");
		int cartCounter = (int) session.getAttribute("user_articles");
		if(id == null) {
			request.setAttribute("error", "Null id.");
		}
		else {
			List<CartArticle> filteredCartList = cartList.stream().filter(article -> article.getId() == Integer.parseInt(id)).collect(Collectors.toList());
			if(filteredCartList.isEmpty()) {
				//INSERT INTO
				cartDao.addToCart(username, Integer.parseInt(id), 1);
				cartList.add(cartDao.pickNewArticle(Integer.parseInt(id), 1));
				cartCounter += 1;
			}
			else {
				//UPDATE
				for(CartArticle article : cartList) {
					if(article.getId() == Integer.parseInt(id)) {
						int newQuantity = filteredCartList.get(0).getQuantity() + 1;
						cartDao.updateArticleQuantity(username, Integer.parseInt(id), newQuantity);
						article.setQuantity(newQuantity);
						cartCounter += 1;
						break;
					}
				}
			}
		}
		session.setAttribute("user_articles", cartCounter);
		session.setAttribute("cartList", cartList);
		request.getRequestDispatcher("pages/ShopPage.jsp").forward(request, response);
	}
}
