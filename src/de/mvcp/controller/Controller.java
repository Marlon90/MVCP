package de.mvcp.controller;

import java.sql.SQLException;

import de.mvcp.model.Article;
import javafx.collections.ObservableList;

public interface Controller {

	public void showView();

	public ObservableList<Article> getAll() throws SQLException;

	public void setArticle(int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException;

	public void deleteArticle(int id) throws SQLException;

	public void updateArticle(int articleId, int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException;

}
