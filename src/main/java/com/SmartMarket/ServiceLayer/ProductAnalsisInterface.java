package com.SmartMarket.ServiceLayer;

import java.util.List;


public interface ProductAnalsisInterface {

	List<Object[]> getTheMostSaleProduct(int day,int limit);
	List<Object[]> getTheLeastSaleProduct(int day, int limit);
	List<Object[]> getSaleQuantityByHour(int day);

}
