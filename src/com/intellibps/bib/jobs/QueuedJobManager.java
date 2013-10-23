package com.intellibps.bib.jobs;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.intellibps.bib.data.DataHeading;
import com.intellibps.bib.data.GenericData;
import com.intellibps.bib.persistence.PersistenceController;
import com.intellibps.bib.util.ExcelHeading;
import com.intellibps.bib.util.ExcelHelper;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
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
        persistenceManager.close();
        return true;
    }

    public void processQueuedJobs()
    {
        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.jobs.QueuedUploadJob WHERE processedDate == null");
        List<QueuedUploadJob> queuedUploadJobs;


        queuedUploadJobs = (List<QueuedUploadJob>) query.execute();

        logger.info("QueuedUploadJobs loaded " + queuedUploadJobs.size());

        for (int i = 0; i < queuedUploadJobs.size(); i++)
        {
            QueuedUploadJob queuedUploadJob = queuedUploadJobs.get(i);
            if (processQueuedJob(queuedUploadJob))
            {
                queuedUploadJob.processedDate(new Date());
                persistenceManager.makePersistent(queuedUploadJob);
            }
        }

        persistenceManager.close();
    }

    private boolean processQueuedJob(QueuedUploadJob queuedUploadJob)
    {
        boolean result = true;

        try
        {
            byte[] fileData = blobstoreService.fetchData(new BlobKey(queuedUploadJob.fileBlobKey()), 0, queuedUploadJob.size());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
            ExcelHelper excelHelper = new ExcelHelper();
            excelHelper.openExcel(byteArrayInputStream);

            int sheetCount = excelHelper.getWorkSheetCount();

            for (int i = 0; i < sheetCount; i++)
            {
                excelHelper.setActiveSheet(i);
                if (!processSheet(excelHelper, queuedUploadJob.company()))
                {
              result = false;
                }
            }
        } catch (Exception ex)
        {
            logger.severe("Failed to process the job: " + queuedUploadJob.id() + ". " + ex.toString());
            result = false;
        }


        return result;

    }

    private boolean processSheet(ExcelHelper excelHelper, Key company)
    {
        boolean result = true;

        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        ExcelHeading[] headings = excelHelper.getSheetHeadings();
        String dataSetName = excelHelper.getSheetName();

        GenericData dataSet = new GenericData();
        dataSet.name(dataSetName);
        dataSet.processedDate(new Date());
        dataSet.company(company);

        for (int i = 0; i < headings.length; i++)
        {
            loadHeaderData(headings[i], dataSet, excelHelper);
        }


        logger.info("DataSet name: " + dataSet.name());

        Enumeration<DataHeading> dataHeadings = dataSet.headings().elements();
        while (dataHeadings.hasMoreElements())
        {
            DataHeading dataHeading = dataHeadings.nextElement();
            logger.info("Data Heading name: " + dataHeading.name() + ", Type: " + dataHeading.headingType());
            switch (dataHeading.headingType().intValue())
            {
                case DataHeading.DATE : logger.info("Date count: " + dataSet.dateValues().get(dataHeading.name()).size());      break;
                case DataHeading.NUMBER : logger.info("Number count: " + dataSet.numberValues().get(dataHeading.name()).size());      break;
                case DataHeading.STRING : logger.info("String count: " + dataSet.stringValues().get(dataHeading.name()).size());      break;

            }
        }

        dataSet.prepareForPersistence();
        persistenceManager.makePersistent(dataSet);

        return result;
    }

    private boolean loadHeaderData(ExcelHeading heading, GenericData dataSet, ExcelHelper excelHelper)
    {
        boolean result = true;
        try
        {
            int dataType = excelHelper.getDataType(heading);
            dataSet.headings().add(new DataHeading(heading.name(), dataType));

            switch (dataType)
            {
                case DataHeading.DATE:
                    loadDateValues(dataSet, heading, excelHelper);
                    break;
                case DataHeading.NUMBER:
                    loadNumberValues(dataSet, heading, excelHelper);
                    break;
                case DataHeading.STRING:
                    loadStringValues(dataSet, heading, excelHelper);
                    break;
            }
        } catch (Exception ex)
        {
            logger.severe(ex.toString());
            result = false;
        }

        return result;
    }

    private void loadNumberValues(GenericData dataSet, ExcelHeading heading, ExcelHelper excelHelper)
    {
        Vector<Double> dateValues = dataSet.getNumberValues(heading.name());
        excelHelper.loadNumberValues(dateValues, heading);
    }

    private void loadStringValues(GenericData dataSet, ExcelHeading heading, ExcelHelper excelHelper)
    {
        Vector<String> stringValues = dataSet.getStringValues(heading.name());
        excelHelper.loadStringValues(stringValues, heading);
    }

    private void loadDateValues(GenericData dataSet, ExcelHeading heading, ExcelHelper excelHelper)
    {
      Vector<Date> dateValues = dataSet.getDateValues(heading.name());
        excelHelper.loadDateValues(dateValues, heading);
    }
}
