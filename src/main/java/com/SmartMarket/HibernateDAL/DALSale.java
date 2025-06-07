package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.ProductsObject;
import com.SmartMarket.Entity.Sales;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class DALSale implements DALSalesInterface {

    private final EntityManager entityManager;

    @Autowired
    public DALSale(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    

	@Override
	@Transactional
	public void addSales(Sales sales) {
		Session session = entityManager.unwrap(Session.class);
        try {
            session.persist(sales);
        } catch (NoResultException e) {
            System.out.print(e);
        }
	}



	@Override
    @Transactional
	public List<Sales> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate) {
		Session session = entityManager.unwrap(Session.class);

	    String sql = "SELECT * FROM Sales WHERE store_id = "+storeId+" AND sale_date >= '"+todayDate+"' AND sale_date < '"+tommorowDate+"'";
	    List<Sales> sales = session.createNativeQuery(sql, Sales.class)
	                                           .getResultList();

	    return sales;
	}
}
