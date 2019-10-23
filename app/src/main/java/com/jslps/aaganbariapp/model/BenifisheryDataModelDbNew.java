package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public  class BenifisheryDataModelDbNew extends SugarRecord {
    private String benfid;
    private String benfname;
    private String createdby;
    private String createdon;
    private String rice;
    private String jaggery;
    private String potato;
    private String arhardal;
    private String chana;
    private String penauts;
    private String noofbenf;

    public String getNoofbenf() {
        return noofbenf;
    }

    public void setNoofbenf(String noofbenf) {
        this.noofbenf = noofbenf;
    }

    public BenifisheryDataModelDbNew(String benfid, String benfname, String createdby, String createdon, String noofbenf) {
        this.benfid = benfid;
        this.benfname = benfname;
        this.createdby = createdby;
        this.createdon = createdon;
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

    public String getRice() {
        return rice;
    }

    public void setRice(String rice) {
        this.rice = rice;
    }

    public String getJaggery() {
        return jaggery;
    }

    public void setJaggery(String jaggery) {
        this.jaggery = jaggery;
    }

    public String getPotato() {
        return potato;
    }

    public void setPotato(String potato) {
        this.potato = potato;
    }

    public String getArhardal() {
        return arhardal;
    }

    public void setArhardal(String arhardal) {
        this.arhardal = arhardal;
    }

    public String getChana() {
        return chana;
    }

    public void setChana(String chana) {
        this.chana = chana;
    }

    public String getPenauts() {
        return penauts;
    }

    public void setPenauts(String penauts) {
        this.penauts = penauts;
    }

    public BenifisheryDataModelDbNew() { }
}
