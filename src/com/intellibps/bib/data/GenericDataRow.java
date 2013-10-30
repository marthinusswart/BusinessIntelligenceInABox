package com.intellibps.bib.data;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/29
 * Time: 6:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenericDataRow
{
    private Vector<Object> columns = new Vector<Object>();

    public Vector<Object> columns()
    {
        return columns;
    }

    public void columns(Vector<Object> columns)
    {
        this.columns = columns;
    }
}
