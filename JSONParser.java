/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Service class dedicated to JSON string parsing
 * JSON format accepted :
 * {
 * "results" : [ {
 *      "_type" : "Position",
 *      "_id" : 410978,
 *      "name" : "Potsdam, USA",
 *      "type" : "location",
 *      "geo_position" : {
 *          "latitude" : 44.66978,
 *          "longitude" : -74.98131
 *      }
 * }, {
 *      "_type" : "Position",
 *      "_id" : 377078,
 *      "name" : "Potsdam, Deutschland",
 *      "type" : "location",
 *      "geo_position" : {
 *          latitude" : 52.39886,
 *          "longitude" : 13.06566
 *      }
 * } ]
 * }
 * @author Remi Domingues
 */
public class JSONParser
{
    /** JSON key of the results object, the value must be an array of locations */
    private static final String JSON_RESULTS = "results";
    
    /** JSON Key of the JSON object type */
    private static final String JSON_OBJECT_TYPE = "_type";
    
    /** JSON Key of the JSON object ID, ID must be an integer */
    private static final String JSON_OBJECT_ID = "_id";
    
    /** JSON Key of the location name */
    private static final String JSON_DATA_NAME = "name";
    
    /** JSON Key of the location type */
    private static final String JSON_DATA_TYPE = "type";
    
    /** JSON Key of the geo position object, must be an array */
    private static final String JSON_DATA_GEO_POS = "geo_position";
    
    /** JSON Key of the latitude field, must be a double */
    private static final String JSON_GEO_POS_LAT = "latitude";
    
    /** JSON Key of the longitude field, must be a double */
    private static final String JSON_GEO_POS_LON = "longitude";
    
    /**
     * Parses a JSON string and returns a list of locations built from it
     * @param jsonContent The JSON string to parse
     * @return A list of location parsed from the JSON string
     * @throws JSONException If an error occurs during the parsing (invalid format)
     */
    public static List<Position> parse(final String jsonContent)
            throws JSONException
    {
        int _id;
        double lat, lon;
        String _type, name, type;
        List<Position> positions = null;
        JSONObject tmpPosition, tmpGeoPos;
        
        Logger.getLogger(GoEuroTest.class.getName()).log(Level.INFO, String.format("Parsing JSON stream..."));
        
        JSONObject parser = new JSONObject(jsonContent);
        JSONArray results = parser.getJSONArray(JSON_RESULTS);
        
        positions = new ArrayList<Position>(results.length());
        
        for(int i = 0; i < results.length(); ++i)
        {
            try
            {
                tmpPosition = results.getJSONObject(i);
                _type = tmpPosition.getString(JSON_OBJECT_TYPE);

                _id = tmpPosition.getInt(JSON_OBJECT_ID);
                name = tmpPosition.getString(JSON_DATA_NAME);
                type = tmpPosition.getString(JSON_DATA_TYPE);

                tmpGeoPos = tmpPosition.getJSONObject(JSON_DATA_GEO_POS);

                lat = tmpGeoPos.getDouble(JSON_GEO_POS_LAT);
                lon = tmpGeoPos.getDouble(JSON_GEO_POS_LON);
                
                positions.add(new Position(_type, _id, name, type, lat, lon));
            }
            catch(JSONException ex)
            {
                Logger.getLogger(GoEuroTest.class.getName()).log(Level.WARNING, String.format("JSON parsing error: JSON object ignored"));
            }
        }
        
        return positions;
    }
}
