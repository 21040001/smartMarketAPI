package com.SmartMarket.HibernateDAL;

import com.SmartMarket.Entity.Stores;

public interface DALStoresInterface {
	String getPasword(int store_id);
	Stores getStore(int store_id);
	void updatePassword(int store_id, String newPassword);
}
