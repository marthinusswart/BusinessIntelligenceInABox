package com.intellibps.bib.security;

import javax.jdo.annotations.Key;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/19
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class Credentials implements Serializable
{

    private com.google.appengine.api.datastore.Key companyId;
    private com.google.appengine.api.datastore.Key userId;
    private String username = null;
    private String email = null;
    private Date loginTime = null;



    public void username(String username)
    {
        this.username = username;
    }

    public String username()
    {
        return this.username;
    }

    public void email(String email)
    {
        this.email = email;
    }

    public String email()
    {
        return this.email;
    }

    public void loginTime(Date time)
    {
        this.loginTime = time;
    }

    public Date loginTime()
    {
        return this.loginTime;
    }

    public com.google.appengine.api.datastore.Key companyId()
    {
        return companyId;
    }

    public void companyId(com.google.appengine.api.datastore.Key companyId)
    {
        this.companyId = companyId;
    }

    public com.google.appengine.api.datastore.Key userId()
    {
        return userId;
    }

    public void userId(com.google.appengine.api.datastore.Key userId)
    {
        this.userId = userId;
    }
}

