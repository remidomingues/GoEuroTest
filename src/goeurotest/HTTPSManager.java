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
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;

/**
 * Service class in charge of retrieving information from an HTTPS URL
 * @author Rémi Domingues
 */
public class HTTPSManager
{
    private static final String GOEURO_URL_TEMPLATE = "https://api.goeuro.com/api/v1/suggest/position/en/name/%s";
    
    private static final String TRUST_STORE_PATH = System.setProperty("javax.net.ssl.trustStore", "cacerts.jks");
    
    private static final String TRUST_STORE_PWD = "changeit";
    
    private static final String SECURE_SOCKET_PROTOCOL = "TLS";
    
    public static String processHTTPSRequest(final String location, boolean checkCertificates) throws MalformedURLException, SSLHandshakeException, IOException
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
            catch(KeyManagementException | NoSuchAlgorithmException | KeyStoreException ex)
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
