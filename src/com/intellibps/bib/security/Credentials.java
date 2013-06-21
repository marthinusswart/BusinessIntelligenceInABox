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

    private Key key;
    private String username = null;
    private String email = null;
    private Date loginTime = null;

    public void key(Key key)
    {
        this.key = key;
    }

    public Key key()
    {
        return this.key;
    }

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
}

