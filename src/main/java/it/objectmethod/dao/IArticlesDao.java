package it.objectmethod.dao;

import java.sql.SQLException;
import java.util.List;

import it.objectmethod.models.Article;

public interface IArticlesDao {

	public List<Article> getArticles() throws SQLException;
}
