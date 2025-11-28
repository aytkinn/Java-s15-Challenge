package com.workintech.library.base;

import java.util.Date;

public abstract class MemberRecord extends Person {
    private int memberId;
    private String type;
    private Date dateOfMembership;
    private int noBooksIssued;
    private int maxBookLimit;
    private String address;
    private String phoneNo;

    public MemberRecord(String name, int memberId,String type,int maxBookLimit) {
        super(name);
        this.memberId=memberId;
        this.type=type;
        this.maxBookLimit=maxBookLimit;
        this.dateOfMembership = new Date();
        this.noBooksIssued=0;
    }

    public int getMemberId() {

        return memberId;
    }
    public int getNoBooksIssued() {

        return noBooksIssued;
    }
    public int getMaxBookLimit() {

        return maxBookLimit;
    }

    public void incBookIssued(){
        if (noBooksIssued < maxBookLimit)
            noBooksIssued++;
    }
    public void deckBookIssued(){
        if (noBooksIssued > 0 )
            noBooksIssued--;
    }

    @Override
    public void whoyouare() {
        System.out.println("I am a" + type + "member named" + getName());
    }
}
