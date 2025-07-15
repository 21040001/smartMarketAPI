package com.SmartMarket.exceptions;

public class CannotGetPassword extends RuntimeException {

	public CannotGetPassword(String id) {
		super("Kiritilgan ID = " + id + " bo'lgan Store topilmadi. Bu sababdan Store parolini o'qib bo'lmaydi. Kiritilgan Store idni kontrol qiling.");	}
}
