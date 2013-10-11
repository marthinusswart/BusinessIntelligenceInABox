package com.intellibps.bib.jobs;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.intellibps.bib.customer.Company;
import com.intellibps.bib.security.User;
import com.intellibps.bib.persistence.PersistenceController;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class QueuedJobManager
{
    private java.util.logging.Logger logger = Logger.getLogger(QueuedJobManager.class.getName());
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public boolean addUploadJob(QueuedUploadJob queuedUploadJob)
    {
        logger.info("QueuedUploadJob persisting, company: " + queuedUploadJob.company().getId());

        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        persistenceManager.makePersistent(queuedUploadJob);
        return true;
    }

    public void processQueuedJobs()
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.jobs.QueuedUploadJob WHERE processedDate == null");
        List<QueuedUploadJob> queuedUploadJobs;


        queuedUploadJobs = (List<QueuedUploadJob>) query.execute();

        logger.info("QueuedUploadJobs loaded " + queuedUploadJobs.size());

        for (int i=0; i<queuedUploadJobs.size(); i++)
        {
            processQueuedJob(queuedUploadJobs.get(i));
        }


    }

    private boolean processQueuedJob(QueuedUploadJob queuedUploadJob)
    {
        boolean result = true;
        logger.info("The file blob key: " + queuedUploadJob.fileBlobKey());
        //blobstoreService.fetchData(queuedUploadJob.fileBlobKey(), )
        return result;
    }
}
