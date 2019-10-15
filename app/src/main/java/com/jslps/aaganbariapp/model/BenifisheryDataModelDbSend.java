package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public  class BenifisheryDataModelDbSend extends SugarRecord {
    private String benfid;
    private String benfname;
    private String createdby;
    private String createdon;
    private String noofmeal;
    private String unitrateofmeal;
    private String noofbenf;
    private String amount;
    private String month;
    private String year;
    private String remarks;
    private String guid;
    private String panchyatcode;
    private String vocode;
    private String aaganwaricode;
    private String panchyatname;
    private String voname;
    private String aaganwariname;
    private String isuploadtoserver;
    private String chexkboxchickpea;
    private String checkboxrice;
    private String checkboxdal;
    private String checkboxpenauts;
    private String checkboxjaggery;
    private String checkboxpotato;

    public BenifisheryDataModelDbSend(String benfid, String benfname, String createdby, String createdon, String noofmeal, String unitrateofmeal, String noofbenf, String amount, String month, String year, String remarks, String guid, String panchyatcode, String vocode, String aaganwaricode, String panchyatname, String voname, String aaganwariname, String isuploadtoserver) {
        this.benfid = benfid;
        this.benfname = benfname;
        this.createdby = createdby;
        this.createdon = createdon;
        this.noofmeal = noofmeal;
        this.unitrateofmeal = unitrateofmeal;
        this.noofbenf = noofbenf;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.remarks = remarks;
        this.guid = guid;
        this.panchyatcode = panchyatcode;
        this.vocode = vocode;
        this.aaganwaricode = aaganwaricode;
        this.panchyatname = panchyatname;
        this.voname = voname;
        this.aaganwariname = aaganwariname;
        this.isuploadtoserver = isuploadtoserver;
    }

    public String getChexkboxchickpea() {
        return chexkboxchickpea;
    }

    public void setChexkboxchickpea(String chexkboxchickpea) {
        this.chexkboxchickpea = chexkboxchickpea;
    }

    public String getCheckboxrice() {
        return checkboxrice;
    }

    public void setCheckboxrice(String checkboxrice) {
        this.checkboxrice = checkboxrice;
    }

    public String getCheckboxdal() {
        return checkboxdal;
    }

    public void setCheckboxdal(String checkboxdal) {
        this.checkboxdal = checkboxdal;
    }

    public String getCheckboxpenauts() {
        return checkboxpenauts;
    }

    public void setCheckboxpenauts(String checkboxpenauts) {
        this.checkboxpenauts = checkboxpenauts;
    }

    public String getCheckboxjaggery() {
        return checkboxjaggery;
    }

    public void setCheckboxjaggery(String checkboxjaggery) {
        this.checkboxjaggery = checkboxjaggery;
    }

    public String getCheckboxpotato() {
        return checkboxpotato;
    }

    public void setCheckboxpotato(String checkboxpotato) {
        this.checkboxpotato = checkboxpotato;
    }

    public String getPanchyatname() {
        return panchyatname;
    }

    public void setPanchyatname(String panchyatname) {
        this.panchyatname = panchyatname;
    }

    public String getVoname() {
        return voname;
    }

    public void setVoname(String voname) {
        this.voname = voname;
    }

    public String getAaganwariname() {
        return aaganwariname;
    }

    public void setAaganwariname(String aaganwariname) {
        this.aaganwariname = aaganwariname;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPanchyatcode() {
        return panchyatcode;
    }

    public void setPanchyatcode(String panchyatcode) {
        this.panchyatcode = panchyatcode;
    }

    public String getVocode() {
        return vocode;
    }

    public void setVocode(String vocode) {
        this.vocode = vocode;
    }

    public String getAaganwaricode() {
        return aaganwaricode;
    }

    public void setAaganwaricode(String aaganwaricode) {
        this.aaganwaricode = aaganwaricode;
    }

    public void setBenfid(String benfid) {
        this.benfid = benfid;
    }

    public void setBenfname(String benfname) {
        this.benfname = benfname;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public void setNoofmeal(String noofmeal) {
        this.noofmeal = noofmeal;
    }

    public void setUnitrateofmeal(String unitrateofmeal) {
        this.unitrateofmeal = unitrateofmeal;
    }

    public void setNoofbenf(String noofbenf) {
        this.noofbenf = noofbenf;
    }

    public BenifisheryDataModelDbSend() {
    }

    public String getNoofbenf() {
        return noofbenf;
    }

    public String getBenfid() {
        return benfid;
    }

    public String getBenfname() {
        return benfname;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getCreatedon() {
        return createdon;
    }

    public String getNoofmeal() {
        return noofmeal;
    }

    public String getUnitrateofmeal() {
        return unitrateofmeal;
    }

    public String getIsuploadtoserver() {
        return isuploadtoserver;
    }

    public void setIsuploadtoserver(String isuploadtoserver) {
        this.isuploadtoserver = isuploadtoserver;
    }
}
