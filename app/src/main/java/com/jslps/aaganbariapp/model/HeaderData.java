package com.jslps.aaganbariapp.model;

public class HeaderData
{
    boolean isLogoRequired;
    String text;

    public HeaderData(boolean isLogoRequired, String text) {
        this.isLogoRequired = isLogoRequired;
        this.text = text;
    }

    public boolean isLogoRequired() {
        return isLogoRequired;
    }

    public String getText() {
        return text;
    }
}

