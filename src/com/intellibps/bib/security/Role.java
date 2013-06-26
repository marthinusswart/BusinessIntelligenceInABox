package com.intellibps.bib.security;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class Role
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @NotPersistent
    private boolean isDirty;
    @NotPersistent
    private boolean isNew;
    @NotPersistent
    private boolean isDeleted;

    public void name(String name)
    {
        this.name = name;
    }

    public String name()
    {
        return this.name;
    }

    public void description(String description)
    {
        this.description = description;
    }

    public String description()
    {
        return description;
    }

    public boolean isDirty()
    {
        return isDirty;
    }

    public void isDirty(boolean isDirty)
    {
        this.isDirty = isDirty;
    }

    public boolean isNew()
    {
        return isNew;
    }

    public void isNew(boolean isNew)
    {
        this.isNew = isNew;
    }

    public boolean isDeleted()
    {
        return isDeleted;
    }

    public void isDeleted(boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public com.google.appengine.api.datastore.Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public void copyFrom(Role role)
    {
        this.name = role.name;
        this.description = role.description;
    }
}
