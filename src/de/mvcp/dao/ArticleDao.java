package de.mvcp.dao;

import java.sql.SQLException;

import de.mvcp.model.Article;
import javafx.collections.ObservableList;

public interface ArticleDao {

	ObservableList<Article> findAll() throws SQLException;

	void updateArticle(int articleId, int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException;

	void deleteArticle(int id) throws SQLException;

	void addArticle(int articleNumber, String articleName, int articleCost, int articlePrice) throws SQLException;
}
