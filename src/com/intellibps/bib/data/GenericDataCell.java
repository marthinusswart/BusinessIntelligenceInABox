package com.intellibps.bib.data;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/29
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenericDataCell
{
    private String name;
    private Object value;

    public String name()
    {
        return name;
    }

    public void name(String name)
    {
        this.name = name;
    }

    public Object value()
    {
        return value;
    }

    public void value(Object value)
    {
        this.value = value;
    }

    public GenericDataCell()
    {

    }

    public GenericDataCell(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }
}
