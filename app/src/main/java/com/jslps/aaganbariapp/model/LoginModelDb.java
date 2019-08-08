package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class LoginModelDb extends SugarRecord {
    private String blockcode;
    private String contactno;
    private String createdby;
    private String createdon;
    private String designation;
    private String districtcode;
    private String loginid;
    private String name;
    private String panchayatcode;
    private String password;
    private String roleid;
    private String username;
    private String villagecode;

    public LoginModelDb() {
    }

    public LoginModelDb(String blockcode, String contactno, String createdby, String createdon, String designation, String districtcode, String loginid, String name, String panchayatcode, String password, String roleid, String username, String villagecode) {
        this.blockcode = blockcode;
        this.contactno = contactno;
        this.createdby = createdby;
        this.createdon = createdon;
        this.designation = designation;
        this.districtcode = districtcode;
        this.loginid = loginid;
        this.name = name;
        this.panchayatcode = panchayatcode;
        this.password = password;
        this.roleid = roleid;
        this.username = username;
        this.villagecode = villagecode;
    }

    public String getBlockcode() {
        return blockcode;
    }

    public String getContactno() {
        return contactno;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getCreatedon() {
        return createdon;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public String getLoginid() {
        return loginid;
    }

    public String getName() {
        return name;
    }

    public String getPanchayatcode() {
        return panchayatcode;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleid() {
        return roleid;
    }

    public String getUsername() {
        return username;
    }

    public String getVillagecode() {
        return villagecode;
    }
}
