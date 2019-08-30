package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class PanchyatDataModelDb extends SugarRecord {
    private String blockcode;
    private String clustercode;
    private String clustername;
    private String clusternameh;
    private String createdby;
    private String createdon;
    private String districtcode;
    private String statecode;

    public PanchyatDataModelDb(String blockcode, String clustercode, String clustername, String clusternameh, String createdby, String createdon, String districtcode, String statecode) {
        this.blockcode = blockcode;
        this.clustercode = clustercode;
        this.clustername = clustername;
        this.clusternameh = clusternameh;
        this.createdby = createdby;
        this.createdon = createdon;
        this.districtcode = districtcode;
        this.statecode = statecode;
    }

    public PanchyatDataModelDb() {
    }

    public String getBlockcode() {
        return blockcode;
    }

    public String getClustercode() {
        return clustercode;
    }

    public String getClustername() {
        return clustername;
    }

    public String getClusternameh() {
        return clusternameh;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getCreatedon() {
        return createdon;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public String getStatecode() {
        return statecode;
    }
    @Override
    public String toString() {
        return clustername;
    }
}
