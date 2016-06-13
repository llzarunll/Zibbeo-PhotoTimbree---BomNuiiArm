package com.zibbeo.phototrimbree.Database;

/**
 * Created by samchaw on 5/28/16 AD.
 */
public class Contact {

    private String id,firstName,lineOne,lineTwo,zipCode,state,country;
    private Boolean envelop;
    private int positionCountry;

    public Contact(){}

    public Contact(String sID,String sFirstName,String sLineOne,
                   String sLineTwo,String sZipCode,String sState,String sCountry,Boolean sEnvelop,int sPosition){
        this.id = sID;
        this.firstName = sFirstName;
        this.lineOne = sLineOne;
        this.lineTwo = sLineTwo;
        this.zipCode = sZipCode;
        this.state = sState;
        this.country = sCountry;
        this.envelop = sEnvelop;
        this.positionCountry = sPosition;
    }

    public Contact(String sFirstName,String sLineOne,
                   String sLineTwo,String sZipCode,String sState,String sCountry,Boolean sEnvelop,int sPosition){
        this.firstName = sFirstName;
        this.lineOne = sLineOne;
        this.lineTwo = sLineTwo;
        this.zipCode = sZipCode;
        this.state = sState;
        this.country = sCountry;
        this.envelop = sEnvelop;
        this.positionCountry = sPosition;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEnvelop(Boolean envelop) {
        this.envelop = envelop;
    }

    public void setPositionCountry(int positionCountry) {
        this.positionCountry = positionCountry;
    }

    public String getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Boolean getEnvelop() {
        return envelop;
    }

    public int getPositionCountry() {
        return positionCountry;
    }
}
