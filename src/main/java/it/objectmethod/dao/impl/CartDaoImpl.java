package it.objectmethod.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.objectmethod.dao.ICartDao;
import it.objectmethod.models.CartArticle;
import it.objectmethod.utils.ConnectionFactory;

public class CartDaoImpl implements ICartDao{

	@Override
	public int getUserArticlesNumber(String username) {
		int cartCounter = 0;
		String sqlQuery = "SELECT SUM(quantita) AS cartArticles\r\n"
				+ "FROM carrello_dettaglio cd\r\n"
				+ "INNER JOIN carrello c\r\n"
				+ "ON c.id_carrello = cd.id_carrello\r\n"
				+ "INNER JOIN utente u\r\n"
				+ "ON u.id_utente = c.id_utente\r\n"
				+ "WHERE u.nome_utente = ?";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				cartCounter = resultSet.getInt("cartArticles");
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartCounter;
	}

	@Override
	public List<CartArticle> getUserCartList(String username) {
		List<CartArticle> cartList = new ArrayList<>();
		CartArticle cartArticle = null;
		String sqlQuery = "SELECT art.*, quantita\r\n"
				+ "FROM articolo art\r\n"
				+ "INNER JOIN carrello_dettaglio cd\r\n"
				+ "ON art.id_articolo = cd.id_articolo\r\n"
				+ "INNER JOIN carrello c\r\n"
				+ "ON c.id_carrello = cd.id_carrello\r\n"
				+ "INNER JOIN utente u\r\n"
				+ "ON u.id_utente = c.id_utente\r\n"
				+ "WHERE u.nome_utente = ?";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				cartArticle = new CartArticle();
				cartArticle.setId(resultSet.getInt("id_articolo"));
				cartArticle.setCode(resultSet.getString("codice_articolo"));
				cartArticle.setName(resultSet.getString("nome_articolo"));
				cartArticle.setQuantity(resultSet.getInt("quantita"));
				cartArticle.setAvailability(resultSet.getInt("disponibilita"));
				cartArticle.setPrice(resultSet.getInt("prezzo_unitario"));
				cartList.add(cartArticle);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartList;
	}

	@Override
	public void updateArticleQuantity(String username, int id, int quantity) {
		String sqlQuery = "UPDATE ecommerce.carrello_dettaglio cd\n"
				+ "INNER JOIN ecommerce.carrello c\n"
				+ "ON c.id_carrello = cd.id_carrello \n"
				+ "INNER JOIN ecommerce.utente u\n"
				+ "ON u.id_utente = c.id_utente \n"
				+ "INNER JOIN ecommerce.articolo art\n"
				+ "ON art.id_articolo = cd.id_articolo\n"
				+ "SET quantita = ?\n"
				+ "WHERE cd.id_articolo = ? AND u.nome_utente = ?";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, quantity);
			statement.setInt(2, id);
			statement.setString(3, username);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addToCart(String username, int id, int quantity) {
		String sqlQuery = "INSERT INTO carrello_dettaglio (id_carrello, quantita, id_articolo)\n"
				+ "SELECT id_carrello, ?, ?\n"
				+ "FROM carrello c\n"
				+ "INNER JOIN utente u\n"
				+ "ON u.id_utente = c.id_utente\n"
				+ "WHERE u.nome_utente = ?";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, quantity);
			statement.setInt(2, id);
			statement.setString(3, username);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CartArticle pickNewArticle(int id, int quantity) {
		CartArticle cartArticle = null;
		String sqlQuery = "SELECT * FROM ecommerce.articolo art\n"
				+ "WHERE art.id_articolo = ?";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				cartArticle = new CartArticle();
				cartArticle.setId(resultSet.getInt("id_articolo"));
				cartArticle.setCode(resultSet.getString("codice_articolo"));
				cartArticle.setName(resultSet.getString("nome_articolo"));
				cartArticle.setQuantity(quantity);
				cartArticle.setAvailability(resultSet.getInt("disponibilita"));
				cartArticle.setPrice(resultSet.getInt("prezzo_unitario"));
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartArticle;
	}

	@Override
	public void buyArticles(String username) {
		String sqlQuery = "DELETE FROM carrello_dettaglio cd WHERE cd.id_carrello = (\r\n"
				+ "SELECT c.id_carrello\r\n"
				+ "FROM carrello c\r\n"
				+ "INNER JOIN utente u\r\n"
				+ "ON c.id_utente = u.id_utente\r\n"
				+ "WHERE u.nome_utente = ?)";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateArticlesAvailability(String username) {
		String sqlQuery = "UPDATE articolo art\r\n"
				+ "INNER JOIN carrello_dettaglio cd\r\n"
				+ "ON art.id_articolo = cd.id_articolo\r\n"
				+ "INNER JOIN carrello c\r\n"
				+ "ON c.id_carrello = cd.id_carrello\r\n"
				+ "INNER JOIN utente u\r\n"
				+ "ON u.id_utente = c.id_utente\r\n"
				+ "SET art.disponibilita = (\r\n"
				+ "SELECT disponibilita - quantita\r\n"
				+ "WHERE u.nome_utente = ?)\r\n"
				+ "WHERE art.id_articolo = (\r\n"
				+ "SELECT art.id_articolo\r\n"
				+ "WHERE u.nome_utente = ?)";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setString(2, username);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeAllArticles(String username, int id) {
		String sqlQuery = "DELETE FROM carrello_dettaglio cd WHERE cd.id_carrello IN (\r\n"
				+ "SELECT c.id_carrello\r\n"
				+ "FROM carrello c\r\n"
				+ "INNER JOIN utente u\r\n"
				+ "ON c.id_utente = u.id_utente\r\n"
				+ "WHERE u.nome_utente = ?)\r\n"
				+ "AND cd.id_articolo = ?";
		try {
			Connection connection = ConnectionFactory.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setInt(2, id);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
