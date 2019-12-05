package com.jslps.aaganbariapp.model;

public class SyncWithServerRespone {
    private String status;
    private String duplicate;
    private String imageError;
    private String NotVO;

    public String getNotVO() {
        return NotVO;
    }

    public void setNotVO(String notVO) {
        NotVO = notVO;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }
}
