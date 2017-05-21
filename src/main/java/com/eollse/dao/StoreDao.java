package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Store;

public interface StoreDao {

	public List<Store> getAllStoreByAreaId(@Param("list") List<Integer> areaId,
			@Param("x") Integer x, @Param("y") Integer y);

	public Integer getAllStoresCount(List<Integer> areaIds);

	public List<Store> findStoreByStoreName(String storeName,
			@Param("areaId") Integer areaId);

	public Integer saveStore(Store store);

	public Integer updateStore(Store store);

	public Integer deleteStoreByStoreId(List<Integer> storeIds);

	public List<Store> getStoreById(@Param("storeId") Integer storeId);

	public Integer editStoreById(Store store);

	public Integer updateAuditStore(List<Integer> storeIds);

	public List<Store> getOneStoreById(Store store);

	public List<String> getStoreNameById(List<Integer> storeIds);
}
