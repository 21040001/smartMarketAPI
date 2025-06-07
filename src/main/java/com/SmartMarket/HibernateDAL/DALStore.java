package com.SmartMarket.HibernateDAL;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.Stores;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class DALStore implements DALStoresInterface {

    private final EntityManager entityManager;

    @Autowired
    public DALStore(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public String getPasword(int store_id) {
        Session session = entityManager.unwrap(Session.class);

        // Faraz qilaylik "Stores" jadvalida "password" degan ustun bor
        TypedQuery<String> query = session.createQuery(
            "SELECT s.password FROM Stores s WHERE s.storeId = :storeId", String.class);
        query.setParameter("storeId", store_id);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public Stores getStore(int store_id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Stores.class, store_id);
    }

	@Override
	@Transactional
	public void updatePassword(int store_id, String newPassword) {
		Session session = entityManager.unwrap(Session.class);
		try {
			Stores st = getStore(store_id);
			Stores store = new Stores();
			store.setStoreId(store_id);
			store.setAdress(st.getAdress());
			store.setName(st.getName());
			store.setPassword(newPassword);
            session.merge(store);
        } catch (NoResultException e) {
            System.out.print(e);
        }
	}
}
