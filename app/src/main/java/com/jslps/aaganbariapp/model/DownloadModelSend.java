package com.jslps.aaganbariapp.model;

import java.util.List;

public class DownloadModelSend {

    private List<BenifisheryDataModelDbSend> Master = null;
    private List<ImageSaveModel> Table1 = null;

    public List<BenifisheryDataModelDbSend> getMaster() {
        return Master;
    }

    public void setMaster(List<BenifisheryDataModelDbSend> master) {
        Master = master;
    }

    public List<ImageSaveModel> getTable1() {
        return Table1;
    }

    public void setTable1(List<ImageSaveModel> table1) {
        Table1 = table1;
    }
}
