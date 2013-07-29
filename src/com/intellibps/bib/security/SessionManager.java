package com.intellibps.bib.security;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.intellibps.bib.persistence.PersistenceController;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
    private int loginTimeOut = 1000* 60 * 5; // 5 min
    private java.util.logging.Logger logger = Logger.getLogger(SessionManager.class.getName());
    private SecureRandom secureRandom = new SecureRandom();

    public boolean isLoggedIn(String sessionId)
    {
        boolean exists = false;
        logger.info("Checking whether the sessionId exists in the memcache");
        exists =  memcacheService.contains(sessionId);
        logger.info("sessionId exists? : " + exists);

        if (exists)
        {
            Credentials credentials = (Credentials) memcacheService.get(sessionId);
            logger.info("Login time: " + credentials.loginTime().toString());
            Date timeNow = new Date(System.currentTimeMillis());
            if (timeNow.getTime() - credentials.loginTime().getTime() > loginTimeOut)
            {
                logger.info("Logout timer was hit: " + credentials.loginTime().toString());
                memcacheService.delete(sessionId);
                exists = false;
            }
            else
            {
                // reset timeout
                credentials.loginTime(timeNow);
                logger.info("Logout timer reset: " + credentials.loginTime().toString());
                memcacheService.put(sessionId,credentials);
            }
        }

        return exists;
    }

    public String login(String email, String password) throws NullPointerException
    {
        String result = null;

        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.security.User WHERE email == :email");
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        try
        {
            List<User> results = (List<User>) query.execute(email);

            if (results.size() > 0)
            {
                User user = results.get(0);
                if (passwordEncryptor.checkPassword(password, user.password()))
                {

                    Credentials credentials = new Credentials();
                    credentials.email(user.email());
                    credentials.loginTime(new Date(System.currentTimeMillis()));
                    credentials.username(user.firstname());
                    credentials.companyId(user.company());
                    credentials.userId(user.id());

                    result = nextSessionId();

                    // Add to memcache
                    memcacheService.put(result, credentials);


                }
            }
        }
        catch (EncryptionOperationNotPossibleException ex)
        {
           result = null;
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

        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
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

                user.roles().add(persistedRole.id());
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

    public boolean encryptDefaultUserPassword()
    {
        boolean result = false;

        PersistenceManager persistenceManager = PersistenceController.persistenceManagerFactory().getPersistenceManager();
        Query query = persistenceManager.newQuery("SELECT FROM com.intellibps.bib.security.User WHERE email == :email");


        try
        {
            List<User> results = (List<User>) query.execute("marthinus.swart@intellibps.com");

            if (results.size() ==  1)
            {
                User user = results.get(0);
                user.encryptPassword();
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

    private String nextSessionId()
    {
        return new BigInteger(130, secureRandom).toString(32);
    }

    public Credentials credentials(String sessionId)
    {
        return (Credentials) memcacheService.get(sessionId);
    }

}
