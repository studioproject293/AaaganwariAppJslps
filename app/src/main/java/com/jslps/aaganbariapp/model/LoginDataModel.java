package com.jslps.aaganbariapp.model;

import java.util.List;

public class LoginDataModel {
    private List<LoginModel> Master = null;
    private List<AanganWariModel> Table1 = null;
    private List<BenifisheryDataModel> Table2 = null;
   /* private List<BenifisheryDataModel> Table3 = null;
    private List<BenifisheryDataModel> Table4 = null;*/
    private List<VOListDataModel> Table5 = null;
    private List<PanchyatDataModel> Table6 = null;
    private List<UnitRateModel> Table7 = null;

    public List<UnitRateModel> getTable7() {
        return Table7;
    }

    public void setTable7(List<UnitRateModel> table7) {
        Table7 = table7;
    }

    public List<LoginModel> getMaster() {
        return Master;
    }

    public void setMaster(List<LoginModel> master) {
        Master = master;
    }

    public List<AanganWariModel> getTable1() {
        return Table1;
    }

    public void setTable1(List<AanganWariModel> table1) {
        Table1 = table1;
    }

    public List<BenifisheryDataModel> getTable2() {
        return Table2;
    }

    public void setTable2(List<BenifisheryDataModel> table2) {
        Table2 = table2;
    }

    public List<VOListDataModel> getTable5() {
        return Table5;
    }

    public void setTable5(List<VOListDataModel> table5) {
        Table5 = table5;
    }

    public List<PanchyatDataModel> getTable6() {
        return Table6;
    }

    public void setTable6(List<PanchyatDataModel> table6) {
        Table6 = table6;
    }
}
