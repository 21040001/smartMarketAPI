package com.SmartMarket.ServiceLayer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SmartMarket.Entity.MonthFoyda;
import com.SmartMarket.HibernateDAL.MonthInterface;
import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.exceptions.MotnhFoydaNotFound;

@Service
public class MonthService implements MonthServiceInterfcae {

	private final MonthInterface month;

	public MonthService(MonthInterface month) {
		super();
		this.month = month;
	}

	private int getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getStoreId();
	}

	@Override
	@Transactional
	public void updateMonthFoyda(LocalDate date, long eski, long yeni) {
		int storeId = getCurrentStoreId();
		month.updateMonthFoyda(storeId, date, eski + yeni);
	}

	@Override
	@Transactional
	public List<MonthFoyda> getMonthFoyda(LocalDate date) {
		int storeId = getCurrentStoreId();
		return month.findByStoreIdAndDateMonthYear(storeId, date);
	}

	@Override
	@Transactional
	public long foydaFindByStoreIdAndDateMonthYear(LocalDate date) {
		int storeId = getCurrentStoreId();
        return month.foydaFindByStoreIdAndDateMonthYear(storeId, date)
                .orElseThrow(() -> new MotnhFoydaNotFound(date));
	}

}
