package com.anvesh.saranamayyappa.model;


import com.anvesh.saranamayyappa.utils.util;


import java.io.Serializable;

public class Contact implements Serializable {

    private static final long serialVersionUID = -4879820593251782763L;

    private int color;
    private String name;
    private String numberWithCountryCode;
    private String number;
    public boolean isSelected;
    public boolean isAlreadySelected;
    public boolean newContact;
    private String contactImagePath;

    public Contact() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public int getcolor() {
        return color;
    }

    public String getNumberWithCountryCode() {
        return numberWithCountryCode;
    }

    public void setNumberWithCountryCode(String numberWithCountryCode) {
        this.numberWithCountryCode = numberWithCountryCode;
    }

    public String getName() {
        return name;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContactImagePath() {
        return contactImagePath;
    }

    public void setContactImagePath(String contactImagePath) {
        this.contactImagePath = contactImagePath;
    }

    public Contact(String name, String number, String contactImagePath) {
        this.name = name;
        this.number = number;
        this.contactImagePath = contactImagePath;

        this.color = util.getRandomColor(name);

    }
}
