package it.objectmethod.dao;

import java.util.List;

import it.objectmethod.models.CartArticle;

public interface ICartDao {

	public int getUserArticlesNumber(String username);

	public List<CartArticle> getUserCartList(String username);

	public void updateArticleQuantity(String username, int id, int quantity);

	public void addToCart(String username, int id, int quantity);
	
	public CartArticle pickNewArticle(int id, int quantity);
	
	public void updateArticlesAvailability(String username);
	
	public void buyArticles(String username);

	public void addRemoveArticle(String username, int id, int quantity);

	public void removeAllArticles(String username, int id);
}
