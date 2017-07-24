package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer userId;
    private Integer areaId;
    private Integer roleId;
    private String userName;
    private String realName;
    private String password;
    private String headIcon;
    private String mobileTel;
    private String officeTel;
    private String editUserName;
    private Date editUserDate;
    private Integer user_enable;
    private String user_login_ip;
    private Date user_last_login;
    private Role role;
    private Area area;

    public User() {
        super();
        this.role = new Role();
        this.area = new Area();
    }


    public User(Integer userId, Integer areaId, Integer roleId, String userName, String realName, String password, String headIcon, String mobileTel, String officeTel, String editUserName, Date editUserDate, Integer user_enable, String user_login_ip, Date user_last_login, Role role, Area area) {
        this.userId = userId;
        this.areaId = areaId;
        this.roleId = roleId;
        this.userName = userName;
        this.realName = realName;
        this.password = password;
        this.headIcon = headIcon;
        this.mobileTel = mobileTel;
        this.officeTel = officeTel;
        this.editUserName = editUserName;
        this.editUserDate = editUserDate;
        this.user_enable = user_enable;
        this.user_login_ip = user_login_ip;
        this.user_last_login = user_last_login;
        this.role = role;
        this.area = area;
    }

    public User(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", areaId=" + areaId +
                ", roleId=" + roleId +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", mobileTel='" + mobileTel + '\'' +
                ", officeTel='" + officeTel + '\'' +
                ", editUserName='" + editUserName + '\'' +
                ", editUserDate=" + editUserDate +
                ", user_enable=" + user_enable +
                ", user_login_ip='" + user_login_ip + '\'' +
                ", user_last_login=" + user_last_login +
                ", role=" + role +
                ", area=" + area +
                '}';
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String paasword) {
        this.password = paasword;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getEditUserName() {
        return editUserName;
    }

    public void s_user(String editUserName) {
        this.editUserName = editUserName;
    }

    public Date getEditUserDate() {
        return editUserDate;
    }

    public void setEditUserDate(Date editUserDate) {
        this.editUserDate = editUserDate;
    }

    public Integer getUser_enable() {
        return user_enable;
    }

    public void setUser_enable(Integer user_enable) {
        this.user_enable = user_enable;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }

    public String getUser_login_ip() {
        return user_login_ip;
    }

    public void setUser_login_ip(String user_login_ip) {
        this.user_login_ip = user_login_ip;
    }

    public Date getUser_last_login() {
        return user_last_login;
    }

    public void setUser_last_login(Date user_last_login) {
        this.user_last_login = user_last_login;
    }

}
