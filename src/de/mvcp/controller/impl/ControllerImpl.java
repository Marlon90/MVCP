package de.mvcp.controller.impl;

import java.sql.SQLException;

import de.mvcp.controller.Controller;
import de.mvcp.dao.ArticleDao;
import de.mvcp.dao.impl.ArticleDaoImpl;
import de.mvcp.model.Article;
import de.mvcp.view.impl.ViewImpl;
import javafx.collections.ObservableList;

public class ControllerImpl implements Controller {

	private ArticleDao ad = new ArticleDaoImpl();

	public ControllerImpl() {

	}

	public void showView() {
		javafx.application.Application.launch(ViewImpl.class);
	}

	public ObservableList<Article> getAll() throws SQLException {
		return ad.findAll();
	}

	public void setArticle(int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException {
		ad.addArticle(articleNummer, articleName, articleCost, articlePrice);
	}

	public void deleteArticle(int id) throws SQLException {
		ad.deleteArticle(id);
	}

	public void updateArticle(int articleId, int articleNummer, String articleName, int articleCost, int articlePrice)
			throws SQLException {
		ad.updateArticle(articleId, articleNummer, articleName, articleCost, articlePrice);
	}
}