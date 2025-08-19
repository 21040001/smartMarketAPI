package com.SmartMarket.control;

import java.util.List;

public interface AnalysisControlInterface {
	List<Object[]> getTheMostSaleProduct(int day,int limit);
	List<Object[]> getTheLeastSaleProduct(int day, int limit);
	List<Object[]> getSaleQuantityByHour(int day);
}
