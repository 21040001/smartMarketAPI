package com.SmartMarket.HibernateDAL;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.Entity.ProductsObject;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
public class DALMonth implements MonthInterface {

	private final EntityManager entityManager ;
	
	public DALMonth(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void updateMonthFoyda(int storeId, LocalDate date, long eski, long yeni) {
	    Session session = entityManager.unwrap(Session.class);
	    long foyda = eski + yeni;

	    String sql = """
	        MERGE month_foyda AS target
	        USING (SELECT :storeId AS store_id, :date AS date) AS source
	        ON (target.store_id = source.store_id AND target.date = source.date)
	        WHEN MATCHED THEN 
	            UPDATE SET month_foyda = :foyda
	        WHEN NOT MATCHED THEN 
	            INSERT (store_id, date, month_foyda)
	            VALUES (:storeId, :date, :foyda);
	        """;

	    session.createNativeQuery(sql)
	           .setParameter("storeId", storeId)
	           .setParameter("date", date)
	           .setParameter("foyda", foyda)
	           .executeUpdate();
	}

	@Override
	@Transactional
	public List<MonthFoyda> getMonthFoyda(int storeId, LocalDate date) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "SELECT month_foyda, date \r\n"
				+ "FROM month_foyda \r\n"
				+ "WHERE store_id = :id \r\n"
				+ "AND YEAR(date) = YEAR(CAST(:date AS DATE))\r\n"
				+ "AND MONTH(date) = MONTH(CAST(:date AS DATE))";
	    List<MonthFoyda> eski = session.createNativeQuery(sql)
	                                           .setParameter("id", storeId)
	                                           .setParameter("date", date)
	                                           .getResultList();
	    return eski;
	}



}
