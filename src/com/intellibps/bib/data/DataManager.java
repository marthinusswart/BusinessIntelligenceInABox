package com.intellibps.bib.data;

import com.google.appengine.api.datastore.Key;
import com.intellibps.bib.customer.Company;
import com.intellibps.bib.persistence.PersistenceController;
import com.intellibps.bib.security.User;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/22
 * Time: 7:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataManager
{
    PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();

    public Company loadCompany(Key companyId)
    {
        Company company = null;
        company = (Company) persistenceManager.getObjectById(Company.class, companyId.getId());
        return company;
    }

    public List<GenericData> loadGenericData(Company company)
    {
        List<GenericData> dataSet = null;
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.data.GenericData where company==:company");
        dataSet = (List<GenericData>) query.execute(company.id());
        return dataSet;
    }

    public void close()
    {
                persistenceManager.close();
    }

    public List<GenericData> loadGenericData(Company company, String reportDataType)
    {
        List<GenericData> dataSet = null;
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.data.GenericData where company==:company && name==:name");
        dataSet = (List<GenericData>) query.execute(company.id(), reportDataType);
        return dataSet;
    }
}
