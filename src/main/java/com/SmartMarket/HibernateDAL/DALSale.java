// Paket nomi
package com.SmartMarket.HibernateDAL;

// Kerakli kutubxonalarni import qilish
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.SmartMarket.Entity.Sales;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

// @Repository - bu klass ma'lumotlar bazasiga ishlovchi qatlam ekanligini bildiradi
@Repository
public class DALSale implements DALSalesInterface {

    // EntityManager orqali Hibernate sessiyalarini boshqaramiz
    private final EntityManager entityManager;

    // Konstruktor - Spring EntityManager'ni avtomatik joylashtiradi (injection)
    @Autowired
    public DALSale(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Yangi savdo (sales) ma'lumotini bazaga qo'shish
    @Override
    @Transactional  // Ushbu metod tranzaksion bo'lishi kerak
    public void addSales(Sales sales) {
        // Hibernate sessiyasini olish
        Session session = entityManager.unwrap(Session.class);
        try {
            // Ma'lumotni bazaga saqlash (persist qilish)
            session.persist(sales);
        } catch (NoResultException e) {
            // Agar hech qanday natija topilmasa, konsolga chiqarish
            System.out.print(e);
        }
    }

    // Bugungi kun savdolarini olish (storeId va sanaga qarab)
    @Override
    @Transactional
    public List<Sales> getTodaySales(int storeId, LocalDate todayDate, LocalDate tommorowDate) {
        // Hibernate sessiyasini olish
        Session session = entityManager.unwrap(Session.class);

        // SQL so‘rovi: ma'lum bir do'konga va kunga tegishli savdolarni olish
        String sql = "SELECT * FROM Sales WHERE store_id = " + storeId +
                     " AND sale_date >= '" + todayDate + "' AND sale_date < '" + tommorowDate + "'";

        // So'rovni bajarish va natijani olish
        List<Sales> sales = session.createNativeQuery(sql, Sales.class)
                                   .getResultList();

        return sales;
    }
}
