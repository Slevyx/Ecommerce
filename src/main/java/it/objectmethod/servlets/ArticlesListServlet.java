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

import it.objectmethod.dao.IArticlesDao;
import it.objectmethod.dao.impl.ArticlesDaoImpl;
import it.objectmethod.models.Article;

@WebServlet("/Articles")
public class ArticlesListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Article> articlesList = new ArrayList<>();
		IArticlesDao articlesDao = new ArticlesDaoImpl();
		HttpSession session = request.getSession();
		try {
			articlesList = articlesDao.getArticles();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("articlesList", articlesList);
		request.getRequestDispatcher("ArticlesToBeBought").forward(request, response);
	}
}
