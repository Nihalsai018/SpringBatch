package com.example.Cloud.CsvtoDataBase.Entity;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	private int id;
	private String product;
	private Double cost;
	private Double prodDis;
	private Double gst;
	public Product() {
		super();
	}
	public Product(int id, String product, Double cost, Double prodDis, Double gst) {
		super();
		this.id = id;
		this.product = product;
		this.cost = cost;
		this.prodDis = prodDis;
		this.gst = gst;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getProdDis() {
		return prodDis;
	}
	public void setProdDis(Double prodDis) {
		this.prodDis = prodDis;
	}
	public Double getGst() {
		return gst;
	}
	public void setGst(Double gst) {
		this.gst = gst;
	}
	
	

		

}
