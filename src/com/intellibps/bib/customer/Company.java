package com.intellibps.bib.customer;

import com.google.appengine.api.datastore.Key;
import com.intellibps.bib.report.Report;

import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    @Persistent(defaultFetchGroup="true")
    private ContactInfo contactInfo = new ContactInfo();
    @Persistent(defaultFetchGroup="true")
    private Set<Key> reports;
    @Persistent
    private String companyCode;
    @NotPersistent
    private boolean isNew = false;
    @NotPersistent
    private boolean isDirty = false;
    @NotPersistent
    private boolean isDeleted = false;
    @NotPersistent
    private ArrayList<Report> fullReports;

    public Company()
    {
        reports(new HashSet<Key>());
        fullReports(new ArrayList<Report>());
    }

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

    public boolean isNew()
    {
        return isNew;
    }

    public void isNew(boolean aNew)
    {
        isNew = aNew;
    }

    public boolean isDirty()
    {
        return isDirty;
    }

    public void isDirty(boolean dirty)
    {
        isDirty = dirty;
    }

    public void copyFrom(Company company)
    {
        this.city = company.city;
        this.companyRegistrationNo = company.companyRegistrationNo;
        if (contactInfo == null)
        {
            contactInfo = new ContactInfo();
        }
        this.contactInfo.copyFrom(company.contactInfo);
        this.country = company.country;
        this.name = company.name;
        this.companyCode = company.companyCode;
    }

    public boolean isDeleted()
    {
        return isDeleted;
    }

    public void isDeleted(boolean deleted)
    {
        isDeleted = deleted;
    }

    public Set<Key> reports()
    {
        return reports;
    }

    public void reports(Set<Key> reports)
    {
        this.reports = reports;
    }

    public ArrayList<Report> fullReports()
    {
        return fullReports;
    }

    public void fullReports(ArrayList<Report> fullReports)
    {
        this.fullReports = fullReports;
    }

    public String companyCode()
    {
        return companyCode;
    }

    public void companyCode(String companyCode)
    {
        this.companyCode = companyCode;
    }
}
