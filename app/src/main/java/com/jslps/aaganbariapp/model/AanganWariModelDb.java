package com.jslps.aaganbariapp.model;

import com.orm.SugarRecord;

public class AanganWariModelDb extends SugarRecord {
    private String anganwadicode;
    private String anganwadiname;
    private String contactno;
    private String createdby;
    private String createdon;
    private String type;
    private String vocode;
    private String awid;

    public String getAwid() {
        return awid;
    }

    public AanganWariModelDb() {
    }

    public AanganWariModelDb(String anganwadicode, String anganwadiname, String contactno, String createdby, String createdon, String type, String vocode, String awid) {
        this.anganwadicode = anganwadicode;
        this.anganwadiname = anganwadiname;
        this.contactno = contactno;
        this.createdby = createdby;
        this.createdon = createdon;
        this.type = type;
        this.vocode = vocode;
        this.awid = awid;
    }

    public String getAnganwadicode() {
        return anganwadicode;
    }

    public String getAnganwadiname() {
        return anganwadiname;
    }

    public String getContactno() {
        return contactno;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getCreatedon() {
        return createdon;
    }

    public String getType() {
        return type;
    }

    public String getVocode() {
        return vocode;
    }
}
