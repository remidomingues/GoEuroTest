/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLHandshakeException;
import org.json.JSONException;

/**
 * Main class of the application, querying the GoEuro API in order to retrieve
 * a JSON stream containing geolocalized information about the location
 * specified in the program's arguments. These information are then exported to
 * a CSV file.
 * Call: java -jar GoEuroTest.jar [-v] "location description"
 * Quote marks are required if the location name contains spaces
 * @author Remi Domingues
 */
public class GoEuroTest
{
	/** Optional program option, enable the validation of every SSL certificate */
	private static final String VALIDATE_CERT_OPTION = "-v";
	
	/**
	 * Displays an error message using a logger, then exits
	 * @param msg The error message to log
	 */
    public static void exitOnError(String msg)
    {
        Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, msg);
        System.exit(1);
    }
    
    /**
     * Query the GoEuro API with the location given in parameter
     * Parse the JSON stream retrieved
     * Export the position obtained to a CSV file
     * @param location The location to geolocalize
     * @param validate true if SSL certificates have to be validated, false else
     */
    private static void runTest(String location, boolean validate)
    {
        String result = null;
        List<Position> positions = null;
        
        try
        {
            result = HTTPSManager.processHTTPSRequest(location, validate);
        }
        catch(SSLHandshakeException ex)
        {
            exitOnError("Unable to find valid certificate to requested target");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            exitOnError("Unable to connect or retrieve data from requested target");
        }
        
        try
        {
            positions = JSONParser.parse(result);
        }
        catch (JSONException ex)
        {
            exitOnError("JSON parsing error: Invalid JSON stream");
        }
        
        try
        {
            location = location.replace("%20", " ");
            CSVSerializer.serialize(location, positions);
        }
        catch (FileNotFoundException ex)
        {
            exitOnError(String.format("Cannot create file in directory %s", System.getProperty("user.dir")));
        }
    }

    /**
     * See class description
     * @param args The program must take in argument a string describing a
     * location
     * Quote marks are necessary if the location name contains spaces
     * Spaces will be replaced by specialized characters
     */
    public static void main(String[] args)
    {
    	boolean validate = false;
        String location = null;
        String correctCall = "Correct syntax: java -jar GoEuroTest.jar [-v] \"location description\"";
        Locale.setDefault(Locale.US);
        
        if(args.length < 1 || args.length > 2)
        {
            exitOnError(String.format("Invalid arguments number: %s", correctCall));
        }
        
        if(args.length == 2)
        {
        	if(!args[0].equals(VALIDATE_CERT_OPTION))
        	{
        		exitOnError(String.format("Invalid argument received: %s (%s expected)", args[0], VALIDATE_CERT_OPTION));
        	}
        	
        	validate = true;
        	location = args[1];
        }
        else
        {
        	location = args[0];
        }
        
        if(location.length() == 0)
        {
            exitOnError(String.format("Location description cannot be empty", correctCall));
        }
        
        location = location.replace(" ", "%20");

        GoEuroTest.runTest(location, validate);
    }
}
