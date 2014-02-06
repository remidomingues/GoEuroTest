/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

/**
 * JSON object data class
 * @author Remi Domingues
 */
public class Position
{
    /** JSON object type */
    private String _type;
    
    /** JSON object ID */
    private int _id;
    
    /** Position name */
    private String name;
    
    /** Position type */
    private String type;
    
    /** Latitude of the position */
    private double latitude;
    
    /** Longitude of the position */
    private double longitude;

    /**
     * Constructor
     * @param _type JSON object type
     * @param _id JSON object ID
     * @param name Name
     * @param type Type
     * @param latitude Latitude
     * @param longitude Longitude
     */
    public Position(String _type, int _id, String name, String type, double latitude, double longitude)
    {
        this._type = _type;
        this._id = _id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Returns a string describing the current object in a CSV format
     * @return The description of the current position in a CSV format
     */
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
        
        sb.append(CSVSerializer.CSV_COORD_FIELD_TEMPLATE.format(longitude));
    
        sb.append(CSVSerializer.CSV_CARRIAGE_RETURN);
        
        return sb.toString();
    }
}
