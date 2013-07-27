package com.intellibps.bib.security;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class PermissionManager
{
    public boolean canUploadData(Credentials credentials)
    {
        return true;
    }

    public boolean canViewReports(Credentials credentials)
    {
        return true;
    }

}
