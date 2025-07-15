package com.SmartMarket.exceptions;

import java.time.LocalDate;

@SuppressWarnings("serial")
public class MotnhFoydaNotFound extends RuntimeException {

	public MotnhFoydaNotFound(LocalDate date ) {
		super(date +" Bu oyga tegishli bir savdoingiz ma'lumotlar bazasida mavjud emas");
	}
}
