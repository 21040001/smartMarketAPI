package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.util.List;

import com.SmartMarket.Entity.MonthFoyda;

public interface MonthInterface {
	void updateMonthFoyda(int storeId, LocalDate date, long eski, long yeni);
	List<MonthFoyda> getMonthFoyda(int storeId, LocalDate date);
}
