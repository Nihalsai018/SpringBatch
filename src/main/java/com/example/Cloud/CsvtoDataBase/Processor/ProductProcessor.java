package com.example.Cloud.CsvtoDataBase.Processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.Cloud.CsvtoDataBase.Entity.Product;

public class ProductProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {
		double cost=item.getCost();
		item.setProdDis(cost*12/100.0);
		item.setGst(cost*25/100.0);
		return item;
	}
	
	
}
