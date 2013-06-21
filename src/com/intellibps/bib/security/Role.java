package com.intellibps.bib.security;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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

    public com.google.appengine.api.datastore.Key Id()
    {
        return id;
    }

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

}
