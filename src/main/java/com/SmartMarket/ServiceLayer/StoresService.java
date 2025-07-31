package com.SmartMarket.ServiceLayer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.SmartMarket.Entity.Stores;
import com.SmartMarket.HibernateDAL.DALStoresInterface;
import com.SmartMarket.dto.StoreDto;
import com.SmartMarket.dto.StoreIdDto;
import com.SmartMarket.dto.StoreUpdateDto;
import com.SmartMarket.exceptions.CannotGetPassword;
import com.SmartMarket.exceptions.StoreNotFoundException;

@Service
public class StoresService implements StoresServiceInterface {

	private final DALStoresInterface store;
	private final ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public StoresService(DALStoresInterface store, ModelMapper modelMapper) {
		super();
		this.store = store;
		this.modelMapper = modelMapper;
	}

	// 🔐 Kullanıcıdan storeId'yi token'dan al
	private int getCurrentStoreId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StoreIdDto principal = (StoreIdDto) auth.getPrincipal();
		return principal.getStoreId();
	}

	@Override
	@Transactional(readOnly = true)
	public String getPasword() {
		int storeId = getCurrentStoreId();
		return store.getPasword(storeId).orElseThrow(() -> new CannotGetPassword(String.valueOf(storeId)));
	}

	@Override
	@Transactional(readOnly = true)
	public StoreDto getStore() {
		int storeId = getCurrentStoreId();
		return store.findByStoreId(storeId).map(s -> modelMapper.map(s, StoreDto.class))
				.orElseThrow(() -> new StoreNotFoundException(String.valueOf(storeId)));
	}

	@Override
	@Transactional
	public void updatePassword(String newPassword) {
		int storeId = getCurrentStoreId();
		StoreDto dto = getStore();
		Stores s = modelMapper.map(dto, Stores.class);
		s.setPassword(passwordEncoder.encode(newPassword));
		store.save(s);
	}

	@Override
	@Transactional
	public void updateStore(StoreUpdateDto dto) {
		Stores storeEntity = store.findByStoreId(dto.getStoreId())
				.orElseThrow(() -> new StoreNotFoundException(String.valueOf(dto.getStoreId())));
		modelMapper.map(dto, storeEntity);
		if (StringUtils.hasText(dto.getPassword())) {
			storeEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
	}

}
