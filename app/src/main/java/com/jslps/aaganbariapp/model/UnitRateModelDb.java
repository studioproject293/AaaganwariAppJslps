package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class UnitRateModelDb extends SugarRecord {
    private String item;
    private Double b1;
    private Double b2;
    private Double b3;
    private Double b4;

    public UnitRateModelDb() {
    }

    public UnitRateModelDb(String item, Double b1, Double b2, Double b3, Double b4) {
        this.item = item;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getB1() {
        return b1;
    }

    public void setB1(Double b1) {
        this.b1 = b1;
    }

    public Double getB2() {
        return b2;
    }

    public void setB2(Double b2) {
        this.b2 = b2;
    }

    public Double getB3() {
        return b3;
    }

    public void setB3(Double b3) {
        this.b3 = b3;
    }

    public Double getB4() {
        return b4;
    }

    public void setB4(Double b4) {
        this.b4 = b4;
    }
}
