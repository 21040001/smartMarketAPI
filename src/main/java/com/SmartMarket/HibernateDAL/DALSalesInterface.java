package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.SmartMarket.Entity.Sales;

public interface DALSalesInterface {

	List<Sales> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate);
    void addSales(Sales sales);
}
