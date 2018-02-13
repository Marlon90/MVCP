package de.mvcp.model.impl;

import de.mvcp.model.Article;

public class ArticleImpl implements Article {

	private int id;
	private int itemNumber;
	private String description;
	private int cost;
	private int price;
	
	public ArticleImpl(int id, int itemNumber, String description, int cost, int price) {

		this.id = id;
		this.itemNumber = itemNumber;
		this.description = description;
		this.cost = cost;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public String getDescription() {
		return description;
	}

	public int getCost() {
		return cost;
	}

	public int getPrice() {
		return price;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}