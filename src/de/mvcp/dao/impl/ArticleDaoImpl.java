package de.mvcp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.mvcp.dao.ArticleDao;
import de.mvcp.jdbc.connector.ConnectorFactory;
import de.mvcp.jdbc.connector.impl.ConnectorFactoryImpl;
import de.mvcp.model.Article;
import de.mvcp.model.impl.ArticleImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArticleDaoImpl implements ArticleDao {

	ConnectorFactory conn;
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;

	private Connection getConnection() throws SQLException {
		Connection conn;
		conn = ConnectorFactoryImpl.getInstance().getConnection();
		return conn;
	}

	public ObservableList<Article> findAll() throws SQLException {
		try {
			String queryString = "SELECT * FROM articles";
			connection = getConnection();
			ptmt = connection.prepareStatement(queryString);
			resultSet = ptmt.executeQuery();
			ObservableList<Article> articleList = FXCollections.observableArrayList();

			if (!resultSet.isBeforeFirst()) {
				System.out.println("No data");
			}

			while (resultSet.next()) {
				articleList.add(new ArticleImpl(resultSet.getInt("article_id"), resultSet.getInt("article_nummer"),
						resultSet.getString("article_description"), resultSet.getInt("article_cost"),
						resultSet.getInt("article_price")));
			}
			return articleList;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (ptmt != null)
					ptmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public void deleteArticle(int id) throws SQLException {
		PreparedStatement stmt = getConnection().prepareStatement("DELETE FROM articles WHERE article_id = ?");
		stmt.setInt(1, id);
		int i = stmt.executeUpdate();
		System.out.println(i + " records deleted");
	}

	@Override
	public void addArticle(int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException {
		PreparedStatement stmt = getConnection().prepareStatement(
				"INSERT INTO articles (article_nummer, article_description, article_cost, article_price) VALUES (?,?,?,?);");
		stmt.setInt(1, articleNummer); // 1 specifies the first parameter in the query
		stmt.setString(2, articleName);
		stmt.setInt(3, articleCost);
		stmt.setInt(4, articlePrice);

		int i = stmt.executeUpdate();
		System.out.println(i + " records inserted");
	}

	@Override
	public void updateArticle(int articleId, int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException {

		PreparedStatement stmt = getConnection().prepareStatement(
				"UPDATE articles SET article_nummer = ?, article_description = ?, article_cost = ?, article_price = ? WHERE article_id == "
						+ articleId + ";");
		stmt.setInt(1, articleNummer);
		stmt.setString(2, articleName);
		stmt.setInt(3, articleCost);
		stmt.setInt(4, articlePrice);
		System.out.println("UPDATE articles SET article_nummer = " + articleNummer + ", article_description = \""
				+ articleName + "\", article_cost = " + articleCost + ", article_price = " + articlePrice
				+ " WHERE article_id == " + articleId + ";");

		int i = stmt.executeUpdate();
		System.out.println(i + " records changed");
	}
}