package com.SmartMarket.HibernateDAL;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.ProductsObject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Repository
public class DALProduct implements DALProductInterface{

	private final EntityManager entityManager ;

	@Autowired
	public DALProduct(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void addProduct(ProductsObject product) {
		 Session session = entityManager.unwrap(Session.class);
	        try {
	            session.persist(product);
	        } catch (NoResultException e) {
	            System.out.print(e);
	        }
	}

	@Override
	@Transactional
	public void deleteProduct(ProductsObject product) {
	    ProductsObject managed = entityManager.merge(product); // 🔁 attached hale getir
	    entityManager.remove(managed);                         // ✅ şimdi silebilirsin
	}


	@Override
	@Transactional
	public void updateProduct(ProductsObject product) {
		Session session = entityManager.unwrap(Session.class);
        try {
            session.merge(product);
        } catch (NoResultException e) {
            System.out.print(e);
        }
	}

	@Override
	public ProductsObject getProduct(int id) {
		try {
	        Session session = entityManager.unwrap(Session.class);
	        ProductsObject product = session.get(ProductsObject.class, id);
	        return product;
	    } catch (Exception e) {
	        System.out.println("Mahsulot topilmadi: " + e.getMessage());
	        return null;
	    }
	}

	@Override
	public List<ProductsObject> getAllProducts(int id) {
	    Session session = entityManager.unwrap(Session.class);

	    String sql = "SELECT * FROM products WHERE store_id = :id";
	    List<ProductsObject> products = session.createNativeQuery(sql, ProductsObject.class)
	                                           .setParameter("id", id)
	                                           .getResultList();

	    return products;
	}

	
}
