package de.mvcp.controller.impl;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import de.mvcp.controller.Controller;
import de.mvcp.dao.ArticleDao;
import de.mvcp.dao.impl.ArticleDaoImpl;
import de.mvcp.model.Article;
import de.mvcp.view.impl.ViewImpl;

public class ControllerImpl implements Controller {

	private ArticleDao ad = new ArticleDaoImpl();

	public ControllerImpl() {

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