package com.intellibps.bib.security;

import com.google.appengine.api.datastore.Key;
import com.intellibps.bib.customer.ContactInfo;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.jdo.annotations.*;
import java.util.ArrayList;
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
    @Persistent
    private com.google.appengine.api.datastore.Key company;
    @NotPersistent
    private boolean isDirty = false;
    @NotPersistent
    private boolean isNew = false;
    @NotPersistent
    private boolean isDeleted = false;
    @NotPersistent
    public static final String DEFAULT_PASSWORD = "12345678";
    @NotPersistent
    private ArrayList<Role> fullRoles;

    public User()
    {
        roles = new HashSet<Key>();
        fullRoles = new ArrayList<Role>();
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

    public void roles(Set<com.google.appengine.api.datastore.Key> roles)
    {
        this.roles = roles;
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

    public Key company()
    {
        return company;
    }

    public void company(Key company)
    {
        this.company = company;
    }

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public void copyFrom(User user)
    {
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.email = user.email;

        if (!user.password.equals(User.DEFAULT_PASSWORD))
        {
            this.password = user.password;
            this.encryptPassword();
        }

        this.roles(user.roles());

    }

    public ArrayList<Role> fullRoles()
    {
        return fullRoles;
    }

    public void roles(ArrayList<Role> roles)
    {
        this.fullRoles = fullRoles;
    }

    public String encrypt(String password)
    {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        return encryptedPassword;
    }

    public void encryptPassword()
    {
        password(encrypt(password()));
    }

}

