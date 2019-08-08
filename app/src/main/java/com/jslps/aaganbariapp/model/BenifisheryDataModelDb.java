package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public  class BenifisheryDataModelDb extends SugarRecord {
    private String benfid;
    private String benfname;
    private String createdby;
    private String createdon;
    private String noofmeal;
    private String unitrateofmeal;
    private String noofbenf;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BenifisheryDataModelDb(String benfid, String benfname, String createdby, String createdon, String noofmeal, String unitrateofmeal, String noofbenf) {
        this.benfid = benfid;
        this.benfname = benfname;
        this.createdby = createdby;
        this.createdon = createdon;
        this.noofmeal = noofmeal;
        this.unitrateofmeal = unitrateofmeal;
        this.noofbenf = noofbenf;
    }

    public String getBenfid() {
        return benfid;
    }

    public void setBenfid(String benfid) {
        this.benfid = benfid;
    }

    public String getBenfname() {
        return benfname;
    }

    public void setBenfname(String benfname) {
        this.benfname = benfname;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getNoofmeal() {
        return noofmeal;
    }

    public void setNoofmeal(String noofmeal) {
        this.noofmeal = noofmeal;
    }

    public String getUnitrateofmeal() {
        return unitrateofmeal;
    }

    public void setUnitrateofmeal(String unitrateofmeal) {
        this.unitrateofmeal = unitrateofmeal;
    }

    public String getNoofbenf() {
        return noofbenf;
    }

    public void setNoofbenf(String noofbenf) {
        this.noofbenf = noofbenf;
    }

    public BenifisheryDataModelDb() {
    }
}
