package com.intellibps.bib.jobs;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class QueuedUploadJob
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String fileBlobKey;
    @Persistent
    private Date queuedDate;
    @Persistent
    private Date processedDate;
    @Persistent
    private Long size;
    @Persistent
    private com.google.appengine.api.datastore.Key company;
    @Persistent
    private com.google.appengine.api.datastore.Key user;

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public String fileBlobKey()
    {
        return fileBlobKey;
    }

    public void fileBlobKey(String fileBlobKey)
    {
        this.fileBlobKey = fileBlobKey;
    }

    public Date qeuedDate()
    {
        return queuedDate;
    }

    public void queuedDate(Date queuedDate)
    {
        this.queuedDate = queuedDate;
    }

    public Date processedDate()
    {
        return processedDate;
    }

    public void processedDate(Date processedDate)
    {
        this.processedDate = processedDate;
    }

    public Key company()
    {
        return company;
    }

    public void company(Key company)
    {
        this.company = company;
    }

    public Key user()
    {
        return user;
    }

    public void user(Key user)
    {
        this.user = user;
    }

    public long size() {
        return size;
    }

    public void size(long size) {
        this.size = size;
    }
}
