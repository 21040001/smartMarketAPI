package com.SmartMarket.serviceLayer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.entity.Sales;
import com.SmartMarket.exceptions.SaleNotFoundException;
import com.SmartMarket.hibernateDAL.DALSalesInterface;
import com.SmartMarket.telegramBot.TelegramBot;

@Service
public class SalesService implements SalesServiceInterface {

	private final DALSalesInterface sale;
	@Autowired
	private TelegramBot bot;

	public SalesService(DALSalesInterface sale) {
		super();
		this.sale = sale;
	}

	// 🔐 Kullanıcıdan storeId'yi token'dan al
	private int getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getStoreId();
	}

	// 🔐 Kullanıcıdan storeId'yi token'dan al
	private String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getUsername();
	}

	@Override
	@Transactional
	public void addSale(Sales s) {
		sale.save(s);
		bot.sendMessage(getCurrentStoreId(), "💰🛒 [" + getCurrentUserName() + "] yangi savdo amalga oshirdi: "
				+ "Savdo miqdori: " + s.getTotalPrice() + ",000 UZS ✅");
	}

	@Override
	@Transactional(readOnly = true)
	public Sales getSale(int id) {
		return sale.getSale(id).orElseThrow(() -> new SaleNotFoundException(String.valueOf(id)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sales> getAllSale() {
		int storeId = getCurrentStoreId();
		return sale.getAllSale(storeId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sales> getTodayAllSales(LocalDate date) {
		int storeId = getCurrentStoreId();
		return sale.getTodayAllSales(storeId, date, date.plusDays(1));
	}

}
