package com.eollse.bo;

import java.util.List;

import com.eollse.po.Menu;
import com.eollse.po.Permission;

public interface PermissionBo {

	public List<Permission> getMenuPer(Menu menu);

	public List<Permission> getAllPermission();


}
