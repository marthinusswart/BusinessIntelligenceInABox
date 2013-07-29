package com.intellibps.bib.security;

import com.intellibps.bib.report.Report;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
public class PermissionManager
{
    private java.util.logging.Logger logger = Logger.getLogger(PermissionManager.class.getName());

    public boolean canUploadData(Credentials credentials)
    {
        return true;
    }

    public boolean canViewReports(Credentials credentials)
    {
        return true;
    }

    public boolean canUserAccessReport(User user, Report report)
    {
        boolean result = false;
        for (int i = 0; i < report.fullRoles().size(); i++)
        {
            logger.info("Report Role: " + report.fullRoles().get(i).name());
            for (int j = 0; j < user.fullRoles().size(); j++)
            {
                logger.info("User Role: " + user.fullRoles().get(j).name());

                if (report.fullRoles().get(i).id().equals(user.fullRoles().get(j).id()))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

}
