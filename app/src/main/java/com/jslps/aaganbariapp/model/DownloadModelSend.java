package com.jslps.aaganbariapp.model;

import java.util.List;

public class DownloadModelSend {

    private List<BenifisheryDataModelDbSendNew> Master = null;
    private List<ImageSaveModel> Table1 = null;

    public List<BenifisheryDataModelDbSendNew> getMaster() {
        return Master;
    }

    public void setMaster(List<BenifisheryDataModelDbSendNew> master) {
        Master = master;
    }

    public List<ImageSaveModel> getTable1() {
        return Table1;
    }

    public void setTable1(List<ImageSaveModel> table1) {
        Table1 = table1;
    }
}
