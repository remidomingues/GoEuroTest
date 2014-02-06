/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

/**
 * Service class in charge of retrieving information from an HTTPS URL
 * SSL certificate validation can be disable if the certificate is self-signed
 * or has expired
 * @author Remi Domingues
 */
public class HTTPSManager
{
    /** HTTPS URL to retrieve information from */
    private static final String GOEURO_URL_TEMPLATE = "https://api.goeuro.com/api/v1/suggest/position/en/name/%s";
    
    /**
     * Processes an HTTPS request and returns the data retrieved
     * This request aims at retrieving geolocalized information from a
     * location
     * @param location The location searched
     * @param checkCertificates true if a valid certificate is required,
     *                          false if self-signed or expired certificates
     *                          are allowed
     * @return Every location with its geographic coordinates which mathes
     *         the location given in parameter
     * @throws MalformedURLException If the URL is malformed
     * @throws SSLHandshakeException If the certificate is self-signed or expired
     * @throws SSLException  If the certificate cannot be validated
     * @throws IOException If an error occurs when retrieving data
     */
    public static String processHTTPSRequest(final String location, boolean checkCertificates)
            throws MalformedURLException, SSLHandshakeException, SSLException, IOException
    {        
        String line;
        StringBuilder sb = new StringBuilder();
        String strUrl = String.format(GOEURO_URL_TEMPLATE, location);
        
        Logger.getLogger(GoEuroTest.class.getName()).log(Level.INFO, String.format("Retrieving information from %s...", strUrl));
        
        URL goEuroUrl = new URL(strUrl);
        HttpsURLConnection connection = (HttpsURLConnection) goEuroUrl.openConnection();
        
        if(!checkCertificates)
        {
            try
            {
                TrustModifier.relaxHostChecking(connection);
            }
            catch(KeyManagementException ex)
            {
                Logger.getLogger(GoEuroTest.class.getName()).log(Level.WARNING, "Unable to relax certificates validation. Usual validation process will be applied");
            }
            catch(NoSuchAlgorithmException ex)
            {
                Logger.getLogger(GoEuroTest.class.getName()).log(Level.WARNING, "Unable to relax certificates validation. Usual validation process will be applied");
            }
            catch(KeyStoreException ex)
            {
                Logger.getLogger(GoEuroTest.class.getName()).log(Level.WARNING, "Unable to relax certificates validation. Usual validation process will be applied");
            }
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        while((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        
        return sb.toString();
    }
}
