package it.objectmethod.servlets;

import java.io.IOException;
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

@WebServlet("/AddRemoveArticles")
public class AddRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		ICartDao cartDao = new CartDaoImpl();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("loggedUser");
		List<CartArticle> cartList = (List<CartArticle>) session.getAttribute("cartList");
		int cartCounter = (int) session.getAttribute("user_articles");
		double total = (double) session.getAttribute("total");
		if(action == null || id == null) {
			request.setAttribute("error", "System has encountered an error while trying to add or remove articles.");
		}
		else {
			for(CartArticle article : cartList) {
				if(article.getId() == Integer.parseInt(id)) {
					if(action.equalsIgnoreCase("add")) {
						int newQuantity = article.getQuantity() + 1;
						cartDao.updateArticleQuantity(username, Integer.parseInt(id), newQuantity);
						article.setQuantity(newQuantity);
						total += article.getPrice();
						cartCounter += 1;
						break;
					}
					else if(action.equalsIgnoreCase("remove")){
						int newQuantity = article.getQuantity() - 1;
						if(newQuantity > 0) {
							cartDao.updateArticleQuantity(username, Integer.parseInt(id), newQuantity);
							article.setQuantity(newQuantity);
							total -= article.getPrice();
							cartCounter -= 1;
							break;
						}
						else {
							cartDao.removeAllArticles(username, Integer.parseInt(id));
							cartList.remove(article);
							total -= article.getPrice();
							cartCounter -= 1;
							break;
						}
					}
					else {
						request.setAttribute("error", "System has encountered an error while trying to add or remove articles.");
					}
				}
			}
		}
		session.setAttribute("total", total);
		session.setAttribute("user_articles", cartCounter);
		session.setAttribute("cartList", cartList);
		request.getRequestDispatcher("Total").forward(request, response);
	}
}
