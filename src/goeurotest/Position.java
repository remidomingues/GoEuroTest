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
}
