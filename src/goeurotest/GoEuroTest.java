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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLHandshakeException;
import org.json.JSONException;

/**
 *
 * @author remidomingues
 */
public class GoEuroTest
{
    
    private static void runTest(String location)
    {
        String result = null;
        List<Position> positions = null;
        
        try
        {
            result = HTTPSManager.processHTTPSRequest(location);
        }
        catch (KeyStoreException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        catch (NoSuchAlgorithmException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        catch (KeyManagementException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        catch (CertificateException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        catch(SSLHandshakeException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, "Unable to find valid certificate to requested target", ex);
            System.exit(1);
        }
        catch (IOException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, "Unable to connect or retrieve data from requested target", ex);
            System.exit(1);
        }
        
        System.out.println(result);
        
        try
        {
            positions = JSONParser.parse(result);
        }
        catch (JSONException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        try
        {
            CSVSerializer.serialize(location, positions);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        catch (UnsupportedEncodingException ex) 
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

 
    public static void main(String[] args) throws IOException
    {
        Locale.setDefault(Locale.US);
        
        if(args.length != 1)
        {
            Logger.getLogger(GoEuroTest.class.getName()).log(Level.SEVERE, "Invalid parameters number : this test requires as input a string describing a location");
            System.exit(1);
        }

        GoEuroTest.runTest(args[0]);
    }
}
