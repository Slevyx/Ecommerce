package it.objectmethod.dao;

import java.sql.SQLException;
import java.util.List;

import it.objectmethod.models.CartArticle;

public interface ICartDao {

	public int getUserArticlesNumber(String username) throws SQLException;

	public List<CartArticle> getUserCartList(String username) throws SQLException;

	public void updateArticleQuantity(String username, int id, int quantity) throws SQLException;

	public CartArticle addToCart(String username, int id, int quantity) throws SQLException;

	public void buyArticles(String username) throws SQLException;

	public void addRemoveArticle(String username, int id, int quantity) throws SQLException;

	public void removeAllArticles(String username, int id) throws SQLException;
}
