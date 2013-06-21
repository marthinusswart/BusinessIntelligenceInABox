package com.intellibps.bib.security;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.intellibps.bib.persistence.PersistanceController;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/06/18
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public class SessionManager
{
    private MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
    private int loginTimeOut = 60 * 30; // 30 min

    public boolean isLoggedIn(String email)
    {
        return memcacheService.contains(email);
    }

    public boolean login(String email, String password) throws NullPointerException
    {
        boolean result = false;

        PersistenceManager persistenceManager = PersistanceController.persistenceManagerFactory.getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.security.User WHERE email == :email");

        try
        {
            List<User> results = (List<User>) query.execute(email);

            if (results.size() > 0)
            {
                User user = results.get(0);
                if (user.password().equals(password))
                {
                    Credentials credentials = new Credentials();
                    credentials.email(user.email());
                    credentials.loginTime(new Date(System.currentTimeMillis()));
                    credentials.username(user.firstname());

                    // Add to memcache
                    memcacheService.put(email, credentials, Expiration.byDeltaSeconds(loginTimeOut));

                    result = true;
                }
            }
        }

        finally
        {
            persistenceManager.close();
        }


        return result;
    }

    public boolean initialise()
    {
        boolean result = false;

        PersistenceManager persistenceManager = PersistanceController.persistenceManagerFactory.getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.security.User WHERE email == :email");


        try
        {
            List<User> results = (List<User>) query.execute("marthinus.swart@intellibps.com");

            if (results.size() ==  0)
            {
                User user = new User();
                user.email("marthinus.swart@intellibps.com");
                user.firstname("Marthinus");
                user.lastname("Swart");
                user.password("changeme");

                Role role = new Role();
                role.name("Superuser");
                role.description("The super user role, has access everywhere");
                Role persistedRole = persistenceManager.makePersistent(role);

                user.roles().add(persistedRole.Id());
                persistenceManager.makePersistent(user);

                result = true;
            }
        }

        finally
        {
            persistenceManager.close();
        }

        return result;
    }

}
