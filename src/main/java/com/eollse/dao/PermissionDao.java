package com.eollse.dao;

import java.util.List;


import com.eollse.po.Menu;
import com.eollse.po.Permission;

public interface PermissionDao {

	public List<Permission> getMenuPer(Menu menu);

	public List<Permission> getAllPermission();


}
