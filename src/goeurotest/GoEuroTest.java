/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.io.*;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLHandshakeException;
import org.json.JSONException;

/**
 * Main class of the application, querying the GoEuro API in order to retrieve
 * a JSON stream containing geolocalized information about the location
 * specified in the program's arguments. These information are then exporter to
 * a CSV file.
 * Call: java -jar GoEuroTest.jar \"location description\"
 * The location description must be surrounded by quotation marks.
 * Spaces are accepted
 * @author Rémi Domingues
 */
public class GoEuroTest
{
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
     */
    private static void runTest(String location)
    {
        String result = null;
        List<Position> positions = null;
        
        try
        {
            result = HTTPSManager.processHTTPSRequest(location, false);
        }
        catch(SSLHandshakeException ex)
        {
            exitOnError("Unable to find valid certificate to requested target");
        }
        catch (IOException ex)
        {
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
     * @see class description
     * @param args The program must take in argument a string describing a
     * location
     * Spaces will be replaced by specialized characters
     */
    public static void main(String[] args)
    {
        String location = null;
        String correctCall = "Correct syntax: java -jar GoEuroTest.jar \"location description\"";
        Locale.setDefault(Locale.US);
        
        if(args.length != 1)
        {
            exitOnError(String.format("Invalid arguments number: %s", correctCall));
        }
        
        location = args[0];
        
        if(location.length() <= 1 || location.charAt(0) != '"' || location.charAt(location.length() -1) != '"')
        {
            exitOnError(String.format("Invalid argument format: %s", correctCall));
        }
        
        location = location.substring(1, location.length() - 1);
        
        if(location.length() == 0)
        {
            exitOnError(String.format("Location description cannot be empty", correctCall));
        }
        
        location = location.replace(" ", "%20");

        GoEuroTest.runTest(location);
    }
}
