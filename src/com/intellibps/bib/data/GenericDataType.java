package com.intellibps.bib.data;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/22
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenericDataType
{
    private String name;

    public GenericDataType(String name)
    {
        this.name = name;
    }


    public String name()
    {
        return name;
    }

    public void name(String name)
    {
        this.name = name;
    }
}
