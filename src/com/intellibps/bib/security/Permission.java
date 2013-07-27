package com.intellibps.bib.security;

import javax.jdo.annotations.*;

/**
 * Created with IntelliJ IDEA.
 * User: marthinus
 * Date: 2013/07/27
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class Permission
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @NotPersistent
    private boolean isDirty;
    @NotPersistent
    private boolean isNew;
    @NotPersistent
    private boolean isDeleted;
}
