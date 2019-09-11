package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class VOListDataModelDb extends SugarRecord {
    private String block;
    private String blockcode;
    private String createdby;
    private String createdon;
    private String district;
    private String districtcode;
    private String flag;
    private String voidd;
    private String panchayat;
    private String panchayatcode;
    private String panchayat_vo;
    private String shgcode;
    private String village;
    private String villagecode;
    private String vocode;
    private String voshg;
    private String voshghindi;

    public VOListDataModelDb(String block, String blockcode, String createdby, String createdon, String district, String districtcode, String flag, String voidd, String panchayat, String panchayatcode, String panchayat_vo, String shgcode, String village, String villagecode, String vocode, String voshg, String voshghindi) {
        this.block = block;
        this.blockcode = blockcode;
        this.createdby = createdby;
        this.createdon = createdon;
        this.district = district;
        this.districtcode = districtcode;
        this.flag = flag;
        this.voidd = voidd;
        this.panchayat = panchayat;
        this.panchayatcode = panchayatcode;
        this.panchayat_vo = panchayat_vo;
        this.shgcode = shgcode;
        this.village = village;
        this.villagecode = villagecode;
        this.vocode = vocode;
        this.voshg = voshg;
        this.voshghindi = voshghindi;
    }

    public VOListDataModelDb() {
    }

    public String getVoshghindi() {
        return voshghindi;
    }

    public void setVoshghindi(String voshghindi) {
        this.voshghindi = voshghindi;
    }

    public VOListDataModelDb(String voshg) {
        this.voshg=voshg;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlockcode() {
        return blockcode;
    }

    public void setBlockcode(String blockcode) {
        this.blockcode = blockcode;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getVoidd() {
        return voidd;
    }

    public void setVoidd(String voidd) {
        this.voidd = voidd;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getPanchayatcode() {
        return panchayatcode;
    }

    public void setPanchayatcode(String panchayatcode) {
        this.panchayatcode = panchayatcode;
    }

    public String getPanchayat_vo() {
        return panchayat_vo;
    }

    public void setPanchayat_vo(String panchayat_vo) {
        this.panchayat_vo = panchayat_vo;
    }

    public String getShgcode() {
        return shgcode;
    }

    public void setShgcode(String shgcode) {
        this.shgcode = shgcode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getVillagecode() {
        return villagecode;
    }

    public void setVillagecode(String villagecode) {
        this.villagecode = villagecode;
    }

    public String getVocode() {
        return vocode;
    }

    public void setVocode(String vocode) {
        this.vocode = vocode;
    }

    public String getVoshg() {
        return voshg;
    }

    public void setVoshg(String voshg) {
        this.voshg = voshg;
    }

    @Override
    public String toString() {
        return voshg;
    }
}
