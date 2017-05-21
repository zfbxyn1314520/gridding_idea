package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Store;


public interface StoreBo {
	public Map<String, Object> getAllStoreByAreaId(List<Integer> areaIds, Integer pageSize, Integer pageCurrent);
	public List<Store> findStoreByStoreName(String storeName,Integer areaId);
	public Integer saveStore(Store store);
	public Integer deleteStoreByStoreId(List<Integer> storeIds);
	public List<Store> getStoreById(Integer storeId);
	public Integer editStoreById(Store store);
	public Integer updateAuditStore(List<Integer> storeIds);
	public List<Store> getOneStoreById(Store store);
	public Integer getAllStoresCount(List<Integer> areaIds);
	public List<String> getStoreNameById(List<Integer> storeIds);
}
