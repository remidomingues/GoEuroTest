/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package goeurotest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author Deeper
 */
public class HTTPSManager
{
    private static final String GOEURO_URL_TEMPLATE = "https://api.goeuro.com/api/v1/suggest/position/en/name/%s";
    
    private static final String TRUST_STORE_PATH = System.setProperty("javax.net.ssl.trustStore", "cacerts.jks");
    
    private static final String TRUST_STORE_PWD = "changeit";
    
    private static final String SECURE_SOCKET_PROTOCOL = "TLS";
    
    public static String processHTTPSRequest(final String location) throws MalformedURLException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException
    {        
        String line;
        StringBuilder sb = new StringBuilder();
        URL goEuroUrl = new URL(String.format(GOEURO_URL_TEMPLATE, location));
        HttpsURLConnection connection = (HttpsURLConnection) goEuroUrl.openConnection();
        
        TrustModifier.relaxHostChecking(connection);
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        while((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        
        return sb.toString();
    } 
}
