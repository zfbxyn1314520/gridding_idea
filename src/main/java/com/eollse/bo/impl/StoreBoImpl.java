package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.StoreBo;
import com.eollse.dao.StoreDao;
import com.eollse.po.Store;

@Service
public class StoreBoImpl implements StoreBo{
	@Autowired
	private StoreDao storeDao;

	@Override
	public Map<String, Object> getAllStoreByAreaId(List<Integer> areaIds, Integer pageSize,Integer pageCurrent) {
		// 计算起始数值
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Store> stores = this.storeDao.getAllStoreByAreaId(areaIds,x, y);			
		int totalRow = this.storeDao.getAllStoresCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", stores);
		return map;
	}

	@Override
	public List<Store> findStoreByStoreName(String storeName,Integer areaId) {
		List<Store> store=storeDao.findStoreByStoreName(storeName,areaId);
		return store;
	}

	@Override
	public Integer saveStore(Store store) {
		return this.storeDao.saveStore(store);	
	}

	@Override
	public Integer deleteStoreByStoreId(List<Integer> storeIds) {
		// TODO Auto-generated method stub
		return this.storeDao.deleteStoreByStoreId(storeIds);
	}

	@Override
	public List<Store> getStoreById(Integer storeId) {
		// TODO Auto-generated method stub
		return this.storeDao.getStoreById(storeId);
	}

	@Override
	public Integer editStoreById(Store store) {
		// TODO Auto-generated method stub
		return this.storeDao.editStoreById(store);
	}

	@Override
	public Integer updateAuditStore(List<Integer> storeIds) {
		// TODO Auto-generated method stub
		return this.storeDao.updateAuditStore(storeIds);
	}

	@Override
	public List<Store> getOneStoreById(Store store) {
		// TODO Auto-generated method stub
		return this.storeDao.getOneStoreById(store);
	}

	@Override
	public Integer getAllStoresCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.storeDao.getAllStoresCount(areaIds);
	}

	@Override
	public List<String> getStoreNameById(List<Integer> storeIds) {
		// TODO Auto-generated method stub
		return this.storeDao.getStoreNameById(storeIds);
	}
	

}
