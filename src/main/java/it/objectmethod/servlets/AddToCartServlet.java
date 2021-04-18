package it.objectmethod.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
				try {
					cartList.add(cartDao.addToCart(username, Integer.parseInt(id), 1));
					for(CartArticle article : cartList) {
						System.out.println(article.getId());
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				//UPDATE
				try {
					for(CartArticle article : cartList) {
						if(article.getId() == Integer.parseInt(id)) {
							int newQuantity = filteredCartList.get(0).getQuantity() + 1;
							cartDao.updateArticleQuantity(username, Integer.parseInt(id), newQuantity);
							article.setQuantity(newQuantity);
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		session.setAttribute("user_articles", cartCounter + 1);
		session.setAttribute("cartList", cartList);
		request.getRequestDispatcher("pages/ShopPage.jsp").forward(request, response);
	}
}
