package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.util.List;


import com.SmartMarket.Entity.Sales;

//bu DAL SPRING jpa bilan yozilmadi chunki JPA va SALES o'zgaruvchi uyushmasligi bor bu sababdan bu normal JPA bilan tayyorlandi
public interface DALSalesInterface {

	List<Sales> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate);
    void addSales(Sales sales);
}
