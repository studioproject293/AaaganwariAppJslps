package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class ImageSaveModel extends SugarRecord {
    private String imgebytes;
    private String finalnames;
    private Long finalsizes;
    private String finaltypes;
    private String guid;
    private String panchyatcode;
    private String vocode;
    private String aaganwaricode;
    private String month;
    private String year;

    public ImageSaveModel() {
    }

    public ImageSaveModel(String imgebytes, String finalnames, Long finalsizes, String finaltypes, String guid, String panchyatcode, String vocode, String aaganwaricode) {
        this.imgebytes = imgebytes;
        this.finalnames = finalnames;
        this.finalsizes = finalsizes;
        this.finaltypes = finaltypes;
        this.guid = guid;
        this.panchyatcode = panchyatcode;
        this.vocode = vocode;
        this.aaganwaricode = aaganwaricode;
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

    public String getImgebytes() {
        return imgebytes;
    }

    public void setImgebytes(String imgebytes) {
        this.imgebytes = imgebytes;
    }

    public String getFinalnames() {
        return finalnames;
    }

    public void setFinalnames(String finalnames) {
        this.finalnames = finalnames;
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

    public String getAaganwaricode() {
        return aaganwaricode;
    }

    public void setAaganwaricode(String aaganwaricode) {
        this.aaganwaricode = aaganwaricode;
    }
}
