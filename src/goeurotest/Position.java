/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

/**
 *
 * @author Deeper
 */
public class Position
{
    private String _type;
    
    private int _id;
    
    private String name;
    
    private String type;
    
    private double latitude;
    
    private double longitude;

    public Position(String _type, int _id, String name, String type, double latitude, double longitude)
    {
        this._type = _type;
        this._id = _id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public String toCSV()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format(CSVSerializer.CSV_STRING_FIELD_TEMPLATE, _type));
        sb.append(CSVSerializer.CSV_SEPARATOR);
        
        sb.append(String.format(CSVSerializer.CSV_INT_FIELD_TEMPLATE, _id));
        sb.append(CSVSerializer.CSV_SEPARATOR);
        
        sb.append(String.format(CSVSerializer.CSV_STRING_FIELD_TEMPLATE, name));
        sb.append(CSVSerializer.CSV_SEPARATOR);
        
        sb.append(String.format(CSVSerializer.CSV_STRING_FIELD_TEMPLATE, type));
        sb.append(CSVSerializer.CSV_SEPARATOR);
        
        sb.append(CSVSerializer.CSV_COORD_FIELD_TEMPLATE.format(latitude));
        sb.append(CSVSerializer.CSV_SEPARATOR);
        
        sb.append(CSVSerializer.CSV_COORD_FIELD_TEMPLATE.format(latitude));
    
        sb.append(CSVSerializer.CSV_CARRIAGE_RETURN);
        
        System.out.println(sb.toString());
        return sb.toString();
    }
}
