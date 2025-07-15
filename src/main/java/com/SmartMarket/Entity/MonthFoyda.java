package com.SmartMarket.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "month_foyda")
public class MonthFoyda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @NotNull
    @Column(name = "storeId")
    private int storeId;

    @NotNull
    @Column(name = "date", updatable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "monthFoyda")
    private long monthFoyda;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getMonthFoyda() {
		return monthFoyda;
	}

	public void setMonthFoyda(long monthFoyda) {
		this.monthFoyda = monthFoyda;
	}

    
    
}
