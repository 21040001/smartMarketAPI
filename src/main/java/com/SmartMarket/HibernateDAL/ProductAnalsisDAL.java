package com.SmartMarket.HibernateDAL;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.SmartMarket.Entity.Sales;

@Repository
public interface ProductAnalsisDAL extends JpaRepository<Sales, Long> {

	/*
	 * BU methodimiz oxirgi __ kun davomida eng ko'p sotilgan mahsulotlar olib
	 * keladi
	 */

	@Query(value = """
			    SELECT TOP (:limit) product_name, SUM(quantity) AS total_quantity
			    FROM sales
			    WHERE sale_date >= DATEADD(DAY, -:day, GETDATE()) AND store_id = :storeId
			    GROUP BY product_name
			    ORDER BY total_quantity DESC
			""", nativeQuery = true)
	Optional<List<Object[]>> getTheMostSaleProduct(@Param("day") int day, @Param("limit") int limit,
			@Param("storeId") int storeId);

	/*
	 * BU methodimiz oxirgi __ kun davomida eng kam sotilgan mahsulotlar olib keladi
	 */
	@Query(value = """
			    SELECT TOP (:limit) product_name, SUM(quantity) AS total_quantity
			    FROM sales
			    WHERE sale_date >= DATEADD(DAY, -:day, GETDATE()) AND store_id = :storeId
			    GROUP BY product_name
			    ORDER BY total_quantity ASC
			""", nativeQuery = true)
	Optional<List<Object[]>> getTheLeastSaleProduct(@Param("day") int day, @Param("limit") int limit,
			@Param("storeId") int storeId);

	@Query(value = "SELECT product_name, SUM(quantity) AS total_quantity\r\n"
				+ "FROM sales\r\n"
				+ "WHERE sale_date >= DATEADD(DAY, -:day, GETDATE()) AND store_id = :storeId AND id=2\r\n"
				+ "GROUP BY product_name\r\n"
				+ "ORDER BY total_quantity DESC;\r\n"
				+ "", nativeQuery = true)
	
	Optional<List<Object[]>> getSaleQuantityProduct(@Param("day") int day, @Param("storeId") String storeId);

		/*
		 * BU methodimiz oxirgi __ kun davommi sotilgan mahsulotlar sonini olib keladia 
		 */
	@Query(value="""
		    SELECT DATEPART(HOUR, sale_date) AS sale_hour, count(quantity) AS total_quantity
		    FROM sales
		    WHERE sale_date >= DATEADD(DAY, -:day, GETDATE())
		      AND store_id = :storeId
		    GROUP BY DATEPART(HOUR, sale_date)
		    ORDER BY sale_hour
		""",nativeQuery=true)

	Optional<List<Object[]>> getSaleQuantityByHour(@Param("day") int day, @Param("storeId") int storeId);

}
