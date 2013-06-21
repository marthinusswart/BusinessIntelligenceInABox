package com.intellibps.bib.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/18
 * Time: 20:26
 * To change this template use File | Settings | File Templates.
 */
public class PersistanceController
{
    public static PersistenceManagerFactory persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");
}
