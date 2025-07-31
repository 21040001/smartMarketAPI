package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import com.SmartMarket.Entity.MonthFoyda;

public interface MonthServiceInterfcae {
	void updateMonthFoyda( LocalDate date, long eski, long yeni);
	List<MonthFoyda> getMonthFoyda( LocalDate date);
	long foydaFindByStoreIdAndDateMonthYear( LocalDate date);
}
