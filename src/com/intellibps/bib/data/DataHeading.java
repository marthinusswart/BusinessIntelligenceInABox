package com.intellibps.bib.data;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/15
 * Time: 7:15 AM
 * To change this template use File | Settings | File Templates.
 */
@PersistenceCapable
public class DataHeading
{

    public static final int NUMBER = 1;
    public static final int STRING = 2;
    public static final int DATE = 3;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String name;
    @Persistent
    private Integer headingType;
    @Persistent
    private Vector<Double> numberValues = new Vector<Double>();
    @Persistent
    private Vector<String> stringValues = new Vector<String>();
    @Persistent
    private Vector<Date> dateValues = new Vector<Date>();

    public DataHeading(String name, int dataType)
    {
        this.name = name;
        this.headingType = dataType;
    }

    public String name()
    {
        return name;
    }

    public void name(String name)
    {
        this.name = name;
    }

    public Integer headingType()
    {
        return headingType;
    }

    public void headingType(Integer headingType)
    {
        this.headingType = headingType;
    }

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public Vector<Double> numberValues()
    {
        return numberValues;
    }

    public void numberValues(Vector<Double> numberValues)
    {
        this.numberValues = numberValues;
    }

    public Vector<String> stringValues()
    {
        return stringValues;
    }

    public void stringValues(Vector<String> stringValues)
    {
        this.stringValues = stringValues;
    }

    public Vector<Date> dateValues()
    {
        return dateValues;
    }

    public void dateValues(Vector<Date> dateValues)
    {
        this.dateValues = dateValues;
    }

    public DataHeading clone(boolean excludeData)
    {
        DataHeading heading = new DataHeading(name, headingType);

        if (!excludeData)
        {

        }

        return heading;
    }
}
