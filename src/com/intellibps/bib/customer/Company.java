package com.intellibps.bib.customer;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class Company
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String name;
    @Persistent
    private String companyRegistrationNo;
    @Persistent
    private String city;
    @Persistent
    private String country;
    @Persistent
    private ContactInfo contactInfo;

    public void name(String name)
    {
        this.name = name;
    }

    public String name()
    {
        return this.name;
    }

    public ContactInfo contactInfo()
    {
        return contactInfo;
    }

    public void contactInfo(ContactInfo contactInfo)
    {
        this.contactInfo = contactInfo;
    }
    public String city()
    {
        return city;
    }

    public void city(String city)
    {
        this.city = city;
    }

    public String country()
    {
        return country;
    }

    public void country(String country)
    {
        this.country = country;
    }

    public String companyRegistrationNo()
    {
        return companyRegistrationNo;
    }

    public void companyRegistrationNo(String companyRegistrationNo)
    {
        this.companyRegistrationNo = companyRegistrationNo;
    }

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }
}
