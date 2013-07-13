package com.intellibps.bib.report;

import com.google.appengine.api.datastore.Key;
import com.intellibps.bib.security.Role;

import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/13
 * Time: 07:54
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class Report
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @Persistent
    private Key type;
    @Persistent
    private String reportUrl;
    @Persistent
    private Set<com.google.appengine.api.datastore.Key> roles;
    @NotPersistent
    private boolean isDirty = false;
    @NotPersistent
    private boolean isNew = false;
    @NotPersistent
    private boolean isDeleted = false;
    @NotPersistent
    private ArrayList<Role> fullRoles;

    public Report()
    {
        roles = new HashSet<Key>();
        fullRoles(new ArrayList<Role>());
    }

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public String name()
    {
        return name;
    }

    public void name(String name)
    {
        this.name = name;
    }

    public String description()
    {
        return description;
    }

    public void description(String description)
    {
        this.description = description;
    }

    public Key type()
    {
        return type;
    }

    public void type(Key type)
    {
        this.type = type;
    }

    public boolean isDirty()
    {
        return isDirty;
    }

    public void isDirty(boolean dirty)
    {
        isDirty = dirty;
    }

    public boolean isNew()
    {
        return isNew;
    }

    public void isNew(boolean aNew)
    {
        isNew = aNew;
    }

    public boolean isDeleted()
    {
        return isDeleted;
    }

    public void isDeleted(boolean deleted)
    {
        isDeleted = deleted;
    }

    public String reportUrl()
    {
        return reportUrl;
    }

    public void reportUrl(String reportUrl)
    {
        this.reportUrl = reportUrl;
    }

    public void copyFrom(Report report)
    {
        this.name = report.name;
        this.description = report.description;
        this.reportUrl = report.reportUrl;
        this.type = report.type;


    }

    public Set<Key> roles()
    {
        return roles;
    }

    public void roles(Set<Key> roles)
    {
        this.roles = roles;
    }

    public ArrayList<Role> fullRoles()
    {
        return fullRoles;
    }

    public void fullRoles(ArrayList<Role> fullRoles)
    {
        this.fullRoles = fullRoles;
    }
}
