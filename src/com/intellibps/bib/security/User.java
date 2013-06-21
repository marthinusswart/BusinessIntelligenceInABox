package com.intellibps.bib.security;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class User
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String firstname;
    @Persistent
    private String lastname;
    @Persistent
    private String email;
    @Persistent
    private String password;
    @Persistent
    private Set<com.google.appengine.api.datastore.Key> roles;

    public User()
    {
        roles = new HashSet<Key>();
    }

    public String firstname()
    {
        return this.firstname;
    }

    public String lastname()
    {
        return this.lastname;
    }

    public String email()
    {
        return this.email;
    }

    public String password()
    {
        return this.password;
    }

    public void firstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void lastname(String lastname)
    {
        this.lastname = lastname;
    }

    public void email(String email)
    {
        this.email = email;
    }

    public void password(String password)
    {
        this.password = password;
    }

    public Set<com.google.appengine.api.datastore.Key> roles()
    {
        return roles;
    }


}

