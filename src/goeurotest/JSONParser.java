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
 *
 * @author Deeper
 */
public class JSONParser
{
    private static final String JSON_RESULTS = "results";
    
    private static final String JSON_OBJECT_TYPE = "_type";
    
    private static final String JSON_OBJECT_ID = "_id";
    
    private static final String JSON_DATA_NAME = "name";
    
    private static final String JSON_DATA_TYPE = "type";
    
    private static final String JSON_DATA_GEO_POS = "geo_position";
    
    private static final String JSON_GEO_POS_LAT = "latitude";
    
    private static final String JSON_GEO_POS_LON = "longitude";
    
    public static List<Position> parse(final String jsonContent) throws JSONException
    {
        int _id;
        double lat, lon;
        String _type, name, type;
        List<Position> positions = null;
        JSONObject tmpPosition, tmpGeoPos;
        
        JSONObject parser = new JSONObject(jsonContent);
        JSONArray results = parser.getJSONArray(JSON_RESULTS);
        
        positions = new ArrayList<>(results.length());
        
        for(int i = 0; i < results.length(); ++i)
        {
            //TODO add error management
            tmpPosition = results.getJSONObject(i);
            _type = tmpPosition.getString(JSON_OBJECT_TYPE);
            _id = tmpPosition.getInt(JSON_OBJECT_ID);
            name = tmpPosition.getString(JSON_DATA_NAME);
            type = tmpPosition.getString(JSON_DATA_TYPE);
            
            tmpGeoPos = tmpPosition.getJSONObject(JSON_DATA_GEO_POS);
            
            try
            {
                lat = tmpGeoPos.getDouble(JSON_GEO_POS_LAT);
                lon = tmpGeoPos.getDouble(JSON_GEO_POS_LON);
                
                positions.add(new Position(_type, _id, name, type, lat, lon));
            }
            catch(JSONException ex)
            {
                Logger.getLogger(JSONParser.class.getName()).log(Level.WARNING, String.format("JSON object <%s>:<%s> ignored : Invalid geo position values received (not a double)", _id, name));
            }
        }
        
        return positions;
    }
}
