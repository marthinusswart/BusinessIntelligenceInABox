package com.intellibps.bib.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/18
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceController
{
    private static PersistenceManagerFactory persistenceManagerFactory =  JDOHelper.getPersistenceManagerFactory("transactions-optional");;
    private static java.util.logging.Logger logger = Logger.getLogger(PersistenceController.class.getName());




    public static PersistenceManagerFactory persistenceManagerFactory()
    {

        return  persistenceManagerFactory;
    }

}
