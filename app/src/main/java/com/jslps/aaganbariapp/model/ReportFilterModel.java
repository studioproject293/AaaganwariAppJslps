package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class ReportFilterModel extends SugarRecord {
    private String vocode;
    private String aaganwaricode;
    private String voname;
    private String aaganwaricount;
    private String aaganwaricountApplied;
    private String aaganwariname;
    private String month;
    private String year;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAaganwariname() {
        return aaganwariname;
    }

    public void setAaganwariname(String aaganwariname) {
        this.aaganwariname = aaganwariname;
    }

    public String getAaganwaricountApplied() {
        return aaganwaricountApplied;
    }

    public void setAaganwaricountApplied(String aaganwaricountApplied) {
        this.aaganwaricountApplied = aaganwaricountApplied;
    }

    public String getVoname() {
        return voname;
    }

    public void setVoname(String voname) {
        this.voname = voname;
    }

    public String getAaganwaricount() {
        return aaganwaricount;
    }

    public void setAaganwaricount(String aaganwaricount) {
        this.aaganwaricount = aaganwaricount;
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
}
