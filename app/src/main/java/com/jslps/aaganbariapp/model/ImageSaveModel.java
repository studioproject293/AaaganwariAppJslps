package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class ImageSaveModel extends SugarRecord {
    private String imagebyte;
    private String imagename;
    private Long finalsizes;
    private String finaltypes;
    private String guid;
    private String panchyatcode;
    private String vocode;
    private String awccode;
    private String month;
    private String year;
    private String isuploadtoserver;
    private String createdby;

    public String getIsuploadtoserver() {
        return isuploadtoserver;
    }

    public void setIsuploadtoserver(String isuploadtoserver) {
        this.isuploadtoserver = isuploadtoserver;
    }

    public ImageSaveModel() {
    }

    public ImageSaveModel(String imagebyte, String imagename, Long finalsizes, String finaltypes, String guid, String panchyatcode, String vocode, String awccode, String month, String year, String isuploadtoserver, String createdby) {
        this.imagebyte = imagebyte;
        this.imagename = imagename;
        this.finalsizes = finalsizes;
        this.finaltypes = finaltypes;
        this.guid = guid;
        this.panchyatcode = panchyatcode;
        this.vocode = vocode;
        this.awccode = awccode;
        this.month = month;
        this.year = year;
        this.isuploadtoserver = isuploadtoserver;
        this.createdby = createdby;
    }

    public String getImagebyte() {
        return imagebyte;
    }

    public void setImagebyte(String imagebyte) {
        this.imagebyte = imagebyte;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public Long getFinalsizes() {
        return finalsizes;
    }

    public void setFinalsizes(Long finalsizes) {
        this.finalsizes = finalsizes;
    }

    public String getFinaltypes() {
        return finaltypes;
    }

    public void setFinaltypes(String finaltypes) {
        this.finaltypes = finaltypes;
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

    public String getAwccode() {
        return awccode;
    }

    public void setAwccode(String awccode) {
        this.awccode = awccode;
    }

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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
}
