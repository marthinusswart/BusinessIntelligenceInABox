package com.intellibps.bib.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.intellibps.bib.jobs.QueuedJobManager;
import com.intellibps.bib.jobs.QueuedUploadJob;
import com.intellibps.bib.security.Credentials;
import com.intellibps.bib.security.SessionManager;
import com.intellibps.bib.util.WebHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class Upload extends HttpServlet
{
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    SessionManager sessionManager = new SessionManager();
    QueuedJobManager queuedJobManager = new QueuedJobManager();
    WebHelper webHelper = new WebHelper();

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!sessionManager.isLoggedIn(webHelper.getSessionId(request.getCookies())))
        {
            response.sendRedirect("/main");
        }
        else
        {
            Credentials credentials = sessionManager.credentials(webHelper.getSessionId(request.getCookies()));

            Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
            Map<String, List<FileInfo>> mapFileInfos =  blobstoreService.getFileInfos(request);
            List<BlobKey> blobKeys = blobs.get("companyFile");
            List<FileInfo> fileInfos = mapFileInfos.get("companyFile");
            BlobKey blobKey = blobKeys.get(0);
            FileInfo fileInfo = fileInfos.get(0);

            QueuedUploadJob queuedUploadJob = new QueuedUploadJob();
            queuedUploadJob.company(credentials.companyId());
            queuedUploadJob.user(credentials.userId());
            queuedUploadJob.fileBlobKey( blobKey.getKeyString());
            queuedUploadJob.queuedDate(new Date());
            queuedUploadJob.size(fileInfo.getSize());
            queuedJobManager.addUploadJob(queuedUploadJob);

            response.sendRedirect("/main");

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
