package com.intellibps.bib.customer;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/20
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class ContactInfo
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String businessPhoneNo;
    @Persistent
    private String mobilePhoneNo;
    @Persistent
    private String businessEmail;
    @Persistent
    private String physicalAddress;
    @Persistent
    private String postalAddress;

    public String businessPhoneNo()
    {
        return businessPhoneNo;
    }

    public void businessPhoneNo(String businessPhoneNo)
    {
        this.businessPhoneNo = businessPhoneNo;
    }

    public String mobilePhoneNo()
    {
        return mobilePhoneNo;
    }

    public void mobilePhoneNo(String mobilePhoneNo)
    {
        this.mobilePhoneNo = mobilePhoneNo;
    }

    public String businessEmail()
    {
        return businessEmail;
    }

    public void businessEmail(String businessEmail)
    {
        this.businessEmail = businessEmail;
    }

    public String physicalAddress()
    {
        return physicalAddress;
    }

    public void physicalAddress(String physicalAddress)
    {
        this.physicalAddress = physicalAddress;
    }

    public String postalAddress()
    {
        return postalAddress;
    }

    public void postalAddress(String postalAddress)
    {
        this.postalAddress = postalAddress;
    }

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public void copyFrom(ContactInfo contactInfo)
    {

        if (contactInfo != null)
        {
        this.businessEmail = contactInfo.businessEmail;
        this.businessPhoneNo = contactInfo.businessPhoneNo;
        this.mobilePhoneNo = contactInfo.mobilePhoneNo;
        this.physicalAddress = contactInfo.physicalAddress;
            this.postalAddress = contactInfo.postalAddress;
        }

    }
}
