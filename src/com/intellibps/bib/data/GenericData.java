package com.intellibps.bib.data;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/10/15
 * Time: 7:11 AM
 * To change this template use File | Settings | File Templates.
 */

@PersistenceCapable
public class GenericData
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private com.google.appengine.api.datastore.Key id;
    @Persistent
    private String name;
    @Persistent
    private Date processedDate;
    @NotPersistent
    private Integer recordCount;
    @Persistent
    private com.google.appengine.api.datastore.Key company;
    @Persistent
    private Vector<DataHeading> headings = new Vector<DataHeading>();
    @NotPersistent
    private HashMap<String, Vector<Double>> numberValues = new HashMap<String, Vector<Double>>();
    @NotPersistent
    private HashMap<String, Vector<String>> stringValues = new HashMap<String, Vector<String>>();
    @NotPersistent
    private HashMap<String, Vector<Date>> dateValues = new HashMap<String, Vector<Date>>();

    public String name()
    {
        return name;
    }

    public void name(String name)
    {
        this.name = name;
    }

    public Vector<DataHeading> headings()
    {
        return headings;
    }

    public void headings(Vector<DataHeading> headings)
    {
        this.headings = headings;
    }

    public HashMap<String, Vector<Double>> numberValues()
    {
        return numberValues;
    }

    public void numberValues(HashMap<String, Vector<Double>> numberValues)
    {
        this.numberValues = numberValues;
    }

    public HashMap<String, Vector<String>> stringValues()
    {
        return stringValues;
    }

    public void stringValues(HashMap<String, Vector<String>> stringValues)
    {
        this.stringValues = stringValues;
    }

    public HashMap<String, Vector<Date>> dateValues()
    {
        return dateValues;
    }

    public void dateValues(HashMap<String, Vector<Date>> dateValues)
    {
        this.dateValues = dateValues;
    }

    public Key id()
    {
        return id;
    }

    public void id(Key id)
    {
        this.id = id;
    }

    public Date processedDate()
    {
        return processedDate;
    }

    public void processedDate(Date processedDate)
    {
        this.processedDate = processedDate;
    }

    public DataHeading getHeading(String name)
    {

        for (int i = 0; i < headings().size(); i++)
        {
            if (headings().get(i).name().equals(name))
            {
                return headings().get(i);
            }
        }

        return null;
    }

    public Vector<Date> getDateValues(String name)
    {
        if (dateValues.containsKey(name))
        {
            return dateValues.get(name);
        } else
        {
            dateValues.put(name, new Vector<Date>());
            return dateValues.get(name);
        }
    }

    public Vector<Double> getNumberValues(String name)
    {
        if (numberValues.containsKey(name))
        {
            return numberValues.get(name);
        } else
        {
            numberValues.put(name, new Vector<Double>());
            return numberValues.get(name);
        }
    }

    public Vector<String> getStringValues(String name)
    {
        if (stringValues.containsKey(name))
        {
            return stringValues.get(name);
        } else
        {
            stringValues.put(name, new Vector<String>());
            return stringValues.get(name);
        }
    }

    public void prepareForPersistence()
    {
        Iterator<String> keys = numberValues().keySet().iterator();
        while (keys.hasNext())
        {
            DataHeading dataHeading = getHeading(keys.next());
            Vector<Double> values = numberValues().get(dataHeading.name());
            for (int i = 0; i < values.size(); i++)
            {
                dataHeading.numberValues().add(values.get(i));
            }
        }

        keys = dateValues().keySet().iterator();
        while (keys.hasNext())
        {
            DataHeading dataHeading = getHeading(keys.next());
            Vector<Date> values = dateValues().get(dataHeading.name());
            for (int i = 0; i < values.size(); i++)
            {
                dataHeading.dateValues().add(values.get(i));
            }
        }

        keys = stringValues().keySet().iterator();
        while (keys.hasNext())
        {
            DataHeading dataHeading = getHeading(keys.next());
            Vector<String> values = stringValues().get(dataHeading.name());
            for (int i = 0; i < values.size(); i++)
            {
                dataHeading.stringValues().add(values.get(i));
            }
        }
    }

    public Key company()
    {
        return company;
    }

    public void company(Key company)
    {
        this.company = company;
    }

    public GenericData clone(boolean excludeData)
    {
        GenericData genericData = new GenericData();
        genericData.name(name);
        genericData.processedDate(processedDate);
        genericData.company(company);
        genericData.id(id);


        Enumeration<DataHeading> enumeration = headings.elements();

        while (enumeration.hasMoreElements())
        {
            DataHeading heading = enumeration.nextElement();
            genericData.headings().add(heading.clone(excludeData));
        }
        return genericData;
    }

    public Integer recordCount()
    {
        return recordCount;
    }

    public void recordCount(Integer recordCount)
    {
        this.recordCount = recordCount;
    }

    public Vector<Vector<GenericDataCell>> toGrid()
    {
        Vector<Vector<GenericDataCell>> grid = new Vector<Vector<GenericDataCell>>();
        DecimalFormat df = new DecimalFormat("###.##");
        SimpleDateFormat ft =
                new SimpleDateFormat("yyyy/MM/dd");

        for (int i = 0; i < headings.size(); i++)
        {

            for (int j = 0; j < recordCount; j++)
            {
                Vector<GenericDataCell> row = null;

                if (i == 0)
                {
                    row = new Vector<GenericDataCell>();
                    grid.add(row);
                } else
                {
                    row = grid.get(j);
                }

                switch (headings.get(i).headingType())
                {
                    case DataHeading.NUMBER:
                        row.add(new GenericDataCell("\"" + headings.get(i).name().replace(" ", "_") + "\"", df.format(headings.get(i).numberValues().get(j))));
                        break;
                    case DataHeading.STRING:
                        row.add(new GenericDataCell("\"" + headings.get(i).name().replace(" ", "_") + "\"", "\"" + headings.get(i).stringValues().get(j) + "\""));
                        break;
                    case DataHeading.DATE:
                        row.add(new GenericDataCell("\"" + headings.get(i).name().replace(" ", "_") + "\"", "\"" + ft.format(headings.get(i).dateValues().get(j)) + "\""));
                        break;
                }

            }
        }

        return grid;
    }

    public String toJson()
    {
        String json = "[";

        Vector<Vector<GenericDataCell>> grid = toGrid();

        for (int i = 0; i < grid.size(); i++)
        {

            String line = "";

            if (i > 0)
            {
                line = line + ",";
            }

            line = line + "{";

            for (int j = 0; j < grid.get(i).size(); j++)
            {
                if (j > 0)
                {
                    line = line + ",";
                }

                line = line + grid.get(i).get(j).name() + ":" + grid.get(i).get(j).value();

            }

            line = line + "}";
            json = json + line;
        }

        json = json + "]";
        return json;
    }
}
