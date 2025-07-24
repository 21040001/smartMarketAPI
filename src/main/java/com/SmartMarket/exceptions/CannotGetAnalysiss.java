package com.SmartMarket.exceptions;

public class CannotGetAnalysiss extends RuntimeException{

	public CannotGetAnalysiss(String name ) { 
		super(name+" analizlerini olishda xato o'rtaga chiqdi!!");
	}

}
