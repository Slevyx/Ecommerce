package it.objectmethod.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.objectmethod.dao.IArticlesDao;
import it.objectmethod.models.Article;
import it.objectmethod.utils.ConnectionFactory;

public class ArticlesDaoImpl implements IArticlesDao{

	@Override
	public List<Article> getArticles() {
		List<Article> articlesList = new ArrayList<>();
		String sqlQuery = "SELECT * FROM articolo";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Article article = new Article();
				article.setId(resultSet.getInt("id_articolo"));
				article.setCode(resultSet.getString("codice_articolo"));
				article.setName(resultSet.getString("nome_articolo"));
				article.setAvailability(resultSet.getInt("disponibilita"));
				article.setPrice(resultSet.getFloat("prezzo_unitario"));
				articlesList.add(article);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articlesList;
	}
}
