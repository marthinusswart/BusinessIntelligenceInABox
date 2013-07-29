package com.intellibps.bib.jobs;

import com.intellibps.bib.customer.Company;
import com.intellibps.bib.security.User;
import com.intellibps.bib.persistence.PersistenceController;

import javax.jdo.PersistenceManager;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class QueuedJobManager
{
    public boolean addUploadJob(QueuedUploadJob queuedUploadJob)
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        persistenceManager.makePersistent(queuedUploadJob);
        return true;
    }
}
