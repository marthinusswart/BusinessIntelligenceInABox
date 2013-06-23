package com.intellibps.bib.util;

import javax.servlet.http.Cookie;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/18
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class WebHelper
{
    public String getEmail(Cookie[] cookies)
    {
        String result = "";
        if (cookies != null)
        {
            for (int i=0;i<cookies.length;i++)
            {
                if (cookies[i].getName().equals("email"))
                {
                    result = cookies[i].getValue();
                    break;
                }
            }
        }
        return result;
    }

    public String getSessionId(Cookie[] cookies)
    {
        String result = "";
        if (cookies != null)
        {
            for (int i=0;i<cookies.length;i++)
            {
                if (cookies[i].getName().equals("sessionid"))
                {
                    result = cookies[i].getValue();
                    break;
                }
            }
        }
        return result;
    }

}
